package com.hbm.inventory.recipes;

import java.io.IOException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.loader.GenericRecipes;
import com.hbm.items.ModItems;
import com.hbm.tileentity.machine.fusion.TileEntityFusionBreeder;

import net.minecraft.item.ItemStack;

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
		
		long solenoid = 25_000;
		double breederCapacity = TileEntityFusionBreeder.capacity;
		
		/// DEMO ///

		// mostly for breeding helium and tritium, energy gains are enough to ignite TH4
		// 15MHE/s to 20MHE/s
		this.register((FusionRecipe) new FusionRecipe("fus.dd").setInputEnergy(750_000).setOutputEnergy(1_000_000).setOutputFlux(breederCapacity / 200)
				.setPower(solenoid).setDuration(100)
				.inputFluids(new FluidStack(Fluids.DEUTERIUM, 20))
				.outputFluids(new FluidStack(Fluids.HELIUM4, 1_000))); // akshuyally it should be helium-3 muh realisme
		
		// early fuel
		// 5MHE/s to 20MHE/s
		this.register((FusionRecipe) new FusionRecipe("fus.do").setInputEnergy(250_000).setOutputEnergy(1_250_000).setOutputFlux(breederCapacity / 200)
				.setPower(solenoid).setDuration(100)
				.inputFluids(new FluidStack(Fluids.DEUTERIUM, 10), new FluidStack(Fluids.OXYGEN, 10))
				.outputItems(new ItemStack(ModItems.pellet_charged)));
		
		// medium fuel
		// 15MHE/s to 75MHE/s
		this.register((FusionRecipe) new FusionRecipe("fus.dt").setInputEnergy(750_000).setOutputEnergy(3_750_000).setOutputFlux(breederCapacity / 100)
				.setPower(solenoid).setDuration(100)
				.inputFluids(new FluidStack(Fluids.DEUTERIUM, 10), new FluidStack(Fluids.TRITIUM, 10))
				.outputFluids(new FluidStack(Fluids.HELIUM4, 1_000)));
		
		// medium fuel, three klystrons or in tandem
		// 50MHE/s to 125MHE/s
		this.register((FusionRecipe) new FusionRecipe("fus.tcl").setInputEnergy(2_500_000).setOutputEnergy(6_250_000).setOutputFlux(breederCapacity / 20)
				.setPower(solenoid).setDuration(100)
				.inputFluids(new FluidStack(Fluids.TRITIUM, 10), new FluidStack(Fluids.CHLORINE, 10))
				.outputItems(new ItemStack(ModItems.pellet_charged)));
		
		// medium fuel, aneutronic
		// 10MHE/s to 75MHE/s
		this.register((FusionRecipe) new FusionRecipe("fus.h3").setInputEnergy(500_000).setOutputEnergy(3_750_000).setOutputFlux(0)
				.setPower(solenoid).setDuration(100)
				.inputFluids(new FluidStack(Fluids.HELIUM3, 20))
				.outputFluids(new FluidStack(Fluids.HELIUM4, 1_000)));
		
		// medium fuel, in tandem with DD
		// 17.5MHE/s to 80MHE/s
		this.register((FusionRecipe) new FusionRecipe("fus.th4").setInputEnergy(875_000).setOutputEnergy(4_000_000).setOutputFlux(breederCapacity / 20)
				.setPower(solenoid).setDuration(100)
				.inputFluids(new FluidStack(Fluids.TRITIUM, 10), new FluidStack(Fluids.HELIUM4, 10))
				.outputItems(new ItemStack(ModItems.pellet_charged)));

		// high fuel, ignition exceeds klystron power, requires TH4 or H3
		// 75MHE/s to 200MHE/s
		this.register((FusionRecipe) new FusionRecipe("fus.cl").setInputEnergy(3_750_000).setOutputEnergy(10_000_000).setOutputFlux(breederCapacity / 10)
				.setPower(solenoid).setDuration(100)
				.inputFluids(new FluidStack(Fluids.CHLORINE, 20))
				.outputItems(new ItemStack(ModItems.pellet_charged)));
		
		/// DEMO ///
		
		
		/*
		 * TODO:
		 * chlorophyte and more liquid byproduct types
		 * stellar flux plasma (post ICF, erisite?)
		 * balefire plasma (raw balefire instead of rocket fuel?)
		 * scrap ionized particle liquefaction recipe
		 * deuterated carbon (deut + refgas + syngas in a chemplant)
		 */
	}

	// foresight! yeah!
	@Override
	public void readExtraData(JsonElement element, FusionRecipe recipe) {
		JsonObject obj = (JsonObject) element;

		recipe.ignitionTemp = obj.get("ignitionTemp").getAsLong();
		recipe.outputTemp = obj.get("outputTemp").getAsLong();
		recipe.neutronFlux = obj.get("outputFlux").getAsDouble();
	}

	@Override
	public void writeExtraData(FusionRecipe recipe, JsonWriter writer) throws IOException {
		writer.name("ignitionTemp").value(recipe.ignitionTemp);
		writer.name("outputTemp").value(recipe.outputTemp);
		writer.name("outputFlux").value(recipe.neutronFlux);
	}
}
