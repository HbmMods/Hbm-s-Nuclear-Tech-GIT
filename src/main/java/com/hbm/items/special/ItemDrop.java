package com.hbm.items.special;

import java.util.List;

import org.apache.logging.log4j.Level;

import com.hbm.config.BombConfig;
import com.hbm.config.GeneralConfig;
import com.hbm.config.WeaponConfig;
import com.hbm.entity.effect.EntityBlackHole;
import com.hbm.entity.effect.EntityCloudFleija;
import com.hbm.entity.effect.EntityRagingVortex;
import com.hbm.entity.effect.EntityVortex;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNT;
import com.hbm.explosion.ExplosionNT.ExAttrib;
import com.hbm.interfaces.IBomb;
import com.hbm.interfaces.Spaghetti;
import com.hbm.items.ModItems;
import com.hbm.lib.HbmCollection;
import com.hbm.main.MainRegistry;
import com.hbm.util.I18nUtil;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
@Spaghetti("Painful!")
public class ItemDrop extends ItemCustomLore {

	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem) {
		if (entityItem != null) {
			
			if(this == ModItems.beta) {
				entityItem.setDead();
				return true;
			}
			
			ItemStack stack = entityItem.getEntityItem();

			if (stack.getItem() != null && stack.getItem() == ModItems.detonator_deadman) {
				if (!entityItem.worldObj.isRemote) {
					
					if(stack.stackTagCompound != null) {
						
						 int x = stack.stackTagCompound.getInteger("x");
						 int y = stack.stackTagCompound.getInteger("y");
						 int z = stack.stackTagCompound.getInteger("z");
						 
						 if(entityItem.worldObj.getBlock(x, y, z) instanceof IBomb)
						 {
							if(!entityItem.worldObj.isRemote)
							{
								((IBomb)entityItem.worldObj.getBlock(x, y, z)).explode(entityItem.worldObj, x, y, z);

					    		if(GeneralConfig.enableExtendedLogging)
					    			MainRegistry.logger.log(Level.INFO, "[DET] Tried to detonate block at " + x + " / " + y + " / " + z + " by dead man's switch!");
							}
						 }
					}

					entityItem.worldObj.createExplosion(entityItem, entityItem.posX, entityItem.posY,
							entityItem.posZ, 0.0F, true);
					entityItem.setDead();
				}
			}
			if (stack.getItem() != null && stack.getItem() == ModItems.detonator_de) {
				if (!entityItem.worldObj.isRemote && WeaponConfig.dropDead) {
					entityItem.worldObj.createExplosion(entityItem, entityItem.posX, entityItem.posY,
							entityItem.posZ, 15.0F, true);

		    		if(GeneralConfig.enableExtendedLogging)
		    			MainRegistry.logger.log(Level.INFO, "[DET] Detonated dead man's explosive at " + ((int)entityItem.posX) + " / " + ((int)entityItem.posY) + " / " + ((int)entityItem.posZ) + "!");
				}
				
				entityItem.setDead();
			}
			
			if (entityItem.onGround) {

				if (stack.getItem() != null && stack.getItem() == ModItems.cell_antimatter && WeaponConfig.dropCell) {
					if (!entityItem.worldObj.isRemote) {
						entityItem.worldObj.createExplosion(entityItem, entityItem.posX, entityItem.posY,
								entityItem.posZ, 10.0F, true);
					}
				}
				if (stack.getItem() != null && stack.getItem() == ModItems.pellet_antimatter && WeaponConfig.dropCell) {
					if (!entityItem.worldObj.isRemote) {
						new ExplosionNT(entityItem.worldObj, entityItem, entityItem.posX, entityItem.posY, entityItem.posZ, 30).overrideResolution(64).addAttrib(ExAttrib.FIRE).addAttrib(ExAttrib.NOSOUND).explode();
						ExplosionLarge.spawnParticles(entityItem.worldObj, entityItem.posX, entityItem.posY, entityItem.posZ, ExplosionLarge.cloudFunction(100));
						entityItem.worldObj.playSoundEffect(entityItem.posX, entityItem.posY, entityItem.posZ, "hbm:weapon.mukeExplosion", 15.0F, 1.0F);
					}
				}
				if (stack.getItem() != null && stack.getItem() == ModItems.cell_anti_schrabidium && WeaponConfig.dropCell) {
					if (!entityItem.worldObj.isRemote) {
						entityItem.worldObj.playSoundEffect(entityItem.posX, entityItem.posY, entityItem.posZ, "random.explode", 100.0F, entityItem.worldObj.rand.nextFloat() * 0.1F + 0.9F);
						entityItem.worldObj.spawnEntityInWorld(EntityNukeExplosionMK3.statFacFleija(entityItem.worldObj, entityItem.posX, entityItem.posY, entityItem.posZ, BombConfig.aSchrabRadius));
			    		
			    		EntityCloudFleija cloud = new EntityCloudFleija(entityItem.worldObj, BombConfig.aSchrabRadius);
			    		cloud.posX = entityItem.posX;
			    		cloud.posY = entityItem.posY;
			    		cloud.posZ = entityItem.posZ;
			    		entityItem.worldObj.spawnEntityInWorld(cloud);
					}
				}
				if (stack.getItem() != null && stack.getItem() == ModItems.singularity && WeaponConfig.dropSing) {
					if (!entityItem.worldObj.isRemote) {

			        	EntityVortex bl = new EntityVortex(entityItem.worldObj, 1.5F);
			        	bl.posX = entityItem.posX ;
			        	bl.posY = entityItem.posY ;
			        	bl.posZ = entityItem.posZ ;
			        	entityItem.worldObj.spawnEntityInWorld(bl);
					}
				}
				if (stack.getItem() != null && stack.getItem() == ModItems.singularity_counter_resonant && WeaponConfig.dropSing) {
					if (!entityItem.worldObj.isRemote) {

			        	EntityVortex bl = new EntityVortex(entityItem.worldObj, 2.5F);
			        	bl.posX = entityItem.posX ;
			        	bl.posY = entityItem.posY ;
			        	bl.posZ = entityItem.posZ ;
			        	entityItem.worldObj.spawnEntityInWorld(bl);
					}
				}
				if (stack.getItem() != null && stack.getItem() == ModItems.singularity_super_heated && WeaponConfig.dropSing) {
					if (!entityItem.worldObj.isRemote) {

			        	EntityVortex bl = new EntityVortex(entityItem.worldObj, 2.5F);
			        	bl.posX = entityItem.posX ;
			        	bl.posY = entityItem.posY ;
			        	bl.posZ = entityItem.posZ ;
			        	entityItem.worldObj.spawnEntityInWorld(bl);
					}
				}
				if (stack.getItem() != null && stack.getItem() == ModItems.black_hole && WeaponConfig.dropSing) {
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

			        	EntityBlackHole bl = new EntityBlackHole(entityItem.worldObj, 1.5F);
			        	bl.posX = entityItem.posX ;
			        	bl.posY = entityItem.posY ;
			        	bl.posZ = entityItem.posZ ;
			        	entityItem.worldObj.spawnEntityInWorld(bl);
					}
				}
				if (stack.getItem() != null && stack.getItem() == ModItems.singularity_spark && WeaponConfig.dropSing) {
					if (!entityItem.worldObj.isRemote) {
			        	EntityRagingVortex bl = new EntityRagingVortex(entityItem.worldObj, 3.5F);
			        	bl.posX = entityItem.posX ;
			        	bl.posY = entityItem.posY ;
			        	bl.posZ = entityItem.posZ ;
			        	entityItem.worldObj.spawnEntityInWorld(bl);
					}
				}
				if (stack.getItem() != null && stack.getItem() == ModItems.crystal_xen && WeaponConfig.dropCrys) {
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
		super.addInformation(itemstack, player, list, bool);
		if (this == ModItems.detonator_deadman)
		{
			if(itemstack.getTagCompound() == null)
				list.add(I18nUtil.resolveKey(HbmCollection.noPos));
			else
				list.add(I18nUtil.resolveKey(HbmCollection.pos, itemstack.stackTagCompound.getInteger("x"), itemstack.stackTagCompound.getInteger("y"), itemstack.stackTagCompound.getInteger("z")));

		}
		list.add(EnumChatFormatting.RED + "[" + I18nUtil.resolveKey(HbmCollection.drop) + "]");
	}
	
	@Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
		if(this != ModItems.detonator_deadman) {
			return super.onItemUse(stack, player, world, x, y, z, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_);
		}
		
		if(stack.stackTagCompound == null)
		{
			stack.stackTagCompound = new NBTTagCompound();
		}
		
		if(player.isSneaking())
		{
			stack.stackTagCompound.setInteger("x", x);
			stack.stackTagCompound.setInteger("y", y);
			stack.stackTagCompound.setInteger("z", z);
			
			if(world.isRemote)
			{
				player.addChatMessage(new ChatComponentText("Position set!"));
			}
			
	        world.playSoundAtEntity(player, "hbm:item.techBoop", 2.0F, 1.0F);
        	
			return true;
		}
		
		return false;
    }

}