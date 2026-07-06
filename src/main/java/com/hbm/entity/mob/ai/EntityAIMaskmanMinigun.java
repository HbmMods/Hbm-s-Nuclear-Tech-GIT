package com.hbm.entity.mob.ai;

import com.hbm.entity.projectile.EntityBulletBaseMK4;
import com.hbm.items.weapon.sedna.factory.XFactory762mm;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.Vec3;

public class EntityAIMaskmanMinigun extends EntityAIBase {

	private EntityCreature owner;
	private EntityLivingBase target;
	int delay;
	int timer;

	public EntityAIMaskmanMinigun(EntityCreature owner, boolean checkSight, boolean nearbyOnly, int delay) {
		this.owner = owner;
		this.delay = delay;
		this.timer = delay;
	}

	@Override
	public boolean shouldExecute() {

		EntityLivingBase entity = this.owner.getAttackTarget();

		if(entity == null || !entity.isEntityAlive()) {
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

		// TEST
		if(target != null) this.owner.getLookHelper().setLookPositionWithEntity(this.target, 15F, 15F);

		if(timer <= 0) {
			timer = delay;

			EntityBulletBaseMK4 bullet = new EntityBulletBaseMK4(this.owner, XFactory762mm.r762_fmj, 5F, 0.05F, 0, 0, 0); // TODO: test the offsets
			owner.worldObj.spawnEntityInWorld(bullet);
			owner.playSound("hbm:weapon.calShoot", 1.0F, 1.0F);
		}
	}
}
