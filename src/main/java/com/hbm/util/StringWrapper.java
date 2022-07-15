package com.hbm.util;

import java.util.Objects;
import java.util.function.Function;

public class StringWrapper<T> implements Wrapper<T>
{
	private final T storedObj;
	private final Function<T, String> stringFunction;
	public StringWrapper(T storedObj, Function<T, String> stringFunction)
	{
		this.storedObj = storedObj;
		this.stringFunction = stringFunction;
	}
	
	@Override
	public T getStoredObj()
	{
		return storedObj;
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(storedObj);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!(obj instanceof StringWrapper))
			return false;
		final StringWrapper<T> other = (StringWrapper<T>) obj;
		return Objects.equals(storedObj, other.storedObj);
	}

	@Override
	public String toString()
	{
		return stringFunction.apply(storedObj);
	}
}
