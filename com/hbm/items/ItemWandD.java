package com.hbm.items;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemWandD extends Item {
	
	@Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
		Block b = world.getBlock(x, y, z);

		if(b == ModBlocks.ore_aluminium)
			MainRegistry.x++;
		if(b == ModBlocks.block_aluminium)
			MainRegistry.x--;
		if(b == ModBlocks.ore_beryllium)
			MainRegistry.y++;
		if(b == ModBlocks.block_beryllium)
			MainRegistry.y--;
		if(b == ModBlocks.ore_copper)
			MainRegistry.z++;
		if(b == ModBlocks.block_copper)
			MainRegistry.z--;
		
		return false;
    }

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(player.isSneaking())
		{
			if(world.isRemote)
				player.addChatMessage(new ChatComponentText(MainRegistry.x + " " + MainRegistry.y + " " + MainRegistry.z));
		}
		
		return stack;
	}

}
