package com.hbm.items.armor;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ArmorTest extends ItemArmor {
	
	public ArmorTest(ArmorMaterial armorMaterial, int armorType) {
		super(armorMaterial, 0, armorType);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
		if(stack.getItem().equals(ModItems.test_helmet) || stack.getItem().equals(ModItems.test_chestplate) || stack.getItem().equals(ModItems.test_boots)) {
			return (RefStrings.MODID + ":textures/armor/test_1.png");
		}
		if(stack.getItem().equals(ModItems.test_leggings)) {
			return (RefStrings.MODID + ":textures/armor/test_2.png");
		}
		
		else return null;
	}

}
