package com.hbm.tileentity.machine.rbmk;

import net.minecraft.item.ItemStack;

public interface IRBMKLoadable {

	/**
	 * @param toLoad the ItemStack that should be loaded
	 * @return TRUE if the provided ItemStack can be inserted into the column
	 */
	public boolean canLoad(ItemStack toLoad);
	
	/**
	 * Loads the given ItemStack, canLoad check necessary first
	 * @param toLoad
	 */
	public void load(ItemStack toLoad);
	
	/**
	 * @return TRUE if the column contains an ItemStack that can be unloaded
	 */
	public boolean canUnload();
	
	/**
	 * @return The next ItemStack to be unloaded
	 */
	public ItemStack provideNext();
	
	/**
	 * Removes the next ItemStack as part of the unloading process
	 */
	public void unload();
}
