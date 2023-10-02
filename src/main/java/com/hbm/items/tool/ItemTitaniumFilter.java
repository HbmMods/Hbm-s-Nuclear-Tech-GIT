package com.hbm.items.tool;

import java.util.List;

import com.hbm.items.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemTitaniumFilter extends Item {
	
	private int dura;

	public ItemTitaniumFilter(int dura) {
		this.dura = dura;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		if(this == ModItems.titanium_filter) {
			list.add("[Needed for Watz Reaction]");
			list.add((getDura(itemstack) / 20) + "/" + (dura / 20));
		}
	}

	public boolean showDurabilityBar(ItemStack stack) {
		return getDurabilityForDisplay(stack) > 0;
	}

	public static int getDura(ItemStack stack) {

		if(stack.stackTagCompound == null)
			return ((ItemTitaniumFilter) stack.getItem()).dura;

		return stack.stackTagCompound.getInteger("dura");
	}

	public static void setDura(ItemStack stack, int dura) {

		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();

		stack.stackTagCompound.setInteger("dura", dura);
	}

	public double getDurabilityForDisplay(ItemStack stack) {
		return 1D - (double) getDura(stack) / (double) dura;
	}
}
