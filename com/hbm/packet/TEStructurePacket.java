package com.hbm.packet;

import java.util.ArrayList;
import java.util.List;

import com.hbm.tileentity.TileEntityMachineIGenerator;
import com.hbm.tileentity.TileEntityPylonRedWire;
import com.hbm.tileentity.TileEntityStructureMarker;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

public class TEStructurePacket implements IMessage {

	int x;
	int y;
	int z;
	int type;

	public TEStructurePacket()
	{
		
	}

	public TEStructurePacket(int x, int y, int z, int type)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.type = type;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		type = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(type);
	}

	public static class Handler implements IMessageHandler<TEStructurePacket, IMessage> {
		
		@Override
		public IMessage onMessage(TEStructurePacket m, MessageContext ctx) {
			TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(m.x, m.y, m.z);

			if (te != null && te instanceof TileEntityStructureMarker) {
					
				TileEntityStructureMarker marker = (TileEntityStructureMarker) te;
				marker.type = m.type;
			}
			return null;
		}
	}
}
