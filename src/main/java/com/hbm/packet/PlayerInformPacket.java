package com.hbm.packet;

import com.hbm.main.MainRegistry;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.IChatComponent;

public class PlayerInformPacket implements IMessage {

	boolean fancy;
	private String dmesg = "";
	private IChatComponent component;

	public PlayerInformPacket() { }

	public PlayerInformPacket(String dmesg) {
		this.fancy = false;
		this.dmesg = dmesg;
	}

	public PlayerInformPacket(IChatComponent component) {
		this.fancy = true;
		this.component = component;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		fancy = buf.readBoolean();
		
		if(!fancy) {
			dmesg = ByteBufUtils.readUTF8String(buf);
		} else {
			component = IChatComponent.Serializer.func_150699_a(ByteBufUtils.readUTF8String(buf));
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(fancy);
		if(!fancy) {
			ByteBufUtils.writeUTF8String(buf, dmesg);
		} else {
			ByteBufUtils.writeUTF8String(buf, IChatComponent.Serializer.func_150696_a(component));
		}
	}

	public static class Handler implements IMessageHandler<PlayerInformPacket, IMessage> {
		
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(PlayerInformPacket m, MessageContext ctx) {
			try {
				
				MainRegistry.proxy.displayTooltip(m.fancy ? m.component.getFormattedText() : m.dmesg);
				
			} catch (Exception x) { }
			return null;
		}
	}
}
