package com.hbm.packet.toclient;

import com.hbm.main.MainRegistry;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class AuxParticlePacket implements IMessage {

	double x;
	double y;
	double z;
	int type;

	public AuxParticlePacket()
	{
		
	}

	public AuxParticlePacket(double x, double y, double z, int type)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.type = type;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readDouble();
		y = buf.readDouble();
		z = buf.readDouble();
		type = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeDouble(x);
		buf.writeDouble(y);
		buf.writeDouble(z);
		buf.writeInt(type);
	}

	public static class Handler implements IMessageHandler<AuxParticlePacket, IMessage> {
		
		@Override
		public IMessage onMessage(AuxParticlePacket m, MessageContext ctx) {
			
			try {
				
				MainRegistry.proxy.particleControl(m.x, m.y, m.z, m.type);
				
			} catch(Exception x) { }
			
			return null;
		}
	}
}
