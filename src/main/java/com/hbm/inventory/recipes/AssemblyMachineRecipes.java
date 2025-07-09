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
		this.register(new GenericRecipe("ass.titaniumdrill").setup(100, 100).outputItems(new ItemStack(ModItems.drill_titanium, 1))
				.inputItems(new OreDictStack(DURA.plateCast(), 1), new OreDictStack(TI.plate(), 8)));

		// bunker blocks
		this.register(new GenericRecipe("ass.cmbtile").setup(100, 100).outputItems(new ItemStack(ModBlocks.cmb_brick, 8))
				.inputItems(new OreDictStack(ANY_CONCRETE.any(), 4), new OreDictStack(CMB.plate(), 4)));
		this.register(new GenericRecipe("ass.cmbbrick").setup(100, 100).outputItems(new ItemStack(ModBlocks.cmb_brick_reinforced, 8))
				.inputItems(new OreDictStack(MAGTUNG.ingot(), 8), new ComparableStack(ModBlocks.ducrete, 4), new ComparableStack(ModBlocks.cmb_brick, 8)));
		this.register(new GenericRecipe("ass.sealframe").setup(100, 100).outputItems(new ItemStack(ModBlocks.seal_frame, 1))
				.inputItems(new OreDictStack(DURA.ingot(), 1), new OreDictStack(STEEL.plateCast(), 1), new OreDictStack(MINGRADE.wireDense(), 1)));
		this.register(new GenericRecipe("ass.sealcontroller").setup(100, 100).outputItems(new ItemStack(ModBlocks.seal_frame, 1))
				.inputItems(new OreDictStack(DURA.ingot(), 1), new OreDictStack(STEEL.plateCast(), 1), new OreDictStack(ANY_PLASTIC.ingot(), 4), new OreDictStack(MINGRADE.wireDense(), 4)));
		
		// machines
		this.register(new GenericRecipe("ass.centrifuge").setup(200, 100).outputItems(new ItemStack(ModBlocks.machine_centrifuge, 1))
				.inputItems(new ComparableStack(ModItems.centrifuge_element, 1), new OreDictStack(ANY_PLASTIC.ingot(), 4), new OreDictStack(STEEL.plate528(), 8), new OreDictStack(CU.plate(), 4), new ComparableStack(ModItems.circuit, 1, EnumCircuitType.ANALOG)));
		this.register(new GenericRecipe("ass.gascent").setup(400, 100).outputItems(new ItemStack(ModBlocks.machine_gascent, 1))
				.inputItems(new ComparableStack(ModItems.centrifuge_element, 4), new OreDictStack(ANY_PLASTIC.ingot(), 8), new OreDictStack(DESH.ingot(), 2), new OreDictStack(STEEL.plate528(), 8), new ComparableStack(ModItems.circuit, 1, EnumCircuitType.ADVANCED.ordinal())));

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
