package com.hbm.items.tool;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;

public class SpadeSchrabidium extends ItemSpade {

	public SpadeSchrabidium(ToolMaterial p_i45353_1_) {
		super(p_i45353_1_);
	}

    @Override
	public EnumRarity getRarity(ItemStack p_77613_1_) {
    	
		return EnumRarity.rare;
    }

}
