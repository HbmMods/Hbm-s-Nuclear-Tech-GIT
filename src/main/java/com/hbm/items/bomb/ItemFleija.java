package com.hbm.items.bomb;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.items.special.ItemRadioactive;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemFleija extends ItemRadioactive {
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Used in:");
		list.add("F.L.E.I.J.A.");
	}

    @Override
	public EnumRarity getRarity(ItemStack p_77613_1_) {
    	
    	if(this == ModItems.fleija_propellant)
    	{
        	return EnumRarity.rare;
    	}
    	
    	return EnumRarity.common;
    }

}
