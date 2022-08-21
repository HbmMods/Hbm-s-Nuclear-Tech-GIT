package com.hbm.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.oredict.OreDictionary;

public class ItemStackUtil {
	
	public static ItemStack carefulCopy(ItemStack stack) {
		if(stack == null)
			return null;
		else
			return stack.copy();
	}
	
	public static ItemStack carefulCopyWithSize(ItemStack stack, int size) {
		if(stack == null)
			return null;
		
		ItemStack copy = stack.copy();
		copy.stackSize = size;
		return copy;
	}
	
	/**
	 * Runs carefulCopy over the entire ItemStack array.
	 * @param array
	 * @return
	 */
	public static ItemStack[] carefulCopyArray(ItemStack[] array) {
		return carefulCopyArray(array, 0, array.length - 1);
	}
	
	/**
	 * Recreates the ItemStack array and only runs carefulCopy over the supplied range. All other fields remain null.
	 * @param array
	 * @param start
	 * @param end
	 * @return
	 */
	public static ItemStack[] carefulCopyArray(ItemStack[] array, int start, int end) {
		if(array == null)
			return null;
		
		ItemStack[] copy = new ItemStack[array.length];
		
		for(int i = start; i <= end; i++) {
			copy[i] = carefulCopy(array[i]);
		}
		
		return copy;
	}
	
	/**
	 * Creates a new array that only contains the copied range.
	 * @param array
	 * @param start
	 * @param end
	 * @return
	 */
	public static ItemStack[] carefulCopyArrayTruncate(ItemStack[] array, int start, int end) {
		if(array == null)
			return null;
		
		int length = end - start + 1;
		ItemStack[] copy = new ItemStack[length];
		
		for(int i = 0; i < length; i++) {
			copy[i] = carefulCopy(array[start + i]);
		}
		
		return copy;
	}

	/**
	 * UNSAFE! Will ignore all existing display tags and override them! In its current state, only fit for items we know don't have any display tags!
	 * Will, however, respect existing NBT tags
	 * @param stack
	 * @param lines
	 */
	public static void addTooltipToStack(ItemStack stack, String... lines) {
		
		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		
		NBTTagCompound display = new NBTTagCompound();
		NBTTagList lore = new NBTTagList();
		
		for(String line : lines) {
			lore.appendTag(new NBTTagString(EnumChatFormatting.RESET + "" + EnumChatFormatting.GRAY + line));
		}
		
		display.setTag("Lore", lore);
		stack.stackTagCompound.setTag("display", display);
	}
	
	public static void addStacksToNBT(ItemStack stack, ItemStack... stacks) {
		
		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		
		NBTTagList tags = new NBTTagList();

		for(int i = 0; i < stacks.length; i++) {
			if(stacks[i] != null) {
				NBTTagCompound slotNBT = new NBTTagCompound();
				slotNBT.setByte("slot", (byte) i);
				stacks[i].writeToNBT(slotNBT);
				tags.appendTag(slotNBT);
			}
		}
		stack.stackTagCompound.setTag("items", tags);
	}
	
	public static ItemStack[] readStacksFromNBT(ItemStack stack) {

		if(!stack.hasTagCompound())
			return null;

		NBTTagList list = stack.stackTagCompound.getTagList("items", 10);
		int count = list.tagCount();

		ItemStack[] stacks = new ItemStack[count];

		for(int i = 0; i < count; i++) {
			NBTTagCompound slotNBT = list.getCompoundTagAt(i);
			byte slot = slotNBT.getByte("slot");
			if(slot >= 0 && slot < stacks.length) {
				stacks[slot] = ItemStack.loadItemStackFromNBT(slotNBT);
			}
		}
		
		return stacks;
	}
	
	//private static HashMap<ComparableStack, List<String>> buffered = new HashMap();
	
	/**
	 * Returns a List<String> of all ore dict names for this stack. Stack cannot be null, list is empty when there are no ore dict entries.
	 * @param stack
	 * @return
	 */
	public static List<String> getOreDictNames(ItemStack stack) {
		
		/*ÜComparableStack comp = new ComparableStack(stack).makeSingular();
		
		List<String> buff = buffered.get(comp);
		
		if(buff != null)
			return buff;*/

		List<String> list = new ArrayList();
		
		int ids[] = OreDictionary.getOreIDs(stack);
		for(int i : ids) {
			list.add(OreDictionary.getOreName(i));
		}
		
		//buffered.put(comp, new ArrayList(list));
		return list;
	}
}
