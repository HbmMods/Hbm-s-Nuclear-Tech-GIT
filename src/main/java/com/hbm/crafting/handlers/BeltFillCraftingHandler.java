package com.hbm.crafting.handlers;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.EnumMagazine;
import com.hbm.items.weapon.ItemMagazine;
import com.hbm.util.EnumUtil;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BeltFillCraftingHandler extends MagazineSystemCraftingHandlerBase
{

	@Override
	public boolean matches(InventoryCrafting crafting, World world)
	{
		final ItemStack belt = getFirstBelt(crafting);
		if (belt == null)
			return false;
		
		final EnumMagazine magazineType = EnumUtil.grabEnumSafely(EnumMagazine.class, belt.getItemDamage());
		
		if (getLink(crafting) == null ? magazineType.links : !magazineType.links)
			return false;
		
		ItemStack ammo = getAmmo(magazineType, crafting);
		
		return ammo != null && ItemMagazine.getUsedAmount(ammo) <= magazineType.capacity;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting crafting)
	{
		final ItemStack belt = getFirstBelt(crafting);
		if (belt == null)
			return null;
		
		final EnumMagazine magazineType = EnumUtil.grabEnumSafely(EnumMagazine.class, belt.getItemDamage());
		
		if (getLink(crafting) == null ? magazineType.links : !magazineType.links)
			return null;
		
		final ItemStack ammo = getAmmo(magazineType, crafting);
		if (ammo == null || ItemMagazine.getUsedAmount(belt) <= magazineType.capacity)
			return null;
		int bulletID = -1;
		
		for (int index = 0; index < magazineType.bullets.size(); index++)
		{
			bulletID = magazineType.bullets.get(index);
			if (BulletConfigSyncingUtil.pullConfig(bulletID).ammo.matchesRecipe(ammo, true))
			{
				bulletID = index;
				break;
			}
		}
		
		final ItemStack updatedBelt = belt.copy();
		ItemMagazine.pushBullet(updatedBelt, bulletID, magazineType.capacity);
		return ammo;
	}

	@Override
	public int getRecipeSize()
	{
		return 3;
	}

	private static ItemStack getFirstBelt(InventoryCrafting crafting)
	{
		for (int i = 0; i < 9; i++)
		{
			final ItemStack stack = crafting.getStackInRowAndColumn(i % 3, i / 3);
			if (stack == null || stack.getItem() == ModItems.gun_magazine)
				continue;
			final EnumMagazine magazine = EnumUtil.grabEnumSafely(EnumMagazine.class, stack.getItemDamage());
			if (magazine.belt)
				return stack;
		}
		return null;
	}
	
}
