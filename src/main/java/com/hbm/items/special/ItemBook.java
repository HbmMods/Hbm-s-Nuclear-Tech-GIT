package com.hbm.items.special;

import java.util.List;

import com.hbm.handler.EnumGUI;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBook extends Item {
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		
		list.add("Edition 4, gold lined pages");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(!world.isRemote)
			player.openGui(MainRegistry.instance, EnumGUI.ITEM_BLACK_BOOK.ordinal(), world, 0, 0, 0);
		
		return stack;
	}

	/*@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(world.isRemote)
			return stack;
		
		if(!player.isSneaking()) {
			List list = world.getEntitiesWithinAABBExcludingEntity(player, AxisAlignedBB.getBoundingBox(player.posX - 10, player.posY - 2, player.posZ - 10, player.posX + 10, player.posY + 2, player.posZ + 10));
			
			for(Object o : list) {
				
				if(o instanceof EntityLivingBase) {
					EntityLivingBase entity = (EntityLivingBase)o;
					
					entity.addPotionEffect(new PotionEffect(HbmPotion.telekinesis.id, 20, 0));
				}
			}
		} else {
			if(player.inventory.hasItemStack(new ItemStack(ModItems.ingot_u238m2, 1, 1)) &&
					player.inventory.hasItemStack(new ItemStack(ModItems.ingot_u238m2, 1, 2)) &&
					player.inventory.hasItemStack(new ItemStack(ModItems.ingot_u238m2, 1, 3))) {
				player.inventory.clearInventory(ModItems.ingot_u238m2, 1);
				player.inventory.clearInventory(ModItems.ingot_u238m2, 2);
				player.inventory.clearInventory(ModItems.ingot_u238m2, 3);
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_u238m2));
				player.inventoryContainer.detectAndSendChanges();
			}
		}
		
		return stack;
	}*/

}
