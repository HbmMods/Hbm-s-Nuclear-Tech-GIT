package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.FluidTank;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.RadiolysisRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemRTGPellet;
import com.hbm.items.machine.ItemRTGPelletDepleted;
import com.hbm.lib.Library;
import com.hbm.tileentity.IRTGUser;
import com.hbm.tileentity.IRadioisotopeFuel;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.Tuple.Pair;

import api.hbm.Date;
import api.hbm.energy.IEnergyGenerator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineRadiolysis extends TileEntityMachineBase implements IEnergyGenerator, IFluidAcceptor, IFluidSource, IRTGUser {
	
	public long power;
	public static final int maxPower = 1000000;
	public int heat;

	public FluidTank[] tanks;
	public List<IFluidAcceptor> list1 = new ArrayList<>();
	public List<IFluidAcceptor> list2 = new ArrayList<>();
	
	private static final int[] slot_io = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 12, 13 };
	private static final int[] slot_rtg = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	private Date date = new Date();
	public TileEntityMachineRadiolysis() {
		super(15); //10 rtg slots, 2 fluid ID slots (io), 2 irradiation slots (io), battery slot
		tanks = new FluidTank[3];
		tanks[0] = new FluidTank(Fluids.NONE, 2000, 0);
		tanks[1] = new FluidTank(Fluids.NONE, 2000, 1);
		tanks[2] = new FluidTank(Fluids.NONE, 2000, 2);
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
		date = new Date(nbt.getByteArray("date"));
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", power);
		nbt.setInteger("heat", heat);
		
		tanks[0].writeToNBT(nbt, "input");
		tanks[1].writeToNBT(nbt, "output1");
		tanks[2].writeToNBT(nbt, "output2");
		nbt.setByteArray("date", date.serialize());
	}
	
	@Override
	public void networkUnpack(NBTTagCompound data) {
		this.power = data.getLong("power");
		this.heat = data.getInteger("heat");
		date = new Date(data.getByteArray("date"));
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			power = Library.chargeItemsFromTE(slots, 14, power, maxPower);
			
			heat = getHeat();
			power += heat * 10;
			
			if(power > maxPower)
				power = maxPower;
			
			
			ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
			this.sendPower(worldObj, xCoord + 2, yCoord, zCoord, dir);
			this.sendPower(worldObj, xCoord - 2, yCoord, zCoord, dir);
			this.sendPower(worldObj, xCoord, yCoord, zCoord + 2, dir);
			this.sendPower(worldObj, xCoord, yCoord, zCoord - 2, dir);
			
			tanks[0].setType(10, 11, slots);
			setupTanks();
			
			if(heat > 100) {
				int crackTime = (int) Math.max(-0.1 * (heat - 100) + 30, 5);
				
				if(worldObj.getTotalWorldTime() % crackTime == 0)
					crack();
				
				if(heat >= 200 && worldObj.getTotalWorldTime() % 100 == 0)
					sterilize();
			}
			
			if(worldObj.getTotalWorldTime() % 10 == 0) {
					fillFluidInit(tanks[1].getTankType());
					fillFluidInit(tanks[2].getTankType());
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setInteger("heat", heat);
			data.setByteArray("date", date.serialize());
			this.networkPack(data, 50);
			
			for(byte i = 0; i < 3; i++)
				tanks[i].updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
		}
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
				slots[12].stackSize -= 1;
				if(slots[12].stackSize <= 0)
					slots[12] = null;
			}
			
			if(!checkIfValid())
				return;
			
			ItemStack output = slots[12].copy();
			output.stackSize = 1;
			
			if(slots[13] == null) {
				slots[12].stackSize -= output.stackSize;
				if(slots[12].stackSize <= 0)
					slots[12] = null;
				slots[13] = output;
				slots[13].stackTagCompound.setBoolean("ntmContagion", false);
			} else if(slots[13].isItemEqual(output) && slots[13].stackSize + output.stackSize <= slots[13].getMaxStackSize()) {
				slots[12].stackSize -= output.stackSize;
				if(slots[12].stackSize <= 0)
					slots[12] = null;
			
				slots[13].stackSize += output.stackSize;
				slots[13].stackTagCompound.setBoolean("ntmContagion", false);
			}
		}
	}
	
	private boolean checkIfValid() {
		if(slots[12] == null)
			return false;
		
		if(!slots[12].hasTagCompound())
			return false;
		
		if(!slots[12].getTagCompound().getBoolean("ntmContagion"))
			return false;
		
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
	
	/* Fluid Methods */
	@Override
	public void setFillForSync(int fill, int index) {
		if(index < 3 && tanks[index] != null)
			tanks[index].setFill(fill);
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		for(FluidTank tank : tanks) {
			if(tank.getTankType() == type) {
				tank.setFill(fill);
			}
		}
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		this.tanks[index].setTankType(type);
	}

	@Override
	public int getFluidFill(FluidType type) {
		for(FluidTank tank : tanks) {
			if(tank.getTankType() == type) {
				return tank.getFill();
			}
		}
		return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if(tanks[0].getTankType() == type) {
			return tanks[0].getMaxFill();
		}
		return 0;
	}

	@Override
	public void fillFluidInit(FluidType type) {
		fillFluid(this.xCoord + 2, this.yCoord, this.zCoord, this.getTact(), type);
		fillFluid(this.xCoord - 2, this.yCoord, this.zCoord, this.getTact(), type);
		fillFluid(this.xCoord, this.yCoord, this.zCoord + 2, this.getTact(), type);
		fillFluid(this.xCoord, this.yCoord, this.zCoord - 2, this.getTact(), type);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}

	@Override
	public boolean getTact() {
		return worldObj.getTotalWorldTime() % 20 < 10;
	}

	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		if(type == tanks[1].getTankType())
			return list1;
		if(type == tanks[2].getTankType())
			return list2;
		return new ArrayList<IFluidAcceptor>();
	}

	@Override
	public void clearFluidList(FluidType type) {
		if(type == tanks[1].getTankType())
			list1.clear();
		if(type == tanks[2].getTankType())
			list2.clear();
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord, zCoord - 1, xCoord + 2, yCoord + 3, zCoord + 2);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public void incrementAge()
	{
		date.increment();
	}

	@Override
	public Date getInternalDate()
	{
		return date;
	}

	@Override
	public int getHeat()
	{
		return updateRTGs();
	}

	@Override
	public int[] getSlots()
	{
		return slot_rtg;
	}

	@Override
	public ItemStack[] getInventory()
	{
		return slots;
	}

	@Override
	public Class<? extends IRadioisotopeFuel> getDesiredClass()
	{
		return ItemRTGPellet.class;
	}
}
