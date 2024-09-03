package com.hbm.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;

public class BufferUtil {

	private static final Charset CHARSET = StandardCharsets.UTF_8;
	
	// Writes a string to a byte buffer by encoding the length and raw bytes
	public static void writeString(ByteBuf buf, String value) {
		if(value == null) {
			buf.writeInt(-1);
			return;
		}

		buf.writeInt(value.length());
		buf.writeBytes(value.getBytes(CHARSET));
	}

	// Reads a string from a byte buffer via the written length and raw bytes
	public static String readString(ByteBuf buf) {
		final int count = buf.readInt();
		if(count < 0) return null;

		final byte[] bytes = new byte[count];
		buf.readBytes(bytes);

		return new String(bytes, CHARSET);
	}

	/**
	 * Writes the ItemStack to the buffer.
	 */
	public static void writeItemStack(ByteBuf buf, ItemStack item) {
		if (item == null)
			buf.writeShort(-1);
		else {
			buf.writeShort(Item.getIdFromItem(item.getItem()));
			buf.writeByte(item.stackSize);
			buf.writeShort(item.getItemDamage());
			NBTTagCompound nbtTagCompound = null;

			if (item.getItem().isDamageable() || item.getItem().getShareTag())
				nbtTagCompound = item.stackTagCompound;

			if(nbtTagCompound != null) {
				byte[] nbtData = new byte[0];
				try {
					nbtData = CompressedStreamTools.compress(nbtTagCompound);
				} catch(IOException e) {
					e.printStackTrace();
				}
				buf.writeShort((short) nbtData.length);
				buf.writeBytes(nbtData);
			} else {
				buf.writeShort(-1);
			}
		}
	}

	/**
	 * Reads an ItemStack from a buffer
	 */
	public static ItemStack readItemStack(ByteBuf buf) {
		ItemStack item = null;
		short id = buf.readShort();

		if (id >= 0) {
			byte quantity = buf.readByte();
			short meta = buf.readShort();
			item = new ItemStack(Item.getItemById(id), quantity, meta);

			short nbtLength = buf.readByte();

			byte[] tags = new byte[nbtLength];
			buf.readBytes(tags);
			try {
				item.stackTagCompound = CompressedStreamTools.func_152457_a(tags, new NBTSizeTracker(2097152L));
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		return item;
	}
}