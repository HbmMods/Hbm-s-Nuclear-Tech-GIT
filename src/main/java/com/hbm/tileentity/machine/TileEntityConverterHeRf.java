package com.hbm.tileentity.machine;

import com.hbm.calc.Location;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IEnergyUser;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityConverterHeRf extends TileEntityMachineBase implements IEnergyUser, IEnergyHandler {
	
	public TileEntityConverterHeRf() {
		super(0);
	}

	@Override
	public String getName() {
		return "";
	}

	public long power;
	public long maxPower = 500000000;
	public EnergyStorage storage = new EnergyStorage(2000000000, 2000000000, 2000000000);
	
	public int buf;
	
	//Thanks to the great people of Fusion Warfare for helping me with this part.

	@Override
	public void updateEntity() {
		if (!worldObj.isRemote) {
			
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
				this.trySubscribe(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
			
			storage.setCapacity((int)power * 4);
			storage.setEnergyStored((int)power * 4);
			
			buf = storage.getEnergyStored();
			
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
			
			power = storage.getEnergyStored() / 4;
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("rf", storage.getEnergyStored());
			data.setInteger("maxrf", storage.getEnergyStored());
			data.setLong("he", power);
			data.setLong("maxhe", power);
			this.networkPack(data, 25);
		}
	}
	
	public void networkUnpack(NBTTagCompound nbt) {
		storage.setEnergyStored(nbt.getInteger("rf"));
		storage.setCapacity(nbt.getInteger("maxrf"));
		power = nbt.getLong("he");
		maxPower = nbt.getLong("maxhe");
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
		
		if(power < 1000000)
			return 500000000;//Long.MAX_VALUE / 100;
		
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

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return 0;
	}
}
