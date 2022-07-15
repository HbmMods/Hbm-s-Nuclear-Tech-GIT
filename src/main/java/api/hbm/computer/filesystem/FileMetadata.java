package api.hbm.computer.filesystem;

import java.math.BigInteger;

import javax.annotation.concurrent.Immutable;

import com.hbm.main.DeserializationException;

import api.hbm.Date;
import api.hbm.serialization.Deserializer;
import api.hbm.serialization.ISerializable;
import io.netty.buffer.ByteBuf;

@Immutable
@org.apache.http.annotation.Immutable
public final class FileMetadata implements ISerializable<FileMetadata>
{
	public static final Deserializer<FileMetadata> deserializer = (bytes) ->
	{
		try
		{
			final ByteBuf buf = allocCopy.apply(bytes);
			final byte[] filenameBytes = new byte[buf.readByte()];
			final byte[] fileTypeBytes = new byte[buf.readByte()];
			buf.readBytes(filenameBytes);
			buf.readBytes(fileTypeBytes);
			final String filename = new String(filenameBytes);
			final String fileType = new String(fileTypeBytes);
			final long fileSize = buf.readLong();
			final int expectedHash = buf.readInt();
			final byte[] createdBytes = new byte[buf.readInt()];
			final byte[] modifiedBytes = new byte[buf.readInt()];
			buf.readBytes(createdBytes);
			buf.readBytes(modifiedBytes);
			final Date created = new Date(createdBytes);
			final Date modified = new Date(modifiedBytes);
			final boolean corrupt = buf.readBoolean();
			return new FileMetadata(filename, fileType, fileSize, expectedHash, created, modified, corrupt);
		} catch (Exception e)
		{
			throw new DeserializationException(e);
		}
	};
	/**
	 * 
	 */
	private static final long serialVersionUID = -7556665102029511004L;
	public final String filename, fileType;
	public final long fileSize;
	public final int expectedHash;
	public final BigInteger dateCreated, dateModified;
	public final boolean corrupted;
	public FileMetadata(String filename, Class<? extends FileBase> clazz, long fileSize, int expectedHash, Date dateCreated, Date dateModified, boolean corrupted)
	{
		this(filename, clazz.getSimpleName(), fileSize, expectedHash, dateCreated, dateModified, corrupted);
	}
	
	public FileMetadata(String filename, String fileType, long fileSize, int expectedHash, Date dateCreated, Date dateModified, boolean corrupted)
	{
		this.filename = filename;
		this.fileType = fileType;
		this.fileSize = fileSize;
		this.expectedHash = expectedHash;
		this.dateCreated = dateCreated.getData();
		this.dateModified = dateModified.getData();
		this.corrupted = corrupted;
	}
	
	@Override
	public byte[] serialize()
	{
		final ByteBuf buf = alloc.get();
		final byte[] filenameBytes = filename.getBytes();
		final byte[] fileTypeBytes = fileType.getBytes();
		buf.writeByte(filenameBytes.length);
		buf.writeByte(fileTypeBytes.length);
		buf.writeBytes(filenameBytes);
		buf.writeBytes(fileTypeBytes);
		buf.writeLong(fileSize);
		buf.writeInt(hashCode());
		final byte[] createdBytes = dateCreated.toByteArray();
		final byte[] modifiedBytes = dateModified.toByteArray();
		buf.writeInt(createdBytes.length);
		buf.writeInt(modifiedBytes.length);
		buf.writeBytes(createdBytes);
		buf.writeBytes(modifiedBytes);
		buf.writeBoolean(corrupted);
		return buf.array();
	}
	@Override
	public FileMetadata deserialize(byte[] bytes) throws DeserializationException
	{
		return deserializer.deserialize(bytes);
	}

}
