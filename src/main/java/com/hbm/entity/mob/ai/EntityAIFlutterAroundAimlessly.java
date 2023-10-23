package com.hbm.entity.mob.ai;

import com.hbm.entity.mob.IFlyingCreature;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIFlutterAroundAimlessly extends EntityAIBase {
	
	private EntityLivingBase living;
	private IFlyingCreature flying;
	
	public EntityAIFlutterAroundAimlessly(EntityLivingBase living, IFlyingCreature flying) {
		this.living = living;
		this.flying = flying;
	}

	@Override
	public boolean shouldExecute() {
		return this.flying.getFlyingState() == this.flying.STATE_FLYING;
	}

	@Override
	public boolean continueExecuting() {
		return shouldExecute();
	}

	@Override
	public void startExecuting() {

		/*this.living.motionX = this.living.getRNG().nextGaussian() * 0.1;
		this.living.motionY = this.living.getRNG().nextGaussian() * 0.1;
		this.living.motionZ = this.living.getRNG().nextGaussian() * 0.1;*/
	}
}
