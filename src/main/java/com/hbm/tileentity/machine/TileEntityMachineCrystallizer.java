package com.hbm.tileentity.machine;

import java.util.HashMap;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.extprop.HbmPlayerProps;
import com.hbm.inventory.UpgradeManagerNT;
import com.hbm.inventory.container.ContainerCrystallizer;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUICrystallizer;
import com.hbm.inventory.recipes.CrystallizerRecipes;
import com.hbm.inventory.recipes.CrystallizerRecipes.CrystallizerRecipe;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.*;
import com.hbm.util.BobMathUtil;
import com.hbm.util.I18nUtil;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IBatteryItem;
import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.fluid.IFluidStandardReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineCrystallizer extends TileEntityMachineBase implements IEnergyReceiverMK2, IFluidStandardReceiver, IGUIProvider, IUpgradeInfoProvider, IFluidCopiable {

	public long power;
	public static final long maxPower = 1000000;
	public static final int demand = 1000;
	public short progress;
	public short duration = 600;
	public boolean isOn;

	public float angle;
	public float prevAngle;

	public FluidTank tank;

	public UpgradeManagerNT upgradeManager = new UpgradeManagerNT();

	public TileEntityMachineCrystallizer() {
		super(8);
		tank = new FluidTank(Fluids.PEROXIDE, 8000);
	}

	@Override
	public String getName() {
		return "container.crystallizer";
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			this.isOn = false;

			this.updateConnections();

			power = Library.chargeTEFromItems(slots, 1, power, maxPower);
			tank.setType(7, slots);
			tank.loadTank(3, 4, slots);

			upgradeManager.checkSlots(this, slots, 5, 6);

			for(int i = 0; i < getCycleCount(); i++) {

				if(canProcess()) {

					progress++;
					power -= getPowerRequired();
					isOn = true;

					if(progress > getDuration()) {
						progress = 0;
						processItem();

						this.markDirty();
					}

				} else {
					progress = 0;
				}
			}

			this.networkPackNT(25);
		} else {

			prevAngle = angle;

			if(isOn) {
				angle += 5F * this.getCycleCount();

				if(angle >= 360) {
					angle -= 360;
					prevAngle -= 360;
				}

				if(worldObj.rand.nextInt(20) == 0 && MainRegistry.proxy.me().getDistance(xCoord + 0.5, yCoord + 6, zCoord + 0.5) < 50) {
					worldObj.spawnParticle("cloud", xCoord + worldObj.rand.nextDouble(), yCoord + 6.5D, zCoord + worldObj.rand.nextDouble(), 0.0, 0.1, 0.0);
				}
			}
		}

		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord + 0.25, yCoord + 1, zCoord + 0.25, xCoord + 0.75, yCoord + 6, zCoord + 0.75).offset(rot.offsetX * 1.5, 0, rot.offsetZ * 1.5));

		for(EntityPlayer player : players) {
			HbmPlayerProps props = HbmPlayerProps.getData(player);
			props.isOnLadder = true;
		}
	}

	private void updateConnections() {

		for(DirPos pos : getConPos()) {
			this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			this.trySubscribe(tank.getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}

	protected DirPos[] getConPos() {

		return new DirPos[] {
				new DirPos(xCoord + 2, yCoord, zCoord + 1, Library.POS_X),
				new DirPos(xCoord + 2, yCoord, zCoord - 1, Library.POS_X),
				new DirPos(xCoord - 2, yCoord, zCoord + 1, Library.NEG_X),
				new DirPos(xCoord - 2, yCoord, zCoord - 1, Library.NEG_X),
				new DirPos(xCoord + 1, yCoord, zCoord + 2, Library.POS_Z),
				new DirPos(xCoord - 1, yCoord, zCoord + 2, Library.POS_Z),
				new DirPos(xCoord + 1, yCoord, zCoord - 2, Library.NEG_Z),
				new DirPos(xCoord - 1, yCoord, zCoord - 2, Library.NEG_Z)
		};
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeShort(progress);
		buf.writeShort(getDuration());
		buf.writeLong(power);
		buf.writeBoolean(isOn);
		tank.serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		progress = buf.readShort();
		duration = buf.readShort();
		power = buf.readLong();
		isOn = buf.readBoolean();
		tank.deserialize(buf);
	}

	private void processItem() {

		CrystallizerRecipe result = CrystallizerRecipes.getOutput(slots[0], tank.getTankType());

		if(result == null) //never happens but you can't be sure enough
			return;

		ItemStack stack = result.output.copy();

		if(slots[2] == null)
			slots[2] = stack;
		else if(slots[2].stackSize + stack.stackSize <= slots[2].getMaxStackSize())
			slots[2].stackSize += stack.stackSize;

		tank.setFill(tank.getFill() - getRequiredAcid(result.acidAmount));

		float freeChance = this.getFreeChance();

		if(freeChance == 0 || freeChance < worldObj.rand.nextFloat())
			this.decrStackSize(0, result.itemAmount);
	}

	private boolean canProcess() {

		//Is there no input?
		if(slots[0] == null)
			return false;

		if(power < getPowerRequired())
			return false;

		CrystallizerRecipe result = CrystallizerRecipes.getOutput(slots[0], tank.getTankType());

		//Or output?
		if(result == null)
			return false;

		//Not enough of the input item?
		if(slots[0].stackSize < result.itemAmount)
			return false;

		if(tank.getFill() < getRequiredAcid(result.acidAmount)) return false;

		ItemStack stack = result.output.copy();

		//Does the output not match?
		if(slots[2] != null && (slots[2].getItem() != stack.getItem() || slots[2].getItemDamage() != stack.getItemDamage()))
			return false;

		//Or is the output slot already full?
		if(slots[2] != null && slots[2].stackSize + stack.stackSize > slots[2].getMaxStackSize())
			return false;

		return true;
	}

	public int getRequiredAcid(int base) {
		int efficiency = upgradeManager.getLevel(UpgradeType.EFFECT);
		if(efficiency > 0) {
			return base * (efficiency + 2);
		}
		return base;
	}

	public float getFreeChance() {
		int efficiency = upgradeManager.getLevel(UpgradeType.EFFECT);
		if(efficiency > 0) {
			return Math.min(efficiency * 0.05F, 0.15F);
		}
		return 0;
	}

	public short getDuration() {
		CrystallizerRecipe result = CrystallizerRecipes.getOutput(slots[0], tank.getTankType());
		int base = result != null ? result.duration : 600;
		int speed = upgradeManager.getLevel(UpgradeType.SPEED);
		if(speed > 0) {
			return (short) Math.ceil((base * Math.max(1F - 0.25F * speed, 0.25F)));
		}
		return (short) base;
	}

	public int getPowerRequired() {
		int speed = upgradeManager.getLevel(UpgradeType.SPEED);
		return (int) (demand + Math.min(speed * 1000, 3000));
	}

	public float getCycleCount() {
		int speed = upgradeManager.getLevel(UpgradeType.OVERDRIVE);
		return Math.min(1 + speed * 2, 7);
	}

	public long getPowerScaled(int i) {
		return (power * i) / maxPower;
	}

	public int getProgressScaled(int i) {
		return (progress * i) / duration;
	}

	@Override
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		power = nbt.getLong("power");
		tank.readFromNBT(nbt, "tank");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setLong("power", power);
		tank.writeToNBT(nbt, "tank");
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {

		CrystallizerRecipe recipe = CrystallizerRecipes.getOutput(itemStack, tank.getTankType());
		if(i == 0 && recipe != null) {
			return true;
		}

		if(i == 1 && itemStack.getItem() instanceof IBatteryItem)
			return true;

		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i == 2;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {

		return side == 0 ? new int[] { 2 } : new int[] { 0, 2 };
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack) {
		super.setInventorySlotContents(i, stack);

		if(stack != null && i >= 5 && i <= 6 && stack.getItem() instanceof ItemMachineUpgrade) {
			worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "hbm:item.upgradePlug", 1.0F, 1.0F);
		}
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tank};
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { tank };
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerCrystallizer(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUICrystallizer(player.inventory, this);
	}

	@Override
	public boolean canProvideInfo(UpgradeType type, int level, boolean extendedInfo) {
		return type == UpgradeType.SPEED || type == UpgradeType.EFFECT || type == UpgradeType.OVERDRIVE;
	}

	@Override
	public void provideInfo(UpgradeType type, int level, List<String> info, boolean extendedInfo) {
		info.add(IUpgradeInfoProvider.getStandardLabel(ModBlocks.machine_crystallizer));
		if(type == UpgradeType.SPEED) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_DELAY, "-" + (level * 25) + "%"));
			info.add(EnumChatFormatting.RED + I18nUtil.resolveKey(this.KEY_CONSUMPTION, "+" + (level * 100) + "%"));
		}
		if(type == UpgradeType.EFFECT) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_EFFICIENCY, "+" + (level * 5) + "%"));
			info.add(EnumChatFormatting.RED + I18nUtil.resolveKey(this.KEY_ACID, "+" + (level * 100 + 100) + "%"));
		}
		if(type == UpgradeType.OVERDRIVE) {
			info.add((BobMathUtil.getBlink() ? EnumChatFormatting.RED : EnumChatFormatting.DARK_GRAY) + "YES");
		}
	}

	@Override
	public HashMap<UpgradeType, Integer> getValidUpgrades() {
		HashMap<UpgradeType, Integer> upgrades = new HashMap<>();
		upgrades.put(UpgradeType.SPEED, 3);
		upgrades.put(UpgradeType.EFFECT, 3);
		upgrades.put(UpgradeType.OVERDRIVE, 3);
		return upgrades;
	}

	@Override
	public int[] getFluidIDToCopy() {
		return new int[]{ tank.getTankType().getID()};
	}

	@Override
	public FluidTank getTankToPaste() {
		return tank;
	}
}
