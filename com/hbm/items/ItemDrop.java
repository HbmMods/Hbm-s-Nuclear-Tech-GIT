package com.hbm.items;

import com.hbm.entity.EntityNukeExplosionAdvanced;
import com.hbm.main.MainRegistry;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemDrop extends Item {

	public boolean onEntityItemUpdate(EntityItem entityItem) {
		if (entityItem != null) {
			if (entityItem.onGround) {

				ItemStack stack = entityItem.getEntityItem();

				if (stack.getItem() != null && stack.getItem() == ModItems.cell_antimatter) {
					if (!entityItem.worldObj.isRemote) {
						entityItem.worldObj.createExplosion(entityItem, entityItem.posX, entityItem.posY,
								entityItem.posZ, 10.0F, true);
					}
				}
				if (stack.getItem() != null && stack.getItem() == ModItems.cell_anti_schrabidium) {
					if (!entityItem.worldObj.isRemote) {
						entityItem.worldObj.playSoundEffect(entityItem.posX, entityItem.posY, entityItem.posZ,
								"random.explode", 1.0f, entityItem.worldObj.rand.nextFloat() * 0.1F + 0.9F);

						EntityNukeExplosionAdvanced entity = new EntityNukeExplosionAdvanced(entityItem.worldObj);
						entity.posX = entityItem.posX;
						entity.posY = entityItem.posY;
						entity.posZ = entityItem.posZ;
						entity.destructionRange = MainRegistry.aSchrabRadius;
						entity.speed = 25;
						entity.coefficient = 1.0F;
						entity.waste = false;

						entityItem.worldObj.spawnEntityInWorld(entity);
					}
				}

				entityItem.setDead();
				return true;
			}
		}
		return false;
    }

}
