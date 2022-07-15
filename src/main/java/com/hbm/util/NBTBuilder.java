package com.hbm.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.hbm.main.DeserializationException;

import api.hbm.serialization.ISerializable;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class NBTBuilder implements ISerializable<NBTBuilder>, Iterable<NBTBase>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 198483453531676699L;
	private final NBTTagCompound nbt;
	public NBTBuilder()
	{
		nbt = new NBTTagCompound();
	}
	public NBTBuilder(NBTTagCompound nbt)
	{
		this.nbt = (NBTTagCompound) nbt.copy();
	}
	public NBTBuilder addTag(String key, NBTBase value)
	{
		nbt.setTag(key, value);
		return this;
	}
	public NBTBuilder addByte(String key, byte value)
	{
		nbt.setByte(key, value);
		return this;
	}
	public NBTBuilder addShort(String key, short value)
	{
		nbt.setShort(key, value);
		return this;
	}
	public NBTBuilder addInt(String key, int value)
	{
		nbt.setInteger(key, value);
		return this;
	}
	public NBTBuilder addLong(String key, long value)
	{
		nbt.setLong(key, value);
		return this;
	}
	public NBTBuilder addFloat(String key, float value)
	{
		nbt.setFloat(key, value);
		return this;
	}
	public NBTBuilder addDouble(String key, double value)
	{
		nbt.setDouble(key, value);
		return this;
	}
	public NBTBuilder addString(String key, String value)
	{
		nbt.setString(key, value);
		return this;
	}
	public NBTBuilder addByteArray(String key, byte[] value)
	{
		nbt.setByteArray(key, value);
		return this;
	}
	public NBTBuilder addIntArray(String key, int[] value)
	{
		nbt.setIntArray(key, value);
		return this;
	}
	public NBTBuilder addBoolean(String key, boolean value)
	{
		nbt.setBoolean(key, value);
		return this;
	}
	public NBTBase getTag(String key)
	{
		return nbt.getTag(key);
	}
	public boolean hasKey(String key)
	{
		return nbt.hasKey(key);
	}
	public boolean hasKey(String key, int type)
	{
		return nbt.hasKey(key, type);
	}
	public byte getByte(String key)
	{
		return nbt.getByte(key);
	}
	public short getShort(String key)
	{
		return nbt.getShort(key);
	}
	public int getInt(String key)
	{
		return nbt.getInteger(key);
	}
	public long getLong(String key)
	{
		return nbt.getLong(key);
	}
	public float getFloat(String key)
	{
		return nbt.getFloat(key);
	}
	public double getDouble(String key)
	{
		return nbt.getDouble(key);
	}
	public String getString(String key)
	{
		return nbt.getString(key);
	}
	public byte[] getByteArray(String key)
	{
		return nbt.getByteArray(key);
	}
	public int[] getIntArray(String key)
	{
		return nbt.getIntArray(key);
	}
	public NBTTagCompound getCompoundTag(String key)
	{
		return nbt.getCompoundTag(key);
	}
	public NBTTagList getTagList(String key, int type)
	{
		return nbt.getTagList(key, type);
	}
	public boolean getBoolean(String key)
	{
		return nbt.getBoolean(key);
	}
	public void removeTag(String key)
	{
		nbt.removeTag(key);
	}
	public boolean hasNoTags()
	{
		return nbt.hasNoTags();
	}
	public NBTTagCompound construct()
	{
		return nbt;
	}
	
	public Set<String> getKeys()
	{
		return ImmutableSet.copyOf(nbt.func_150296_c());
	}
	
	public NBTBase[] getTags()
	{
		final Set<String> keys = new HashSet<String>(getKeys());
		final NBTBase[] tags = new NBTBase[keys.size()];
		int i = 0;
		final Iterator<String> iterator = keys.iterator();
		while (iterator.hasNext())
		{
			tags[i] = nbt.getTag(iterator.next());
			i++;
			iterator.remove();
		}
		return tags;
	}
	
	public Map<String, NBTBase> decompileNBT()
	{
		final Map<String, NBTBase> map = new HashMap<String, NBTBase>();
		final Set<String> keys = new HashSet<String>(getKeys());
		final Iterator<String> iterator = keys.iterator();
		while (iterator.hasNext())
		{
			map.put(iterator.next(), nbt.getTag(iterator.next()));
			iterator.remove();
		}
		return ImmutableMap.copyOf(map);
	}
	
	@Override
	public NBTBuilder clone()
	{
		return new NBTBuilder(nbt);
	}
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		else if (obj instanceof NBTBuilder)
		{
			final NBTBuilder b = (NBTBuilder) obj;
			return b.construct().equals(nbt);
		}
		else if (obj instanceof NBTTagCompound)
		{
			final NBTTagCompound t = (NBTTagCompound) obj;
			return t.equals(nbt);
		}
		else
			return false;
	}
	@Override
	public int hashCode()
	{
		return nbt.hashCode();
	}
	@Override
	public String toString()
	{
		return nbt.toString();
	}
	
	public void removeIf(Predicate<NBTBase> predicate)
	{
		for (String key : getKeys())
			if (predicate.test(getTag(key)))
				removeTag(key);
	}
	
	@Override
	public NBTBuilder deserialize(byte[] bytes) throws DeserializationException
	{
		return new NBTBuilder(NBTSerialization.COMPOUND_DESERIALIZER.deserialize(bytes));
	}
	@Override
	public byte[] serialize()
	{
		return NBTSerialization.serializeCompound(nbt);
	}
	@Override
	public Iterator<NBTBase> iterator()
	{
		return new NBTIterator();
	}
	
	protected class NBTIterator implements Iterator<NBTBase>
	{
		private final Iterator<String> iterator;
		public NBTIterator()
		{
			iterator = getKeys().iterator();
		}
		@Override
		public boolean hasNext()
		{
			return iterator.hasNext();
		}

		@Override
		public NBTBase next()
		{
			return getTag(iterator.next());
		}
	}
}
