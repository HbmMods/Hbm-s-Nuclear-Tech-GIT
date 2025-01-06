package com.hbm.entity.mob.ai;

import com.hbm.entity.mob.IFlyingCreature;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIStartFlying extends EntityAIBase {
	
	private EntityLivingBase living;
	private IFlyingCreature flying;
	
	public EntityAIStartFlying(EntityLivingBase living, IFlyingCreature flying) {
		this.living = living;
		this.flying = flying;
	}

	@Override
	public boolean shouldExecute() {
		//take off if attacked, on fire or at random (avg 30s)
		return this.flying.getFlyingState() == this.flying.STATE_WALKING && (this.living.getAITarget() != null || this.living.isBurning() || this.living.getRNG().nextInt(600) == 0);
	}

	@Override
	public void startExecuting() {
		this.flying.setFlyingState(this.flying.STATE_FLYING);
	}
}
