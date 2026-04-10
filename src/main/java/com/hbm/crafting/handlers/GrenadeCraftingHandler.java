package com.hbm.crafting.handlers;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.grenade.ItemGrenadeExtra.EnumGrenadeExtra;
import com.hbm.items.weapon.grenade.ItemGrenadeFilling.EnumGrenadeFilling;
import com.hbm.items.weapon.grenade.ItemGrenadeFuze.EnumGrenadeFuze;
import com.hbm.items.weapon.grenade.ItemGrenadeShell.EnumGrenadeShell;
import com.hbm.items.weapon.grenade.ItemGrenadeUniversal;
import com.hbm.util.EnumUtil;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class GrenadeCraftingHandler implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		if(hasForeignObject(inv)) return false; // can't be non-grenade items and can't be more than 4 items total
		EnumGrenadeShell shell = getFirst(inv, ModItems.grenade_shell, EnumGrenadeShell.class); // only one shell, null otherwise
		EnumGrenadeFilling filling = getFirst(inv, ModItems.grenade_filling, EnumGrenadeFilling.class); // only one filling, null otherwise
		EnumGrenadeFuze fuze = getFirst(inv, ModItems.grenade_fuze, EnumGrenadeFuze.class); // only one fuze, null otherwise
		// this leaves the extra unaccounted for, but the restrictions we put in place will allow exactly one without dedicated check
		return shell != null && filling != null && fuze != null;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		EnumGrenadeShell shell = getFirst(inv, ModItems.grenade_shell, EnumGrenadeShell.class);
		EnumGrenadeFilling filling = getFirst(inv, ModItems.grenade_filling, EnumGrenadeFilling.class);
		EnumGrenadeFuze fuze = getFirst(inv, ModItems.grenade_fuze, EnumGrenadeFuze.class);
		EnumGrenadeExtra extra = getFirst(inv, ModItems.grenade_extra, EnumGrenadeExtra.class); // if this is null, then we don't care, MAKE works with a null extra too
		return ItemGrenadeUniversal.make(shell, filling, fuze, extra);
	}

	@Override
	public int getRecipeSize() {
		return 4;
	}
	
	// why write the same crap four times when you can just use your massive cock instead
	public static <T extends Enum> T getFirst(InventoryCrafting inv, Item itemType, Class<? extends T> type) { // god i love generics
		T firstShell = null;
		
		for(int i = 0; i < 9; i++) {
			ItemStack stack = inv.getStackInRowAndColumn(i % 3, i / 3);
			if(stack == null) continue;
			if(stack.getItem() == itemType) {
				if(firstShell != null) return null;
				firstShell = EnumUtil.grabEnumSafely(type, stack.getItemDamage());
			}
		}
		
		return firstShell;
	}
	
	// this should weed out non-grenade grids quickly as to not waste too much CPU time
	public static boolean hasForeignObject(InventoryCrafting inv) {
		int itemCount = 0;
		for(int i = 0; i < 9; i++) {
			ItemStack stack = inv.getStackInRowAndColumn(i % 3, i / 3);
			if(stack == null) continue;
			if(stack.getItem() != ModItems.grenade_shell &&
					stack.getItem() != ModItems.grenade_filling &&
					stack.getItem() != ModItems.grenade_fuze &&
					stack.getItem() != ModItems.grenade_extra) return true;
			itemCount++;
			if(itemCount > 4) return true;
		}
		return false;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return new ItemStack(ModItems.grenade_universal);
	}
}
