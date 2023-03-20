package com.hbm.items.armor;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ModArmor extends ItemArmor {

	public ModArmor(ArmorMaterial material, int slot) {
		super(material, 0, slot);
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
		if(stack.getItem().equals(ModItems.paa_plate) || stack.getItem().equals(ModItems.paa_boots)) {
			return (RefStrings.MODID + ":textures/armor/paa_1.png");
		}
		if(stack.getItem().equals(ModItems.paa_legs)) {
			return (RefStrings.MODID + ":textures/armor/paa_2.png");
		}
		if(stack.getItem().equals(ModItems.asbestos_helmet) || stack.getItem().equals(ModItems.asbestos_plate) || stack.getItem().equals(ModItems.asbestos_boots)) {
			return (RefStrings.MODID + ":textures/armor/asbestos_1.png");
		}
		if(stack.getItem().equals(ModItems.asbestos_legs)) {
			return (RefStrings.MODID + ":textures/armor/asbestos_2.png");
		}
		if(stack.getItem().equals(ModItems.jackt)) {
			return (RefStrings.MODID + ":textures/armor/jackt.png");
		}
		if(stack.getItem().equals(ModItems.jackt2)) {
			return (RefStrings.MODID + ":textures/armor/jackt2.png");
		}
		if(stack.getItem().equals(ModItems.security_helmet) || stack.getItem().equals(ModItems.security_plate) || stack.getItem().equals(ModItems.security_boots)) {
			return (RefStrings.MODID + ":textures/armor/security_1.png");
		}
		if(stack.getItem().equals(ModItems.security_legs)) {
			return (RefStrings.MODID + ":textures/armor/security_2.png");
		}
		if(stack.getItem().equals(ModItems.mask_rag)) {
			return (RefStrings.MODID + ":textures/armor/rag_damp.png");
		}
		if(stack.getItem().equals(ModItems.mask_piss)) {
			return (RefStrings.MODID + ":textures/armor/rag_piss.png");
		}
		
		else return null;
	}

}
