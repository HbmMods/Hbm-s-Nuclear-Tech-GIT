package com.hbm.packet;

import com.hbm.entity.logic.EntityDeathBlast;
import com.hbm.saveddata.SatelliteSaveStructure;
import com.hbm.saveddata.SatelliteSavedData;

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

		    SatelliteSavedData data = (SatelliteSavedData)p.worldObj.perWorldStorage.loadData(SatelliteSavedData.class, "satellites");
		    if(data == null) {
		    	p.worldObj.perWorldStorage.setData("satellites", new SatelliteSavedData(p.worldObj));
		        
		        data = (SatelliteSavedData)p.worldObj.perWorldStorage.loadData(SatelliteSavedData.class, "satellites");
		    }
		    
		    SatelliteSaveStructure sat = data.getSatFromFreq(m.freq);
		    
		    if(sat != null) {
		    	if(sat.lastOp + 10000 < System.currentTimeMillis()) {
		    		sat.lastOp = System.currentTimeMillis();
		    		
		    		int y = p.worldObj.getHeightValue(m.x, m.z);
		    		
		    		//ExplosionLarge.explodeFire(p.worldObj, m.x, y, m.z, 50, true, true, true);
		    		EntityDeathBlast blast = new EntityDeathBlast(p.worldObj);
		    		blast.posX = m.x;
		    		blast.posY = y;
		    		blast.posZ = m.z;
		    		
		    		p.worldObj.spawnEntityInWorld(blast);
		    	}
		    }
			
			return null;
		}
	}
}
