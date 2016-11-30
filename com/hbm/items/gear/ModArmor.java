package com.hbm.items.gear;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ModArmor extends ItemArmor {

	public ModArmor(ArmorMaterial p_i45325_1_, int p_i45325_2_, int p_i45325_3_) {
		super(p_i45325_1_, p_i45325_2_, p_i45325_3_);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
		if(stack.getItem().equals(ModItems.steel_helmet) || stack.getItem().equals(ModItems.steel_plate) || stack.getItem().equals(ModItems.steel_boots)) {
			return (RefStrings.MODID + ":textures/armor/steel_1.png");
		}
		if(stack.getItem().equals(ModItems.steel_legs)) {
			return (RefStrings.MODID + ":textures/armor/steel_2.png");
		}
		if(stack.getItem().equals(ModItems.titanium_helmet) || stack.getItem().equals(ModItems.titanium_plate) || stack.getItem().equals(ModItems.titanium_boots)) {
			return (RefStrings.MODID + ":textures/armor/titanium_1.png");
		}
		if(stack.getItem().equals(ModItems.titanium_legs)) {
			return (RefStrings.MODID + ":textures/armor/titanium_2.png");
		}
		if(stack.getItem().equals(ModItems.alloy_helmet) || stack.getItem().equals(ModItems.alloy_plate) || stack.getItem().equals(ModItems.alloy_boots)) {
			return (RefStrings.MODID + ":textures/armor/alloy_1.png");
		}
		if(stack.getItem().equals(ModItems.alloy_legs)) {
			return (RefStrings.MODID + ":textures/armor/alloy_2.png");
		}
		if(stack.getItem().equals(ModItems.cmb_helmet) || stack.getItem().equals(ModItems.cmb_plate) || stack.getItem().equals(ModItems.cmb_boots)) {
			return (RefStrings.MODID + ":textures/armor/cmb_1.png");
		}
		if(stack.getItem().equals(ModItems.cmb_legs)) {
			return (RefStrings.MODID + ":textures/armor/cmb_2.png");
		}
<<<<<<< HEAD:com/hbm/items/gear/ModArmor.java
=======
<<<<<<< HEAD
>>>>>>> 5525318475377d238c79edc90a14ee8fa48397af:com/hbm/items/ModArmor.java
		if(stack.getItem().equals(ModItems.paa_plate) || stack.getItem().equals(ModItems.paa_boots)) {
			return (RefStrings.MODID + ":textures/armor/paa_1.png");
		}
		if(stack.getItem().equals(ModItems.paa_legs)) {
			return (RefStrings.MODID + ":textures/armor/paa_2.png");
		}
<<<<<<< HEAD:com/hbm/items/gear/ModArmor.java
=======
=======
>>>>>>> 540fb3d256a0f4ae6a8b1db586f8e9cfd6ed7372
>>>>>>> 5525318475377d238c79edc90a14ee8fa48397af:com/hbm/items/ModArmor.java
		
		else return null;
	}

}
