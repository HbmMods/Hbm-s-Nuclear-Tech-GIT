package com.hbm.packet;

import com.hbm.tileentity.machine.TileEntityMachineMiningDrill;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

public class TEDrillPacket implements IMessage {

	int x;
	int y;
	int z;
	float spin;
	float torque;

	public TEDrillPacket()
	{
		
	}

	public TEDrillPacket(int x, int y, int z, float spin, float torque)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.spin = spin;
		this.torque = torque;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		spin = buf.readFloat();
		torque = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeFloat(spin);
		buf.writeFloat(torque);
	}

	public static class Handler implements IMessageHandler<TEDrillPacket, IMessage> {
		
		@Override
		public IMessage onMessage(TEDrillPacket m, MessageContext ctx) {
			TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(m.x, m.y, m.z);

			if (te != null && te instanceof TileEntityMachineMiningDrill) {
					
				TileEntityMachineMiningDrill gen = (TileEntityMachineMiningDrill) te;
				gen.rotation = m.spin;
				gen.torque = m.torque;
			}
			return null;
		}
	}
}
