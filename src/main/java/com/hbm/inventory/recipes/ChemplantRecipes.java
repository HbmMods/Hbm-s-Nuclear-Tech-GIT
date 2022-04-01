package com.hbm.inventory.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.inventory.FluidStack;
import static com.hbm.inventory.OreDictManager.*;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ChemplantRecipes {
	
	/**
	 * Nice order: The order in which the ChemRecipe are added to the recipes list
	 * Meta order: Fixed using the id param, saved in indexMapping
	 */

	public static HashMap<Integer, ChemRecipe> indexMapping = new HashMap();
	public static List<ChemRecipe> recipes = new ArrayList();
	
	public static void register() {
		
		registerFuelProcessing();
		//6-30, formerly oil cracking, coal liquefaction and solidifciation
		registerOtherOil();
		
		recipes.add(new ChemRecipe(36, "COOLANT", 50)
				.inputItems(new OreDictStack(KNO.dust()))
				.inputFluids(new FluidStack(Fluids.WATER, 1800))
				.outputFluids(new FluidStack(Fluids.COOLANT, 2000)));
		recipes.add(new ChemRecipe(37, "CRYOGEL", 50)
				.inputItems(new ComparableStack(ModItems.powder_ice))
				.inputFluids(new FluidStack(Fluids.COOLANT, 1800))
				.outputFluids(new FluidStack(Fluids.CRYOGEL, 2000)));
		recipes.add(new ChemRecipe(38, "DESH", 300)
				.inputItems(new ComparableStack(ModItems.powder_desh_mix))
				.inputFluids(
						GeneralConfig.enableBabyMode ?
								new FluidStack[] {new FluidStack(Fluids.LIGHTOIL, 200)} :
								new FluidStack[] {new FluidStack(Fluids.MERCURY, 200), new FluidStack(Fluids.LIGHTOIL, 200)})
				.outputItems(new ItemStack(ModItems.ingot_desh)));
		recipes.add(new ChemRecipe(39, "NITAN", 50)
				.inputItems(new ComparableStack(ModItems.powder_nitan_mix))
				.inputFluids(
						new FluidStack(Fluids.KEROSENE, 600),
						new FluidStack(Fluids.MERCURY, 200))
				.outputFluids(new FluidStack(Fluids.NITAN, 1000)));
		recipes.add(new ChemRecipe(40, "PEROXIDE", 50)
				.inputFluids(new FluidStack(Fluids.WATER, 1000))
				.outputFluids(new FluidStack(Fluids.ACID, 800)));
		recipes.add(new ChemRecipe(41, "CIRCUIT_4", 200)
				.inputItems(
						new ComparableStack(ModItems.circuit_red_copper),
						new ComparableStack(ModItems.wire_gold, 4),
						new OreDictStack(LAPIS.dust()),
						new OreDictStack(ANY_PLASTIC.ingot()))
				.inputFluids(new FluidStack(Fluids.ACID, 400), new FluidStack(Fluids.PETROLEUM, 200))
				.outputItems(new ItemStack(ModItems.circuit_gold)));
		recipes.add(new ChemRecipe(42, "CIRCUIT_5", 250)
				.inputItems(
						new ComparableStack(ModItems.circuit_gold),
						new ComparableStack(ModItems.wire_schrabidium, 4),
						new OreDictStack(DIAMOND.dust()),
						new OreDictStack(DESH.ingot()))
				.inputFluids(new FluidStack(Fluids.ACID, 800), new FluidStack(Fluids.MERCURY, 200))
				.outputItems(new ItemStack(ModItems.circuit_schrabidium)));
		recipes.add(new ChemRecipe(43, "POLYMER", 100)
				.inputItems(
						new OreDictStack(COAL.dust(), 2),
						new OreDictStack(F.dust()))
				.inputFluids(new FluidStack(Fluids.PETROLEUM, 500))
				.outputItems(new ItemStack(ModItems.ingot_polymer)));
		recipes.add(new ChemRecipe(81, "BAKELITE", 100)
				.inputFluids(
						new FluidStack(Fluids.AROMATICS, 500),
						new FluidStack(Fluids.PETROLEUM, 500))
				.outputItems(new ItemStack(ModItems.ingot_bakelite)));
		recipes.add(new ChemRecipe(82, "RUBBER", 100)
				.inputItems(new OreDictStack(S.dust()))
				.inputFluids(new FluidStack(Fluids.UNSATURATEDS, 500))
				.outputItems(new ItemStack(ModItems.ingot_rubber)));
		recipes.add(new ChemRecipe(83, "TNT", 150)
				.inputItems(new OreDictStack(KNO.dust()))
				.inputFluids(new FluidStack(Fluids.AROMATICS, 500))
				.outputItems(new ItemStack(ModItems.ball_tnt, 4)));
		recipes.add(new ChemRecipe(89, "DYNAMITE", 50)
				.inputItems(
						new ComparableStack(Items.sugar),
						new OreDictStack(KNO.dust()),
						new OreDictStack("sand"))
				.outputItems(new ItemStack(ModItems.ball_dynamite, 2)));
		recipes.add(new ChemRecipe(84, "C4", 150)
				.inputItems(new OreDictStack(KNO.dust()))
				.inputFluids(new FluidStack(Fluids.UNSATURATEDS, 500))
				.outputItems(new ItemStack(ModItems.ingot_c4, 4)));
		//44, formerly deuterium
		//45, formerly steam
		recipes.add(new ChemRecipe(46, "YELLOWCAKE", 250)
				.inputItems(
						new OreDictStack(U.billet(), 2), //12 nuggets: the numbers do match up :)
						new OreDictStack(S.dust(), 2))
				.inputFluids(new FluidStack(Fluids.ACID, 500))
				.outputItems(new ItemStack(ModItems.powder_yellowcake)));
		recipes.add(new ChemRecipe(47, "UF6", 100)
				.inputItems(
						new ComparableStack(ModItems.powder_yellowcake),
						new OreDictStack(F.dust(), 4))
				.inputFluids(new FluidStack(Fluids.WATER, 1000))
				.outputItems(new ItemStack(ModItems.sulfur, 2))
				.outputFluids(new FluidStack(Fluids.UF6, 1200)));
		recipes.add(new ChemRecipe(48, "PUF6", 150)
				.inputItems(
						new OreDictStack(PU.dust()),
						new OreDictStack(F.dust(), 3))
				.inputFluids(new FluidStack(Fluids.WATER, 1000))
				.outputFluids(new FluidStack(Fluids.PUF6, 900)));
		recipes.add(new ChemRecipe(49, "SAS3", 200)
				.inputItems(
						new OreDictStack(SA326.dust()),
						new OreDictStack(S.dust(), 2))
				.inputFluids(new FluidStack(Fluids.ACID, 2000))
				.outputFluids(new FluidStack(Fluids.SAS3, 1000)));
		recipes.add(new ChemRecipe(50, "DYN_SCHRAB", 1200)
				.inputItems(
						new ComparableStack(ModItems.dynosphere_desh_charged, 3),
						new OreDictStack(U.ingot()),
						new ComparableStack(ModItems.catalyst_clay, 8))
				.outputItems(
						new ItemStack(ModItems.ingot_schrabidium),
						new ItemStack(ModItems.powder_desh),
						new ItemStack(ModItems.powder_desh_mix))
				.outputFluids(new FluidStack(Fluids.WATZ, 50)));
		recipes.add(new ChemRecipe(51, "DYN_EUPH", 3600)
				.inputItems(
						new ComparableStack(ModItems.dynosphere_schrabidium_charged, 1),
						new OreDictStack(PU.ingot()),
						new ComparableStack(ModItems.catalyst_clay, 16),
						new OreDictStack(EUPH.ingot()))
				.outputItems(
						new ItemStack(ModItems.nugget_euphemium, 12),
						new ItemStack(ModItems.powder_schrabidium, 4),
						new ItemStack(ModItems.powder_power, 4))
				.outputFluids(new FluidStack(Fluids.WATZ, 100)));
		recipes.add(new ChemRecipe(52, "DYN_DNT", 6000)
				.inputItems(
						new ComparableStack(ModItems.dynosphere_euphemium_charged, 2),
						new ComparableStack(ModItems.powder_spark_mix),
						new ComparableStack(ModItems.ingot_starmetal),
						new ComparableStack(ModItems.catalyst_clay, 32))
				.outputItems(
						new ItemStack(ModItems.ingot_dineutronium),
						new ItemStack(ModItems.powder_euphemium, 8),
						new ItemStack(ModItems.powder_nitan_mix, 8))
				.outputFluids(new FluidStack(Fluids.WATZ, 150)));
		recipes.add(new ChemRecipe(53, "CORDITE", 40)
				.inputItems(
						new OreDictStack(KNO.dust(), 2),
						new OreDictStack(KEY_PLANKS),
						new ComparableStack(Items.sugar))
				.inputFluids(new FluidStack(Fluids.HEATINGOIL, 200))
				.outputItems(new ItemStack(ModItems.cordite, 4)));
		recipes.add(new ChemRecipe(54, "KEVLAR", 40)
				.inputItems(
						new OreDictStack(KNO.dust(), 2),
						new ComparableStack(Items.brick),
						new OreDictStack(COAL.dust()))
				.inputFluids(new FluidStack(Fluids.PETROLEUM, 100))
				.outputItems(new ItemStack(ModItems.plate_kevlar, 4)));
		recipes.add(new ChemRecipe(55, "CONCRETE", 100)
				.inputItems(
						new ComparableStack(Blocks.gravel, 8),
						new ComparableStack(Blocks.sand, 8))
				.inputFluids(new FluidStack(Fluids.WATER, 2000))
				.outputItems(new ItemStack(ModBlocks.concrete_smooth, 16)));
		recipes.add(new ChemRecipe(56, "CONCRETE_ASBESTOS", 100)
				.inputItems(
						new ComparableStack(Blocks.gravel, 2),
						new ComparableStack(Blocks.sand, 2),
						new OreDictStack(ASBESTOS.ingot(), 4))
				.inputFluids(new FluidStack(Fluids.WATER, 2000))
				.outputItems(new ItemStack(ModBlocks.concrete_asbestos, 16)));
		recipes.add(new ChemRecipe(79, "DUCRETE", 150)
				.inputItems(
						new ComparableStack(Blocks.sand, 8),
						new OreDictStack(U238.billet(), 2),
						new ComparableStack(Items.clay_ball, 4))
				.inputFluids(new FluidStack(Fluids.WATER, 2000))
				.outputItems(new ItemStack(ModBlocks.ducrete_smooth, 8)));
		recipes.add(new ChemRecipe(57, "SOLID_FUEL", 200)
				.inputItems(
						new ComparableStack(ModItems.solid_fuel, 2),
						new OreDictStack(KNO.dust()),
						new OreDictStack(REDSTONE.dust()))
				.inputFluids(new FluidStack(Fluids.PETROLEUM, 200))
				.outputItems(new ItemStack(ModItems.rocket_fuel, 4)));
		recipes.add(new ChemRecipe(58, "ELECTROLYSIS", 150)
				.inputFluids(new FluidStack(Fluids.WATER, 8000))
				.outputFluids(
						new FluidStack(Fluids.HYDROGEN, 800),
						new FluidStack(Fluids.OXYGEN, 800)));
		recipes.add(new ChemRecipe(59, "XENON", 300)
				.inputFluids(new FluidStack(Fluids.NONE, 0))
				.outputFluids(new FluidStack(Fluids.XENON, 50)));
		recipes.add(new ChemRecipe(60, "XENON_OXY", 20)
				.inputFluids(new FluidStack(Fluids.OXYGEN, 250))
				.outputFluids(new FluidStack(Fluids.XENON, 50)));
		recipes.add(new ChemRecipe(61, "SATURN", 60)
				.inputItems(
						new ComparableStack(ModItems.powder_dura_steel),
						new OreDictStack(P_RED.dust()))
				.inputFluids(
						new FluidStack(Fluids.ACID, 100),
						new FluidStack(Fluids.MERCURY, 50))
				.outputItems(new ItemStack(ModItems.ingot_saturnite, 2)));
		recipes.add(new ChemRecipe(62, "BALEFIRE", 100)
				.inputItems(new ComparableStack(ModItems.egg_balefire_shard))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 6000))
				.outputItems(new ItemStack(ModItems.powder_balefire))
				.outputFluids(new FluidStack(Fluids.BALEFIRE, 8000)));
		recipes.add(new ChemRecipe(63, "SCHRABIDIC", 100)
				.inputItems(new ComparableStack(ModItems.pellet_charged))
				.inputFluids(
						new FluidStack(Fluids.SAS3, 8000),
						new FluidStack(Fluids.ACID, 6000))
				.outputFluids(new FluidStack(Fluids.SCHRABIDIC, 16000)));
		recipes.add(new ChemRecipe(64, "SCHRABIDATE", 150)
				.inputItems(new OreDictStack(IRON.dust()))
				.inputFluids(new FluidStack(Fluids.SCHRABIDIC, 250))
				.outputItems(new ItemStack(ModItems.powder_schrabidate)));
		recipes.add(new ChemRecipe(65, "COLTAN_CLEANING", 60)
				.inputItems(
						new OreDictStack(COLTAN.dust(), 2),
						new OreDictStack(COAL.dust()))
				.inputFluids(
						new FluidStack(Fluids.ACID, 250),
						new FluidStack(Fluids.HYDROGEN, 500))
				.outputItems(
						new ItemStack(ModItems.powder_coltan),
						new ItemStack(ModItems.powder_niobium),
						new ItemStack(ModItems.dust))
				.outputFluids(new FluidStack(Fluids.WATER, 500)));
		recipes.add(new ChemRecipe(66, "COLTAN_PAIN", 120)
				.inputItems(
						new ComparableStack(ModItems.powder_coltan),
						new OreDictStack(F.dust()))
				.inputFluids(
						new FluidStack(Fluids.GAS, 1000),
						new FluidStack(Fluids.OXYGEN, 500))
				.outputFluids(new FluidStack(Fluids.PAIN, 1000)));
		recipes.add(new ChemRecipe(67, "COLTAN_CRYSTAL", 80)
				.inputFluids(
						new FluidStack(Fluids.PAIN, 1000),
						new FluidStack(Fluids.ACID, 500))
				.outputItems(
						new ItemStack(ModItems.gem_tantalium),
						new ItemStack(ModItems.dust, 3))
				.outputFluids(new FluidStack(Fluids.WATER, 250)));
		recipes.add(new ChemRecipe(68, "VIT_LIQUID", 100)
				.inputItems(new ComparableStack(ModBlocks.sand_lead))
				.inputFluids(new FluidStack(Fluids.WASTEFLUID, 1000))
				.outputItems(new ItemStack(ModItems.nuclear_waste_vitrified)));
		recipes.add(new ChemRecipe(69, "VIT_GAS", 100)
				.inputItems(new ComparableStack(ModBlocks.sand_lead))
				.inputFluids(new FluidStack(Fluids.WASTEGAS, 1000))
				.outputItems(new ItemStack(ModItems.nuclear_waste_vitrified)));
		recipes.add(new ChemRecipe(88, "LUBRICANT", 20)
				.inputFluids(
						new FluidStack(Fluids.HEATINGOIL, 500),
						new FluidStack(Fluids.UNSATURATEDS, 500))
				.outputFluids(new FluidStack(Fluids.LUBRICANT, 1000)));
		recipes.add(new ChemRecipe(70, "TEL", 40)
				.inputItems(
						new OreDictStack(ANY_TAR.any()),
						new OreDictStack(PB.dust()))
				.inputFluids(
						new FluidStack(Fluids.PETROLEUM, 100),
						new FluidStack(Fluids.STEAM, 1000))
				.outputItems(new ItemStack(ModItems.antiknock)));
		recipes.add(new ChemRecipe(4, "FR_REOIL", 30)
				.inputFluids(new FluidStack(1000, Fluids.SMEAR))
				.outputFluids(new FluidStack(800, Fluids.RECLAIMED)));
		recipes.add(new ChemRecipe(5, "FR_PETROIL", 30)
				.inputFluids(
						new FluidStack(800, Fluids.RECLAIMED),
						new FluidStack(200, Fluids.LUBRICANT))
				.outputFluids(new FluidStack(1000, Fluids.PETROIL)));
		recipes.add(new ChemRecipe(86, "PETROIL_LEADED", 40)
				.inputItems(new ComparableStack(ModItems.antiknock))
				.inputFluids(new FluidStack(Fluids.PETROIL, 10_000))
				.outputFluids(new FluidStack(Fluids.PETROIL_LEADED, 12_000)));
		recipes.add(new ChemRecipe(71, "GASOLINE", 40)
				.inputFluids(new FluidStack(Fluids.NAPHTHA, 1000))
				.outputFluids(new FluidStack(Fluids.GASOLINE, 800)));
		recipes.add(new ChemRecipe(85, "GASOLINE_LEADED", 40)
				.inputItems(new ComparableStack(ModItems.antiknock))
				.inputFluids(new FluidStack(Fluids.GASOLINE, 10_000))
				.outputFluids(new FluidStack(Fluids.GASOLINE_LEADED, 12_000)));
		recipes.add(new ChemRecipe(87, "COALGAS_LEADED", 40)
				.inputItems(new ComparableStack(ModItems.antiknock))
				.inputFluids(new FluidStack(Fluids.COALGAS, 10_000))
				.outputFluids(new FluidStack(Fluids.COALGAS_LEADED, 12_000)));
		recipes.add(new ChemRecipe(72, "FRACKSOL", 20)
				.inputItems(new OreDictStack(S.dust()))
				.inputFluids(
						new FluidStack(Fluids.PETROLEUM, 100),
						new FluidStack(Fluids.WATER, 1000))
				.outputFluids(new FluidStack(Fluids.FRACKSOL, 1000)));
		recipes.add(new ChemRecipe(73, "HELIUM3", 200)
				.inputItems(new ComparableStack(ModBlocks.moon_turf, 8))
				.outputFluids(new FluidStack(Fluids.HELIUM3, 1000)));
		recipes.add(new ChemRecipe(74, "OSMIRIDIUM_DEATH", 240)
				.inputItems(
						new ComparableStack(ModItems.powder_paleogenite),
						new OreDictStack(F.dust(), 8),
						new ComparableStack(ModItems.nugget_bismuth, 4))
				.inputFluids(new FluidStack(Fluids.ACID, 1000))
				.outputFluids(new FluidStack(Fluids.DEATH, 1000)));
		recipes.add(new ChemRecipe(75, "ETHANOL", 50)
				.inputItems(new ComparableStack(Items.sugar, 6))
				.outputFluids(new FluidStack(Fluids.ETHANOL, 1000)));
		recipes.add(new ChemRecipe(76, "METH", 30)
				.inputItems(
						new ComparableStack(Items.wheat),
						new ComparableStack(Items.dye, 2, 3))
				.inputFluids(
						new FluidStack(Fluids.LUBRICANT, 400),
						new FluidStack(Fluids.ACID, 400))
				.outputItems(new ItemStack(ModItems.chocolate, 4)));
		recipes.add(new ChemRecipe(77, "CO2", 60)
				.inputFluids(new FluidStack(Fluids.GAS, 1000))
				.outputFluids(new FluidStack(Fluids.CARBONDIOXIDE, 1000)));
		recipes.add(new ChemRecipe(78, "HEAVY_ELECTROLYSIS", 150)
				.inputFluids(new FluidStack(Fluids.HEAVYWATER, 8000))
				.outputFluids(
						new FluidStack(Fluids.DEUTERIUM, 400),
						new FluidStack(Fluids.OXYGEN, 400)));
		recipes.add(new ChemRecipe(80, "EPEARL", 100)
				.inputItems(new OreDictStack(DIAMOND.dust(), 1))
				.inputFluids(new FluidStack(Fluids.XPJUICE, 500))
				.outputFluids(new FluidStack(Fluids.ENDERJUICE, 100)));
		
	}
	
	public static void registerFuelProcessing() {
		recipes.add(new ChemRecipe(0, "FP_HEAVYOIL", 50)
				.inputFluids(new FluidStack(1000, Fluids.HEAVYOIL))
				.outputFluids(
						new FluidStack(RefineryRecipes.heavy_frac_bitu * 10, Fluids.BITUMEN),
						new FluidStack(RefineryRecipes.heavy_frac_smear * 10, Fluids.SMEAR)
						));
		recipes.add(new ChemRecipe(1, "FP_SMEAR", 50)
				.inputFluids(new FluidStack(1000, Fluids.SMEAR))
				.outputFluids(
						new FluidStack(RefineryRecipes.smear_frac_heat * 10, Fluids.HEATINGOIL),
						new FluidStack(RefineryRecipes.smear_frac_lube * 10, Fluids.LUBRICANT)
						));
		recipes.add(new ChemRecipe(2, "FP_NAPHTHA", 50)
				.inputFluids(new FluidStack(1000, Fluids.NAPHTHA))
				.outputFluids(
						new FluidStack(RefineryRecipes.napht_frac_heat * 10, Fluids.HEATINGOIL),
						new FluidStack(RefineryRecipes.napht_frac_diesel * 10, Fluids.DIESEL)
						));
		recipes.add(new ChemRecipe(3, "FP_LIGHTOIL", 50)
				.inputFluids(new FluidStack(1000, Fluids.LIGHTOIL))
				.outputFluids(
						new FluidStack(RefineryRecipes.light_frac_diesel * 10, Fluids.DIESEL),
						new FluidStack(RefineryRecipes.light_frac_kero * 10, Fluids.KEROSENE)
						));
	}

	public static void registerOtherOil() {
		recipes.add(new ChemRecipe(31, "BP_BIOGAS", 200)
				.inputItems(new ComparableStack(ModItems.biomass, 16))
				.outputFluids(new FluidStack(4000, Fluids.BIOGAS)));
		recipes.add(new ChemRecipe(32, "BP_BIOFUEL", 100)
				.inputFluids(new FluidStack(2000, Fluids.BIOGAS))
				.outputFluids(new FluidStack(1000, Fluids.BIOFUEL)));
		recipes.add(new ChemRecipe(33, "LPG", 100)
				.inputFluids(new FluidStack(2000, Fluids.PETROLEUM))
				.outputFluids(new FluidStack(1000, Fluids.LPG)));
		recipes.add(new ChemRecipe(34, "OIL_SAND", 200)
				.inputItems(new ComparableStack(ModBlocks.ore_oil_sand, 16), new OreDictStack(ANY_TAR.any(), 1))
				.outputItems(new ItemStack(Blocks.sand, 4), new ItemStack(Blocks.sand, 4), new ItemStack(Blocks.sand, 4), new ItemStack(Blocks.sand, 4))
				.outputFluids(new FluidStack(1000, Fluids.BITUMEN)));
		recipes.add(new ChemRecipe(35, "ASPHALT", 100)
				.inputItems(new ComparableStack(Blocks.gravel, 2), new ComparableStack(Blocks.sand, 6))
				.inputFluids(new FluidStack(1000, Fluids.BITUMEN))
				.outputItems(new ItemStack(ModBlocks.asphalt, 16)));
	}
	
	public static class ChemRecipe {

		public int listing;
		private int id;
		public String name;
		public AStack[] inputs;
		public FluidStack[] inputFluids;
		public ItemStack[] outputs;
		public FluidStack[] outputFluids;
		private int duration;
		
		public ChemRecipe(int index, String name, int duration) {
			this.id = index;
			this.name = name;
			this.duration = duration;
			this.listing = recipes.size();
			
			this.inputs = new AStack[4];
			this.outputs = new ItemStack[4];
			this.inputFluids = new FluidStack[2];
			this.outputFluids = new FluidStack[2];
			
			if(!indexMapping.containsKey(id)) {
				indexMapping.put(id, this);
			} else {
				throw new IllegalStateException("Chemical plant recipe " + name + " has been registered with duplicate id " + id + " used by " + indexMapping.get(id).name + "!");
			}
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
		
		public int getId() {
			return this.id;
		}
		
		public int getDuration() {
			return this.duration;
		}
	}
}