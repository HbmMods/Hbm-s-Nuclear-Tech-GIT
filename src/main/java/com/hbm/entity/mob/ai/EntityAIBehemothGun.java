package com.hbm.entity.mob.ai;

import com.hbm.entity.projectile.EntityArtilleryShell;
import com.hbm.entity.projectile.EntityBulletBaseNT;
import com.hbm.handler.BulletConfigSyncingUtil;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.Vec3;

public class EntityAIBehemothGun extends EntityAIBase {
	
	private EntityCreature owner;
    private EntityLivingBase target;
    private int delay;
    private int timer;
    private int switchAttackDistance;
    private boolean artilleryMode;
    private int reloadTimer;
    private int reloadDelay;

	public EntityAIBehemothGun(EntityCreature owner, boolean checkSight, boolean nearbyOnly, int delay, int switchAttackDistance, int reloadDelay) {
		this.owner = owner;
		this.delay = delay;
		this.timer = delay;
		this.switchAttackDistance = switchAttackDistance;
		this.artilleryMode = false;
		this.reloadTimer = reloadDelay;
		this.reloadDelay = reloadDelay;
	}

	@Override
	public boolean shouldExecute() {
        EntityLivingBase entity = this.owner.getAttackTarget();

        if(entity == null) {
            return false;
        } else {
            this.target = entity;
            double dist = Vec3.createVectorHelper(target.posX - owner.posX, target.posY - owner.posY, target.posZ - owner.posZ).lengthVector();
            if(dist > switchAttackDistance) {
                artilleryMode = true;
            } else {
                artilleryMode = false;
            }
            return dist > 2 && dist < 50;
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
			if(artilleryMode) {
				fireArtilleryShell();
			} else {
				fireGatlingBarrage();
			}
			timer = delay;
		}
		this.owner.rotationYaw = this.owner.rotationYawHead;
    }

	private void fireGatlingBarrage() {
		EntityBulletBaseNT bullet = new EntityBulletBaseNT(owner.worldObj, BulletConfigSyncingUtil.WORLDWAR, owner, target, 1.0F, 0);
		owner.worldObj.spawnEntityInWorld(bullet);
		owner.playSound("hbm:weapon.calShoot", 1.0F, 1.0F);
	}

	private void fireArtilleryShell() {
		if(reloadTimer <= 0) {
			EntityArtilleryShell grenade = new EntityArtilleryShell(owner.worldObj);
			grenade.setType(10);
			grenade.setPosition(owner.posX, owner.posY + 10, owner.posZ);
			Vec3 vec = Vec3.createVectorHelper(target.posX - owner.posX, 0, target.posZ - owner.posZ);
			grenade.motionX = vec.xCoord * 0.05D;
			grenade.motionY = 0.5D + owner.getRNG().nextDouble() * 0.5D;
			grenade.motionZ = vec.zCoord * 0.05D;
			grenade.setThrowableHeading(grenade.motionX , grenade.motionY * 6, grenade.motionZ , 5F, 0);
			grenade.setTarget(target.posX, target.posZ, target.posY);
			owner.worldObj.playSoundEffect(owner.posX, owner.posY, owner.posZ, "hbm:turret.jeremy_fire", 25.0F, 1.0F);
			owner.worldObj.spawnEntityInWorld(grenade);
			reloadTimer = reloadDelay;
		} else {
			reloadTimer--;
		}
	}

}