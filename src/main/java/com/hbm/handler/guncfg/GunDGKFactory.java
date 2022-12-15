package com.hbm.handler.guncfg;

import com.hbm.handler.BulletConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.particle.SpentCasingConfig;

import net.minecraft.util.Vec3;

public class GunDGKFactory {

	public static final SpentCasingConfig CASING_DGK = Gun50BMGFactory.CONFIG_LUNA.toBuilder("dgk")
			.setInitialMotion(Vec3.createVectorHelper(1, 1, 0)).setPosOffset(null).setOverrideColor(false)
			.build();
	
	public static BulletConfiguration getDGKConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		bullet.ammo = new ComparableStack(ModItems.ammo_dgk);
		return bullet;
	}

}