package com.hbm.crafting.handlers;

import static com.hbm.items.weapon.EnumMagazine.getBulletID;
import static com.hbm.main.CraftingManager.count;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.EnumMagazine;
import com.hbm.items.weapon.ItemMagazine;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BeltCreationCraftingHandler extends MagazineSystemCraftingHandlerBase
{

	@Override
	public boolean matches(InventoryCrafting crafting, World world)
	{
		for (EnumMagazine magazineType : EnumMagazine.values())
		{
			if (!magazineType.belt)
				continue;
			
			final ItemStack ammo = getAmmo(magazineType, crafting), link = getLink(crafting);
			if (ammo == null)
				continue;
			
			if (count(crafting) <= (magazineType.links ? 2 : 1) && (magazineType.links ? link != null : link == null))
				return true;
		}
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting crafting)
	{
		for (EnumMagazine magazineType : EnumMagazine.values())
		{
			if (!magazineType.belt)
				continue;
			
			final ItemStack ammo = getAmmo(magazineType, crafting), link = getLink(crafting);
			
			if (ammo != null && count(crafting) <= (magazineType.links ? 2 : 1) && magazineType.links ? link != null : link == null)
			{
				final ItemStack belt = ModItems.gun_magazine.stackFromEnum(magazineType);
				ItemMagazine.initNBT(belt, magazineType.capacity);
				ItemMagazine.pushBullet(belt, getBulletID(magazineType, ammo), magazineType.capacity);
				return belt;
			}
		}
		return null;
	}

	@Override
	public int getRecipeSize()
	{
		return 2;
	}

}
