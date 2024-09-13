package com.hbm.tileentity.machine;

import java.io.IOException;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.saveddata.TomSaveData;
import com.hbm.tileentity.IFluidCopiable;
import com.hbm.tileentity.IConfigurableMachine;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.util.CompatEnergyControl;

import api.hbm.fluid.IFluidStandardTransceiver;
import api.hbm.tile.IInfoProviderEC;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.EnumSkyBlock;

public class TileEntityCondenser extends TileEntityLoadedBase implements IFluidStandardTransceiver, INBTPacketReceiver, IInfoProviderEC, IConfigurableMachine, IFluidCopiable {

	public int age = 0;
	public FluidTank[] tanks;
	
	public int waterTimer = 0;
	protected int throughput;
	
	//Configurable values
	public static int inputTankSize = 100;
	public static int outputTankSize = 100;

	public TileEntityCondenser() {
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(Fluids.SPENTSTEAM, inputTankSize);
		tanks[1] = new FluidTank(Fluids.WATER, outputTankSize);
	}

	@Override
	public String getConfigName() {
		return "condenser";
	}

	@Override
	public void readIfPresent(JsonObject obj) {
		inputTankSize = IConfigurableMachine.grab(obj, "I:inputTankSize", inputTankSize);
		outputTankSize = IConfigurableMachine.grab(obj, "I:outputTankSize", outputTankSize);
	}

	@Override
	public void writeConfig(JsonWriter writer) throws IOException {
		writer.name("I:inputTankSize").value(inputTankSize);
		writer.name("I:outputTankSize").value(outputTankSize);
	}


	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			age++;
			if(age >= 2) {
				age = 0;
			}
			
			NBTTagCompound data = new NBTTagCompound();
			this.tanks[0].writeToNBT(data, "0");
			
			if(this.waterTimer > 0)
				this.waterTimer--;

			int convert = Math.min(tanks[0].getFill(), tanks[1].getMaxFill() - tanks[1].getFill());
			this.throughput = convert;
			
			if(extraCondition(convert)) {
				tanks[0].setFill(tanks[0].getFill() - convert);
				
				if(convert > 0)
					this.waterTimer = 20;
				
				int light = this.worldObj.getSavedLightValue(EnumSkyBlock.Sky, this.xCoord, this.yCoord, this.zCoord);
				
				if(TomSaveData.forWorld(worldObj).fire > 1e-5 && light > 7) { // Make both steam and water evaporate during firestorms...
					tanks[1].setFill(tanks[1].getFill() - convert);
				} else {
					tanks[1].setFill(tanks[1].getFill() + convert);
				}
				
				postConvert(convert);
			}
			
			this.tanks[1].writeToNBT(data, "1");
			
			this.subscribeToAllAround(tanks[0].getTankType(), this);
			this.sendFluidToAll(tanks[1], this);
			
			data.setByte("timer", (byte) this.waterTimer);
			packExtra(data);
			INBTPacketReceiver.networkPack(this, data, 150);
		}
	}
	
	public void packExtra(NBTTagCompound data) { }
	public boolean extraCondition(int convert) { return true; }
	public void postConvert(int convert) { }

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.tanks[0].readFromNBT(nbt, "0");
		this.tanks[1].readFromNBT(nbt, "1");
		this.waterTimer = nbt.getByte("timer");
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		tanks[0].readFromNBT(nbt, "water");
		tanks[1].readFromNBT(nbt, "steam");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		tanks[0].writeToNBT(nbt, "water");
		tanks[1].writeToNBT(nbt, "steam");
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {tanks [1]};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tanks [0]};
	}

	@Override
	public FluidTank[] getAllTanks() {
		return tanks;
	}

	@Override
	public void provideExtraInfo(NBTTagCompound data) {
		data.setDouble(CompatEnergyControl.D_CONSUMPTION_MB, throughput);
		data.setDouble(CompatEnergyControl.D_OUTPUT_MB, throughput);
	}

	@Override
	public FluidTank getTankToPaste() {
		return null;
	}
}
