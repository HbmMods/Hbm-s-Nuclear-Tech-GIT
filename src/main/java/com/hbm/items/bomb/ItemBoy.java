package com.hbm.items.bomb;

import java.util.List;

import com.hbm.items.special.ItemHazard;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemBoy extends ItemHazard {
	
	public ItemBoy(float radiation) {
		super(radiation);
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Used in:");
		list.add("Little Boy");
		super.addInformation(itemstack, player, list, bool);
	}

}
