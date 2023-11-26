package com.hbm.items.special;

import java.util.List;

import com.hbm.items.ModItems;

import com.hbm.util.I18nUtil;
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
		
		stack.stackSize--;
		player.inventory.addItemStackToInventory(new ItemStack(ModItems.rag_piss));
		
		return stack;
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		for(String s : I18nUtil.resolveKeyArray("item.rag.desc"))
		    list.add(s);
	}
}
