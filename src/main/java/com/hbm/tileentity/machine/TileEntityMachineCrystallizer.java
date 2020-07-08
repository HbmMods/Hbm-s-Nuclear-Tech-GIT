package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.inventory.CrystallizerRecipes;
import com.hbm.inventory.FluidTank;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IBatteryItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityMachineCrystallizer extends TileEntityMachineBase implements IConsumer, IFluidAcceptor {
	
	public long power;
	public static final long maxPower = 100000;
	public static final int demand = 1000;
	public static final int acidRequired = 500;
	public short progress;
	public static final short duration = 600;
	
	public FluidTank tank;

	public TileEntityMachineCrystallizer() {
		super(5);
		tank = new FluidTank(FluidType.ACID, 8000, 0);
	}

	@Override
	public String getName() {
		return "container.crystallizer";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			power = Library.chargeTEFromItems(slots, 1, power, maxPower);
			tank.loadTank(3, 4, slots);
			
			if(canProcess()) {
				
				progress++;
				power -= demand;
				
				if(progress > duration) {
					progress = 0;
					tank.setFill(tank.getFill() - acidRequired);
					processItem();
					
					this.markDirty();
				}
				
			} else {
				progress = 0;
			}
			
			tank.updateTank(xCoord, yCoord, zCoord, this.worldObj.provider.dimensionId);
			
			NBTTagCompound data = new NBTTagCompound();
			data.setShort("progress", progress);
			data.setLong("power", power);
			this.networkPack(data, 25);
		}
	}
	
	public void networkUnpack(NBTTagCompound data) {
		
		this.power = data.getLong("power");
		this.progress = data.getShort("progress");
	}
	
	private void processItem() {

		ItemStack result = CrystallizerRecipes.getOutput(slots[0]);
		
		if(result == null) //never happens but you can't be sure enough
			return;
		
		if(slots[2] == null)
			slots[2] = result;
		else if(slots[2].stackSize < slots[2].getMaxStackSize())
			slots[2].stackSize++;
		
		this.decrStackSize(0, 1);
	}
	
	private boolean canProcess() {
		
		//Is there no input?
		if(slots[0] == null)
			return false;
		
		if(power < demand)
			return false;
		
		if(tank.getFill() < acidRequired)
			return false;
		
		ItemStack result = CrystallizerRecipes.getOutput(slots[0]);
		
		//Or output?
		if(result == null)
			return false;
		
		//Does the output not match?
		if(slots[2] != null && (slots[2].getItem() != result.getItem() || slots[2].getItemDamage() != result.getItemDamage()))
			return false;
		
		//Or is the output slot already full?
		if(slots[2] != null && slots[2].stackSize >= slots[2].getMaxStackSize())
			return false;
		
		return true;
	}
	
	public long getPowerScaled(int i) {
		return (power * i) / maxPower;
	}
	
	public int getProgressScaled(int i) {
		return (progress * i) / duration;
	}

	@Override
	public void setFillstate(int fill, int index) {
		tank.setFill(fill);
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		tank.setFill(fill);
	}

	@Override
	public void setType(FluidType type, int index) {
		tank.setTankType(type);
	}

	@Override
	public List<FluidTank> getTanks() {
		return new ArrayList() {{ add(tank); }};
	}

	@Override
	public int getFluidFill(FluidType type) {
		return tank.getFill();
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		return tank.getMaxFill();
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
		
		if(i == 0 && CrystallizerRecipes.getOutput(itemStack) != null)
			return true;
		
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
		
		return side == 0 ? new int[] { 2 } : (side == 1 ? new int[] { 0 } : new int[] { 1 });
	}
}
