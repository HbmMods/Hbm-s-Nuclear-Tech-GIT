package com.hbm.items.food;

import java.util.List;

import com.hbm.items.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class ItemNugget extends ItemFood {

	public ItemNugget(int p_i45340_1_, boolean p_i45340_2_) {
		super(p_i45340_1_, p_i45340_2_);
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		if (this == ModItems.gun_moist_nugget) {
			list.add("A Moisn-Na...no wait, it's");
			list.add("just a moist nugget.");
		}
	}

}
