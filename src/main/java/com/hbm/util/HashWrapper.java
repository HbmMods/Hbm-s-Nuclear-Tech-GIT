package com.hbm.util;

import java.util.function.Function;
import java.util.function.Predicate;

public class HashWrapper<T> implements Wrapper<T>
{
	private final T storedObj;
	private final Function<T, Integer> hashFunction;
	private final Predicate<T> equalFunction;
	public HashWrapper(T storedObj, Function<T, Integer> hashFunction, Predicate<T> equalPredicate)
	{
		this.storedObj = storedObj;
		this.hashFunction = hashFunction;
		this.equalFunction = equalPredicate;
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
		return storedObj.toString();
	}
}
