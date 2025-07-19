package com.hbm.items.tool;

import com.hbm.blocks.bomb.BlockCrashedBomb;
import com.hbm.items.ModItems;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemAmatExtractor extends Item {

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		
		if(world.getBlock(x, y, z) instanceof BlockCrashedBomb) {
			if(!world.isRemote && player.inventory.hasItem(ModItems.cell_empty)) {
				
				float chance = world.rand.nextFloat();
				
				if(chance < 0.01) {
					((BlockCrashedBomb) world.getBlock(x, y, z)).explode(world, x, y, z);
				} else if(chance <= 0.3) {
					player.inventory.consumeInventoryItem(ModItems.cell_empty);
	
					if(!player.inventory.addItemStackToInventory(new ItemStack(ModItems.cell_balefire))) {
						player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.cell_balefire, 1, 0), false);
					}
				} else {
					player.inventory.consumeInventoryItem(ModItems.cell_empty);
	
					if(!player.inventory.addItemStackToInventory(new ItemStack(ModItems.cell_antimatter))) {
						player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.cell_antimatter, 1, 0), false);
					}
				}
				
				player.inventoryContainer.detectAndSendChanges();
				ContaminationUtil.contaminate(player, HazardType.RADIATION, ContaminationType.CREATIVE, 50.0F);
			}
			
			player.swingItem();
			return true;
		}
		
		return false;
	}
}
