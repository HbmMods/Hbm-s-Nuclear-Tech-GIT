package api.hbm.computer.filesystem;

import java.util.Set;

import javax.annotation.CheckReturnValue;

public interface IDataStorageSystem extends IDataStorageBase
{
	public long getUsedCapacity();
	public boolean writeFile(FileMetadata metadata, FileBase file);
	public boolean deleteFile(String filename);
	public boolean overwriteFile(FileMetadata metadata, FileBase file);
	public FileMetadata getFileMetadata(String filename);
	@CheckReturnValue
	public FileBase readFile(String filename);
	@CheckReturnValue
	public byte[] getFileHash(String filename);
	public Set<String> getFileList();
}
