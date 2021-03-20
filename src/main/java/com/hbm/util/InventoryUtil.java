package com.hbm.util;

import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

//'t was about time
public class InventoryUtil {

	/**
	 * Will attempt to cram a much of the given itemstack into the stack array as possible
	 * The rest will be returned
	 * @param inv the stack array, usually a TE's inventory
	 * @param start the starting index (inclusive)
	 * @param end the end index (inclusive)
	 * @param stack the stack to be added to the inventory
	 * @return the remainder of the stack that could not have been added, can return null
	 */
	public static ItemStack tryAddItemToInventory(ItemStack[] inv, int start, int end, ItemStack stack) {
		
		ItemStack rem = tryAddItemToExistingStack(inv, start, end, stack);
		
		if(rem == null)
			return null;
		
		boolean didAdd = tryAddItemToNewSlot(inv, start, end, rem);
		
		if(didAdd)
			return null;
		else
			return rem;
	}
	
	/**
	 * Functionally equal to tryAddItemToInventory, but will not try to create new stacks in empty slots
	 * @param inv
	 * @param start
	 * @param end
	 * @param stack
	 * @return
	 */
	public static ItemStack tryAddItemToExistingStack(ItemStack[] inv, int start, int end, ItemStack stack) {
		
		if(stack == null || stack.stackSize == 0)
			return null;
		
		for(int i = start; i <= end; i++) {

			if(doesStackDataMatch(inv[i], stack)) {
				
				int transfer = Math.min(stack.stackSize, inv[i].getMaxStackSize() - inv[i].stackSize);
				
				if(transfer > 0) {
					inv[i].stackSize += transfer;
					stack.stackSize -= transfer;
					
					if(stack.stackSize == 0)
						return null;
				}
			}
		}
		
		return stack;
	}
	
	/**
	 * Will place the stack in the first empty slot
	 * @param inv
	 * @param start
	 * @param end
	 * @param stack
	 * @return whether the stack could be added or not
	 */
	public static boolean tryAddItemToNewSlot(ItemStack[] inv, int start, int end, ItemStack stack) {
		
		if(stack == null || stack.stackSize == 0)
			return true;
		
		for(int i = start; i <= end; i++) {
			
			if(inv[i] == null) {
				inv[i] = stack;
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Much of the same but with an ISidedInventory instance instead of a slot array
	 * @param inv
	 * @param start
	 * @param end
	 * @param stack
	 * @return
	 */
	public static ItemStack tryAddItemToInventory(IInventory inv, int start, int end, ItemStack stack) {
		
		ItemStack rem = tryAddItemToExistingStack(inv, start, end, stack);
		
		if(rem == null)
			return null;
		
		boolean didAdd = tryAddItemToNewSlot(inv, start, end, rem);
		
		if(didAdd)
			return null;
		else
			return rem;
	}
	
	public static ItemStack tryAddItemToExistingStack(IInventory inv, int start, int end, ItemStack stack) {
		
		if(stack == null || stack.stackSize == 0)
			return null;
		
		for(int i = start; i <= end; i++) {

			if(doesStackDataMatch(inv.getStackInSlot(i), stack)) {
				
				int transfer = Math.min(stack.stackSize, inv.getStackInSlot(i).getMaxStackSize() - inv.getStackInSlot(i).stackSize);
				
				if(transfer > 0) {
					inv.getStackInSlot(i).stackSize += transfer;
					stack.stackSize -= transfer;
					
					if(stack.stackSize == 0)
						return null;
				}
			}
		}
		
		return stack;
	}
	
	public static boolean tryAddItemToNewSlot(IInventory inv, int start, int end, ItemStack stack) {
		
		if(stack == null || stack.stackSize == 0)
			return true;
		
		for(int i = start; i <= end; i++) {
			
			if(inv.getStackInSlot(i) == null) {
				inv.setInventorySlotContents(i, stack);
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Compares item, metadata and NBT data of two stacks. Also handles null values!
	 * @param stack1
	 * @param stack2
	 * @return
	 */
	public static boolean doesStackDataMatch(ItemStack stack1, ItemStack stack2) {
		
		if(stack1 == null && stack2 == null)
			return true;
		
		if(stack1 == null && stack2 != null)
			return false;
		
		if(stack1 != null && stack2 == null)
			return false;
		
		if(stack1.getItem() != stack2.getItem())
			return false;
		
		if(stack1.getItemDamage() != stack2.getItemDamage())
			return false;
		
		if(!stack1.hasTagCompound() && !stack2.hasTagCompound())
			return true;
		
		if(stack1.hasTagCompound() && !stack2.hasTagCompound())
			return false;
		
		if(!stack1.hasTagCompound() && stack2.hasTagCompound())
			return false;
		
		return stack1.getTagCompound().equals(stack2.getTagCompound());
	}
}
