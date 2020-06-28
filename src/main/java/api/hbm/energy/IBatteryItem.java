package api.hbm.energy;

import net.minecraft.item.ItemStack;

public interface IBatteryItem {

	public void chargeBattery(ItemStack stack, long i);
    public void setCharge(ItemStack stack, long i);
    public void dischargeBattery(ItemStack stack, long i);
    public long getCharge(ItemStack stack);
    public long getMaxCharge();
    public long getChargeRate();    
    public long getDischargeRate();
}
