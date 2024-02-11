package com.hbm.items.special;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemCustomMissilePart;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemLootCrate extends Item {

	public static List<ItemCustomMissilePart> list10 = new ArrayList();
	public static List<ItemCustomMissilePart> list15 = new ArrayList();
	public static List<ItemCustomMissilePart> listMisc = new ArrayList();
	private static Random rand = new Random();

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

		player.inventoryContainer.detectAndSendChanges();

		if(stack.getItem() == ModItems.loot_10)
			player.inventory.addItemStackToInventory(new ItemStack(choose(list10)));
		if(stack.getItem() == ModItems.loot_15)
			player.inventory.addItemStackToInventory(new ItemStack(choose(list15)));
		if(stack.getItem() == ModItems.loot_misc)
			player.inventory.addItemStackToInventory(new ItemStack(choose(listMisc)));
		
		stack.stackSize--;
		return stack;
	}
	
	private ItemCustomMissilePart choose(List<ItemCustomMissilePart> parts) {
		
		boolean flag = true;
		ItemCustomMissilePart item = null;
		
		while(flag) {
			item = parts.get(rand.nextInt(parts.size()));
			
			switch(item.rarity) {
			case COMMON:
				flag = false;
				break;
			case UNCOMMON:
				if(rand.nextInt(5) == 0) flag = false;
				break;
			case RARE:
				if(rand.nextInt(10) == 0) flag = false;
				break;
			case EPIC:
				if(rand.nextInt(25) == 0) flag = false;
				break;
			case LEGENDARY:
				if(rand.nextInt(50) == 0) flag = false;
				break;
			case SEWS_CLOTHES_AND_SUCKS_HORSE_COCK:
				if(rand.nextInt(100) == 0) flag = false;
				break;
			
			}
		}
		
		return item;
	}
}
