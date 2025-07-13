package com.hbm.util;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class BufferUtil {

	private static final Charset CHARSET = StandardCharsets.UTF_8;

	// Writes a string to a byte buffer by encoding the length and raw bytes
	public static void writeString(ByteBuf buf, String value) {
		if(value == null) {
			buf.writeInt(-1);
			return;
		}

		buf.writeInt(value.getBytes(CHARSET).length);
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
	 * Writes an integer array to a buffer.
	 */
	public static void writeIntArray(ByteBuf buf, int[] array) {
		buf.writeInt(array.length);
		for (int value : array) {
			buf.writeInt(value);
		}
	}

	/**
	 * Reads an integer array from a buffer.
	 */
	public static int[] readIntArray(ByteBuf buf) {
		int length = buf.readInt();

		int[] array = new int[length];

		for (int i = 0; i < length; i++) {
			array[i] = buf.readInt();
		}

		return array;
	}

	/**
	 * Writes a vector to a buffer.
	 */
	public static void writeVec3(ByteBuf buf, Vec3 vector) {
		buf.writeBoolean(vector != null);
		if(vector == null) return;
		buf.writeDouble(vector.xCoord);
		buf.writeDouble(vector.yCoord);
		buf.writeDouble(vector.zCoord);
	}

	/**
	 * Reads a vector from a buffer.
	 */
	public static Vec3 readVec3(ByteBuf buf) {
		boolean vectorExists = buf.readBoolean();
		if(!vectorExists) {
			return null;
		}
		double x = buf.readDouble();
		double y = buf.readDouble();
		double z = buf.readDouble();

		return Vec3.createVectorHelper(x, y, z);
	}

	/**
	 * Writes a NBTTagCompound to a buffer.
	 */
	public static void writeNBT(ByteBuf buf, NBTTagCompound compound) {
		if(compound != null) {
			byte[] nbtData = new byte[0];
			try {
				nbtData = CompressedStreamTools.compress(compound);
			} catch(IOException e) {
				e.printStackTrace();
			}
			buf.writeShort((short) nbtData.length);
			buf.writeBytes(nbtData);
		} else
			buf.writeShort(-1);
	}

	/**
	 * Reads a NBTTagCompound from a buffer.
	 */
	public static NBTTagCompound readNBT(ByteBuf buf) {
		short nbtLength = buf.readShort();

		if (nbtLength == -1) // check if no compound was even given.
			return new NBTTagCompound();
		byte[] tags = new byte[nbtLength];
		buf.readBytes(tags);
		try {
			return CompressedStreamTools.func_152457_a(tags, new NBTSizeTracker(2097152L));
		} catch(IOException e) {
			e.printStackTrace();
		}
		return new NBTTagCompound();
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

			writeNBT(buf, nbtTagCompound);
		}
	}

	/**
	 * Reads an ItemStack from a buffer.
	 */
	public static ItemStack readItemStack(ByteBuf buf) {
		ItemStack item = null;
		short id = buf.readShort();

		if (id >= 0) {
			byte quantity = buf.readByte();
			short meta = buf.readShort();
			item = new ItemStack(Item.getItemById(id), quantity, meta);
			item.stackTagCompound = readNBT(buf);
		}
		return item;
	}
}
