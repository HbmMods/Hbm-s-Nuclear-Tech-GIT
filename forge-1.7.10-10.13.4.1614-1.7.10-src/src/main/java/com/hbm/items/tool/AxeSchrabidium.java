package com.hbm.items.tool;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

public class AxeSchrabidium extends ItemAxe {

	public AxeSchrabidium(ToolMaterial p_i45327_1_) {
		super(p_i45327_1_);
	}

    @Override
	public EnumRarity getRarity(ItemStack p_77613_1_) {
    	
		return EnumRarity.rare;
    }

}
