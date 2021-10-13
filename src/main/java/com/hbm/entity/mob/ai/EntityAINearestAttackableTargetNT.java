package com.hbm.entity.mob.ai;

import java.util.Collections;
import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget.Sorter;
import net.minecraft.entity.ai.EntityAITarget;

public class EntityAINearestAttackableTargetNT extends EntityAITarget {

	private final Class targetClass;
	private final int targetChance;
	private final Sorter theNearestAttackableTargetSorter;
	private final IEntitySelector targetEntitySelector;
	private EntityLivingBase targetEntity;
	private final double searchRange;

	public EntityAINearestAttackableTargetNT(EntityCreature owner, Class targetClass, int targetChance, boolean shouldCheckSight, boolean nearbyOnly, final IEntitySelector selector, double range) {
		super(owner, shouldCheckSight, nearbyOnly);
		this.targetClass = targetClass;
		this.targetChance = targetChance;
		this.theNearestAttackableTargetSorter = new Sorter(owner);
		this.searchRange = range;
		setMutexBits(1);

		this.targetEntitySelector = new IEntitySelector() {

			@Override
			public boolean isEntityApplicable(Entity entity) {
				return selector != null && !selector.isEntityApplicable(entity) ? false : !(entity instanceof EntityLivingBase) ? false : EntityAINearestAttackableTargetNT.this.isSuitableTarget((EntityLivingBase) entity, false);
			}
		};
	}

	@Override
	protected double getTargetDistance() {
		return this.searchRange;
	}

	@Override
	public boolean shouldExecute() {

		if((this.targetChance > 0) && (this.taskOwner.getRNG().nextInt(this.targetChance) != 0)) {
			return false;
		}
		double range = getTargetDistance();
		List targets = this.taskOwner.worldObj.selectEntitiesWithinAABB(this.targetClass, this.taskOwner.boundingBox.expand(range, range, range), this.targetEntitySelector);
		Collections.sort(targets, this.theNearestAttackableTargetSorter);
		
		if(targets.isEmpty()) {
			return false;
		}
		this.targetEntity = ((EntityLivingBase) targets.get(0));
		return true;
	}

	@Override
	public void resetTask() {
		this.taskOwner.setAttackTarget(targetEntity);
	}
}
