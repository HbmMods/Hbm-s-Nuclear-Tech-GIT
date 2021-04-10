package com.hbm.items.bomb;

import java.util.List;

import com.hbm.items.special.ItemHazard;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemGadget extends ItemHazard {
	
	public ItemGadget(float radiation) {
		super(radiation);
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Used in:");
		list.add("The Gadget");
		super.addInformation(itemstack, player, list, bool);
	}

}
