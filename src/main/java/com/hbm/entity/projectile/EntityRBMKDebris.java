package com.hbm.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityRBMKDebris extends Entity {
	
	public float rot;
	public float lastRot;

	public EntityRBMKDebris(World world) {
		super(world);
	}

	public EntityRBMKDebris(World world, double x, double y, double z, DebrisType type) {
		super(world);
		this.setPosition(x, y, z);
		this.setType(type);
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(20, 0);
		this.rot = this.lastRot = this.rand.nextFloat() * 360;
	}

	@Override
	public void onUpdate() {
		
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		
		this.motionY -= 0.04D;
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		
		this.lastRot = this.rot;

		if(this.onGround) {
			this.motionX *= 0.85D;
			this.motionZ *= 0.85D;
			this.motionY *= -0.5D;
			
		} else {
			
			this.rot += 10F;
			
			if(rot >= 360F) {
				this.rot -= 360F;
				this.lastRot -= 360F;
			}
		}
		
		if(this.ticksExisted > 1000)
			this.setDead();
	}
	
	public void setType(DebrisType type) {
		this.dataWatcher.updateObject(20, type.ordinal());
	}
	
	public DebrisType getType() {
		return DebrisType.values()[Math.abs(this.dataWatcher.getWatchableObjectInt(20)) % DebrisType.values().length];
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		this.dataWatcher.updateObject(20, nbt.getInteger("debtype"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("debtype", this.dataWatcher.getWatchableObjectInt(20));
	}
	
	public static enum DebrisType {
		BLANK,		//just a metal beam
		ELEMENT,	//the entire casing of a fuel assembly because fuck you
		FUEL,		//spicy
		ROD,		//solid boron rod
		GRAPHITE,	//spicy rock
		LID;		//the all destroying harbinger of annihilation
	}
}
