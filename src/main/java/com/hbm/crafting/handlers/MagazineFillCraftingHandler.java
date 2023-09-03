package com.hbm.crafting.handlers;

import static com.hbm.items.weapon.EnumMagazine.getBulletID;
import static com.hbm.main.CraftingManager.count;

import com.hbm.items.weapon.EnumMagazine;
import com.hbm.items.weapon.ItemMagazine;
import com.hbm.util.EnumUtil;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MagazineFillCraftingHandler extends MagazineSystemCraftingHandlerBase
{

	@Override
	public boolean matches(InventoryCrafting crafting, World world)
	{
		final ItemStack mag = getMagazine(crafting), ammo, link;
		if (mag == null)
			return false;
		final EnumMagazine magazineType = EnumUtil.grabEnumSafely(EnumMagazine.class, mag.getItemDamage());
		
		ammo = getAmmo(magazineType, crafting);
		link = getLink(crafting);
		
		return ammo != null && count(crafting) <= (magazineType.links ? 3 : 2) && ItemMagazine.getUsedAmount(mag) <= magazineType.capacity && (magazineType.links ? link != null : link == null);
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting crafting)
	{
		final ItemStack magazine = getMagazine(crafting), ammo, link;
		if (magazine == null)
			return null;
		final EnumMagazine magazineType = EnumUtil.grabEnumSafely(EnumMagazine.class, magazine.getItemDamage());
		
		ammo = getAmmo(magazineType, crafting);
		link = getLink(crafting);
		
		if (ammo == null || magazineType.links && link == null || !magazineType.links && link != null || ItemMagazine.getUsedAmount(magazine) >= magazineType.capacity)
			return null;
		
		final ItemStack output = magazine.copy();
		output.stackSize = 1;
		ItemMagazine.pushBullet(output, getBulletID(magazineType, ammo), magazineType.capacity);
		return output;
	}

	@Override
	public int getRecipeSize()
	{
		return 3;
	}

}
