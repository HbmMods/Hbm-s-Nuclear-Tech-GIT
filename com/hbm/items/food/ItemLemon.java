package com.hbm.items.food;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class ItemLemon extends ItemFood {

	public ItemLemon(int p_i45339_1_, float p_i45339_2_, boolean p_i45339_3_) {
		super(p_i45339_1_, p_i45339_2_, p_i45339_3_);
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Eh, good enough.");
	}

}
