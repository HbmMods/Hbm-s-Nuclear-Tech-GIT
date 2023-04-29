package com.hbm.items.tool;

import java.util.List;
import java.util.Random;

import com.hbm.handler.BossSpawnHandler;

import net.minecraft.entity.player.EntityPlayer;
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
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		list.add("Right click to summon a meteorite!");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

		stack.damageItem(1, player);

		if(!world.isRemote) {
			BossSpawnHandler.spawnMeteorAtPlayer(player, false, true);
			player.addChatMessage(new ChatComponentText("Watch your head!"));
		}

		world.playSoundAtEntity(player, "hbm:item.techBleep", 1.0F, 1.0F);
		player.swingItem();
		
		return stack;
	}
}
