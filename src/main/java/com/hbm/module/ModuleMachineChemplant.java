package com.hbm.module;

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
public class ModuleMachineChemplant {
	
	public int index;
	public IEnergyHandlerMK2 battery;
	public ItemStack[] slots;
	public int[] inputSlots = new int[3];
	public int[] outputSlots = new int[3];
	public FluidTank[] inputTanks = new FluidTank[3];
	public FluidTank[] outputTanks = new FluidTank[3];
	
	public String recipe;
	public float progress;

	public ModuleMachineChemplant(int index, IEnergyHandlerMK2 battery, ItemStack[] slots) {
		this.index = index;
		this.battery = battery;
		this.slots = slots;
	}
	
	public boolean canProcess() {
		GenericRecipe recipe = ChemicalPlantRecipes.INSTANCE.recipeNameMap.get(this.recipe);
		if(recipe == null) return false;
		if(battery.getPower() < recipe.power) return false;
		
		//TBI
		
		return true;
	}
	
	public void resetProgress() { this.progress = 0F; }
	
	public void update() {
		
	}

	public ModuleMachineChemplant iInput(int a, int b, int c) { inputSlots[0] = a; inputSlots[1] = b; inputSlots[2] = c; return this; }
	public ModuleMachineChemplant iOutput(int a, int b, int c) { outputSlots[0] = a; outputSlots[1] = b; outputSlots[2] = c; return this; }
	public ModuleMachineChemplant fInput(FluidTank a, FluidTank b, FluidTank c) { inputTanks[0] = a; inputTanks[1] = b; inputTanks[2] = c; return this; }
	public ModuleMachineChemplant fOutput(FluidTank a, FluidTank b, FluidTank c) { outputTanks[0] = a; outputTanks[1] = b; outputTanks[2] = c; return this; }
}
