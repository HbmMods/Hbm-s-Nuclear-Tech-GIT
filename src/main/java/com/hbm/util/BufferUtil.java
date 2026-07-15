package com.hbm.util;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
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
	public static final int MAX_STRING_BYTES = 1024 * 1024;
	public static final int MAX_INT_ARRAY_LENGTH = 1024 * 1024;
	public static final int MAX_COMPRESSED_NBT_BYTES = 0xFFFE;

	// Writes a string to a byte buffer by encoding the length and raw bytes
	public static void writeString(ByteBuf buf, String value) {
		if(value == null) {
			buf.writeInt(-1);
			return;
		}

		byte[] bytes = value.getBytes(CHARSET);
		if(bytes.length > MAX_STRING_BYTES) throw new EncoderException("String exceeds " + MAX_STRING_BYTES + " encoded bytes");
		buf.writeInt(bytes.length);
		buf.writeBytes(bytes);
	}

	// Reads a string from a byte buffer via the written length and raw bytes
	public static String readString(ByteBuf buf) {
		return readString(buf, MAX_STRING_BYTES);
	}

	public static String readString(ByteBuf buf, int maxBytes) {
		if(buf.readableBytes() < 4) throw new DecoderException("Missing string length");
		final int count = buf.readInt();
		if(count == -1) return null;
		if(count < 0) throw new DecoderException("Invalid string length " + count);
		if(count > maxBytes) throw new DecoderException("String length " + count + " exceeds " + maxBytes);
		if(count > buf.readableBytes()) throw new DecoderException("String length " + count + " exceeds remaining packet bytes " + buf.readableBytes());

		final byte[] bytes = new byte[count];
		buf.readBytes(bytes);

		return new String(bytes, CHARSET);
	}

	/**
	 * Writes an integer array to a buffer.
	 */
	public static void writeIntArray(ByteBuf buf, int[] array) {
		if(array == null) throw new EncoderException("Cannot encode a null int array");
		if(array.length > MAX_INT_ARRAY_LENGTH) throw new EncoderException("Integer array exceeds " + MAX_INT_ARRAY_LENGTH + " elements");
		buf.writeInt(array.length);
		for (int value : array) {
			buf.writeInt(value);
		}
	}

	/**
	 * Reads an integer array from a buffer.
	 */
	public static int[] readIntArray(ByteBuf buf) {
		return readIntArray(buf, MAX_INT_ARRAY_LENGTH);
	}

	public static int[] readIntArray(ByteBuf buf, int maxLength) {
		if(buf.readableBytes() < 4) throw new DecoderException("Missing integer array length");
		int length = buf.readInt();
		if(length < 0 || length > maxLength) throw new DecoderException("Invalid integer array length " + length + " (max " + maxLength + ")");
		if((long) length * 4L > buf.readableBytes()) throw new DecoderException("Integer array exceeds remaining packet bytes");

		int[] array = new int[length];

		for (int i = 0; i < length; i++) {
			array[i] = buf.readInt();
		}

		return array;
	}

	public static byte[] readByteArray(ByteBuf buf, int maxLength) {
		if(buf.readableBytes() < 4) throw new DecoderException("Missing byte array length");
		int length = buf.readInt();
		if(length < 0 || length > maxLength || length > buf.readableBytes()) throw new DecoderException("Invalid byte array length " + length + " (max " + maxLength + ")");
		byte[] array = new byte[length];
		buf.readBytes(array);
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
			byte[] nbtData;
			try {
				nbtData = CompressedStreamTools.compress(compound);
			} catch(IOException e) {
				throw new EncoderException("Failed to compress NBT", e);
			}
			if(nbtData.length > MAX_COMPRESSED_NBT_BYTES) throw new EncoderException("Compressed NBT exceeds " + MAX_COMPRESSED_NBT_BYTES + " bytes");
			buf.writeShort(nbtData.length);
			buf.writeBytes(nbtData);
		} else
			buf.writeShort(-1);
	}

	/**
	 * Reads a NBTTagCompound from a buffer.
	 */
	public static NBTTagCompound readNBT(ByteBuf buf) {
		if(buf.readableBytes() < 2) throw new DecoderException("Missing NBT length");
		int nbtLength = buf.readUnsignedShort();

		if(nbtLength == 0xFFFF)
			return new NBTTagCompound();
		if(nbtLength > MAX_COMPRESSED_NBT_BYTES || nbtLength > buf.readableBytes()) throw new DecoderException("Invalid compressed NBT length " + nbtLength);
		byte[] tags = new byte[nbtLength];
		buf.readBytes(tags);
		try {
			return CompressedStreamTools.func_152457_a(tags, new NBTSizeTracker(2097152L));
		} catch(IOException e) {
			throw new DecoderException("Failed to decode compressed NBT", e);
		}
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
			Item stackItem = Item.getItemById(id);
			if(stackItem == null || quantity <= 0) throw new DecoderException("Invalid item stack id/count: " + id + "/" + quantity);
			item = new ItemStack(stackItem, quantity, meta);
			item.stackTagCompound = readNBT(buf);
		}
		return item;
	}
}
