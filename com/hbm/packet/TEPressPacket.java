package com.hbm.packet;

import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityMachinePress;
import com.hbm.tileentity.machine.TileEntityMachineTurbofan;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TEPressPacket implements IMessage {

	int x;
	int y;
	int z;
	int item;
	int meta;
	int progress;

	public TEPressPacket()
	{
		
	}

	public TEPressPacket(int x, int y, int z, ItemStack stack, int progress)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.item = 0;
		this.meta = 0;
		if(stack != null) {
			this.item = Item.getIdFromItem(stack.getItem());
			this.meta = stack.getItemDamage();
		}
		this.progress = progress;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		item = buf.readInt();
		meta = buf.readInt();
		progress = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(item);
		buf.writeInt(meta);
		buf.writeInt(progress);
	}

	public static class Handler implements IMessageHandler<TEPressPacket, IMessage> {
		
		@Override
		public IMessage onMessage(TEPressPacket m, MessageContext ctx) {
			TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(m.x, m.y, m.z);

			if (te != null && te instanceof TileEntityMachinePress) {
					
				TileEntityMachinePress gen = (TileEntityMachinePress) te;
				gen.item = m.item;
				gen.meta = m.meta;
				gen.progress = m.progress;
			}
			return null;
		}
	}
}
