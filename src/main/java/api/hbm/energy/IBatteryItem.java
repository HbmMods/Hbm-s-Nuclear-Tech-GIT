package api.hbm.energy;

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
    
    public static ItemStack emptyBattery(ItemStack stack)
    {
    	if (stack != null && stack.getItem() instanceof IBatteryItem)
    	{
    		ItemStack stackOut = stack.copy();
    		stackOut.stackTagCompound = new NBTTagCompound();
    		stackOut.stackTagCompound.setLong("charge", 0);
    		return stackOut.copy();
    	}
    	return null;
    }
    public static ItemStack emptyBattery(Item item)
    {
    	if (item instanceof IBatteryItem)
    	{
    		ItemStack stack = new ItemStack(item);
    		stack.stackTagCompound = new NBTTagCompound();
    		stack.stackTagCompound.setLong("charge", 0);
    		return stack.copy();
    	}
    	else
    		return null;
    }
    
    public static ItemStack fullBattery(ItemStack stack)
    {
    	if (stack != null && stack.getItem() instanceof IBatteryItem)
    	{
    		ItemStack stackOut = stack.copy();
    		stackOut.stackTagCompound = new NBTTagCompound();
    		stackOut.stackTagCompound.setLong("charge", ((IBatteryItem) stack.getItem()).getMaxCharge());
    		return stackOut.copy();
    	}
    	return null;
    }
    public static ItemStack fullBattery(Item item)
    {
    	if (item instanceof IBatteryItem)
    	{
    		ItemStack stack = new ItemStack(item);
    		stack.stackTagCompound = new NBTTagCompound();
    		stack.stackTagCompound.setLong("charge", ((IBatteryItem) item).getMaxCharge());
    		return stack.copy();
    	}
    	else
    		return null;
    }

}
