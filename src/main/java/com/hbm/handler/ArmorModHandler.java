package com.hbm.handler;

import com.hbm.items.armor.ItemArmorMod;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ArmorModHandler {

	public static final int helmet_only = 0;
	public static final int plate_only = 1;
	public static final int legs_only = 2;
	public static final int boots_only = 3;
	public static final int servos = 4;
	public static final int cladding = 5;
	public static final int kevlar = 6;
	public static final int plating = 7;
	
	//The key for the NBTTagCompound that holds the armor mods
	public static final String MOD_COMPOUND_KEY = "ntm_armor_mods";
	//The key for the specific slot inside the armor mod NBT Tag
	public static final String MOD_SLOT_KEY = "mod_slot_";
	
	/**
	 * Checks if a mod can be applied to an armor piece
	 * Needs to be used to prevent people from inserting invalid items into the armor table
	 * @param armor
	 * @param mod
	 * @return
	 */
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
	
	/**
	 * Applies an mod to the given armor piece
	 * Make sure to check for applicability first
	 * Will override present mods so make sure to only use unmodded armor pieces
	 * @param armor
	 * @param mod
	 */
	public static void applyMod(ItemStack armor, ItemStack mod) {
		
		if(!armor.hasTagCompound())
			armor.stackTagCompound = new NBTTagCompound();
		
		NBTTagCompound nbt = armor.getTagCompound();
		
		if(!nbt.hasKey(MOD_COMPOUND_KEY))
			nbt.setTag(MOD_COMPOUND_KEY, new NBTTagCompound());
		
		NBTTagCompound mods = nbt.getCompoundTag(MOD_COMPOUND_KEY);
		
		ItemArmorMod aMod = (ItemArmorMod)mod.getItem();
		int slot = aMod.type;
		
		mods.setString(MOD_SLOT_KEY + slot, Item.itemRegistry.getNameForObject(mod.getItem()));
	}
	
	/**
	 * Removes the mod from the given slot
	 * @param armor
	 * @param slot
	 */
	public static void removeMod(ItemStack armor, int slot) {
		
		if(armor == null)
			return;
		
		if(!armor.hasTagCompound())
			armor.stackTagCompound = new NBTTagCompound();
		
		NBTTagCompound nbt = armor.getTagCompound();
		
		if(!nbt.hasKey(MOD_COMPOUND_KEY))
			nbt.setTag(MOD_COMPOUND_KEY, new NBTTagCompound());
		
		NBTTagCompound mods = nbt.getCompoundTag(MOD_COMPOUND_KEY);
		mods.removeTag(MOD_SLOT_KEY + slot);
		
		if(mods.hasNoTags())
			clearMods(armor);
	}
	
	/**
	 * Removes ALL mods
	 * Should be used when the armor piece is put in the armor table slot AFTER the armor pieces have been separated
	 * @param armor
	 */
	public static void clearMods(ItemStack armor) {
		
		if(!armor.hasTagCompound())
			return;
		
		NBTTagCompound nbt = armor.getTagCompound();
		nbt.removeTag(MOD_COMPOUND_KEY);
	}
	
	/**
	 * Does what the name implies
	 * @param armor
	 * @return
	 */
	public static boolean hasMods(ItemStack armor) {
		
		if(!armor.hasTagCompound())
			return false;
		
		NBTTagCompound nbt = armor.getTagCompound();
		return nbt.hasKey(MOD_COMPOUND_KEY);
	}
	
	public static ItemStack[] pryMods(ItemStack armor) {
		
		ItemStack[] slots = new ItemStack[8];

		if(!hasMods(armor))
			return slots;
		
		NBTTagCompound nbt = armor.getTagCompound();
		NBTTagCompound mods = nbt.getCompoundTag(MOD_COMPOUND_KEY);
		
		for(int i = 0; i < 8; i++) {
			
			String mod = mods.getString(MOD_SLOT_KEY + i);
			Item item = (Item)Item.itemRegistry.getObject(mod);
			
			if(item != null)
				slots[i] = new ItemStack(item);
		}
		
		return slots;
	}
}
