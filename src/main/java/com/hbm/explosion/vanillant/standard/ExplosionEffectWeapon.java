package com.hbm.explosion.vanillant.standard;

import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.interfaces.IExplosionSFX;
import com.hbm.particle.helper.ExplosionSmallCreator;

import net.minecraft.world.World;

public class ExplosionEffectWeapon implements IExplosionSFX {
	
	int cloudCount;
	float cloudScale;
	float cloudSpeedMult;
	
	public ExplosionEffectWeapon(int cloudCount, float cloudScale, float cloudSpeedMult) {
		this.cloudCount = cloudCount;
		this.cloudScale = cloudScale;
		this.cloudSpeedMult = cloudSpeedMult;
	}

	@Override
	public void doEffect(ExplosionVNT explosion, World world, double x, double y, double z, float size) {
		if(world.isRemote) return;
		
		ExplosionSmallCreator.composeEffect(world, x, y, z, cloudCount, cloudScale, cloudSpeedMult);
	}
}
