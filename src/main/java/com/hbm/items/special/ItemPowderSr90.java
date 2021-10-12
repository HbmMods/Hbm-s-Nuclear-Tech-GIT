package com.hbm.items.special;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemPowderSr90 extends Item {
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		list.add("Every cloud has a silver lining...");
		list.add("Except nuclear mushroom clouds,");
		list.add("which have a lining of Strontium-90,");
		list.add("Caesium-137 and other radioactive");
		list.add("Isotopes.");
	}
}