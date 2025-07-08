package com.hbm.inventory.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.hbm.inventory.OreDictManager.*;
import static com.hbm.inventory.material.Mats.*;

import com.hbm.inventory.FluidStack;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.inventory.recipes.loader.GenericRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIcon;

import net.minecraft.item.ItemStack;

public class AssemblyMachineRecipes extends GenericRecipes<GenericRecipe> {
	
	public static final AssemblyMachineRecipes INSTANCE = new AssemblyMachineRecipes();

	@Override public int inputItemLimit() { return 12; }
	@Override public int inputFluidLimit() { return 1; }
	@Override public int outputItemLimit() { return 1; }
	@Override public int outputFluidLimit() { return 1; }

	@Override public String getFileName() { return "hbmAsemblyMachine.json"; }
	@Override public GenericRecipe instantiateRecipe(String name) { return new GenericRecipe(name); }

	@Override
	public void registerDefaults() {
		
		this.register(new GenericRecipe("ass.test").setup(100, 1_000)
				.inputItems(new OreDictStack(STEEL.ingot(), 5))
				.outputItems(new ItemStack(ModItems.plate_welded, 1, MAT_STEEL.id)));
	}
	
	public static HashMap getRecipes() {
		HashMap<Object, Object> recipes = new HashMap<Object, Object>();
		
		for(GenericRecipe recipe : INSTANCE.recipeOrderedList) {
			List input = new ArrayList();
			if(recipe.inputItem != null) for(AStack stack : recipe.inputItem) input.add(stack);
			if(recipe.inputFluid != null) for(FluidStack stack : recipe.inputFluid) input.add(ItemFluidIcon.make(stack));
			List output = new ArrayList();
			if(recipe.outputItem != null) for(IOutput stack : recipe.outputItem) output.add(stack.getAllPossibilities());
			if(recipe.outputFluid != null) for(FluidStack stack : recipe.outputFluid) output.add(ItemFluidIcon.make(stack));
			recipes.put(input.toArray(), output.toArray());
		}
		
		return recipes;
	}
}
