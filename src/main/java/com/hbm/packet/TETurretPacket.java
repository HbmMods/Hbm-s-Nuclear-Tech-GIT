package com.hbm.packet;

import com.hbm.tileentity.bomb.TileEntityTurretBase;
import com.hbm.tileentity.machine.TileEntityStructureMarker;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

public class TETurretPacket implements IMessage {

	int x;
	int y;
	int z;
	double yaw;
	double pitch;

	public TETurretPacket()
	{
		
	}

	public TETurretPacket(int x, int y, int z, double yaw, double pitch)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		yaw = buf.readDouble();
		pitch = buf.readDouble();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeDouble(yaw);
		buf.writeDouble(pitch);
	}

	public static class Handler implements IMessageHandler<TETurretPacket, IMessage> {
		
		@Override
		public IMessage onMessage(TETurretPacket m, MessageContext ctx) {
			try {
			TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(m.x, m.y, m.z);

			if (te != null && te instanceof TileEntityTurretBase) {
					
				TileEntityTurretBase turret = (TileEntityTurretBase) te;
				turret.rotationYaw = m.yaw;
				turret.rotationPitch = m.pitch;
			}
			} catch(Exception ex) {
				
			}
			return null;
		}
	}
}
