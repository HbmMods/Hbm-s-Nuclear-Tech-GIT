package com.hbm.items.tool;

import api.hbm.block.IScrewable;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemScrewdriver extends Item {

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float fX, float fY, float fZ) {
		
		Block b = world.getBlock(x, y, z);
		
		if(b instanceof IScrewable) {
			return ((IScrewable)b).onScrew(world, player, x, y, z, side, fX, fY, fZ);
		}
		
		return false;
	}
}
