package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.UpgradeManager;
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
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.IUpgradeInfoProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.CrucibleUtil;
import com.hbm.util.I18nUtil;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityElectrolyser extends TileEntityMachineBase implements IEnergyReceiverMK2, IFluidStandardTransceiver, IControlReceiver, IGUIProvider, IUpgradeInfoProvider {
	
	public long power;
	public static final long maxPower = 20000000;
	public static final int usageOreBase = 10_000;
	public static final int usageFluidBase = 10_000;
	public int usageOre;
	public int usageFluid;
	
	public int progressFluid;
	public static final int processFluidTimeBase = 20;
	public int processFluidTime;
	public int progressOre;
	public static final int processOreTimeBase = 600;
	public int processOreTime;

	public MaterialStack leftStack;
	public MaterialStack rightStack;
	public int maxMaterial = MaterialShapes.BLOCK.q(16);
	
	public FluidTank[] tanks;

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
			
			UpgradeManager.eval(slots, 1, 2);
			int speedLevel = Math.min(UpgradeManager.getLevel(UpgradeType.SPEED), 3);
			int powerLevel = Math.min(UpgradeManager.getLevel(UpgradeType.POWER), 3);

			processFluidTime = processFluidTimeBase - processFluidTimeBase * speedLevel / 4;
			processOreTime = processOreTimeBase - processOreTimeBase * speedLevel / 4;
			usageOre = usageOreBase - usageOreBase * powerLevel / 4;
			usageFluid = usageFluidBase - usageFluidBase * powerLevel / 4;
			
			if(this.canProcessFluid()) {
				this.progressFluid++;
				this.power -= this.usageFluid;
				
				if(this.progressFluid >= this.processFluidTime) {
					this.processFluids();
					this.progressFluid = 0;
					this.markChanged();
				}
			}
			
			if(this.canProcesMetal()) {
				this.progressOre++;
				this.power -= this.usageOre;
				
				if(this.progressOre >= this.processOreTime) {
					this.processMetal();
					this.progressOre = 0;
					this.markChanged();
				}
			}
			
			if(this.leftStack != null) {
				
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset).getOpposite();
				List<MaterialStack> toCast = new ArrayList();
				toCast.add(this.leftStack);
				
				Vec3 impact = Vec3.createVectorHelper(0, 0, 0);
				MaterialStack didPour = CrucibleUtil.pourFullStack(worldObj, xCoord + 0.5D + dir.offsetX * 5.875D, yCoord + 2D, zCoord + 0.5D + dir.offsetZ * 5.875D, 6, true, toCast, MaterialShapes.NUGGET.q(3), impact);

				if(didPour != null) {
					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "foundry");
					data.setInteger("color", didPour.material.moltenColor);
					data.setByte("dir", (byte) dir.ordinal());
					data.setFloat("off", 0.625F);
					data.setFloat("base", 0.625F);
					data.setFloat("len", Math.max(1F, yCoord - (float) (Math.ceil(impact.yCoord) - 0.875) + 2));
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, xCoord + 0.5D + dir.offsetX * 5.875D, yCoord + 2, zCoord + 0.5D + dir.offsetZ * 5.875D), new TargetPoint(worldObj.provider.dimensionId, xCoord + 0.5, yCoord + 1, zCoord + 0.5, 50));
					
					if(this.leftStack.amount <= 0) this.leftStack = null;
				}
			}
			
			if(this.rightStack != null) {
				
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
				List<MaterialStack> toCast = new ArrayList();
				toCast.add(this.rightStack);
				
				Vec3 impact = Vec3.createVectorHelper(0, 0, 0);
				MaterialStack didPour = CrucibleUtil.pourFullStack(worldObj, xCoord + 0.5D + dir.offsetX * 5.875D, yCoord + 2D, zCoord + 0.5D + dir.offsetZ * 5.875D, 6, true, toCast, MaterialShapes.NUGGET.q(3), impact);

				if(didPour != null) {
					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "foundry");
					data.setInteger("color", didPour.material.moltenColor);
					data.setByte("dir", (byte) dir.ordinal());
					data.setFloat("off", 0.625F);
					data.setFloat("base", 0.625F);
					data.setFloat("len", Math.max(1F, yCoord - (float) (Math.ceil(impact.yCoord) - 0.875) + 2));
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, xCoord + 0.5D + dir.offsetX * 5.875D, yCoord + 2, zCoord + 0.5D + dir.offsetZ * 5.875D), new TargetPoint(worldObj.provider.dimensionId, xCoord + 0.5, yCoord + 1, zCoord + 0.5, 50));
					
					if(this.rightStack.amount <= 0) this.rightStack = null;
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			data.setInteger("progressFluid", this.progressFluid);
			data.setInteger("progressOre", this.progressOre);
			data.setInteger("usageOre", this.usageOre);
			data.setInteger("usageFluid", this.usageFluid);
			data.setInteger("processFluidTime", this.processFluidTime);
			data.setInteger("processOreTime", this.processOreTime);
			if(this.leftStack != null) {
				data.setInteger("leftType", leftStack.material.id);
				data.setInteger("leftAmount", leftStack.amount);
			}
			if(this.rightStack != null) {
				data.setInteger("rightType", rightStack.material.id);
				data.setInteger("rightAmount", rightStack.amount);
			}
			for(int i = 0; i < 4; i++) tanks[i].writeToNBT(data, "t" + i);
			this.networkPack(data, 50);
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
	public void networkUnpack(NBTTagCompound nbt) {
		super.networkUnpack(nbt);
		
		this.power = nbt.getLong("power");
		this.progressFluid = nbt.getInteger("progressFluid");
		this.progressOre = nbt.getInteger("progressOre");
		this.usageOre = nbt.getInteger("usageOre");
		this.usageFluid = nbt.getInteger("usageFluid");
		this.processFluidTime = nbt.getInteger("processFluidTime");
		this.processOreTime = nbt.getInteger("processOreTime");
		if(nbt.hasKey("leftType")) this.leftStack = new MaterialStack(Mats.matById.get(nbt.getInteger("leftType")), nbt.getInteger("leftAmount"));
		else this.leftStack = null;
		if(nbt.hasKey("rightType")) this.rightStack = new MaterialStack(Mats.matById.get(nbt.getInteger("rightType")), nbt.getInteger("rightAmount"));
		else this.rightStack = null;
		for(int i = 0; i < 4; i++) tanks[i].readFromNBT(nbt, "t" + i);
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
	
	public boolean canProcesMetal() {
		
		if(slots[14] == null) return false;
		if(this.power < usageOre) return false;
		if(this.tanks[3].getFill() < 100) return false;
		
		ElectrolysisMetalRecipe recipe = ElectrolyserMetalRecipes.getRecipe(slots[14]);
		if(recipe == null) return false;
		
		if(leftStack != null) {
			if(recipe.output1.material != leftStack.material) return false;
			if(recipe.output1.amount + leftStack.amount > this.maxMaterial) return false;
		}
		
		if(rightStack != null) {
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
		
		if(leftStack == null) {
			leftStack = new MaterialStack(recipe.output1.material, recipe.output1.amount);
		} else {
			leftStack.amount += recipe.output1.amount;
		}
		
		if(rightStack == null) {
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
		nbt.setInteger("processFluidTime", this.processFluidTime);
		nbt.setInteger("processOreTime", this.processOreTime);
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
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
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
		return type == UpgradeType.SPEED || type == UpgradeType.POWER;
	}

	@Override
	public void provideInfo(UpgradeType type, int level, List<String> info, boolean extendedInfo) {
		info.add(IUpgradeInfoProvider.getStandardLabel(ModBlocks.machine_electrolyser));
		if(type == UpgradeType.SPEED) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_DELAY, "-" + (level * 25) + "%"));
		}
		if(type == UpgradeType.POWER) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_CONSUMPTION, "-" + (level * 25) + "%"));
		}
	}

	@Override
	public int getMaxLevel(UpgradeType type) {
		if(type == UpgradeType.SPEED) return 3;
		if(type == UpgradeType.POWER) return 3;
		return 0;
	}
}
