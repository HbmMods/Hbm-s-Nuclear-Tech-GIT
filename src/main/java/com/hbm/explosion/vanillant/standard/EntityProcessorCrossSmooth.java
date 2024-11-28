package com.hbm.explosion.vanillant.standard;

import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.util.DamageResistanceHandler.DamageClass;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

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
	
	@Override
	public void attackEntity(Entity entity, ExplosionVNT source, float amount) {
		entity.attackEntityFrom(BulletConfig.getDamage(null, source.exploder instanceof EntityLivingBase ? (EntityLivingBase) source.exploder : null, clazz), amount);
	}

	@Override
	public float calculateDamage(double distanceScaled, double density, double knockback, float size) {
		return (float) (fixedDamage * (1 - distanceScaled));
	}
}
