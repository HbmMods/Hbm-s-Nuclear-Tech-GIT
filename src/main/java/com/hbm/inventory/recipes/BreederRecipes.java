package com.hbm.inventory.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.RecipesCommon;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBreedingRod.*;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class BreederRecipes {

	private static HashMap<ComparableStack, BreederRecipe> recipes = new HashMap();
	
	public static void registerRecipes() {
		
		setRecipe(BreedingRodType.LITHIUM, BreedingRodType.TRITIUM, 200);
		setRecipe(BreedingRodType.CO, BreedingRodType.CO60, 100);
		setRecipe(BreedingRodType.RA226, BreedingRodType.AC227, 300);
		setRecipe(BreedingRodType.TH232, BreedingRodType.THF, 500);
		setRecipe(BreedingRodType.U235, BreedingRodType.NP237, 300);
		setRecipe(BreedingRodType.NP237, BreedingRodType.PU238, 200);
		setRecipe(BreedingRodType.PU238, BreedingRodType.PU239, 1000);
		setRecipe(BreedingRodType.U238, BreedingRodType.RGP, 300);
		setRecipe(BreedingRodType.URANIUM, BreedingRodType.RGP, 200);
		setRecipe(BreedingRodType.RGP, BreedingRodType.WASTE, 200);
		
//		recipes.put(new ComparableStack(ModItems.meteorite_sword_etched), new BreederRecipe(new ItemStack(ModItems.meteorite_sword_bred), 1000));
	}
	
	/** Sets recipes for single, dual, and quad rods **/
	public static void setRecipe(BreedingRodType inputType, BreedingRodType outputType, int flux) {
		recipes.put(new ComparableStack(new ItemStack(ModItems.rod, 1, inputType.ordinal())), new BreederRecipe(new ItemStack(ModItems.rod, 1, outputType.ordinal()), flux));
		recipes.put(new ComparableStack(new ItemStack(ModItems.rod_dual, 1, inputType.ordinal())), new BreederRecipe(new ItemStack(ModItems.rod_dual, 1, outputType.ordinal()), flux * 2));
		recipes.put(new ComparableStack(new ItemStack(ModItems.rod_quad, 1, inputType.ordinal())), new BreederRecipe(new ItemStack(ModItems.rod_quad, 1, outputType.ordinal()), flux * 3));
	}
	
	public static HashMap<ItemStack, BreederRecipe> getAllRecipes() {
		
		HashMap<ItemStack, BreederRecipe> map = new HashMap();
		
		for(Map.Entry<ComparableStack, BreederRecipe> recipe : recipes.entrySet()) {
			map.put(recipe.getKey().toStack(), recipe.getValue());
		}
		
		return map;
	}
	
	public static BreederRecipe getOutput(ItemStack stack) {
		
		if(stack == null)
			return null;
		
		ComparableStack sta = new ComparableStack(stack).makeSingular();
		return BreederRecipes.recipes.get(sta);
	}
	
	//nicer than opaque object arrays
	public static class BreederRecipe {
		
		public ItemStack output;
		public int flux;
		
		public BreederRecipe(Item output, int flux) {
			this(new ItemStack(output), flux);
		}
		
		public BreederRecipe(ItemStack output, int flux) {
			this.output = output;
			this.flux = flux;
		}
	}

}
