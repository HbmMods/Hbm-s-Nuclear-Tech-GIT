package com.hbm.packet.toserver;

import java.io.IOException;

import com.hbm.items.IItemControlReceiver;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

public class NBTItemControlPacket implements IMessage {
	
	PacketBuffer buffer;

	public NBTItemControlPacket() { }

	public NBTItemControlPacket(NBTTagCompound nbt) {
		
		this.buffer = new PacketBuffer(Unpooled.buffer());
		
		try {
			buffer.writeNBTTagCompoundToBuffer(nbt);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		
		if (buffer == null) {
			buffer = new PacketBuffer(Unpooled.buffer());
		}
		buffer.writeBytes(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		
		if (buffer == null) {
			buffer = new PacketBuffer(Unpooled.buffer());
		}
		buf.writeBytes(buffer);
	}

	public static class Handler implements IMessageHandler<NBTItemControlPacket, IMessage> {
		
		@Override
		public IMessage onMessage(NBTItemControlPacket m, MessageContext ctx) {

			EntityPlayer p = ctx.getServerHandler().playerEntity;
			
			try {
				
				NBTTagCompound nbt = m.buffer.readNBTTagCompoundFromBuffer();
				
				if(nbt != null) {
					ItemStack held = p.getHeldItem();
					
					if(held != null && held.getItem() instanceof IItemControlReceiver) {
						((IItemControlReceiver) held.getItem()).receiveControl(held, nbt);
					}
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return null;
		}
	}
}
