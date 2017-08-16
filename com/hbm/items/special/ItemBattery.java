package com.hbm.items.special;

import java.util.List;

import com.hbm.items.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemBattery extends Item {
	
	private long maxCharge;

	public ItemBattery(int dura) {
		this.maxCharge = dura;
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		long charge = maxCharge;
		if(itemstack.hasTagCompound())
			charge = ItemBattery.getCharge(itemstack);
		
		if(itemstack.getItem() != ModItems.fusion_core && itemstack.getItem() != ModItems.factory_core_titanium && itemstack.getItem() != ModItems.factory_core_advanced && itemstack.getItem() != ModItems.energy_core)
		{
			list.add("Energy stored: " + (charge * 100) + "/" + (maxCharge * 100) + " HE");
		} else {
			long charge1 = (charge  * 100) / this.maxCharge;
			list.add("Charge: " + charge1 + "%");
		}
	}

    @Override
	public EnumRarity getRarity(ItemStack p_77613_1_) {
    	
    	if(this == ModItems.battery_schrabidium)
    	{
        	return EnumRarity.rare;
    	}

    	if(this == ModItems.fusion_core || this == ModItems.factory_core_titanium || this == ModItems.factory_core_advanced || this == ModItems.energy_core)
    	{
        	return EnumRarity.uncommon;
    	}
    	
    	return EnumRarity.common;
    }
    
    public void chargeBattery(ItemStack stack, int i) {
    	if(stack.getItem() instanceof ItemBattery) {
    		if(stack.hasTagCompound()) {
    			stack.stackTagCompound.setLong("charge", stack.stackTagCompound.getLong("charge") + i);
    		} else {
    			stack.stackTagCompound = new NBTTagCompound();
    			stack.stackTagCompound.setLong("charge", i);
    		}
    	}
    }
    
    public void setCharge(ItemStack stack, int i) {
    	if(stack.getItem() instanceof ItemBattery) {
    		if(stack.hasTagCompound()) {
    			stack.stackTagCompound.setLong("charge", i);
    		} else {
    			stack.stackTagCompound = new NBTTagCompound();
    			stack.stackTagCompound.setLong("charge", i);
    		}
    	}
    }
    
    public void dischargeBattery(ItemStack stack, int i) {
    	if(stack.getItem() instanceof ItemBattery) {
    		if(stack.hasTagCompound()) {
    			stack.stackTagCompound.setLong("charge", stack.stackTagCompound.getLong("charge") - i);
    		} else {
    			stack.stackTagCompound = new NBTTagCompound();
    			stack.stackTagCompound.setLong("charge", this.maxCharge - i);
    		}
    	}
    }
    
    public static long getCharge(ItemStack stack) {
    	if(stack.getItem() instanceof ItemBattery) {
    		if(stack.hasTagCompound()) {
    			return stack.stackTagCompound.getLong("charge");
    		} else {
    			stack.stackTagCompound = new NBTTagCompound();
    			stack.stackTagCompound.setLong("charge", ((ItemBattery)stack.getItem()).maxCharge);
    			return stack.stackTagCompound.getLong("charge");
    		}
    	}
    	
    	return 0;
    }
    
    public long getMaxCharge() {
    	return maxCharge;
    }
    
    public static long getMaxChargeStatic(ItemStack stack) {
    	return ((ItemBattery)stack.getItem()).maxCharge;
    }
    
    public static ItemStack getEmptyBattery(Item item) {
    	
    	if(item instanceof ItemBattery) {
    		ItemStack stack = new ItemStack(item);
    		stack.stackTagCompound = new NBTTagCompound();
    		stack.stackTagCompound.setLong("charge", 0);
    		return stack.copy();
    	}
    	
    	return null;
    }
    
    public static ItemStack getFullBattery(Item item) {
    	
    	if(item instanceof ItemBattery) {
    		ItemStack stack = new ItemStack(item);
    		stack.stackTagCompound = new NBTTagCompound();
    		stack.stackTagCompound.setLong("charge", getMaxChargeStatic(stack));
    		return stack.copy();
    	}
    	
    	return null;
    }
	
}
