package com.hbm.entity.mob.ai;

import java.util.function.Predicate;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

/**
 * Identical to EntityAISwimming, but with an added conditional lambda for maximum reusability.
 * 
 * @author hbm
 */
public class EntityAISwimmingConditional extends EntityAIBase {
	
	private EntityLiving living;
	private Predicate condition;

	public EntityAISwimmingConditional(EntityLiving living, Predicate condition) {
		this.living = living;
		this.condition = condition;
		this.setMutexBits(4);
		living.getNavigator().setCanSwim(true);
	}

	@Override
	public boolean shouldExecute() {
		return (this.living.isInWater() || this.living.handleLavaMovement()) && condition.test(living);
	}

	@Override
	public void updateTask() {
		if(this.living.getRNG().nextFloat() < 0.8F) {
			this.living.getJumpHelper().setJumping();
		}
	}
}
