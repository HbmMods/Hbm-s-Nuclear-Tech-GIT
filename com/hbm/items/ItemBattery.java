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
		if(itemstack.getItem() != ModItems.fusion_core && itemstack.getItem() != ModItems.factory_core_titanium && itemstack.getItem() != ModItems.factory_core_advanced)
		{
			list.add("Energy stored: " + ((this.getMaxDamage() - this.getDamage(itemstack)) * 100) + " HE");
		} else {
			if(this.getDamage(itemstack) != 0)
			{
				int charge = ((this.getMaxDamage() - this.getDamage(itemstack)) * 100) / this.getMaxDamage();
				charge++;
				list.add("Charge: " + charge + "%");
			} else {
				list.add("Charge: 100%");
			}
		}
	}

    @Override
	public EnumRarity getRarity(ItemStack p_77613_1_) {
    	
    	if(this == ModItems.battery_schrabidium)
    	{
        	return EnumRarity.rare;
    	}

    	if(this == ModItems.fusion_core || this == ModItems.factory_core_titanium || this == ModItems.factory_core_advanced)
    	{
        	return EnumRarity.uncommon;
    	}
    	
    	return EnumRarity.common;
    }
	
}
