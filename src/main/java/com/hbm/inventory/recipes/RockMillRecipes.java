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
		
		String groupCrush = "autoswitch.crushing";
		
		this.register(new GenericRecipe("rock.cobble").setup(duraShort, consumption).setNameWrapper("rock.crushing")
				.inputItems(new OreDictStack(KEY_COBBLESTONE))
				.inputFluids(new FluidStack(Fluids.WATER, 250))
				.outputItems(new ChanceOutputMulti(
						new ChanceOutput(new ItemStack(Blocks.gravel), 93),
						new ChanceOutput(new ItemStack(ModItems.powder_quartz), 5),
					new ChanceOutput(new ItemStack(ModItems.mineral_clump), 2)
				)).setIconToFirstIngredient().setGroup(groupCrush, INSTANCE));
		
		this.register(new GenericRecipe("rock.gravel").setup(duraShort, consumption).setNameWrapper("rock.crushing")
				.inputItems(new ComparableStack(Blocks.gravel))
				.inputFluids(new FluidStack(Fluids.WATER, 250))
				.outputItems(new ChanceOutputMulti(
						new ChanceOutput(new ItemStack(Blocks.sand), 75),
						new ChanceOutput(new ItemStack(Items.flint), 20),
						new ChanceOutput(new ItemStack(ModItems.powder_boron), 5)
				)).setIconToFirstIngredient().setGroup(groupCrush, INSTANCE));
		
		this.register(new GenericRecipe("rock.sand").setup(duraShort, consumption).setNameWrapper("rock.crushing")
				.inputItems(new OreDictStack(KEY_SAND))
				.inputFluids(new FluidStack(Fluids.WATER, 250))
				.outputItems(new ChanceOutputMulti(
						new ChanceOutput(new ItemStack(ModItems.dust), 90),
						new ChanceOutput(new ItemStack(ModItems.powder_calcium), 5),
						new ChanceOutput(new ItemStack(ModItems.fluorite), 5)
				)).setIconToFirstIngredient().setGroup(groupCrush, INSTANCE));
		
		this.register(new GenericRecipe("rock.netherrack").setup(duraShort, consumption).setNameWrapper("rock.crushing")
				.inputItems(new ComparableStack(Blocks.netherrack))
				.inputFluids(new FluidStack(Fluids.WATER, 250))
				.outputItems(new ChanceOutputMulti(
						new ChanceOutput(new ItemStack(Blocks.gravel), 50),
						new ChanceOutput(new ItemStack(Blocks.soul_sand), 25),
						new ChanceOutput(new ItemStack(Items.glowstone_dust), 15),
						new ChanceOutput(new ItemStack(ModItems.powder_quartz), 10)
				)).setIconToFirstIngredient().setGroup(groupCrush, INSTANCE));
		
		this.register(new GenericRecipe("rock.soulsand").setup(duraShort, consumption).setNameWrapper("rock.crushing")
				.inputItems(new ComparableStack(Blocks.soul_sand))
				.inputFluids(new FluidStack(Fluids.WATER, 250))
				.outputItems(new ChanceOutputMulti(
						new ChanceOutput(new ItemStack(Blocks.sand), 50),
						new ChanceOutput(new ItemStack(ModItems.powder_fire), 25),
						new ChanceOutput(new ItemStack(ModItems.powder_uranium), 15),
						new ChanceOutput(new ItemStack(Items.blaze_powder), 5),
						new ChanceOutput(new ItemStack(Items.nether_wart), 5)
				)).setIconToFirstIngredient().setGroup(groupCrush, INSTANCE));
		
		this.register(new GenericRecipe("rock.schist").setup(duraLong, consumption).setNameWrapper("rock.crushing")
				.inputItems(new ComparableStack(ModBlocks.stone_gneiss))
				.inputFluids(new FluidStack(Fluids.WATER, 250))
				.outputItems(new ChanceOutputMulti(
						new ChanceOutput(new ItemStack(Blocks.gravel), 50),
						new ChanceOutput(new ItemStack(Blocks.sand), 10),
						new ChanceOutput(new ItemStack(ModItems.powder_lithium), 25),
						new ChanceOutput(new ItemStack(ModItems.powder_niobium), 5),
						new ChanceOutput(new ItemStack(ModItems.powder_uranium), 5),
						new ChanceOutput(new ItemStack(ModItems.powder_gold), 5)
				)).setIconToFirstIngredient().setGroup(groupCrush, INSTANCE));
		
		this.register(new GenericRecipe("rock.hematite").setup(duraLong, consumption).setNameWrapper("rock.crushing")
				.inputItems(new OreDictStack(HEMATITE.ore()))
				.inputFluids(new FluidStack(Fluids.WATER, 250))
				.outputItems(new ChanceOutputMulti(
						new ChanceOutput(new ItemStack(Blocks.gravel), 65),
						new ChanceOutput(new ItemStack(ModItems.powder_iron), 25),
						new ChanceOutput(new ItemStack(ModItems.powder_titanium), 10)
				)).setIconToFirstIngredient().setGroup(groupCrush, INSTANCE));
		
		this.register(new GenericRecipe("rock.bauxite").setup(duraLong, consumption).setNameWrapper("rock.crushing")
				.inputItems(new OreDictStack(BAUXITE.ore()))
				.inputFluids(new FluidStack(Fluids.WATER, 250))
				.outputItems(new ChanceOutputMulti(
						new ChanceOutput(new ItemStack(Blocks.gravel), 25),
						new ChanceOutput(new ItemStack(Items.clay_ball), 25),
						new ChanceOutput(new ItemStack(ModBlocks.stone_resource, 1, 2), 25),
						new ChanceOutput(new ItemStack(ModBlocks.ore_titanium), 25)
				)).setIconToFirstIngredient().setGroup(groupCrush, INSTANCE));
		
		this.register(new GenericRecipe("rock.clay").setup(duraLong, consumption)
				.inputItems(new OreDictStack(KEY_SAND),
						new ComparableStack(ModItems.dust))
				.inputFluids(new FluidStack(Fluids.WATER, 1_000))
				.outputItems(new ItemStack(Items.clay_ball, 4)));
	}
}
