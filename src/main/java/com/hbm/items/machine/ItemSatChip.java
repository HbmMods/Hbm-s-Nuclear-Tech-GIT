package com.hbm.items.machine;

import java.util.List;

import com.hbm.items.ISatChip;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemSatChip extends Item implements ISatChip {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		list.add("Satellite frequency: " + getFreq(itemstack));
	}

}
