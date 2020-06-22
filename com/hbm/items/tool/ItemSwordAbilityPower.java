package com.hbm.items.tool;

import java.util.List;

import com.hbm.lib.Library;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemSwordAbilityPower extends ItemSwordAbility {

	public long maxPower = 1;
	public long chargeRate;
	public long consumption;

	public ItemSwordAbilityPower(float damage, double movement, ToolMaterial material, long maxPower, long chargeRate, long consumption) {
		super(damage, movement, material);
		this.maxPower = maxPower;
		this.chargeRate = chargeRate;
		this.consumption = consumption;
		this.setMaxDamage(1);
	}
    
    public void chargeBattery(ItemStack stack, long i) {
    	if(stack.getItem() instanceof ItemSwordAbilityPower) {
    		if(stack.hasTagCompound()) {
    			stack.stackTagCompound.setLong("charge", stack.stackTagCompound.getLong("charge") + i);
    		} else {
    			stack.stackTagCompound = new NBTTagCompound();
    			stack.stackTagCompound.setLong("charge", i);
    		}
    	}
    }
    
    public void setCharge(ItemStack stack, long i) {
    	if(stack.getItem() instanceof ItemSwordAbilityPower) {
    		if(stack.hasTagCompound()) {
    			stack.stackTagCompound.setLong("charge", i);
    		} else {
    			stack.stackTagCompound = new NBTTagCompound();
    			stack.stackTagCompound.setLong("charge", i);
    		}
    	}
    }
    
    public void dischargeBattery(ItemStack stack, long i) {
    	if(stack.getItem() instanceof ItemSwordAbilityPower) {
    		if(stack.hasTagCompound()) {
    			stack.stackTagCompound.setLong("charge", stack.stackTagCompound.getLong("charge") - i);
    		} else {
    			stack.stackTagCompound = new NBTTagCompound();
    			stack.stackTagCompound.setLong("charge", this.maxPower - i);
    		}
    		
    		if(stack.stackTagCompound.getLong("charge") < 0)
    			stack.stackTagCompound.setLong("charge", 0);
    	}
    }
    
    public static long getCharge(ItemStack stack) {
    	if(stack.getItem() instanceof ItemSwordAbilityPower) {
    		if(stack.hasTagCompound()) {
    			return stack.stackTagCompound.getLong("charge");
    		} else {
    			stack.stackTagCompound = new NBTTagCompound();
    			stack.stackTagCompound.setLong("charge", ((ItemSwordAbilityPower)stack.getItem()).maxPower);
    			return stack.stackTagCompound.getLong("charge");
    		}
    	}
    	
    	return 0;
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
    	
    	list.add("Charge: " + Library.getShortNumber(getCharge(stack)) + " / " + Library.getShortNumber(maxPower));
    	
    	super.addInformation(stack, player, list, ext);
    }
    
    public boolean showDurabilityBar(ItemStack stack) {
    	
        return getCharge(stack) < maxPower;
    }
    
    public double getDurabilityForDisplay(ItemStack stack) {
    	
        return 1 - (double)getCharge(stack) / (double)maxPower;
    }
    
    protected boolean canOperate(ItemStack stack) {
    	
    	return getCharge(stack) >= this.consumption;
    }
    
    public long getMaxCharge() {
    	return maxPower;
    }
    
    public long getChargeRate() {
    	return chargeRate;
    }
    
    public static long getMaxChargeStatic(ItemStack stack) {
    	return ((ItemSwordAbilityPower)stack.getItem()).maxPower;
    }
    
    public void setDamage(ItemStack stack, int damage)
    {
        this.dischargeBattery(stack, damage * consumption);
    }
    
    public boolean isDamageable() {
    	return true;
    }
}
