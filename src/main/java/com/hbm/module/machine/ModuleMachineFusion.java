package com.hbm.module.machine;

import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.recipes.FusionRecipes;
import com.hbm.inventory.recipes.loader.GenericRecipes;

import api.hbm.energymk2.IEnergyHandlerMK2;
import net.minecraft.item.ItemStack;

public class ModuleMachineFusion extends ModuleMachineBase {

	public ModuleMachineFusion(int index, IEnergyHandlerMK2 battery, ItemStack[] slots) {
		super(index, battery, slots);
		this.inputSlots = new int[0];
		this.outputSlots = new int[1];
		this.inputTanks = new FluidTank[3];
		this.outputTanks = new FluidTank[1];
	}

	@Override
	public GenericRecipes getRecipeSet() {
		return FusionRecipes.INSTANCE;
	}

	public ModuleMachineFusion itemOutput(int slot) { outputSlots[0] = slot; return this; }
	public ModuleMachineFusion fluidInput(FluidTank a, FluidTank b, FluidTank c) { inputTanks[0] = a; inputTanks[1] = b; inputTanks[2] = c; return this; }
	public ModuleMachineFusion fluidOutput(FluidTank a) { outputTanks[0] = a; return this; }
}
