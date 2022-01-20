package com.hbm.entity.missile;

import com.hbm.blocks.ModBlocks;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.handler.SiegeOrchestrator;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class EntitySiegeDropship extends EntityThrowable {

	public int health = 10;

	public EntitySiegeDropship(World world) {
		super(world);
	}

	public EntitySiegeDropship(World world, double x, double y, double z) {
		super(world, x, y, z);
		this.health *= (SiegeOrchestrator.level + 1);
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		
		if(this.isEntityInvulnerable()) {
			return false;
			
		} else {
			
			if(!this.isDead && !this.worldObj.isRemote) {
				health -= amount;

				if(this.health <= 0) {
					this.setDead();
					SiegeOrchestrator.levelCounter += SiegeOrchestrator.getTierAddDrop(worldObj);
				}
			}

			return true;
		}
	}

	@Override
	public void onUpdate() {

		this.motionX = 0;
		this.motionY = -0.5;
		this.motionZ = 0;

		super.onUpdate();
	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {

		if(mop.typeOfHit == MovingObjectType.BLOCK) {
			this.setDead();
			
			if(SiegeOrchestrator.enableBaseSpawning(worldObj)) {
				worldObj.setBlock(mop.blockX, mop.blockY, mop.blockZ, ModBlocks.siege_shield);
			} else if(SiegeOrchestrator.enableMobSpawning(worldObj)) {
				SiegeOrchestrator.spawnRandomMob(worldObj, mop.blockX + 0.5, mop.blockY + 1, mop.blockZ + 0.5);
			}
			
			ExplosionLarge.spawnParticles(worldObj, posX, posY + 1, posZ, 15);
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("health", this.health);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		
		this.health = nbt.getInteger("health");
	}
}
