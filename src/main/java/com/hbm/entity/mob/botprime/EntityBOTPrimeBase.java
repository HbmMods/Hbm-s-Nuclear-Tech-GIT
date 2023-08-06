package com.hbm.entity.mob.botprime;

import com.hbm.entity.projectile.EntityBulletBaseNT;
import com.hbm.handler.BulletConfigSyncingUtil;

import api.hbm.entity.IRadiationImmune;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class EntityBOTPrimeBase extends EntityWormBaseNT implements IRadiationImmune {

	public int attackCounter = 0;

	protected final IEntitySelector selector = new IEntitySelector() {

		@Override
		public boolean isEntityApplicable(Entity ent) {

			if(ent instanceof EntityWormBaseNT && ((EntityWormBaseNT) ent).getHeadID() == EntityBOTPrimeBase.this.getHeadID())
				return false;

			return true;
		}

	};

	public EntityBOTPrimeBase(World world) {
		super(world);
		this.setSize(2.0F, 2.0F);
		this.isImmuneToFire = true;
		this.isAirBorne = true;
		this.noClip = true;
		this.renderDistanceWeight = 15.0D;
		this.dragInAir = 0.995F;
		this.dragInGround = 0.98F;
		this.knockbackDivider = 1.0D;
	}

	public boolean canEntityBeSeenThroughNonSolids(Entity p_70685_1_) {
		return this.worldObj.func_147447_a(Vec3.createVectorHelper(this.posX, this.posY + (double) this.getEyeHeight(), this.posZ), Vec3.createVectorHelper(p_70685_1_.posX, p_70685_1_.posY + (double) p_70685_1_.getEyeHeight(), p_70685_1_.posZ), false, true, false) == null;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15000.0D);
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
	}

	@Override
	protected boolean isAIEnabled() {
		return true;
	}

	@Override
	protected boolean canDespawn() {
		return false;
	}

	protected String getHurtSound() {
		return "mob.blaze.hit";
	}

	protected String getDeathSound() {
		return "hbm:entity.bombDet";
	}

	protected void laserAttack(Entity target, boolean head) {

		if(!(target instanceof EntityLivingBase))
			return;

		EntityLivingBase living = (EntityLivingBase) target;

		if(head) {

			for(int i = 0; i < 5; i++) {

				EntityBulletBaseNT bullet = new EntityBulletBaseNT(this.worldObj, BulletConfigSyncingUtil.WORM_LASER, this, living, 1.0F, i * 0.05F);
				this.worldObj.spawnEntityInWorld(bullet);
			}

			this.playSound("hbm:weapon.ballsLaser", 5.0F, 0.75F);

		} else {
			EntityBulletBaseNT bullet = new EntityBulletBaseNT(this.worldObj, BulletConfigSyncingUtil.WORM_BOLT, this, living, 0.5F, 0.125F);
			this.worldObj.spawnEntityInWorld(bullet);
			this.playSound("hbm:weapon.ballsLaser", 5.0F, 1.0F);
		}
	}
}
