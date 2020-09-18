package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.CyclotronRecipes;
import com.hbm.inventory.FluidTank;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityMachineCyclotron extends TileEntityMachineBase implements IFluidSource, IFluidAcceptor {
	
	public long power;
	public static final long maxPower = 100000000;
	public int consumption = 1000000;
	
	public boolean isOn;
	
	private int age;
	
	public int progress;
	public static final int duration = 690;
	
	public FluidTank coolant;
	public FluidTank amat;
	
	public List<IFluidAcceptor> list = new ArrayList();

	public TileEntityMachineCyclotron() {
		super(16);

		coolant = new FluidTank(FluidType.COOLANT, 32000, 0);
		amat = new FluidTank(FluidType.AMAT, 8000, 1);
	}

	@Override
	public String getName() {
		return "container.machineCyclotron";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {

			age++;
			if(age >= 20)
			{
				age = 0;
			}
			
			if(age == 9 || age == 19)
				fillFluidInit(amat.getTankType());

			this.power = Library.chargeTEFromItems(slots, 13, power, maxPower);
			this.coolant.loadTank(11, 12, slots);
			this.amat.unloadTank(9, 10, slots);
			
			if(isOn) {
				
				if(canProcess()) {
					
					progress++;
					power -= consumption;
					
					if(progress >= duration) {
						process();
						progress = 0;
						this.markDirty();
					}
					
					if(coolant.getFill() > 0) {
						
						if(worldObj.rand.nextInt(3) == 0)
							coolant.setFill(coolant.getFill() - 1);
						
					} else {
						worldObj.newExplosion(null, xCoord + 0.5, yCoord + 1.5, zCoord + 0.5, 25, true, true);
					}
					
				} else {
					progress = 0;
				}
				
			} else {
				progress = 0;
			}
			
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setInteger("progress", progress);
			data.setBoolean("isOn", isOn);
			this.networkPack(data, 25);
			
			this.coolant.updateTank(xCoord, yCoord, zCoord, this.worldObj.provider.dimensionId);
			this.amat.updateTank(xCoord, yCoord, zCoord, this.worldObj.provider.dimensionId);
		}
	}
	
	public void networkUnpack(NBTTagCompound data) {
		this.isOn = data.getBoolean("isOn");
		this.power = data.getLong("power");
		this.progress = data.getInteger("progress");
	}
	
	public void handleButtonPacket(int value, int meta) {

		System.out.println("Before: " + isOn);
		
		if(this.isOn)
			this.isOn = false;
		else
			this.isOn = true;
		
		System.out.println("After: " + isOn);
	}
	
	public boolean canProcess() {
		
		if(power < consumption)
			return false;
		
		for(int i = 0; i < 3; i++) {
			
			ItemStack out = CyclotronRecipes.getOutput(slots[i + 3], slots[i]);
			
			if(out == null)
				continue;
			
			if(slots[i + 6] == null)
				return true;
			
			if(slots[i + 6].getItem() == out.getItem() && slots[i + 6].getItemDamage() == out.getItemDamage() && slots[i + 6].stackSize < out.getMaxStackSize())
				return true;
		}
		
		return false;
	}
	
	public void process() {
		
		int amat = 0;
		
		for(int i = 0; i < 3; i++) {
			
			ItemStack out = CyclotronRecipes.getOutput(slots[i + 3], slots[i]);
			
			if(out == null)
				continue;
			
			if(slots[i + 6] == null) {
				
				amat += this.getAmat(slots[i]);
				this.decrStackSize(i, 1);
				this.decrStackSize(i + 3, 1);
				slots[i + 6] = out;
				continue;
			}
			
			if(slots[i + 6].getItem() == out.getItem() && slots[i + 6].getItemDamage() == out.getItemDamage() && slots[i + 6].stackSize < out.getMaxStackSize()) {
				
				amat += this.getAmat(slots[i]);
				this.decrStackSize(i, 1);
				this.decrStackSize(i + 3, 1);
				slots[i + 6].stackSize++;
			}
		}
		
		this.amat.setFill(this.amat.getFill() + amat);
		if(this.amat.getFill() > this.amat.getMaxFill())
			this.amat.setFill(this.amat.getMaxFill());
	}
	
	public int getAmat(ItemStack stack) {
		
		//TODO: move to cyclotron recipe handler and register it on a per-recipe basis
		
		if(stack == null)
			return 0;
		
		if(stack.getItem() == ModItems.part_lithium)
			return 50;
		if(stack.getItem() == ModItems.part_beryllium)
			return 25;
		if(stack.getItem() == ModItems.part_carbon)
			return 10;
		if(stack.getItem() == ModItems.part_copper)
			return 15;
		if(stack.getItem() == ModItems.part_plutonium)
			return 100;
		
		return 0;
	}

	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}

	public int getProgressScaled(int i) {
		return (progress * i) / duration;
	}

	@Override
	public void setFillstate(int fill, int index) {
		if(index == 0)
			coolant.setFill(fill);
		else if(index == 1)
			amat.setFill(fill);
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		if(type == FluidType.COOLANT)
			coolant.setFill(fill);
		else if(type == FluidType.AMAT)
			amat.setFill(fill);
	}

	@Override
	public void setType(FluidType type, int index) {
		if(index == 0)
			coolant.setTankType(type);
		else if(index == 1)
			coolant.setTankType(type);
	}

	@Override
	public List<FluidTank> getTanks() {
		return Arrays.asList(new FluidTank[] {coolant, amat});
	}

	@Override
	public int getFluidFill(FluidType type) {
		if(type == FluidType.COOLANT)
			return coolant.getFill();
		else if(type == FluidType.AMAT)
			return amat.getFill();
		
		return 0;
	}

	@Override
	public void fillFluidInit(FluidType type) {

		fillFluid(xCoord + 3, yCoord, zCoord + 1, getTact(), type);
		fillFluid(xCoord + 3, yCoord, zCoord - 1, getTact(), type);
		fillFluid(xCoord - 3, yCoord, zCoord + 1, getTact(), type);
		fillFluid(xCoord - 3, yCoord, zCoord - 1, getTact(), type);

		fillFluid(xCoord + 1, yCoord, zCoord + 3, getTact(), type);
		fillFluid(xCoord - 1, yCoord, zCoord + 3, getTact(), type);
		fillFluid(xCoord + 1, yCoord, zCoord - 3, getTact(), type);
		fillFluid(xCoord - 1, yCoord, zCoord - 3, getTact(), type);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}
	
	@Override
	public boolean getTact() {
		return age >= 0 && age < 10;
	}

	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		return list;
	}

	@Override
	public void clearFluidList(FluidType type) {
		list.clear();
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		
		if(type == FluidType.COOLANT)
			return coolant.getMaxFill();
		
		return 0;
	}
}
