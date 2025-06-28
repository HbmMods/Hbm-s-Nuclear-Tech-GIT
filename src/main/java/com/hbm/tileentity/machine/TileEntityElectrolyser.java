package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.threading.PacketThreading;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.UpgradeManagerNT;
import com.hbm.inventory.container.ContainerElectrolyserFluid;
import com.hbm.inventory.container.ContainerElectrolyserMetal;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIElectrolyserFluid;
import com.hbm.inventory.gui.GUIElectrolyserMetal;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.inventory.recipes.ElectrolyserFluidRecipes;
import com.hbm.inventory.recipes.ElectrolyserFluidRecipes.ElectrolysisRecipe;
import com.hbm.inventory.recipes.ElectrolyserMetalRecipes;
import com.hbm.inventory.recipes.ElectrolyserMetalRecipes.ElectrolysisMetalRecipe;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.*;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.util.BobMathUtil;
import com.hbm.util.CrucibleUtil;
import com.hbm.util.fauxpointtwelve.DirPos;
import com.hbm.util.i18n.I18nUtil;

import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityElectrolyser extends TileEntityMachineBase implements IEnergyReceiverMK2, IFluidStandardTransceiver, IControlReceiver, IGUIProvider, IUpgradeInfoProvider, IFluidCopiable, IMetalCopiable {

	public long power;
	public static final long maxPower = 20000000;
	public static final int usageOreBase = 10_000;
	public static final int usageFluidBase = 10_000;
	public int usageOre;
	public int usageFluid;

	public int progressFluid;
	public int processFluidTime = 100;
	public int progressOre;
	public int processOreTime = 600;

	public MaterialStack leftStack;
	public MaterialStack rightStack;
	public int maxMaterial = MaterialShapes.BLOCK.q(16);

	public FluidTank[] tanks;

	public UpgradeManagerNT upgradeManager = new UpgradeManagerNT();

	public TileEntityElectrolyser() {
		//0: Battery
		//1-2: Upgrades
		//// FLUID
		//3-4: Fluid ID
		//5-10: Fluid IO
		//11-13: Byproducts
		//// METAL
		//14: Crystal
		//15-20: Outputs
		super(21);
		tanks = new FluidTank[4];
		tanks[0] = new FluidTank(Fluids.WATER, 16000);
		tanks[1] = new FluidTank(Fluids.HYDROGEN, 16000);
		tanks[2] = new FluidTank(Fluids.OXYGEN, 16000);
		tanks[3] = new FluidTank(Fluids.NITRIC_ACID, 16000);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int meta) {
		return new int[] { 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 };
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		if(i == 14) return ElectrolyserMetalRecipes.getRecipe(itemStack) != null;
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i != 14;
	}

	@Override
	public String getName() {
		return "container.machineElectrolyser";
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			this.power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			this.tanks[0].setType(3, 4, slots);
			this.tanks[0].loadTank(5, 6, slots);
			this.tanks[1].unloadTank(7, 8, slots);
			this.tanks[2].unloadTank(9, 10, slots);

			if(worldObj.getTotalWorldTime() % 20 == 0) {
				for(DirPos pos : this.getConPos()) {
					this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
					this.trySubscribe(tanks[0].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
					this.trySubscribe(tanks[3].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());

					if(tanks[1].getFill() > 0) this.sendFluid(tanks[1], worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
					if(tanks[2].getFill() > 0) this.sendFluid(tanks[2], worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				}
			}

			upgradeManager.checkSlots(this, slots, 1, 2);
			int speedLevel = upgradeManager.getLevel(UpgradeType.SPEED);
			int powerLevel = upgradeManager.getLevel(UpgradeType.POWER);

			usageOre = usageOreBase - usageOreBase * powerLevel / 4 + usageOreBase * speedLevel;
			usageFluid = usageFluidBase - usageFluidBase * powerLevel / 4 + usageFluidBase * speedLevel;

			for(int i = 0; i < getCycleCount(); i++) {
				if (this.canProcessFluid()) {
					this.progressFluid++;
					this.power -= this.usageFluid;

					if (this.progressFluid >= this.getDurationFluid()) {
						this.processFluids();
						this.progressFluid = 0;
						this.markChanged();
					}
				}

				if (this.canProcessMetal()) {
					this.progressOre++;
					this.power -= this.usageOre;

					if (this.progressOre >= this.getDurationMetal()) {
						this.processMetal();
						this.progressOre = 0;
						this.markChanged();
					}
				}
			}

			if(this.leftStack != null) {

				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset).getOpposite();
				List<MaterialStack> toCast = new ArrayList();
				toCast.add(this.leftStack);

				Vec3 impact = Vec3.createVectorHelper(0, 0, 0);
				MaterialStack didPour = CrucibleUtil.pourFullStack(worldObj, xCoord + 0.5D + dir.offsetX * 5.875D, yCoord + 2D, zCoord + 0.5D + dir.offsetZ * 5.875D, 6, true, toCast, MaterialShapes.NUGGET.q(3) * Math.max (getCycleCount() * speedLevel, 1), impact);

				if(didPour != null) {
					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "foundry");
					data.setInteger("color", didPour.material.moltenColor);
					data.setByte("dir", (byte) dir.ordinal());
					data.setFloat("off", 0.625F);
					data.setFloat("base", 0.625F);
					data.setFloat("len", Math.max(1F, yCoord - (float) (Math.ceil(impact.yCoord) - 0.875) + 2));
					PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, xCoord + 0.5D + dir.offsetX * 5.875D, yCoord + 2, zCoord + 0.5D + dir.offsetZ * 5.875D), new TargetPoint(worldObj.provider.dimensionId, xCoord + 0.5, yCoord + 1, zCoord + 0.5, 50));

					if(this.leftStack.amount <= 0) this.leftStack = null;
				}
			}

			if(this.rightStack != null) {

				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
				List<MaterialStack> toCast = new ArrayList();
				toCast.add(this.rightStack);

				Vec3 impact = Vec3.createVectorHelper(0, 0, 0);
				MaterialStack didPour = CrucibleUtil.pourFullStack(worldObj, xCoord + 0.5D + dir.offsetX * 5.875D, yCoord + 2D, zCoord + 0.5D + dir.offsetZ * 5.875D, 6, true, toCast, MaterialShapes.NUGGET.q(3) * Math.max (getCycleCount() * speedLevel, 1), impact);

				if(didPour != null) {
					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "foundry");
					data.setInteger("color", didPour.material.moltenColor);
					data.setByte("dir", (byte) dir.ordinal());
					data.setFloat("off", 0.625F);
					data.setFloat("base", 0.625F);
					data.setFloat("len", Math.max(1F, yCoord - (float) (Math.ceil(impact.yCoord) - 0.875) + 2));
					PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, xCoord + 0.5D + dir.offsetX * 5.875D, yCoord + 2, zCoord + 0.5D + dir.offsetZ * 5.875D), new TargetPoint(worldObj.provider.dimensionId, xCoord + 0.5, yCoord + 1, zCoord + 0.5, 50));

					if(this.rightStack.amount <= 0) this.rightStack = null;
				}
			}

			this.networkPackNT(50);
		}
	}

	public DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		return new DirPos[] {
				new DirPos(xCoord - dir.offsetX * 6, yCoord, zCoord - dir.offsetZ * 6, dir.getOpposite()),
				new DirPos(xCoord - dir.offsetX * 6 + rot.offsetX, yCoord, zCoord - dir.offsetZ * 6 + rot.offsetZ, dir.getOpposite()),
				new DirPos(xCoord - dir.offsetX * 6 - rot.offsetX, yCoord, zCoord - dir.offsetZ * 6 - rot.offsetZ, dir.getOpposite()),
				new DirPos(xCoord + dir.offsetX * 6, yCoord, zCoord + dir.offsetZ * 6, dir),
				new DirPos(xCoord + dir.offsetX * 6 + rot.offsetX, yCoord, zCoord + dir.offsetZ * 6 + rot.offsetZ, dir),
				new DirPos(xCoord + dir.offsetX * 6 - rot.offsetX, yCoord, zCoord + dir.offsetZ * 6 - rot.offsetZ, dir)
		};
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeLong(this.power);
		buf.writeInt(this.progressFluid);
		buf.writeInt(this.progressOre);
		buf.writeInt(this.usageOre);
		buf.writeInt(this.usageFluid);
		buf.writeInt(this.getDurationFluid());
		buf.writeInt(this.getDurationMetal());
		for(int i = 0; i < 4; i++) tanks[i].serialize(buf);
		buf.writeBoolean(this.leftStack != null);
		buf.writeBoolean(this.rightStack != null);
		if(this.leftStack != null) {
			buf.writeInt(leftStack.material.id);
			buf.writeInt(leftStack.amount);
		}
		if(this.rightStack != null) {
			buf.writeInt(rightStack.material.id);
			buf.writeInt(rightStack.amount);
		}
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.power = buf.readLong();
		this.progressFluid = buf.readInt();
		this.progressOre = buf.readInt();
		this.usageOre = buf.readInt();
		this.usageFluid = buf.readInt();
		this.processFluidTime = buf.readInt();
		this.processOreTime = buf.readInt();
		for(int i = 0; i < 4; i++) tanks[i].deserialize(buf);
		boolean left = buf.readBoolean();
		boolean right = buf.readBoolean();
		if(left) {
			this.leftStack = new MaterialStack(Mats.matById.get(buf.readInt()), buf.readInt());
		}
		if(right) {
			this.rightStack = new MaterialStack(Mats.matById.get(buf.readInt()), buf.readInt());
		}
	}

	public boolean canProcessFluid() {

		if(this.power < usageFluid) return false;

		ElectrolysisRecipe recipe = ElectrolyserFluidRecipes.recipes.get(tanks[0].getTankType());

		if(recipe == null) return false;
		if(recipe.amount > tanks[0].getFill()) return false;
		if(recipe.output1.type == tanks[1].getTankType() && recipe.output1.fill + tanks[1].getFill() > tanks[1].getMaxFill()) return false;
		if(recipe.output2.type == tanks[2].getTankType() && recipe.output2.fill + tanks[2].getFill() > tanks[2].getMaxFill()) return false;

		if(recipe.byproduct != null) {

			for(int i = 0; i < recipe.byproduct.length; i++) {
				ItemStack slot = slots[11 + i];
				ItemStack byproduct = recipe.byproduct[i];

				if(slot == null) continue;
				if(!slot.isItemEqual(byproduct)) return false;
				if(slot.stackSize + byproduct.stackSize > slot.getMaxStackSize()) return false;
			}
		}

		return true;
	}

	public void processFluids() {

		ElectrolysisRecipe recipe = ElectrolyserFluidRecipes.recipes.get(tanks[0].getTankType());
		tanks[0].setFill(tanks[0].getFill() - recipe.amount);
		tanks[1].setTankType(recipe.output1.type);
		tanks[2].setTankType(recipe.output2.type);
		tanks[1].setFill(tanks[1].getFill() + recipe.output1.fill);
		tanks[2].setFill(tanks[2].getFill() + recipe.output2.fill);

		if(recipe.byproduct != null) {

			for(int i = 0; i < recipe.byproduct.length; i++) {
				ItemStack slot = slots[11 + i];
				ItemStack byproduct = recipe.byproduct[i];

				if(slot == null) {
					slots[11 + i] = byproduct.copy();
				} else {
					slots[11 + i].stackSize += byproduct.stackSize;
				}
			}
		}
	}

	public boolean canProcessMetal() {

		if(slots[14] == null) return false;
		if(this.power < usageOre) return false;
		if(this.tanks[3].getFill() < 100) return false;

		ElectrolysisMetalRecipe recipe = ElectrolyserMetalRecipes.getRecipe(slots[14]);
		if(recipe == null) return false;

		if(leftStack != null && recipe.output1 != null) {
			if(recipe.output1.material != leftStack.material) return false;
			if(recipe.output1.amount + leftStack.amount > this.maxMaterial) return false;
		}

		if(rightStack != null && recipe.output2 != null) {
			if(recipe.output2.material != rightStack.material) return false;
			if(recipe.output2.amount + rightStack.amount > this.maxMaterial) return false;
		}

		if(recipe.byproduct != null) {

			for(int i = 0; i < recipe.byproduct.length; i++) {
				ItemStack slot = slots[15 + i];
				ItemStack byproduct = recipe.byproduct[i];

				if(slot == null) continue;
				if(!slot.isItemEqual(byproduct)) return false;
				if(slot.stackSize + byproduct.stackSize > slot.getMaxStackSize()) return false;
			}
		}

		return true;
	}

	public void processMetal() {

		ElectrolysisMetalRecipe recipe = ElectrolyserMetalRecipes.getRecipe(slots[14]);
		if(recipe.output1 != null)
			if(leftStack == null) {
				leftStack = new MaterialStack(recipe.output1.material, recipe.output1.amount);
			} else {
				leftStack.amount += recipe.output1.amount;
			}

		if(recipe.output2 != null)
			if(rightStack == null ) {
				rightStack = new MaterialStack(recipe.output2.material, recipe.output2.amount);
			} else {
				rightStack.amount += recipe.output2.amount;
			}

		if(recipe.byproduct != null) {

			for(int i = 0; i < recipe.byproduct.length; i++) {
				ItemStack slot = slots[15 + i];
				ItemStack byproduct = recipe.byproduct[i];

				if(slot == null) {
					slots[15 + i] = byproduct.copy();
				} else {
					slots[15 + i].stackSize += byproduct.stackSize;
				}
			}
		}

		this.tanks[3].setFill(this.tanks[3].getFill() - 100);
		this.decrStackSize(14, 1);
	}

	public int getDurationMetal() {
		ElectrolysisMetalRecipe result = ElectrolyserMetalRecipes.getRecipe(slots[14]);
		int base = result != null ? result.duration : 600;
		int speed = upgradeManager.getLevel(UpgradeType.SPEED) - Math.min(upgradeManager.getLevel(UpgradeType.POWER), 1);
		return (int) Math.ceil((base * Math.max(1F - 0.25F * speed, 0.2)));
	}
	public int getDurationFluid() {
		ElectrolysisRecipe result = ElectrolyserFluidRecipes.getRecipe(tanks[0].getTankType());
		int base = result != null ? result.duration : 100;
		int speed = upgradeManager.getLevel(UpgradeType.SPEED) - Math.min(upgradeManager.getLevel(UpgradeType.POWER), 1);
		return (int) Math.ceil((base * Math.max(1F - 0.25F * speed, 0.2)));

	}

	public int getCycleCount() {
		int speed = upgradeManager.getLevel(UpgradeType.OVERDRIVE);
		return Math.min(1 + speed * 2, 7);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.power = nbt.getLong("power");
		this.progressFluid = nbt.getInteger("progressFluid");
		this.progressOre = nbt.getInteger("progressOre");
		this.processFluidTime = nbt.getInteger("processFluidTime");
		this.processOreTime = nbt.getInteger("processOreTime");
		if(nbt.hasKey("leftType")) this.leftStack = new MaterialStack(Mats.matById.get(nbt.getInteger("leftType")), nbt.getInteger("leftAmount"));
		else this.leftStack = null;
		if(nbt.hasKey("rightType")) this.rightStack = new MaterialStack(Mats.matById.get(nbt.getInteger("rightType")), nbt.getInteger("rightAmount"));
		else this.rightStack = null;
		for(int i = 0; i < 4; i++) tanks[i].readFromNBT(nbt, "t" + i);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setLong("power", this.power);
		nbt.setInteger("progressFluid", this.progressFluid);
		nbt.setInteger("progressOre", this.progressOre);
		nbt.setInteger("processFluidTime", getDurationFluid());
		nbt.setInteger("processOreTime", getDurationMetal());
		if(this.leftStack != null) {
			nbt.setInteger("leftType", leftStack.material.id);
			nbt.setInteger("leftAmount", leftStack.amount);
		}
		if(this.rightStack != null) {
			nbt.setInteger("rightType", rightStack.material.id);
			nbt.setInteger("rightAmount", rightStack.amount);
		}
		for(int i = 0; i < 4; i++) tanks[i].writeToNBT(nbt, "t" + i);

	}

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 5,
					yCoord - 0,
					zCoord - 5,
					xCoord + 6,
					yCoord + 4,
					zCoord + 6
					);
		}

		return bb;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return tanks;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {tanks[1], tanks[2]};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tanks[0], tanks[3]};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == 0) return new ContainerElectrolyserFluid(player.inventory, this);
		return new ContainerElectrolyserMetal(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == 0) return new GUIElectrolyserFluid(player.inventory, this);
		return new GUIElectrolyserMetal(player.inventory, this);
	}

	@Override
	public void receiveControl(NBTTagCompound data) { }

	@Override
	public void receiveControl(EntityPlayer player, NBTTagCompound data) {

		if(data.hasKey("sgm")) FMLNetworkHandler.openGui(player, MainRegistry.instance, 1, worldObj, xCoord, yCoord, zCoord);
		if(data.hasKey("sgf")) FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, worldObj, xCoord, yCoord, zCoord);
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return this.isUseableByPlayer(player);
	}

	@Override
	public boolean canProvideInfo(UpgradeType type, int level, boolean extendedInfo) {
		return type == UpgradeType.SPEED || type == UpgradeType.POWER || type == UpgradeType.OVERDRIVE;
	}

	@Override
	public void provideInfo(UpgradeType type, int level, List<String> info, boolean extendedInfo) {
		info.add(IUpgradeInfoProvider.getStandardLabel(ModBlocks.machine_electrolyser));
		if(type == UpgradeType.SPEED) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_DELAY, "-" + (level * 25) + "%"));
			info.add(EnumChatFormatting.RED + I18nUtil.resolveKey(this.KEY_CONSUMPTION, "+" + (level * 100) + "%"));
		}
		if(type == UpgradeType.POWER) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_CONSUMPTION, "-" + (level * 25) + "%"));
			info.add(EnumChatFormatting.RED + I18nUtil.resolveKey(this.KEY_DELAY, "+" + (25) + "%"));
		}
		if(type == UpgradeType.OVERDRIVE) {
			info.add((BobMathUtil.getBlink() ? EnumChatFormatting.RED : EnumChatFormatting.DARK_GRAY) + "YES");
		}
	}

	@Override
	public HashMap<UpgradeType, Integer> getValidUpgrades() {
		HashMap<UpgradeType, Integer> upgrades = new HashMap<>();
		upgrades.put(UpgradeType.SPEED, 3);
		upgrades.put(UpgradeType.POWER, 3);
		upgrades.put(UpgradeType.OVERDRIVE, 3);
		return upgrades;
	}

	@Override
	public FluidTank getTankToPaste() {
		return tanks[0];
	}

	@Override
	public NBTTagCompound getSettings(World world, int x, int y, int z) {
		NBTTagCompound tag = new NBTTagCompound();
		if(getFluidIDToCopy().length > 0)
			tag.setIntArray("fluidID", getFluidIDToCopy());
		if(getMatsToCopy().length > 0)
			tag.setIntArray("matFilter", getMatsToCopy());
		return tag;
	}

	@Override
	public void pasteSettings(NBTTagCompound nbt, int index, World world, EntityPlayer player, int x, int y, int z) {
		IFluidCopiable.super.pasteSettings(nbt, index, world, player, x, y, z);
	}

	@Override
	public String[] infoForDisplay(World world, int x, int y, int z) {
		ArrayList<String> names = new ArrayList<>();
		int[] fluidIDs = getFluidIDToCopy();
		int[] matIDs = getMatsToCopy();

		for (int fluidID : fluidIDs) {
			names.add(Fluids.fromID(fluidID).getUnlocalizedName());
		}
		for (int matID : matIDs) {
			names.add(Mats.matById.get(matID).getUnlocalizedName());
		}

		return names.toArray(new String[0]);
	}

	@Override
	public int[] getMatsToCopy() {
		ArrayList<Integer> types = new ArrayList<>();
		if(leftStack != null)	types.add(leftStack.material.id);
		if(rightStack != null)	types.add(rightStack.material.id);
		return BobMathUtil.intCollectionToArray(types);
	}
}
