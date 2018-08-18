package com.hbm.tileentity.machine;

import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEAssemblerPacket;

import net.minecraft.tileentity.TileEntity;

public class TileEntityRadiobox extends TileEntity {
	
	public double freq;
	public int type;
	public String message;
	public int music;
	
	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			//PacketDispatcher.wrapper.sendToAll(new TEAssemblerPacket(xCoord, yCoord, zCoord));
		}
	}

}
