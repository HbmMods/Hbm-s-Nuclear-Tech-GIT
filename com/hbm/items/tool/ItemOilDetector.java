package com.hbm.items.tool;

import java.util.List;

import com.hbm.blocks.ModBlocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemOilDetector extends Item {

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Right click to scan for oil.");
		list.add("Scanner can only detect larger deposits!");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

		boolean oil = false;
		boolean direct = false;
		int x = (int)player.posX;
		int y = (int)player.posY;
		int z = (int)player.posZ;

		for(int i =  y + 15; i > 5; i--)
			if(world.getBlock(x, i, z) == ModBlocks.ore_oil)
				direct = true;
		for(int i =  y + 15; i > 5; i--)
			if(world.getBlock(x + 5, i, z) == ModBlocks.ore_oil)
				oil = true;
		for(int i =  y + 15; i > 5; i--)
			if(world.getBlock(x - 5, i, z) == ModBlocks.ore_oil)
				oil = true;
		for(int i =  y + 15; i > 5; i--)
			if(world.getBlock(x, i, z + 5) == ModBlocks.ore_oil)
				oil = true;
		for(int i =  y + 15; i > 5; i--)
			if(world.getBlock(x, i, z - 5) == ModBlocks.ore_oil)
				oil = true;
		
		if(direct)
			oil = true;
		
		if(world.isRemote)
		{
			if(oil) {
				player.addChatMessage(new ChatComponentText("Oil deposit detected!"));
				if(direct)
					player.addChatMessage(new ChatComponentText("Oil deposit directly below!"));
			} else {
				player.addChatMessage(new ChatComponentText("No oil detected."));
			}
		}

    	world.playSoundAtEntity(player, "hbm:item.techBleep", 1.0F, 1.0F);
		
		player.swingItem();
		
		return stack;
		
	}

}
