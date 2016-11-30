package com.hbm.items.gear;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;

public class HoeSchrabidium extends ItemHoe {

	public HoeSchrabidium(ToolMaterial p_i45343_1_) {
		super(p_i45343_1_);
	}

    @Override
	public EnumRarity getRarity(ItemStack p_77613_1_) {
    	
		return EnumRarity.rare;
    }

}
