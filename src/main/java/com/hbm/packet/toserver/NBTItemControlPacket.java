package com.hbm.packet.toserver;

import java.io.IOException;

import com.hbm.items.IItemControlReceiver;
import com.hbm.packet.IDiscardablePacket;
import com.hbm.packet.PacketSecurity;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

public class NBTItemControlPacket implements IMessage, IDiscardablePacket {

	private static final int MAX_PACKET_BYTES = 32 * 1024;

	PacketBuffer buffer;

	public NBTItemControlPacket() { }

	public NBTItemControlPacket(NBTTagCompound nbt) {

		this.buffer = new PacketBuffer(Unpooled.buffer());

		try {
			buffer.writeNBTTagCompoundToBuffer(nbt);
			if(buffer.readableBytes() > MAX_PACKET_BYTES) {
				discard();
				throw new EncoderException("NBT item control packet exceeds " + MAX_PACKET_BYTES + " bytes");
			}

		} catch(IOException e) {
			discard();
			throw new EncoderException("Failed to encode item control packet", e);
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {

		if(buf.readableBytes() > MAX_PACKET_BYTES) throw new DecoderException("NBT item control packet exceeds " + MAX_PACKET_BYTES + " bytes");
		if(buffer == null) {
			buffer = new PacketBuffer(Unpooled.buffer(Math.min(buf.readableBytes(), MAX_PACKET_BYTES)));
		}
		buffer.writeBytes(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {

		if(buffer == null) {
			buffer = new PacketBuffer(Unpooled.buffer());
		}
		buf.writeBytes(buffer);
	}

	public static class Handler implements IMessageHandler<NBTItemControlPacket, IMessage> {

		@Override
		public IMessage onMessage(NBTItemControlPacket m, MessageContext ctx) {

			EntityPlayer p = ctx.getServerHandler().playerEntity;
			if(p == null || !PacketSecurity.allow(p, "nbt_item_control", 100)) {
				m.discard();
				return null;
			}

			try {

				NBTTagCompound nbt = m.buffer.readNBTTagCompoundFromBuffer();

				if(nbt != null) {
					ItemStack held = p.getHeldItem();

					if(held != null && held.getItem() instanceof IItemControlReceiver) {
						((IItemControlReceiver) held.getItem()).receiveControl(held, nbt);
					}
				}

			} catch (Exception e) {
				MainRegistry.logger.warn("Rejected malformed item control packet from " + p.getCommandSenderName(), e);
			} finally {
				m.discard();
			}

			return null;
		}
	}

	@Override
	public void discard() {
		if(buffer != null && buffer.refCnt() > 0) buffer.release();
	}
}
