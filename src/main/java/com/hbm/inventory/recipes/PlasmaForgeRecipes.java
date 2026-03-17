package com.hbm.inventory.recipes;

import static com.hbm.inventory.OreDictManager.BSCCO;
import static com.hbm.inventory.OreDictManager.SBD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.inventory.recipes.loader.GenericRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.ItemEnums.EnumExpensiveType;
import com.hbm.items.ItemGenericPart.EnumPartType;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.items.machine.ItemCircuit.EnumCircuitType;

import net.minecraft.item.ItemStack;

public class PlasmaForgeRecipes extends GenericRecipes<GenericRecipe> {

	public static final PlasmaForgeRecipes INSTANCE = new PlasmaForgeRecipes();

	@Override public int inputItemLimit() { return 12; }
	@Override public int inputFluidLimit() { return 1; }
	@Override public int outputItemLimit() { return 1; }
	@Override public int outputFluidLimit() { return 0; }

	@Override public String getFileName() { return "hbmPlasmaForge.json"; }
	@Override public GenericRecipe instantiateRecipe(String name) { return new GenericRecipe(name); }

	@Override
	public void registerDefaults() {

		this.register(new GenericRecipe("plsm.gerald").setup(6_000, 100).outputItems(new ItemStack(ModItems.sat_gerald, 1))
				.inputItems(new OreDictStack(SBD.plateCast(), 64),
						new OreDictStack(SBD.plateCast(), 64),
						new OreDictStack(BSCCO.wireDense(), 64),
						new OreDictStack(BSCCO.wireDense(), 64),
						new ComparableStack(ModBlocks.det_nuke, 64),
						new ComparableStack(ModItems.part_generic, 64, EnumPartType.HDE),
						new ComparableStack(ModItems.part_generic, 64, EnumPartType.HDE),
						new ComparableStack(ModItems.part_generic, 64, EnumPartType.HDE),
						new ComparableStack(ModItems.part_generic, 64, EnumPartType.HDE),
						new ComparableStack(ModItems.circuit, 64, EnumCircuitType.CONTROLLER_QUANTUM),
						new ComparableStack(ModItems.coin_ufo, 1))
				.inputItemsEx(new OreDictStack(SBD.plateCast(), 64),
						new OreDictStack(BSCCO.wireDense(), 64),
						new ComparableStack(ModBlocks.det_nuke, 64),
						new ComparableStack(ModItems.item_expensive, 64, EnumExpensiveType.BRONZE_TUBES),
						new ComparableStack(ModItems.item_expensive, 64, EnumExpensiveType.BRONZE_TUBES),
						new ComparableStack(ModItems.item_expensive, 64, EnumExpensiveType.BRONZE_TUBES),
						new ComparableStack(ModItems.part_generic, 64, EnumPartType.HDE),
						new ComparableStack(ModItems.part_generic, 64, EnumPartType.HDE),
						new ComparableStack(ModItems.part_generic, 64, EnumPartType.HDE),
						new ComparableStack(ModItems.circuit, 64, EnumCircuitType.CONTROLLER_QUANTUM),
						new ComparableStack(ModItems.item_expensive, 64, EnumExpensiveType.COMPUTER),
						new ComparableStack(ModItems.coin_ufo, 1)).setPools(GenericRecipes.POOL_PREFIX_DISCOVER + "gerald"));
	}

	public static HashMap getRecipes() {
		HashMap<Object, Object> recipes = new HashMap<Object, Object>();

		for(GenericRecipe recipe : INSTANCE.recipeOrderedList) {
			List input = new ArrayList();
			if(recipe.inputItem != null) for(AStack stack : recipe.inputItem) input.add(stack);
			if(recipe.inputFluid != null) for(FluidStack stack : recipe.inputFluid) input.add(ItemFluidIcon.make(stack));
			List output = new ArrayList();
			if(recipe.outputItem != null) for(IOutput stack : recipe.outputItem) output.add(stack.getAllPossibilities());
			recipes.put(input.toArray(), output.toArray());
		}

		return recipes;
	}
}
