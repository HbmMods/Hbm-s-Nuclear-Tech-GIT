package api.hbm.computer.filesystem;

import java.util.Set;

import javax.annotation.CheckForNull;
import javax.annotation.CheckReturnValue;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IDataStorageItem
{
	public static final String KEY_USED = "USED_CAPACITY", PREFIX = "STORED_FILE_", H_SUFFIX = "_HASH", KEY_FILE_LIST = "FILE_LIST", M_PREFIX = "META_";
	
	public static void setUsedCapacity(ItemStack stack, long size)
	{
		if (!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		stack.stackTagCompound.setLong(KEY_USED, size);
	}
	
	public default long getUsedCapacity(ItemStack stack)
	{
		if (!stack.hasTagCompound())
		{
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setLong(KEY_USED, getMaxCapacity(stack));
		}
		
		return stack.stackTagCompound.getLong(KEY_USED);
	}
	
	public long getMaxCapacity(ItemStack stack);
	public long getReadSpeed(ItemStack stack);
	public long getWriteSpeed(ItemStack stack);
	public boolean writeFile(FileBase file, FileMetadata metadata, ItemStack stack);
	public boolean deleteFile(String filename, ItemStack stack);
	public boolean overwriteFile(FileBase file, FileMetadata metadata, ItemStack stack);
	@CheckReturnValue
	@CheckForNull
	public FileBase readFile(String filename, ItemStack stack);
	@CheckReturnValue
	public byte[] getFileHash(String filename, ItemStack stack);
	@CheckReturnValue
	@CheckForNull
	public FileMetadata getFileMetadata(String filename, ItemStack stack);
	public Set<String> getFileList(ItemStack stack);
}
