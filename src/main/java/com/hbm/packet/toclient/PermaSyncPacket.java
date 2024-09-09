package com.hbm.packet.toclient;

import com.hbm.packet.PermaSyncHandler;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
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
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(PermaSyncPacket m, MessageContext ctx) {
			
			try {

				EntityPlayer player = Minecraft.getMinecraft().thePlayer;
				if(player != null) PermaSyncHandler.readPacket(m.out, player.worldObj, player);
				
			} catch(Exception x) { }
			
			return null;
		}
	}
}
