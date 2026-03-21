package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.AssemblyMachineRecipes;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.inventory.recipes.loader.GenericRecipes;

import net.minecraft.block.Block;

public class AssemblyMachineRecipeHandler extends NEIGenericRecipeHandler {

	public AssemblyMachineRecipeHandler() {
		super(ModBlocks.machine_assembly_machine.getLocalizedName(), AssemblyMachineRecipes.INSTANCE, ModBlocks.machine_assembly_machine);
	}

	public AssemblyMachineRecipeHandler(String displayName, GenericRecipes recipeSet, Block... machines) { super(displayName, recipeSet, machines); }

	@Override public String getRecipeID() { return "ntmAssemblyMachine"; }
	
	@Override public int getInputXOffset(GenericRecipe recipe, int inputCount) { return inputCount > 12 ? -9 : inputCount > 9 ? 18 : 0; }
	@Override public int getOutputXOffset(GenericRecipe recipe, int outputCount) { return getOffset(recipe); }
	@Override public int getMachineXOffset(GenericRecipe recipe) { return getOffset(recipe); }
	
	public int getOffset(GenericRecipe recipe) {
		int length = 0;
		if(recipe.inputItem != null) length += recipe.inputItem.length;
		if(recipe.inputFluid != null) length += recipe.inputFluid.length;
		if(length > 12) return 27;
		if(length > 9) return 18;
		return 0;
	}
}
