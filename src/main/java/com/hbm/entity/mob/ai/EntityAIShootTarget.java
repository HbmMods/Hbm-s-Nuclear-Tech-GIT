package com.hbm.entity.mob.ai;

import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.handler.BulletConfigSyncingUtil;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.Vec3;

public class EntityAIShootTarget extends EntityAIBase {
	
	private EntityCreature owner;
    private EntityLivingBase target;
    int delay;
    int timer;

	public EntityAIShootTarget(EntityCreature owner, boolean checkSight, boolean nearbyOnly, int delay) {
		this.owner = owner;
		this.delay = delay;
		timer = delay;
	}

	@Override
	public boolean shouldExecute() {
		
        EntityLivingBase entity = this.owner.getAttackTarget();

        if(entity == null) {
            return false;
            
        } else {
            this.target = entity;
            double dist = Vec3.createVectorHelper(target.posX - owner.posX, target.posY - owner.posY, target.posZ - owner.posZ).lengthVector();
            return dist > 5 && dist < 10;
        }
	}
	
	@Override
    public boolean continueExecuting() {
        return this.shouldExecute() || !this.owner.getNavigator().noPath();
    }

	@Override
    public void updateTask() {
    	
		timer--;
		
		if(timer <= 0) {
			timer = delay;

			EntityBulletBase bullet = new EntityBulletBase(owner.worldObj, BulletConfigSyncingUtil.G20_SHRAPNEL, owner, target, 1.6F, 0);
			owner.worldObj.spawnEntityInWorld(bullet);
			owner.playSound("hbm:weapon.sawShoot", 1.0F, 2.0F);
		}
    }
}
