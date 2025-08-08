package com.hbm.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class WeaponConfig {
	
	public static int ciwsHitrate = 50;

	public static boolean dropCell = true;
	public static boolean dropSing = true;
	public static boolean dropStar = true;
	public static boolean dropCrys = true;
	public static boolean dropDead = true;

	public static boolean linearAnimations = false;
	
	public static void loadFromConfig(Configuration config) {

		final String CATEGORY_MISSILE = CommonConfig.CATEGORY_MISSILE;
		Property propCiwsHitrate = config.get(CATEGORY_MISSILE, "7.03_ciwsAccuracy", 50);
		propCiwsHitrate.comment = "Additional modifier for CIWS accuracy";
		ciwsHitrate = propCiwsHitrate.getInt();

		final String CATEGORY_DROPS = CommonConfig.CATEGORY_DROPS;
		dropCell = CommonConfig.createConfigBool(config, CATEGORY_DROPS, "10.00_dropCell", "Whether antimatter cells should explode when dropped", true);
		dropSing = CommonConfig.createConfigBool(config, CATEGORY_DROPS, "10.01_dropBHole", "Whether singularities and black holes should spawn when dropped", true);
		dropStar = CommonConfig.createConfigBool(config, CATEGORY_DROPS, "10.02_dropStar", "Whether rigged star blaster cells should explode when dropped", true);
		dropCrys = CommonConfig.createConfigBool(config, CATEGORY_DROPS, "10.04_dropCrys", "Whether xen crystals should move blocks when dropped", true);
		dropDead = CommonConfig.createConfigBool(config, CATEGORY_DROPS, "10.05_dropDead", "Whether dead man's explosives should explode when dropped", true);

		final String CATEGORY_WEAPONS = CommonConfig.CATEGORY_WEAPONS;
		linearAnimations = CommonConfig.createConfigBool(config, CATEGORY_WEAPONS, "18.00_linearAnimations", "Should heavily stylised weapon animations be replaced with more conventional ones?", false);

	}
}
