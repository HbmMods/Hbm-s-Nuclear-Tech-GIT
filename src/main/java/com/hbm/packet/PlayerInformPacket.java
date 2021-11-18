package com.hbm.packet;

import com.hbm.main.MainRegistry;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;

public class PlayerInformPacket implements IMessage {

	String dmesg = "";

	public PlayerInformPacket() { }

	public PlayerInformPacket(String dmesg) {
		this.dmesg = dmesg;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		dmesg = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, dmesg);
	}

	public static class Handler implements IMessageHandler<PlayerInformPacket, IMessage> {
		
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(PlayerInformPacket m, MessageContext ctx) {
			try {
				MainRegistry.proxy.displayTooltip(m.dmesg);
				
			} catch (Exception x) { }
			return null;
		}
	}
}
