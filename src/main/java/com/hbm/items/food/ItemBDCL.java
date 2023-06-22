package com.hbm.items.food;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBDCL extends Item {

	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 32;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack p_77661_1_) {
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
}
