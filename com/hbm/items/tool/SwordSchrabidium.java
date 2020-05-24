package com.hbm.items.tool;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class SwordSchrabidium extends ItemSword {

	public SwordSchrabidium(ToolMaterial p_i45356_1_) {
		super(p_i45356_1_);
	}

    @Override
	public EnumRarity getRarity(ItemStack p_77613_1_) {
    	
		return EnumRarity.rare;
    }

}
