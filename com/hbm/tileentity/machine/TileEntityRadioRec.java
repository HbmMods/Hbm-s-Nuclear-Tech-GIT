package com.hbm.tileentity.machine;

import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.PacketDispatcher;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityRadioRec extends TileEntity {

	public double freq;
	public boolean isOn = false;
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			PacketDispatcher.wrapper.sendToAll(new AuxGaugePacket(xCoord, yCoord, zCoord, isOn ? 1 : 0, 0));
			PacketDispatcher.wrapper.sendToAll(new AuxGaugePacket(xCoord, yCoord, zCoord, (int)(freq * 10D), 1));
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		freq = nbt.getDouble("freq");
		isOn = nbt.getBoolean("isOn");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setDouble("freq", freq);
		nbt.setBoolean("isOn", isOn);
	}
}
