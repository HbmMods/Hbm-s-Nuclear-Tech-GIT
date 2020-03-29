package com.hbm.items.tool;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemLeadBox extends Item {

	// Without this method, your inventory will NOT work!!!
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 1; // return any value greater than zero
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		//if(world.isRemote)
		//	player.openGui(MainRegistry.instance, ModItems.guiID_item_box, world, 0, 0, 0);
		
		return stack;
	}
}
