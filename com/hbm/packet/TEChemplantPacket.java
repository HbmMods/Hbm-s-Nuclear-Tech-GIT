package com.hbm.packet;

import com.hbm.tileentity.TileEntityMachineAssembler;
import com.hbm.tileentity.TileEntityMachineChemplant;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

public class TEChemplantPacket implements IMessage {

	int x;
	int y;
	int z;
	float spin;
	boolean isProgressing;

	public TEChemplantPacket()
	{
		
	}

	public TEChemplantPacket(int x, int y, int z, float spin, boolean isProgressing)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.spin = spin;
		this.isProgressing = isProgressing;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		spin = buf.readFloat();
		isProgressing = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeFloat(spin);
		buf.writeBoolean(isProgressing);
	}

	public static class Handler implements IMessageHandler<TEChemplantPacket, IMessage> {
		
		@Override
		public IMessage onMessage(TEChemplantPacket m, MessageContext ctx) {
			TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(m.x, m.y, m.z);

			if (te != null && te instanceof TileEntityMachineChemplant) {
					
				TileEntityMachineChemplant gen = (TileEntityMachineChemplant) te;
				gen.rotation = m.spin;
				gen.isProgressing = m.isProgressing;
			}
			return null;
		}
	}
}
