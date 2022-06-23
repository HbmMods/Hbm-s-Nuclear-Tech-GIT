package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.ItemStackUtil;

import api.hbm.energy.IEnergyUser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityMachineAutocrafter extends TileEntityMachineBase implements IEnergyUser {

	public static final String MODE_EXACT = "exact";
	public static final String MODE_WILDCARD = "wildcard";
	public String[] modes = new String[9];
	
	public List<IRecipe> recipes = new ArrayList();
	public int recipeIndex;
	public int recipeCount;

	public TileEntityMachineAutocrafter() {
		super(21);
	}
	
	public void initPattern(ItemStack stack, int i) {
		
		if(worldObj.isRemote) return;
		
		if(stack == null) {
			modes[i] = null;
			return;
		}
		
		List<String> names = ItemStackUtil.getOreDictNames(stack);

		if(iterateAndCheck(names, i ,"ingot")) return;
		if(iterateAndCheck(names, i ,"block")) return;
		if(iterateAndCheck(names, i ,"dust")) return;
		if(iterateAndCheck(names, i ,"nugget")) return;
		if(iterateAndCheck(names, i ,"plate")) return;
		
		if(stack.getHasSubtypes()) {
			modes[i] = MODE_EXACT;
		} else {
			modes[i] = MODE_WILDCARD;
		}
	}
	
	private boolean iterateAndCheck(List<String> names, int i, String prefix) {
		
		for(String s : names) {
			if(s.startsWith(prefix)) {
				modes[i] = s;
				return true;
			}
		}
		
		return false;
	}
	
	public void nextMode(int i) {
		
		if(worldObj.isRemote) return;
		
		ItemStack stack = slots[i];
		
		if(stack == null) {
			modes[i] = null;
			return;
		}
		
		if(modes[i] == null) {
			modes[i] = MODE_EXACT;
		} else if(MODE_EXACT.equals(modes[i])) {
			modes[i] = MODE_WILDCARD;
		} else if(MODE_WILDCARD.equals(modes[i])) {
			
			List<String> names = ItemStackUtil.getOreDictNames(stack);
			
			if(names.isEmpty()) {
				modes[i] = MODE_EXACT;
			} else {
				modes[i] = names.get(0);
			}
		} else {
			
			List<String> names = ItemStackUtil.getOreDictNames(stack);
			
			if(names.size() < 2 || modes[i].equals(names.get(names.size() - 1))) {
				modes[i] = MODE_EXACT;
			} else {
				
				for(int j = 0; j < names.size() - 1; j++) {
					
					if(modes[i].equals(names.get(j))) {
						modes[i] = names.get(j + 1);
						return;
					}
				}
			}
		}
	}
	
	public void nextTemplate() {
		
		if(worldObj.isRemote) return;
		
		this.recipeIndex++;
		
		if(this.recipeIndex >= this.recipes.size())
			this.recipeIndex = 0;
		
		if(!this.recipes.isEmpty()) {
			slots[9] = this.recipes.get(this.recipeIndex).getCraftingResult(getTemplateGrid());
		} else {
			slots[9] = null;
		}
	}

	@Override
	public String getName() {
		return "container.autocrafter";
	}
	
	protected InventoryCraftingAuto craftingInventory = new InventoryCraftingAuto(3, 3);

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.power = Library.chargeTEFromItems(slots, 20, power, maxPower);
			this.updateStandardConnections(worldObj, this);
			
			if(!this.recipes.isEmpty() && this.power >= this.consumption) {
				IRecipe recipe = this.recipes.get(recipeIndex);
				
				if(recipe.matches(this.getRecipeGrid(), this.worldObj)) {
					ItemStack stack = recipe.getCraftingResult(this.getRecipeGrid());
					
					if(stack != null) {
						
						boolean didCraft = false;
						
						if(slots[19] == null) {
							slots[19] = stack.copy();
							didCraft = true;
						} else if(slots[19].isItemEqual(stack) && ItemStack.areItemStackTagsEqual(stack, slots[19]) && slots[19].stackSize + stack.stackSize <= slots[19].getMaxStackSize()) {
							slots[19].stackSize += stack.stackSize;
							didCraft = true;
						}
						
						if(didCraft) {
							for(int i = 10; i < 19; i++) {
								
								ItemStack ingredient = this.getStackInSlot(i);

								if(ingredient != null) {
									this.decrStackSize(i, 1);

									if(ingredient.getItem().hasContainerItem(ingredient)) {
										ItemStack container = ingredient.getItem().getContainerItem(ingredient);

										if(container != null && container.isItemStackDamageable() && container.getItemDamage() > container.getMaxDamage()) {
											continue;
										}

										this.setInventorySlotContents(i, container);
									}
								}
							}
							
							this.power -= this.consumption;
						}
					}
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			for(int i = 0; i < 9; i++) {
				if(modes[i] != null) {
					data.setString("mode" + i, modes[i]);
				}
			}
			data.setInteger("count", this.recipeCount);
			data.setInteger("rec", this.recipeIndex);
			this.networkPack(data, 15);
		}
	}
	
	@Override
	public void networkUnpack(NBTTagCompound data) {
		this.power = data.getLong("power");
		
		modes = new String[9];
		for(int i = 0; i < 9; i++) {
			if(data.hasKey("mode" + i)) {
				modes[i] = data.getString("mode" + i);
			}
		}
		this.recipeCount = data.getInteger("count");
		this.recipeIndex = data.getInteger("rec");
	}
	
	public void updateTemplateGrid() {

		this.recipes = getMatchingRecipes(this.getTemplateGrid());
		this.recipeCount = recipes.size();
		this.recipeIndex = 0;
		
		if(!this.recipes.isEmpty()) {
			slots[9] = this.recipes.get(this.recipeIndex).getCraftingResult(getTemplateGrid());
		} else {
			slots[9] = null;
		}
	}
	
	public List<IRecipe> getMatchingRecipes(InventoryCrafting grid) {
		List<IRecipe> recipes = new ArrayList();
		
		for(Object o : CraftingManager.getInstance().getRecipeList()) {
			IRecipe recipe = (IRecipe) o;
			
			if(recipe.matches(grid, worldObj)) {
				recipes.add(recipe);
			}
		}
		
		return recipes;
	}

	public int[] access = new int[] { 10, 11, 12, 13, 14, 15, 16, 17, 18, 19 };
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return access;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack stack, int j) {
		if(i == 19)
			return true;
		
		if(i > 9 && i < 19) {
			ItemStack filter = slots[i - 10];
			String mode = modes[i - 10];
			
			if(filter == null || mode == null || mode.isEmpty()) return true;
			
			if(isValidForFilter(filter, mode, stack)) {
				return false;
			}
			
			return true;
		}
		
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		
		//only allow insertion for the nine recipe slots
		if(slot < 10 || slot > 18)
			return false;
		
		//is the filter at this space null? no input.
		if(slots[slot - 10] == null)
			return false;
		
		//let's find all slots that this item could potentially go in
		List<Integer> validSlots = new ArrayList();
		for(int i = 0; i < 9; i++) {
			ItemStack filter = slots[i];
			String mode = modes[i];
			
			if(filter == null || mode == null || mode.isEmpty()) continue;
			
			if(isValidForFilter(filter, mode, stack)) {
				validSlots.add(i + 10);
				
				//if the current slot is valid and has no item in it, shortcut to true [*]
				if(i + 10 == slot && slots[slot] == null) {
					return true;
				}
			}
		}
		
		//if the slot we are looking at isn't valid, skip
		if(!validSlots.contains(slot)) {
			return false;
		}
		
		//assumption from [*]: the slot has to be valid by now, and it cannot be null
		int size = slots[slot].stackSize;
		
		//now we decide based on stacksize, woohoo
		for(Integer i : validSlots) {
			ItemStack valid = slots[i];
			
			if(valid == null) return false; //null? since slots[slot] is not null by now, this other slot needs the item more
			if(!(valid.isItemEqual(stack) && ItemStack.areItemStackTagsEqual(valid, stack))) continue; //different item anyway? out with it
			
			//if there is another slot that actually does need the same item more, cancel
			if(valid.stackSize < size)
				return false;
		}
		
		//prevent items with containers from stacking
		if(stack.getItem().hasContainerItem(stack))
			return false;
		
		//by now, we either already have filled the slot (if valid by filter and null) or weeded out all other options, which means it is good to go
		return true;
	}
	
	private boolean isValidForFilter(ItemStack filter, String mode, ItemStack input) {
		
		switch(mode) {
		case MODE_EXACT: return input.isItemEqual(filter) && ItemStack.areItemStackTagsEqual(input, filter);
		case MODE_WILDCARD: return input.getItem() == filter.getItem() && ItemStack.areItemStackTagsEqual(input, filter);
		default:
			List<String> keys = ItemStackUtil.getOreDictNames(input);
			return keys.contains(mode);
		}
	}
	
	public InventoryCrafting getTemplateGrid() {
		this.craftingInventory.loadIventory(slots, 0);
		return this.craftingInventory;
	}
	
	public InventoryCrafting getRecipeGrid() {
		this.craftingInventory.loadIventory(slots, 10);
		return this.craftingInventory;
	}
	
	public static class InventoryCraftingAuto extends InventoryCrafting {

		public InventoryCraftingAuto(int width, int height) {
			super(new ContainerBlank() /* "can't be null boo hoo" */, width, height);
		}
		
		public void loadIventory(ItemStack[] slots, int start) {
			
			for(int i = 0; i < this.getSizeInventory(); i++) {
				this.setInventorySlotContents(i, slots[start + i]);
			}
		}
		
		public static class ContainerBlank extends Container {
			@Override public void onCraftMatrixChanged(IInventory inventory) { }
			@Override public boolean canInteractWith(EntityPlayer player) { return false; }
		}
	}
	
	public static int consumption = 100;
	public static long maxPower = consumption * 100;
	public long power;

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.power = nbt.getLong("power");
		
		for(int i = 0; i < 9; i++) {
			if(nbt.hasKey("mode" + i)) {
				modes[i] = nbt.getString("mode" + i);
			}
		}
		
		this.recipes = getMatchingRecipes(this.getTemplateGrid());
		this.recipeCount = recipes.size();
		this.recipeIndex = nbt.getInteger("rec");
		
		if(!this.recipes.isEmpty()) {
			slots[9] = this.recipes.get(this.recipeIndex).getCraftingResult(getTemplateGrid());
		} else {
			slots[9] = null;
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", power);
		
		for(int i = 0; i < 9; i++) {
			if(modes[i] != null) {
				nbt.setString("mode" + i, modes[i]);
			}
		}
		
		nbt.setInteger("rec", this.recipeIndex);
	}
}
