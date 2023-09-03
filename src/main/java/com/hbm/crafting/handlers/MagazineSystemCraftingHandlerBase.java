package com.hbm.crafting.handlers;

import static com.hbm.items.weapon.EnumMagazine.isValidAmmo;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.EnumMagazine;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

public abstract class MagazineSystemCraftingHandlerBase implements IRecipe
{

	@Override
	public ItemStack getRecipeOutput()
	{
		return null;
	}
	
	static ItemStack getAmmo(EnumMagazine magazineType, InventoryCrafting crafting)
	{
		for (int i = 0; i < 9; i++)
		{
			final ItemStack stack = crafting.getStackInRowAndColumn(i % 3, i / 3);
			if (stack != null && isValidAmmo(magazineType, stack))
				return stack;
		}
		return null;
	}

	static ItemStack getLink(InventoryCrafting crafting)
	{
		for (int i = 0; i < 9; i++)
		{
			final ItemStack stack = crafting.getStackInRowAndColumn(i % 3, i / 3);
			if (stack != null && stack.getItem() == ModItems.ammo_link)
				return stack;
		}
		return null;
	}

	static ItemStack getMagazine(InventoryCrafting crafting)
	{
		for (int i = 0; i < 9; i++)
		{
			final ItemStack stack = crafting.getStackInRowAndColumn(i % 3, i / 3);
			if (stack != null && stack.getItem() == ModItems.gun_magazine)
				return stack;
		}
		return null;
	}

}
