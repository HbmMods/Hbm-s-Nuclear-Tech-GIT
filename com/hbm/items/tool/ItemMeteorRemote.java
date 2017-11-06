package com.hbm.items.tool;

import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.projectile.EntityMeteor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemMeteorRemote extends Item {
	
	Random rand = new Random();
	
	public ItemMeteorRemote() {
		this.canRepair = false;
		this.setMaxDamage(2);
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Right click to summon a meteorite!");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		stack.damageItem(1, player);

		if(!world.isRemote)
		{
			EntityMeteor meteor = new EntityMeteor(world);
			meteor.posX = player.posX + world.rand.nextInt(201) - 100;
			meteor.posY = 384;
			meteor.posZ = player.posZ + world.rand.nextInt(201) - 100;
			meteor.motionX = world.rand.nextDouble() - 0.5;
			meteor.motionY = -2.5;
			meteor.motionZ = world.rand.nextDouble() - 0.5;
			world.spawnEntityInWorld(meteor);
		}
		if(world.isRemote)
		{
			player.addChatMessage(new ChatComponentText("Watch your head!"));
		}

    	world.playSoundAtEntity(player, "hbm:item.techBleep", 1.0F, 1.0F);
		
		player.swingItem();
		
		return stack;
		
	}
}
