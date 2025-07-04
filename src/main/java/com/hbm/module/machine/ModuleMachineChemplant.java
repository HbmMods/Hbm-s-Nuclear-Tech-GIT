package com.hbm.module.machine;

import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.recipes.ChemicalPlantRecipes;
import com.hbm.inventory.recipes.loader.GenericRecipe;

import api.hbm.energymk2.IEnergyHandlerMK2;
import net.minecraft.item.ItemStack;

/**
 * Option 1: Make a base class with weird arbitrary overrides to define shit like slots for multi machines like the chemfac
 * Option 2: Make an easy to define module which can be used by whatever needs it, hypothetically allowing a mixed recipe machine.
 * In the hudson bay, you know how we do it.
 * @author hbm
 */
public class ModuleMachineChemplant extends ModuleMachineBase {

	public ModuleMachineChemplant(int index, IEnergyHandlerMK2 battery, ItemStack[] slots) {
		super(index, battery, slots);
		this.inputSlots = new int[3];
		this.outputSlots = new int[3];
		this.inputTanks = new FluidTank[3];
		this.outputTanks = new FluidTank[3];
	}

	@Override
	public GenericRecipe getRecipe() {
		return ChemicalPlantRecipes.INSTANCE.recipeNameMap.get(this.recipe);
	}

	public ModuleMachineChemplant itemInput(int a, int b, int c) { inputSlots[0] = a; inputSlots[1] = b; inputSlots[2] = c; return this; }
	public ModuleMachineChemplant itemOutput(int a, int b, int c) { outputSlots[0] = a; outputSlots[1] = b; outputSlots[2] = c; return this; }
	public ModuleMachineChemplant fluidInput(FluidTank a, FluidTank b, FluidTank c) { inputTanks[0] = a; inputTanks[1] = b; inputTanks[2] = c; return this; }
	public ModuleMachineChemplant fluidOutput(FluidTank a, FluidTank b, FluidTank c) { outputTanks[0] = a; outputTanks[1] = b; outputTanks[2] = c; return this; }
}
