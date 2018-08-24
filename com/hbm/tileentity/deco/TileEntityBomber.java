package com.hbm.tileentity.deco;

import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.PacketDispatcher;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityBomber extends TileEntity {

	public int yaw;
	public int pitch;
	public int type = 1;

	@Override
	public void updateEntity() {

		if (!worldObj.isRemote) {
			PacketDispatcher.wrapper.sendToAll(new AuxGaugePacket(xCoord, yCoord, zCoord, yaw, 0));
			PacketDispatcher.wrapper.sendToAll(new AuxGaugePacket(xCoord, yCoord, zCoord, pitch, 1));
			PacketDispatcher.wrapper.sendToAll(new AuxGaugePacket(xCoord, yCoord, zCoord, type, 2));
		}
	}

	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		yaw = nbt.getInteger("bomberYaw");
		pitch = nbt.getInteger("bomberPitch");
		type = nbt.getInteger("bomberType");
	}

	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("bomberYaw", yaw);
		nbt.setInteger("bomberPitch", pitch);
		nbt.setInteger("bomberType", type);
	}

}
