package com.hbm.packet;

import com.hbm.interfaces.Spaghetti;
import com.hbm.items.ModItems;
import com.hbm.tileentity.bomb.TileEntityLaunchPad;
import com.hbm.tileentity.bomb.TileEntityLaunchPadPassenger;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TEPassengerPacket implements IMessage {

	int x;
	int y;
	int z;
	int type;

	public TEPassengerPacket() { }

	@Spaghetti("die")
	public TEPassengerPacket(int x, int y, int z, ItemStack stack) {
		
		this.x = x;
		this.y = y;
		this.z = z;
		this.type = 0;
		if(stack != null) {
			if(stack.getItem() == ModItems.passenger_carrier)
				type = 20;
			
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		type = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(type);
	}

	public static class Handler implements IMessageHandler<TEPassengerPacket, IMessage> {
		
		@Override
		public IMessage onMessage(TEPassengerPacket m, MessageContext ctx) {
			
			try {
				TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(m.x, m.y, m.z);
	
				if (te != null && te instanceof TileEntityLaunchPadPassenger) {
						
					TileEntityLaunchPadPassenger gen = (TileEntityLaunchPadPassenger)te;
					gen.state = m.type;
				}
			} catch(Exception e) { }
			
			return null;
		}
	}
}
