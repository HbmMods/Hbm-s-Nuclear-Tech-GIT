package com.hbm.handler;

import com.hbm.items.armor.ItemArmorMod;

import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ArmorModHandler {

	public static final int helmet_only = 0;
	public static final int plate_only = 1;
	public static final int legs_only = 2;
	public static final int boots_only = 3;
	public static final int servos = 4;
	public static final int cladding = 5;
	public static final int kevlar = 6;
	public static final int plating = 7;
	
	public static boolean isApplicable(ItemStack armor, ItemStack mod) {
		
		if(armor == null || mod == null)
			return false;
		
		if(!(armor.getItem() instanceof ItemArmor))
			return false;
		
		if(!(mod.getItem() instanceof ItemArmorMod))
			return false;
		
		int type = ((ItemArmor)armor.getItem()).armorType;
		
		ItemArmorMod aMod = (ItemArmorMod)mod.getItem();
		
		return (type == 0 && aMod.helmet) || (type == 1 && aMod.chestplate) || (type == 2 && aMod.leggings) || (type == 3 && aMod.boots);
	}
}
