package com.hbm.tileentity.network;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.tileentity.IConfigurableMachine;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energymk2.IEnergyProviderMK2;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

import java.io.IOException;

public class TileEntityConverterRfHe extends TileEntityLoadedBase implements IEnergyProviderMK2, IEnergyHandler, IConfigurableMachine {

	public long power;
	public final long maxPower = 5_000_000;
	public static long rfInput = 2;
	public static long heOutput = 5;
	public static double inputDecay = 0.0;

	public EnergyStorage storage = new EnergyStorage(1_000_000, 1_000_000, 1_000_000);

	@Override
	public void updateEntity() {
		
		if (!worldObj.isRemote) {
			
			long rfCreated = Math.min(storage.getEnergyStored(), (maxPower - power) * rfInput / heOutput);
			storage.setEnergyStored((int) (storage.getEnergyStored() - rfCreated));
			power += rfCreated * heOutput / rfInput;
			if(storage.getEnergyStored() > 0) storage.extractEnergy((int) Math.ceil(storage.getEnergyStored() * inputDecay), false);
			if(rfCreated > 0) this.worldObj.markTileEntityChunkModified(this.xCoord, this.yCoord, this.zCoord, this);
			
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				this.tryProvide(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
			}
		}
	}
	
	@Override public boolean canConnectEnergy(ForgeDirection from) { return true; }
	@Override public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) { return storage.receiveEnergy(maxReceive, simulate); }
	@Override public int getEnergyStored(ForgeDirection from) { return storage.getEnergyStored(); }
	@Override public int getMaxEnergyStored(ForgeDirection from) { return storage.getMaxEnergyStored(); }
	@Override public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) { return 0; }

	@Override public long getPower() { return power; }
	@Override public void setPower(long power) { this.power = power; }
	@Override public long getMaxPower() { return maxPower; }
	
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
	public String getConfigName() {
		return "RFToHEConverter";
	}

	@Override
	public void readIfPresent(JsonObject obj) {
		rfInput = IConfigurableMachine.grab(obj, "L:RF_Used2", rfInput);
		heOutput = IConfigurableMachine.grab(obj, "L:HE_Created2", heOutput);
		inputDecay = IConfigurableMachine.grab(obj, "D:inputDecay2", inputDecay);
	}

	@Override
	public void writeConfig(JsonWriter writer) throws IOException {
		writer.name("L:RF_Used2").value(rfInput);
		writer.name("L:HE_Created2").value(heOutput);
		writer.name("D:inputDecay2").value(inputDecay);
	}
}
