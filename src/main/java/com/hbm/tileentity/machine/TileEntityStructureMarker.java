package com.hbm.tileentity.machine;

import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEStructurePacket;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityStructureMarker extends TileEntity {
	
	//0: Nuclear Reactor
	//1: Watz Power Plant
	//2: Fusionary Watz Plant
	public int type = 0;
	
	@Override
	public void updateEntity() {
		
		if(this.type > 2)
			type -= 3;

		if(!worldObj.isRemote)
			PacketDispatcher.wrapper.sendToAllAround(new TEStructurePacket(xCoord, yCoord, zCoord, type), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 250));
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
