package com.hbm.module;

import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.recipes.ChemicalPlantRecipes;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.inventory.recipes.loader.GenericRecipes.IOutput;

import api.hbm.energymk2.IEnergyHandlerMK2;
import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Option 1: Make a base class with weird arbitrary overrides to define shit like slots for multi machines like the chemfac
 * Option 2: Make an easy to define module which can be used by whatever needs it, hypothetically allowing a mixed recipe machine.
 * In the hudson bay, you know how we do it.
 * @author hbm
 */
public class ModuleMachineChemplant {
	
	// setup
	public int index;
	public IEnergyHandlerMK2 battery;
	public ItemStack[] slots;
	public int[] inputSlots = new int[3];
	public int[] outputSlots = new int[3];
	public FluidTank[] inputTanks = new FluidTank[3];
	public FluidTank[] outputTanks = new FluidTank[3];
	// running vars
	public String recipe = "null";
	public float progress;
	// return signals
	public boolean didProcess = false;
	public boolean markDirty = false;

	public ModuleMachineChemplant(int index, IEnergyHandlerMK2 battery, ItemStack[] slots) {
		this.index = index;
		this.battery = battery;
		this.slots = slots;
	}
	
	/** Chances tank type and pressure based on recipe */
	public void setupTanks(GenericRecipe recipe) {
		if(recipe == null) return;
		if(recipe.inputFluid != null) for(int i = 0; i < Math.min(inputTanks.length, recipe.inputFluid.length); i++) inputTanks[i].conform(recipe.inputFluid[i]);
		if(recipe.outputFluid != null) for(int i = 0; i < Math.min(outputTanks.length, recipe.outputFluid.length); i++) outputTanks[i].conform(recipe.outputFluid[i]);
	}
	
	/** Expects the tanks to be set up correctly beforehand */
	public boolean canProcess(GenericRecipe recipe) {
		if(recipe == null) return false;
		if(battery.getPower() < recipe.power) return false;
		
		if(recipe.inputItem != null) {
			for(int i = 0; i < Math.min(recipe.inputItem.length, inputSlots.length); i++) {
				if(!recipe.inputItem[i].matchesRecipe(slots[inputSlots[i]], false)) return false;
			}
		}
		
		if(recipe.inputFluid != null) {
			for(int i = 0; i < Math.min(recipe.inputFluid.length, inputTanks.length); i++) {
				if(inputTanks[i].getFill() < recipe.inputFluid[i].fill) return false;
			}
		}
		
		if(recipe.outputItem != null) {
			for(int i = 0; i < Math.min(recipe.outputItem.length, outputSlots.length); i++) {
				ItemStack stack = slots[outputSlots[i]];
				if(stack == null) continue; // always continue if output slot is free
				IOutput output = recipe.outputItem[i];
				if(output.possibleMultiOutput()) return false; // output slot needs to be empty to decide on multi outputs
				ItemStack single = output.getSingle();
				if(single == null) return false; // shouldn't be possible but better safe than sorry
				if(stack.getItem() != single.getItem()) return false;
				if(stack.getItemDamage() != single.getItemDamage()) return false;
				if(stack.stackSize + single.stackSize > stack.getMaxStackSize()) return false;
			}
		}
		
		if(recipe.outputFluid != null) {
			for(int i = 0; i < Math.min(recipe.outputFluid.length, outputTanks.length); i++) {
				if(recipe.outputFluid[i].fill + outputTanks[i].getFill() > outputTanks[i].getMaxFill()) return false;
			}
		}
		
		return true;
	}
	
	public void process(GenericRecipe recipe) {
		
		this.battery.setPower(this.battery.getPower() - recipe.power);
		float step = Math.min(1F / recipe.duration, 1F); // can't do more than one recipe per tick, might look into that later
		this.progress += step;
		
		if(this.progress >= 1F) {
			this.progress -= 1F;
			
			if(recipe.inputItem != null) {
				for(int i = 0; i < Math.min(recipe.inputItem.length, inputSlots.length); i++) {
					slots[inputSlots[i]].stackSize -= recipe.inputItem[i].stacksize;
					if(slots[inputSlots[i]].stackSize <= 0) slots[inputSlots[i]] = null;
				}
			}
			
			if(recipe.inputFluid != null) {
				for(int i = 0; i < Math.min(recipe.inputFluid.length, inputTanks.length); i++) {
					inputTanks[i].setFill(inputTanks[i].getFill() - recipe.inputFluid[i].fill);
				}
			}
			
			if(recipe.outputItem != null) {
				for(int i = 0; i < Math.min(recipe.outputItem.length, outputSlots.length); i++) {
					ItemStack collapse = recipe.outputItem[i].collapse();
					if(slots[outputSlots[i]] == null) {
						slots[outputSlots[i]] = collapse;
					} else {
						slots[outputSlots[i]].stackSize += collapse.stackSize; // we can do this because we've already established that the result slot is not null if it's a single output
					}
				}
			}
			
			if(recipe.outputFluid != null) {
				for(int i = 0; i < Math.min(recipe.outputFluid.length, outputTanks.length); i++) {
					outputTanks[i].setFill(outputTanks[i].getFill() + recipe.outputFluid[i].fill);
				}
			}
			
			this.markDirty = true;
		}
	}
	
	public void update() {
		GenericRecipe recipe = ChemicalPlantRecipes.INSTANCE.recipeNameMap.get(this.recipe);
		this.setupTanks(recipe);

		this.didProcess = false;
		this.markDirty = false;
		
		if(this.canProcess(recipe)) {
			this.process(recipe);
			this.didProcess = true;
		} else {
			this.progress = 0F;
		}
	}
	
	/** For item IO, instead of the TE doing all the work it only has to handle non-recipe stuff, the module does the rest */
	public boolean isItemValid(int slot, ItemStack stack) {
		GenericRecipe recipe = ChemicalPlantRecipes.INSTANCE.recipeNameMap.get(this.recipe);
		if(recipe == null) return false;
		if(recipe.inputItem == null) return false;
		
		for(int i = 0; i < Math.min(inputSlots.length, recipe.inputItem.length); i++) {
			if(inputSlots[i] == slot && recipe.inputItem[i].matchesRecipe(stack, true)) return true;
		}
		
		return false;
	}

	public ModuleMachineChemplant itemInput(int a, int b, int c) { inputSlots[0] = a; inputSlots[1] = b; inputSlots[2] = c; return this; }
	public ModuleMachineChemplant itemOutput(int a, int b, int c) { outputSlots[0] = a; outputSlots[1] = b; outputSlots[2] = c; return this; }
	public ModuleMachineChemplant fluidInput(FluidTank a, FluidTank b, FluidTank c) { inputTanks[0] = a; inputTanks[1] = b; inputTanks[2] = c; return this; }
	public ModuleMachineChemplant fluidOutput(FluidTank a, FluidTank b, FluidTank c) { outputTanks[0] = a; outputTanks[1] = b; outputTanks[2] = c; return this; }
	
	public void serialize(ByteBuf buf) {
		buf.writeFloat(progress);
		ByteBufUtils.writeUTF8String(buf, recipe);
	}
	
	public void deserialize(ByteBuf buf) {
		this.progress = buf.readFloat();
		this.recipe = ByteBufUtils.readUTF8String(buf);
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		this.progress = nbt.getFloat("progress");
		this.recipe = nbt.getString("recipe");
	}
	
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setFloat("progress", progress);
		nbt.setString("recipe", recipe);
	}
}
