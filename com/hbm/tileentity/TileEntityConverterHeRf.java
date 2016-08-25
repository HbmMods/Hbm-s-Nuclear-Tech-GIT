package com.hbm.tileentity;

import com.hbm.calc.Location;
import com.hbm.interfaces.IConsumer;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityConverterHeRf extends TileEntity implements IConsumer, IEnergyProvider {
	
	public int power;
	public final int maxPower = 1000000;
	public EnergyStorage storage = new EnergyStorage(4000000, 2500000, 2500000);
	
	//Thanks to the great people of Fusion Warfare for helping me with this part.

	@Override
	public void updateEntity() {
		if (!worldObj.isRemote) {

			if(power >= 100000 && storage.getEnergyStored() + 400000 <= storage.getMaxEnergyStored())
			{
				power -= 100000;
				storage.setEnergyStored(storage.getEnergyStored() + 400000);
			}
			if(power >= 10000 && storage.getEnergyStored() + 40000 <= storage.getMaxEnergyStored())
			{
				power -= 10000;
				storage.setEnergyStored(storage.getEnergyStored() + 40000);
			}
			if(power >= 1000 && storage.getEnergyStored() + 4000 <= storage.getMaxEnergyStored())
			{
				power -= 1000;
				storage.setEnergyStored(storage.getEnergyStored() + 4000);
			}
			if(power >= 100 && storage.getEnergyStored() + 400 <= storage.getMaxEnergyStored())
			{
				power -= 100;
				storage.setEnergyStored(storage.getEnergyStored() + 400);
			}
			if(power >= 10 && storage.getEnergyStored() + 40 <= storage.getMaxEnergyStored())
			{
				power -= 10;
				storage.setEnergyStored(storage.getEnergyStored() + 4);
			}
			if(power >= 1 && storage.getEnergyStored() + 4 <= storage.getMaxEnergyStored())
			{
				power -= 1;
				storage.setEnergyStored(storage.getEnergyStored() + 40);
			}
			
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
	public void setPower(int i) {
		power = i;
	}

	@Override
	public int getPower() {
		return power;
	}

	@Override
	public int getMaxPower() {
		return maxPower;
	}
	
	public int getPowerScaled(int i) {
		return (power * i) / maxPower;
	}
	
	public int getFluxScaled(int i) {
		return (storage.getEnergyStored() * i) / storage.getMaxEnergyStored();
	}

}
