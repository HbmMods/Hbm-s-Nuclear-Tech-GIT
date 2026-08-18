package com.hbm.inventory.recipes;

import static com.hbm.inventory.OreDictManager.CINNABAR;
import static com.hbm.inventory.OreDictManager.KEY_BLUE;

import com.hbm.inventory.FluidStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.inventory.recipes.loader.GenericRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemDrive.EnumDriveType;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class SuperComputerRecipes extends GenericRecipes<GenericRecipe> {
	
	public static final SuperComputerRecipes INSTANCE = new SuperComputerRecipes();

	@Override public int inputItemLimit() { return 3; }
	@Override public int inputFluidLimit() { return 1; }
	@Override public int outputItemLimit() { return 3; }
	@Override public int outputFluidLimit() { return 1; }

	@Override public String getFileName() { return "hbmSuperComputer.json"; }
	@Override public GenericRecipe instantiateRecipe(String name) { return new GenericRecipe(name); }

	@Override
	public void registerDefaults() {
		
		int min = 60 * 20;

		// simulate
		registerSimulation(EnumDriveType.FLASH_FLIGHTSIM, "com.flightcalc");
		registerSimulation(EnumDriveType.FLASH_PARTICLESIM, "com.particlecalc");
		
		// process
		registerTriplet("com.processflight", 30 * min, 15 * min, 5 * min, EnumDriveType.DISK_FLIGHTDATA, EnumDriveType.DISK_FLIGHTDATA_PROCESSED, EnumDriveType.DISK_BROKEN, 99, 95, 90);
		registerTriplet("com.processorbit", 60 * min, 30 * min, 15 * min, EnumDriveType.DISK_ORBITDATA, EnumDriveType.DISK_ORBITDATA_PROCESSED, EnumDriveType.DISK_BROKEN, 90, 80, 65);
		
		// copy
		registerCopy("com.copyflightcalc", 15 * min, EnumDriveType.FLASH_FLIGHTSIM, EnumDriveType.FLASH_EMPTY, EnumDriveType.FLASH_BROKEN, 95);
		registerCopy("com.copyparticlecalc", 15 * min, EnumDriveType.FLASH_PARTICLESIM, EnumDriveType.FLASH_EMPTY, EnumDriveType.FLASH_BROKEN, 95);
		registerCopy("com.copyfligthdata", 15 * min, EnumDriveType.DISK_FLIGHTDATA_PROCESSED, EnumDriveType.DISK_EMPTY, EnumDriveType.DISK_BROKEN, 75);
		registerCopy("com.copyorbitdata", 30 * min, EnumDriveType.DISK_ORBITDATA_PROCESSED, EnumDriveType.DISK_EMPTY, EnumDriveType.DISK_BROKEN, 75);
		
		this.register(new GenericRecipe("com.blueprints").setup(15 * min, 50_000L)
				.inputItems(new ComparableStack(Items.paper, 16),
						new OreDictStack(KEY_BLUE, 16))
				.outputItems(new ChanceOutputMulti(
					new ChanceOutput(new ItemStack(ModItems.blueprint_folder, 1, 0), 20),
					new ChanceOutput(new ItemStack(Items.paper, 16, 0), 80))
				));
		this.register(new GenericRecipe("com.beigeprints").setup(15 * min, 50_000L)
				.inputItems(new ComparableStack(Items.paper, 24),
						new OreDictStack(CINNABAR.gem(), 24))
				.outputItems(new ChanceOutputMulti(
					new ChanceOutput(new ItemStack(ModItems.blueprint_folder, 1, 1), 10),
					new ChanceOutput(new ItemStack(Items.paper, 24, 0), 90))
				));
	}
	
	protected void registerSimulation(EnumDriveType type, String name) {
		
		int min = 60 * 20;
		registerTriplet(name, 15 * min, 5 * min / 2, 1 * min, EnumDriveType.FLASH_EMPTY, type, EnumDriveType.FLASH_BROKEN, 95, 50, 25);
	}
	
	protected void registerTriplet(String name, int time0, int time1, int time2, EnumDriveType input, EnumDriveType output, EnumDriveType broken, int chance0, int chance1, int chance2) {
		
		this.register(new GenericRecipe(name + "_water").setup(time0, 10_000)
				.inputItems(new ComparableStack(ModItems.drive, 1, input))
				.outputItems(new ChanceOutputMulti(
						new ChanceOutput(new ItemStack(ModItems.drive, 1, output.ordinal()), chance0),
						new ChanceOutput(new ItemStack(ModItems.drive, 1, broken.ordinal()), 100 - chance0)))
				.inputFluids(new FluidStack(Fluids.WATER, 16_000)).outputFluids(new FluidStack(Fluids.SPENTSTEAM, 16_000)));
		
		this.register(new GenericRecipe(name + "_pfm").setup(time1, 10_000)
				.inputItems(new ComparableStack(ModItems.drive, 1, input))
				.outputItems(new ChanceOutputMulti(
						new ChanceOutput(new ItemStack(ModItems.drive, 1, output.ordinal()), chance1),
						new ChanceOutput(new ItemStack(ModItems.drive, 1, broken.ordinal()), 100 - chance1)))
				.inputFluids(new FluidStack(Fluids.PERFLUOROMETHYL_COLD, 16_000)).outputFluids(new FluidStack(Fluids.PERFLUOROMETHYL, 16_000)));
		
		this.register(new GenericRecipe(name + "_helium").setup(time2, 10_000)
				.inputItems(new ComparableStack(ModItems.drive, 1, input))
				.outputItems(new ChanceOutputMulti(
						new ChanceOutput(new ItemStack(ModItems.drive, 1, output.ordinal()), chance2),
						new ChanceOutput(new ItemStack(ModItems.drive, 1, broken.ordinal()), 100 - chance2)))
				.inputFluids(new FluidStack(Fluids.HELIUM4, 16_000)));
	}
	
	protected void registerCopy(String name, int time, EnumDriveType full, EnumDriveType empty, EnumDriveType broken, int chance) {
		
		this.register(new GenericRecipe(name).setup(time, 10_000).setNameWrapper("com.copy")
				.inputItems(new ComparableStack(ModItems.drive, 1, full), new ComparableStack(ModItems.drive, 1, empty))
				.outputItems(new ChanceOutputMulti(
						new ChanceOutput(new ItemStack(ModItems.drive, 2, full.ordinal()), chance),
						new ChanceOutput(new ItemStack(ModItems.drive, 2, broken.ordinal()), 100 - chance))));
	}
}
