package com.hbm.tileentity.network;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.calc.Location;
import com.hbm.tileentity.IConfigurableMachine;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energymk2.IEnergyReceiverMK2;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.io.IOException;

public class TileEntityConverterHeRf extends TileEntityLoadedBase implements IEnergyReceiverMK2, IEnergyHandler, IConfigurableMachine {
	
	//Thanks to the great people of Fusion Warfare for helping me with the original implementation of the RF energy API
	
	public long power;
	public final long maxPower = 5_000_000;
	public static long heInput = 5;
	public static long rfOutput = 1;
	public static double inputDecay = 0.0;
	public EnergyStorage storage = new EnergyStorage(1_000_000, 1_000_000, 1_000_000);

	@Override
	public void updateEntity() {
		
		if (!worldObj.isRemote) {
			
			long rfCreated = Math.min(storage.getMaxEnergyStored() - storage.getEnergyStored(), power / heInput * rfOutput);
			this.power -= rfCreated * heInput / rfOutput;
			this.storage.setEnergyStored((int) (storage.getEnergyStored() + rfCreated));
			if(power > 0) this.power *= (1D - inputDecay);
			if(rfCreated > 0) this.worldObj.markTileEntityChunkModified(this.xCoord, this.yCoord, this.zCoord, this);
			
			for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				this.trySubscribe(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
				
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

	@Override public boolean canConnectEnergy(ForgeDirection from) { return true; }
	@Override public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) { return 0; }
	@Override public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) { return storage.extractEnergy(maxExtract, simulate); }
	@Override public int getEnergyStored(ForgeDirection from) { return storage.getEnergyStored(); }
	@Override public int getMaxEnergyStored(ForgeDirection from) { return storage.getMaxEnergyStored(); }

	@Override public void setPower(long i) { power = i; }
	@Override public long getPower() { return power; }
	@Override public long getMaxPower() { return maxPower; }
	@Override public ConnectionPriority getPriority() { return ConnectionPriority.LOW; }
	
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
		return "HEToRFConverter";
	}

	@Override
	public void readIfPresent(JsonObject obj) {
		heInput = IConfigurableMachine.grab(obj, "L:HE_Used", heInput);
		rfOutput = IConfigurableMachine.grab(obj, "L:RF_Created", rfOutput);
		inputDecay = IConfigurableMachine.grab(obj, "D:inputDecay2", inputDecay);
	}

	@Override
	public void writeConfig(JsonWriter writer) throws IOException {
		writer.name("L:HE_Used").value(heInput);
		writer.name("L:RF_Created").value(rfOutput);
		writer.name("D:inputDecay2").value(inputDecay);
	}
}
