package com.hbm.util;

import java.util.List;

import com.hbm.inventory.AnvilRecipes.AnvilOutput;
import com.hbm.inventory.RecipesCommon.AStack;

import net.minecraft.entity.player.EntityPlayer;
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
	
	/**
	 * Checks if a player has matching item stacks in his inventory and removes them if so desired
	 * @param player
	 * @param stacks the AStacks (comparable or ore-dicted)
	 * @param shouldRemove whether it should just return true or false or if a successful check should also remove all the items
	 * @return whether the player has the required item stacks or not
	 */
	public static boolean doesPlayerHaveAStacks(EntityPlayer player, List<AStack> stacks, boolean shouldRemove) {
		
		ItemStack[] original = player.inventory.mainInventory;
		ItemStack[] inventory = new ItemStack[original.length];
		AStack[] input = new AStack[stacks.size()];
		
		//first we copy the inputs into an array because 1. it's easier to deal with and 2. we can dick around with the stack sized with no repercussions
		for(int i = 0; i < input.length; i++) {
			input[i] = stacks.get(i).copy();
		}
		
		//then we copy the inventory so we can dick around with it as well without making actual modifications to the player's inventory
		for(int i = 0; i < original.length; i++) {
			if(original[i] != null) {
				inventory[i] = original[i].copy();
			}
		}
		
		//now we go through every ingredient...
		for(int i = 0; i < input.length; i++) {
			
			AStack stack = input[i];
			
			//...and compare each ingredient to every stack in the inventory
			for(int j = 0; j < inventory.length; j++) {
				
				ItemStack inv = inventory[j];
				
				//we check if it matches but ignore stack size for now
				if(stack.matchesRecipe(inv, true)) {
					//and NOW we care about the stack size
					int size = Math.min(stack.stacksize, inv.stackSize);
					stack.stacksize -= size;
					inv.stackSize -= size;
					
					//spent stacks are removed from the equation so that we don't cross ourselves later on
					if(stack.stacksize <= 0) {
						input[i] = null;
						break;
					}
					
					if(inv.stackSize <= 0) {
						inventory[j] = null;
						System.out.println("da yis");
					}
				}
			}
		}
		
		for(AStack stack : input) {
			if(stack != null) {
				return false;
			}
		}
		
		if(shouldRemove) {
			for(int i = 0; i < original.length; i++) {
				
				if(inventory[i] != null && inventory[i].stackSize <= 0)
					original[i] = null;
				else
					original[i] = inventory[i];
			}
		}
		
		return true;
	}
	
	public static void giveChanceStacksToPlayer(EntityPlayer player, List<AnvilOutput> stacks) {
		
		for(AnvilOutput out : stacks) {
			if(out.chance == 1.0F || player.getRNG().nextFloat() < out.chance) {
				if(!player.inventory.addItemStackToInventory(out.stack.copy())) {
					player.dropPlayerItemWithRandomChoice(out.stack.copy(), false);
				}
			}
		}
	}
}
