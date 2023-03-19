package com.hbm.items.armor;

import com.hbm.lib.RefStrings;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class MaskOfInfamy extends ItemArmor {

	public MaskOfInfamy(ArmorMaterial mat, int slot) {
		super(mat, 0, slot);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
		
		return (RefStrings.MODID + ":textures/armor/MaskOfInfamy.png");
	}

}
