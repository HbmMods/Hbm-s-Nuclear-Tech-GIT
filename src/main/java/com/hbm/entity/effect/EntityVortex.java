package com.hbm.entity.effect;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityVortex extends EntityBlackHole {
	
	public float shrinkRate = 0.0025F;

	public EntityVortex(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
	}

	public EntityVortex(World world, float size) {
		super(world);
		this.dataWatcher.updateObject(16, size);
	}
	
	public EntityVortex setShrinkRate(float shrinkRate) {
		this.shrinkRate = shrinkRate;
		return this;
	}
	
	@Override
	public void onUpdate() {
		
		this.dataWatcher.updateObject(16, this.dataWatcher.getWatchableObjectFloat(16) - shrinkRate);
		if(this.dataWatcher.getWatchableObjectFloat(16) <= 0) {
			this.setDead();
			return;
		}
		
		super.onUpdate();
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		this.shrinkRate = nbt.getFloat("shrinkRate");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setFloat("shrinkRate", this.shrinkRate);
	}
}
