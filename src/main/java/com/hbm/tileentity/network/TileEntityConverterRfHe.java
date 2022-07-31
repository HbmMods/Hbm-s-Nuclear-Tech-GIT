package com.hbm.tileentity.network;

import com.hbm.config.MachineConfig;
import com.hbm.interfaces.Untested;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energy.IEnergyGenerator;
import cofh.api.energy.IEnergyHandler;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityConverterRfHe extends TileEntityLoadedBase implements IEnergyGenerator, IEnergyHandler {
	 long rate = MachineConfig.convRate;
	@Override
	public void setPower(long power) {
		subBuffer = power;
	}

	@Override
	public long getPower() {
		return subBuffer;
	}

	@Override
	public long getMaxPower() {
		return subBuffer;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}

	private long subBuffer;
	private boolean recursionBrake = false;;
	
	@Untested
	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		
		if(recursionBrake)
			return 0;
		
		if(simulate)
			return 0;
		
		recursionBrake = true;
		
		long capacity = maxReceive / rate;
		subBuffer = capacity;
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			this.sendPower(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
		}
		
		recursionBrake = false;
		
		return (int) ((capacity - subBuffer) * rate);
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return 0;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return 0;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return 1000000;
	}
}
