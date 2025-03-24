package com.hbm.items.weapon.sedna.mods;

import net.minecraft.item.ItemStack;

public interface IWeaponMod {

	/** Lower numbers get installed and therefore evaluated first. Important when multiplicative and additive bonuses are supposed to stack */
	public int getModPriority();
	public String[] getSlots();
	/** The meat and bones of the upgrade eval. Requires the base value, the held gun, the value's
	 * identifier and the yet unmodified parent (i.e. if the value is part of the receiver, that receiver) */
	public <T> T eval(T base, ItemStack gun, String key, Object parent);
	
	public default void onInstall(ItemStack gun, ItemStack mod, int index) { }
	public default void onUninstall(ItemStack gun, ItemStack mod, int index) { }
}
