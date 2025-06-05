package com.hbm.inventory.recipes;

import static com.hbm.inventory.OreDictManager.*;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.inventory.recipes.loader.GenericRecipes;
import com.hbm.items.ItemEnums.EnumFuelAdditive;
import com.hbm.items.ModItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ChemicalPlantRecipes extends GenericRecipes<GenericRecipe> {
	
	public static final ChemicalPlantRecipes INSTANCE = new ChemicalPlantRecipes();

	@Override public int inputItemLimit() { return 3; }
	@Override public int inputFluidLimit() { return 3; }
	@Override public int outputItemLimit() { return 3; }
	@Override public int outputFluidLimit() { return 3; }

	@Override public String getFileName() { return "hbmChemicalPlant.json"; }
	@Override public GenericRecipe instantiateRecipe(String name) { return new GenericRecipe(name); }

	@Override
	public void registerDefaults() {
		
		/// REGULAR FLUIDS ///
		this.register(new GenericRecipe("chem.hydrogen").setupNamed(20, 400).setIcon(ModItems.gas_full, Fluids.HYDROGEN.getID())
				.inputItems(new OreDictStack(COAL.gem(), 1))
				.inputFluids(new FluidStack(Fluids.WATER, 8_000))
				.outputFluids(new FluidStack(Fluids.HYDROGEN, 500)));
		
		this.register(new GenericRecipe("chem.hydrogencoke").setupNamed(20, 400).setIcon(ModItems.gas_full, Fluids.HYDROGEN.getID())
				.inputItems(new OreDictStack(ANY_COKE.gem(), 1))
				.inputFluids(new FluidStack(Fluids.WATER, 8_000))
				.outputFluids(new FluidStack(Fluids.HYDROGEN, 500)));
		
		this.register(new GenericRecipe("chem.oxygen").setupNamed(20, 400).setIcon(ModItems.gas_full, Fluids.OXYGEN.getID())
				.inputFluids(new FluidStack(Fluids.AIR, 8_000))
				.outputFluids(new FluidStack(Fluids.OXYGEN, 500)));
		
		this.register(new GenericRecipe("chem.xenon").setupNamed(300, 1_000).setIcon(ModItems.gas_full, Fluids.XENON.getID())
				.inputFluids(new FluidStack(Fluids.AIR, 16_000))
				.outputFluids(new FluidStack(Fluids.XENON, 50)));
		
		this.register(new GenericRecipe("chem.xenonoxy").setupNamed(20, 1_000).setIcon(ModItems.gas_full, Fluids.XENON.getID())
				.inputFluids(new FluidStack(Fluids.AIR, 8_000), new FluidStack(Fluids.OXYGEN, 250))
				.outputFluids(new FluidStack(Fluids.XENON, 50)));
		
		this.register(new GenericRecipe("chem.helium3").setupNamed(200, 2_000).setIcon(ModItems.gas_full, Fluids.HELIUM3.getID())
				.inputItems(new ComparableStack(ModBlocks.moon_turf, 8))
				.outputFluids(new FluidStack(Fluids.HELIUM3, 1_000)));
		
		this.register(new GenericRecipe("chem.co2").setup(60, 100)
				.inputFluids(new FluidStack(Fluids.GAS, 1_000))
				.outputFluids(new FluidStack(Fluids.CARBONDIOXIDE, 1_000)));
		
		this.register(new GenericRecipe("chem.perfluoromethyl").setup(20, 100)
				.inputItems(new OreDictStack(F.dust()))
				.inputFluids(new FluidStack(Fluids.PETROLEUM, 1_000), new FluidStack(Fluids.UNSATURATEDS, 500))
				.outputFluids(new FluidStack(Fluids.PERFLUOROMETHYL, 1_000)));
		
		this.register(new GenericRecipe("chem.cccentrifuge").setup(200, 100)
				.inputFluids(new FluidStack(Fluids.CHLOROCALCITE_CLEANED, 500), new FluidStack(Fluids.SULFURIC_ACID, 8_000))
				.outputFluids(new FluidStack(Fluids.POTASSIUM_CHLORIDE, 250), new FluidStack(Fluids.CALCIUM_CHLORIDE, 250)));
		
		/// OILS ///
		this.register(new GenericRecipe("chem.ethanol").setupNamed(50, 100).setIcon(ModItems.canister_full, Fluids.ETHANOL.getID())
				.inputItems(new ComparableStack(Items.sugar, 10))
				.outputFluids(new FluidStack(Fluids.ETHANOL, 1000)));
		
		this.register(new GenericRecipe("chem.biogas").setupNamed(60, 100).setIcon(ModItems.gas_full, Fluids.BIOGAS.getID())
				.inputItems(new ComparableStack(ModItems.biomass, 16))
				.inputFluids(new FluidStack(Fluids.AIR, 4_000))
				.outputFluids(new FluidStack(Fluids.BIOGAS, 2_000)));
		
		this.register(new GenericRecipe("chem.biofuel").setupNamed(60, 100).setIcon(ModItems.canister_full, Fluids.BIOFUEL.getID())
				.inputFluids(new FluidStack(Fluids.BIOGAS, 1_500), new FluidStack(Fluids.ETHANOL, 250))
				.outputFluids(new FluidStack(Fluids.BIOFUEL, 1_000)));
		
		this.register(new GenericRecipe("chem.reoil").setupNamed(40, 100).setIcon(ModItems.canister_full, Fluids.RECLAIMED.getID())
				.inputFluids(new FluidStack(Fluids.SMEAR, 1_000))
				.outputFluids(new FluidStack(Fluids.RECLAIMED, 800)));
		
		this.register(new GenericRecipe("chem.gasoline").setupNamed(40, 100).setIcon(ModItems.canister_full, Fluids.GASOLINE.getID())
				.inputFluids(new FluidStack(Fluids.NAPHTHA, 1000))
				.outputFluids(new FluidStack(Fluids.GASOLINE, 800)));
		
		this.register(new GenericRecipe("chem.tarsand").setupNamed(200, 100).setIcon(ModBlocks.ore_oil_sand)
				.inputItems(new ComparableStack(ModBlocks.ore_oil_sand, 16), new OreDictStack(ANY_TAR.any(), 1))
				.outputItems(new ItemStack(Blocks.sand, 16))
				.outputFluids(new FluidStack(Fluids.BITUMEN, 1_000)));
		
		this.register(new GenericRecipe("chem.tel").setup(40, 100)
				.inputItems(new OreDictStack(ANY_TAR.any()), new OreDictStack(PB.dust()))
				.inputFluids(new FluidStack(Fluids.PETROLEUM, 100), new FluidStack(Fluids.STEAM, 1000))
				.outputItems(DictFrame.fromOne(ModItems.fuel_additive, EnumFuelAdditive.ANTIKNOCK)));
		
		this.register(new GenericRecipe("chem.deicer").setup(40, 100)
				.inputFluids(new FluidStack(Fluids.GAS, 100), new FluidStack(Fluids.HYDROGEN, 50))
				.outputItems(DictFrame.fromOne(ModItems.fuel_additive, EnumFuelAdditive.DEICER)));

		/// THE CONC AND ASPHALE ///
		this.register(new GenericRecipe("chem.concrete").setup(100, 100)
				.inputItems(new ComparableStack(ModItems.powder_cement, 1), new ComparableStack(Blocks.gravel, 8), new OreDictStack(KEY_SAND, 8))
				.inputFluids(new FluidStack(Fluids.WATER, 2_000))
				.outputItems(new ItemStack(ModBlocks.concrete_smooth, 16)));
		
		this.register(new GenericRecipe("chem.concreteasbestos").setup(100, 100)
				.inputItems(new ComparableStack(ModItems.powder_cement, 4), new OreDictStack(ASBESTOS.ingot(), (GeneralConfig.enableLBSM && GeneralConfig.enableLBSMSimpleChemsitry) ? 1 : 4), new OreDictStack(KEY_SAND, 8))
				.inputFluids(new FluidStack(Fluids.WATER, 2_000))
				.outputItems(new ItemStack(ModBlocks.concrete_asbestos, 16)));

		this.register(new GenericRecipe("chem.ducrete").setup(150, 100)
				.inputItems(new ComparableStack(ModItems.powder_cement, 4), new OreDictStack(FERRO.ingot()), new OreDictStack(KEY_SAND, 8))
				.inputFluids(new FluidStack(Fluids.WATER, 2_000))
				.outputItems(new ItemStack(ModBlocks.ducrete_smooth, 8)));
		
		this.register(new GenericRecipe("chem.asphalt").setup(100, 100)
				.inputItems(new ComparableStack(Blocks.gravel, 2), new OreDictStack(KEY_SAND, 6))
				.inputFluids(new FluidStack(Fluids.BITUMEN, 1_000))
				.outputItems(new ItemStack(ModBlocks.asphalt, 16)));
		
		/// SOLIDS ///
		this.register(new GenericRecipe("chem.desh").setup(200, 100)
				.inputItems(new ComparableStack(ModItems.powder_desh_mix))
				.inputFluids((GeneralConfig.enableLBSM && GeneralConfig.enableLBSMSimpleChemsitry) ?
								new FluidStack[] {new FluidStack(Fluids.LIGHTOIL, 200)} :
								new FluidStack[] {new FluidStack(Fluids.LIGHTOIL, 200), new FluidStack(Fluids.MERCURY, 200)})
				.outputItems(new ItemStack(ModItems.ingot_desh)));
		
		this.register(new GenericRecipe("chem.polymer").setup(100, 100)
				.inputItems(new OreDictStack(COAL.dust(), 2), new OreDictStack(F.dust()))
				.inputFluids(new FluidStack(Fluids.PETROLEUM, 500, GeneralConfig.enable528 ? 1 : 0))
				.outputItems(new ItemStack(ModItems.ingot_polymer)));
		
		this.register(new GenericRecipe("chem.bakelite").setup(100, 100)
				.inputFluids(new FluidStack(Fluids.AROMATICS, 500, GeneralConfig.enable528 ? 1 : 0), new FluidStack(Fluids.PETROLEUM, 500, GeneralConfig.enable528 ? 1 : 0))
				.outputItems(new ItemStack(ModItems.ingot_bakelite)));
		
		this.register(new GenericRecipe("chem.rubber").setup(100, 200)
				.inputItems(new OreDictStack(S.dust()))
				.inputFluids(new FluidStack(Fluids.UNSATURATEDS, 500, GeneralConfig.enable528 ? 2 : 0))
				.outputItems(new ItemStack(ModItems.ingot_rubber)));
		
		this.register(new GenericRecipe("chem.hardplastic").setup(100, 1_000)
				.inputFluids(new FluidStack(Fluids.XYLENE, 500, GeneralConfig.enable528 ? 2 : 0), new FluidStack(Fluids.PHOSGENE, 500, GeneralConfig.enable528 ? 2 : 0))
				.outputItems(new ItemStack(ModItems.ingot_pc)));
		
		this.register(new GenericRecipe("chem.pvc").setup(100, 1_000)
				.inputItems(new OreDictStack(CD.dust()))
				.inputFluids(new FluidStack(Fluids.UNSATURATEDS, 250, GeneralConfig.enable528 ? 2 : 0), new FluidStack(Fluids.CHLORINE, 250, GeneralConfig.enable528 ? 2 : 0))
				.outputItems(new ItemStack(ModItems.ingot_pvc, 2)));
		
		this.register(new GenericRecipe("chem.kevlar").setup(20, 300)
				.inputFluids(new FluidStack(Fluids.AROMATICS, 200), new FluidStack(Fluids.NITRIC_ACID, 100), new FluidStack(GeneralConfig.enable528 ? Fluids.PHOSGENE : Fluids.CHLORINE, 100))
				.outputItems(new ItemStack(ModItems.plate_kevlar, 4)));
		
		this.register(new GenericRecipe("chem.meth").setup(30, 300)
				.inputItems(new ComparableStack(Items.wheat), new ComparableStack(Items.dye, 2, 3))
				.inputFluids(new FluidStack(Fluids.LUBRICANT, 400), new FluidStack(Fluids.PEROXIDE, 500))
				.outputItems(new ItemStack(ModItems.chocolate, 4)));
		
		this.register(new GenericRecipe("chem.epearl").setup(100, 300)
				.inputItems(new OreDictStack(DIAMOND.dust(), 1))
				.inputFluids(new FluidStack(Fluids.XPJUICE, 500))
				.outputFluids(new FluidStack(Fluids.ENDERJUICE, 100)));
		
		this.register(new GenericRecipe("chem.meatprocessing").setupNamed(200, 200).setIcon(ModItems.glyphid_meat)
				.inputItems(new OreDictStack(KEY_GLYPHID_MEAT, 3))
				.inputFluids(new FluidStack(Fluids.WATER, 1_000))
				.outputItems(new ItemStack(ModItems.sulfur, 4), new ItemStack(ModItems.niter, 3))
				.outputFluids(new FluidStack(Fluids.SALIENT, 250)));
		
		this.register(new GenericRecipe("chem.rustysteel").setup(40, 100)
				.inputItems(new ComparableStack(ModBlocks.deco_steel, 8))
				.inputFluids(new FluidStack(Fluids.WATER, 1000))
				.outputItems(new ItemStack(ModBlocks.deco_rusty_steel, 8)));

		/// ACIDS ///
		this.register(new GenericRecipe("chem.peroxide").setup(50, 100)
				.inputFluids(new FluidStack(Fluids.WATER, 1_000))
				.outputFluids(new FluidStack(Fluids.PEROXIDE, 1_000)));
		
		this.register(new GenericRecipe("chem.sulfuricacid").setup(50, 100)
				.inputItems(new OreDictStack(S.dust()))
				.inputFluids(new FluidStack(Fluids.PEROXIDE, 1_000), new FluidStack(Fluids.WATER, 1_000))
				.outputFluids(new FluidStack(Fluids.SULFURIC_ACID, 2_000)));
		
		this.register(new GenericRecipe("chem.nitricacid").setup(50, 100)
				.inputItems(new OreDictStack(KNO.dust()))
				.inputFluids(new FluidStack(Fluids.SULFURIC_ACID, 500))
				.outputFluids(new FluidStack(Fluids.NITRIC_ACID, 1_000)));
		
		this.register(new GenericRecipe("chem.birkeland").setupNamed(200, 5_000)
				.inputFluids(new FluidStack(Fluids.AIR, 8_000), new FluidStack(Fluids.WATER, 2_000))
				.outputFluids(new FluidStack(Fluids.NITRIC_ACID, 1_000)));
		
		this.register(new GenericRecipe("chem.schrabidic").setup(100, 5_000)
				.inputItems(new ComparableStack(ModItems.pellet_charged))
				.inputFluids(new FluidStack(Fluids.SAS3, 8000), new FluidStack(Fluids.PEROXIDE, 6000))
				.outputFluids(new FluidStack(Fluids.SCHRABIDIC, 16000)));
		
		this.register(new GenericRecipe("chem.schrabidate").setup(150, 5_000)
				.inputFluids(new FluidStack(Fluids.SCHRABIDIC, 250))
				.outputItems(new ItemStack(ModItems.powder_schrabidate)));
		
		/// COLTAN ///
		this.register(new GenericRecipe("chem.coltancleaning").setup(60, 100)
				.inputItems(new OreDictStack(COLTAN.dust(), 2), new OreDictStack(COAL.dust()))
				.inputFluids(new FluidStack(Fluids.PEROXIDE, 250), new FluidStack(Fluids.HYDROGEN, 500))
				.outputItems(new ItemStack(ModItems.powder_coltan), new ItemStack(ModItems.powder_niobium), new ItemStack(ModItems.dust))
				.outputFluids(new FluidStack(Fluids.WATER, 500)));

		this.register(new GenericRecipe("chem.coltanpain").setup(120, 100)
				.inputItems(new ComparableStack(ModItems.powder_coltan), new OreDictStack(F.dust()))
				.inputFluids(new FluidStack(Fluids.GAS, 1000), new FluidStack(Fluids.OXYGEN, 500))
				.outputFluids(new FluidStack(Fluids.PAIN, 1000)));

		this.register(new GenericRecipe("chem.coltancrystal").setup(80, 100)
				.inputFluids(new FluidStack(Fluids.PAIN, 1000), new FluidStack(Fluids.PEROXIDE, 500))
				.outputItems(new ItemStack(ModItems.gem_tantalium), new ItemStack(ModItems.dust, 3))
				.outputFluids(new FluidStack(Fluids.WATER, 250)));
		
		/// EXPLOSIVES ///
		this.register(new GenericRecipe("chem.cordite").setup(40, 100)
				.inputItems(new OreDictStack(KNO.dust(), 2), new ComparableStack(ModItems.powder_sawdust, 2))
				.inputFluids((GeneralConfig.enableLBSM && GeneralConfig.enableLBSMSimpleChemsitry) ? new FluidStack(Fluids.HEATINGOIL, 200) : new FluidStack(Fluids.GAS, 200))
				.outputItems(new ItemStack(ModItems.cordite, 4)));
		
		this.register(new GenericRecipe("chem.rocketfuel").setup(200, 100)
				.inputItems(new ComparableStack(ModItems.solid_fuel, 2))
				.inputFluids(new FluidStack(Fluids.PETROLEUM, 200, GeneralConfig.enable528 ? 1 : 0), new FluidStack(Fluids.NITRIC_ACID, 100))
				.outputItems(new ItemStack(ModItems.rocket_fuel, 4)));
		
		this.register(new GenericRecipe("chem.dynamite").setup(50, 100)
				.inputItems(new ComparableStack(Items.sugar), new OreDictStack(KNO.dust()), new OreDictStack(KEY_SAND))
				.outputItems(new ItemStack(ModItems.ball_dynamite, 2)));
		
		this.register(new GenericRecipe("chem.tnt").setup(100, 1_000)
				.inputItems(new OreDictStack(KNO.dust()))
				.inputFluids(new FluidStack(Fluids.AROMATICS, 500, GeneralConfig.enable528 ? 1 : 0))
				.outputItems(new ItemStack(ModItems.ball_tnt, 4)));
		
		this.register(new GenericRecipe("chem.tatb").setup(50, 5_000)
				.inputItems(new ComparableStack(ModItems.ball_tnt))
				.inputFluids(new FluidStack(Fluids.SOURGAS, 200, 1), new FluidStack(Fluids.NITRIC_ACID, 10))
				.outputItems(new ItemStack(ModItems.ball_tatb)));
		
		this.register(new GenericRecipe("chem.c4").setup(100, 1_000)
				.inputItems(new OreDictStack(KNO.dust()))
				.inputFluids(new FluidStack(Fluids.UNSATURATEDS, 500, GeneralConfig.enable528 ? 1 : 0))
				.outputItems(new ItemStack(ModItems.ingot_c4, 4)));
		
		this.register(new GenericRecipe("chem.shellchlorine").setup(100, 1_000)
				.inputItems(new ComparableStack(ModItems.ammo_arty, 1, 0), new OreDictStack(ANY_PLASTIC.ingot(), 1))
				.inputFluids(new FluidStack(Fluids.CHLORINE, 4_000))
				.outputItems(new ItemStack(ModItems.ammo_arty, 1, 9)));
		
		this.register(new GenericRecipe("chem.shellphosgene").setup(100, 1_000)
				.inputItems(new ComparableStack(ModItems.ammo_arty, 1, 0), new OreDictStack(ANY_PLASTIC.ingot(), 1))
				.inputFluids(new FluidStack(Fluids.PHOSGENE, 4_000))
				.outputItems(new ItemStack(ModItems.ammo_arty, 1, 10)));
		
		this.register(new GenericRecipe("chem.shellmustard").setup(100, 1_000)
				.inputItems(new ComparableStack(ModItems.ammo_arty, 1, 0), new OreDictStack(ANY_PLASTIC.ingot(), 1))
				.inputFluids(new FluidStack(Fluids.MUSTARDGAS, 4_000))
				.outputItems(new ItemStack(ModItems.ammo_arty, 1, 11)));
		
		/// GLASS ///
		this.register(new GenericRecipe("chem.laminate").setup(20, 100)
				.inputFluids(new FluidStack(Fluids.XYLENE, 50), new FluidStack(Fluids.PHOSGENE, 50))
				.inputItems(new OreDictStack(KEY_ANYGLASS), new OreDictStack(STEEL.bolt(), 4))
				.outputItems(new ItemStack(ModBlocks.reinforced_laminate)));
		
		/// NUCLEAR PROCESSING ///
		this.register(new GenericRecipe("chem.yellowcake").setup(250, 500)
				.inputItems(new OreDictStack(U.billet(), 2), new OreDictStack(S.dust(), 2))
				.inputFluids(new FluidStack(Fluids.PEROXIDE, 500))
				.outputItems(new ItemStack(ModItems.powder_yellowcake)));

		this.register(new GenericRecipe("chem.uf6").setup(100, 500).setIcon(ModItems.fluid_icon, Fluids.UF6.getID())
				.inputItems(new ComparableStack(ModItems.powder_yellowcake), new OreDictStack(F.dust(), 4))
				.inputFluids(new FluidStack(Fluids.WATER, 1_000))
				.outputItems(new ItemStack(ModItems.sulfur, 2))
				.outputFluids(new FluidStack(Fluids.UF6, 1_200)));

		this.register(new GenericRecipe("chem.puf6").setup(200, 500)
				.inputItems(new OreDictStack(PU.dust()), new OreDictStack(F.dust(), 3))
				.inputFluids(new FluidStack(Fluids.WATER, 1_000))
				.outputFluids(new FluidStack(Fluids.PUF6, 900)));

		this.register(new GenericRecipe("chem.sas3").setup(200, 5_000)
				.inputItems(new OreDictStack(SA326.dust()), new OreDictStack(S.dust(), 2))
				.inputFluids(new FluidStack(Fluids.PEROXIDE, 2_000))
				.outputFluids(new FluidStack(Fluids.SAS3, 1_000)));

		this.register(new GenericRecipe("chem.balefire").setup(100, 10_000).setIcon(ModItems.fluid_icon, Fluids.BALEFIRE.getID())
				.inputItems(new ComparableStack(ModItems.egg_balefire_shard))
				.inputFluids(new FluidStack(Fluids.KEROSENE, 6_000))
				.outputItems(new ItemStack(ModItems.powder_balefire))
				.outputFluids(new FluidStack(Fluids.BALEFIRE, 8_000)));

		this.register(new GenericRecipe("chem.thoriumsalt").setup(100, 10_000).setIcon(ModItems.fluid_icon, Fluids.THORIUM_SALT.getID())
				.inputFluids(new FluidStack(Fluids.THORIUM_SALT_DEPLETED, 16_000))
				.inputItems(new OreDictStack(TH232.nugget(), 2))
				.outputFluids(new FluidStack(Fluids.THORIUM_SALT, 16_000))
				.outputItems(
						new ChanceOutput(new ItemStack(ModItems.nugget_u233, 1), 0.5F),
						new ChanceOutput(new ItemStack(ModItems.nuclear_waste_tiny, 1), 0.25F)));
		
		/// VITRIFICATION ///
		this.register(new GenericRecipe("chem.vitliquid").setup(100, 1_000)
				.inputItems(new ComparableStack(ModBlocks.sand_lead))
				.inputFluids(new FluidStack(Fluids.WASTEFLUID, 1_000))
				.outputItems(new ItemStack(ModItems.nuclear_waste_vitrified)));
		
		this.register(new GenericRecipe("chem.vitgaseous").setup(100, 1_000)
				.inputItems(new ComparableStack(ModBlocks.sand_lead))
				.inputFluids(new FluidStack(Fluids.WASTEGAS, 1_000))
				.outputItems(new ItemStack(ModItems.nuclear_waste_vitrified)));
		
		this.register(new GenericRecipe("chem.vitsolid").setup(300, 1_000)
				.inputItems(new ComparableStack(ModBlocks.sand_lead), new ComparableStack(ModItems.nuclear_waste, 4))
				.outputItems(new ItemStack(ModItems.nuclear_waste_vitrified, 4)));
		
		/// OSMIRIDIUM ///
		this.register(new GenericRecipe("chem.osmiridiumdeath").setup(240, 1_000)
				.inputItems(new ComparableStack(ModItems.powder_paleogenite), new OreDictStack(F.dust(), 8), new ComparableStack(ModItems.nugget_bismuth, 4))
				.inputFluids(new FluidStack(Fluids.PEROXIDE, 1_000, 5))
				.outputFluids(new FluidStack(Fluids.DEATH, 1_000, 0)));
		
	}
}
