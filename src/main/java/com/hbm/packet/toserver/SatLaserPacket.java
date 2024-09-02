package com.hbm.packet.toserver;

import com.hbm.items.ISatChip;
import com.hbm.items.tool.ItemSatInterface;
import com.hbm.saveddata.SatelliteSavedData;
import com.hbm.saveddata.satellites.Satellite;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class SatLaserPacket implements IMessage {

	//0: Add
	//1: Subtract
	//2: Set
	int x;
	int z;
	int freq;

	public SatLaserPacket()
	{
		
	}

	public SatLaserPacket(int x, int z, int freq)
	{
		this.x = x;
		this.z = z;
		this.freq = freq;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		z = buf.readInt();
		freq = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(z);
		buf.writeInt(freq);
	}

	public static class Handler implements IMessageHandler<SatLaserPacket, IMessage> {
		
		@Override
		public IMessage onMessage(SatLaserPacket m, MessageContext ctx) {
			
			EntityPlayer p = ctx.getServerHandler().playerEntity;
			
			if(p.getHeldItem() != null && p.getHeldItem().getItem() instanceof ItemSatInterface) {
				
				int freq = ISatChip.getFreqS(p.getHeldItem());
				
				if(freq == m.freq) {
				    Satellite sat = SatelliteSavedData.getData(p.worldObj).getSatFromFreq(m.freq);
				    
				    if(sat != null)
				    	sat.onClick(p.worldObj, m.x, m.z);
				}
			}
			
			return null;
		}
	}
}
