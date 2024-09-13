package com.hbm.packet.toclient;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;

public class ParticleBurstPacket implements IMessage {

	int x;
	int y;
	int z;
	int block;
	int meta;

	public ParticleBurstPacket()
	{
		
	}

	public ParticleBurstPacket(int x, int y, int z, int block, int meta)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.block = block;
		this.meta = meta;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		block = buf.readInt();
		meta = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(block);
		buf.writeInt(meta);
	}

	public static class Handler implements IMessageHandler<ParticleBurstPacket, IMessage> {
		
		@Override
		public IMessage onMessage(ParticleBurstPacket m, MessageContext ctx) {
			
			try {
				Minecraft.getMinecraft().effectRenderer.addBlockDestroyEffects(m.x, m.y, m.z, Block.getBlockById(m.block), m.meta);
			} catch(Exception x) { }
			
			return null;
		}
	}
}
