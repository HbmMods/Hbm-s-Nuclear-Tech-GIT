package com.hbm.packet;

import com.hbm.tileentity.machine.TileEntityMachineAssembler;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

public class TEAssemblerPacket implements IMessage {

	int x;
	int y;
	int z;
	float spin;
	boolean progress;

	public TEAssemblerPacket()
	{
		
	}

	public TEAssemblerPacket(int x, int y, int z, float spin, boolean bool)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.spin = spin;
		this.progress = bool;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		spin = buf.readFloat();
		progress = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeFloat(spin);
		buf.writeBoolean(progress);
	}

	public static class Handler implements IMessageHandler<TEAssemblerPacket, IMessage> {
		
		@Override
		public IMessage onMessage(TEAssemblerPacket m, MessageContext ctx) {
			TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(m.x, m.y, m.z);

			if (te != null && te instanceof TileEntityMachineAssembler) {
					
				TileEntityMachineAssembler gen = (TileEntityMachineAssembler) te;
				gen.rotation = m.spin;
				gen.isProgressing = m.progress;
			}
			return null;
		}
	}
}
