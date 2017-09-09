package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.ISource;
import com.hbm.lib.Library;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityConverterRfHe extends TileEntity implements ISource, IEnergyReceiver {
	
	public int power;
	public final int maxPower = 1000000;
	public List<IConsumer> list = new ArrayList();
	public int age = 0;
	public EnergyStorage storage = new EnergyStorage(4000000, 2500000, 2500000);
	
	@Override
	public void updateEntity() {
		if (!worldObj.isRemote) {

			if(storage.getEnergyStored() >= 400000 && power + 100000 <= maxPower)
			{
				storage.setEnergyStored(storage.getEnergyStored() - 400000);
				power += 100000;
			}
			if(storage.getEnergyStored() >= 40000 && power + 10000 <= maxPower)
			{
				storage.setEnergyStored(storage.getEnergyStored() - 40000);
				power += 10000;
			}
			if(storage.getEnergyStored() >= 4000 && power + 1000 <= maxPower)
			{
				storage.setEnergyStored(storage.getEnergyStored() - 4000);
				power += 1000;
			}
			if(storage.getEnergyStored() >= 400 && power + 100 <= maxPower)
			{
				storage.setEnergyStored(storage.getEnergyStored() - 400);
				power += 100;
			}
			if(storage.getEnergyStored() >= 40 && power + 10 <= maxPower)
			{
				storage.setEnergyStored(storage.getEnergyStored() - 40);
				power += 10;
			}
			if(storage.getEnergyStored() >= 4 && power + 1 <= maxPower)
			{
				storage.setEnergyStored(storage.getEnergyStored() - 4);
				power += 1;
			}
		}
			
		age++;
		if(age >= 20)
		{
			age = 0;
		}
		
		if(age == 9 || age == 19)
			ffgeuaInit();
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return storage.receiveEnergy(maxReceive, simulate);
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
		if(age >= 0 && age < 10)
		{
			return true;
		}
		
		return false;
	}
	
	public int getPowerScaled(int i) {
		return (power * i) / maxPower;
	}
	
	public int getFluxScaled(int i) {
		return (storage.getEnergyStored() * i) / storage.getMaxEnergyStored();
	}

	@Override
	public int getSPower() {
		return power;
	}

	@Override
	public void setSPower(int i) {
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

}
