package com.hbm.items;

import java.util.List;

import com.hbm.entity.EntityNukeExplosionAdvanced;
import com.hbm.main.MainRegistry;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
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
				if (stack.getItem() != null && stack.getItem() == ModItems.singularity) {
					if (!entityItem.worldObj.isRemote) {
						entityItem.worldObj.playSoundEffect(entityItem.posX, entityItem.posY, entityItem.posZ,
								"random.explode", 1.0f, entityItem.worldObj.rand.nextFloat() * 0.1F + 0.9F);

						EntityNukeExplosionAdvanced entity = new EntityNukeExplosionAdvanced(entityItem.worldObj);
						entity.posX = entityItem.posX;
						entity.posY = entityItem.posY;
						entity.posZ = entityItem.posZ;
						entity.destructionRange = MainRegistry.aSchrabRadius;
						entity.speed = 25;
						entity.coefficient = 0.01F;
						entity.coefficient2 = 0.01F;
						entity.waste = false;

						entityItem.worldObj.spawnEntityInWorld(entity);
					}
				}
				if (stack.getItem() != null && stack.getItem() == ModItems.singularity_counter_resonant) {
					if (!entityItem.worldObj.isRemote) {
						entityItem.worldObj.playSoundEffect(entityItem.posX, entityItem.posY, entityItem.posZ,
								"random.explode", 1.0f, entityItem.worldObj.rand.nextFloat() * 0.1F + 0.9F);

						EntityNukeExplosionAdvanced entity = new EntityNukeExplosionAdvanced(entityItem.worldObj);
						entity.posX = entityItem.posX;
						entity.posY = entityItem.posY;
						entity.posZ = entityItem.posZ;
						entity.destructionRange = MainRegistry.aSchrabRadius * 2;
						entity.speed = 25;
						entity.coefficient = 0.01F;
						entity.coefficient2 = 0.01F;
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
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		if (itemstack.getItem() != null && itemstack.getItem() == ModItems.cell_antimatter) {
			list.add("Warning: Exposure to matter will");
			list.add("lead to violent annihilation!");
		}
		if (itemstack.getItem() != null && itemstack.getItem() == ModItems.cell_anti_schrabidium) {
			list.add("Warning: Exposure to matter will");
			list.add("create a fólkvangr field!");
		}
		if (itemstack.getItem() != null && itemstack.getItem() == ModItems.singularity) {
			list.add("You may be asking:");
			list.add("\"But HBM, a manifold with an undefined");
			list.add("state of spacetime? How is this possible?\"");
			list.add("Long answer short:");
			list.add("\"I have no idea!\"");
		}
		if (itemstack.getItem() != null && itemstack.getItem() == ModItems.singularity_counter_resonant) {
			list.add("Nullifies resonance of objects in");
			list.add("non-euclidean space, creates variable");
			list.add("gravity well. Spontaneously creates");
			list.add("tesseracts.");
		}
	}

}
