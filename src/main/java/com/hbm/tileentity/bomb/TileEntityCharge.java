package com.hbm.tileentity.bomb;

import com.hbm.blocks.bomb.BlockChargeBase;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.NBTPacket;
import com.hbm.tileentity.INBTPacketReceiver;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityCharge extends TileEntity implements INBTPacketReceiver {
	
	public boolean started;
	public int timer;

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(started) {
				timer--;
				
				if(timer % 20 == 0 && timer > 0)
					worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:weapon.fstbmbPing", 1.0F, 1.0F);
				
				if(timer <= 0) {
					((BlockChargeBase)this.getBlockType()).explode(worldObj, xCoord, yCoord, zCoord);
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("timer", timer);
			data.setBoolean("started", started);
			PacketDispatcher.wrapper.sendToAllAround(new NBTPacket(data, xCoord, yCoord, zCoord), new TargetPoint(this.worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 100));
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound data) {
		timer = data.getInteger("timer");
		started = data.getBoolean("started");
	}
	
	public String getMinutes() {
		
		String mins = "" + (timer / 1200);
		
		if(mins.length() == 1)
			mins = "0" + mins;
		
		return mins;
	}
	
	public String getSeconds() {
		
		String mins = "" + ((timer / 20) % 60);
		
		if(mins.length() == 1)
			mins = "0" + mins;
		
		return mins;
	}
}
