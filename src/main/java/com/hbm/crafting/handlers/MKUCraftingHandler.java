package com.hbm.crafting.handlers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.hbm.items.ModItems;
import com.hbm.items.special.ItemBookLore;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class MKUCraftingHandler implements IRecipe {

	public static ItemStack[] MKURecipe;
	private static long lastSeed;

	@Override
	public boolean matches(InventoryCrafting inventory, World world) {

		if(world == null)
			return false;

		if(MKURecipe == null || world.getSeed() != lastSeed)
			generateRecipe(world);

		for(int i = 0; i < 9; i++) {
			ItemStack stack = inventory.getStackInRowAndColumn(i % 3, i / 3);
			ItemStack recipe = MKURecipe[i];

			if(stack == null && recipe == null)
				continue;

			if(stack != null && recipe != null && stack.getItem() == recipe.getItem() && stack.getItemDamage() == recipe.getItemDamage())
				continue;

			return false;
		}

		return true;
	}

	public static void generateRecipe(World world) {
		Random rand = new Random(world.getSeed());

		if(lastSeed == world.getSeed() && MKURecipe != null)
			return;

		lastSeed = world.getSeed();

		List<ItemStack> list = Arrays.asList(new ItemStack[] {
				new ItemStack(ModItems.powder_iodine),
				new ItemStack(ModItems.powder_fire),
				new ItemStack(ModItems.dust),
				new ItemStack(ModItems.ingot_mercury),
				new ItemStack(ModItems.morning_glory),
				new ItemStack(ModItems.syringe_metal_empty),
				null,
				null,
				null
		});

		Collections.shuffle(list, rand);

		MKURecipe = list.toArray(new ItemStack[9]);
	}

	public static Item getMKUItem(World world) {
		switch(world.rand.nextInt(6)) {
		case 0: return ModItems.powder_iodine;
		case 1: return ModItems.powder_fire;
		case 2: return ModItems.dust;
		case 3: return ModItems.ingot_mercury;
		case 4: return ModItems.morning_glory;
		case 5: return ModItems.syringe_metal_empty;
		default: return ModItems.flame_pony;
		}
	}

	public static ItemStack generateBook(World world, Item mkuItem) {
		MKUCraftingHandler.generateRecipe(world);
		ItemStack[] recipe = MKUCraftingHandler.MKURecipe;

		if(recipe == null) return new ItemStack(ModItems.flame_pony);

		String key = null;
		int pages = 1;
		if(mkuItem == ModItems.powder_iodine) { key = "book_iodine"; pages = 3; }
		if(mkuItem == ModItems.powder_fire) { key = "book_phosphorous"; pages = 2; }
		if(mkuItem == ModItems.dust) { key = "book_dust"; pages = 3; }
		if(mkuItem == ModItems.ingot_mercury) { key = "book_mercury"; pages = 2; }
		if(mkuItem == ModItems.morning_glory) { key = "book_flower"; pages = 2; }
		if(mkuItem == ModItems.syringe_metal_empty) { key = "book_syringe"; pages = 2; }

		if(key == null) return new ItemStack(ModItems.flame_pony);

		int s = 1;
		for(int i = 0; i < 9; i++) {
			if(recipe[i] != null && recipe[i].getItem() == mkuItem) {
				s = i + 1; break;
			}
		}

		ItemStack book = ItemBookLore.createBook(key, pages, 0x271E44, 0xFBFFF4);
		ItemBookLore.addArgs(book, pages - 1, String.valueOf(s));

		return book;
	}

	@Override
	public int getRecipeSize() {
		return 6;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventory) {
		return getRecipeOutput();
	}

	@Override
	public ItemStack getRecipeOutput() {
		return new ItemStack(ModItems.syringe_mkunicorn);
	}
}