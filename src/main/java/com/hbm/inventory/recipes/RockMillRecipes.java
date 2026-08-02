package com.hbm.inventory.recipes;

import static com.hbm.inventory.OreDictManager.*;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.inventory.recipes.loader.GenericRecipes;
import com.hbm.items.ModItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class RockMillRecipes extends GenericRecipes<GenericRecipe> {

	public static final RockMillRecipes INSTANCE = new RockMillRecipes();

	@Override public int inputItemLimit() { return 3; }
	@Override public int inputFluidLimit() { return 1; }
	@Override public int outputItemLimit() { return 3; }
	@Override public int outputFluidLimit() { return 1; }

	@Override public String getFileName() { return "hbmRockMill.json"; }
	@Override public GenericRecipe instantiateRecipe(String name) { return new GenericRecipe(name); }

	@Override
	public void registerDefaults() {
		
		int consumption = 25;
		int duraShort = 100;
		int duraLong = 200;
		
		this.register(new GenericRecipe("rock.cobble").setupNamed(duraShort, consumption).setNameWrapper("rock.crushing")
				.inputItems(new OreDictStack(KEY_COBBLESTONE))
				.inputFluids(new FluidStack(Fluids.WATER, 250))
				.outputItems(new ChanceOutputMulti(
						new ChanceOutput(new ItemStack(Blocks.gravel), 95),
						new ChanceOutput(new ItemStack(ModItems.powder_quartz), 5)
				)).setIconToFirstIngredient());
		
		this.register(new GenericRecipe("rock.gravel").setupNamed(duraShort, consumption).setNameWrapper("rock.crushing")
				.inputItems(new ComparableStack(Blocks.gravel))
				.inputFluids(new FluidStack(Fluids.WATER, 250))
				.outputItems(new ChanceOutputMulti(
						new ChanceOutput(new ItemStack(Blocks.sand), 75),
						new ChanceOutput(new ItemStack(Items.flint), 20),
						new ChanceOutput(new ItemStack(ModItems.powder_boron), 5)
				)).setIconToFirstIngredient());
		
		this.register(new GenericRecipe("rock.bauxite").setupNamed(duraLong, consumption).setNameWrapper("rock.crushing")
				.inputItems(new OreDictStack(BAUXITE.ore()))
				.inputFluids(new FluidStack(Fluids.WATER, 250))
				.outputItems(new ChanceOutputMulti(
						new ChanceOutput(new ItemStack(Blocks.gravel), 25),
						new ChanceOutput(new ItemStack(Items.clay_ball), 25),
						new ChanceOutput(new ItemStack(ModBlocks.stone_resource, 1, 2), 25),
						new ChanceOutput(new ItemStack(ModBlocks.ore_titanium, 1, 2), 25)
				)).setIconToFirstIngredient());
	}
}
