package com.hbm.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

public class PermaSyncPacket implements IMessage {
	
	EntityPlayerMP player;	//server only, for writing
	ByteBuf out;			//client only, for reading

	public PermaSyncPacket() { }

	public PermaSyncPacket(EntityPlayerMP player) {
		this.player = player;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		PermaSyncHandler.writePacket(buf, player.worldObj, player);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.out = buf;
	}

	public static class Handler implements IMessageHandler<PermaSyncPacket, IMessage> {
		
		@Override
		public IMessage onMessage(PermaSyncPacket m, MessageContext ctx) {
			
			try {
				
				EntityPlayerMP player = m.player;
				if(player != null) PermaSyncHandler.readPacket(m.out, player.worldObj, player);
				
			} catch(Exception x) { }
			
			return null;
		}
	}
}
