package com.hbm.tileentity.machine;

import com.hbm.inventory.FluidStack;
import com.hbm.inventory.container.ContainerRadiolysis;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIRadiolysis;
import com.hbm.inventory.recipes.RadiolysisRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemRTGPellet;
import com.hbm.items.machine.ItemRTGPelletDepleted;
import com.hbm.lib.Library;
import com.hbm.tileentity.IFluidCopiable;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.CompatEnergyControl;
import com.hbm.util.RTGUtil;
import com.hbm.util.Tuple.Pair;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IEnergyProviderMK2;
import api.hbm.fluid.IFluidStandardTransceiver;
import api.hbm.tile.IInfoProviderEC;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineRadiolysis extends TileEntityMachineBase implements IEnergyProviderMK2, IFluidStandardTransceiver, IGUIProvider, IInfoProviderEC, IFluidCopiable {

	public long power;
	public static final int maxPower = 1000000;
	public int heat;

	public FluidTank[] tanks;

	private static final int[] slot_io = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 12, 13 };
	private static final int[] slot_rtg = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

	public TileEntityMachineRadiolysis() {
		super(15); //10 rtg slots, 2 fluid ID slots (io), 2 irradiation slots (io), battery slot
		tanks = new FluidTank[3];
		tanks[0] = new FluidTank(Fluids.NONE, 2_000);
		tanks[1] = new FluidTank(Fluids.NONE, 2_000);
		tanks[2] = new FluidTank(Fluids.NONE, 2_000);
	}

	@Override
	public String getName() {
		return "container.radiolysis";
	}

	/* IO Methods */
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return i == 12 || (i < 10 && itemStack.getItem() instanceof ItemRTGPellet);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return slot_io;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return (i < 10 && itemStack.getItem() instanceof ItemRTGPelletDepleted) || i == 13;
	}

	/* NBT Methods */
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.power = nbt.getLong("power");
		this.heat = nbt.getInteger("heat");

		tanks[0].readFromNBT(nbt, "input");
		tanks[1].readFromNBT(nbt, "output1");
		tanks[2].readFromNBT(nbt, "output2");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setLong("power", power);
		nbt.setInteger("heat", heat);

		tanks[0].writeToNBT(nbt, "input");
		tanks[1].writeToNBT(nbt, "output1");
		tanks[2].writeToNBT(nbt, "output2");
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			power = Library.chargeItemsFromTE(slots, 14, power, maxPower);

			heat = RTGUtil.updateRTGs(slots, slot_rtg);
			power += heat * 10;

			if(power > maxPower)
				power = maxPower;

			tanks[0].setType(10, 11, slots);
			setupTanks();

			if(heat > 100) {
				int crackTime = (int) Math.max(-0.1 * (heat - 100) + 30, 5);

				if(worldObj.getTotalWorldTime() % crackTime == 0)
					crack();

				if(heat >= 200 && worldObj.getTotalWorldTime() % 100 == 0)
					sterilize();
			}

			for(DirPos pos : getConPos()) {
				this.tryProvide(worldObj, pos.getX(), pos.getY(),pos.getZ(), pos.getDir());
				this.trySubscribe(tanks[0].getTankType(), worldObj, pos.getX(), pos.getY(),pos.getZ(), pos.getDir());
				if(tanks[1].getFill() > 0) this.sendFluid(tanks[1], worldObj, pos.getX(), pos.getY(),pos.getZ(), pos.getDir());
				if(tanks[2].getFill() > 0) this.sendFluid(tanks[2], worldObj, pos.getX(), pos.getY(),pos.getZ(), pos.getDir());
			}

			this.networkPackNT(50);
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeLong(this.power);
		buf.writeInt(this.heat);
		tanks[0].serialize(buf);
		tanks[1].serialize(buf);
		tanks[2].serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.power = buf.readLong();
		this.heat = buf.readInt();
		tanks[0].deserialize(buf);
		tanks[1].deserialize(buf);
		tanks[2].deserialize(buf);
	}

	protected DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(xCoord + 2, yCoord, zCoord, Library.POS_X),
				new DirPos(xCoord - 2, yCoord, zCoord, Library.NEG_X),
				new DirPos(xCoord, yCoord, zCoord + 2, Library.POS_Z),
				new DirPos(xCoord, yCoord, zCoord - 2, Library.NEG_Z)
		};
	}

	/* Processing Methods */
	private void crack() {

		Pair<FluidStack, FluidStack> quart = RadiolysisRecipes.getRadiolysis(tanks[0].getTankType());

		if(quart != null) {

			int left = quart.getKey().fill;
			int right = quart.getValue().fill;

			if(tanks[0].getFill() >= 100 && hasSpace(left, right)) {
				tanks[0].setFill(tanks[0].getFill() - 100);
				tanks[1].setFill(tanks[1].getFill() + left);
				tanks[2].setFill(tanks[2].getFill() + right);
			}
		}
	}

	private boolean hasSpace(int left, int right) {
		return tanks[1].getFill() + left <= tanks[1].getMaxFill() && tanks[2].getFill() + right <= tanks[2].getMaxFill();
	}

	private void setupTanks() {

		Pair<FluidStack, FluidStack> quart = RadiolysisRecipes.getRadiolysis(tanks[0].getTankType());

		if(quart != null) {
			tanks[1].setTankType(quart.getKey().type);
			tanks[2].setTankType(quart.getValue().type);
		} else {
			tanks[0].setTankType(Fluids.NONE);
			tanks[1].setTankType(Fluids.NONE);
			tanks[2].setTankType(Fluids.NONE);
		}

	}

	// Code: pressure, sword, sterilize.
	private void sterilize() {
		if(slots[12] != null) {
			if(slots[12].getItem() instanceof ItemFood && !(slots[12].getItem() == ModItems.pancake)) {
				this.decrStackSize(12, 1);
			}

			if(!checkIfValid()) return;

			ItemStack output = slots[12].copy();
			output.stackSize = 1;

			if(slots[13] == null) {
				this.decrStackSize(12, output.stackSize);
				slots[13] = output;
				slots[13].stackTagCompound.removeTag("ntmContagion");
				if(slots[13].stackTagCompound.hasNoTags()) {
					slots[13].stackTagCompound = null;
				}
			} else if(slots[13].isItemEqual(output) && slots[13].stackSize + output.stackSize <= slots[13].getMaxStackSize()) {
				this.decrStackSize(12, output.stackSize);
				slots[13].stackSize += output.stackSize;
				if(slots[13].hasTagCompound()) { // redundant but just to be sure
					slots[13].stackTagCompound.removeTag("ntmContagion");
					if(slots[13].stackTagCompound.hasNoTags()) {
						slots[13].stackTagCompound = null;
					}
				}
			}
		}
	}

	private boolean checkIfValid() {
		if(slots[12] == null) return false;
		if(!slots[12].hasTagCompound()) return false;
		if(!slots[12].getTagCompound().getBoolean("ntmContagion")) return false;
		return true;
	}

	/* Power methods */
	@Override
	public void setPower(long power) {
		this.power = power;
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
	public FluidTank[] getAllTanks() {
		return tanks;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {tanks[1], tanks[2]};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tanks[0]};
	}

	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		return dir != ForgeDirection.UNKNOWN && dir != ForgeDirection.DOWN;
	}

	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord, zCoord - 1, xCoord + 2, yCoord + 3, zCoord + 2);
	}

	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerRadiolysis(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIRadiolysis(player.inventory, this);
	}

	@Override
	public void provideExtraInfo(NBTTagCompound data) {
		data.setDouble(CompatEnergyControl.D_OUTPUT_HE, this.heat * 10);
	}
}
