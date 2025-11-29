package com.hbm.module.machine;

import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.recipes.FusionRecipes;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.inventory.recipes.loader.GenericRecipes;

import api.hbm.energymk2.IEnergyHandlerMK2;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleMachineFusion extends ModuleMachineBase {

	public double processSpeed = 1D;
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
	public void preUpdate(double processSpeed, double bonusSpeed) {
		this.processSpeed = processSpeed;
		this.bonusSpeed = bonusSpeed;
	}
	
	@Override
	protected int findMultiplier(GenericRecipe recipe) {
        int count = 50; // the fallback value of 50
		if(processSpeed <= 0) return 0;
		
		if(recipe.inputFluid != null) {
			for(int i = 0; i < Math.min(recipe.inputFluid.length, inputTanks.length); i++) {
                if(recipe.inputFluid[i].fill == 0) continue; // to prevent division by zero
                count = Math.min(count, inputTanks[i].getFill() / (int) Math.ceil(recipe.inputFluid[i].fill * processSpeed));
                if(count == 0) return 0;
			}
		}
		
		return count; // although we have this at a steady rate...
	}
	
	@Override
	public void process(GenericRecipe recipe, double speed, double power, int count) {
		this.battery.setPower(this.battery.getPower() - (long) Math.ceil((power == 1 ? recipe.power : (long) (recipe.power * power)) * processSpeed));
		double step = speed / recipe.duration * processSpeed;
		this.progress += step;
        int multi = Math.min((int)this.progress, count);

        this.bonus += step * this.bonusSpeed;
		
		// fusion reactor is the only machine as of now that consumes input while not having finished the output
		if(recipe.inputFluid != null) {
			for(int i = 0; i < Math.min(recipe.inputFluid.length, inputTanks.length); i++) {
				inputTanks[i].setFill(inputTanks[i].getFill() - (int) Math.ceil(recipe.inputFluid[i].fill * processSpeed));
			}
		}
		
		if(multi > 0) {
			produceItem(recipe, multi);
			
			if(this.canProcess(recipe, speed, power) > 0)  this.progress -= multi;
			else this.progress = 0D;
		}
		
		int bonusMulti = this.fitOutput(recipe, (int) this.bonus);
        if(bonusMulti > 0) {
			produceItem(recipe, bonusMulti);
			this.bonus -= bonusMulti;
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeDouble(bonus);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.bonus = buf.readDouble();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.bonus = nbt.getDouble("bonus" + index);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setDouble("bonus" + index, bonus);
	}
}
