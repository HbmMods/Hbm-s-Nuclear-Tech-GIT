package api.hbm.computer.filesystem;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.google.common.annotations.Beta;
import com.hbm.lib.Library;
import com.hbm.main.DeserializationException;
import com.hbm.util.Tuple.Pair;

import api.hbm.serialization.ISerializable;
import io.netty.buffer.ByteBuf;

@Beta
public class DataStorageSystem implements IDataStorageSystem, ISerializable<DataStorageSystem>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4781614731522225061L;
	private final HashMap<String, FilePair> fileList = new HashMap<>();
	private final long maxSize, readSpeed, writeSpeed;
	private transient Optional<FilePair> currentOperation = Optional.empty();
	public DataStorageSystem(long maxSize, long readSpeed, long writeSpeed)
	{
		this.maxSize = maxSize;
		this.readSpeed = readSpeed;
		this.writeSpeed = writeSpeed;
	}

	public DataStorageSystem(Map<String, FilePair> source, long maxSize, long readSpeed, long writeSpeed)
	{
		this(maxSize, readSpeed, writeSpeed);
		fileList.putAll(source);
	}
	
	@Override
	public long getMaxCapacity()
	{
		return maxSize;
	}

	@Override
	public long getReadSpeed()
	{
		return readSpeed;
	}

	@Override
	public long getWriteSpeed()
	{
		return writeSpeed;
	}

	@Override
	public long getUsedCapacity()
	{
		long size = 0;
		
		for (Pair<FileMetadata, FileBase> p : fileList.values())
			size += p.getKey().fileSize;
		
		return size;
	}

	@Override
	public boolean writeFile(FileMetadata metadata, FileBase file)
	{
		if (currentOperation.isPresent() || getUsedCapacity() + metadata.fileSize > getMaxCapacity())
			return false;
		
		currentOperation = Optional.of(new FilePair(metadata, file));
		return true;
	}

	@Override
	public boolean deleteFile(String filename)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean overwriteFile(FileMetadata metadata, FileBase file)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public FileMetadata getFileMetadata(String filename)
	{
		return fileList.containsKey(filename) ? fileList.get(filename).getKey() : null;
	}

	@Override
	public FileBase readFile(String filename)
	{
		return fileList.containsKey(filename) ? fileList.get(filename).getValue() : null;
	}

	@Override
	public byte[] getFileHash(String filename)
	{
		return fileList.containsKey(filename) ? Library.getHash(fileList.get(filename).getValue().serialize()) : FileBase.emptyBytes;
	}

	@Override
	public Set<String> getFileList()
	{
		return fileList.keySet();
	}

	@Override
	public DataStorageSystem deserialize(byte[] bytes) throws DeserializationException
	{
		try
		{
			final ByteBuf buf = allocCopy.apply(bytes);
			final HashMap<String, FilePair> readFiles = new HashMap<>();
			final long mSize = buf.readLong(), rSpeed = buf.readLong(), wSpeed = buf.readLong();
			
			while (buf.readBoolean())
			{
				final byte[] metadataBytes = new byte[buf.readInt()];
				final byte[] fileBytes = new byte[buf.readInt()];
				buf.readBytes(metadataBytes);
				buf.readBytes(fileBytes);
				final FileMetadata metadata = FileMetadata.deserializer.deserialize(metadataBytes);
				final FileBase fileBase = FileBase.deserializeFromRegistry(metadata.fileType, fileBytes);
				readFiles.put(metadata.filename, new FilePair(metadata, fileBase));
			}
			
			return new DataStorageSystem(readFiles, mSize, rSpeed, wSpeed);
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new DeserializationException(e);
		}
	}

	@Override
	public byte[] serialize()
	{
		final ByteBuf buf = alloc.get();
		buf.writeLong(maxSize);
		buf.writeLong(readSpeed);
		buf.writeLong(writeSpeed);
		final Iterator<FilePair> iterator = fileList.values().iterator();
		buf.writeBoolean(iterator.hasNext());
		while (iterator.hasNext())
		{
			final FilePair file = iterator.next();
			final byte[] metadataBytes = file.getKey().serialize();
			final byte[] fileBytes = file.getValue().serialize();
			buf.writeInt(metadataBytes.length);
			buf.writeInt(fileBytes.length);
			
			buf.writeBytes(metadataBytes);
			buf.writeBytes(fileBytes);
			
			buf.writeBoolean(iterator.hasNext());
		}
		
		return buf.array();
	}

}
