package com.hbm.inventory.recipes;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.FluidTypeHandler.FluidTypeTheOldOne;
import com.hbm.inventory.FluidStack;
import static com.hbm.inventory.OreDictManager.*;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.items.ModItems;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ChemplantRecipes {

	public static List<ChemRecipe> recipes = new ArrayList();
	
	public static void register() {
		
		registerFuelProcessing();
		registerFuelCracking();
		registerCoalCracking();
		registerSolidFuel();
		registerOtherOil();
		recipes.add(new ChemRecipe("COOLANT", 0));
		recipes.add(new ChemRecipe("CRYOGEL", 0));
		recipes.add(new ChemRecipe("DESH", 0));
		recipes.add(new ChemRecipe("NITAN", 0));
		recipes.add(new ChemRecipe("PEROXIDE", 0));
		recipes.add(new ChemRecipe("CIRCUIT_4", 0));
		recipes.add(new ChemRecipe("CIRCUIT_5", 0));
		recipes.add(new ChemRecipe("POLYMER", 0));
		recipes.add(new ChemRecipe("DEUTERIUM", 0));
		recipes.add(new ChemRecipe("STEAM", 0));
		recipes.add(new ChemRecipe("YELLOWCAKE", 0));
		recipes.add(new ChemRecipe("UF6", 0));
		recipes.add(new ChemRecipe("PUF6", 0));
		recipes.add(new ChemRecipe("SAS3", 0));
		recipes.add(new ChemRecipe("DYN_SCHRAB", 0));
		recipes.add(new ChemRecipe("DYN_EUPH", 0));
		recipes.add(new ChemRecipe("DYN_DNT", 0));
		recipes.add(new ChemRecipe("CORDITE", 0));
		recipes.add(new ChemRecipe("KEVLAR", 0));
		recipes.add(new ChemRecipe("CONCRETE", 0));
		recipes.add(new ChemRecipe("CONCRETE_ASBESTOS", 0));
		recipes.add(new ChemRecipe("SOLID_FUEL", 0));
		recipes.add(new ChemRecipe("ELECTROLYSIS", 0));
		recipes.add(new ChemRecipe("XENON", 0));
		recipes.add(new ChemRecipe("XENON_OXY", 0));
		recipes.add(new ChemRecipe("SATURN", 0));
		recipes.add(new ChemRecipe("BALEFIRE", 0));
		recipes.add(new ChemRecipe("SCHRABIDIC", 0));
		recipes.add(new ChemRecipe("SCHRABIDATE", 0));
		recipes.add(new ChemRecipe("COLTAN_CLEANING", 0));
		recipes.add(new ChemRecipe("COLTAN_PAIN", 0));
		recipes.add(new ChemRecipe("COLTAN_CRYSTAL", 0));
		recipes.add(new ChemRecipe("VIT_LIQUID", 0));
		recipes.add(new ChemRecipe("VIT_GAS", 0));
		recipes.add(new ChemRecipe("TEL", 0));
		recipes.add(new ChemRecipe("GASOLINE", 0));
		recipes.add(new ChemRecipe("FRACKSOL", 0));
		recipes.add(new ChemRecipe("DUCRETE", 0));
		
	}
	
	public static void registerFuelProcessing() {
		recipes.add(new ChemRecipe("FP_HEAVYOIL", 50)
				.inputFluids(new FluidStack(1000, FluidTypeTheOldOne.HEAVYOIL))
				.outputFluids(
						new FluidStack(RefineryRecipes.heavy_frac_bitu * 10, FluidTypeTheOldOne.BITUMEN),
						new FluidStack(RefineryRecipes.heavy_frac_smear * 10, FluidTypeTheOldOne.SMEAR)
						));
		recipes.add(new ChemRecipe("FP_SMEAR", 50)
				.inputFluids(new FluidStack(1000, FluidTypeTheOldOne.SMEAR))
				.outputFluids(
						new FluidStack(RefineryRecipes.smear_frac_heat * 10, FluidTypeTheOldOne.HEATINGOIL),
						new FluidStack(RefineryRecipes.smear_frac_lube * 10, FluidTypeTheOldOne.LUBRICANT)
						));
		recipes.add(new ChemRecipe("FP_NAPHTHA", 50)
				.inputFluids(new FluidStack(1000, FluidTypeTheOldOne.NAPHTHA))
				.outputFluids(
						new FluidStack(RefineryRecipes.napht_frac_heat * 10, FluidTypeTheOldOne.HEATINGOIL),
						new FluidStack(RefineryRecipes.napht_frac_diesel * 10, FluidTypeTheOldOne.DIESEL)
						));
		recipes.add(new ChemRecipe("FP_LIGHTOIL", 50)
				.inputFluids(new FluidStack(1000, FluidTypeTheOldOne.LIGHTOIL))
				.outputFluids(
						new FluidStack(RefineryRecipes.light_frac_diesel * 10, FluidTypeTheOldOne.DIESEL),
						new FluidStack(RefineryRecipes.light_frac_kero * 10, FluidTypeTheOldOne.KEROSENE)
						));
		recipes.add(new ChemRecipe("FR_REOIL", 30)
				.inputFluids(new FluidStack(1000, FluidTypeTheOldOne.SMEAR))
				.outputFluids(new FluidStack(800, FluidTypeTheOldOne.RECLAIMED)));
		recipes.add(new ChemRecipe("FR_PETROIL", 30)
				.inputFluids(
						new FluidStack(800, FluidTypeTheOldOne.RECLAIMED),
						new FluidStack(200, FluidTypeTheOldOne.LUBRICANT))
				.outputFluids(new FluidStack(1000, FluidTypeTheOldOne.PETROIL)));
	}
	
	public static void registerFuelCracking() {
		recipes.add(new ChemRecipe("FC_BITUMEN", 100)
				.inputFluids(
						new FluidStack(1200, FluidTypeTheOldOne.BITUMEN),
						new FluidStack(2400, FluidTypeTheOldOne.STEAM))
				.outputFluids(
						new FluidStack(1000, FluidTypeTheOldOne.OIL),
						new FluidStack(200, FluidTypeTheOldOne.PETROLEUM)));
		recipes.add(new ChemRecipe("FC_I_NAPHTHA", 150)
				.inputFluids(
						new FluidStack(1400, FluidTypeTheOldOne.SMEAR),
						new FluidStack(800, FluidTypeTheOldOne.WATER))
				.outputFluids(new FluidStack(800, FluidTypeTheOldOne.NAPHTHA)));
		recipes.add(new ChemRecipe("FC_GAS_PETROLEUM", 100)
				.inputFluids(
						new FluidStack(1800, FluidTypeTheOldOne.GAS),
						new FluidStack(1200, FluidTypeTheOldOne.WATER))
				.outputFluids(new FluidStack(800, FluidTypeTheOldOne.PETROLEUM)));
		recipes.add(new ChemRecipe("FC_DIESEL_KEROSENE", 150)
				.inputFluids(
						new FluidStack(1200, FluidTypeTheOldOne.DIESEL),
						new FluidStack(2000, FluidTypeTheOldOne.STEAM))
				.outputFluids(new FluidStack(400, FluidTypeTheOldOne.KEROSENE)));
		recipes.add(new ChemRecipe("FC_KEROSENE_PETROLEUM", 150)
				.inputFluids(
						new FluidStack(1400, FluidTypeTheOldOne.KEROSENE),
						new FluidStack(2000, FluidTypeTheOldOne.STEAM))
				.outputFluids(new FluidStack(800, FluidTypeTheOldOne.PETROLEUM)));
	}
	
	public static void registerCoalCracking() {
		recipes.add(new ChemRecipe("CC_OIL", 150)
				.inputItems(new OreDictStack(COAL.dust(), 8), new ComparableStack(ModItems.oil_tar, 4))
				.inputFluids(new FluidStack(1400, FluidTypeTheOldOne.STEAM))
				.outputFluids(new FluidStack(2000, FluidTypeTheOldOne.OIL)));
		recipes.add(new ChemRecipe("CC_I", 200)
				.inputItems(new OreDictStack(COAL.dust(), 6), new ComparableStack(ModItems.oil_tar, 4))
				.inputFluids(new FluidStack(1800, FluidTypeTheOldOne.WATER))
				.outputFluids(new FluidStack(1600, FluidTypeTheOldOne.SMEAR)));
		recipes.add(new ChemRecipe("CC_HEATING", 250)
				.inputItems(new OreDictStack(COAL.dust(), 6), new ComparableStack(ModItems.oil_tar, 4))
				.inputFluids(new FluidStack(2000, FluidTypeTheOldOne.STEAM))
				.outputFluids(new FluidStack(1800, FluidTypeTheOldOne.HEATINGOIL)));
		recipes.add(new ChemRecipe("CC_HEAVY", 200)
				.inputItems(new OreDictStack(COAL.dust(), 8), new ComparableStack(ModItems.oil_tar, 4))
				.inputFluids(new FluidStack(1400, FluidTypeTheOldOne.WATER))
				.outputFluids(new FluidStack(1800, FluidTypeTheOldOne.HEAVYOIL)));
		recipes.add(new ChemRecipe("CC_NAPHTHA", 300)
				.inputItems(new OreDictStack(COAL.dust(), 8), new ComparableStack(ModItems.oil_tar, 4))
				.inputFluids(new FluidStack(2400, FluidTypeTheOldOne.STEAM))
				.outputFluids(new FluidStack(2000, FluidTypeTheOldOne.NAPHTHA)));
	}
	
	public static void registerSolidFuel() {
		recipes.add(new ChemRecipe("SF_OIL", 20)
				.inputFluids(new FluidStack(350, FluidTypeTheOldOne.OIL))
				.outputItems(new ItemStack(ModItems.oil_tar, 1), new ItemStack(ModItems.oil_tar, 1)));
		recipes.add(new ChemRecipe("SF_HEAVYOIL", 20)
				.inputFluids(new FluidStack(250, FluidTypeTheOldOne.HEAVYOIL))
				.outputItems(new ItemStack(ModItems.oil_tar, 1), new ItemStack(ModItems.oil_tar, 1)));
		recipes.add(new ChemRecipe("SF_SMEAR", 20)
				.inputFluids(new FluidStack(200, FluidTypeTheOldOne.SMEAR))
				.outputItems(new ItemStack(ModItems.solid_fuel, 1), new ItemStack(ModItems.solid_fuel, 1)));
		recipes.add(new ChemRecipe("SF_HEATINGOIL", 20)
				.inputFluids(new FluidStack(100, FluidTypeTheOldOne.HEATINGOIL))
				.outputItems(new ItemStack(ModItems.solid_fuel, 1), new ItemStack(ModItems.solid_fuel, 1)));
		recipes.add(new ChemRecipe("SF_RECLAIMED", 20)
				.inputFluids(new FluidStack(200, FluidTypeTheOldOne.RECLAIMED))
				.outputItems(new ItemStack(ModItems.solid_fuel, 1), new ItemStack(ModItems.solid_fuel, 1)));
		recipes.add(new ChemRecipe("SF_PETROIL", 20)
				.inputFluids(new FluidStack(250, FluidTypeTheOldOne.PETROIL))
				.outputItems(new ItemStack(ModItems.solid_fuel, 1), new ItemStack(ModItems.solid_fuel, 1)));
		recipes.add(new ChemRecipe("SF_LUBRICANT", 20)
				.inputFluids(new FluidStack(250, FluidTypeTheOldOne.LUBRICANT))
				.outputItems(new ItemStack(ModItems.solid_fuel, 1), new ItemStack(ModItems.solid_fuel, 1)));
		recipes.add(new ChemRecipe("SF_NAPHTHA", 20)
				.inputFluids(new FluidStack(300, FluidTypeTheOldOne.NAPHTHA))
				.outputItems(new ItemStack(ModItems.solid_fuel, 1), new ItemStack(ModItems.solid_fuel, 1)));
		recipes.add(new ChemRecipe("SF_DIESEL", 20)
				.inputFluids(new FluidStack(400, FluidTypeTheOldOne.DIESEL))
				.outputItems(new ItemStack(ModItems.solid_fuel, 1), new ItemStack(ModItems.solid_fuel, 1)));
		recipes.add(new ChemRecipe("SF_LIGHTOIL", 20)
				.inputFluids(new FluidStack(450, FluidTypeTheOldOne.LIGHTOIL))
				.outputItems(new ItemStack(ModItems.solid_fuel, 1), new ItemStack(ModItems.solid_fuel, 1)));
		recipes.add(new ChemRecipe("SF_KEROSENE", 20)
				.inputFluids(new FluidStack(550, FluidTypeTheOldOne.KEROSENE))
				.outputItems(new ItemStack(ModItems.solid_fuel, 1), new ItemStack(ModItems.solid_fuel, 1)));
		recipes.add(new ChemRecipe("SF_GAS", 20)
				.inputFluids(new FluidStack(750, FluidTypeTheOldOne.GAS))
				.outputItems(new ItemStack(ModItems.solid_fuel, 1), new ItemStack(ModItems.solid_fuel, 1)));
		recipes.add(new ChemRecipe("SF_PETROLEUM", 20)
				.inputFluids(new FluidStack(600, FluidTypeTheOldOne.PETROLEUM))
				.outputItems(new ItemStack(ModItems.solid_fuel, 1), new ItemStack(ModItems.solid_fuel, 1)));
		recipes.add(new ChemRecipe("SF_BIOGAS", 20)
				.inputFluids(new FluidStack(3500, FluidTypeTheOldOne.BIOGAS))
				.outputItems(new ItemStack(ModItems.solid_fuel, 1), new ItemStack(ModItems.solid_fuel, 1)));
		recipes.add(new ChemRecipe("SF_BIOFUEL", 20)
				.inputFluids(new FluidStack(1500, FluidTypeTheOldOne.BIOFUEL))
				.outputItems(new ItemStack(ModItems.solid_fuel, 1), new ItemStack(ModItems.solid_fuel, 1)));;
	}

	public static void registerOtherOil() {
		recipes.add(new ChemRecipe("BP_BIOGAS", 200)
				.inputItems(new ComparableStack(ModItems.biomass, 16))
				.outputFluids(new FluidStack(4000, FluidTypeTheOldOne.BIOGAS)));
		recipes.add(new ChemRecipe("BP_BIOFUEL", 100)
				.inputFluids(new FluidStack(2000, FluidTypeTheOldOne.BIOGAS))
				.outputFluids(new FluidStack(1000, FluidTypeTheOldOne.BIOFUEL)));
		recipes.add(new ChemRecipe("LPG", 100)
				.inputFluids(new FluidStack(2000, FluidTypeTheOldOne.PETROLEUM))
				.outputFluids(new FluidStack(1000, FluidTypeTheOldOne.LPG)));
		recipes.add(new ChemRecipe("OIL_SAND", 200)
				.inputItems(new ComparableStack(ModBlocks.ore_oil_sand, 16), new ComparableStack(ModItems.oil_tar, 1))
				.outputItems(new ItemStack(Blocks.sand, 4), new ItemStack(Blocks.sand, 4), new ItemStack(Blocks.sand, 4), new ItemStack(Blocks.sand, 4))
				.outputFluids(new FluidStack(1000, FluidTypeTheOldOne.BITUMEN)));
		recipes.add(new ChemRecipe("ASPHALT", 100)
				.inputItems(new ComparableStack(Blocks.gravel, 2), new ComparableStack(Blocks.sand, 6))
				.inputFluids(new FluidStack(1000, FluidTypeTheOldOne.BITUMEN))
				.outputItems(new ItemStack(ModBlocks.asphalt, 4), new ItemStack(ModBlocks.asphalt, 4), new ItemStack(ModBlocks.asphalt, 4), new ItemStack(ModBlocks.asphalt, 4)));
	}
	
	public static class ChemRecipe {

		public String name;
		private AStack[] inputs;
		private FluidStack[] inputFluids;
		private ItemStack[] outputs;
		private FluidStack[] outputFluids;
		private int duration;
		
		public ChemRecipe(String name, int duration) {
			this.name = name;
			this.duration = duration;
			
			this.inputs = new AStack[4];
			this.outputs = new ItemStack[4];
			this.inputFluids = new FluidStack[2];
			this.outputFluids = new FluidStack[2];
		}
		
		public ChemRecipe inputItems(AStack... in) {
			for(int i = 0; i < in.length; i++) this.inputs[i] = in[i];
			return this;
		}
		
		public ChemRecipe inputFluids(FluidStack... in) {
			for(int i = 0; i < in.length; i++) this.inputFluids[i] = in[i];
			return this;
		}
		
		public ChemRecipe outputItems(ItemStack... out) {
			for(int i = 0; i < out.length; i++) this.outputs[i] = out[i];
			return this;
		}
		
		public ChemRecipe outputFluids(FluidStack... out) {
			for(int i = 0; i < out.length; i++) this.outputFluids[i] = out[i];
			return this;
		}
	}
}
