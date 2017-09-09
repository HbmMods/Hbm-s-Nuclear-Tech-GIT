package com.hbm.packet;

import com.hbm.tileentity.machine.TileEntityMachineIGenerator;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

public class TEIGeneratorPacket implements IMessage {

	int x;
	int y;
	int z;
	float spin;

	public TEIGeneratorPacket()
	{
		
	}

	public TEIGeneratorPacket(int x, int y, int z, float spin)
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

	public static class Handler implements IMessageHandler<TEIGeneratorPacket, IMessage> {
		
		@Override
		public IMessage onMessage(TEIGeneratorPacket m, MessageContext ctx) {
			TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(m.x, m.y, m.z);

			if (te != null && te instanceof TileEntityMachineIGenerator) {
					
				TileEntityMachineIGenerator gen = (TileEntityMachineIGenerator) te;
				gen.rotation = m.spin;
			}
			return null;
		}
	}
}
