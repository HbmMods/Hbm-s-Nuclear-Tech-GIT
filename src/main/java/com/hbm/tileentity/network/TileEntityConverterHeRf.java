package com.hbm.tileentity.network;

import com.hbm.calc.Location;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energymk2.IEnergyReceiverMK2;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityConverterHeRf extends TileEntityLoadedBase implements IEnergyReceiverMK2, IEnergyHandler {
	
	//Thanks to the great people of Fusion Warfare for helping me with the original implementation of the RF energy API

	@Override
	public void updateEntity() {
		
		if (!worldObj.isRemote) {
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
				this.trySubscribe(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
		}
	}
	
	@Override public boolean canConnectEnergy(ForgeDirection from) { return true; }
	@Override public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) { return 0; }
	@Override public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) { return 0; }
	@Override public int getEnergyStored(ForgeDirection from) { return 0; }
	@Override public int getMaxEnergyStored(ForgeDirection from) { return 0; }
	@Override public long getPower() { return 0; }
	@Override public void setPower(long power) { }

	@Override
	public long getMaxPower() {
		return Integer.MAX_VALUE / 4;
	}

	private long lastTransfer = 0;
	
	@Override
	public long getReceiverSpeed() {
		
		if(lastTransfer > 0) {
			return lastTransfer * 2;
		} else {
			return getMaxPower();
		}
	}
	
	private boolean recursionBrake = false;
	
	@Override
	public long transferPower(long power) {
		
		if(recursionBrake)
			return power;
		
		recursionBrake = true;
		
		// we have to limit the transfer amount because otherwise FEnSUs would overflow the RF output, twice
		long out = Math.min(power, Long.MAX_VALUE / 4);
		int toRF = (int) Math.min(Integer.MAX_VALUE, out * 4);
		int rfTransferred = 0;
		int totalTransferred = 0;

		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {

			Location loc = new Location(worldObj, xCoord, yCoord, zCoord).add(dir);
			TileEntity entity = loc.getTileEntity();

			if(entity != null && entity instanceof IEnergyReceiver) {
				
				IEnergyReceiver receiver = (IEnergyReceiver) entity;
				rfTransferred = receiver.receiveEnergy(dir.getOpposite(), toRF, false);
				totalTransferred += rfTransferred;
				
				toRF -= rfTransferred; //to prevent energy duping
			}
		}

		recursionBrake = false;
		lastTransfer = totalTransferred / 4;
		
		return power - (totalTransferred / 4);
	}
}
