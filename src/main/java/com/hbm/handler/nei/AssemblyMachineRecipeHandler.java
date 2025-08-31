package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.AssemblyMachineRecipes;
import com.hbm.inventory.recipes.loader.GenericRecipe;

public class AssemblyMachineRecipeHandler extends NEIGenericRecipeHandler {

	public AssemblyMachineRecipeHandler() {
		super(ModBlocks.machine_assembly_machine.getLocalizedName(), AssemblyMachineRecipes.INSTANCE, ModBlocks.machine_assembly_machine);
	}

	@Override public String getRecipeID() { return "ntmAssemblyMachine"; }
	
	@Override public int getInputXOffset(GenericRecipe recipe, int inputCount) { return recipe.inputItem != null && recipe.inputItem.length > 9 ? 18 : 0; }
	@Override public int getOutputXOffset(GenericRecipe recipe, int outputCount) { return recipe.inputItem != null && recipe.inputItem.length > 9 ? 18 : 0; }
	@Override public int getMachineXOffset(GenericRecipe recipe) { return recipe.inputItem != null && recipe.inputItem.length > 9 ? 18 : 0; }
}
