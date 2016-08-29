package com.hbm.items;

import java.util.List;

import com.hbm.interfaces.IBomb;
import com.hbm.lib.Library;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemRamManipulator extends Item {

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		list.add("A broken remote.");
		if (itemstack.getTagCompound() == null) {
		} else {
			list.add("");
			list.add(String.valueOf(itemstack.stackTagCompound.getInteger("code")));
		}
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_,
			float p_77648_8_, float p_77648_9_, float p_77648_10_) {
		if (stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
		}

		if (!player.isSneaking()) {

			if (world.isRemote) {
				player.addChatMessage(new ChatComponentText("Position set!"));
			}
			
			Block block = world.getBlock(x, y, z);

			if(block == Blocks.dirt)
				stack.stackTagCompound.setInteger("code", stack.stackTagCompound.getInteger("code") * 10 + 0);
			if(block == Blocks.cobblestone)
				stack.stackTagCompound.setInteger("code", stack.stackTagCompound.getInteger("code") * 10 + 1);
			if(block == Blocks.stone)
				stack.stackTagCompound.setInteger("code", stack.stackTagCompound.getInteger("code") * 10 + 2);
			if(block == Blocks.log)
				stack.stackTagCompound.setInteger("code", stack.stackTagCompound.getInteger("code") * 10 + 3);
			if(block == Blocks.planks)
				stack.stackTagCompound.setInteger("code", stack.stackTagCompound.getInteger("code") * 10 + 4);
			if(block == Blocks.gravel)
				stack.stackTagCompound.setInteger("code", stack.stackTagCompound.getInteger("code") * 10 + 5);
			if(block == Blocks.sand)
				stack.stackTagCompound.setInteger("code", stack.stackTagCompound.getInteger("code") * 10 + 6);
			if(block == Blocks.sandstone)
				stack.stackTagCompound.setInteger("code", stack.stackTagCompound.getInteger("code") * 10 + 7);
			if(block == Blocks.tallgrass)
				stack.stackTagCompound.setInteger("code", stack.stackTagCompound.getInteger("code") * 10 + 8);
			if(block == Blocks.double_plant)
				stack.stackTagCompound.setInteger("code", stack.stackTagCompound.getInteger("code") * 10 + 9);
			
			return true;
		}

		return false;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

		if (stack.stackTagCompound == null) {
		} else {
			if(!player.isSneaking())
			{
				if(Library.getItemByCode(stack.stackTagCompound.getInteger("code")) != null)
					player.inventory.addItemStackToInventory(new ItemStack((Library.getItemByCode(stack.stackTagCompound.getInteger("code")))));
			} else {
				stack.stackTagCompound.setInteger("code", 0);
			}
		}

		return stack;

	}
}
