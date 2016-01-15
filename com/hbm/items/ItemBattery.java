package com.hbm.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBattery extends Item {

	public ItemBattery(int dura) {
		this.setMaxDamage(dura);
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		if(itemstack.getItem() != ModItems.fusion_core)
		{
			list.add("Energy stored: " + ((this.getMaxDamage() - this.getDamage(itemstack)) * 100) + " HE");
		} else {
			list.add("Charge: " + ((this.getMaxDamage() - this.getDamage(itemstack)) * 100) / this.getMaxDamage() + "%");
		}
	}

    @Override
	public EnumRarity getRarity(ItemStack p_77613_1_) {
    	
    	if(this == ModItems.battery_schrabidium)
    	{
        	return EnumRarity.rare;
    	}
    	
    	return EnumRarity.common;
    }
	
}
