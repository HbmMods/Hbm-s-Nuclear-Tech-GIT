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
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.items.machine.ItemCircuit.EnumCircuitType;

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

		// weapon parts
		this.register(new GenericRecipe("ass.clusterpellets").setup(50, 100).outputItems(new ItemStack(ModItems.pellet_cluster, 1))
				.inputItems(new OreDictStack(STEEL.plate(), 4), new OreDictStack(ANY_HIGHEXPLOSIVE.ingot(), 1)));
		this.register(new GenericRecipe("ass.buckshot").setup(50, 100).outputItems(new ItemStack(ModItems.pellet_buckshot, 1))
				.inputItems(new OreDictStack(PB.nugget(), 6)));
		
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
		this.register(new GenericRecipe("ass.centrifuge").setup(200, 100).outputItems(new ItemStack(ModBlocks.machine_centrifuge, 1))
				.inputItems(new ComparableStack(ModItems.centrifuge_element, 1), new OreDictStack(ANY_PLASTIC.ingot(), 4), new OreDictStack(STEEL.plate528(), 8), new OreDictStack(CU.plate(), 4), new ComparableStack(ModItems.circuit, 1, EnumCircuitType.ANALOG)));
		this.register(new GenericRecipe("ass.gascent").setup(400, 100).outputItems(new ItemStack(ModBlocks.machine_gascent, 1))
				.inputItems(new ComparableStack(ModItems.centrifuge_element, 4), new OreDictStack(ANY_PLASTIC.ingot(), 8), new OreDictStack(DESH.ingot(), 2), new OreDictStack(STEEL.plate528(), 8), new ComparableStack(ModItems.circuit, 1, EnumCircuitType.ADVANCED.ordinal())));

		// rancid shit mob spawners
		this.register(new GenericRecipe("ass.chopper").setup(1_200, 100).outputItems(new ItemStack(ModItems.spawn_chopper, 8))
				.inputItems(new OreDictStack(CMB.plateCast(), 24), new OreDictStack(STEEL.plate(), 32), new OreDictStack(MAGTUNG.wireFine(), 48), new ComparableStack(ModItems.motor_desh, 5), new ComparableStack(ModItems.circuit, 1, EnumCircuitType.CONTROLLER_ADVANCED)));
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
