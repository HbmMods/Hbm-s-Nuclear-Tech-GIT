package com.hbm.tileentity.machine;

import java.util.HashMap;
import java.util.List;

import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.UpgradeManagerNT;
import com.hbm.inventory.container.ContainerMachineArcWelder;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachineArcWelder;
import com.hbm.inventory.recipes.ArcWelderRecipes;
import com.hbm.inventory.recipes.ArcWelderRecipes.ArcWelderRecipe;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.Library;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.*;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.util.I18nUtil;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.fluid.IFluidStandardReceiver;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineArcWelder extends TileEntityMachineBase implements IEnergyReceiverMK2, IFluidStandardReceiver, IConditionalInvAccess, IGUIProvider, IUpgradeInfoProvider, IFluidCopiable {

	public long power;
	public long maxPower = 2_000;
	public long consumption;

	public int progress;
	public int processTime = 1;

	public FluidTank tank;
	public ItemStack display;

	public UpgradeManagerNT upgradeManager = new UpgradeManagerNT();

	public TileEntityMachineArcWelder() {
		super(8);
		this.tank = new FluidTank(Fluids.NONE, 24_000);
	}

	@Override
	public String getName() {
		return "container.machineArcWelder";
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack) {
		super.setInventorySlotContents(i, stack);

		if(stack != null && stack.getItem() instanceof ItemMachineUpgrade && i >= 6 && i <= 7) {
			worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "hbm:item.upgradePlug", 1.0F, 1.0F);
		}
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			this.power = Library.chargeTEFromItems(slots, 4, this.getPower(), this.getMaxPower());
			this.tank.setType(5, slots);

			if(worldObj.getTotalWorldTime() % 20 == 0) {
				for(DirPos pos : getConPos()) {
					this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
					if(tank.getTankType() != Fluids.NONE) this.trySubscribe(tank.getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				}
			}

			ArcWelderRecipe recipe = ArcWelderRecipes.getRecipe(slots[0], slots[1], slots[2]);
			long intendedMaxPower;

			upgradeManager.checkSlots(this, slots, 6, 7);
			int redLevel = upgradeManager.getLevel(UpgradeType.SPEED);
			int blueLevel = upgradeManager.getLevel(UpgradeType.POWER);

			if(recipe != null) {
				this.processTime = recipe.duration - (recipe.duration * redLevel / 6) + (recipe.duration * blueLevel / 3);
				this.consumption = recipe.consumption + (recipe.consumption * redLevel) - (recipe.consumption * blueLevel / 6);
				intendedMaxPower = recipe.consumption * 20;

				if(canProcess(recipe)) {
					this.progress++;
					this.power -= this.consumption;

					if(progress >= processTime) {
						this.progress = 0;
						this.consumeItems(recipe);

						if(slots[3] == null) {
							slots[3] = recipe.output.copy();
						} else {
							slots[3].stackSize += recipe.output.stackSize;
						}

						this.markDirty();
					}

					if(worldObj.getTotalWorldTime() % 2 == 0) {
						ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
						NBTTagCompound dPart = new NBTTagCompound();
						dPart.setString("type", worldObj.getTotalWorldTime() % 20 == 0 ? "tau" : "hadron");
						dPart.setByte("count", (byte) 5);
						PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(dPart, xCoord + 0.5 - dir.offsetX * 0.5, yCoord + 1.25, zCoord + 0.5 - dir.offsetZ * 0.5), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 25));
					}

				} else {
					this.progress = 0;
				}

			} else {
				this.progress = 0;
				this.consumption = 100;
				intendedMaxPower = 2000;
			}

			this.maxPower = Math.max(intendedMaxPower, power);

			this.networkPackNT(25);
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeLong(power);
		buf.writeLong(maxPower);
		buf.writeLong(consumption);
		buf.writeInt(progress);
		buf.writeInt(processTime);

		tank.serialize(buf);

		ArcWelderRecipe recipe = ArcWelderRecipes.getRecipe(slots[0], slots[1], slots[2]);

		if(recipe != null) {
			buf.writeBoolean(true);
			buf.writeInt(Item.getIdFromItem(recipe.output.getItem()));
			buf.writeInt(recipe.output.getItemDamage());
		} else
			buf.writeBoolean(false);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		power = buf.readLong();
		maxPower = buf.readLong();
		consumption = buf.readLong();
		progress = buf.readInt();
		processTime = buf.readInt();

		tank.deserialize(buf);

		if(buf.readBoolean()) {
			this.display = new ItemStack(Item.getItemById(buf.readInt()), 1, buf.readInt());
		} else
			this.display = null;
	}

	public boolean canProcess(ArcWelderRecipe recipe) {

		if(this.power < this.consumption) return false;

		if(recipe.fluid != null) {
			if(this.tank.getTankType() != recipe.fluid.type) return false;
			if(this.tank.getFill() < recipe.fluid.fill) return false;
		}

		if(slots[3] != null) {
			if(slots[3].getItem() != recipe.output.getItem()) return false;
			if(slots[3].getItemDamage() != recipe.output.getItemDamage()) return false;
			if(slots[3].stackSize + recipe.output.stackSize > slots[3].getMaxStackSize()) return false;
		}

		return true;
	}

	public void consumeItems(ArcWelderRecipe recipe) {

		for(AStack aStack : recipe.ingredients) {

			for(int i = 0; i < 3; i++) {
				ItemStack stack = slots[i];
				if(aStack.matchesRecipe(stack, true) && stack.stackSize >= aStack.stacksize) {
					this.decrStackSize(i, aStack.stacksize);
					break;
				}
			}
		}

		if(recipe.fluid != null) {
			this.tank.setFill(tank.getFill() - recipe.fluid.fill);
		}
	}

	protected DirPos[] getConPos() {

		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		return new DirPos[] {
				new DirPos(xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ, dir),
				new DirPos(xCoord + dir.offsetX + rot.offsetX, yCoord, zCoord + dir.offsetZ + rot.offsetZ, dir),
				new DirPos(xCoord + dir.offsetX - rot.offsetX, yCoord, zCoord + dir.offsetZ - rot.offsetZ, dir),
				new DirPos(xCoord - dir.offsetX * 2, yCoord, zCoord - dir.offsetZ * 2, dir.getOpposite()),
				new DirPos(xCoord - dir.offsetX * 2 + rot.offsetX, yCoord, zCoord - dir.offsetZ * 2 + rot.offsetZ, dir.getOpposite()),
				new DirPos(xCoord - dir.offsetX * 2 - rot.offsetX, yCoord, zCoord - dir.offsetZ * 2 - rot.offsetZ, dir.getOpposite()),
				new DirPos(xCoord + rot.offsetX * 2, yCoord, zCoord + rot.offsetZ * 2, rot),
				new DirPos(xCoord - dir.offsetX + rot.offsetX * 2, yCoord, zCoord - dir.offsetZ + rot.offsetZ * 2, rot),
				new DirPos(xCoord - rot.offsetX * 2, yCoord, zCoord - rot.offsetZ * 2, rot.getOpposite()),
				new DirPos(xCoord - dir.offsetX - rot.offsetX * 2, yCoord, zCoord - dir.offsetZ - rot.offsetZ * 2, rot.getOpposite())
		};
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.power = nbt.getLong("power");
		this.maxPower = nbt.getLong("maxPower");
		this.progress = nbt.getInteger("progress");
		this.processTime = nbt.getInteger("processTime");
		tank.readFromNBT(nbt, "t");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setLong("power", power);
		nbt.setLong("maxPower", maxPower);
		nbt.setInteger("progress", progress);
		nbt.setInteger("processTime", processTime);
		tank.writeToNBT(nbt, "t");
	}

	@Override
	public long getPower() {
		return Math.max(Math.min(power, maxPower), 0);
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {tank};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tank};
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return slot < 3;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack  stack, int side) {
		return slot == 3;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 1, 3 };
	}

	@Override
	public boolean isItemValidForSlot(int x, int y, int z, int slot, ItemStack stack) {
		return slot < 3;
	}

	@Override
	public boolean canInsertItem(int x, int y, int z, int slot, ItemStack stack, int side) {
		return slot < 3;
	}

	@Override
	public boolean canExtractItem(int x, int y, int z, int slot, ItemStack stack, int side) {
		return slot == 3;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int x, int y, int z, int side) {
		BlockPos pos = new BlockPos(x, y, z);
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		BlockPos core = new BlockPos(xCoord, yCoord, zCoord);

		//Red
		if(pos.equals(core.clone().offset(rot)) || pos.equals(core.clone().offset(rot.getOpposite()).offset(dir.getOpposite())))
			return new int[] { 0, 3 };

		//Yellow
		if(pos.equals(core.clone().offset(dir.getOpposite())))
			return new int[] { 1, 3 };

		//Green
		if(pos.equals(core.clone().offset(rot.getOpposite())) || pos.equals(core.clone().offset(rot).offset(dir.getOpposite())))
			return new int[] { 2, 3 };

		return new int[] { };
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineArcWelder(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineArcWelder(player.inventory, this);
	}

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 1,
					yCoord,
					zCoord - 1,
					xCoord + 2,
					yCoord + 3,
					zCoord + 2
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
	public boolean canProvideInfo(UpgradeType type, int level, boolean extendedInfo) {
		return type == UpgradeType.SPEED || type == UpgradeType.POWER;
	}

	@Override
	public void provideInfo(UpgradeType type, int level, List<String> info, boolean extendedInfo) {
		info.add(IUpgradeInfoProvider.getStandardLabel(ModBlocks.machine_arc_welder));
		if(type == UpgradeType.SPEED) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_DELAY, "-" + (level * 100 / 6) + "%"));
			info.add(EnumChatFormatting.RED + I18nUtil.resolveKey(this.KEY_CONSUMPTION, "+" + (level * 100) + "%"));
		}
		if(type == UpgradeType.POWER) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_CONSUMPTION, "-" + (level * 100 / 6) + "%"));
			info.add(EnumChatFormatting.RED + I18nUtil.resolveKey(this.KEY_DELAY, "+" + (level * 100 / 3) + "%"));
		}
	}

	@Override
	public HashMap<UpgradeType, Integer> getValidUpgrades() {
		HashMap<UpgradeType, Integer> upgrades = new HashMap<>();
		upgrades.put(UpgradeType.SPEED, 3);
		upgrades.put(UpgradeType.POWER, 3);
		return upgrades;
	}

	@Override
	public FluidTank getTankToPaste() {
		return tank;
	}
}
