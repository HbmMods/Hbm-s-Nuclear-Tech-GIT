package com.hbm.util;

import static com.hbm.lib.Library.concatArrays;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.hbm.main.DeserializationException;

import api.hbm.serialization.Deserializer;
import api.hbm.serialization.ISerializable;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.*;

public class NBTSerialization
{
	public static final Deserializer<NBTTagCompound> COMPOUND_DESERIALIZER = NBTSerialization::deserializeTagCompound;
	public static final Deserializer<NBTTagList> LIST_DESERIALIZER = NBTSerialization::deserializeList;
	public static final Deserializer<NBTBase> BASE_DESERIALIZER = NBTSerialization::deserializeBase;
	public static byte[] serializeBase(NBTBase nbtBase)
	{
		switch (nbtBase.getId())
		{
			case 0: return new byte[] {0};
			case 1: return new byte[] {1, ((NBTTagByte) nbtBase).func_150290_f()};
			case 2:
				final short nbtShort = ((NBTTagShort) nbtBase).func_150289_e();
				return new byte[] {2, (byte) (nbtShort >>> 8), (byte) nbtShort};
			case 3:
				final int nbtInt = ((NBTTagInt) nbtBase).func_150287_d();
				return new byte[] {3, (byte) (nbtInt >>> 24), (byte) (nbtInt >>> 16), (byte) (nbtInt >>> 8), (byte) nbtInt};
			case 4:
				final long nbtLong = ((NBTTagLong) nbtBase).func_150291_c();
				return new byte[] {4, (byte) (nbtLong >>> 56), (byte) (nbtLong >>> 48), (byte) (nbtLong >>> 40), (byte) (nbtLong >>> 32), (byte) (nbtLong >>> 24), (byte) (nbtLong >>> 16), (byte) (nbtLong >>> 8), (byte) nbtLong};
			case 5:
				final int nbtFloat = Float.floatToRawIntBits(((NBTTagFloat) nbtBase).func_150288_h());
				return new byte[] {5, (byte) (nbtFloat >>> 24), (byte) (nbtFloat >>> 16), (byte) (nbtFloat >>> 8), (byte) nbtFloat};
			case 6:
				final long nbtDouble = Double.doubleToRawLongBits(((NBTTagDouble) nbtBase).func_150286_g());
				return new byte[] {6, (byte) (nbtDouble >>> 56), (byte) (nbtDouble >>> 48), (byte) (nbtDouble >>> 40), (byte) (nbtDouble >>> 32), (byte) (nbtDouble >>> 24), (byte) (nbtDouble >>> 16), (byte) (nbtDouble >>> 8), (byte) nbtDouble};
			case 7:
				final byte[] nbtBytes = ((NBTTagByteArray) nbtBase).func_150292_c();
				final int nbtBytesLen = nbtBytes.length;
				final byte[] nbtBytesHeader = {7, (byte) (nbtBytesLen >>> 24), (byte) (nbtBytesLen >>> 16), (byte) (nbtBytesLen >>> 8), (byte) nbtBytesLen};
				return concatArrays(nbtBytesHeader, nbtBytes);
			case 8:
				final byte[] nbtString = ((NBTTagString) nbtBase).func_150285_a_().getBytes();
				final int nbtStringLen = nbtString.length;
				final byte[] nbtStringHeader = {8, (byte) (nbtStringLen >>> 24), (byte) (nbtStringLen >>> 16), (byte) (nbtStringLen >>> 8), (byte) nbtStringLen};
				return concatArrays(nbtStringHeader, nbtString);
			case 9: return serializeList((NBTTagList) nbtBase);
			case 10: return serializeCompound((NBTTagCompound) nbtBase);
			case 11:
				final ByteBuf buf = ISerializable.alloc.get();
				buf.writeByte(11);
				final int[] ints = ((NBTTagIntArray) nbtBase).func_150302_c();
				buf.writeInt(ints.length);
				for (int i : ints)
					buf.writeInt(i);
			default: return new byte[0];
		}
	}
	
	public static byte[] serializeList(NBTTagList nbtTagList)
	{
		final ByteBuf buf = ISerializable.alloc.get();
		final ByteBuf dataBuf = ISerializable.alloc.get();
		final NBTTagList copy = (NBTTagList) nbtTagList.copy();
		buf.writeByte(9);
		buf.writeByte(copy.func_150303_d());
		buf.writeInt(copy.tagCount());
		for (int i = 0; i < nbtTagList.tagCount(); i++)
			dataBuf.writeBytes(serializeBase(copy.removeTag(i)));
		buf.writeInt(dataBuf.array().length + 6);
		buf.writeBytes(dataBuf);
		return buf.array();
	}
	
	public static byte[] serializeCompound(NBTTagCompound tagCompound)
	{
		final ByteBuf buf = ISerializable.alloc.get();
		final ByteBuf dataBuf = ISerializable.alloc.get();
		final Set<String> nameSet = ImmutableSet.copyOf(tagCompound.func_150296_c());
		int count = 0;
		for (String name : nameSet)
		{
			final byte[] nameBytes = name.getBytes();
			final byte[] nbtBytes = serializeBase(tagCompound.getTag(name));
			dataBuf.writeInt(name.length());
			dataBuf.writeInt(nbtBytes.length);
			dataBuf.writeBytes(nameBytes);
			dataBuf.writeBytes(nbtBytes);
			count++;
		}
		buf.writeInt(dataBuf.array().length);
		buf.writeInt(count);
		buf.writeBytes(dataBuf);
		return buf.array();
	}
	
	public static NBTBase deserializeBase(byte[] bytes) throws DeserializationException
	{
		try
		{
			final ByteBuf buf = ISerializable.allocCopy.apply(bytes);
			final byte type = buf.readByte();
			switch (type)
			{
				case 0: return new NBTTagEnd();
				case 1: return new NBTTagByte(buf.readByte());
				case 2: return new NBTTagShort(buf.readShort());
				case 3: return new NBTTagInt(buf.readInt());
				case 4: return new NBTTagLong(buf.readLong());
				case 5: return new NBTTagFloat(buf.readFloat());
				case 6: return new NBTTagDouble(buf.readDouble());
				case 7:
					final byte[] nbtBytes = new byte[buf.readInt()];
					buf.readBytes(nbtBytes);
					return new NBTTagByteArray(nbtBytes);
				case 8:
					final byte[] stringBytes = new byte[buf.readInt()];
					buf.readBytes(stringBytes);
					return new NBTTagString(new String(stringBytes));
				case 9: return deserializeList(bytes);
				case 10:
				case 11:
					final int[] nbtInts = new int[buf.readInt()];
					for (int i = 0; i < nbtInts.length; i++)
						nbtInts[i] = buf.readInt();
					return new NBTTagIntArray(nbtInts);
				default: return null;
			}
		} catch (Exception e)
		{
			throw new DeserializationException(e);
		}
	}
	
	public static NBTTagList deserializeList(byte[] bytes) throws DeserializationException
	{
		try
		{
			final ByteBuf buf = ISerializable.allocCopy.apply(bytes);
			buf.readByte();
			final byte type = buf.readByte();
			final int length = buf.readInt();
			final NBTTagList tagList = new NBTTagList();
			for (int i = 0; i < length; i++)
			{
				switch (type)
				{
					case 0: tagList.appendTag(new NBTTagEnd()); break;
					case 1: tagList.appendTag(new NBTTagByte(buf.readByte())); break;
					case 2: tagList.appendTag(new NBTTagShort(buf.readShort())); break;
					case 3: tagList.appendTag(new NBTTagInt(buf.readInt())); break;
					case 4: tagList.appendTag(new NBTTagLong(buf.readLong())); break;
					case 5: tagList.appendTag(new NBTTagFloat(buf.readFloat())); break;
					case 6: tagList.appendTag(new NBTTagDouble(buf.readDouble())); break;
					case 7:
						final byte[] nbtBytes = new byte[buf.readInt()];
						buf.readBytes(nbtBytes);
						tagList.appendTag(new NBTTagByteArray(nbtBytes));
						break;
					case 8:
						final byte[] nbtString = new byte[buf.readInt()];
						buf.readBytes(nbtString);
						tagList.appendTag(new NBTTagString(new String(nbtString)));
						break;
					case 9:
						final byte[] subListBytes = new byte[buf.getInt(buf.readerIndex() + 6)];
						buf.readBytes(subListBytes);
						tagList.appendTag(deserializeList(subListBytes));
						break;
					case 10:
						final byte[] compoundBytes = new byte[buf.getInt(buf.readerIndex())];
						buf.readBytes(compoundBytes);
						tagList.appendTag(deserializeTagCompound(compoundBytes));
						break;
					case 11:
						final int[] nbtInts = new int[buf.readInt()];
						for (int x = 0; x < nbtInts.length; x++)
							nbtInts[x] = buf.readInt();
						tagList.appendTag(new NBTTagIntArray(nbtInts));
						break;
					default: return tagList;
				}
			}
			return tagList;
		} catch (Exception e)
		{
			throw new DeserializationException(e);
		}
	}
	
	public static NBTTagCompound deserializeTagCompound(byte[] bytes) throws DeserializationException
	{
		try
		{
			final NBTBuilder nbtBuilder = new NBTBuilder();
			final ByteBuf buf = ISerializable.allocCopy.apply(bytes);
			buf.readInt();
			final int tagCount = buf.readInt();
			for (int i = 0; i < tagCount; i++)
			{
				final byte[] nameBytes = new byte[buf.readInt()];
				final byte[] nbtBytes = new byte[buf.readInt()];
				buf.readBytes(nameBytes);
				buf.readBytes(nbtBytes);
				nbtBuilder.addTag(new String(nameBytes), deserializeBase(nbtBytes));
			}
			return nbtBuilder.construct();
		} catch (Exception e)
		{
			throw new DeserializationException(e);
		}
	}
}
