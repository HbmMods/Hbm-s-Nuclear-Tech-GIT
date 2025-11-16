package com.hbm.module.machine;

import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.recipes.FusionRecipes;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.inventory.recipes.loader.GenericRecipes;

import api.hbm.energymk2.IEnergyHandlerMK2;
import net.minecraft.item.ItemStack;

public class ModuleMachineFusion extends ModuleMachineBase {
	
	public double bonusSpeed = 0D;
	public double bonus;

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
	
	// setup needs to run before update, used to keep track of things that ModuleMachineBase doesn't handle
	public void preUpdate(double bonusSpeed) {
		this.bonusSpeed = bonusSpeed;
	}
	
	@Override
	public void process(GenericRecipe recipe, double speed, double power) {
		this.battery.setPower(this.battery.getPower() - (power == 1 ? recipe.power : (long) (recipe.power * power)));
		double step = Math.min(speed / recipe.duration, 1D); // can't do more than one recipe per tick, might look into that later
		this.progress += step;
		this.bonus += step * this.bonusSpeed;
		this.bonus = Math.min(this.bonus, 1.5D); // bonus might not be used immediately in rare circumstances, allow 50% buffer
		
		if(this.progress >= 1D) {
			consumeInput(recipe);
			produceItem(recipe);
			
			if(this.canProcess(recipe, speed, power))  this.progress -= 1D;
			else this.progress = 0D;
		}
		
		if(this.bonus >= 1D && this.canFitOutput(recipe)) {
			produceItem(recipe);
			this.bonus -= 1D;
		}
	}
}
