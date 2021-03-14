package com.hbm.tileentity.bomb;

import com.hbm.entity.item.EntityFireworks;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityFireworks extends TileEntity {

	public int color = 0xff0000;
	public String message = "NUCLEAR TECH";
	public int charges;
	
	int index;
	int delay;
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord) && !message.isEmpty() && charges > 0) {
				
				delay--;
				
				if(delay <= 0) {
					delay = 30;
					
					int c = (int)(message.charAt(index));
					
					int mod = index % 9;

					double offX = (mod / 3 - 1) * 0.3125;
					double offZ = (mod % 3 - 1) * 0.3125;
					
					EntityFireworks fireworks = new EntityFireworks(worldObj, xCoord + 0.5 + offX, yCoord + 1.5, zCoord + 0.5 + offZ, color, c);
					worldObj.spawnEntityInWorld(fireworks);
					
					worldObj.playSoundAtEntity(fireworks, "hbm:weapon.rocketFlame", 3.0F, 1.0F);
					
					charges--;
					this.markDirty();
					
					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "vanillaExt");
					data.setString("mode", "flame");
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, xCoord + 0.5 + offX, yCoord + 1.125, zCoord + 0.5 + offZ), new TargetPoint(this.worldObj.provider.dimensionId, xCoord + 0.5 + offX, yCoord + 1.125, zCoord + 0.5 + offZ, 100));
					
					index++;
					
					if(index >= message.length()) {
						index = 0;
						delay = 100;
					}
				}
				
			} else {
				delay = 0;
				index = 0;
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.charges = nbt.getInteger("charges");
		this.color = nbt.getInteger("color");
		this.message = nbt.getString("message");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setInteger("charges", charges);
		nbt.setInteger("color", color);
		nbt.setString("message", message);
	}
}
