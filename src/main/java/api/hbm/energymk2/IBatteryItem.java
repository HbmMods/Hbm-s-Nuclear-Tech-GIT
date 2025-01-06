package api.hbm.energymk2;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IBatteryItem {

	public void chargeBattery(ItemStack stack, long i);
	public void setCharge(ItemStack stack, long i);
	public void dischargeBattery(ItemStack stack, long i);
	public long getCharge(ItemStack stack);
	public long getMaxCharge(ItemStack stack);
	public long getChargeRate();
	public long getDischargeRate();
	
	/** Returns a string for the NBT tag name of the long storing power */
	public default String getChargeTagName() {
		return "charge";
	}

	/** Returns a string for the NBT tag name of the long storing power */
	public static String getChargeTagName(ItemStack stack) {
		return ((IBatteryItem) stack.getItem()).getChargeTagName();
	}

	/** Returns an empty battery stack from the passed ItemStack, the original won't be modified */
	public static ItemStack emptyBattery(ItemStack stack) {
		if(stack != null && stack.getItem() instanceof IBatteryItem) {
			String keyName = getChargeTagName(stack);
			ItemStack stackOut = stack.copy();
			stackOut.stackTagCompound = new NBTTagCompound();
			stackOut.stackTagCompound.setLong(keyName, 0);
			return stackOut.copy();
		}
		return null;
	}

	/** Returns an empty battery stack from the passed Item */
	public static ItemStack emptyBattery(Item item) {
		return item instanceof IBatteryItem ? emptyBattery(new ItemStack(item)) : null;
	}
}
