package com.hbm.packet.toserver;

import java.io.IOException;

import com.hbm.interfaces.IControlReceiver;
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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;

public class NBTControlPacket implements IMessage, IDiscardablePacket {

	private static final int MAX_PACKET_BYTES = 64 * 1024;

	PacketBuffer buffer;
	int x;
	int y;
	int z;

	public NBTControlPacket() { }

	public NBTControlPacket(NBTTagCompound nbt, int x, int y, int z) {

		this.buffer = new PacketBuffer(Unpooled.buffer());
		this.x = x;
		this.y = y;
		this.z = z;

		try {
			buffer.writeNBTTagCompoundToBuffer(nbt);
			if(buffer.readableBytes() > MAX_PACKET_BYTES) {
				discard();
				throw new EncoderException("NBT control packet exceeds " + MAX_PACKET_BYTES + " bytes");
			}
		} catch (IOException e) {
			discard();
			throw new EncoderException("Failed to encode NBT control packet", e);
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {

		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();

		if(buf.readableBytes() > MAX_PACKET_BYTES) throw new DecoderException("NBT control packet exceeds " + MAX_PACKET_BYTES + " bytes");
		if(buffer == null) buffer = new PacketBuffer(Unpooled.buffer(Math.min(buf.readableBytes(), MAX_PACKET_BYTES)));

		buffer.writeBytes(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {

		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);

		if (buffer == null) buffer = new PacketBuffer(Unpooled.buffer());

		buf.writeBytes(buffer);
	}

	public static class Handler implements IMessageHandler<NBTControlPacket, IMessage> {

		@Override
		public IMessage onMessage(NBTControlPacket m, MessageContext ctx) {

			EntityPlayer p = ctx.getServerHandler().playerEntity;

			if(p == null || p.worldObj == null || !PacketSecurity.allow(p, "nbt_control", 100)) {
				m.discard();
				return null;
			}
			if(m.y < 0 || m.y >= p.worldObj.getHeight()
					|| p.getDistanceSq(m.x + 0.5D, m.y + 0.5D, m.z + 0.5D) > PacketSecurity.MAX_TILE_DISTANCE_SQ) {
				m.discard();
				return null;
			}

			TileEntity te = p.worldObj.getTileEntity(m.x, m.y, m.z);
			if(!PacketSecurity.canAccessTile(p, te)) {
				m.discard();
				return null;
			}

			try {

				NBTTagCompound nbt = m.buffer.readNBTTagCompoundFromBuffer();

				if(nbt != null) {
					if(te instanceof IControlReceiver) {

						IControlReceiver tile = (IControlReceiver)te;

						if(tile.hasPermission(p)) {
							tile.receiveControl(p, nbt);
							tile.receiveControl(nbt);
						}
					}
				}

			} catch (Exception e) {
				MainRegistry.logger.warn("Rejected malformed NBT control packet from " + p.getCommandSenderName(), e);
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
