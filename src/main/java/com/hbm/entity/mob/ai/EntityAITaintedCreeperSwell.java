package com.hbm.entity.mob.ai;

import com.hbm.entity.mob.EntityTaintedCreeper;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAITaintedCreeperSwell extends EntityAIBase {
    /** The creeper that is swelling. */
    EntityTaintedCreeper swellingCreeper;
    /** The creeper's attack target. This is used for the changing of the creeper's state. */
    EntityLivingBase creeperAttackTarget;
    public EntityAITaintedCreeperSwell(EntityTaintedCreeper p_i1655_1_)
    {
        this.swellingCreeper = p_i1655_1_;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
	public boolean shouldExecute()
    {
        EntityLivingBase entitylivingbase = this.swellingCreeper.getAttackTarget();
        return this.swellingCreeper.getCreeperState() > 0 || entitylivingbase != null && this.swellingCreeper.getDistanceSqToEntity(entitylivingbase) < 9.0D;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
	public void startExecuting()
    {
        this.swellingCreeper.getNavigator().clearPathEntity();
        this.creeperAttackTarget = this.swellingCreeper.getAttackTarget();
    }

    /**
     * Resets the task
     */
    @Override
	public void resetTask()
    {
        this.creeperAttackTarget = null;
    }

    /**
     * Updates the task
     */
    @Override
	public void updateTask()
    {
        if (this.creeperAttackTarget == null)
        {
            this.swellingCreeper.setCreeperState(-1);
        }
        else if (this.swellingCreeper.getDistanceSqToEntity(this.creeperAttackTarget) > 49.0D)
        {
            this.swellingCreeper.setCreeperState(-1);
        }
        else if (!this.swellingCreeper.getEntitySenses().canSee(this.creeperAttackTarget))
        {
            this.swellingCreeper.setCreeperState(-1);
        }
        else
        {
            this.swellingCreeper.setCreeperState(1);
        }
    }
}
