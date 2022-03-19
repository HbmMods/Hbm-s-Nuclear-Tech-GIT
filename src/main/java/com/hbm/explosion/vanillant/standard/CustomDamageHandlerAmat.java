package com.hbm.explosion.vanillant.standard;

import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.interfaces.ICustomDamageHandler;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class CustomDamageHandlerAmat implements ICustomDamageHandler {
	
	protected float radiation;
	
	public CustomDamageHandlerAmat(float radiation) {
		this.radiation = radiation;
	}

	@Override
	public void handleAttack(ExplosionVNT explosion, Entity entity, double distanceScaled) {
		if(entity instanceof EntityLivingBase)
			ContaminationUtil.contaminate((EntityLivingBase)entity, HazardType.RADIATION, ContaminationType.CREATIVE, (float) (radiation * (1D - distanceScaled) * explosion.size));
	}
}
