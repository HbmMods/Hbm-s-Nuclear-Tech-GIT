package com.hbm.items.special;

import java.util.List;

import com.hbm.entity.mob.EntityDuck;
import com.hbm.entity.mob.EntityHunterChopper;
import com.hbm.entity.mob.EntityUFO;
import com.hbm.entity.mob.botprime.EntityBOTPrimeHead;
import com.hbm.entity.mob.EntityMaskMan;
import com.hbm.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemChopper extends Item {

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		
		if(world.isRemote) {
			return true;
		} else {
			
			Block block = world.getBlock(x, y, z);
			
			x += Facing.offsetsXForSide[side];
			y += Facing.offsetsYForSide[side];
			z += Facing.offsetsZForSide[side];
			double offset = 0.0D;

			if(side == 1 && block.getRenderType() == 11)
				offset = 0.5D;

			Entity entity = spawnCreature(world, stack.getItemDamage(), x + 0.5D, y + offset, z + 0.5D);

			if(entity != null) {
				if(entity instanceof EntityLivingBase && stack.hasDisplayName()) {
					((EntityLiving) entity).setCustomNameTag(stack.getDisplayName());
				}

				if(!player.capabilities.isCreativeMode) {
					--stack.stackSize;
				}
			}

			return true;
		}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(world.isRemote) {
			return stack;
			
		} else {
			MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);

			if(movingobjectposition == null) {
				return stack;
			} else {
				if(movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
					int i = movingobjectposition.blockX;
					int j = movingobjectposition.blockY;
					int k = movingobjectposition.blockZ;

					if(!world.canMineBlock(player, i, j, k)) {
						return stack;
					}

					if(!player.canPlayerEdit(i, j, k, movingobjectposition.sideHit, stack)) {
						return stack;
					}

					if(world.getBlock(i, j, k) instanceof BlockLiquid) {
						Entity entity = spawnCreature(world, stack.getItemDamage(), i, j, k);

						if(entity != null) {
							if(entity instanceof EntityLivingBase && stack.hasDisplayName()) {
								((EntityLiving) entity).setCustomNameTag(stack.getDisplayName());
							}

							if(!player.capabilities.isCreativeMode) {
								--stack.stackSize;
							}
						}
					}
				}

				return stack;
			}
		}
	}

	public Entity spawnCreature(World world, int dmg, double x, double y, double z) {
		Entity entity = null;
		
		if(this == ModItems.spawn_chopper)
			entity = new EntityHunterChopper(world);
		
		if(this == ModItems.spawn_worm)
			entity = new EntityBOTPrimeHead(world);
		
		if(this == ModItems.spawn_ufo) {
			entity = new EntityUFO(world);
			((EntityUFO)entity).scanCooldown = 100;
			y += 35;
		}
		
		if(this == ModItems.spawn_duck)
			entity = new EntityDuck(world);

		if(this == ModItems.spawn_maskman) 
			entity = new EntityMaskMan(world);
		
		if(entity != null) {

			EntityLiving entityliving = (EntityLiving) entity;
			entity.setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
			entityliving.rotationYawHead = entityliving.rotationYaw;
			entityliving.renderYawOffset = entityliving.rotationYaw;
			entityliving.onSpawnWithEgg((IEntityLivingData) null);
			world.spawnEntityInWorld(entity);
		}

		return entity;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		if(this == ModItems.spawn_worm) {
			list.add("Without a player in survival mode");
			list.add("to target, he struggles around a lot.");
			list.add("");
			list.add("He's doing his best so please show him");
			list.add("some consideration.");
		}
	}

}
