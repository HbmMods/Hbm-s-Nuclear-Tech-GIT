package com.hbm.items.weapon.sedna.mags;

import net.minecraft.item.ItemStack;

public interface IMagazine {

	/** What ammo is loaded currently */
	public Object getType(ItemStack stack);
	/** How much ammo this mag can carry */
	public int getCapacity(ItemStack stack);
	/** How much ammo is currently loaded */
	public int getAmount(ItemStack stack);
	/** Sets the mag's ammo level */
	public void setAmount(ItemStack stack, int amount);
	/** The action done at the end of one reload cycle, either loading one shell or replacing the whole mag */
	public void reloadAction(ItemStack stack);
	/** The stack that should be displayed for the ammo HUD */
	public ItemStack getIcon(ItemStack stack);
}
