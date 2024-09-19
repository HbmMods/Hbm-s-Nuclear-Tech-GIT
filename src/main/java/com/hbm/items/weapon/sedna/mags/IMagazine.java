package com.hbm.items.weapon.sedna.mags;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * The magazine simply provides the receiver it's attached to with ammo, the receiver does not care where it comes from.
 * Therefore it is the mag's responsibility to handle reloading, any type restrictions as well as belt-like action from "magless" guns.
 * 
 * @author hbm
 */
public interface IMagazine {

	/** What ammo is loaded currently */
	public Object getType(ItemStack stack);
	/** Sets the mag's ammo type */
	public void setType(ItemStack stack, Object type);
	/** How much ammo this mag can carry */
	public int getCapacity(ItemStack stack);
	/** How much ammo is currently loaded */
	public int getAmount(ItemStack stack);
	/** Sets the mag's ammo level */
	public void setAmount(ItemStack stack, int amount);
	/** If a reload can even be initiated, i.e. the player even has bullets to load */
	public boolean canReload(ItemStack stack, EntityPlayer player);
	/** The action done at the end of one reload cycle, either loading one shell or replacing the whole mag */
	public void reloadAction(ItemStack stack, EntityPlayer player);
	/** The stack that should be displayed for the ammo HUD */
	public ItemStack getIcon(ItemStack stack);
}
