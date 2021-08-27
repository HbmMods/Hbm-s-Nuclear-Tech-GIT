package com.hbm.handler.indexing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.util.Tuple;

import net.minecraft.item.ItemStack;

/**
 * Instead of registering all recycling recipes by hand like a bunch of morons, how about we assign some basic
 * material values to basic items and then detect the rest based on recipes?
 * @author hbm
 *
 */
public class HbmMaterials {
	
	public static enum Mat {
		IRON,
		GOLD,
		STEEL;
		
		private ItemStack recyclesInto;
		
		public void setRecycling(ItemStack recyclesInto) {
			this.recyclesInto = recyclesInto;
		}
		
		public ItemStack getRecycling() {
			return recyclesInto.copy();
		}
	}
	
	private static HashMap<AStack, List<Tuple.Pair<Mat, Double>>> map = new HashMap(); //ACHIEVEMENT GET! Stack three generics inside of each other!
	
	/**
	 * Quickly adds our tuples to the map, or expands the list if it already exists
	 * @param stack
	 * @param tuples
	 */
	public static void registerItem(AStack stack, Tuple.Pair<Mat, Double>... tuples) {
		
		List<Tuple.Pair<Mat, Double>> existing = map.get(stack);
		
		if(existing == null)
			existing = new ArrayList();
		
		existing.addAll(Arrays.asList(tuples));
		
		map.put(stack, existing);
	}
	
	/**
	 * Removes a tuple list from the map, to clear out unwanted auto-generated recipes
	 * @param stack
	 */
	public static void knockTuples(AStack stack) {
		map.remove(stack);
	}
}
