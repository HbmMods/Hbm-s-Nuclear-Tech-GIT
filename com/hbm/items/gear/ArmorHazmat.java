package com.hbm.items.gear;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ArmorHazmat extends ItemArmor {

	public ArmorHazmat(ArmorMaterial p_i45325_1_, int p_i45325_2_, int p_i45325_3_) {
		super(p_i45325_1_, p_i45325_2_, p_i45325_3_);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
		if(stack.getItem().equals(ModItems.hazmat_helmet) || stack.getItem().equals(ModItems.hazmat_plate) || stack.getItem().equals(ModItems.hazmat_boots)) {
			return (RefStrings.MODID + ":textures/armor/hazmat_1.png");
		}
		if(stack.getItem().equals(ModItems.hazmat_legs)) {
			return (RefStrings.MODID + ":textures/armor/hazmat_2.png");
		}
		if(stack.getItem().equals(ModItems.hazmat_paa_helmet) || stack.getItem().equals(ModItems.hazmat_paa_plate) || stack.getItem().equals(ModItems.hazmat_paa_boots)) {
			return (RefStrings.MODID + ":textures/armor/hazmat_paa_1.png");
		}
		if(stack.getItem().equals(ModItems.hazmat_paa_legs)) {
			return (RefStrings.MODID + ":textures/armor/hazmat_paa_2.png");
		}
		
		else return null;
	}

}
