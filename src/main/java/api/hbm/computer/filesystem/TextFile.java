package api.hbm.computer.filesystem;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.hbm.lib.Library;
import com.hbm.main.DeserializationException;

public class TextFile extends FileBase
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2738483188204643613L;
	private final ArrayList<String> data = new ArrayList<String>();
	public TextFile(String...strings)
	{
		data.addAll(Arrays.asList(strings));
	}
	
	public TextFile(Collection<String> strings)
	{
		data.addAll(strings);
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
		long size = 0;
		for (String s : data)
			size += s.length();
		return size;
	}
	
	@Override
	public byte[] serialize()
	{
		return Library.compressStringCollection(data);
	}

	@Override
	public TextFile deserialize(byte[] bytes) throws DeserializationException
	{
		return new TextFile(Library.decompressStringBytes(bytes));
	}

	@Override
	public int hashCode()
	{
		return data.hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null || !(obj instanceof TextFile))
			return false;
		TextFile test = (TextFile) obj;
		return data.equals(test.data);
	}

	@Override
	public TextFile clone()
	{
		return new TextFile(data);
	}

}
