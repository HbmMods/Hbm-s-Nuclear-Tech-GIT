package com.hbm.util;

public interface Wrapper<T>
{
	public T getStoredObj();
	@Override
	public boolean equals(Object obj);
	@Override
	public int hashCode();
	@Override
	public String toString();
}
