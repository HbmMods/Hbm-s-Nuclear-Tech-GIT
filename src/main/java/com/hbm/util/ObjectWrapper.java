package com.hbm.util;

import java.util.function.Function;
import java.util.function.Predicate;

public class ObjectWrapper<T> implements Wrapper<T>
{
	private final T storedObj;
	private final Function<T, String> stringFunction;
	private final Function<T, Integer> hashFunction;
	private final Predicate<T> equalFunction;
	public ObjectWrapper(
			T storedObj, Function<T, String> stringFunction, Function<T, Integer> hashFunction,
			Predicate<T> equalFunction
	)
	{
		this.storedObj = storedObj;
		this.stringFunction = stringFunction;
		this.hashFunction = hashFunction;
		this.equalFunction = equalFunction;
	}
	
	@Override
	public T getStoredObj()
	{
		return storedObj;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		if (!(obj.getClass().equals(storedObj.getClass())))
			return false;
		return equalFunction.test((T) obj);
	}
	
	@Override
	public int hashCode()
	{
		return hashFunction.apply(storedObj);
	}
	
	@Override
	public String toString()
	{
		return stringFunction.apply(storedObj);
	}
}
