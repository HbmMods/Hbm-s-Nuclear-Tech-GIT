package com.hbm.tileentity.machine;

import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEStructurePacket;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityStructureMarker extends TileEntity {
	
	//0: Factory
	//1: Nuclear Reactor
	//2: Reactor with Coat
	//3: Fusion Reactor
	//4: Fusion Reactor with Coat
	//5: Watz Power Plant
	//6: Fusionary Watz Plant
	public int type = 0;
	
	@Override
	public void updateEntity() {
		
		if(this.type > 6)
			type -= 7;

		if(!worldObj.isRemote)
			PacketDispatcher.wrapper.sendToAll(new TEStructurePacket(xCoord, yCoord, zCoord, type));
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		type = nbt.getInteger("type");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("type", type);
	}

}
