package api.hbm.computer.filesystem;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.hbm.main.DeserializationException;

import api.hbm.serialization.ISerializable;
import api.hbm.serialization.SerializationRegistry;

public abstract class FileBase implements Cloneable, ISerializable<FileBase>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5309270065067336455L;
	public static final byte[] emptyBytes = new byte[0];
	public FileBase()
	{
		SerializationRegistry.register(this.getClass(), this);
	}
	
	public static FileBase deserializeFromRegistry(String type, byte[] bytes) throws DeserializationException
	{
		return (FileBase) (SerializationRegistry.registered(type) ? SerializationRegistry.deserialize(type, bytes) : new UnknownFile(bytes));
	}
	
//	public static ByteBuf allocateByteBuffer()
//	{
//		return Unpooled.buffer();
//	}
//	
//	public static ByteBuf allocateCopy(byte[] bytes)
//	{
//		return Unpooled.copiedBuffer(bytes);
//	}
	
//	public abstract FileMetadata generateMetadata();
	protected abstract void writeObject(ObjectOutputStream stream) throws IOException;
	protected abstract void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException;
	public abstract long getSize();
	@Override
	public abstract FileBase deserialize(byte[] bytes) throws DeserializationException;
	@Override
	public abstract int hashCode();
	@Override
	public abstract boolean equals(Object obj);
	@Override
	public abstract FileBase clone();
}
