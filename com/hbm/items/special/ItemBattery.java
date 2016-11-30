package com.hbm.items.special;

import java.util.List;

import com.hbm.items.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBattery extends Item {

	public ItemBattery(int dura) {
		this.setMaxDamage(dura);
		this.setNoRepair();
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		if(itemstack.getItem() != ModItems.fusion_core && itemstack.getItem() != ModItems.factory_core_titanium && itemstack.getItem() != ModItems.factory_core_advanced && itemstack.getItem() != ModItems.energy_core)
		{
			list.add("Energy stored: " + ((this.getMaxDamage() - this.getDamage(itemstack)) * 100) + " HE");
		} else {
			int charge = ((this.getMaxDamage() - this.getDamage(itemstack)) * 100) / this.getMaxDamage();
			list.add("Charge: " + charge + "%");
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
	
}
