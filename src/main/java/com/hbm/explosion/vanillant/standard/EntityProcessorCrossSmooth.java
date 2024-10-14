package com.hbm.explosion.vanillant.standard;

public class EntityProcessorCrossSmooth extends EntityProcessorCross {

	protected float fixedDamage;
	
	public EntityProcessorCrossSmooth(double nodeDist, float fixedDamage) {
		super(nodeDist);
		this.fixedDamage = fixedDamage;
	}
	
	public float calculateDamage(double distanceScaled, double density, double knockback, float size) {
		return (float) (fixedDamage * (1 - distanceScaled));
	}
}
