package com.hbm.entity.mob.ai;

import com.hbm.entity.projectile.EntityBulletBaseNT;
import com.hbm.handler.BulletConfigSyncingUtil;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.Vec3;

public class EntityAIBehemothGun extends EntityAIBase {
	
	private EntityCreature owner;
    private EntityLivingBase target;
    int delay;
    int timer;

	public EntityAIBehemothGun(EntityCreature owner, boolean checkSight, boolean nearbyOnly, int delay) {
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
            return dist > 10 && dist < 50;
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

			EntityBulletBaseNT bullet = new EntityBulletBaseNT(owner.worldObj, BulletConfigSyncingUtil.WORLDWAR, owner, target, 1.0F, 0);
			owner.worldObj.spawnEntityInWorld(bullet);
			owner.playSound("hbm:weapon.calShoot", 1.0F, 1.0F);
		}
		
		/*
		 			EntityArtilleryShell grenade = new EntityArtilleryShell(owner.worldObj);
			grenade.setType(10);
			grenade.setPosition(owner.posX, owner.posY + 10, owner.posZ);
			Vec3 vec = Vec3.createVectorHelper(target.posX - owner.posX, 0, target.posZ - owner.posZ);
			grenade.motionX = vec.xCoord * 0.05D;
			grenade.motionY = 0.5D + owner.getRNG().nextDouble() * 0.5D;
			grenade.motionZ = vec.zCoord * 0.05D;
			grenade.setThrowableHeading(grenade.motionX, grenade.motionY * 6, grenade.motionZ, 1F, 0);
			grenade.setVelocity(grenade.motionX * 4, grenade.motionY * 6, grenade.motionZ* 4);
			owner.worldObj.playSoundEffect(owner.posX, owner.posY, owner.posZ, "hbm:turret.jeremy_fire", 25.0F, 1.0F);

			owner.worldObj.spawnEntityInWorld(grenade);
		 */
		
		this.owner.rotationYaw = this.owner.rotationYawHead;
    }
}
