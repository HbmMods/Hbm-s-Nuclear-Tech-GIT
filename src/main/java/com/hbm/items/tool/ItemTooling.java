package com.hbm.items.tool;

import api.hbm.block.IToolable;
import api.hbm.block.IToolable.ToolType;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemTooling extends Item {
	
	ToolType type;
	
	public ItemTooling(ToolType type) {
		this.type = type;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float fX, float fY, float fZ) {
		
		Block b = world.getBlock(x, y, z);
		
		if(b instanceof IToolable) {
			return ((IToolable)b).onScrew(world, player, x, y, z, side, fX, fY, fZ, this.type);
		}
		
		return false;
	}
}
