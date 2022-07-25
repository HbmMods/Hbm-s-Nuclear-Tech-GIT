package com.hbm.packet;

import com.hbm.tileentity.machine.TileEntityMachineTurbofan;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

public class TETurbofanPacket implements IMessage {

	int x;
	int y;
	int z;
	int spin;
	boolean isRunning;

	public TETurbofanPacket()
	{
		
	}

	public TETurbofanPacket(int x, int y, int z, int spin, boolean isRunning)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.spin = spin;
		this.isRunning = isRunning;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		spin = buf.readInt();
		isRunning = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(spin);
		buf.writeBoolean(isRunning);
	}

	public static class Handler implements IMessageHandler<TETurbofanPacket, IMessage> {
		
		@Override
		public IMessage onMessage(TETurbofanPacket m, MessageContext ctx) {
			TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(m.x, m.y, m.z);

			if (te != null && te instanceof TileEntityMachineTurbofan) {
					
				TileEntityMachineTurbofan gen = (TileEntityMachineTurbofan) te;
				gen.spin = m.spin;
				gen.wasOn = m.isRunning;
			}
			return null;
		}
	}
}
