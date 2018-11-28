package com.hbm.items.tool;

import java.util.List;

import com.hbm.items.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemKeyPin extends Item {

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		if(getPins(itemstack) != 0)
			list.add("Pin configuration: " + getPins(itemstack));
		else
			list.add("Pins not set!");
		
		if(this == ModItems.key_fake) {

			list.add("");
			list.add("Pins can neither be changed, nor copied.");
		}
	}

	public static int getPins(ItemStack stack) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
			return 0;
		}
		return stack.stackTagCompound.getInteger("pins");
	}
	
	public static void setPins(ItemStack stack, int i) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
		}
		stack.stackTagCompound.setInteger("pins", i);
	}
	
	public boolean canTransfer() {
		return this != ModItems.key_fake;
	}
}
