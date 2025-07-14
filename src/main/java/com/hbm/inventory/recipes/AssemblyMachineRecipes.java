package com.hbm.inventory.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.hbm.inventory.OreDictManager.*;
import static com.hbm.inventory.material.Mats.*;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.inventory.recipes.loader.GenericRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.ItemGenericPart.EnumPartType;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.items.machine.ItemCircuit.EnumCircuitType;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmo;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class AssemblyMachineRecipes extends GenericRecipes<GenericRecipe> {
	
	public static final AssemblyMachineRecipes INSTANCE = new AssemblyMachineRecipes();

	@Override public int inputItemLimit() { return 12; }
	@Override public int inputFluidLimit() { return 1; }
	@Override public int outputItemLimit() { return 1; }
	@Override public int outputFluidLimit() { return 1; }

	@Override public String getFileName() { return "hbmAsemblyMachine.json"; }
	@Override public GenericRecipe instantiateRecipe(String name) { return new GenericRecipe(name); }

	@Override
	public void registerDefaults() {
		
		// plates and ingots
		this.register(new GenericRecipe("ass.platemixed").setup(50, 100).outputItems(new ItemStack(ModItems.plate_mixed, 4))
				.inputItems(new OreDictStack(ALLOY.plate(), 2), new OreDictStack(OreDictManager.getReflector(), 1), new OreDictStack(BIGMT.plate(), 1)));
		this.register(new GenericRecipe("ass.dalekanium").setup(200, 100).outputItems(new ItemStack(ModItems.plate_dalekanium, 1))
				.inputItems(new ComparableStack(ModBlocks.block_meteor, 1)));

		// cloth
		this.register(new GenericRecipe("ass.hazcloth").setup(50, 100).outputItems(new ItemStack(ModItems.hazmat_cloth, 4))
				.inputItems(new OreDictStack(PB.dust(), 4), new ComparableStack(Items.string, 8)));
		this.register(new GenericRecipe("ass.firecloth").setup(50, 100).outputItems(new ItemStack(ModItems.asbestos_cloth, 4))
				.inputItems(new OreDictStack(ASBESTOS.ingot(), 1), new ComparableStack(Items.string, 8)));
		this.register(new GenericRecipe("ass.filtercoal").setup(50, 100).outputItems(new ItemStack(ModItems.filter_coal, 1))
				.inputItems(new OreDictStack(COAL.dust(), 4), new ComparableStack(Items.string, 2), new ComparableStack(Items.paper, 1)));

		// machine parts
		this.register(new GenericRecipe("ass.centrifugetower").setup(100, 100).outputItems(new ItemStack(ModItems.centrifuge_element, 1))
				.inputItems(new OreDictStack(DURA.plate528(), 4), new OreDictStack(TI.plate528(), 4), new ComparableStack(ModItems.motor, 1)));
		this.register(new GenericRecipe("ass.reactorcore").setup(100, 100).outputItems(new ItemStack(ModItems.reactor_core, 1))
				.inputItems(new OreDictStack(PB.plateCast(), 4), new OreDictStack(BE.ingot(), 8), new OreDictStack(OreDictManager.getReflector(), 8), new OreDictStack(ASBESTOS.ingot(), 4)));
		this.register(new GenericRecipe("ass.thermoelement").setup(60, 100).outputItems(new ItemStack(ModItems.thermo_element, 1))
				.inputItems(new OreDictStack(STEEL.plate(), 1), new OreDictStack(MINGRADE.wireFine(), 2), new OreDictStack(NETHERQUARTZ.dust(), 2)));
		this.register(new GenericRecipe("ass.thermoelementsilicon").setup(60, 100).outputItems(new ItemStack(ModItems.thermo_element, 1))
				.inputItems(new OreDictStack(STEEL.plate(), 1), new OreDictStack(GOLD.wireFine(), 2), new OreDictStack(SI.billet(), 1)));
		this.register(new GenericRecipe("ass.rtgunit").setup(100, 100).outputItems(new ItemStack(ModItems.rtg_unit, 1))
				.inputItems(new OreDictStack(PB.plateCast(), 2), new OreDictStack(CU.plate(), 4), new ComparableStack(ModItems.thermo_element, 2)));
		this.register(new GenericRecipe("ass.magnetron").setup(40, 100).outputItems(new ItemStack(ModItems.magnetron, 1))
				.inputItems(new OreDictStack(CU.plate(), 3), new OreDictStack(W.wireFine(), 4)));
		this.register(new GenericRecipe("ass.titaniumdrill").setup(100, 100).outputItems(new ItemStack(ModItems.drill_titanium, 1))
				.inputItems(new OreDictStack(DURA.plateCast(), 1), new OreDictStack(TI.plate(), 8)));
		this.register(new GenericRecipe("ass.entanglementkit").setup(200, 100).outputItems(new ItemStack(ModItems.entanglement_kit, 1))
				.inputItems(new OreDictStack(DURA.plateCast(), 4), new OreDictStack(CU.plate(), 24), new OreDictStack(GOLD.wireDense(), 16))
				.inputFluids(new FluidStack(Fluids.XENON, 8_000)));
		this.register(new GenericRecipe("ass.protoreactor").setup(200, 100).outputItems(new ItemStack(ModItems.dysfunctional_reactor, 1))
				.inputItems(new OreDictStack(STEEL.shell(), 4), new OreDictStack(PB.plateCast(), 4), new ComparableStack(ModItems.rod_quad_empty, 10), new OreDictStack(KEY_BROWN, 3)));

		// powders
		this.register(new GenericRecipe("ass.partlith").setup(40, 100).outputItems(new ItemStack(ModItems.part_lithium, 8))
				.inputItems(new OreDictStack(LI.dust(), 1)));
		this.register(new GenericRecipe("ass.partberyl").setup(40, 100).outputItems(new ItemStack(ModItems.part_beryllium, 8))
				.inputItems(new OreDictStack(BE.dust(), 1)));
		this.register(new GenericRecipe("ass.partcoal").setup(40, 100).outputItems(new ItemStack(ModItems.part_carbon, 8))
				.inputItems(new OreDictStack(COAL.dust(), 1)));
		this.register(new GenericRecipe("ass.partcop").setup(40, 100).outputItems(new ItemStack(ModItems.part_copper, 8))
				.inputItems(new OreDictStack(CU.dust(), 1)));
		this.register(new GenericRecipe("ass.partplut").setup(40, 100).outputItems(new ItemStack(ModItems.part_plutonium, 8))
				.inputItems(new OreDictStack(PU.dust(), 1)));
		
		// bunker blocks
		this.register(new GenericRecipe("ass.cmbtile").setup(100, 100).outputItems(new ItemStack(ModBlocks.cmb_brick, 8))
				.inputItems(new OreDictStack(ANY_CONCRETE.any(), 4), new OreDictStack(CMB.plate(), 4)));
		this.register(new GenericRecipe("ass.cmbbrick").setup(100, 100).outputItems(new ItemStack(ModBlocks.cmb_brick_reinforced, 8))
				.inputItems(new OreDictStack(MAGTUNG.ingot(), 8), new ComparableStack(ModBlocks.ducrete, 4), new ComparableStack(ModBlocks.cmb_brick, 8)));
		this.register(new GenericRecipe("ass.sealframe").setup(100, 100).outputItems(new ItemStack(ModBlocks.seal_frame, 1))
				.inputItems(new OreDictStack(DURA.ingot(), 1), new OreDictStack(STEEL.plateCast(), 1), new OreDictStack(MINGRADE.wireDense(), 1)));
		this.register(new GenericRecipe("ass.sealcontroller").setup(100, 100).outputItems(new ItemStack(ModBlocks.seal_controller, 1))
				.inputItems(new OreDictStack(DURA.ingot(), 1), new OreDictStack(STEEL.plateCast(), 1), new OreDictStack(ANY_PLASTIC.ingot(), 4), new OreDictStack(MINGRADE.wireDense(), 4)));
		
		// machines
		this.register(new GenericRecipe("ass.shredder").setup(100, 100).outputItems(new ItemStack(ModBlocks.machine_shredder, 1))
				.inputItems(new OreDictStack(STEEL.plate528(), 8), new OreDictStack(CU.plate(), 4), new ComparableStack(ModItems.motor, 2)));
		this.register(new GenericRecipe("ass.chemplant").setup(200, 100).outputItems(new ItemStack(ModBlocks.machine_chemical_plant, 1))
				.inputItems(new OreDictStack(STEEL.ingot(), 8), new OreDictStack(CU.pipe(), 2), new ComparableStack(ModItems.plate_polymer, 16), new ComparableStack(ModItems.motor, 2), new ComparableStack(ModItems.coil_tungsten, 2), new ComparableStack(ModItems.circuit, 1, EnumCircuitType.ANALOG)));
		this.register(new GenericRecipe("ass.centrifuge").setup(200, 100).outputItems(new ItemStack(ModBlocks.machine_centrifuge, 1))
				.inputItems(new ComparableStack(ModItems.centrifuge_element, 1), new OreDictStack(ANY_PLASTIC.ingot(), 4), new OreDictStack(STEEL.plate528(), 8), new OreDictStack(CU.plate(), 4), new ComparableStack(ModItems.circuit, 1, EnumCircuitType.ANALOG)));
		this.register(new GenericRecipe("ass.gascent").setup(400, 100).outputItems(new ItemStack(ModBlocks.machine_gascent, 1))
				.inputItems(new ComparableStack(ModItems.centrifuge_element, 4), new OreDictStack(ANY_PLASTIC.ingot(), 8), new OreDictStack(DESH.ingot(), 2), new OreDictStack(STEEL.plate528(), 8), new ComparableStack(ModItems.circuit, 1, EnumCircuitType.ADVANCED.ordinal())));
		this.register(new GenericRecipe("ass.acidizer").setup(200, 100).outputItems(new ItemStack(ModBlocks.machine_crystallizer, 1))
				.inputItems(new OreDictStack(STEEL.plateWelded(), 2), new OreDictStack(TI.shell(), 3), new OreDictStack(DESH.ingot(), 4), new ComparableStack(ModItems.motor, 1), new ComparableStack(ModItems.circuit, 2, EnumCircuitType.BASIC)));
		this.register(new GenericRecipe("ass.rtg").setup(200, 100).outputItems(new ItemStack(ModBlocks.machine_rtg_grey, 1))
				.inputItems(new ComparableStack(ModItems.rtg_unit, 3), new OreDictStack(STEEL.plate528(), 4), new OreDictStack(MINGRADE.wireFine(), 16), new OreDictStack(ANY_PLASTIC.ingot(), 4)));
		this.register(new GenericRecipe("ass.derrick").setup(200, 100).outputItems(new ItemStack(ModBlocks.machine_well, 1))
				.inputItems(new OreDictStack(STEEL.plate(), 8), new OreDictStack(CU.plateCast(), 2), new OreDictStack(STEEL.pipe(), 4), new ComparableStack(ModItems.motor, 1), new ComparableStack(ModItems.drill_titanium, 1)));
		this.register(new GenericRecipe("ass.pumpjack").setup(400, 100).outputItems(new ItemStack(ModBlocks.machine_pumpjack, 1))
				.inputItems(new OreDictStack(DURA.plate(), 8), new OreDictStack(STEEL.plateWelded(), 8), new OreDictStack(STEEL.pipe(), 12), new ComparableStack(ModItems.motor_desh), new ComparableStack(ModItems.drill_titanium, 1)));
		this.register(new GenericRecipe("ass.flarestack").setup(100, 100).outputItems(new ItemStack(ModBlocks.machine_flare, 1))
				.inputItems(new OreDictStack(STEEL.plate(), 12), new OreDictStack(CU.plate528(), 4), new OreDictStack(STEEL.shell(), 4), new ComparableStack(ModItems.thermo_element, 3)));
		this.register(new GenericRecipe("ass.refinery").setup(200, 100).outputItems(new ItemStack(ModBlocks.machine_refinery, 1))
				.inputItems(new OreDictStack(STEEL.plateWelded(), 3), new OreDictStack(CU.plate528(), 8), new OreDictStack(STEEL.shell(), 4), new OreDictStack(STEEL.pipe(), 12), new ComparableStack(ModItems.plate_polymer, 8), new ComparableStack(ModItems.circuit, 3, EnumCircuitType.ANALOG)));
		this.register(new GenericRecipe("ass.coker").setup(200, 100).outputItems(new ItemStack(ModBlocks.machine_coker, 1))
				.inputItems(new OreDictStack(STEEL.plateWelded(), 8), new OreDictStack(STEEL.shell(), 4), new OreDictStack(CU.plate528(), 8), new OreDictStack(RUBBER.ingot(), 4), new OreDictStack(NB.ingot(), 4)));
		this.register(new GenericRecipe("ass.epress").setup(100, 100).outputItems(new ItemStack(ModBlocks.machine_epress, 1))
				.inputItems(new OreDictStack(STEEL.plate(), 8), new OreDictStack(ANY_RUBBER.ingot(), 4), new ComparableStack(ModItems.part_generic, 2, EnumPartType.PISTON_HYDRAULIC.ordinal()), new ComparableStack(ModItems.circuit, 1, EnumCircuitType.BASIC)));
		this.register(new GenericRecipe("ass.mininglaser").setup(400, 100).outputItems(new ItemStack(ModBlocks.machine_mining_laser, 1))
				.inputItems(new ComparableStack(ModItems.tank_steel, 3), new OreDictStack(STEEL.plate528(), 16), new ComparableStack(ModItems.crystal_redstone, 3), new ComparableStack(Items.diamond, 3), new OreDictStack(ANY_PLASTIC.ingot(), 8), new ComparableStack(ModItems.motor, 3), new OreDictStack(DURA.plate(), 4)));
		this.register(new GenericRecipe("ass.teleporter").setup(100, 100).outputItems(new ItemStack(ModBlocks.machine_teleporter, 1))
				.inputItems(new OreDictStack(TI.plate(), 12), new OreDictStack(ALLOY.plate528(), 12), new OreDictStack(GOLD.wireFine(), 32), new ComparableStack(ModItems.entanglement_kit, 1), new ComparableStack(ModBlocks.machine_battery, 1)));
		
		// generators
		this.register(new GenericRecipe("ass.dieselgen").setup(200, 100).outputItems(new ItemStack(ModBlocks.machine_diesel, 1))
				.inputItems(new OreDictStack(STEEL.shell(), 1), new OreDictStack(CU.plateCast(), 2), new ComparableStack(ModItems.coil_copper, 4)));
		this.register(new GenericRecipe("ass.turbofan").setup(300, 100).outputItems(new ItemStack(ModBlocks.machine_turbofan, 1))
				.inputItems(new OreDictStack(TI.shell(), 8), new OreDictStack(DURA.pipe(), 4), new OreDictStack(ANY_PLASTIC.ingot(), 12), new ComparableStack(ModItems.turbine_tungsten, 1), new OreDictStack(GOLD.wireDense(), 12), new ComparableStack(ModItems.circuit, 3, EnumCircuitType.BASIC.ordinal())));
		this.register(new GenericRecipe("ass.gasturbine").setup(400, 100).outputItems(new ItemStack(ModBlocks.machine_turbinegas, 1))
				.inputItems(new OreDictStack(STEEL.shell(), 10), new OreDictStack(GOLD.wireDense(), 12), new OreDictStack(DURA.pipe(), 4),new OreDictStack(STEEL.pipe(), 4), new ComparableStack(ModItems.turbine_tungsten, 1), new ComparableStack(ModItems.ingot_rubber, 12), new ComparableStack(ModItems.circuit, 3, EnumCircuitType.BASIC.ordinal())));

		// batteries
		this.register(new GenericRecipe("ass.battery").setup(100, 100).outputItems(new ItemStack(ModBlocks.machine_battery, 1))
				.inputItems(new OreDictStack(STEEL.plateWelded(), 1), new OreDictStack(S.dust(), 12), new OreDictStack(PB.dust(), 12)));
		this.register(new GenericRecipe("ass.batterylithium").setup(100, 100).outputItems(new ItemStack(ModBlocks.machine_battery, 1))
				.inputItems(new OreDictStack(ANY_PLASTIC.ingot(), 8), new OreDictStack(CO.dust(), 12), new OreDictStack(LI.dust(), 12)));
		this.register(new GenericRecipe("ass.batteryschrabidium").setup(200, 100).outputItems(new ItemStack(ModBlocks.machine_battery, 1))
				.inputItems(new OreDictStack(DESH.ingot(), 16), new OreDictStack(NP237.dust(), 12), new OreDictStack(SA326.dust(), 12)));
		this.register(new GenericRecipe("ass.batterydnt").setup(400, 100).outputItems(new ItemStack(ModBlocks.machine_battery, 1))
				.inputItems(new OreDictStack(DNT.ingot(), 24), new ComparableStack(ModItems.powder_spark_mix, 12), new ComparableStack(ModItems.battery_spark_cell_1000, 1), new OreDictStack(CMB.ingot(), 32)));

		// fluid tanks
		this.register(new GenericRecipe("ass.tank").setup(200, 100).outputItems(new ItemStack(ModBlocks.machine_fluidtank, 1))
				.inputItems(new OreDictStack(STEEL.plate528(), 8), new OreDictStack(STEEL.shell(), 4), new OreDictStack(ANY_TAR.any(), 4)));
		this.register(new GenericRecipe("ass.bat9k").setup(200, 100).outputItems(new ItemStack(ModBlocks.machine_bat9000, 1))
				.inputItems(new OreDictStack(STEEL.plate528(), 16), new OreDictStack(ANY_RESISTANTALLOY.plateWelded(), 2), new ComparableStack(ModBlocks.steel_scaffold, 16), new OreDictStack(ANY_TAR.any(), 16)));
		this.register(new GenericRecipe("ass.orbus").setup(300, 100).outputItems(new ItemStack(ModBlocks.machine_orbus, 1))
				.inputItems(new OreDictStack(ANY_RESISTANTALLOY.plateWelded(), 8), new OreDictStack(BIGMT.plateCast(), 4), new ComparableStack(ModItems.coil_advanced_alloy, 12), new ComparableStack(ModItems.battery_sc_polonium, 1)));

		// fusion reactor
		this.register(new GenericRecipe("ass.fusionconductor").setup(100, 100).outputItems(new ItemStack(ModBlocks.fusion_conductor, 1))
				.inputItems(new ComparableStack(ModItems.coil_advanced_alloy, 5)));
		this.register(new GenericRecipe("ass.fusioncenter").setup(200, 100).outputItems(new ItemStack(ModBlocks.fusion_center, 1))
				.inputItems(new OreDictStack(ANY_HARDPLASTIC.ingot(), 4), new OreDictStack(STEEL.plate528(), 6), new OreDictStack(ALLOY.wireFine(), 24)));
		this.register(new GenericRecipe("ass.fusionmotor").setup(400, 100).outputItems(new ItemStack(ModBlocks.fusion_motor, 1))
				.inputItems(new OreDictStack(TI.ingot(), 4), new OreDictStack(STEEL.ingot(), 2), new ComparableStack(ModItems.motor, 4)));
		this.register(new GenericRecipe("ass.fusionheater").setup(200, 100).outputItems(new ItemStack(ModBlocks.fusion_heater, 4))
				.inputItems(new OreDictStack(W.plateWelded(), 2), new OreDictStack(STEEL.plateWelded(), 2), new OreDictStack(OreDictManager.getReflector(), 2), new ComparableStack(ModItems.magnetron, 2)));
		
		// watz
		this.register(new GenericRecipe("ass.watzrod").setup(200, 100).outputItems(new ItemStack(ModBlocks.watz_element, 3))
				.inputItems(new OreDictStack(STEEL.plateCast(), 2), new OreDictStack(ZR.ingot(), 2), new OreDictStack(BIGMT.ingot(), 2), new OreDictStack(ANY_HARDPLASTIC.ingot(), 4)));
		this.register(new GenericRecipe("ass.watzcooler").setup(200, 100).outputItems(new ItemStack(ModBlocks.watz_cooler, 3))
				.inputItems(new OreDictStack(STEEL.plateCast(), 2), new OreDictStack(CU.plateCast(), 4), new OreDictStack(RUBBER.ingot(), 2)));
		this.register(new GenericRecipe("ass.watzcasing").setup(100, 100).outputItems(new ItemStack(ModBlocks.watz_end, 3))
				.inputItems(new OreDictStack(ANY_RESISTANTALLOY.plateWelded()), new OreDictStack(B.ingot(), 3), new OreDictStack(STEEL.plateWelded(), 2)));
		
		/*
		this.register(new GenericRecipe("ass.").setup(, 100).outputItems(new ItemStack(ModBlocks., 1))
				.inputItems());
		*/
		
		// rancid shit mob spawners
		this.register(new GenericRecipe("ass.chopper").setup(1_200, 100).outputItems(new ItemStack(ModItems.spawn_chopper, 8))
				.inputItems(new OreDictStack(CMB.plateCast(), 24), new OreDictStack(STEEL.plate(), 32), new OreDictStack(MAGTUNG.wireFine(), 48), new ComparableStack(ModItems.motor_desh, 5), new ComparableStack(ModItems.circuit, 1, EnumCircuitType.CONTROLLER_ADVANCED)));
		
		// weapon parts
		this.register(new GenericRecipe("ass.clusterpellets").setup(50, 100).outputItems(new ItemStack(ModItems.pellet_cluster, 1))
				.inputItems(new OreDictStack(STEEL.plate(), 4), new OreDictStack(ANY_HIGHEXPLOSIVE.ingot(), 1)));
		this.register(new GenericRecipe("ass.buckshot").setup(50, 100).outputItems(new ItemStack(ModItems.pellet_buckshot, 1))
				.inputItems(new OreDictStack(PB.nugget(), 6)));
		
		// bombs
		this.register(new GenericRecipe("ass.minenaval").setup(300, 100).outputItems(new ItemStack(ModBlocks.mine_naval, 1))
				.inputItems(new ComparableStack(ModItems.sphere_steel, 1), new OreDictStack(STEEL.pipe(), 3), new ComparableStack(ModItems.circuit, 1, EnumCircuitType.ADVANCED), new OreDictStack(ANY_PLASTICEXPLOSIVE.ingot(), 24)));
		this.register(new GenericRecipe("ass.gadget").setup(300, 100).outputItems(new ItemStack(ModBlocks.nuke_gadget, 1))
				.inputItems(new ComparableStack(ModItems.sphere_steel, 1), new ComparableStack(ModItems.fins_flat, 2), new ComparableStack(ModItems.pedestal_steel, 1), new ComparableStack(ModItems.circuit, 3, EnumCircuitType.CONTROLLER), new OreDictStack(KEY_GRAY, 8)));
		this.register(new GenericRecipe("ass.littleboy").setup(300, 100).outputItems(new ItemStack(ModBlocks.nuke_boy, 1))
				.inputItems(new OreDictStack(STEEL.shell(), 2), new ComparableStack(ModItems.fins_small_steel, 1), new ComparableStack(ModItems.circuit, 2, EnumCircuitType.CONTROLLER), new OreDictStack(KEY_BLUE, 4)));
		this.register(new GenericRecipe("ass.fatman").setup(300, 100).outputItems(new ItemStack(ModBlocks.nuke_man, 1))
				.inputItems(new ComparableStack(ModItems.sphere_steel, 1), new OreDictStack(STEEL.shell(), 2), new ComparableStack(ModItems.fins_big_steel, 1), new ComparableStack(ModItems.circuit, 3, EnumCircuitType.CONTROLLER), new OreDictStack(KEY_YELLOW, 6)));
		this.register(new GenericRecipe("ass.ivymike").setup(600, 100).outputItems(new ItemStack(ModBlocks.nuke_mike, 1))
				.inputItems(new ComparableStack(ModItems.sphere_steel, 1), new OreDictStack(AL.shell(), 4), new ComparableStack(ModItems.circuit, 8, EnumCircuitType.CONTROLLER_ADVANCED), new OreDictStack(KEY_LIGHTGRAY, 16)));
		this.register(new GenericRecipe("ass.tsarbomba").setup(1_200, 100).outputItems(new ItemStack(ModBlocks.nuke_tsar, 1))
				.inputItems(new ComparableStack(ModItems.sphere_steel, 1), new OreDictStack(TI.shell(), 6), new OreDictStack(STEEL.shell(), 2), new ComparableStack(ModItems.fins_tri_steel, 1), new ComparableStack(ModItems.circuit, 16, EnumCircuitType.CONTROLLER_ADVANCED), new OreDictStack(KEY_BLACK, 8)));
		this.register(new GenericRecipe("ass.ninadidnothingwrong").setup(300, 100).outputItems(new ItemStack(ModBlocks.nuke_prototype, 1))
				.inputItems(new ComparableStack(ModItems.dysfunctional_reactor, 1), new OreDictStack(STEEL.shell(), 2), new ComparableStack(ModItems.ingot_euphemium, 3), new ComparableStack(ModItems.circuit, 8, EnumCircuitType.CONTROLLER_ADVANCED)));
		this.register(new GenericRecipe("ass.fleija").setup(300, 100).outputItems(new ItemStack(ModBlocks.nuke_fleija, 1))
				.inputItems(new OreDictStack(AL.shell(), 1), new ComparableStack(ModItems.fins_quad_titanium, 1), new ComparableStack(ModItems.circuit, 1, EnumCircuitType.CONTROLLER), new OreDictStack(KEY_WHITE, 4)));
		this.register(new GenericRecipe("ass.solinium").setup(300, 100).outputItems(new ItemStack(ModBlocks.nuke_solinium, 1))
				.inputItems(new OreDictStack(STEEL.shell(), 2), new ComparableStack(ModItems.fins_quad_titanium, 1), new ComparableStack(ModItems.circuit, 1, EnumCircuitType.CONTROLLER), new OreDictStack(KEY_GRAY, 8)));
		this.register(new GenericRecipe("ass.n2mine").setup(200, 100).outputItems(new ItemStack(ModBlocks.nuke_n2, 1))
				.inputItems(new OreDictStack(STEEL.shell(), 6), new OreDictStack(MAGTUNG.wireFine(), 12), new ComparableStack(ModItems.circuit, 2, EnumCircuitType.CONTROLLER), new OreDictStack(KEY_GREEN, 8)));
		this.register(new GenericRecipe("ass.balefirebomb").setup(400, 100).outputItems(new ItemStack(ModBlocks.nuke_fstbmb, 1))
				.inputItems(new ComparableStack(ModItems.sphere_steel, 1), new OreDictStack(TI.shell(), 6), new ComparableStack(ModItems.fins_big_steel, 1), new ComparableStack(ModItems.powder_magic, 8), new ComparableStack(ModItems.circuit, 4, EnumCircuitType.CONTROLLER_ADVANCED), new OreDictStack(KEY_GRAY, 8)));
		this.register(new GenericRecipe("ass.customnuke").setup(300, 100).outputItems(new ItemStack(ModBlocks.nuke_custom, 1))
				.inputItems(new OreDictStack(STEEL.shell(), 2), new ComparableStack(ModItems.fins_small_steel, 1), new ComparableStack(ModItems.circuit, 8, EnumCircuitType.CONTROLLER_ADVANCED), new OreDictStack(KEY_GRAY, 4)));
		this.register(new GenericRecipe("ass.levibomb").setup(200, 100).outputItems(new ItemStack(ModBlocks.float_bomb, 1))
				.inputItems(new OreDictStack(TI.plate(), 12), new ComparableStack(ModItems.circuit, 4, EnumCircuitType.ADVANCED), new OreDictStack(GOLD.wireDense(), 8)));
		this.register(new GenericRecipe("ass.endobomb").setup(200, 100).outputItems(new ItemStack(ModBlocks.therm_endo, 1))
				.inputItems(new OreDictStack(TI.plate(), 12), new ComparableStack(ModItems.powder_ice, 32), new ComparableStack(ModItems.circuit, 1, EnumCircuitType.ADVANCED), new ComparableStack(ModItems.coil_gold, 4)));
		this.register(new GenericRecipe("ass.exobomb").setup(200, 100).outputItems(new ItemStack(ModBlocks.therm_exo, 1))
				.inputItems(new OreDictStack(TI.plate(), 12), new OreDictStack(P_RED.dust(), 32), new ComparableStack(ModItems.circuit, 1, EnumCircuitType.ADVANCED), new ComparableStack(ModItems.coil_gold, 4)));

		/*
		this.register(new GenericRecipe("ass.").setup(, 100).outputItems(new ItemStack(ModItems., 1))
				.inputItems());
		*/
		
		// bomb parts
		this.register(new GenericRecipe("ass.explosivelenses1").setup(400, 100).outputItems(new ItemStack(ModItems.early_explosive_lenses, 1))
				.inputItems(new OreDictStack(AL.plate(), 8), new OreDictStack(GOLD.wireFine(), 16), new ComparableStack(ModBlocks.det_cord, 8), new OreDictStack(CU.plate(), 2), new OreDictStack(ANY_HIGHEXPLOSIVE.ingot(), 20), new OreDictStack(ANY_PLASTIC.ingot(), 4)));
		this.register(new GenericRecipe("ass.explosivelenses2").setup(400, 100).outputItems(new ItemStack(ModItems.explosive_lenses, 1))
				.inputItems(new OreDictStack(AL.plate(), 8), new OreDictStack(MINGRADE.wireFine(), 16), new OreDictStack(ANY_PLASTICEXPLOSIVE.ingot(), 4), new OreDictStack(CU.plate(), 2), new ComparableStack(ModItems.ball_tatb, 16), new OreDictStack(RUBBER.ingot(), 2)));
		this.register(new GenericRecipe("ass.wiring").setup(200, 100).outputItems(new ItemStack(ModItems.gadget_wireing, 1))
				.inputItems(new OreDictStack(IRON.plate(), 1), new OreDictStack(GOLD.wireFine(), 12)));
		this.register(new GenericRecipe("ass.core1").setup(1_200, 100).outputItems(new ItemStack(ModItems.gadget_core, 1))
				.inputItems(new OreDictStack(PU239.nugget(), 7), new OreDictStack(U238.nugget(), 3)));
		this.register(new GenericRecipe("ass.boyshield").setup(200, 100).outputItems(new ItemStack(ModItems.boy_shielding, 1))
				.inputItems(new OreDictStack(OreDictManager.getReflector(), 12), new OreDictStack(STEEL.plate528(), 4)));
		this.register(new GenericRecipe("ass.boytarget").setup(200, 100).outputItems(new ItemStack(ModItems.boy_target, 1))
				.inputItems(new OreDictStack(U235.nugget(), 18)));
		this.register(new GenericRecipe("ass.boybullet").setup(200, 100).outputItems(new ItemStack(ModItems.boy_bullet, 1))
				.inputItems(new OreDictStack(U235.nugget(), 9)));
		this.register(new GenericRecipe("ass.boypropellant").setup(200, 100).outputItems(new ItemStack(ModItems.boy_propellant, 1))
				.inputItems(new ComparableStack(ModItems.cordite, 8), new OreDictStack(IRON.plate528(), 8), new OreDictStack(AL.plate528(), 4), new OreDictStack(MINGRADE.wireFine(), 4)));
		this.register(new GenericRecipe("ass.boyigniter").setup(200, 100).outputItems(new ItemStack(ModItems.boy_igniter, 1))
				.inputItems(new OreDictStack(AL.shell(), 3), new OreDictStack(DURA.plateCast(), 1), new ComparableStack(ModItems.circuit, 1, EnumCircuitType.ADVANCED.ordinal()), new OreDictStack(MINGRADE.wireFine(), 16)));
		this.register(new GenericRecipe("ass.manigniter").setup(200, 100).outputItems(new ItemStack(ModItems.man_igniter, 1))
				.inputItems(new OreDictStack(STEEL.plate528(), 6), new ComparableStack(ModItems.circuit, 1, EnumCircuitType.ADVANCED.ordinal()), new OreDictStack(MINGRADE.wireFine(), 9)));
		this.register(new GenericRecipe("ass.mancore").setup(1_200, 100).outputItems(new ItemStack(ModItems.man_core, 1))
				.inputItems(new OreDictStack(PU239.nugget(), 8), new OreDictStack(BE.nugget(), 2)));
		this.register(new GenericRecipe("ass.mikecore").setup(1_200, 100).outputItems(new ItemStack(ModItems.mike_core, 1))
				.inputItems(new OreDictStack(U238.nugget(), 24), new OreDictStack(PB.ingot(), 6)));
		this.register(new GenericRecipe("ass.mikedeut").setup(600, 100).outputItems(new ItemStack(ModItems.mike_deut, 1))
				.inputItems(new OreDictStack(IRON.plate528(), 12), new OreDictStack(STEEL.plate528(), 16), new ComparableStack(ModItems.cell_deuterium, 10)));
		this.register(new GenericRecipe("ass.mikecooler").setup(300, 100).outputItems(new ItemStack(ModItems.mike_cooling_unit, 1))
				.inputItems(new OreDictStack(IRON.plate528(), 8), new ComparableStack(ModItems.coil_copper, 5), new ComparableStack(ModItems.coil_tungsten, 5), new ComparableStack(ModItems.motor, 2)));
		this.register(new GenericRecipe("ass.fleijaigniter").setup(200, 100).outputItems(new ItemStack(ModItems.fleija_igniter, 1))
				.inputItems(new OreDictStack(TI.plate528(), 6), new OreDictStack(SA326.wireFine(), 2), new ComparableStack(ModItems.circuit, 1, EnumCircuitType.ADVANCED.ordinal())));
		this.register(new GenericRecipe("ass.fleijacore").setup(600, 100).outputItems(new ItemStack(ModItems.fleija_core, 1))
				.inputItems(new OreDictStack(U235.nugget(), 8), new OreDictStack(NP237.nugget(), 2), new OreDictStack(BE.nugget(), 4), new ComparableStack(ModItems.coil_copper, 2)));
		this.register(new GenericRecipe("ass.fleijacharge").setup(300, 100).outputItems(new ItemStack(ModItems.fleija_propellant, 1))
				.inputItems(new OreDictStack(ANY_HIGHEXPLOSIVE.ingot(), 3), new OreDictStack(SA326.plate(), 8)));
		this.register(new GenericRecipe("ass.soliniumigniter").setup(200, 100).outputItems(new ItemStack(ModItems.solinium_igniter, 1))
				.inputItems(new OreDictStack(TI.plate528(), 4), new OreDictStack(ALLOY.wireFine(), 2), new ComparableStack(ModItems.circuit, 1, EnumCircuitType.ADVANCED.ordinal()), new ComparableStack(ModItems.coil_gold, 1)));
		this.register(new GenericRecipe("ass.soliniumcore").setup(600, 100).outputItems(new ItemStack(ModItems.solinium_core, 1))
				.inputItems(new OreDictStack(SA327.nugget(), 9), new OreDictStack(EUPH.nugget(), 1)));
		this.register(new GenericRecipe("ass.soliniumcharge").setup(300, 100).outputItems(new ItemStack(ModItems.solinium_propellant, 1))
				.inputItems(new OreDictStack(ANY_HIGHEXPLOSIVE.ingot(), 3), new OreDictStack(OreDictManager.getReflector(), 2), new ComparableStack(ModItems.plate_polymer, 6), new OreDictStack(W.wireFine(), 6), new ComparableStack(ModItems.biomass_compressed, 4)));
		
		// grenades
		this.register(new GenericRecipe("ass.incgrenade").setup(100, 100).outputItems(new ItemStack(ModItems.grenade_fire, 1))
				.inputItems(new ComparableStack(ModItems.grenade_frag, 1), new OreDictStack(P_RED.dust(), 1), new OreDictStack(CU.plate(), 2)));
		this.register(new GenericRecipe("ass.shrapgrenade").setup(100, 100).outputItems(new ItemStack(ModItems.grenade_shrapnel, 1))
				.inputItems(new ComparableStack(ModItems.grenade_frag, 1), new ComparableStack(ModItems.pellet_buckshot, 1), new OreDictStack(STEEL.plate(), 2)));
		this.register(new GenericRecipe("ass.clustergrenade").setup(100, 100).outputItems(new ItemStack(ModItems.grenade_cluster, 1))
				.inputItems(new ComparableStack(ModItems.grenade_frag, 1), new ComparableStack(ModItems.pellet_cluster, 1), new OreDictStack(STEEL.plate(), 2)));
		this.register(new GenericRecipe("ass.signalflare").setup(100, 100).outputItems(new ItemStack(ModItems.grenade_flare, 1))
				.inputItems(new ComparableStack(ModItems.grenade_generic, 1), new ComparableStack(Items.glowstone_dust, 1), new OreDictStack(AL.plate(), 2)));
		this.register(new GenericRecipe("ass.electricgrenade").setup(100, 100).outputItems(new ItemStack(ModItems.grenade_electric, 1))
				.inputItems(new ComparableStack(ModItems.grenade_generic, 1), new ComparableStack(ModItems.circuit, 3, EnumCircuitType.CAPACITOR.ordinal()), new OreDictStack(GOLD.plate(), 2)));
		this.register(new GenericRecipe("ass.pulsegrenade").setup(100, 100).outputItems(new ItemStack(ModItems.grenade_pulse, 4))
				.inputItems(new OreDictStack(STEEL.plate(), 1), new OreDictStack(IRON.plate(), 3), new OreDictStack(MINGRADE.wireFine(), 6), new ComparableStack(Items.diamond, 1)));
		this.register(new GenericRecipe("ass.plasmagrenade").setup(200, 100).outputItems(new ItemStack(ModItems.grenade_plasma, 2))
				.inputItems(new OreDictStack(STEEL.plate(), 3), new OreDictStack(ALLOY.plate(), 1), new ComparableStack(ModItems.coil_advanced_torus, 1), new ComparableStack(ModItems.cell_deuterium, 1), new ComparableStack(ModItems.cell_tritium, 1)));
		this.register(new GenericRecipe("ass.taugrenade").setup(200, 100).outputItems(new ItemStack(ModItems.grenade_tau, 2))
				.inputItems(new OreDictStack(PB.plate(), 3), new OreDictStack(ALLOY.plate(), 1), new ComparableStack(ModItems.coil_advanced_torus, 1), new ComparableStack(ModItems.ammo_standard, 1, EnumAmmo.TAU_URANIUM)));
		this.register(new GenericRecipe("ass.schrabgrenade").setup(200, 100).outputItems(new ItemStack(ModItems.grenade_schrabidium, 1))
				.inputItems(new ComparableStack(ModItems.grenade_flare, 1), new OreDictStack(SA326.dust(), 1), new OreDictStack(OreDictManager.getReflector(), 2)));
		this.register(new GenericRecipe("ass.nukagrenade").setup(600, 100).outputItems(new ItemStack(ModItems.grenade_nuclear, 1))
				.inputItems(new OreDictStack(IRON.plate(), 1), new OreDictStack(STEEL.plate(), 1), new OreDictStack(PU239.nugget(), 2), new OreDictStack(MINGRADE.wireFine(), 2)));
		this.register(new GenericRecipe("ass.zomggrenade").setup(600, 100).outputItems(new ItemStack(ModItems.grenade_zomg, 1))
				.inputItems(new ComparableStack(ModItems.plate_paa, 3), new OreDictStack(OreDictManager.getReflector(), 1), new ComparableStack(ModItems.coil_magnetized_tungsten, 3), new ComparableStack(ModItems.powder_power, 3)));
		this.register(new GenericRecipe("ass.bholegrenade").setup(1_200, 100).outputItems(new ItemStack(ModItems.grenade_black_hole, 1))
				.inputItems(new OreDictStack(ANY_PLASTIC.ingot(), 6), new OreDictStack(OreDictManager.getReflector(), 3), new ComparableStack(ModItems.coil_magnetized_tungsten, 2), new ComparableStack(ModItems.black_hole, 1)));
		
		// missile parts
		this.register(new GenericRecipe("ass.missileassembly").setup(200, 100).outputItems(new ItemStack(ModItems.missile_assembly, 1))
				.inputItems(new OreDictStack(AL.shell(), 2), new OreDictStack(TI.shell(), 4), new OreDictStack(ANY_PLASTIC.ingot(), 8), new ComparableStack(ModItems.rocket_fuel, 8), new ComparableStack(ModItems.circuit, 1, EnumCircuitType.BASIC)));
		this.register(new GenericRecipe("ass.warheadhe1").setup(100, 100).outputItems(new ItemStack(ModItems.warhead_generic_small, 1))
				.inputItems(new OreDictStack(TI.plate(), 4), new ComparableStack(ModItems.ball_dynamite, 2), new ComparableStack(ModItems.circuit, 1, EnumCircuitType.CHIP)));
		this.register(new GenericRecipe("ass.warheadhe2").setup(200, 100).outputItems(new ItemStack(ModItems.warhead_generic_medium, 1))
				.inputItems(new OreDictStack(TI.plate(), 8), new OreDictStack(ANY_HIGHEXPLOSIVE.ingot(), 4), new ComparableStack(ModItems.circuit, 1, EnumCircuitType.BASIC)));
		this.register(new GenericRecipe("ass.warheadhe3").setup(400, 100).outputItems(new ItemStack(ModItems.warhead_generic_large, 1))
				.inputItems(new OreDictStack(TI.plate(), 16), new OreDictStack(ANY_HIGHEXPLOSIVE.ingot(), 8), new ComparableStack(ModItems.circuit, 1, EnumCircuitType.ADVANCED)));
		this.register(new GenericRecipe("ass.warheadinc1").setup(100, 100).outputItems(new ItemStack(ModItems.warhead_incendiary_small, 1))
				.inputItems(new ComparableStack(ModItems.warhead_generic_small, 1), new OreDictStack(P_RED.dust(), 2)));
		this.register(new GenericRecipe("ass.warheadinc2").setup(200, 100).outputItems(new ItemStack(ModItems.warhead_incendiary_medium, 1))
				.inputItems(new ComparableStack(ModItems.warhead_generic_medium, 1), new OreDictStack(P_RED.dust(), 4)));
		this.register(new GenericRecipe("ass.warheadinc3").setup(400, 100).outputItems(new ItemStack(ModItems.warhead_incendiary_large, 1))
				.inputItems(new ComparableStack(ModItems.warhead_generic_large, 1), new OreDictStack(P_RED.dust(), 8)));
		this.register(new GenericRecipe("ass.warheadcl1").setup(100, 100).outputItems(new ItemStack(ModItems.warhead_cluster_small, 1))
				.inputItems(new ComparableStack(ModItems.warhead_generic_small, 1), new ComparableStack(ModItems.pellet_cluster, 2)));
		this.register(new GenericRecipe("ass.warheadcl2").setup(200, 100).outputItems(new ItemStack(ModItems.warhead_cluster_medium, 1))
				.inputItems(new ComparableStack(ModItems.warhead_generic_medium, 1), new ComparableStack(ModItems.pellet_cluster, 4)));
		this.register(new GenericRecipe("ass.warheadcl3").setup(400, 100).outputItems(new ItemStack(ModItems.warhead_cluster_large, 1))
				.inputItems(new ComparableStack(ModItems.warhead_generic_large, 1), new ComparableStack(ModItems.pellet_cluster, 8)));
		this.register(new GenericRecipe("ass.warheadbb1").setup(100, 100).outputItems(new ItemStack(ModItems.warhead_buster_small, 1))
				.inputItems(new ComparableStack(ModItems.warhead_generic_small, 1), new OreDictStack(ANY_HIGHEXPLOSIVE.ingot(), 2)));
		this.register(new GenericRecipe("ass.warheadbb2").setup(200, 100).outputItems(new ItemStack(ModItems.warhead_buster_medium, 1))
				.inputItems(new ComparableStack(ModItems.warhead_generic_medium, 1), new OreDictStack(ANY_HIGHEXPLOSIVE.ingot(), 4)));
		this.register(new GenericRecipe("ass.warheadbb3").setup(400, 100).outputItems(new ItemStack(ModItems.warhead_buster_large, 1))
				.inputItems(new ComparableStack(ModItems.warhead_generic_large, 1), new OreDictStack(ANY_HIGHEXPLOSIVE.ingot(), 8)));
		this.register(new GenericRecipe("ass.warheadnuke").setup(400, 100).outputItems(new ItemStack(ModItems.warhead_nuclear, 1))
				.inputItems(new OreDictStack(TI.plateCast(), 12), new OreDictStack(PB.plateCast(), 6), new OreDictStack(U235.billet(), 6), new ComparableStack(ModItems.cordite, 12), new ComparableStack(ModItems.circuit, 1, EnumCircuitType.CONTROLLER)));
		this.register(new GenericRecipe("ass.warheadthermonuke").setup(600, 100).outputItems(new ItemStack(ModItems.warhead_mirv, 1))
				.inputItems(new OreDictStack(TI.plateCast(), 12), new OreDictStack(PB.plateCast(), 6), new OreDictStack(PU239.billet(), 8), new ComparableStack(ModItems.ball_tatb, 12), new ComparableStack(ModItems.circuit, 2, EnumCircuitType.CONTROLLER_ADVANCED))
				.inputFluids(new FluidStack(Fluids.DEUTERIUM, 4_000)));
		this.register(new GenericRecipe("ass.warheadvolcano").setup(600, 100).outputItems(new ItemStack(ModItems.warhead_volcano, 1))
				.inputItems(new OreDictStack(TI.plateCast(), 12), new OreDictStack(STEEL.plateCast(), 6), new ComparableStack(ModBlocks.det_nuke, 3), new OreDictStack(U238.block(), 24), new ComparableStack(ModItems.circuit, 5, EnumCircuitType.CAPACITOR_BOARD.ordinal())));
		this.register(new GenericRecipe("ass.stealthmissile").setup(1_200, 100).outputItems(new ItemStack(ModItems.missile_stealth, 1))
				.inputItems(new OreDictStack(TI.plate(), 20), new OreDictStack(AL.plate(), 20), new OreDictStack(KEY_BLACK, 16), new OreDictStack(ANY_HARDPLASTIC.ingot(), 16), new OreDictStack(ANY_HIGHEXPLOSIVE.ingot(), 4), new ComparableStack(ModItems.circuit, 4, EnumCircuitType.ADVANCED.ordinal()), new OreDictStack(STEEL.bolt(), 32)));
		this.register(new GenericRecipe("ass.thrusternerva").setup(600, 100).outputItems(new ItemStack(ModItems.thruster_nuclear, 1))
				.inputItems(new OreDictStack(DURA.ingot(), 32), new OreDictStack(B.ingot(), 8), new OreDictStack(PB.plate(), 16), new ComparableStack(ModItems.pipes_steel)));
	}
	
	public static HashMap getRecipes() {
		HashMap<Object, Object> recipes = new HashMap<Object, Object>();
		
		for(GenericRecipe recipe : INSTANCE.recipeOrderedList) {
			List input = new ArrayList();
			if(recipe.inputItem != null) for(AStack stack : recipe.inputItem) input.add(stack);
			if(recipe.inputFluid != null) for(FluidStack stack : recipe.inputFluid) input.add(ItemFluidIcon.make(stack));
			List output = new ArrayList();
			if(recipe.outputItem != null) for(IOutput stack : recipe.outputItem) output.add(stack.getAllPossibilities());
			if(recipe.outputFluid != null) for(FluidStack stack : recipe.outputFluid) output.add(ItemFluidIcon.make(stack));
			recipes.put(input.toArray(), output.toArray());
		}
		
		return recipes;
	}
}
