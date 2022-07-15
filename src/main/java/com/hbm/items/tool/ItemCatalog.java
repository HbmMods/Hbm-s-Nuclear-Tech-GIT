package com.hbm.items.tool;

import java.util.List;

import com.hbm.handler.EnumGUI;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCatalog extends Item {

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(world.isRemote)
			player.openGui(MainRegistry.instance, EnumGUI.ITEM_BOBMAZON.ordinal(), world, 0, 0, 0);
		
		return stack;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		
		if(this == ModItems.bobmazon_hidden) {
			list.add("For a guide on how to obtain this, visit https://bit.ly/2TPgcqT");
			list.add("No tricks this time, i promise.");
		}
	}
}
