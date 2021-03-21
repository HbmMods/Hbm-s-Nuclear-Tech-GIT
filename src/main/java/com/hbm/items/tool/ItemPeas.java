package com.hbm.items.tool;

import java.util.List;

import com.hbm.entity.mob.EntityQuackos;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemPeas extends Item{


	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if (!player.capabilities.isCreativeMode) {
			--stack.stackSize;
		}
		
		List<EntityQuackos> quacc = world.getEntitiesWithinAABB(EntityQuackos.class, player.boundingBox.expand(50, 50, 50));
		
		for(EntityQuackos ducc : quacc) {
			ducc.despawn();
		}
		
		return stack;
	}
}
