package com.hbm.packet.toserver;

import com.hbm.handler.packet.PacketOptimizationHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class TEDataRequestPacket implements IMessage {

	private int x;
	private int z;

	public TEDataRequestPacket() {}

	public TEDataRequestPacket(int x, int z) {
		this.x = x;
		this.z = z;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.x = buf.readInt();
		this.z = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.x);
		buf.writeInt(this.z);
	}

	public static class Handler implements IMessageHandler<TEDataRequestPacket, IMessage> {

		@Override
		public IMessage onMessage(TEDataRequestPacket m, MessageContext ctx) {
			PacketOptimizationHandler.forceSendChunk(ctx.getServerHandler().playerEntity, m.x, m.z);
			return null;
		}
	}
}
