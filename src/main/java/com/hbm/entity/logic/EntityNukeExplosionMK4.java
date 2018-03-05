package com.hbm.entity.logic;

import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNukeRay;
import com.hbm.main.MainRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityNukeExplosionMK4 extends Entity {
	
	//Strength of the blast
	public int strength;
	//How many rays should be created
	public int count;
	//How many rays are calculated per tick
	public int speed;
	public int length;
	
	ExplosionNukeRay explosion;

	public EntityNukeExplosionMK4(World p_i1582_1_) {
		super(p_i1582_1_);
	}
	
	public EntityNukeExplosionMK4(World world, int strength, int count, int speed, int length) {
		super(world);
		this.strength = strength;
		this.count = count;
		this.speed = speed;
		this.length = length;
	}
	
	@Override
	public void onUpdate() {
		
		if(strength == 0) {
			this.setDead();
			return;
		}
		
		if(explosion == null)
			explosion = new ExplosionNukeRay(worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, this.strength, this.count, this.speed, this.length);
		
		if(explosion.getStoredSize() < count / length) {
			//if(!worldObj.isRemote)
			//MainRegistry.logger.info(explosion.getStoredSize() + " / " + count / length);
				explosion.collectTip(speed * 10);
		} else if(explosion.getProgress() < count / length) {
			//if(!worldObj.isRemote)
			//MainRegistry.logger.info(explosion.getProgress() + " / " + count / length);
				explosion.processTip(speed);
		} else {
			this.setDead();
		}
	}

	@Override
	protected void entityInit() {
		
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		
	}
	
	public static EntityNukeExplosionMK4 statFac(World world, int r, double x, double y, double z) {
		EntityNukeExplosionMK4 mk4 = new EntityNukeExplosionMK4(world);
		mk4.strength = (int)(r / 1.5);
		mk4.count = (int)(4 * Math.PI * Math.pow(mk4.strength, 2) * 25);
		mk4.speed = (int)Math.ceil(100000 / mk4.strength);
		mk4.setPosition(x, y, z);
		mk4.length = mk4.strength / 2;
		return mk4;
	}

}
