package api.hbm.computer.filesystem;

public interface IDataStorageBase
{
	public long getMaxCapacity();
	public long getReadSpeed();
	public long getWriteSpeed();
}
