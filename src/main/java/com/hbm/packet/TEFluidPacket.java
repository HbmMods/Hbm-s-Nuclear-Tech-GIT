package com.hbm.packet;

import java.util.Arrays;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IFluidContainer;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

public class TEFluidPacket implements IMessage {

	int x;
	int y;
	int z;
	int fill;
	int index;
	int type;

	public TEFluidPacket()
	{
		
	}

	public TEFluidPacket(int x, int y, int z, int fill, int index, FluidType type)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.fill = fill;
		this.index = index;
		this.type = Arrays.asList(FluidType.values()).indexOf(type);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		fill = buf.readInt();
		index = buf.readInt();
		type = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(fill);
		buf.writeInt(index);
		buf.writeInt(type);
	}

	public static class Handler implements IMessageHandler<TEFluidPacket, IMessage> {
		
		@Override
		public IMessage onMessage(TEFluidPacket m, MessageContext ctx) {
			try{
			TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(m.x, m.y, m.z);

			if (te != null && te instanceof IFluidContainer) {
					
				IFluidContainer gen = (IFluidContainer) te;
				gen.setFillstate(m.fill, m.index);
				gen.setType(FluidType.getEnum(m.type), m.index);
			}
			} catch(Exception x) { }
			return null;
		}
	}
}
