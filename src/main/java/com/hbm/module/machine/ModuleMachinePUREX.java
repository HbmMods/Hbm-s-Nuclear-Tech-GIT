package com.hbm.module.machine;

import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.recipes.PUREXRecipes;
import com.hbm.inventory.recipes.loader.GenericRecipe;

import api.hbm.energymk2.IEnergyHandlerMK2;
import net.minecraft.item.ItemStack;

public class ModuleMachinePUREX extends ModuleMachineBase {

	public ModuleMachinePUREX(int index, IEnergyHandlerMK2 battery, ItemStack[] slots) {
		super(index, battery, slots);
		this.inputSlots = new int[3];
		this.outputSlots = new int[6];
		this.inputTanks = new FluidTank[3];
		this.outputTanks = new FluidTank[1];
	}

	@Override
	public GenericRecipe getRecipe() {
		return PUREXRecipes.INSTANCE.recipeNameMap.get(this.recipe);
	}

	public ModuleMachinePUREX itemInput(int start) { for(int i = 0; i < inputSlots.length; i++) inputSlots[i] = start + i; return this; }
	public ModuleMachinePUREX itemOutput(int start) { for(int i = 0; i < outputSlots.length; i++) outputSlots[i] = start + i; return this; }
	public ModuleMachinePUREX fluidInput(FluidTank a, FluidTank b, FluidTank c) { inputTanks[0] = a; inputTanks[1] = b; inputTanks[2] = c; return this; }
	public ModuleMachinePUREX fluidOutput(FluidTank a) { outputTanks[0] = a; return this; }
}
