package api.hbm.internet;

import javax.annotation.Nonnull;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IDataStorageUser
{
	public long getMaxCapacity();
	public long getReadSpeed();
	public long getWriteSpeed();
	public NBTTagCompound getStoredData(ItemStack stack);
	public void deleteData(ItemStack itemStack, long toErase);
	
	public static final String GROUP_KEY = "NTM_DATA_STORAGE";
	public static final NBTTagCompound BLANK = new NBTTagCompound();
	
	public default String getStorageTagName()
	{
		return "used";
	}
	
	public static String getStorageTagName(@Nonnull ItemStack stack)
	{
		assert stack.getItem() instanceof IDataStorageUser;
		return ((IDataStorageUser) stack.getItem()).getStorageTagName();
	}
	
	public static long getDataUsed(ItemStack stack)
	{
		if (stack != null && stack.getItem() instanceof IDataStorageUser)
		{
			final String keyName = getStorageTagName(stack);
			if (stack.hasTagCompound() && stack.getTagCompound().hasKey(keyName))
				return stack.getTagCompound().getLong(keyName);
		}
		return 0;
	}
	
	public static ItemStack getBlankMedium(Item item)
	{
//		System.out.println(item instanceof IDataStorageUser);
		if (item instanceof IDataStorageUser)
			return getBlankMedium(new ItemStack(item));
		else
			return null;
	}
	
	public static ItemStack getBlankMedium(ItemStack stack)
	{
		if (stack != null && stack.getItem() instanceof IDataStorageUser)
		{
			ItemStack stackOut = stack.copy();
			stackOut.stackTagCompound = new NBTTagCompound();
			stackOut.stackTagCompound.setLong(getStorageTagName(stack), 0);
			return stackOut;
		}
		else
			return null;
	}
	
	public static ItemStack getFullMedium(Item item)
	{
		if (item instanceof IDataStorageUser)
			return getFullMedium(new ItemStack(item));
		else
			return null;
	}
	
	public static ItemStack getFullMedium(ItemStack stack)
	{
		if (stack != null && stack.getItem() instanceof IDataStorageUser)
		{
			ItemStack stackOut = stack.copy();
			stackOut.stackTagCompound = new NBTTagCompound();
			stackOut.stackTagCompound.setLong(getStorageTagName(stack), ((IDataStorageUser) stack.getItem()).getMaxCapacity());
			return stackOut;
		}
		else
			return null;
	}
	
}
