package com.hbm.items.special;

import com.hbm.entity.item.EntitytemWaste;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemNuclearWaste extends Item {

	@Override
	public boolean hasCustomEntity(ItemStack stack) {
		return true;
	}
	
	@Override
	public int getEntityLifespan(ItemStack itemStack, World world) {
		return Integer.MAX_VALUE;
	}

	@Override
	public Entity createEntity(World world, Entity entityItem, ItemStack itemstack) {
		
		EntitytemWaste entity = new EntitytemWaste(world, entityItem.posX, entityItem.posY, entityItem.posZ, itemstack);
		entity.motionX = entityItem.motionX;
		entity.motionY = entityItem.motionY;
		entity.motionZ = entityItem.motionZ;
		entity.delayBeforeCanPickup = 10;
		
		return entity;
	}
}
