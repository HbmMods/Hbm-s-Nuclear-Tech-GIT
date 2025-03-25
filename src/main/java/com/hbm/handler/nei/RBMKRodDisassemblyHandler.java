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

	public static class ComparableStackHeat extends ComparableStack {

		// I was going to filter by these, but found it is just best to show all possible recipes for everything but heat
		// that and... I'm actually stumped on how to filter by NBT, seeing as both `isApplicable` and `matchesRecipe` don't seem to work
		private final boolean matchHot;
		private final int matchEnrichment;
		private final boolean matchPoison;

		public ComparableStackHeat(Item item, boolean matchHot) {
			this(item, matchHot, -1, false);
		}

		public ComparableStackHeat(Item item, boolean matchHot, int matchEnrichment, boolean matchPoison) {
			super(item);
			this.matchHot = matchHot;
			this.matchEnrichment = matchEnrichment;
			this.matchPoison = matchPoison;
		}

		public ItemStack toStack() {
			ItemStack stack = super.toStack();
			ItemRBMKRod rod = (ItemRBMKRod) stack.getItem();
			if(matchEnrichment >= 0) {
				ItemRBMKRod.setYield(stack, Math.max(1 - ((double) matchEnrichment) / 5, 0.05) * rod.yield);
			} else {
				ItemRBMKRod.setYield(stack, 0.2 * rod.yield);
			}
			if(matchPoison) ItemRBMKRod.setPoison(stack, 50);
			if(!matchHot) return stack;
			ItemRBMKRod.setCoreHeat(stack, 100);
			ItemRBMKRod.setHullHeat(stack, 50);
			return stack;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime * result + matchEnrichment;
			result = prime * result + (matchPoison ? 1 : 0);
			return result;
		}

	}
}