package com.hbm.packet;

import com.hbm.tileentity.machine.TileEntityMachineAssembler;
import com.hbm.tileentity.machine.TileEntityMachinePumpjack;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

public class TEPumpjackPacket implements IMessage {

	int x;
	int y;
	int z;
	int spin;
	boolean progress;

	public TEPumpjackPacket()
	{
		
	}

	public TEPumpjackPacket(int x, int y, int z, int spin, boolean bool)
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
		spin = buf.readInt();
		progress = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(spin);
		buf.writeBoolean(progress);
	}

	public static class Handler implements IMessageHandler<TEPumpjackPacket, IMessage> {
		
		@Override
		public IMessage onMessage(TEPumpjackPacket m, MessageContext ctx) {
			TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(m.x, m.y, m.z);

			if (te != null && te instanceof TileEntityMachinePumpjack) {
					
				TileEntityMachinePumpjack gen = (TileEntityMachinePumpjack) te;
				gen.rotation = m.spin;
				gen.isProgressing = m.progress;
			}
			return null;
		}
	}
}
