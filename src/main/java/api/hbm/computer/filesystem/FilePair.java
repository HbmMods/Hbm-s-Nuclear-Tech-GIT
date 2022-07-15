package api.hbm.computer.filesystem;

import com.hbm.util.Tuple.Pair;

public class FilePair extends Pair<FileMetadata, FileBase>
{
	public FilePair(FileMetadata metadata, FileBase file)
	{
		super(metadata, file);
	}
}
