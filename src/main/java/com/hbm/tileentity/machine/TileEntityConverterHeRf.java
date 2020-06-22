package com.hbm.tileentity.machine;

import com.hbm.calc.Location;
import com.hbm.interfaces.IConsumer;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityConverterHeRf extends TileEntity implements IConsumer, IEnergyProvider {
	
	public long power;
	public final long maxPower = 1000000;
	public EnergyStorage storage = new EnergyStorage(4000000, 2500000, 2500000);
	
	//Thanks to the great people of Fusion Warfare for helping me with this part.

	@Override
	public void updateEntity() {
		if (!worldObj.isRemote) {
			
			long convert = Math.min(storage.getMaxEnergyStored() - storage.getEnergyStored(), power * 4);

			power -= convert / 4;
			storage.setEnergyStored((int) (storage.getEnergyStored() + convert));
			
			if(convert > 0)
				this.markDirty();
			
			for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			
				Location loc = new Location(worldObj, xCoord, yCoord, zCoord).add(dir);
				TileEntity entity = loc.getTileEntity();
			
				if (entity != null && entity instanceof IEnergyReceiver) {
				
					IEnergyReceiver receiver = (IEnergyReceiver) entity;
					
					int maxExtract = storage.getMaxExtract();
					int maxAvailable = storage.extractEnergy(maxExtract, true);
					int energyTransferred = receiver.receiveEnergy(dir.getOpposite(), maxAvailable, false);

					storage.extractEnergy(energyTransferred, false);
				}
			}
		}
	}
	
	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return storage.extractEnergy(maxExtract, simulate);
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return storage.getMaxEnergyStored();
	}

	@Override
	public void setPower(long i) {
		power = i;
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}
	
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}
	
	public long getFluxScaled(long i) {
		return (storage.getEnergyStored() * i) / storage.getMaxEnergyStored();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.power = nbt.getLong("power");
		storage.readFromNBT(nbt);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", power);
		storage.writeToNBT(nbt);
	}

}
