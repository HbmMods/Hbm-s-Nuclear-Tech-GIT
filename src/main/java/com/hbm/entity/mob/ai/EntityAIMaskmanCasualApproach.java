package com.hbm.entity.mob.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityAIMaskmanCasualApproach extends EntityAIBase {

	World worldObj;
	EntityCreature attacker;
	int attackTick;
	double speedTowardsTarget;
	boolean longMemory;
	PathEntity entityPathEntity;
	Class classTarget;
	private int pathTimer;
	private double lastX;
	private double lastY;
	private double lastZ;

	private int failedPathFindingPenalty;

	public EntityAIMaskmanCasualApproach(EntityCreature owner, Class target, double speed, boolean longMemory) {
		this(owner, speed, longMemory);
		this.classTarget = target;
	}

	public EntityAIMaskmanCasualApproach(EntityCreature owner, double speed, boolean longMemory) {
		this.attacker = owner;
		this.worldObj = owner.worldObj;
		this.speedTowardsTarget = speed;
		this.longMemory = longMemory;
		this.setMutexBits(3);
	}
	
	public boolean shouldExecute() {
		EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();

		if(entitylivingbase == null) {
			return false;
			
		} else if(!entitylivingbase.isEntityAlive()) {
			return false;
			
		} else if(this.classTarget != null && !this.classTarget.isAssignableFrom(entitylivingbase.getClass())) {
			return false;
			
		} else {
			
			if(--this.pathTimer <= 0) {
				
				double[] pos = getApproachPos();
				this.entityPathEntity = this.attacker.getNavigator().getPathToXYZ(pos[0], pos[1], pos[2]);
				this.pathTimer = 4 + this.attacker.getRNG().nextInt(7);
				return this.entityPathEntity != null;
				
			} else {
				return true;
			}
		}
	}

	
	public boolean continueExecuting() {
		
		EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
		
		return entitylivingbase == null ? false
				: (!entitylivingbase.isEntityAlive() ? false
				: (!this.longMemory ? !this.attacker.getNavigator().noPath()
				: this.attacker.isWithinHomeDistance(MathHelper.floor_double(entitylivingbase.posX), MathHelper.floor_double(entitylivingbase.posY), MathHelper.floor_double(entitylivingbase.posZ))));
	}

	
	public void startExecuting() {
		this.attacker.getNavigator().setPath(this.entityPathEntity, this.speedTowardsTarget);
		this.pathTimer = 0;
	}

	
	public void resetTask() {
		this.attacker.getNavigator().clearPathEntity();
	}

	
	public void updateTask() {
		
		EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
		this.attacker.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);
		double d0 = this.attacker.getDistanceSq(entitylivingbase.posX, entitylivingbase.boundingBox.minY, entitylivingbase.posZ);
		
		this.pathTimer--;

		if((this.longMemory || this.attacker.getEntitySenses().canSee(entitylivingbase)) && this.pathTimer <= 0
				&& (this.lastX == 0.0D && this.lastY == 0.0D && this.lastZ == 0.0D
				|| entitylivingbase.getDistanceSq(this.lastX, this.lastY, this.lastZ) >= 1.0D
				|| this.attacker.getRNG().nextFloat() < 0.05F)) {
			
			this.lastX = entitylivingbase.posX;
			this.lastY = entitylivingbase.boundingBox.minY;
			this.lastZ = entitylivingbase.posZ;
			this.pathTimer = failedPathFindingPenalty + 4 + this.attacker.getRNG().nextInt(7);

			if(this.attacker.getNavigator().getPath() != null) {
				
				PathPoint finalPathPoint = this.attacker.getNavigator().getPath().getFinalPathPoint();
				if(finalPathPoint != null && entitylivingbase.getDistanceSq(finalPathPoint.xCoord, finalPathPoint.yCoord, finalPathPoint.zCoord) < 1) {
					failedPathFindingPenalty = 0;
					
				} else {
					failedPathFindingPenalty += 10;
				}
				
			} else {
				failedPathFindingPenalty += 10;
			}

			if(d0 > 1024.0D) {
				this.pathTimer += 10;
			} else if(d0 > 256.0D) {
				this.pathTimer += 5;
			}

			double[] pos = getApproachPos();
			if(!this.attacker.getNavigator().tryMoveToXYZ(pos[0], pos[1], pos[2], speedTowardsTarget)) {
				this.pathTimer += 15;
			}
		}

		this.attackTick = Math.max(this.attackTick - 1, 0);

		/*if(d0 <= d1 && this.attackTick <= 20) {
			this.attackTick = 20;

			if(this.attacker.getHeldItem() != null) {
				this.attacker.swingItem();
			}

			this.attacker.attackEntityAsMob(entitylivingbase);
		}*/
	}
	
	public double[] getApproachPos() {
		
		EntityLivingBase target = this.attacker.getAttackTarget();
		
		Vec3 vec = Vec3.createVectorHelper(this.attacker.posX - target.posX, this.attacker.posY - target.posY, this.attacker.posZ - target.posZ);
		
		double range = Math.min(vec.lengthVector(), 20) - 10;
		
		vec = vec.normalize();
    	
    	double x = this.attacker.posX + vec.xCoord * range + this.attacker.getRNG().nextGaussian() * 2;
    	double y = this.attacker.posY + vec.yCoord - 5 + this.attacker.getRNG().nextInt(11);
    	double z = this.attacker.posZ + vec.zCoord * range + this.attacker.getRNG().nextGaussian() * 2;
    	
    	return new double[] {x, y, z};
	}
}
