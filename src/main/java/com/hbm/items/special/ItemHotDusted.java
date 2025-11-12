package com.hbm.items.special;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemHotDusted extends ItemHot {

	public ItemHotDusted(int heat) {
		super(heat);
		this.setHasSubtypes(true);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		list.add("Forged " + stack.getItemDamage() + " time(s)");
	}
	
	public static int getMaxHeat(ItemStack stack) {
		return heat - stack.getItemDamage() * 10;
	}
}
