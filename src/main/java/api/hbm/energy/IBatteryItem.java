package api.hbm.energy;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IBatteryItem {

	public void chargeBattery(ItemStack stack, long i);
	public void setCharge(ItemStack stack, long i);
	public void dischargeBattery(ItemStack stack, long i);
	public long getCharge(ItemStack stack);
	public long getMaxCharge();
	public long getChargeRate();
	public long getDischargeRate();
	
    public default String getChargeTagName()
    {
    	return "charge";
    }
    
    public static String getChargeTagName(@Nonnull ItemStack stack)
    {
    	assert stack.getItem() instanceof IBatteryItem;
    	return ((IBatteryItem) stack.getItem()).getChargeTagName();
    }
    
    public static long getChargeStatic(ItemStack stack)
    {
    	if (stack != null && stack.getItem() instanceof IBatteryItem)
    	{
    		final String keyName = getChargeTagName(stack);
    		if (stack.hasTagCompound() && stack.stackTagCompound.hasKey(keyName))
    			return stack.stackTagCompound.getLong(keyName);
    	}
    	return 0;
    }
    
    public static ItemStack emptyBattery(ItemStack stack)
    {
    	if (stack != null && stack.getItem() instanceof IBatteryItem)
    	{
    		final String keyName = getChargeTagName(stack);
    		ItemStack stackOut = stack.copy();
    		stackOut.stackTagCompound = new NBTTagCompound();
    		stackOut.stackTagCompound.setLong(keyName, 0);
    		return stackOut.copy();
    	}
    	return null;
    }
    @CheckForNull
    public static ItemStack emptyBattery(Item item)
    {
    	return (item instanceof IBatteryItem) ? emptyBattery(new ItemStack(item)) : null;
    }
    
    public static ItemStack fullBattery(ItemStack stack)
    {
    	if (stack != null && stack.getItem() instanceof IBatteryItem)
    	{
    		final String keyName = getChargeTagName(stack);
    		ItemStack stackOut = stack.copy();
    		stackOut.stackTagCompound = new NBTTagCompound();
    		stackOut.stackTagCompound.setLong(keyName, ((IBatteryItem) stack.getItem()).getMaxCharge());
    		return stackOut.copy();
    	}
    	else
    		return null;
    }
    @CheckForNull
    public static ItemStack fullBattery(Item item)
    {
    	return (item instanceof IBatteryItem) ? fullBattery(new ItemStack(item)) : null;
    }
}
