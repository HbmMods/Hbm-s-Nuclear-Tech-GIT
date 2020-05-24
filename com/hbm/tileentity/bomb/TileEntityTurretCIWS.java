package com.hbm.tileentity.bomb;

import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class TileEntityTurretCIWS extends TileEntityTurretBase {

	public int spin;
	public int rotation;
	
	@Override
	public void updateEntity() {
		
		super.updateEntity();
		
		if(!worldObj.isRemote) {
			
			if(spin > 0)
				spin -= 1;
			
			rotation += spin;
			rotation = rotation % 360;
			
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(xCoord, yCoord, zCoord, rotation, 0), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
		}
	}

}
