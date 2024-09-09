package com.hbm.packet.toserver;

import com.hbm.items.ModItems;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemDesignatorPacket implements IMessage {

	//0: Add
	//1: Subtract
	//2: Set
	int operator;
	int value;
	int reference;

	public ItemDesignatorPacket()
	{
		
	}

	public ItemDesignatorPacket(int operator, int value, int reference)
	{
		this.operator = operator;
		this.value = value;
		this.reference = reference;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		operator = buf.readInt();
		value = buf.readInt();
		reference = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(operator);
		buf.writeInt(value);
		buf.writeInt(reference);
	}

	public static class Handler implements IMessageHandler<ItemDesignatorPacket, IMessage> {
		
		@Override
		public IMessage onMessage(ItemDesignatorPacket m, MessageContext ctx) {
			
			EntityPlayer p = ctx.getServerHandler().playerEntity;
			
			ItemStack stack = p.getHeldItem();
			
			if(stack != null && stack.getItem() == ModItems.designator_manual) {
				if(!stack.hasTagCompound())
					stack.stackTagCompound = new NBTTagCompound();
				int x = stack.stackTagCompound.getInteger("xCoord");
				int z = stack.stackTagCompound.getInteger("zCoord");
				
				int result = 0;

				if(m.operator == 0)
					result += m.value;
				if(m.operator == 1)
					result -= m.value;
				if(m.operator == 2) {
					if(m.reference == 0)
						stack.stackTagCompound.setInteger("xCoord", (int)Math.round(p.posX));
					else
						stack.stackTagCompound.setInteger("zCoord", (int)Math.round(p.posZ));
					return null;
				}
				
				if(m.reference == 0)
					stack.stackTagCompound.setInteger("xCoord", x + result);
				else
					stack.stackTagCompound.setInteger("zCoord", z + result);
			}
			
			return null;
		}
	}
}
