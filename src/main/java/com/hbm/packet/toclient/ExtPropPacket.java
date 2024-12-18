package com.hbm.packet.toclient;

import com.hbm.extprop.HbmLivingProps;
import com.hbm.extprop.HbmPlayerProps;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;

public class ExtPropPacket implements IMessage {

	ByteBuf buffer;

	public ExtPropPacket() { }

	public ExtPropPacket(ByteBuf buf) {

		this.buffer = Unpooled.buffer();
		buffer.writeBytes(buf);
	}

	@Override
	public void fromBytes(ByteBuf buf) {

		if (buffer == null) {
			buffer = new PacketBuffer(Unpooled.buffer());
		}
		buffer.writeBytes(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {

		if (buffer == null) {
			buffer = new PacketBuffer(Unpooled.buffer());
		}
		buf.writeBytes(buffer);
	}

	public static class Handler implements IMessageHandler<ExtPropPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(ExtPropPacket m, MessageContext ctx) {

			if(Minecraft.getMinecraft().theWorld == null)
				return null;

			HbmLivingProps props = HbmLivingProps.getData(Minecraft.getMinecraft().thePlayer);
			HbmPlayerProps pprps = HbmPlayerProps.getData(Minecraft.getMinecraft().thePlayer);

			props.deserialize(m.buffer);
			pprps.deserialize(m.buffer);

			m.buffer.release();

			return null;
		}
	}
}
