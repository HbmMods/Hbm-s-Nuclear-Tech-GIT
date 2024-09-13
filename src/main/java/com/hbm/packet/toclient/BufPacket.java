package com.hbm.packet.toclient;

import com.hbm.tileentity.IBufPacketReceiver;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

public class BufPacket implements IMessage {

	int x;
	int y;
	int z;
	IBufPacketReceiver rec;
	ByteBuf buf;
	
	public BufPacket() { }

	public BufPacket(int x, int y, int z, IBufPacketReceiver rec) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.rec = rec;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
		this.buf = buf;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		this.rec.serialize(buf);
	}

	public static class Handler implements IMessageHandler<BufPacket, IMessage> {
		
		@Override
		public IMessage onMessage(BufPacket m, MessageContext ctx) {
			
			if(Minecraft.getMinecraft().theWorld == null)
				return null;
			
			TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(m.x, m.y, m.z);
			
			if(te instanceof IBufPacketReceiver) {
				((IBufPacketReceiver) te).deserialize(m.buf);
			}
			
			return null;
		}
	}
}
