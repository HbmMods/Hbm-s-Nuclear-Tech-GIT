package com.hbm.entity.mob.ai;

import java.util.function.Predicate;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;

public class EntityAIWanderConditional extends EntityAIBase {
	
	private EntityCreature creature;
	private double speed;
	private Predicate condition;
	private double xPosition;
	private double yPosition;
	private double zPosition;

	public EntityAIWanderConditional(EntityCreature creature, double speed, Predicate condition) {
		this.creature = creature;
		this.speed = speed;
		this.condition = condition;
		this.setMutexBits(1);
	}

	@Override
	public boolean shouldExecute() {
		
		if(!condition.test(creature)) return false;
		
		if(this.creature.getAge() >= 100) {
			return false;
		} else if(this.creature.getRNG().nextInt(120) != 0) {
			return false;
		} else {
			Vec3 vec3 = RandomPositionGenerator.findRandomTarget(this.creature, 10, 7);

			if(vec3 == null) {
				return false;
			} else {
				this.xPosition = vec3.xCoord;
				this.yPosition = vec3.yCoord;
				this.zPosition = vec3.zCoord;
				return true;
			}
		}
	}

	@Override
	public boolean continueExecuting() {
		return !this.creature.getNavigator().noPath() && condition.test(creature);
	}

	@Override
	public void startExecuting() {
		this.creature.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.speed);
	}
}
