package com.hbm.entity.missile;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.mob.siege.SiegeTier;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.handler.SiegeOrchestrator;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class EntitySiegeDropship extends EntityThrowable {

	public int health = 20;

	public EntitySiegeDropship(World world) {
		super(world);
		this.health *= Math.pow((SiegeOrchestrator.level + 1), 2);
		this.setSize(0.5F, 1F);
	}

	public EntitySiegeDropship(World world, double x, double y, double z) {
		super(world, x, y, z);
		this.health *= Math.pow((SiegeOrchestrator.level + 1), 2);
		this.setSize(0.5F, 1F);
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
					
					SiegeTier tier = SiegeTier.tiers[SiegeOrchestrator.level];
					if(tier == null)
						tier = SiegeTier.DNT;
					
					for(ItemStack drop : tier.dropItem) {
						this.entityDropItem(drop.copy(), 0F);
					}
					
					ExplosionLarge.spawnParticles(worldObj, posX, posY + 1, posZ, 10);
					
					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "plasmablast");
					data.setFloat("r", 1F);
					data.setFloat("g", 0F);
					data.setFloat("b", 0F);
					data.setFloat("scale", 20F);
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, posX, posY, posZ),
							new TargetPoint(worldObj.provider.dimensionId, posX, posY, posZ, 100));
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
		
		if(!worldObj.isRemote && this.ticksExisted % 2 == 0) {
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "plasmablast");
			data.setFloat("r", 0.1F);
			data.setFloat("g", 0.75F);
			data.setFloat("b", 1.0F);
			data.setFloat("scale", 3F);
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, posX, posY, posZ),
					new TargetPoint(worldObj.provider.dimensionId, posX, posY, posZ, 100));
		}

		super.onUpdate();
	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {

		if(mop.typeOfHit == MovingObjectType.BLOCK) {
			this.setDead();
			
			if(SiegeOrchestrator.enableBaseSpawning(worldObj)) {
				worldObj.setBlock(mop.blockX, mop.blockY, mop.blockZ, ModBlocks.siege_shield);
			} else if(SiegeOrchestrator.enableMobSpawning(worldObj)) {
				SiegeOrchestrator.spawnRandomMob(worldObj, mop.blockX + 0.5, mop.blockY + 1, mop.blockZ + 0.5, null);
			}
			
			ExplosionLarge.spawnParticles(worldObj, posX, posY + 1, posZ, 10);
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
