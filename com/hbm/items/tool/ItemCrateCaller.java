package com.hbm.items.tool;

import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IBomb;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemCrateCaller extends Item {
	
	Random rand = new Random();
	
	public ItemCrateCaller() {
		this.canRepair = false;
		this.setMaxDamage(4);
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Right click to request supply drop!");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		stack.damageItem(1, player);

		int x = rand.nextInt(31) - 15;
		int z = rand.nextInt(31) - 15;

		if(!world.isRemote)
		{
			if(world.getBlock((int)player.posX + x, 255, (int)player.posZ + z) == Blocks.air)
				world.setBlock((int)player.posX + x, 255, (int)player.posZ + z, ModBlocks.crate);
		}
		if(world.isRemote)
		{
			player.addChatMessage(new ChatComponentText("Called in supply drop!"));
		}

    	world.playSoundAtEntity(player, "hbm:item.techBleep", 1.0F, 1.0F);
		
		player.swingItem();
		
		return stack;
		
	}

}
