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
		
		// mostly for breeding helium and tritium, energy gains are enough to ignite TH4
		// 15MHE/s to 20MHE/s
		this.register((FusionRecipe) new FusionRecipe("fus.dd").setInputEnergy(750_000).setOutputEnergy(1_000_000).setOutputFlux(breederCapacity / 200)
				.setRGB(1F, 0.2F, 0.2F) // extra red
				.setNamed().setIcon(new ItemStack(ModItems.gas_full, 1, Fluids.DEUTERIUM.getID()))
				.setPower(solenoid).setDuration(100)
				.inputFluids(new FluidStack(Fluids.DEUTERIUM, 20))
				.outputFluids(new FluidStack(Fluids.HELIUM4, 1_000))); // akshuyally it should be helium-3 muh realisme
		
		// early fuel
		// 5MHE/s to 20MHE/s
		this.register((FusionRecipe) new FusionRecipe("fus.do").setInputEnergy(250_000).setOutputEnergy(1_250_000).setOutputFlux(breederCapacity / 200)
				.setNamed().setIcon(new ItemStack(ModItems.gas_full, 1, Fluids.OXYGEN.getID()))
				.setPower(solenoid).setDuration(100)
				.inputFluids(new FluidStack(Fluids.DEUTERIUM, 10), new FluidStack(Fluids.OXYGEN, 10))
				.outputItems(new ItemStack(ModItems.pellet_charged)));
		
		// medium fuel
		// 15MHE/s to 75MHE/s
		this.register((FusionRecipe) new FusionRecipe("fus.dt").setInputEnergy(750_000).setOutputEnergy(3_750_000).setOutputFlux(breederCapacity / 100)
				.setNamed().setIcon(new ItemStack(ModItems.gas_full, 1, Fluids.HELIUM4.getID()))
				.setPower(solenoid).setDuration(100)
				.inputFluids(new FluidStack(Fluids.DEUTERIUM, 10), new FluidStack(Fluids.TRITIUM, 10))
				.outputFluids(new FluidStack(Fluids.HELIUM4, 1_000)));
		
		// medium fuel, three klystrons or in tandem
		// 50MHE/s to 125MHE/s
		this.register((FusionRecipe) new FusionRecipe("fus.tcl").setInputEnergy(2_500_000).setOutputEnergy(6_250_000).setOutputFlux(breederCapacity / 20)
				.setRGB(0.8F, 0.6F, 0.4F) // everything chlorine is piss yellow
				.setNamed().setIcon(new ItemStack(ModItems.powder_chlorophyte))
				.setPower(solenoid).setDuration(100)
				.inputFluids(new FluidStack(Fluids.TRITIUM, 10), new FluidStack(Fluids.CHLORINE, 10))
				.outputItems(new ItemStack(ModItems.powder_chlorophyte)));
		
		// medium fuel, aneutronic
		// 10MHE/s to 75MHE/s
		this.register((FusionRecipe) new FusionRecipe("fus.h3").setInputEnergy(500_000).setOutputEnergy(3_750_000).setOutputFlux(0)
				.setRGB(0.2F, 0.2F, 1F) // helium blue
				.setNamed().setIcon(new ItemStack(ModItems.gas_full, 1, Fluids.HELIUM3.getID()))
				.setPower(solenoid).setDuration(100)
				.inputFluids(new FluidStack(Fluids.HELIUM3, 20))
				.outputFluids(new FluidStack(Fluids.HELIUM4, 1_000)));
		
		// medium fuel, in tandem with DD
		// 17.5MHE/s to 80MHE/s
		this.register((FusionRecipe) new FusionRecipe("fus.th4").setInputEnergy(875_000).setOutputEnergy(4_000_000).setOutputFlux(breederCapacity / 20)
				.setRGB(0.2F, 0.2F, 1F)
				.setNamed().setIcon(new ItemStack(ModItems.gas_full, 1, Fluids.TRITIUM.getID()))
				.setPower(solenoid).setDuration(100)
				.inputFluids(new FluidStack(Fluids.TRITIUM, 10), new FluidStack(Fluids.HELIUM4, 10))
				.outputItems(new ItemStack(ModItems.pellet_charged)));

		// high fuel, ignition exceeds klystron power, requires TH4 or H3
		// 75MHE/s to 200MHE/s
		this.register((FusionRecipe) new FusionRecipe("fus.cl").setInputEnergy(3_750_000).setOutputEnergy(10_000_000).setOutputFlux(breederCapacity / 10)
				.setRGB(1F, 0.6F, 0.2F) // even more yellow
				.setNamed().setIcon(new ItemStack(ModItems.powder_chlorophyte))
				.setPower(solenoid).setDuration(100)
				.inputFluids(new FluidStack(Fluids.CHLORINE, 20))
				.outputItems(new ItemStack(ModItems.powder_chlorophyte)));

		// high fuel, requires chlorine phase to ignite
		// 200MHE/s to 500MHE/s
		this.register((FusionRecipe) new FusionRecipe("fus.dhc").setInputEnergy(10_000_000).setOutputEnergy(25_000_000).setOutputFlux(breederCapacity / 5)
				.setRGB(0.2F, 0.8F, 0.8F) // cyan
				.setNamed().setIcon(new ItemStack(ModItems.fluid_icon, 1, Fluids.DHC.getID()))
				.setPower(solenoid).setDuration(100)
				.inputFluids(new FluidStack(Fluids.DHC, 20))
				.outputItems(new ItemStack(ModItems.powder_chlorophyte)));

		// high fuel, low ignition point
		// 20MHE/s to 250MHE/s
		this.register((FusionRecipe) new FusionRecipe("fus.bf").setInputEnergy(1_000_000).setOutputEnergy(12_500_000).setOutputFlux(breederCapacity / 5)
				.setRGB(0.2F, 1F, 0.2F) // what do you think?
				.setNamed().setIcon(new ItemStack(ModItems.fluid_icon, 1, Fluids.BALEFIRE.getID()))
				.setPower(solenoid).setDuration(100)
				.inputFluids(new FluidStack(Fluids.BALEFIRE, 15), new FluidStack(Fluids.AMAT, 5)) // do we kick the antimatter requirement or maybe change it?
				.outputItems(new ItemStack(ModItems.powder_balefire)));

		// high fuel, high ignition point
		// 200MHE/s/s to 1GHE/s
		this.register((FusionRecipe) new FusionRecipe("fus.stellar").setInputEnergy(10_000_000).setOutputEnergy(50_000_000).setOutputFlux(breederCapacity / 1)
				.setRGB(1F, 0.4F, 0.1F) // brilliant orange, like looking into a furnace
				.setNamed().setIcon(new ItemStack(ModItems.fluid_icon, 1, Fluids.STELLAR_FLUX.getID()))
				.setPower(solenoid).setDuration(100)
				.inputFluids(new FluidStack(Fluids.STELLAR_FLUX, 10))
				.outputItems(new ItemStack(ModItems.powder_gold))); // eough
	}

	// foresight! yeah!
	@Override
	public void readExtraData(JsonElement element, FusionRecipe recipe) {
		JsonObject obj = (JsonObject) element;

		recipe.ignitionTemp = obj.get("ignitionTemp").getAsLong();
		recipe.outputTemp = obj.get("outputTemp").getAsLong();
		recipe.neutronFlux = obj.get("outputFlux").getAsDouble();
		recipe.r = obj.get("r").getAsFloat();
		recipe.g = obj.get("g").getAsFloat();
		recipe.b = obj.get("b").getAsFloat();
	}

	@Override
	public void writeExtraData(FusionRecipe recipe, JsonWriter writer) throws IOException {
		writer.name("ignitionTemp").value(recipe.ignitionTemp);
		writer.name("outputTemp").value(recipe.outputTemp);
		writer.name("outputFlux").value(recipe.neutronFlux);
		writer.name("r").value(recipe.r);
		writer.name("g").value(recipe.g);
		writer.name("b").value(recipe.b);
	}
}
