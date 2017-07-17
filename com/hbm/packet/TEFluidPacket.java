package com.hbm.packet;

import java.util.ArrayList;
import java.util.List;

import com.hbm.interfaces.IFluidContainer;
import com.hbm.tileentity.TileEntityMachineAssembler;
import com.hbm.tileentity.TileEntityMachineIGenerator;
import com.hbm.tileentity.TileEntityMachineMiningDrill;
import com.hbm.tileentity.TileEntityPylonRedWire;

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

	public TEFluidPacket()
	{
		
	}

	public TEFluidPacket(int x, int y, int z, int fill, int index)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.fill = fill;
		this.index = index;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		fill = buf.readInt();
		index = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(fill);
		buf.writeInt(index);
	}

	public static class Handler implements IMessageHandler<TEFluidPacket, IMessage> {
		
		@Override
		public IMessage onMessage(TEFluidPacket m, MessageContext ctx) {
			TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(m.x, m.y, m.z);

			if (te != null && te instanceof IFluidContainer) {
					
				IFluidContainer gen = (IFluidContainer) te;
				gen.setFillstate(m.fill, m.index);
			}
			return null;
		}
	}
}
