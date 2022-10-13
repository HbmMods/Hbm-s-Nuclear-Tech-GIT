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
    	return ((IBatteryItem) stack.getItem()).getChargeTagName();
    }
	
	@CheckForNull
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
}
