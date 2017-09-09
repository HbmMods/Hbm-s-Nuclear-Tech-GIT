package com.hbm.packet;

import com.hbm.tileentity.machine.TileEntityMachineMiningDrill;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

public class TEDrillSoundPacket implements IMessage {

	int x;
	int y;
	int z;
	float spin;

	public TEDrillSoundPacket()
	{
		
	}

	public TEDrillSoundPacket(int x, int y, int z, float spin)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.spin = spin;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		spin = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeFloat(spin);
	}

	public static class Handler implements IMessageHandler<TEDrillSoundPacket, IMessage> {
		
		@Override
		public IMessage onMessage(TEDrillSoundPacket m, MessageContext ctx) {
			TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(m.x, m.y, m.z);

			if (te != null && te instanceof TileEntityMachineMiningDrill) {
					
				TileEntityMachineMiningDrill gen = (TileEntityMachineMiningDrill) te;
				gen.torque = m.spin;
			}
			return null;
		}
	}
}
