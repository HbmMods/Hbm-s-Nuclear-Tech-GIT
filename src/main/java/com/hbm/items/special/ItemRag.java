package com.hbm.items.special;

import java.util.List;

import com.hbm.extprop.HbmLivingProps;
import com.hbm.items.ModItems;

import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemRag extends Item {

	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem) {
		
		if(entityItem != null && !entityItem.worldObj.isRemote) {
			
			if(entityItem.worldObj.getBlock((int)Math.floor(entityItem.posX), (int)Math.floor(entityItem.posY), (int)Math.floor(entityItem.posZ)).getMaterial() == Material.water) {
				ItemStack stack = entityItem.getEntityItem();
				entityItem.setEntityItemStack(new ItemStack(ModItems.rag_damp, stack.stackSize));
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(HbmLivingProps.getRadiation(player) > 500) {
			stack.stackSize--;
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rag_blood));
			return stack;
		}
		else {
			stack.stackSize--;
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rag_piss));
			return stack;	
		}
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		list.add("Drop into water to make damp cloth.");
		list.add("Right-click to urinate on the cloth.");
	}
}
