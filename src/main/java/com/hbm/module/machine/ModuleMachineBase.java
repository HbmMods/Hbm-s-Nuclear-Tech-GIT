package com.hbm.module.machine;

import java.util.List;

import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.inventory.recipes.loader.GenericRecipes;
import com.hbm.inventory.recipes.loader.GenericRecipes.IOutput;
import com.hbm.items.machine.ItemBlueprints;

import api.hbm.energymk2.IEnergyHandlerMK2;
import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ModuleMachineBase {
	
	// setup
	public int index;
	public IEnergyHandlerMK2 battery;
	public ItemStack[] slots;
	public int[] inputSlots;
	public int[] outputSlots;
	public FluidTank[] inputTanks;
	public FluidTank[] outputTanks;
	// running vars
	public String recipe = "null";
	public double progress;
	// return signals
	public boolean didProcess = false;
	public boolean markDirty = false;

	public ModuleMachineBase(int index, IEnergyHandlerMK2 battery, ItemStack[] slots) {
		this.index = index;
		this.battery = battery;
		this.slots = slots;
	}
	
	/** Chances tank type and pressure based on recipe */
	public void setupTanks(GenericRecipe recipe) {
		if(recipe == null) return;
		for(int i = 0; i < inputTanks.length; i++) if(recipe.inputFluid != null && recipe.inputFluid.length > i) inputTanks[i].conform(recipe.inputFluid[i]); else inputTanks[i].resetTank();
		for(int i = 0; i < outputTanks.length; i++) if(recipe.outputFluid != null && recipe.outputFluid.length > i) outputTanks[i].conform(recipe.outputFluid[i]); else outputTanks[i].resetTank();
	}
	
	/** Expects the tanks to be set up correctly beforehand */
    public int canProcess(GenericRecipe recipe, double speed, double power) {
		if(recipe == null) return 0;
		
		// auto switch functionality
		if(recipe.autoSwitchGroup != null && inputSlots.length > 0 && slots[inputSlots[0]] != null) {
			ItemStack itemToSwitchBy = slots[inputSlots[0]];
			List<GenericRecipe> recipes = (List<GenericRecipe>) this.getRecipeSet().autoSwitchGroups.get(recipe.autoSwitchGroup);
			if(recipes != null) for(GenericRecipe nextRec : recipes) {
				if(nextRec.getInternalName().equals(this.recipe)) continue;
				if(nextRec.inputItem == null) continue;
				if(nextRec.inputItem[0].matchesRecipe(itemToSwitchBy, true)) { // perform the switch
					this.recipe = nextRec.getInternalName();
					return 0; // cancel the recipe this tick since we need to do the previous checking all over again
				}
			}
		}
		
		if(power != 1 && battery.getPower() < recipe.power * power) return 0; // only check with floating point numbers if mult is not 1
		if(power == 1 && battery.getPower() < recipe.power) return 0;

        int multi = findMultiplier(recipe); // for calculating the times that the current recipe can be done
		
		if(multi == 0) return 0;
		
		return fitOutput(recipe, multi);
	}
	
	protected int findMultiplier(GenericRecipe recipe) {
		int count = 50; // the fallback value of 50
        if(recipe.inputItem != null) {
			for(int i = 0; i < Math.min(recipe.inputItem.length, inputSlots.length); i++) {
                count = Math.min(count, recipe.inputItem[i].matchesRecipe(slots[inputSlots[i]]));
				if(count == 0) return 0;
			}
		}
		
		if(recipe.inputFluid != null) {
			for(int i = 0; i < Math.min(recipe.inputFluid.length, inputTanks.length); i++) {
                if(recipe.inputFluid[i].fill == 0) continue; // to prevent division by zero; although this should be checked on the recipe side
                count = Math.min(count, inputTanks[i].getFill() / recipe.inputFluid[i].fill);
                if(count == 0) return 0;
			}
		}
		
		return count;
	}
	
	/** Whether (and how many times) the machine can hold the output produced by the recipe */
	protected int fitOutput(GenericRecipe recipe, int count) {
		
		if(recipe.outputItem != null) {
			for(int i = 0; i < Math.min(recipe.outputItem.length, outputSlots.length); i++) {
				ItemStack stack = slots[outputSlots[i]];
				IOutput output = recipe.outputItem[i];
				if(output.possibleMultiOutput()) return 0; // output slot needs to be empty to decide on multi outputs
                ItemStack single = output.getSingle();

                if(stack == null) {
                    count = Math.min(count, single.getMaxStackSize() / single.stackSize);
                    continue; // always continue if output slot is free
                }

				if(single == null) return 0; // shouldn't be possible but better safe than sorry
				if(stack.getItem() != single.getItem()) return 0;
				if(stack.getItemDamage() != single.getItemDamage()) return 0;
                count = Math.min(count, (stack.getMaxStackSize() - stack.stackSize) / single.stackSize);

                if(count == 0) return 0;
			}
		}
		
		if(recipe.outputFluid != null) {
			for(int i = 0; i < Math.min(recipe.outputFluid.length, outputTanks.length); i++) {
                count = Math.min(count, (outputTanks[i].getMaxFill() - outputTanks[i].getFill()) / recipe.outputFluid[i].fill);
                if(count == 0) return 0;
			}
		}

        return count;
	}
	
	public void process(GenericRecipe recipe, double speed, double power, int count) {
		
		this.battery.setPower(this.battery.getPower() - (power == 1 ? recipe.power : (long) (recipe.power * power)));
		double step = speed / recipe.duration; // now this should be able to do multiple times of recipe in one tick
		this.progress += step;
        int multi = Math.min((int)this.progress, count);
		
		if(multi > 0) {
			consumeInput(recipe, multi);
			produceItem(recipe, multi);
			
			if(this.canProcess(recipe, speed, power) > 0) this.progress -= multi;
			else this.progress = 0D;
		}
	}
	
	/** Part 1 of the process completion, uses up input */
	protected void consumeInput(GenericRecipe recipe, int multi) {
		
		if(recipe.inputItem != null) {
			for(int i = 0; i < Math.min(recipe.inputItem.length, inputSlots.length); i++) {
				slots[inputSlots[i]].stackSize -= multi * recipe.inputItem[i].stacksize;
				if(slots[inputSlots[i]].stackSize <= 0) slots[inputSlots[i]] = null;
			}
		}
		
		if(recipe.inputFluid != null) {
			for(int i = 0; i < Math.min(recipe.inputFluid.length, inputTanks.length); i++) {
				inputTanks[i].setFill(inputTanks[i].getFill() - multi * recipe.inputFluid[i].fill);
			}
		}
	}
	
	/** Part 2 of the process completion, generated output */
	protected void produceItem(GenericRecipe recipe, int multi) {
		
		if(recipe.outputItem != null) {
			for(int i = 0; i < Math.min(recipe.outputItem.length, outputSlots.length); i++) {
				ItemStack collapse = recipe.outputItem[i].collapse();
                if(collapse != null) collapse.stackSize *= multi;
				if(slots[outputSlots[i]] == null) {
					slots[outputSlots[i]] = collapse;
				} else {
					if(collapse != null) slots[outputSlots[i]].stackSize += collapse.stackSize; // we can do this because we've already established that the result slot is not null if it's a single output
				}
			}
		}
		
		if(recipe.outputFluid != null) {
			for(int i = 0; i < Math.min(recipe.outputFluid.length, outputTanks.length); i++) {
				outputTanks[i].setFill(outputTanks[i].getFill() + multi * recipe.outputFluid[i].fill);
			}
		}
		
		this.markDirty = true;
	}

	public GenericRecipe getRecipe() {
		return (GenericRecipe) getRecipeSet().recipeNameMap.get(this.recipe);
	}
	
	public abstract GenericRecipes getRecipeSet();
	
	public void update(double speed, double power, boolean extraCondition, ItemStack blueprint) {
		GenericRecipe recipe = getRecipe();
		
		if(recipe != null && recipe.isPooled() && !recipe.isPartOfPool(ItemBlueprints.grabPool(blueprint))) {
			this.didProcess = false;
			this.progress = 0F;
			this.recipe = "null";
			return;
		}
		
		this.setupTanks(recipe);

		this.didProcess = false;
		this.markDirty = false;

        int count = this.canProcess(recipe, speed, power); //find multi using canProcess method
		if(extraCondition && count > 0) {
			this.process(recipe, speed, power, count);
			this.didProcess = true;
		} else {
			this.progress = 0F;
		}
	}
	
	/** For item IO, instead of the TE doing all the work it only has to handle non-recipe stuff, the module does the rest */
	public boolean isItemValid(int slot, ItemStack stack) {
		GenericRecipe recipe = getRecipe();
		if(recipe == null) return false;
		if(recipe.inputItem == null) return false;
		
		for(int i = 0; i < Math.min(inputSlots.length, recipe.inputItem.length); i++) {
			if(inputSlots[i] == slot && recipe.inputItem[i].matchesRecipe(stack, true)) return true;
		}
		
		if(recipe.autoSwitchGroup != null) {
			List<GenericRecipe> recipes = (List<GenericRecipe>) this.getRecipeSet().autoSwitchGroups.get(recipe.autoSwitchGroup); // why the FUCK does this need a cast
			if(recipes != null) for(GenericRecipe newRec : recipes) {
				if(newRec.inputItem == null) continue;
				if(inputSlots.length > 0 && inputSlots[0] == slot && newRec.inputItem[0].matchesRecipe(stack, true)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	/** Returns true if the supplied slot is occupied with an item that does not match the recipe */
	public boolean isSlotClogged(int slot) {
		boolean isSlotValid = false;
		for(int i : inputSlots) if(i == slot) isSlotValid = true;
		if(!isSlotValid) return false;
		ItemStack stack = slots[slot];
		if(stack == null) return false;
		return !isItemValid(slot, stack); // we need to use this because it also handles autoswitch correctly, otherwise autoswitch items may be ejected instantly
	}
	
	public void serialize(ByteBuf buf) {
		buf.writeDouble(progress);
		ByteBufUtils.writeUTF8String(buf, recipe);
	}
	
	public void deserialize(ByteBuf buf) {
		this.progress = buf.readDouble();
		this.recipe = ByteBufUtils.readUTF8String(buf);
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		this.progress = nbt.getDouble("progress" + index);
		this.recipe = nbt.getString("recipe" + index);
	}
	
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setDouble("progress" + index, progress);
		nbt.setString("recipe" + index, recipe);
	}
}
