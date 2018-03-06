package com.hbm.tileentity.bomb;

import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.PacketDispatcher;

import net.minecraft.tileentity.TileEntity;

public class TileEntityTurretCIWS extends TileEntityTurretBase {

	public int spin;
	public int rotation;
	
	@Override
	public void updateEntity() {
		
		super.updateEntity();
		
		this.ammo = 100;
		
		
		
		if(!worldObj.isRemote) {
			
			if(spin > 0)
				spin -= 1;
			
			rotation += spin;
			rotation = rotation % 360;
			
			PacketDispatcher.wrapper.sendToAll(new AuxGaugePacket(xCoord, yCoord, zCoord, rotation, 0));
		}
	}

}
