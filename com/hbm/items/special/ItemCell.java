package com.hbm.items.special;

import java.util.Random;

import com.hbm.blocks.bomb.BlockCrashedBomb;
import com.hbm.items.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCell extends Item {
	
	@Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
		if(world.getBlock(p_77648_4_, p_77648_5_, p_77648_6_) instanceof BlockCrashedBomb)
		{
			Random rand = new Random();
			int i = rand.nextInt(100);
			if(i == 0)
			{
	            if (!world.isRemote)
	            {
	            	((BlockCrashedBomb)world.getBlock(p_77648_4_, p_77648_5_, p_77648_6_)).explode(world, p_77648_4_, p_77648_5_, p_77648_6_);
	            }
			} else if(i < 90)
			{
	            //if (!world.isRemote)
	            {
	            	player.inventory.consumeInventoryItem(ModItems.cell_empty);

	            	if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.cell_antimatter)))
	            	{
	            		player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.cell_antimatter, 1, 0), false);
	            	}
	            }
			} else {
	            //if (!world.isRemote)
	            {
	            	player.inventory.consumeInventoryItem(ModItems.cell_empty);

	            	if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.cell_anti_schrabidium)))
	            	{
	            		player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.cell_anti_schrabidium, 1, 0), false);
	            	}
	            }
			}
			return true;
		}
		return false;
    }

}
