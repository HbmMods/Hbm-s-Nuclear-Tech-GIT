package com.hbm.handler.nei;

import java.util.HashMap;

import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.machine.ItemRBMKRod;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RBMKRodDisassemblyHandler extends NEIUniversalHandler {

	public RBMKRodDisassemblyHandler() {
		super("RBMK Rod Disassembly", Blocks.crafting_table, getRecipes());
	}

	@Override
	public String getKey() {
		return "ntmRBMKDisassembly";
	}

	public static HashMap<ComparableStack, ItemStack> getRecipes() {
		HashMap<ComparableStack, ItemStack> map = new HashMap<>();

		for(ItemRBMKRod rod : ItemRBMKRod.craftableRods) {
			for(int enrichment = 0; enrichment <= 4; enrichment++) {
				ItemStack result = new ItemStack(rod.pellet, 8, enrichment);
				map.put(new ComparableStackHeat(rod, false, enrichment, false), result);

				if(rod.pellet.isXenonEnabled()) {
					ItemStack resultPoison = new ItemStack(rod.pellet, 8, enrichment + 5);
					map.put(new ComparableStackHeat(rod, false, enrichment, true), resultPoison);
				}
			}
		}

		return map;
	}

	// Don't show recipes for hot rods (which will cause it to only show cooling recipes)
	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		if(ingredient != null && ingredient.getItem() != null && ingredient.getItem() instanceof ItemRBMKRod) {
			if(ItemRBMKRod.getCoreHeat(ingredient) > 50 || ItemRBMKRod.getHullHeat(ingredient) > 50) return;
		}

		super.loadUsageRecipes(ingredient);
	}

	public static class ComparableStackHeat extends ComparableStack {

		// I was going to filter by all of these, but found it is just best to show all possible recipes for everything but heat
		private final boolean isHot;
		private final int enrichment;
		private final boolean hasPoison;

		public ComparableStackHeat(Item item, boolean isHot) {
			this(item, isHot, -1, false);
		}

		public ComparableStackHeat(Item item, boolean isHot, int enrichment, boolean hasPoison) {
			super(item);
			this.isHot = isHot;
			this.enrichment = enrichment;
			this.hasPoison = hasPoison;
		}

		public ItemStack toStack() {
			ItemStack stack = super.toStack();
			ItemRBMKRod rod = (ItemRBMKRod) stack.getItem();
			if(enrichment >= 0) {
				ItemRBMKRod.setYield(stack, Math.min(1 - ((double) enrichment) / 5, 0.99) * rod.yield);
			} else {
				ItemRBMKRod.setYield(stack, 0.2 * rod.yield);
			}
			if(hasPoison) ItemRBMKRod.setPoison(stack, 50);
			if(!isHot) return stack;
			ItemRBMKRod.setCoreHeat(stack, 100);
			ItemRBMKRod.setHullHeat(stack, 50);
			return stack;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime * result + enrichment;
			result = prime * result + (hasPoison ? 1 : 0);
			return result;
		}

	}
}