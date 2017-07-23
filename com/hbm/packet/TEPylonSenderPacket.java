package com.hbm.packet;

import com.hbm.tileentity.TileEntityPylonRedWire;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

public class TEPylonSenderPacket implements IMessage {
	
	//Pylon connection synchronization packet, Mk.III
	//1: try sending list, every entry gets noted in the bit buffer
	//2: Up to 3 entries (9 variables in total, not counting origin coordiantes) sync all connections at once
	//3: One packet sent for each connection, packets are lighter and work fine for rendering

	int x;
	int y;
	int z;
	int conX;
	int conY;
	int conZ;

	public TEPylonSenderPacket()
	{
		
	}

	public TEPylonSenderPacket(int x, int y, int z, int conX, int conY, int conZ)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.conX = conX;
		this.conY = conY;
		this.conZ = conZ;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		conX = buf.readInt();
		conY = buf.readInt();
		conZ = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(conX);
		buf.writeInt(conY);
		buf.writeInt(conZ);
	}

	public static class Handler implements IMessageHandler<TEPylonSenderPacket, IMessage> {
		
		@Override
		public IMessage onMessage(TEPylonSenderPacket m, MessageContext ctx) {
			TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(m.x, m.y, m.z);

			if (te != null && te instanceof TileEntityPylonRedWire) {
					
				TileEntityPylonRedWire pyl = (TileEntityPylonRedWire) te;
				pyl.addTileEntityBasedOnCoords(m.conX, m.conY, m.conZ);
			}
			return null;
		}
	}
}
