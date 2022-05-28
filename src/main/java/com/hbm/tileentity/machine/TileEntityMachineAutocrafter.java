package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.ItemStackUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;

public class TileEntityMachineAutocrafter extends TileEntityMachineBase {

	public static final String MODE_EXACT = "exact";
	public static final String MODE_WILDCARD = "wildcard";
	public String[] modes = new String[9];

	public TileEntityMachineAutocrafter() {
		super(21);
	}
	
	public void initPattern(ItemStack stack, int i) {
		
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

	@Override
	public String getName() {
		return "container.autocrafter";
	}
	
	protected InventoryCraftingAuto craftingInventory = new InventoryCraftingAuto(3, 3);

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.craftingInventory.loadIventory(slots, 10);
			ItemStack stack = CraftingManager.getInstance().findMatchingRecipe(this.craftingInventory, this.worldObj);
			
			if(stack != null) {
				slots[19] = stack.copy();
				
				for(int i = 10; i < 19; i++) {
					this.decrStackSize(i, 1);
				}
			}
		}
	}
	
	public void updateTemplateGrid() {

		this.craftingInventory.loadIventory(slots, 0);
		ItemStack temp = CraftingManager.getInstance().findMatchingRecipe(this.craftingInventory, this.worldObj);
		
		if(temp != null) {
			slots[9] = temp.copy();
		} else {
			slots[9] = null;
		}
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
			@Override
			public void onCraftMatrixChanged(IInventory inventory) { }
			@Override
			public boolean canInteractWith(EntityPlayer player) { return false; }
		}
	}
}
