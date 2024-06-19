package com.hbm.items.food;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBDCL extends Item {

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 40;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.drink;
	}
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		return stack;
	}

	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {

		if(!player.capabilities.isCreativeMode) {
			--stack.stackSize;
		}
		return stack;
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
		
		if(count % 5 == 0 && count >= 10) {
			player.playSound("hbm:player.gulp", 1F, 1F);
		}
		
		if(count == 1) {
			this.onEaten(stack, player.worldObj, player);
			player.clearItemInUse();
			player.itemInUseCount = 10;
			player.playSound("hbm:player.groan", 1F, 1F);
			return;
		}
		
		if(count <= 24 && count % 4 == 0) {
			player.itemInUseCount--;
		}
	}
}
