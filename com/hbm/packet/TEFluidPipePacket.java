package com.hbm.packet;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.tileentity.TileEntityFluidDuct;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

public class TEFluidPipePacket implements IMessage {

	int x;
	int y;
	int z;
	FluidType type;

	public TEFluidPipePacket()
	{
		
	}

	public TEFluidPipePacket(int x, int y, int z, FluidType type)
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
		type = FluidType.getEnum(buf.readInt());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
        for (int i = 0; i < FluidType.values().length; ++i)
        {
            if(FluidType.values()[i] == type) {
            	buf.writeInt(i);
            	break;
            }
        }
	}

	public static class Handler implements IMessageHandler<TEFluidPipePacket, IMessage> {
		
		@Override
		public IMessage onMessage(TEFluidPipePacket m, MessageContext ctx) {
			TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(m.x, m.y, m.z);

			if (te != null && te instanceof TileEntityFluidDuct) {
					
				TileEntityFluidDuct duct = (TileEntityFluidDuct) te;
				duct.type = m.type;
			}
			return null;
		}
	}
}
