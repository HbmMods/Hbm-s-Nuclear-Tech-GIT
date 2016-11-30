package com.hbm.items.bomb;

import java.util.List;

import com.hbm.items.special.ItemRadioactive;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemManMike extends ItemRadioactive {
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Used in:");
		list.add("Fat Man");
		list.add("Ivy Mike");
		list.add("Tsar Bomba");
	}

}
