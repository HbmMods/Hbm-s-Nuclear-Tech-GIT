package com.hbm.inventory.recipes;

import static com.hbm.inventory.OreDictManager.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.BlockICFLaserComponent.EnumICFPart;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.inventory.recipes.loader.GenericRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.ItemEnums.EnumExpensiveType;
import com.hbm.items.ItemGenericPart.EnumPartType;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.items.machine.ItemBatteryPack.EnumBatteryPack;
import com.hbm.items.machine.ItemCircuit.EnumCircuitType;

import net.minecraft.item.ItemStack;

public class PlasmaForgeRecipes extends GenericRecipes<PlasmaForgeRecipe> {

	public static final PlasmaForgeRecipes INSTANCE = new PlasmaForgeRecipes();

	@Override public int inputItemLimit() { return 12; }
	@Override public int inputFluidLimit() { return 1; }
	@Override public int outputItemLimit() { return 1; }
	@Override public int outputFluidLimit() { return 0; }

	@Override public String getFileName() { return "hbmPlasmaForge.json"; }
	@Override public PlasmaForgeRecipe instantiateRecipe(String name) { return new PlasmaForgeRecipe(name); }

	@Override
	public void registerDefaults() {
		
		String autoPlate = "autoswitch.weldPlates";
		
		// Plates
		this.register((PlasmaForgeRecipe) new PlasmaForgeRecipe("plsm.plateeuphemium").setInputEnergy(1_000_000).setup(600, 10_000_000).outputItems(new ItemStack(ModItems.plate_euphemium, 4))
				.inputItems(new OreDictStack(EUPH.ingot(), 4), new OreDictStack(AT.dust(), 3), new OreDictStack(BI.dust(), 1), new OreDictStack(VOLCANIC.gem(), 1), new OreDictStack(OSMIRIDIUM.ingot())));
		this.register((PlasmaForgeRecipe) new PlasmaForgeRecipe("plsm.platednt").setInputEnergy(1_000_000).setup(600, 10_000_000).outputItems(new ItemStack(ModItems.plate_dineutronium, 4))
				.inputItems(new OreDictStack(DNT.ingot(), 4), new ComparableStack(ModItems.powder_spark_mix, 2), new OreDictStack(DESH.ingot(), 1)));

		// Components
		this.register((PlasmaForgeRecipe) new PlasmaForgeRecipe("plsm.hde").setInputEnergy(10_000_000).setup(600, 25_000_000L)
				.outputItems(DictFrame.fromOne(ModItems.part_generic, EnumPartType.HDE))
				.inputItems(new OreDictStack(ANY_BISMOIDBRONZE.plateCast(), 2), new OreDictStack(CMB.plateWelded(), 1), new ComparableStack(ModItems.ingot_cft))
				.inputFluids(new FluidStack(Fluids.STELLAR_FLUX, 4_000)));
		
		// Welded Plates
		this.register((PlasmaForgeRecipe) new PlasmaForgeRecipe("plsm.weldiron").setInputEnergy(500_000).setup(50, 100L)
				.outputItems(new ItemStack(ModItems.plate_welded, 1, Mats.MAT_IRON.id))
				.inputItems(new OreDictStack(IRON.plateCast(), 2)).setGroup(autoPlate, this));
		this.register((PlasmaForgeRecipe) new PlasmaForgeRecipe("plsm.weldsteel").setInputEnergy(500_000).setup(50, 500L)
				.outputItems(new ItemStack(ModItems.plate_welded, 1, Mats.MAT_STEEL.id))
				.inputItems(new OreDictStack(STEEL.plateCast(), 2)).setGroup(autoPlate, this));
		this.register((PlasmaForgeRecipe) new PlasmaForgeRecipe("plsm.weldcopper").setInputEnergy(500_000).setup(50, 1_000L)
				.outputItems(new ItemStack(ModItems.plate_welded, 1, Mats.MAT_COPPER.id))
				.inputItems(new OreDictStack(CU.plateCast(), 2)).setGroup(autoPlate, this));
		this.register((PlasmaForgeRecipe) new PlasmaForgeRecipe("plsm.weldtitanium").setInputEnergy(500_000).setup(300, 50_000L)
				.outputItems(new ItemStack(ModItems.plate_welded, 1, Mats.MAT_TITANIUM.id))
				.inputItems(new OreDictStack(TI.plateCast(), 2)).setGroup(autoPlate, this));
		this.register((PlasmaForgeRecipe) new PlasmaForgeRecipe("plsm.weldzirconium").setInputEnergy(500_000).setup(300, 10_000L)
				.outputItems(new ItemStack(ModItems.plate_welded, 1, Mats.MAT_ZIRCONIUM.id))
				.inputItems(new OreDictStack(ZR.plateCast(), 2)).setGroup(autoPlate, this));
		this.register((PlasmaForgeRecipe) new PlasmaForgeRecipe("plsm.weldaluminium").setInputEnergy(500_000).setup(150, 10_000L)
				.outputItems(new ItemStack(ModItems.plate_welded, 1, Mats.MAT_ALUMINIUM.id))
				.inputItems(new OreDictStack(AL.plateCast(), 2)).setGroup(autoPlate, this));
		this.register((PlasmaForgeRecipe) new PlasmaForgeRecipe("plsm.weldtcalloy").setInputEnergy(500_000).setup(600, 1_000_000L)
				.outputItems(new ItemStack(ModItems.plate_welded, 1, Mats.MAT_TCALLOY.id))
				.inputItems(new OreDictStack(TCALLOY.plateCast(), 2))
				.inputFluids(new FluidStack(Fluids.OXYGEN, 1_000)).setGroup(autoPlate, this));
		this.register((PlasmaForgeRecipe) new PlasmaForgeRecipe("plsm.weldcdalloy").setInputEnergy(500_000).setup(600, 1_000_000L)
				.outputItems(new ItemStack(ModItems.plate_welded, 1, Mats.MAT_CDALLOY.id))
				.inputItems(new OreDictStack(CDALLOY.plateCast(), 2))
				.inputFluids(new FluidStack(Fluids.OXYGEN, 1_000)).setGroup(autoPlate, this));
		this.register((PlasmaForgeRecipe) new PlasmaForgeRecipe("plsm.weldtungsten").setInputEnergy(500_000).setup(600, 250_000L)
				.outputItems(new ItemStack(ModItems.plate_welded, 1, Mats.MAT_TUNGSTEN.id))
				.inputItems(new OreDictStack(W.plateCast(), 2))
				.inputFluids(new FluidStack(Fluids.OXYGEN, 1_000)).setGroup(autoPlate, this));
		this.register((PlasmaForgeRecipe) new PlasmaForgeRecipe("plsm.weldcmb").setInputEnergy(500_000).setup(600, 10_000_000L)
				.outputItems(new ItemStack(ModItems.plate_welded, 1, Mats.MAT_CMB.id))
				.inputItems(new OreDictStack(CMB.plateCast(), 2))
				.inputFluids(new FluidStack(Fluids.REFORMGAS, 1_000)).setGroup(autoPlate, this));
		this.register((PlasmaForgeRecipe) new PlasmaForgeRecipe("plsm.weldosmiridium").setInputEnergy(500_000).setup(3_000, 50_000_000L)
				.outputItems(new ItemStack(ModItems.plate_welded, 1, Mats.MAT_OSMIRIDIUM.id))
				.inputItems(new OreDictStack(OSMIRIDIUM.plateCast(), 2))
				.inputFluids(new FluidStack(Fluids.REFORMGAS, 16_000)).setGroup(autoPlate, this));
		
		// Fusion
		this.register((PlasmaForgeRecipe) new PlasmaForgeRecipe("plsm.fusionvessel").setInputEnergy(3_000_000).setup(1_200, 2_000_000).outputItems(new ItemStack(ModBlocks.fusion_torus))
				.inputItems(new ComparableStack(ModBlocks.fusion_component, 64, 0),
						new ComparableStack(ModBlocks.fusion_component, 64, 0),
						new ComparableStack(ModBlocks.fusion_component, 64, 0),
						new ComparableStack(ModBlocks.fusion_component, 64, 0),
						new ComparableStack(ModBlocks.fusion_component, 64, 0),
						new ComparableStack(ModBlocks.fusion_component, 64, 0),
						new ComparableStack(ModBlocks.fusion_component, 64, 3),
						new ComparableStack(ModBlocks.fusion_component, 64, 3),
						new ComparableStack(ModBlocks.fusion_component, 64, 3),
						new ComparableStack(ModBlocks.fusion_component, 64, 2),
						new ComparableStack(ModBlocks.fusion_component, 64, 2),
						new ComparableStack(ModItems.circuit, 4, EnumCircuitType.QUANTUM))
				.setPools528(PlasmaForgeRecipes.POOL_PREFIX_528 + "chlorophyte"));
		
		// ICF
		this.register((PlasmaForgeRecipe) new PlasmaForgeRecipe("plsm.icfcell").setInputEnergy(1_000_000).setup(800, 10_000_000).outputItems(new ItemStack(ModBlocks.icf_laser_component, 1, EnumICFPart.CELL.ordinal()))
				.inputItems(new ComparableStack(ModItems.ingot_cft, 2), new OreDictStack(ANY_BISMOIDBRONZE.plateCast(), 4), new ComparableStack(ModBlocks.glass_quartz, 16))
				.inputItemsEx(new ComparableStack(ModItems.item_expensive, 2, EnumExpensiveType.BRONZE_TUBES), new ComparableStack(ModItems.ingot_cft, 8), new ComparableStack(ModBlocks.glass_quartz, 16))
				.setPools528(PlasmaForgeRecipes.POOL_PREFIX_528 + "chlorophyte"));
		this.register((PlasmaForgeRecipe) new PlasmaForgeRecipe("plsm.icfemitter").setInputEnergy(1_000_000).setup(800, 10_000_000).outputItems(new ItemStack(ModBlocks.icf_laser_component, 1, EnumICFPart.EMITTER.ordinal()))
				.inputItems(new OreDictStack(W.plateWelded(), 4), new OreDictStack(MAGTUNG.wireDense(), 16))
				.inputItemsEx(new OreDictStack(W.plateWelded(), 8), new OreDictStack(MAGTUNG.wireDense(), 16))
				.inputFluids(new FluidStack(Fluids.XENON, 16_000))
				.setPools528(PlasmaForgeRecipes.POOL_PREFIX_528 + "chlorophyte"));
		this.register((PlasmaForgeRecipe) new PlasmaForgeRecipe("plsm.icfcapacitor").setInputEnergy(1_000_000).setup(800, 10_000_000).outputItems(new ItemStack(ModBlocks.icf_laser_component, 1, EnumICFPart.CAPACITOR.ordinal()))
				.inputItems(new OreDictStack(ANY_RESISTANTALLOY.plateWelded(), 1), new OreDictStack(ND.wireDense(), 16), new OreDictStack(SBD.wireDense(), 2))
				.inputItemsEx(new ComparableStack(ModItems.item_expensive, 3, EnumExpensiveType.FERRO_PLATING), new OreDictStack(ND.wireDense(), 16), new OreDictStack(SBD.wireDense(), 2))
				.setPools528(PlasmaForgeRecipes.POOL_PREFIX_528 + "chlorophyte"));
		this.register((PlasmaForgeRecipe) new PlasmaForgeRecipe("plsm.icfturbo").setInputEnergy(1_000_000).setup(800, 10_000_000).outputItems(new ItemStack(ModBlocks.icf_laser_component, 1, EnumICFPart.TURBO.ordinal()))
				.inputItems(new OreDictStack(ANY_RESISTANTALLOY.plateWelded(), 2), new OreDictStack(DNT.wireDense(), 4), new OreDictStack(SBD.wireDense(), 4))
				.inputItemsEx(new OreDictStack(ANY_RESISTANTALLOY.plateWelded(), 8), new OreDictStack(DNT.wireDense(), 8), new OreDictStack(SBD.wireDense(), 4))
				.setPools528(PlasmaForgeRecipes.POOL_PREFIX_528 + "chlorophyte"));
		this.register((PlasmaForgeRecipe) new PlasmaForgeRecipe("plsm.icfcasing").setInputEnergy(1_000_000).setup(800, 10_000_000).outputItems(new ItemStack(ModBlocks.icf_laser_component, 1, EnumICFPart.CASING.ordinal()))
				.inputItems(new OreDictStack(ANY_BISMOIDBRONZE.plateCast(), 4), new OreDictStack(BIGMT.plateCast(), 4), new OreDictStack(ANY_HARDPLASTIC.ingot(), 16))
				.inputItemsEx(new ComparableStack(ModItems.item_expensive, 4, EnumExpensiveType.BRONZE_TUBES), new OreDictStack(BIGMT.plateCast(), 4), new OreDictStack(ANY_HARDPLASTIC.ingot(), 16))
				.setPools528(PlasmaForgeRecipes.POOL_PREFIX_528 + "chlorophyte"));
		this.register((PlasmaForgeRecipe) new PlasmaForgeRecipe("plsm.icfport").setInputEnergy(1_000_000).setup(800, 10_000_000).outputItems(new ItemStack(ModBlocks.icf_laser_component, 1, EnumICFPart.PORT.ordinal()))
				.inputItems(new OreDictStack(ANY_BISMOIDBRONZE.plateCast(), 4), new OreDictStack(ANY_HARDPLASTIC.ingot(), 16), new OreDictStack(ND.wireDense(), 16))
				.inputItemsEx(new ComparableStack(ModItems.item_expensive, 4, EnumExpensiveType.BRONZE_TUBES), new OreDictStack(ANY_HARDPLASTIC.ingot(), 16), new OreDictStack(ND.wireDense(), 16))
				.setPools528(PlasmaForgeRecipes.POOL_PREFIX_528 + "chlorophyte"));
		this.register((PlasmaForgeRecipe) new PlasmaForgeRecipe("plsm.icfcontroller").setInputEnergy(1_000_000).setup(800, 10_000_000).outputItems(new ItemStack(ModBlocks.icf_controller, 1))
				.inputItems(new ComparableStack(ModItems.ingot_cft, 16), new OreDictStack(ANY_BISMOIDBRONZE.plateCast(), 4), new OreDictStack(ANY_HARDPLASTIC.ingot(), 16), new ComparableStack(ModItems.circuit, 16, EnumCircuitType.BISMOID))
				.inputItemsEx(new ComparableStack(ModItems.item_expensive, 4, EnumExpensiveType.BRONZE_TUBES), new ComparableStack(ModItems.ingot_cft, 16), new OreDictStack(ANY_HARDPLASTIC.ingot(), 16), new ComparableStack(ModItems.circuit, 32, EnumCircuitType.BISMOID), new ComparableStack(ModItems.item_expensive, 4, EnumExpensiveType.COMPUTER))
				.setPools528(PlasmaForgeRecipes.POOL_PREFIX_528 + "chlorophyte"));
		this.register((PlasmaForgeRecipe) new PlasmaForgeRecipe("plsm.icfscaffold").setInputEnergy(1_000_000).setup(800, 10_000_000).outputItems(new ItemStack(ModBlocks.icf_component, 1, 0))
				.inputItems(new OreDictStack(STEEL.plateWelded(), 4), new OreDictStack(TI.plateWelded(), 2))
				.inputItemsEx(new ComparableStack(ModItems.item_expensive, 8, EnumExpensiveType.STEEL_PLATING))
				.setPools528(PlasmaForgeRecipes.POOL_PREFIX_528 + "chlorophyte"));
		this.register((PlasmaForgeRecipe) new PlasmaForgeRecipe("plsm.icfvessel").setInputEnergy(1_000_000).setup(800, 10_000_000).outputItems(new ItemStack(ModBlocks.icf_component, 1, 1))
				.inputItems(new ComparableStack(ModItems.ingot_cft, 1), new OreDictStack(CMB.plateCast(), 1), new OreDictStack(W.plateWelded(), 2))
				.setPools528(PlasmaForgeRecipes.POOL_PREFIX_528 + "chlorophyte"));
		this.register((PlasmaForgeRecipe) new PlasmaForgeRecipe("plsm.icfstructural").setInputEnergy(1_000_000).setup(800, 10_000_000).outputItems(new ItemStack(ModBlocks.icf_component, 1, 3))
				.inputItems(new OreDictStack(STEEL.plateWelded(), 2), new OreDictStack(CU.plateWelded(), 2), new OreDictStack(ANY_BISMOIDBRONZE.plateCast(), 1))
				.inputItemsEx(new ComparableStack(ModItems.item_expensive, 1, EnumExpensiveType.BRONZE_TUBES), new OreDictStack(STEEL.plateWelded(), 8))
				.setPools528(PlasmaForgeRecipes.POOL_PREFIX_528 + "chlorophyte"));
		this.register((PlasmaForgeRecipe) new PlasmaForgeRecipe("plsm.icfcore").setInputEnergy(1_000_000).setup(3_000, 10_000_000).outputItems(new ItemStack(ModBlocks.struct_icf_core, 1))
				.inputItems(new OreDictStack(CMB.plateWelded(), 16), new OreDictStack(ANY_RESISTANTALLOY.plateWelded(), 16), new OreDictStack(ANY_BISMOIDBRONZE.plateCast(), 16), new OreDictStack(SBD.wireDense(), 32), new ComparableStack(ModItems.circuit, 32, EnumCircuitType.BISMOID), new ComparableStack(ModItems.circuit, 16, EnumCircuitType.QUANTUM))
				.inputItemsEx(new ComparableStack(ModItems.item_expensive, 16, EnumExpensiveType.BRONZE_TUBES), new OreDictStack(CMB.plateWelded(), 16), new OreDictStack(SBD.wireDense(), 32), new ComparableStack(ModItems.circuit, 32, EnumCircuitType.QUANTUM), new ComparableStack(ModItems.item_expensive, 16, EnumExpensiveType.COMPUTER))
				.setPools528(PlasmaForgeRecipes.POOL_PREFIX_528 + "chlorophyte"));
		this.register((PlasmaForgeRecipe) new PlasmaForgeRecipe("plsm.icfpress").setInputEnergy(1_000_000).setup(800, 10_000_000).outputItems(new ItemStack(ModBlocks.machine_icf_press, 1))
				.inputItems(new OreDictStack(GOLD.plateCast(), 8), new ComparableStack(ModItems.motor, 4), new ComparableStack(ModItems.circuit, 1, EnumCircuitType.BISMOID))
				.setPools528(PlasmaForgeRecipes.POOL_PREFIX_528 + "chlorophyte"));

		// weapons
		this.register((PlasmaForgeRecipe) new PlasmaForgeRecipe("plsm.schrabhammer").setInputEnergy(25_000_000).setup(6_000, 10_000_000).outputItems(new ItemStack(ModItems.schrabidium_hammer, 1))
				.inputItems(
						new OreDictStack(SA326.block(), 35),
						new ComparableStack(ModItems.billet_yharonite, 64),
						new ComparableStack(ModItems.billet_yharonite, 64),
						new ComparableStack(ModItems.coin_ufo, 1),
						new ComparableStack(ModItems.fragment_meteorite, 64),
						new ComparableStack(ModItems.fragment_meteorite, 64),
						new ComparableStack(ModItems.fragment_meteorite, 64),
						new ComparableStack(ModItems.fragment_meteorite, 64),
						new ComparableStack(ModItems.fragment_meteorite, 64),
						new ComparableStack(ModItems.fragment_meteorite, 64),
						new ComparableStack(ModItems.fragment_meteorite, 64),
						new ComparableStack(ModItems.fragment_meteorite, 64)));
		
		this.register((PlasmaForgeRecipe) new PlasmaForgeRecipe("ass.fensusan").setInputEnergy(50_000_000).setup(6_000, 50_000_000).outputItems(new ItemStack(ModBlocks.machine_battery_redd, 1))
				.inputItems(new ComparableStack(ModItems.ingot_electronium, 64),
						new ComparableStack(ModItems.battery_pack, 1, EnumBatteryPack.BATTERY_QUANTUM),
						new OreDictStack(OSMIRIDIUM.plateWelded(), 64),
						new OreDictStack(OSMIRIDIUM.plateWelded(), 64),
						new OreDictStack(ANY_BISMOIDBRONZE.plateCast(), 64),
						new OreDictStack(CMB.plateCast(), 32),
						new OreDictStack(MAGTUNG.wireDense(), 32),
						new ComparableStack(ModItems.plate_dineutronium, 64),
						new ComparableStack(ModItems.powder_magic, 64),
						new ComparableStack(ModItems.ingot_u238m2),
						new ComparableStack(ModItems.ingot_cft, 64),
						new ComparableStack(ModItems.ingot_cft, 64))
				.inputItemsEx(new ComparableStack(ModItems.ingot_electronium, 64),
						new ComparableStack(ModItems.battery_pack, 1, EnumBatteryPack.BATTERY_QUANTUM),
						new ComparableStack(ModItems.item_expensive, 64, EnumExpensiveType.BRONZE_TUBES),
						new ComparableStack(ModItems.item_expensive, 64, EnumExpensiveType.FERRO_PLATING),
						new OreDictStack(OSMIRIDIUM.plateWelded(), 64),
						new OreDictStack(OSMIRIDIUM.plateWelded(), 64),
						new OreDictStack(OSMIRIDIUM.plateWelded(), 64),
						new OreDictStack(ANY_BISMOIDBRONZE.plateCast(), 64),
						new OreDictStack(CMB.plateCast(), 64),
						new ComparableStack(ModItems.ingot_u238m2),
						new ComparableStack(ModItems.ingot_cft, 64),
						new ComparableStack(ModItems.ingot_cft, 64)));

		this.register((PlasmaForgeRecipe) new PlasmaForgeRecipe("plsm.gerald").setInputEnergy(25_000_000).setup(12_000, 50_000_000).outputItems(new ItemStack(ModItems.sat_gerald, 1))
				.inputItems(new OreDictStack(SBD.plateCast(), 64),
						new OreDictStack(SBD.plateCast(), 64),
						new OreDictStack(BSCCO.wireDense(), 64),
						new OreDictStack(BSCCO.wireDense(), 64),
						new ComparableStack(ModBlocks.det_nuke, 64),
						new ComparableStack(ModItems.part_generic, 64, EnumPartType.HDE),
						new ComparableStack(ModItems.part_generic, 64, EnumPartType.HDE),
						new ComparableStack(ModItems.part_generic, 64, EnumPartType.HDE),
						new ComparableStack(ModItems.part_generic, 64, EnumPartType.HDE),
						new ComparableStack(ModItems.circuit, 64, EnumCircuitType.CONTROLLER_QUANTUM),
						new ComparableStack(ModItems.coin_ufo, 1))
				.inputItemsEx(new OreDictStack(SBD.plateCast(), 64),
						new OreDictStack(BSCCO.wireDense(), 64),
						new ComparableStack(ModBlocks.det_nuke, 64),
						new ComparableStack(ModItems.item_expensive, 64, EnumExpensiveType.BRONZE_TUBES),
						new ComparableStack(ModItems.item_expensive, 64, EnumExpensiveType.BRONZE_TUBES),
						new ComparableStack(ModItems.item_expensive, 64, EnumExpensiveType.BRONZE_TUBES),
						new ComparableStack(ModItems.part_generic, 64, EnumPartType.HDE),
						new ComparableStack(ModItems.part_generic, 64, EnumPartType.HDE),
						new ComparableStack(ModItems.part_generic, 64, EnumPartType.HDE),
						new ComparableStack(ModItems.circuit, 64, EnumCircuitType.CONTROLLER_QUANTUM),
						new ComparableStack(ModItems.item_expensive, 64, EnumExpensiveType.COMPUTER),
						new ComparableStack(ModItems.coin_ufo, 1)).setPools(PlasmaForgeRecipes.POOL_PREFIX_DISCOVER + "gerald"));

		// really, really boring fucking temp recipes
		this.register((PlasmaForgeRecipe) new PlasmaForgeRecipe("plsm.dfccore").setInputEnergy(50_000_000).setup(12_000, 100_000_000).outputItems(new ItemStack(ModBlocks.dfc_core)).inputFluids(new FluidStack(Fluids.STELLAR_FLUX, 12_000))
				.inputItems(new OreDictStack(OSMIRIDIUM.plateWelded(), 16), new OreDictStack(DNT.wireDense(), 16), new ComparableStack(ModItems.circuit, 12, EnumCircuitType.CONTROLLER_QUANTUM), new ComparableStack(ModItems.singularity_spark), new ComparableStack(ModItems.powder_chlorophyte, 64)));
		this.register((PlasmaForgeRecipe) new PlasmaForgeRecipe("plsm.dfcemitter").setInputEnergy(50_000_000).setup(1_200, 10_000_000).outputItems(new ItemStack(ModBlocks.dfc_emitter)).inputFluids(new FluidStack(Fluids.STELLAR_FLUX, 4_000))
				.inputItems(new OreDictStack(OSMIRIDIUM.plateWelded(), 16), new OreDictStack(STAR.wireDense(), 16), new ComparableStack(ModItems.circuit, 8, EnumCircuitType.CONTROLLER_QUANTUM)));
		this.register((PlasmaForgeRecipe) new PlasmaForgeRecipe("plsm.dfcreceiver").setInputEnergy(50_000_000).setup(1_200, 10_000_000).outputItems(new ItemStack(ModBlocks.dfc_receiver)).inputFluids(new FluidStack(Fluids.STELLAR_FLUX, 4_000))
				.inputItems(new OreDictStack(OSMIRIDIUM.plateWelded(), 16), new OreDictStack(STAR.plateCast(), 16), new ComparableStack(ModItems.circuit, 8, EnumCircuitType.CONTROLLER_QUANTUM)));
		this.register((PlasmaForgeRecipe) new PlasmaForgeRecipe("plsm.dfcinjector").setInputEnergy(50_000_000).setup(1_200, 10_000_000).outputItems(new ItemStack(ModBlocks.dfc_injector)).inputFluids(new FluidStack(Fluids.STELLAR_FLUX, 4_000))
				.inputItems(new OreDictStack(OSMIRIDIUM.plateWelded(), 16), new OreDictStack(BIGMT.plateCast(), 16), new ComparableStack(ModItems.circuit, 4, EnumCircuitType.CONTROLLER_ADVANCED)));
		this.register((PlasmaForgeRecipe) new PlasmaForgeRecipe("plsm.dfcstabilizer").setInputEnergy(50_000_000).setup(1_200, 10_000_000).outputItems(new ItemStack(ModBlocks.dfc_stabilizer)).inputFluids(new FluidStack(Fluids.STELLAR_FLUX, 4_000))
				.inputItems(new OreDictStack(OSMIRIDIUM.plateWelded(), 16), new OreDictStack(SBD.wireDense(), 16), new ComparableStack(ModItems.circuit, 8, EnumCircuitType.CONTROLLER_QUANTUM)));
	}

	@Override
	public void readExtraData(JsonElement element, PlasmaForgeRecipe recipe) {
		JsonObject obj = (JsonObject) element;
		recipe.ignitionTemp = obj.get("ignitionTemp").getAsLong();
	}

	@Override
	public void writeExtraData(PlasmaForgeRecipe recipe, JsonWriter writer) throws IOException {
		writer.name("ignitionTemp").value(recipe.ignitionTemp);
	}

	public static HashMap getRecipes() {
		HashMap<Object, Object> recipes = new HashMap<Object, Object>();

		for(GenericRecipe recipe : INSTANCE.recipeOrderedList) {
			List input = new ArrayList();
			if(recipe.inputItem != null) for(AStack stack : recipe.inputItem) input.add(stack);
			if(recipe.inputFluid != null) for(FluidStack stack : recipe.inputFluid) input.add(ItemFluidIcon.make(stack));
			List output = new ArrayList();
			if(recipe.outputItem != null) for(IOutput stack : recipe.outputItem) output.add(stack.getAllPossibilities());
			recipes.put(input.toArray(), output.toArray());
		}

		return recipes;
	}
}
