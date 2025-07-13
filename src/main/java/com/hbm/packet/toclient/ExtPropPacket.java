package com.hbm.packet.toclient;

import com.hbm.extprop.HbmLivingProps;
import com.hbm.extprop.HbmPlayerProps;

import com.hbm.packet.threading.PrecompiledPacket;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;

public class ExtPropPacket extends PrecompiledPacket {

	HbmLivingProps props;
	HbmPlayerProps pprps;
	ByteBuf buf;

	public ExtPropPacket() { }

	public ExtPropPacket(HbmLivingProps props, HbmPlayerProps pprps) {
		this.props = props;
		this.pprps = pprps;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.buf = buf;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		props.serialize(buf);
		pprps.serialize(buf);
	}

	public static class Handler implements IMessageHandler<ExtPropPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(ExtPropPacket m, MessageContext ctx) {

			if(Minecraft.getMinecraft().theWorld == null)
				return null;

			HbmLivingProps props = HbmLivingProps.getData(Minecraft.getMinecraft().thePlayer);
			HbmPlayerProps pprps = HbmPlayerProps.getData(Minecraft.getMinecraft().thePlayer);

			props.deserialize(m.buf);
			pprps.deserialize(m.buf);

			m.buf.release();

			return null;
		}
	}
}
