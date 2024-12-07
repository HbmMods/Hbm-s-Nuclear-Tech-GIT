package com.hbm.explosion.vanillant.standard;

import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.factory.ConfettiUtil;
import com.hbm.util.EntityDamageUtil;
import com.hbm.util.DamageResistanceHandler.DamageClass;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class EntityProcessorCrossSmooth extends EntityProcessorCross {

	protected float fixedDamage;
	protected float pierceDT = 0;
	protected float pierceDR = 0;
	protected DamageClass clazz = DamageClass.EXPLOSIVE;
	
	public EntityProcessorCrossSmooth(double nodeDist, float fixedDamage) {
		super(nodeDist);
		this.fixedDamage = fixedDamage;
	}
	
	public EntityProcessorCrossSmooth setupPiercing(float pierceDT, float pierceDR) {
		this.pierceDT = pierceDT;
		this.pierceDR = pierceDR;
		return this;
	}
	
	public EntityProcessorCrossSmooth setDamageClass(DamageClass clazz) {
		this.clazz = clazz;
		return this;
	}
	
	@Override
	public void attackEntity(Entity entity, ExplosionVNT source, float amount) {
		if(!entity.isEntityAlive()) return;
		DamageSource dmg = BulletConfig.getDamage(null, source.exploder instanceof EntityLivingBase ? (EntityLivingBase) source.exploder : null, clazz);
		if(!(entity instanceof EntityLivingBase)) {
			entity.attackEntityFrom(dmg, amount);
		} else {
			EntityDamageUtil.attackEntityFromNT((EntityLivingBase) entity, dmg, amount, true, false, 0F, pierceDT, pierceDR);
			if(!entity.isEntityAlive()) ConfettiUtil.decideConfetti((EntityLivingBase) entity, dmg);
		}
	}

	@Override
	public float calculateDamage(double distanceScaled, double density, double knockback, float size) {
		return (float) (fixedDamage * (1 - distanceScaled));
	}
}
