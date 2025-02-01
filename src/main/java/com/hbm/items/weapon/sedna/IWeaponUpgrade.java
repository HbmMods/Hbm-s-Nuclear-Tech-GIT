package com.hbm.items.weapon.sedna;

import net.minecraft.item.ItemStack;

public interface IWeaponUpgrade {

	/** Lower numbers get installed and therefore evaluated first. Important when multiplicative and additive bonuses are supposed to stack */
	public int getModPriority(ItemStack stack);
	/** Which "slots" this upgrade occupies, can be any value, upgrades that have at least one matching slot are incompatible */
	public String[] getSlots(ItemStack stack);
	/** The meat and bones of the upgrade eval. Requires the base value, the held gun, the value's
	 * identifier and the yet unmodified parent (i.e. if the value is part of the receiver, that receiver) */
	public default <T> T eval(T base, ItemStack stack, String key, Object parent) { return base; }
}
