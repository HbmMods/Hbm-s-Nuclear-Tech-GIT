package api.hbm.computer.filesystem;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.hbm.main.DeserializationException;

import io.netty.buffer.ByteBuf;

public final class UnknownFile extends FileBase
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4430074659417292512L;
	private final byte[] data;
	private final long size;
	public UnknownFile(byte[] bytes)
	{
		data = bytes;
		size = data.length;
	}
	
	public UnknownFile(byte[] bytes, long size)
	{
		data = bytes;
		this.size = size;
	}
	
	@Override
	protected void writeObject(ObjectOutputStream stream) throws IOException
	{
		stream.defaultWriteObject();
	}
	
	@Override
	protected void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException
	{
		stream.defaultReadObject();
	}

	@Override
	public byte[] serialize()
	{
		final ByteBuf buf = alloc.get();
		buf.writeLong(size);
		buf.writeBytes(data);
		return buf.array();
	}

	@Override
	public long getSize()
	{
		return size;
	}

	@Override
	public UnknownFile deserialize(byte[] bytes) throws DeserializationException
	{
		try
		{
			final ByteBuf buf = allocCopy.apply(bytes);
			final long dataSize = buf.readLong();
			final byte[] dataBytes = new byte[buf.capacity() - 8];
			buf.writeBytes(dataBytes);
			return new UnknownFile(dataBytes, dataSize);
		} catch (Exception e)
		{
			throw new DeserializationException(e);
		}
	}

	@Override
	public int hashCode()
	{
		return Long.hashCode(size) * 13 * data.hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null || !(obj instanceof UnknownFile))
			return false;
		
		final UnknownFile test = (UnknownFile) obj;
		if (data.length != test.data.length || size == test.size)
			return false;
		for (int i = 0; i < data.length; i++)
		{
			if (data[i] != test.data[i])
				return false;
		}
		
		return true;
	}

	@Override
	public UnknownFile clone()
	{
		return new UnknownFile(data, size);
	}

}
