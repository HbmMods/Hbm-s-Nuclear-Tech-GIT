package com.hbm.inventory.recipes;

import com.hbm.inventory.FluidStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.loader.GenericRecipes;

public class FusionRecipes extends GenericRecipes<FusionRecipe> {

	public static final FusionRecipes INSTANCE = new FusionRecipes();

	@Override public int inputItemLimit() { return 0; }
	@Override public int inputFluidLimit() { return 3; }
	@Override public int outputItemLimit() { return 1; }
	@Override public int outputFluidLimit() { return 11; }

	@Override public String getFileName() { return "hbmFusion.json"; }
	@Override public FusionRecipe instantiateRecipe(String name) { return new FusionRecipe(name); }

	@Override
	public void registerDefaults() {
		
		long solenoid = 100_000;
		
		this.register((FusionRecipe) new FusionRecipe("fus.d-t").setInputEnergy(1_000_000).setOutputEnergy(20_000_000)
				.setPower(solenoid).setDuration(100)
				.inputFluids(new FluidStack(Fluids.DEUTERIUM, 10), new FluidStack(Fluids.TRITIUM, 10))
				.outputFluids(new FluidStack(Fluids.HELIUM4, 1_000)));
	}
}
