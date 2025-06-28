package com.hbm.util;

import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.recipes.anvil.AnvilRecipes.AnvilOutput;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityFurnaceBrick;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

//'t was about time
public class InventoryUtil {

	public static int[] masquerade(ISidedInventory sided, int side) {

		if(sided instanceof TileEntityFurnace) return new int[] {1, 0};
		if(sided instanceof TileEntityFurnaceBrick) return new int[] {1, 0, 3};

		return sided.getAccessibleSlotsFromSide(side);
	}

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

	public static ItemStack tryAddItemToInventory(ItemStack[] inv, ItemStack stack) {
		return tryAddItemToInventory(inv, 0, inv.length - 1, stack);
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

	public static boolean tryConsumeAStack(ItemStack[] inv, int start, int end, AStack stack) {

		if(stack == null)
			return true;

		AStack copy = stack.copy();

		for(int i = start; i <= end; i++) {
			ItemStack in = inv[i];

			if(stack.matchesRecipe(in, true)) {
				int size = Math.min(copy.stacksize, in.stackSize);

				in.stackSize -= size;
				copy.stacksize -= size;

				if(in.stackSize == 0)
					inv[i] = null;
				if(copy.stacksize == 0)
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

		if(stack1 == null && stack2 == null) return true;
		if(stack1 == null && stack2 != null) return false;
		if(stack1 != null && stack2 == null) return false;
		if(stack1.getItem() != stack2.getItem()) return false;
		if(stack1.getItemDamage() != stack2.getItemDamage()) return false;
		if(!stack1.hasTagCompound() && !stack2.hasTagCompound()) return true;
		if(stack1.hasTagCompound() && !stack2.hasTagCompound()) return false;
		if(!stack1.hasTagCompound() && stack2.hasTagCompound()) return false;

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
		boolean[] modified = new boolean[original.length];
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
					modified[j] = true;

					//spent stacks are removed from the equation so that we don't cross ourselves later on
					if(stack.stacksize <= 0) {
						input[i] = null;
						break;
					}

					if(inv.stackSize <= 0) {
						inventory[j] = null;
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

				if(inventory[i] != null && inventory[i].stackSize <= 0) {
					original[i] = null;
				} else {
					if(modified[i]) original[i] = inventory[i];
				}
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

	public static boolean hasOreDictMatches(EntityPlayer player, String dict, int count) {
		return countOreDictMatches(player, dict) >= count;
	}

	public static int countOreDictMatches(EntityPlayer player, String dict) {

		int count = 0;

		for(int i = 0; i < player.inventory.mainInventory.length; i++) {

			ItemStack stack = player.inventory.mainInventory[i];

			if(stack != null) {

				int[] ids = OreDictionary.getOreIDs(stack);

				for(int id : ids) {
					if(OreDictionary.getOreName(id).equals(dict)) {
						count += stack.stackSize;
						break;
					}
				}
			}
		}

		return count;
	}

	public static void consumeOreDictMatches(EntityPlayer player, String dict, int count) {

		for(int i = 0; i < player.inventory.mainInventory.length; i++) {

			ItemStack stack = player.inventory.mainInventory[i];

			if(stack != null) {

				int[] ids = OreDictionary.getOreIDs(stack);

				for(int id : ids) {
					if(OreDictionary.getOreName(id).equals(dict)) {

						int toConsume = Math.min(count, stack.stackSize);
						player.inventory.decrStackSize(i, toConsume);
						count -= toConsume;
						break;
					}
				}
			}
		}
	}

	/**
	 * Turns objects into 2D ItemStack arrays. Index 1: Ingredient slot, index 2: variation (ore dict)
	 * Handles:<br>
	 * <ul>
	 * <li>ItemStack</li>
	 * <li>ItemStack[]</li>
	 * <li>AStack</li>
	 * <li>AStack[]</li>
	 * </ul>
	 * @param o
	 * @return
	 */
	public static ItemStack[][] extractObject(Object o) {

		if(o instanceof ItemStack) {
			ItemStack[][] stacks = new ItemStack[1][1];
			stacks[0][0] = ((ItemStack)o).copy();
			return stacks;
		}

		if(o instanceof ItemStack[]) {
			ItemStack[] ingredients = (ItemStack[]) o;
			ItemStack[][] stacks = new ItemStack[ingredients.length][1];
			for(int i = 0; i < ingredients.length; i++) {
				stacks[i][0] = ingredients[i];
			}
			return stacks;
		}

		if(o instanceof ItemStack[][]) {
			return (ItemStack[][]) o;
		}

		if(o instanceof AStack) {
			AStack astack = (AStack) o;
			ItemStack[] ext = astack.extractForNEI().toArray(new ItemStack[0]);
			ItemStack[][] stacks = new ItemStack[1][0];
			stacks[0] = ext; //untested, do java arrays allow that? the capacity set is 0 after all
			return stacks;
		}

		if(o instanceof AStack[]) {
			AStack[] ingredients = (AStack[]) o;
			ItemStack[][] stacks = new ItemStack[ingredients.length][0];

			for(int i = 0; i < ingredients.length; i++) {
				stacks[i] = ingredients[i].extractForNEI().toArray(new ItemStack[0]);
			}

			return stacks;
		}

		/* in emergency situations with mixed types where AStacks coexist with NBT dependent ItemStacks, such as for fluid icons */
		if(o instanceof Object[]) {
			Object[] ingredients = (Object[]) o;
			ItemStack[][] stacks = new ItemStack[ingredients.length][0];

			for(int i = 0; i < ingredients.length; i++) {
				Object ingredient = ingredients[i];

				if(ingredient instanceof AStack) {
					stacks[i] = ((AStack) ingredient).extractForNEI().toArray(new ItemStack[0]);
				}
				if(ingredient instanceof ItemStack) {
					stacks[i] = new ItemStack[1];
					stacks[i][0] = ((ItemStack) ingredient).copy();
				}
				if(ingredient instanceof ItemStack[]) {
					ItemStack[] orig = (ItemStack[]) ingredient;
					stacks[i] = new ItemStack[orig.length];
					for(int j = 0; j < orig.length; j++) stacks[i][j] = orig[j].copy();
				}
			}

			return stacks;
		}

		MainRegistry.logger.warn("InventoryUtil: extractObject failed for type " + o);
		return new ItemStack[0][0];
	}

	public static boolean doesArrayHaveIngredients(ItemStack[] array, int start, int end, AStack... ingredients) {
		ItemStack[] copy = ItemStackUtil.carefulCopyArrayTruncate(array, start, end);

		AStack[] req = new AStack[ingredients.length];
		for(int i = 0; i < req.length; i++) {
			req[i] = ingredients[i] == null ? null : ingredients[i].copy();
		}

		for(AStack ingredient : req) {

			if(ingredient == null)
				continue;

			for(ItemStack input : copy) {

				if(input == null)
					continue;

				if(ingredient.matchesRecipe(input, true)) {
					int size = Math.min(input.stackSize, ingredient.stacksize);

					ingredient.stacksize -= size;
					input.stackSize -= size;

					if(ingredient.stacksize == 0)
						break;
				}
			}

			//we have iterated over the entire input array and removed all matching entries, yet the ingredient is still not exhausted, meaning the input wasn't enough
			if(ingredient.stacksize > 0)
				return false;
		}

		return true;
	}

	public static boolean doesArrayHaveSpace(ItemStack[] array, int start, int end, ItemStack[] items) {
		ItemStack[] copy = ItemStackUtil.carefulCopyArrayTruncate(array, start, end);

		for(ItemStack item : items) {

			if(item == null)
				continue;

			ItemStack remainder = tryAddItemToInventory(copy, item.copy());
			if(remainder != null) {
				return false;
			}
		}

		return true;
	}

	/**
	 * A fixed re-implementation of the original Container.mergeItemStack that repects stack size and slot restrictions.
	 * @param slots
	 * @param stack
	 * @param start
	 * @param end
	 * @param reverse
	 * @return
	 */
	public static boolean mergeItemStack(List<Slot> slots, ItemStack stack, int start, int end, boolean reverse) {

		boolean success = false;
		int index = start;

		if(reverse) {
			index = end - 1;
		}

		Slot slot;
		ItemStack current;

		if(stack.isStackable()) {

			while(stack.stackSize > 0 && (!reverse && index < end || reverse && index >= start)) {
				slot = slots.get(index);
				current = slot.getStack();

				if(current != null) {
					int max = Math.min(stack.getMaxStackSize(), slot.getSlotStackLimit());
					int toRemove = Math.min(stack.stackSize, max);

					if(slot.isItemValid(ItemStackUtil.carefulCopyWithSize(stack, toRemove)) && current.getItem() == stack.getItem() &&
							(!stack.getHasSubtypes() || stack.getItemDamage() == current.getItemDamage()) && ItemStack.areItemStackTagsEqual(stack, current)) {

						int currentSize = current.stackSize + stack.stackSize;
						if(currentSize <= max) {
							stack.stackSize = 0;
							current.stackSize = currentSize;
							slot.putStack(current);
							success = true;
						} else if(current.stackSize < max) {
							stack.stackSize -= max - current.stackSize;
							current.stackSize = max;
							slot.putStack(current);
							success = true;
						}
					}
				}

				if(reverse) {
					--index;
				} else {
					++index;
				}
			}
		}

		if(stack.stackSize > 0) {
			if(reverse) {
				index = end - 1;
			} else {
				index = start;
			}

			while((!reverse && index < end || reverse && index >= start) && stack.stackSize > 0) {
				slot = slots.get(index);
				current = slot.getStack();

				if(current == null) {

					int max = Math.min(stack.getMaxStackSize(), slot.getSlotStackLimit());
					int toRemove = Math.min(stack.stackSize, max);

					if(slot.isItemValid(ItemStackUtil.carefulCopyWithSize(stack, toRemove))) {
						current = stack.splitStack(toRemove);
						slot.putStack(current);
						success = true;
					}
				}

				if(reverse) {
					--index;
				} else {
					++index;
				}
			}
		}

		return success;
	}

	public static int countAStackMatches(ItemStack[] inventory, AStack stack, boolean ignoreSize) {
		int count = 0;

		for(ItemStack itemStack : inventory) {
			if(itemStack != null) {
				if(stack.matchesRecipe(itemStack, true)) {
					count += itemStack.stackSize;
				}
			}
		}
		return ignoreSize ? count : count / stack.stacksize;
	}

	public static int countAStackMatches(EntityPlayer player, AStack stack, boolean ignoreSize) {
		return countAStackMatches(player.inventory.mainInventory, stack, ignoreSize);
	}

	public static boolean doesPlayerHaveAStack(EntityPlayer player, AStack stack, boolean shouldRemove, boolean ignoreSize) {
		return doesInventoryHaveAStack(player.inventory.mainInventory, stack, shouldRemove, ignoreSize);
	}

	public static boolean doesInventoryHaveAStack(ItemStack[] inventory, AStack stack, boolean shouldRemove, boolean ignoreSize) {
		final int totalMatches;
		int totalStacks = 0;
		for(ItemStack itemStack : inventory) {
			if(itemStack != null && stack.matchesRecipe(itemStack, ignoreSize))
				totalStacks += itemStack.stackSize;
			if(!shouldRemove && ignoreSize && totalStacks > 0)
				return true;
		}

		totalMatches = ignoreSize ? totalStacks : totalStacks / stack.stacksize;

		if(shouldRemove) {
			int consumedStacks = 0, requiredStacks = ignoreSize ? 1 : stack.stacksize;
			for(ItemStack itemStack : inventory) {
				if(consumedStacks > requiredStacks)
					break;
				if(itemStack != null && stack.matchesRecipe(itemStack, true)) {
					int toConsume = Math.min(itemStack.stackSize, requiredStacks - consumedStacks);
					itemStack.stackSize -= toConsume;
					consumedStacks += toConsume;
				}
			}
		}

		return totalMatches > 0;
	}
}
