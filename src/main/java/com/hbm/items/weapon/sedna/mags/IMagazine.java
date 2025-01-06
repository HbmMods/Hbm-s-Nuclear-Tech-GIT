package com.hbm.items.weapon.sedna.mags;

import com.hbm.particle.SpentCasing;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/**
 * The magazine simply provides the receiver it's attached to with ammo, the receiver does not care where it comes from.
 * Therefore it is the mag's responsibility to handle reloading, any type restrictions as well as belt-like action from "magless" guns.
 * 
 * @author hbm
 */
public interface IMagazine<T> {

	/** What ammo is loaded currently */
	public T getType(ItemStack stack, IInventory inventory);
	/** Sets the mag's ammo type */
	public void setType(ItemStack stack, T type);
	/** How much ammo this mag can carry */
	public int getCapacity(ItemStack stack);
	/** How much ammo is currently loaded */
	public int getAmount(ItemStack stack, IInventory inventory);
	/** Sets the mag's ammo level */
	public void setAmount(ItemStack stack, int amount);
	/** removes the specified amount fro mthe magazine */
	public void useUpAmmo(ItemStack stack, IInventory inventory, int amount);
	/** If a reload can even be initiated, i.e. the player even has bullets to load, inventory can be null */
	public boolean canReload(ItemStack stack, IInventory inventory);
	/** The action done at the end of one reload cycle, either loading one shell or replacing the whole mag, inventory can be null */
	public void reloadAction(ItemStack stack, IInventory inventory);
	/** The stack that should be displayed for the ammo HUD */
	public ItemStack getIconForHUD(ItemStack stack, EntityPlayer player);
	/** It explains itself */
	public String reportAmmoStateForHUD(ItemStack stack, EntityPlayer player);
	/** Casing config to use then ejecting */
	public SpentCasing getCasing(ItemStack stack, IInventory inventory);
	/** When reloading, remember the amount before reload is initiated */
	public void setAmountBeforeReload(ItemStack stack, int amount);
	/** Amount of rounds before reload has started. Do note that the NBT stack sync likely arrives
	 * after the animation packets, so for RELOAD type anims, use the live ammo count instead! */
	public int getAmountBeforeReload(ItemStack stack);
	/** Sets amount of ammo after each reload operation */
	public void setAmountAfterReload(ItemStack stack, int amount);
	/** Cached amount of ammo after the most recent reload */
	public int getAmountAfterReload(ItemStack stack);
}
