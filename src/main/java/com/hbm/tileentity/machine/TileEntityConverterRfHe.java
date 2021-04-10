package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.ISource;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityConverterRfHe extends TileEntityMachineBase implements ISource, IEnergyHandler {
	
	public long power;
	public long maxPower = 500000000;
	public List<IConsumer> list = new ArrayList();
	public boolean tact;
	public EnergyStorage storage = new EnergyStorage(2000000000, 2000000000, 2000000000);
	
	public int buf;
	
	public TileEntityConverterRfHe() {
		super(0);
	}

	@Override
	public String getName() {
		return "";
	}
	
	@Override
	public void updateEntity() {
		
		if (!worldObj.isRemote) {
			
			power = storage.getEnergyStored() / 4;
			maxPower = Math.max(1000000, power);
			
			buf = storage.getEnergyStored();

			tact = false;
			ffgeuaInit();
			tact = true;
			ffgeuaInit();
			
			storage.setEnergyStored((int)power * 4);
			
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
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		storage.setCapacity(2000000000);
		return storage.receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		
		if(storage.getEnergyStored() < 4000000)
			return 2000000000;
		
		return storage.getMaxEnergyStored();
	}

	@Override
	public void ffgeua(int x, int y, int z, boolean newTact) {

		Library.ffgeua(x, y, z, newTact, this, worldObj);
	}

	@Override
	public void ffgeuaInit() {
		ffgeua(this.xCoord, this.yCoord + 1, this.zCoord, getTact());
		ffgeua(this.xCoord, this.yCoord - 1, this.zCoord, getTact());
		ffgeua(this.xCoord - 1, this.yCoord, this.zCoord, getTact());
		ffgeua(this.xCoord + 1, this.yCoord, this.zCoord, getTact());
		ffgeua(this.xCoord, this.yCoord, this.zCoord - 1, getTact());
		ffgeua(this.xCoord, this.yCoord, this.zCoord + 1, getTact());
	}
	
	@Override
	public boolean getTact() {
		return tact;
	}
	
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}
	
	public int getFluxScaled(int i) {
		return (storage.getEnergyStored() * i) / storage.getMaxEnergyStored();
	}

	@Override
	public long getSPower() {
		return power;
	}

	@Override
	public void setSPower(long i) {
		this.power = i;
	}

	@Override
	public List<IConsumer> getList() {
		return list;
	}

	@Override
	public void clearList() {
		this.list.clear();
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
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return 0;
	}

}
