package com.hbm.items.special;

import java.util.List;

import com.hbm.entity.effect.EntityBlackHole;
import com.hbm.entity.effect.EntityCloudFleija;
import com.hbm.entity.logic.EntityNukeExplosionAdvanced;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.items.ModItems;
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
								"random.explode", 100.0f, entityItem.worldObj.rand.nextFloat() * 0.1F + 0.9F);

						EntityNukeExplosionAdvanced entity = new EntityNukeExplosionAdvanced(entityItem.worldObj);
						entity.posX = entityItem.posX;
						entity.posY = entityItem.posY;
						entity.posZ = entityItem.posZ;
						entity.destructionRange = MainRegistry.aSchrabRadius;
						entity.speed = 25;
						entity.coefficient = 1.0F;
						entity.waste = false;

						entityItem.worldObj.spawnEntityInWorld(entity);
			    		
			    		EntityCloudFleija cloud = new EntityCloudFleija(entityItem.worldObj, MainRegistry.aSchrabRadius);
			    		cloud.posX = entityItem.posX;
			    		cloud.posY = entityItem.posY;
			    		cloud.posZ = entityItem.posZ;
			    		entityItem.worldObj.spawnEntityInWorld(cloud);
					}
				}
				if (stack.getItem() != null && stack.getItem() == ModItems.singularity) {
					if (!entityItem.worldObj.isRemote) {
						entityItem.worldObj.playSoundEffect(entityItem.posX, entityItem.posY, entityItem.posZ,
								"random.explode", 100.0f, entityItem.worldObj.rand.nextFloat() * 0.1F + 0.9F);

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
								"random.explode", 100.0f, entityItem.worldObj.rand.nextFloat() * 0.1F + 0.9F);

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
				if (stack.getItem() != null && stack.getItem() == ModItems.singularity_super_heated) {
					if (!entityItem.worldObj.isRemote) {
						entityItem.worldObj.playSoundEffect(entityItem.posX, entityItem.posY, entityItem.posZ,
								"random.explode", 100.0f, entityItem.worldObj.rand.nextFloat() * 0.1F + 0.9F);

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
				if (stack.getItem() != null && stack.getItem() == ModItems.black_hole) {
					if (!entityItem.worldObj.isRemote) {
						/*entityItem.worldObj.playSoundEffect(entityItem.posX, entityItem.posY, entityItem.posZ,
								"random.explode", 100.0f, entityItem.worldObj.rand.nextFloat() * 0.1F + 0.9F);

						EntityNukeExplosionAdvanced entity = new EntityNukeExplosionAdvanced(entityItem.worldObj);
						entity.posX = entityItem.posX;
						entity.posY = entityItem.posY;
						entity.posZ = entityItem.posZ;
						entity.destructionRange = MainRegistry.aSchrabRadius * 3;
						entity.speed = 25;
						entity.coefficient = 0.01F;
						entity.coefficient2 = 0.01F;
						entity.waste = false;

						entityItem.worldObj.spawnEntityInWorld(entity);*/

			        	EntityBlackHole bl = new EntityBlackHole(entityItem.worldObj, 0.5F);
			        	bl.posX = entityItem.posX ;
			        	bl.posY = entityItem.posY ;
			        	bl.posZ = entityItem.posZ ;
			        	entityItem.worldObj.spawnEntityInWorld(bl);
					}
				}
				if (stack.getItem() != null && stack.getItem() == ModItems.crystal_xen) {
					if (!entityItem.worldObj.isRemote) {
						ExplosionChaos.floater(entityItem.worldObj, (int)entityItem.posX, (int)entityItem.posY, (int)entityItem.posZ, 25, 75);
						ExplosionChaos.move(entityItem.worldObj, (int)entityItem.posX, (int)entityItem.posY, (int)entityItem.posZ, 25, 0, 75, 0);
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
			list.add("gravity well. Spontaneously spawns");
			list.add("tesseracts. If a tesseract happens to");
			list.add("appear near you, do not look directly");
			list.add("at it.");
		}
		if (itemstack.getItem() != null && itemstack.getItem() == ModItems.singularity_super_heated) {
			list.add("Continuously heats up matter by");
			list.add("resonating every planck second.");
			list.add("Tends to catch fire or to create");
			list.add("small plamsa arcs. Not edible.");
		}
		if (itemstack.getItem() != null && itemstack.getItem() == ModItems.black_hole) {
			list.add("Contains a regular singularity");
			list.add("in the center. Large enough to");
			list.add("stay stable. It's not the end");
			list.add("of the world as we know it,");
			list.add("and I don't feel fine.");
		}
	}

}
