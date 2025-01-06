package com.hbm.entity.mob.ai;

import java.util.function.Predicate;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;

public class EntityAIPanicConditional extends EntityAIBase {

	private EntityCreature creature;
	private double speed;
	private Predicate condition;
	private double randPosX;
	private double randPosY;
	private double randPosZ;

	public EntityAIPanicConditional(EntityCreature creature, double speed, Predicate condition) {
		this.creature = creature;
		this.speed = speed;
		this.condition = condition;
		this.setMutexBits(1);
	}
	
	@Override
	public boolean shouldExecute() {
		
		if(!condition.test(creature)) return false;
		
		if(this.creature.getAITarget() == null && !this.creature.isBurning()) {
			return false;
		} else {
			Vec3 vec3 = RandomPositionGenerator.findRandomTarget(this.creature, 5, 4);

			if(vec3 == null) {
				return false;
			} else {
				this.randPosX = vec3.xCoord;
				this.randPosY = vec3.yCoord;
				this.randPosZ = vec3.zCoord;
				return true;
			}
		}
	}

	@Override
	public void startExecuting() {
		this.creature.getNavigator().tryMoveToXYZ(this.randPosX, this.randPosY, this.randPosZ, this.speed);
	}

	@Override
	public boolean continueExecuting() {
		return !this.creature.getNavigator().noPath() && condition.test(creature);
	}
}
