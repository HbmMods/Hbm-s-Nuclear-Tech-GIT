package com.hbm.entity.mob.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIStepTowardsTarget extends EntityAIBase {

    private final EntityCreature entity;
    private final double stepDistance;
    private final double speed;
    private final int stepDuration;
    private final int stepCooldown;
    private final double minDistance;
    private int stepTimer;

    public EntityAIStepTowardsTarget(EntityCreature entity, double stepDistance, double speed, int stepDuration, int stepCooldown, double minDistance) {
        this.entity = entity;
        this.stepDistance = stepDistance;
        this.speed = speed;
        this.stepDuration = stepDuration;
        this.stepCooldown = stepCooldown;
        this.minDistance = minDistance;
        this.stepTimer = 0;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        return this.entity.getAttackTarget() != null && this.entity.getAttackTarget().isEntityAlive();
    }

    @Override
    public void startExecuting() {
        this.stepTimer = 0;
    }

    @Override
    public boolean continueExecuting() {
        return this.entity.getAttackTarget() != null && this.entity.getAttackTarget().isEntityAlive();
    }

    @Override
    public void updateTask() {
        if (this.stepTimer > 0) {
            this.stepTimer--;
        } else {
            EntityLivingBase target = this.entity.getAttackTarget();
            if (target != null && target.isEntityAlive()) {
                double dx = target.posX - this.entity.posX;
                double dz = target.posZ - this.entity.posZ;
                double distance = Math.sqrt(dx * dx + dz * dz);
    			this.entity.playSound("hbm:entity.beep", 1.0F, 1.0F);

                if (distance > minDistance) {
                    dx = (dx / distance) * stepDistance;
                    dz = (dz / distance) * stepDistance;

                    this.entity.getNavigator().tryMoveToXYZ(this.entity.posX + dx, this.entity.posY, this.entity.posZ + dz, this.speed);
                }

                this.stepTimer = stepDuration + stepCooldown; // Reset step timer with cooldown
            }
        }
    }
}
