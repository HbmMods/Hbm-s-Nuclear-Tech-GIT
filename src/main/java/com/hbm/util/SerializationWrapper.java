package com.hbm.util;

import java.util.function.Function;

import com.hbm.main.DeserializationException;

import api.hbm.serialization.Deserializer;
import api.hbm.serialization.ISerializable;

public class SerializationWrapper<T> implements Wrapper<T>, ISerializable<T>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5855157815810568096L;
	private final T storedObj;
	private transient final Function<T, byte[]> serializationFunction;
	private transient final Deserializer<T> deserializer;

	public SerializationWrapper(T storedObj, Function<T, byte[]> serializationFunction, Deserializer<T> deserializer)
	{
		this.storedObj = storedObj;
		this.serializationFunction = serializationFunction;
		this.deserializer = deserializer;
	}

	@Override
	public byte[] serialize()
	{
		return serializationFunction.apply(storedObj);
	}
	
	@Override
	public T deserialize(byte[] bytes) throws DeserializationException
	{
		return deserializer.deserialize(bytes);
	}
	
	@Override
	public T getStoredObj()
	{
		return storedObj;
	}

	@Override
	public boolean equals(Object obj)
	{
		return storedObj.equals(obj);
	}
	
	@Override
	public int hashCode()
	{
		return storedObj.hashCode();
	}
	
	@Override
	public String toString()
	{
		return storedObj.toString();
	}
}
