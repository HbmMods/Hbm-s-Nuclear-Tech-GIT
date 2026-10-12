package com.hbm.inventory.recipes;

import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.recipes.loader.GenericRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemCircuit.EnumCircuitType;
import com.hbm.items.machine.ItemOrbitalAssembly.EnumOrbitalAssembly;

import net.minecraft.item.ItemStack;

public class SpaceAssemblerRecipes extends GenericRecipes<GenericRecipeNoPower> {

	public static final SpaceAssemblerRecipes INSTANCE = new SpaceAssemblerRecipes();

	@Override public int inputItemLimit() { return 1; }
	@Override public int inputFluidLimit() { return 0; }
	@Override public int outputItemLimit() { return 9; }
	@Override public int outputFluidLimit() { return 0; }

	@Override
	public GenericRecipeNoPower instantiateRecipe(String name) {
		return new GenericRecipeNoPower(name);
	}

	@Override
	public void registerDefaults() {
		
		int minute = 60 * 20;
		
		this.register((GenericRecipeNoPower) new GenericRecipeNoPower("space.crystalcircuit").setup(3 * minute, 0L)
				.inputItems(new ComparableStack(ModItems.orbital_assembly, 1, EnumOrbitalAssembly.CRYSTAL_CIRCUIT))
				.outputItems(DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CRYSTAL)));
	}

	@Override
	public String getFileName() {
		return "hbmSpaceAssembler.json";
	}

	public GenericRecipeNoPower getRecipe(ItemStack stack) {

		for(GenericRecipeNoPower recipe : this.recipeOrderedList) {
			if(recipe.inputItem.length == 1 && recipe.inputItem[0].matchesRecipe(stack, true)) return recipe;
		}
		return null;
	}
}
