package com.hbm.items.tool;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;

public class PickaxeSchrabidium extends ItemPickaxe {

	public PickaxeSchrabidium(ToolMaterial p_i45347_1_) {
		super(p_i45347_1_);
	}

    @Override
	public EnumRarity getRarity(ItemStack p_77613_1_) {
    	
		return EnumRarity.rare;
    }

}
