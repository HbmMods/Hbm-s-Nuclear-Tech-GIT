package com.hbm.items.bomb;

import java.util.List;

import com.hbm.items.special.ItemRadioactive;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemMike extends ItemRadioactive {
	
	public ItemMike(float radiation) {
		super(radiation);
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Used in:");
		list.add("Ivy Mike");
	}

}
