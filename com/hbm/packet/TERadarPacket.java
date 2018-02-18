package com.hbm.packet;

import com.hbm.tileentity.conductor.TileEntityPylonRedWire;
import com.hbm.tileentity.machine.TileEntityMachineRadar;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

public class TERadarPacket implements IMessage {

	int x;
	int y;
	int z;
	int conX;
	int conY;
	int conZ;

	public TERadarPacket() {

	}

	public TERadarPacket(int x, int y, int z, int conX, int conY, int conZ) {
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

	public static class Handler implements IMessageHandler<TERadarPacket, IMessage> {

		@Override
		public IMessage onMessage(TERadarPacket m, MessageContext ctx) {
			TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(m.x, m.y, m.z);

			try {
				if (te != null && te instanceof TileEntityMachineRadar) {

					TileEntityMachineRadar radar = (TileEntityMachineRadar) te;
					radar.nearbyMissiles.add(new int[]{m.x, m.y, m.z});
				}
			} catch (Exception x) {
			}
			return null;
		}
	}
}
