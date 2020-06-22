package com.hbm.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;

public class EnchantmentUtil {
	
	public static void addEnchantment(ItemStack stack, Enchantment enchantment, int level) {

		stack.addEnchantment(enchantment, level);
	}
	
	public static void removeEnchantment(ItemStack stack, Enchantment enchantment) {
		
		int i = 0;
		for( ; i < stack.getEnchantmentTagList().tagCount(); i++) {
			if(stack.getEnchantmentTagList().getCompoundTagAt(i).getShort("id") == enchantment.effectId)
				break;
		}

		stack.getEnchantmentTagList().removeTag(i);
		
		if(stack.getEnchantmentTagList().tagCount() == 0)
			stack.getTagCompound().removeTag("ench");
	}
}
