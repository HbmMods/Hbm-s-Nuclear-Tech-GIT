package com.hbm.items.weapon.sedna;

import net.minecraft.item.ItemStack;

/**
 * The upgrade manager operates by scraping upgrades from a gun, then iterating over them and evaluating the given value, passing the modified value to successive mods.
 * The way that mods stack (additive vs multiplicative) depends on the order the mod is installed in
 * 
 * @author hbm
 */
public class WeaponUpgradeManager {
	
	//TODO: add caching so this doesn't have to run 15 times per single action
	
	
	public static ItemStack[] getUpgrades(ItemStack stack) {
		return null; // TBI
	}
	
	/** Scrapes all upgrades, iterates over them and evaluates the given value. The parent (i.e. holder of the base value)
	 * is passed for context (so upgrades can differentiate primary and secondary receivers for example). Passing a null
	 * stack causes the base value to be returned. */
	public static <T> T eval(T base, ItemStack stack, String key, Object parent) {
		if(stack == null) return base;
		
		ItemStack[] upgrades = getUpgrades(stack);
		if(upgrades != null) for(ItemStack upgradeStack : upgrades) {
			if(upgradeStack.getItem() instanceof IWeaponUpgrade) {
				IWeaponUpgrade upgrade = (IWeaponUpgrade) upgradeStack.getItem();
				base = upgrade.eval(base, upgradeStack, key, parent);
			}
		}
		
		return base;
	}
}
