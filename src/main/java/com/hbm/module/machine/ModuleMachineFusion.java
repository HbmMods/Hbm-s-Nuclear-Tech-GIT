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
		if(processSpeed <= 0) return 0;

        if(recipe.inputFluid != null) {
            for(int i = 0; i < Math.min(recipe.inputFluid.length, inputTanks.length); i++) {
                if(inputTanks[i].getFill() > 0 && inputTanks[i].getFill() < (int) Math.ceil(recipe.inputFluid[i].fill * processSpeed)) return 0;
            }
        }

        return 1; // 1 is enough for torus under any circumstances
	}
	
	@Override
	public void process(GenericRecipe recipe, double speed, double power, int count) {
        this.battery.setPower(this.battery.getPower() - (long) Math.ceil((power == 1 ? recipe.power : (long) (recipe.power * power)) * processSpeed));
        double step = Math.min(speed / recipe.duration * processSpeed, 1D); // can't do more than one recipe per tick, might look into that later
        this.progress += step;
        this.bonus += step * this.bonusSpeed;
        //this.bonus = Math.min(this.bonus, 1.5D); // bonus might not be used immediately in rare circumstances, allow 50% buffer

        // fusion reactor is the only machine as of now that consumes input while not having finished the output
        if(recipe.inputFluid != null) {
            for(int i = 0; i < Math.min(recipe.inputFluid.length, inputTanks.length); i++) {
                inputTanks[i].setFill(inputTanks[i].getFill() - (int) Math.ceil(recipe.inputFluid[i].fill * processSpeed));
            }
        }

        if(this.progress >= 1D) {
            produceItem(recipe, 1); // again, 1 should be enough

            if(this.canProcess(recipe, speed, power) >= 1)  this.progress -= 1D;
            else this.progress = 0D;
        }

        int bonusMulti = bonus < 1D ? 0 : this.fitOutput(recipe, (int) bonus); // maybe this should handle multi processing
        if(bonusMulti >= 1) {
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
