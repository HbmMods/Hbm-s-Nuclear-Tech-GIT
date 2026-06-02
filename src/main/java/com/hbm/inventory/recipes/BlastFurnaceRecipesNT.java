package com.hbm.inventory.recipes;

import static com.hbm.inventory.OreDictManager.*;

import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.inventory.recipes.loader.GenericRecipes;
import com.hbm.items.ModItems;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class BlastFurnaceRecipesNT extends GenericRecipes<GenericRecipe> {
	
	public static final BlastFurnaceRecipesNT INSTANCE = new BlastFurnaceRecipesNT();

	@Override public int inputItemLimit() { return 2; }
	@Override public int inputFluidLimit() { return 0; }
	@Override public int outputItemLimit() { return 2; }
	@Override public int outputFluidLimit() { return 0; }

	@Override
	public GenericRecipe instantiateRecipe(String name) {
		return new GenericRecipe(name);
	}

	@Override
	public void registerDefaults() {

		// 20s per ingot of steel produced
		this.register(new GenericRecipe("blast.steelFromIngot").setDuration(800)
				.inputItems(new OreDictStack(IRON.ingot(), 2), new OreDictStack(KEY_SAND))
				.outputItems(new ItemStack(ModItems.ingot_steel, 2), new ItemStack(ModItems.ingot_raw, 1, Mats.MAT_SLAG.id)));
		this.register(new GenericRecipe("blast.steelFromDust").setDuration(800)
				.inputItems(new OreDictStack(IRON.dust(), 2), new OreDictStack(KEY_SAND))
				.outputItems(new ItemStack(ModItems.ingot_steel, 2), new ItemStack(ModItems.ingot_raw, 1, Mats.MAT_SLAG.id)));
		this.register(new GenericRecipe("blast.steelFromOre").setDuration(800)
				.inputItems(new OreDictStack(IRON.ore()), new OreDictStack(KEY_SAND))
				.outputItems(new ItemStack(ModItems.ingot_steel, 2), new ItemStack(ModItems.ingot_raw, 2, Mats.MAT_SLAG.id)));
		this.register(new GenericRecipe("blast.steelWithFlux").setDuration(1_200)
				.inputItems(new OreDictStack(IRON.ore()), new ComparableStack(ModItems.powder_flux))
				.outputItems(new ItemStack(ModItems.ingot_steel, 3), new ItemStack(ModItems.ingot_raw, 2, Mats.MAT_SLAG.id)));

		this.register(new GenericRecipe("blast.mingrade").setDuration(400)
				.inputItems(new OreDictStack(CU.ingot()), new OreDictStack(REDSTONE.dust()))
				.outputItems(new ItemStack(ModItems.ingot_red_copper, 2)));
		this.register(new GenericRecipe("blast.mingradeDust").setDuration(400)
				.inputItems(new OreDictStack(CU.dust()), new OreDictStack(REDSTONE.dust()))
				.outputItems(new ItemStack(ModItems.ingot_red_copper, 2)));
		this.register(new GenericRecipe("blast.mingradeIngot").setDuration(400)
				.inputItems(new OreDictStack(CU.ingot()), new OreDictStack(REDSTONE.ingot()))
				.outputItems(new ItemStack(ModItems.ingot_red_copper, 2)));
		this.register(new GenericRecipe("blast.mingradeCursed").setDuration(400)
				.inputItems(new OreDictStack(CU.dust()), new OreDictStack(REDSTONE.ingot()))
				.outputItems(new ItemStack(ModItems.ingot_red_copper, 2)));
		this.register(new GenericRecipe("blast.mingradeOre").setDuration(1_200)
				.inputItems(new OreDictStack(CU.ore()), new OreDictStack(REDSTONE.dust(), 6))
				.outputItems(new ItemStack(ModItems.ingot_red_copper, 6), new ItemStack(ModItems.ingot_raw, 1, Mats.MAT_SLAG.id)));

		this.register(new GenericRecipe("blast.meteorSword").setDuration(1_200)
				.inputItems(new OreDictStack(CO.ingot()), new ComparableStack(ModItems.meteorite_sword_hardened, 1))
				.outputItems(new ItemStack(ModItems.meteorite_sword_alloyed, 1)));

		this.register(new GenericRecipe("blast.starmetal").setDuration(600)
				.inputItems(new OreDictStack(BIGMT.ingot()), new ComparableStack(ModItems.powder_meteorite, 1))
				.outputItems(new ItemStack(ModItems.ingot_starmetal, 1)));

		this.register(new GenericRecipe("blast.paa").setDuration(600)
				.inputItems(new OreDictStack(GOLD.ingot()), new ComparableStack(ModItems.plate_mixed, 1))
				.outputItems(new ItemStack(ModItems.plate_paa, 1)));

		this.register(new GenericRecipe("blast.firebrick").setDuration(800)
				.inputItems(new OreDictStack(AL.dust()), new ComparableStack(Items.clay_ball, 7))
				.outputItems(new ItemStack(ModItems.ingot_firebrick, 8)));
		this.register(new GenericRecipe("blast.firebrickLimestone").setDuration(800)
				.inputItems(new OreDictStack(LIMESTONE.ore()), new ComparableStack(Items.clay_ball, 6))
				.outputItems(new ItemStack(ModItems.ingot_firebrick, 8)));
	}

	@Override
	public String getFileName() {
		return "hbmBlastFurnace.json";
	}
	
	public GenericRecipe getRecipe(ItemStack s0, ItemStack s1) {
		
		for(GenericRecipe recipe : this.recipeOrderedList) {
			if(recipe.inputItem.length == 1) {
				if(s0 != null && s1 == null && recipe.inputItem[0].matchesRecipe(s0, false)) return recipe;
				if(s0 == null && s1 != null && recipe.inputItem[0].matchesRecipe(s1, false)) return recipe;
			}
			if(recipe.inputItem.length == 2 && s0 != null && s1 != null) {
				if(recipe.inputItem[0].matchesRecipe(s0, true) && recipe.inputItem[1].matchesRecipe(s1, false)) return recipe;
				if(recipe.inputItem[1].matchesRecipe(s0, true) && recipe.inputItem[0].matchesRecipe(s1, false)) return recipe;
			}
		}
		
		return null;
	}
}
