package com.hbm.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumChatFormatting;

public class ItemStackUtil {
	
	public static ItemStack carefulCopy(ItemStack stack) {
		if(stack == null)
			return null;
		else
			return stack.copy();
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
}
