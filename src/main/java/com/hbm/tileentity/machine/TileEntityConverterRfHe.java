package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.interfaces.Untested;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IEnergyGenerator;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityConverterRfHe extends TileEntity implements IEnergyGenerator, IEnergyHandler {

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
	
	@Untested
	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		
		if(simulate)
			return 0;
		
		long capacity = maxReceive * 4L;
		subBuffer = capacity;
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			this.sendPower(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
		}
		
		return (int) ((capacity - subBuffer) / 4L);
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
