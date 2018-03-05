package com.hbm.items.gear;

import com.hbm.lib.RefStrings;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class MaskOfInfamy extends ItemArmor {

	public MaskOfInfamy(ArmorMaterial p_i45325_1_, int p_i45325_2_, int p_i45325_3_) {
		super(p_i45325_1_, p_i45325_2_, p_i45325_3_);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
		
		return (RefStrings.MODID + ":textures/armor/MaskOfInfamy.png");
	}

}
