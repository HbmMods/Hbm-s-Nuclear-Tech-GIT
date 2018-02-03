package com.hbm.entity.logic;

import com.hbm.explosion.ExplosionLarge;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityNukeExplosionMK4 extends Entity {
	
	//Strength of the blast
	public long strength;
	//How many rays should be created
	public long count;
	//How many rays are calculated per tick
	public int speed;
	//How many rays have already been processed
	public long done;

	public EntityNukeExplosionMK4(World p_i1582_1_) {
		super(p_i1582_1_);
	}
	
	public EntityNukeExplosionMK4(World world, long strength, long count, int speed) {
		super(world);
		this.strength = strength;
		this.count = count;
		this.speed = speed;
	}
	
	@Override
	public void onUpdate() {
		ExplosionLarge.destructionRay(worldObj, posX, posY, posZ, speed, strength);
		done += speed;
		if(done >= count)
			this.setDead();
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

}
