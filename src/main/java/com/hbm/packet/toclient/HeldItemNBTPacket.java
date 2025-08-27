package com.hbm.packet.toclient;

import com.hbm.util.BufferUtil;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class HeldItemNBTPacket implements IMessage {
	
	private ItemStack stack;
	
	public HeldItemNBTPacket() { }
	
	public HeldItemNBTPacket(ItemStack stack) {
		this.stack = stack;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeShort(Item.getIdFromItem(stack.getItem()));
		buf.writeByte(stack.stackSize);
		buf.writeShort(stack.getItemDamage());
		NBTTagCompound nbtTagCompound = null;
		nbtTagCompound = stack.stackTagCompound;
		BufferUtil.writeNBT(buf, nbtTagCompound);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		short id = buf.readShort();
		if(id >= 0) {
			byte quantity = buf.readByte();
			short meta = buf.readShort();
			stack = new ItemStack(Item.getItemById(id), quantity, meta);
			stack.stackTagCompound = BufferUtil.readNBT(buf);
		}
	}

	public static class Handler implements IMessageHandler<HeldItemNBTPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(HeldItemNBTPacket m, MessageContext ctx) {
			try {
				EntityPlayer player = Minecraft.getMinecraft().thePlayer;
				if(m.stack == null) return null;
				
				ItemStack held = player.getHeldItem();
				if(held == null) return null;
				if(held.getItem() != m.stack.getItem()) return null;
				if(held.getItemDamage() != m.stack.getItemDamage()) return null;
				
				held.stackTagCompound = m.stack.stackTagCompound;

			} catch(Exception x) { } finally { }
			return null;
		}
	}
}
