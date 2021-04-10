package com.hbm.entity.effect;

import com.hbm.entity.logic.EntityBalefire;

import net.minecraft.world.World;

public class EntityVortex extends EntityBlackHole {

	EntityBalefire bf = new EntityBalefire(worldObj);
	int disYield;

	public EntityVortex(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
	}

	public EntityVortex(World world, float size) {
		super(world);
		this.dataWatcher.updateObject(16, size);
		disYield = (int)Math.ceil(size) * 10;
	}
	
	@Override
	public void onUpdate() {
		bf.posX = this.posX;
		bf.posY = this.posY;
		bf.posZ = this.posZ;
		this.dataWatcher.updateObject(16, this.dataWatcher.getWatchableObjectFloat(16) - 0.0025F);
		if(this.dataWatcher.getWatchableObjectFloat(16) <= 0) {
			this.setDead();
			bf.destructionRange = disYield;
			worldObj.spawnEntityInWorld(bf);
			worldObj.spawnEntityInWorld(EntityNukeCloudSmall.statFacBale(worldObj, posX, posY, posZ, this.disYield * 1.5F, 1000));
			return;
		}
		
		super.onUpdate();
	}

}
