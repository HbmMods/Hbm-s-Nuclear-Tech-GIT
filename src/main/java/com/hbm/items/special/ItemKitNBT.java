package com.hbm.items.special;

import com.hbm.items.ModItems;
import com.hbm.util.ItemStackUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemKitNBT extends Item {
	
	public ItemKitNBT() {
		this.setMaxStackSize(1);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		ItemStack[] stacks = ItemStackUtil.readStacksFromNBT(stack);
		
		if(stacks != null) {
			
			for(ItemStack item : stacks) {
				if(item != null) {
					player.inventory.addItemStackToInventory(item.copy());
				}
			}
		}
		
		ItemStack container = stack.getItem().getContainerItem(stack);
		
		if(container != null) {
			player.inventory.addItemStackToInventory(container.copy());
		}
		
		stack.stackSize--;
		
		return stack;
	}
	
	public static ItemStack create(ItemStack... contents) {
		ItemStack stack = new ItemStack(ModItems.kit_custom);
		stack.stackTagCompound = new NBTTagCompound();
		ItemStackUtil.addStacksToNBT(stack, contents);
		
		return stack;
	}
}
