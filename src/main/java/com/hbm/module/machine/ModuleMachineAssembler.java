package com.hbm.module.machine;

import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.recipes.AssemblyMachineRecipes;
import com.hbm.inventory.recipes.loader.GenericRecipe;

import api.hbm.energymk2.IEnergyHandlerMK2;
import net.minecraft.item.ItemStack;

public class ModuleMachineAssembler extends ModuleMachineBase {

	public ModuleMachineAssembler(int index, IEnergyHandlerMK2 battery, ItemStack[] slots) {
		super(index, battery, slots);
		this.inputSlots = new int[12];
		this.outputSlots = new int[1];
		this.inputTanks = new FluidTank[1];
		this.outputTanks = new FluidTank[1];
	}

	@Override
	public GenericRecipe getRecipe() {
		return AssemblyMachineRecipes.INSTANCE.recipeNameMap.get(this.recipe);
	}

	public ModuleMachineAssembler itemInput(int from) { for(int i = 0; i < inputSlots.length; i++) inputSlots[i] = from + i; return this; }
	public ModuleMachineAssembler itemOutput(int a) { outputSlots[0] = a; return this; }
	public ModuleMachineAssembler fluidInput(FluidTank a) { inputTanks[0] = a; return this; }
	public ModuleMachineAssembler fluidOutput(FluidTank a) { outputTanks[0] = a; return this; }
}
