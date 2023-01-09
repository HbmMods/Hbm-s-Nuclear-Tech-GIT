package com.hbm.handler.guncfg;

import com.hbm.calc.EasyLocation;
import com.hbm.handler.BulletConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.particle.SpentCasingConfig;

import net.minecraft.util.Vec3;

public class GunDGKFactory {

	public static final SpentCasingConfig CASING_DGK = Gun50BMGFactory.CONFIG_LUNA.toBuilder("dgk")
			.setInitialMotion(Vec3.createVectorHelper(0.8, 1, 0)).setPosOffset(new EasyLocation(0.15, 1.5, -1.5))
			.setOverrideColor(false).setPitchFactor(0.1f).setYawFactor(0.08f)
			.build();
	
	public static BulletConfiguration getDGKConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		bullet.ammo = new ComparableStack(ModItems.ammo_dgk);
		return bullet;
	}

}