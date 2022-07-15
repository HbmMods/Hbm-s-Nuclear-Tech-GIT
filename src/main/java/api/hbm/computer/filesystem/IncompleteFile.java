package api.hbm.computer.filesystem;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.hbm.main.DeserializationException;

import io.netty.buffer.ByteBuf;

public final class IncompleteFile extends FileBase
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3114466332681503506L;
	public final String fileType;
	public final long completeSize;
	private long currentSize;
	public IncompleteFile(Class<? extends FileBase> clazz, long fullSize)
	{
		this(clazz.getSimpleName(), fullSize);
	}
	
	public IncompleteFile(String type, long fullSize)
	{
		fileType = type;
		completeSize = fullSize;
	}

	public IncompleteFile writeTo(long amount)
	{
		currentSize += amount;
		return this;
	}
	
	public boolean isComplete()
	{
		return completeSize <= currentSize;
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
	public long getSize()
	{
		return currentSize;
	}
	
	@Override
	public byte[] serialize()
	{
		final ByteBuf buf = alloc.get();
		final byte[] stringBytes = fileType.getBytes();
		buf.writeByte(stringBytes.length);
		buf.writeBytes(stringBytes);
		buf.writeLong(completeSize);
		buf.writeLong(currentSize);
		return buf.array();
	}

	@Override
	public IncompleteFile deserialize(byte[] bytes) throws DeserializationException
	{
		try
		{
			final ByteBuf buf = allocCopy.apply(bytes);
			final byte[] stringBytes = new byte[buf.readByte()];
			buf.readBytes(stringBytes);
			final long completeSize = buf.readLong(), currentSize = buf.readLong();
			return new IncompleteFile(new String(stringBytes), completeSize).writeTo(currentSize);
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new DeserializationException(e);
		}
	}

	@Override
	public int hashCode()
	{
		return fileType.hashCode() * 47 * Long.hashCode(completeSize) * 31 * Long.hashCode(currentSize);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null || !(obj instanceof IncompleteFile))
			return false;
		
		final IncompleteFile test = (IncompleteFile) obj;
		return fileType.equals(test.fileType) && completeSize == test.completeSize && currentSize == test.currentSize;
	}

	@Override
	public IncompleteFile clone()
	{
		return new IncompleteFile(fileType, completeSize).writeTo(currentSize);
	}

}
