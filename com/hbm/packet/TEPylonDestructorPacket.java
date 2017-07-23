package com.hbm.packet;

import com.hbm.tileentity.TileEntityPylonRedWire;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

public class TEPylonDestructorPacket implements IMessage {

	int x;
	int y;
	int z;

	public TEPylonDestructorPacket()
	{
		
	}

	public TEPylonDestructorPacket(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
	}

	public static class Handler implements IMessageHandler<TEPylonDestructorPacket, IMessage> {
		
		@Override
		public IMessage onMessage(TEPylonDestructorPacket m, MessageContext ctx) {
			TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(m.x, m.y, m.z);

			if (te != null && te instanceof TileEntityPylonRedWire) {
					
				TileEntityPylonRedWire pyl = (TileEntityPylonRedWire) te;
				pyl.connected.clear();
			}
			return null;
		}
	}
}
