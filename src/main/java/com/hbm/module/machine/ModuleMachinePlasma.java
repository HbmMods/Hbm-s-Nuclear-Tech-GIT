package com.hbm.module.machine;

import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.recipes.PlasmaForgeRecipes;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.inventory.recipes.loader.GenericRecipes;
import com.hbm.util.BobMathUtil;

import api.hbm.energymk2.IEnergyHandlerMK2;
import net.minecraft.item.ItemStack;

public class ModuleMachinePlasma extends ModuleMachineBase {

	public ModuleMachinePlasma(int index, IEnergyHandlerMK2 battery, ItemStack[] slots) {
		super(index, battery, slots);
		this.inputSlots = new int[12];
		this.outputSlots = new int[1];
		this.inputTanks = new FluidTank[1];
		this.outputTanks = new FluidTank[0];
	}

	@Override
	public GenericRecipes getRecipeSet() {
		return PlasmaForgeRecipes.INSTANCE;
	}
	
	@Override
	public void setupTanks(GenericRecipe recipe) {
		super.setupTanks(recipe);
		if(recipe == null) return;
		for(int i = 0; i < inputTanks.length; i++) if(recipe.inputFluid != null && recipe.inputFluid.length > i) inputTanks[i].changeTankSize(BobMathUtil.max(inputTanks[i].getFill(), recipe.inputFluid[i].fill * 2, 16_000));
	}

	public ModuleMachinePlasma itemInput(int from) { for(int i = 0; i < inputSlots.length; i++) inputSlots[i] = from + i; return this; }
	public ModuleMachinePlasma itemOutput(int a) { outputSlots[0] = a; return this; }
	public ModuleMachinePlasma fluidInput(FluidTank a) { inputTanks[0] = a; return this; }
}
