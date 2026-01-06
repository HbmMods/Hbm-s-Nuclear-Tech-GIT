package com.hbm.inventory.recipes;

import static com.hbm.inventory.OreDictManager.*;

import com.hbm.config.GeneralConfig;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.NBTStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.inventory.recipes.loader.GenericRecipes;
import com.hbm.items.BrokenItem;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemCircuit.EnumCircuitType;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFood.FishType;
import net.minecraft.item.ItemStack;

public class PrecAssRecipes extends GenericRecipes<GenericRecipe> {

	public static final PrecAssRecipes INSTANCE = new PrecAssRecipes();

	@Override public int inputItemLimit() { return 9; }
	@Override public int inputFluidLimit() { return 1; }
	@Override public int outputItemLimit() { return 9; }
	@Override public int outputFluidLimit() { return 1; }

	@Override public String getFileName() { return "hbmPrecisionAssembly.json"; }
	@Override public GenericRecipe instantiateRecipe(String name) { return new GenericRecipe(name); }

	@Override
	public void registerDefaults() {

		// i cast: bleeding anus
		// i cast: XL horse dildo (unlubed)
		// i cast: salted lemon juice in stab wound
		// i cast: ovorial torsion
		// i cast: perpetually breaking and healing kneecaps
		// i cast: used needle sandwich
		if(GeneralConfig.enable528) {
			registerPair(new GenericRecipe("precass.chip").setup(100, 200L)
					.inputItems(new ComparableStack(ModItems.circuit, 1, EnumCircuitType.SILICON),
							new ComparableStack(ModItems.plate_polymer, 3),
							new OreDictStack(GOLD.wireFine(), 4)).setPools(POOL_PREFIX_528 + "chip"),
					DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CHIP), 50, GeneralConfig.enableExpensiveMode ? 10 : 90);
			
			registerPair(new GenericRecipe("precass.chip_bismoid").setup(200, 1_000L)
					.inputItems(new ComparableStack(ModItems.circuit, 4, EnumCircuitType.SILICON),
							new ComparableStack(ModItems.plate_polymer, 8),
							new OreDictStack(ANY_BISMOID.nugget(), 2),
							new OreDictStack(GOLD.wireFine(), 4))
					.inputFluids(new FluidStack(Fluids.PERFLUOROMETHYL, 1_000)).setPools(POOL_PREFIX_528 + "chip_bismoid"),
					DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CHIP_BISMOID), 50, GeneralConfig.enableExpensiveMode ? 10 : 75);
			
			registerPair(new GenericRecipe("precass.chip_quantum").setup(300, 20_000L)
					.inputItems(new ComparableStack(ModItems.circuit, 8, EnumCircuitType.SILICON),
							new OreDictStack(BSCCO.wireDense(), 2),
							new OreDictStack(ANY_HARDPLASTIC.ingot(), 8),
							new ComparableStack(ModItems.pellet_charged, 4),
							new OreDictStack(GOLD.wireFine(), 8))
					.inputFluids(new FluidStack(Fluids.HELIUM4, 4_000)).setPools(POOL_PREFIX_528 + "chip_quantum"),
					DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CHIP_QUANTUM), 50, GeneralConfig.enableExpensiveMode ? 10 : 50);
			
			registerPair(new GenericRecipe("precass.atomic_clock").setup(200, 2_000L)
					.inputItems(new ComparableStack(ModItems.circuit, 8, EnumCircuitType.CHIP),
							new OreDictStack(ANY_PLASTIC.ingot(), 4),
							new OreDictStack(ZR.wireFine(), 8),
							new OreDictStack(SR.dust(), 1)).setPools(POOL_PREFIX_528 + "strontium"),
					DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ATOMIC_CLOCK), 50, GeneralConfig.enableExpensiveMode ? 10 : 50);
			
			registerPair(new GenericRecipe("precass.controller").setup(400, 15_000L)
					.inputItems(new ComparableStack(ModItems.circuit, 32, EnumCircuitType.CHIP),
							new ComparableStack(ModItems.circuit, 32, EnumCircuitType.CAPACITOR),
							new ComparableStack(ModItems.circuit, 16, EnumCircuitType.CAPACITOR_TANTALIUM),
							new ComparableStack(ModItems.circuit, 1, EnumCircuitType.CONTROLLER_CHASSIS),
							new ComparableStack(ModItems.upgrade_speed_1),
							new OreDictStack(PB.wireFine(), 16))
					.inputFluids(new FluidStack(Fluids.PERFLUOROMETHYL, 1_000)),
					DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CONTROLLER), 10, GeneralConfig.enableExpensiveMode ? 50 : 90);

			registerPair(new GenericRecipe("precass.controller_advanced").setup(600, 25_000)
					.inputItems(new ComparableStack(ModItems.circuit, 16, EnumCircuitType.CHIP_BISMOID),
							new ComparableStack(ModItems.circuit, 48, EnumCircuitType.CAPACITOR_TANTALIUM),
							new ComparableStack(ModItems.circuit, 1, EnumCircuitType.ATOMIC_CLOCK),
							new ComparableStack(ModItems.circuit, 1, EnumCircuitType.CONTROLLER_CHASSIS),
							new ComparableStack(ModItems.upgrade_speed_3),
							new OreDictStack(PB.wireFine(), 24))
					.inputFluids(new FluidStack(Fluids.PERFLUOROMETHYL, 4_000)),
					DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CONTROLLER_ADVANCED), 10, GeneralConfig.enableExpensiveMode ? 33 : 75);

			registerPair(new GenericRecipe("precass.controller_quantum").setup(600, 250_000)
					.inputItems(new ComparableStack(ModItems.circuit, 16, EnumCircuitType.CHIP_QUANTUM),
							new ComparableStack(ModItems.circuit, 48, EnumCircuitType.CHIP_BISMOID),
							new ComparableStack(ModItems.circuit, 8, EnumCircuitType.ATOMIC_CLOCK),
							new ComparableStack(ModItems.circuit, 2, EnumCircuitType.CONTROLLER_ADVANCED),
							new ComparableStack(ModItems.upgrade_overdrive_1),
							new OreDictStack(PB.wireFine(), 32))
					.inputFluids(new FluidStack(Fluids.PERFLUOROMETHYL_COLD, 6_000)),
					DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CONTROLLER_QUANTUM), 5, GeneralConfig.enableExpensiveMode ? 10 : 50);
			
			addFirstUpgrade(ModItems.upgrade_speed_1, ModItems.upgrade_speed_2, "precass.upgrade_speed_ii");
			addSecondUpgrade(ModItems.upgrade_speed_2, ModItems.upgrade_speed_3, "precass.upgrade_speed_iii");
			addFirstUpgrade(ModItems.upgrade_effect_1, ModItems.upgrade_effect_2, "precass.upgrade_effect_ii");
			addSecondUpgrade(ModItems.upgrade_effect_2, ModItems.upgrade_effect_3, "precass.upgrade_effect_iii");
			addFirstUpgrade(ModItems.upgrade_power_1, ModItems.upgrade_power_2, "precass.upgrade_power_ii");
			addSecondUpgrade(ModItems.upgrade_power_2, ModItems.upgrade_power_3, "precass.upgrade_power_iii");
			addFirstUpgrade(ModItems.upgrade_fortune_1, ModItems.upgrade_fortune_2, "precass.upgrade_fortune_ii");
			addSecondUpgrade(ModItems.upgrade_fortune_2, ModItems.upgrade_fortune_3, "precass.upgrade_fortune_iii");
			addFirstUpgrade(ModItems.upgrade_afterburn_1, ModItems.upgrade_afterburn_2, "precass.upgrade_ab_ii");
			addSecondUpgrade(ModItems.upgrade_afterburn_2, ModItems.upgrade_afterburn_3, "precass.upgrade_ab_iii");

			registerPair(new GenericRecipe("precass.upgrade_overdive_i").setup(200, 1_000)
					.inputItems(new ComparableStack(ModItems.upgrade_speed_3, 1),
							new ComparableStack(ModItems.upgrade_effect_3, 1),
							new OreDictStack(BIGMT.ingot(), 16),
							new OreDictStack(ANY_HARDPLASTIC.ingot(), 16),
							new ComparableStack(ModItems.circuit, 16, EnumCircuitType.ADVANCED)),
					new ItemStack(ModItems.upgrade_overdrive_1), 10, GeneralConfig.enableExpensiveMode ? 10 : 50);
			registerPair(new GenericRecipe("precass.upgrade_overdive_ii").setup(600, 5_000)
					.inputItems(new ComparableStack(ModItems.upgrade_overdrive_1, 1),
							new ComparableStack(ModItems.upgrade_speed_3, 1),
							new ComparableStack(ModItems.upgrade_effect_3, 1),
							new OreDictStack(BIGMT.ingot(), 16),
							new ComparableStack(ModItems.ingot_cft, 8),
							new ComparableStack(ModItems.circuit, 16, EnumCircuitType.CAPACITOR_BOARD)),
					new ItemStack(ModItems.upgrade_overdrive_2), 10, GeneralConfig.enableExpensiveMode ? 10 : 50);
			registerPair(new GenericRecipe("precass.upgrade_overdive_iii").setup(1_200, 100_000)
					.inputItems(new ComparableStack(ModItems.upgrade_overdrive_2, 1),
							new ComparableStack(ModItems.upgrade_speed_3, 1),
							new ComparableStack(ModItems.upgrade_effect_3, 1),
							new OreDictStack(ANY_BISMOIDBRONZE.ingot(), 16),
							new ComparableStack(ModItems.ingot_cft, 16),
							new ComparableStack(ModItems.circuit, 16, EnumCircuitType.BISMOID)),
					new ItemStack(ModItems.upgrade_overdrive_3), 5, GeneralConfig.enableExpensiveMode ? 10 : 50);
		}
		
		int min = 1_200;
		
		// all hail the pufferfish, driver of all innovation
		this.register(new GenericRecipe("precass.blueprints").setup(5 * min, 20_000L)
				.inputItems(new ComparableStack(Items.paper, 16),
						new OreDictStack(KEY_BLUE, 16),
						new ComparableStack(Items.fish, 4, FishType.PUFFERFISH))
				.outputItems(new ChanceOutputMulti(
					new ChanceOutput(new ItemStack(ModItems.blueprint_folder, 1, 0), 10),
					new ChanceOutput(new ItemStack(Items.paper, 16, 0), 90))
				));
		this.register(new GenericRecipe("precass.beigeprints").setup(5 * min, 50_000L)
				.inputItems(new ComparableStack(Items.paper, 24),
						new OreDictStack(CINNABAR.gem(), 24),
						new ComparableStack(Items.fish, 8, FishType.PUFFERFISH))
				.outputItems(new ChanceOutputMulti(
					new ChanceOutput(new ItemStack(ModItems.blueprint_folder, 1, 1), 5),
					new ChanceOutput(new ItemStack(Items.paper, 24, 0), 95))
				));
	}
	
	public void addFirstUpgrade(Item lower, Item higher, String name) {
		
		registerPair(new GenericRecipe(name).setup(300, 10_000)
				.inputItems(new ComparableStack(ModItems.circuit, 8, EnumCircuitType.CHIP),
						new ComparableStack(ModItems.circuit, 4, EnumCircuitType.CAPACITOR_TANTALIUM),
						new ComparableStack(lower), new OreDictStack(ANY_PLASTIC.ingot(), 4)),
				new ItemStack(higher), 15, 25); // upgrades are now actually valuable
	}
	
	public void addSecondUpgrade(Item lower, Item higher, String name) {
		
		registerPair(new GenericRecipe(name).setup(400, 25_000)
				.inputItems(new ComparableStack(ModItems.circuit, 16, EnumCircuitType.CHIP),
						new ComparableStack(ModItems.circuit, 16, EnumCircuitType.CAPACITOR_TANTALIUM),
						new ComparableStack(lower), new OreDictStack(RUBBER.ingot(), 4))
				.inputFluids(new FluidStack(Fluids.SOLVENT, 500)),
				new ItemStack(higher), 5, 10); // admittedly this one's just me being a dick
	}
	
	/** Registers a generic pair of faulty product and recycling of broken items. */
	public void registerPair(GenericRecipe recipe, ItemStack output, int chance, int reclaim) {
		recipe.outputItems(new ChanceOutputMulti(
				new ChanceOutput(output, chance),
				new ChanceOutput(BrokenItem.make(output), 100 - chance)
				));
		
		this.register(recipe);
		
		float fReclaim = reclaim / 100F;
		
		IOutput[] recycle = new IOutput[recipe.inputItem.length];
		for(int i = 0; i < recycle.length; i++) {
			ItemStack stack = recipe.inputItem[i].extractForNEI().get(0).copy();
			recycle[i] = new ChanceOutput(stack, fReclaim);
		}
		
		FluidStack[] fluid = recipe.inputFluid != null ? new FluidStack[1] : null;
		if(fluid != null) {
			fluid[0] = new FluidStack(recipe.inputFluid[0].type, (int) Math.round(recipe.inputFluid[0].fill * fReclaim));
		}
		
		this.register(new GenericRecipe(recipe.getInternalName() + ".recycle").setup(recipe.duration, recipe.power).setNameWrapper("precass.recycle")
				.setIcon(BrokenItem.make(output))
				.inputItems(new NBTStack(BrokenItem.make(output)))
				.outputItems(recycle)
				.outputFluids(fluid));
	}
}
