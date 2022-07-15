package com.hbm.entity.missile;

import java.util.ArrayList;
import java.util.List;

import api.hbm.entity.IRadarDetectable;
import api.hbm.entity.IRadarDetectable.RadarTargetType;

import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.entity.logic.EntityNukeExplosionMK4;
import com.hbm.entity.logic.IChunkLoader;
import com.hbm.entity.particle.EntitySmokeFX;
import com.hbm.entity.projectile.EntityThrowableNT;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;

public class EntityMIRV extends EntityThrowableNT implements IChunkLoader, IRadarDetectable {
	
	private Ticket loaderTicket;
	public int health = 25;
	
	public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_) {
		if(this.isEntityInvulnerable()) {
			return false;
		} else {
			if(!this.isDead && !this.worldObj.isRemote) {
				health -= p_70097_2_;

				if(this.health <= 0) {
					this.setDead();
					this.killMissile();
				}
			}

			return true;
		}
	}

	private void killMissile() {
		ExplosionLarge.explode(worldObj, posX, posY, posZ, 5, true, false, true);
		ExplosionLarge.spawnShrapnelShower(worldObj, posX, posY, posZ, motionX, motionY, motionZ, 15, 0.075);
	}
	public EntityMIRV(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
	}
	
	@Override
	protected void entityInit() {
		init(ForgeChunkManager.requestTicket(MainRegistry.instance, worldObj, Type.ENTITY));
		this.dataWatcher.addObject(8, Integer.valueOf(this.health));
	}
	
	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;

		this.motionY -= 0.03;

		this.rotation();

		if(this.worldObj.getBlock((int) this.posX, (int) this.posY, (int) this.posZ) != Blocks.air) {
			if(!this.worldObj.isRemote) {
				//worldObj.spawnEntityInWorld(EntityNukeExplosionMK4.statFac(worldObj, BombConfig.missileRadius, posX, posY, posZ));

				/*EntityNukeCloudSmall entity2 = new EntityNukeCloudSmall(this.worldObj, 1000, BombConfig.missileRadius * 0.005F);
				entity2.posX = this.posX;
				entity2.posY = this.posY;
				entity2.posZ = this.posZ;
				this.worldObj.spawnEntityInWorld(entity2);*/
			}
			this.setDead();
		}

		this.worldObj.spawnEntityInWorld(new EntitySmokeFX(this.worldObj, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0));
		loadNeighboringChunks((int)(posX / 16), (int)(posZ / 16));
	}

	protected void rotation() {
		float f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
		this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

		for(this.rotationPitch = (float) (Math.atan2(this.motionY, f2) * 180.0D / Math.PI) - 90; this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
			;
		}

		while(this.rotationPitch - this.prevRotationPitch >= 180.0F) {
			this.prevRotationPitch += 360.0F;
		}

		while(this.rotationYaw - this.prevRotationYaw < -180.0F) {
			this.prevRotationYaw -= 360.0F;
		}

		while(this.rotationYaw - this.prevRotationYaw >= 180.0F) {
			this.prevRotationYaw += 360.0F;
		}
	}
  
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		return distance < 500000;
	}
	
	@Override
	public RadarTargetType getTargetType() {
		return RadarTargetType.MIRVLET;
	}


	

	
	@Override
    public void init(Ticket ticket) {
	   if(!worldObj.isRemote) {
		
          if(ticket != null) {
        	
              if(loaderTicket == null) {
            	
            	loaderTicket = ticket;
            	loaderTicket.bindEntity(this);
            	loaderTicket.getModData();
              }

            ForgeChunkManager.forceChunk(loaderTicket, new ChunkCoordIntPair(chunkCoordX, chunkCoordZ));
        }
     }
   }

   List<ChunkCoordIntPair> loadedChunks = new ArrayList<ChunkCoordIntPair>();
   public void loadNeighboringChunks(int newChunkX, int newChunkZ)
   {
       if(!worldObj.isRemote && loaderTicket != null)
       {
           for(ChunkCoordIntPair chunk : loadedChunks)
           {
            ForgeChunkManager.unforceChunk(loaderTicket, chunk);
           }

            loadedChunks.clear();
            loadedChunks.add(new ChunkCoordIntPair(newChunkX, newChunkZ));
            loadedChunks.add(new ChunkCoordIntPair(newChunkX + 1, newChunkZ + 1));
            loadedChunks.add(new ChunkCoordIntPair(newChunkX - 1, newChunkZ - 1));
            loadedChunks.add(new ChunkCoordIntPair(newChunkX + 1, newChunkZ - 1));
            loadedChunks.add(new ChunkCoordIntPair(newChunkX - 1, newChunkZ + 1));
            loadedChunks.add(new ChunkCoordIntPair(newChunkX + 1, newChunkZ));
            loadedChunks.add(new ChunkCoordIntPair(newChunkX, newChunkZ + 1));
            loadedChunks.add(new ChunkCoordIntPair(newChunkX - 1, newChunkZ));
            loadedChunks.add(new ChunkCoordIntPair(newChunkX, newChunkZ - 1));

        for(ChunkCoordIntPair chunk : loadedChunks)
        {
            ForgeChunkManager.forceChunk(loaderTicket, chunk);
        }
    }
}

@Override
protected void onImpact(MovingObjectPosition mop) {
	
}
}
