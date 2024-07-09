package com.hbm.items.weapon;

import java.util.List;

import com.hbm.handler.RocketStruct;
import com.hbm.items.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemCustomRocket extends Item {

	public static ItemStack build(RocketStruct rocket) {
		ItemStack stack = new ItemStack(ModItems.rocket_custom);

		return stack;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {

	}

}
