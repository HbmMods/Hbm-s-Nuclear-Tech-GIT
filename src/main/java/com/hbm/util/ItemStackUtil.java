package com.hbm.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumChatFormatting;

public class ItemStackUtil {

	/**
	 * UNSAFE! Will ignore all existing tags and override them! In its current state, only fit for items we know don't have any display tags!
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
}
