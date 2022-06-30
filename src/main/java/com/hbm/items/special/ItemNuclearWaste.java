package com.hbm.items.special;

import com.hbm.entity.item.EntityItemWaste;

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
		
		EntityItemWaste entity = new EntityItemWaste(world, entityItem.posX, entityItem.posY, entityItem.posZ, itemstack);
		entity.motionX = entityItem.motionX;
		entity.motionY = entityItem.motionY;
		entity.motionZ = entityItem.motionZ;
		entity.delayBeforeCanPickup = 10;
		
		entityItem.setDead();
		
		return entity;
	}
}
