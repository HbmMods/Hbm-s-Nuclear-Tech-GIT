package com.hbm.entity.missile;

import com.hbm.blocks.ModBlocks;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.util.ParticleUtil;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityMinerRocket extends Entity {
	//0 landing, 1 unloading, 2 lifting
	public int timer = 0;

	public EntityMinerRocket(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
        this.setSize(1F, 3F);
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(16, 0);
		this.dataWatcher.addObject(17, 0);
	}
	
	@Override
	public void onUpdate() {
		if(dataWatcher.getWatchableObjectInt(16) == 0)
			motionY = -0.75;
		if(dataWatcher.getWatchableObjectInt(16) == 1)
			motionY = 0;
		if(dataWatcher.getWatchableObjectInt(16) == 2)
			motionY = 1;
		
		motionX = 0;
		motionZ = 0;
		
		this.setPositionAndRotation(posX + motionX, posY + motionY, posZ + motionZ, 0.0F, 0.0F);

		if(dataWatcher.getWatchableObjectInt(16) == 0 && worldObj.getBlock((int)(posX - 0.5), (int)(posY - 0.5), (int)(posZ - 0.5)) == ModBlocks.sat_dock) {
			dataWatcher.updateObject(16, 1);
			motionY = 0;
			posY = (int)posY;
		} else if(worldObj.getBlock((int)(posX - 0.5), (int)(posY + 1), (int)(posZ - 0.5)).getMaterial() != Material.air && !worldObj.isRemote && dataWatcher.getWatchableObjectInt(16) != 1) {
			this.setDead();
			ExplosionLarge.explodeFire(worldObj, posX - 0.5, posY, posZ - 0.5, 10F, true, false, true);
			//worldObj.setBlock((int)(posX - 0.5), (int)(posY + 0.5), (int)(posZ - 0.5), Blocks.dirt);
		}
		
		if(dataWatcher.getWatchableObjectInt(16) == 1) {
			if(!worldObj.isRemote && ticksExisted % 4 == 0)
				ExplosionLarge.spawnShock(worldObj, posX, posY, posZ, 1 + rand.nextInt(3), 1 + rand.nextGaussian());
			
			timer++;
			
			if(timer > 100) {
				dataWatcher.updateObject(16, 2);
			}
		}
		
		if(dataWatcher.getWatchableObjectInt(16) != 1 && !worldObj.isRemote && ticksExisted % 2 == 0) {
			ParticleUtil.spawnGasFlame(worldObj, posX, posY - 0.5, posZ, 0.0, -1.0, 0.0);
		}
		
		if(dataWatcher.getWatchableObjectInt(16) == 2 && posY > 300)
			this.setDead();
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		dataWatcher.updateObject(16, nbt.getInteger("mode"));
		dataWatcher.updateObject(17, nbt.getInteger("sat"));
		timer = nbt.getInteger("timer");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("mode", dataWatcher.getWatchableObjectInt(16));
		nbt.setInteger("sat", dataWatcher.getWatchableObjectInt(17));
		nbt.setInteger("timer", timer);
	}
}
