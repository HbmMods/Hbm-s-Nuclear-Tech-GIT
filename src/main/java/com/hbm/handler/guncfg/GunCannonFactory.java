package com.hbm.handler.guncfg;

import com.hbm.explosion.ExplosionNukeSmall;
import com.hbm.handler.BulletConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ItemAmmoEnums.Ammo240Shell;
import com.hbm.items.ModItems;
import com.hbm.particle.SpentCasing;
import com.hbm.particle.SpentCasing.CasingType;

public class GunCannonFactory {
	
	protected static SpentCasing CASINNG240MM;
	
	static {
		CASINNG240MM = new SpentCasing(CasingType.BOTTLENECK).setScale(7.5F).setBounceMotion(0.5F, 0.5F).setColor(SpentCasing.COLOR_CASE_BRASS).setupSmoke(1F, 0.5D, 60, 20);
	}

	public static BulletConfiguration getShellConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardShellConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_shell.stackFromEnum(Ammo240Shell.STOCK));
		bullet.dmgMin = 25;
		bullet.dmgMax = 35;
		bullet.explosive = 4F;
		bullet.blockDamage = false;
		
		bullet.spentCasing = CASINNG240MM.register("240MM"); //same instance everywhere, only register once
		
		return bullet;
	}

	public static BulletConfiguration getShellExplosiveConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardShellConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_shell.stackFromEnum(Ammo240Shell.EXPLOSIVE));
		bullet.dmgMin = 35;
		bullet.dmgMax = 45;
		bullet.explosive = 4F;
		bullet.blockDamage = true;
		
		bullet.spentCasing = CASINNG240MM;
		
		return bullet;
	}

	public static BulletConfiguration getShellAPConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardShellConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_shell.stackFromEnum(Ammo240Shell.APFSDS_T));
		bullet.dmgMin = 50;
		bullet.dmgMax = 55;
		bullet.doesPenetrate = true;
		bullet.style = BulletConfiguration.STYLE_APDS;
		
		bullet.spentCasing = CASINNG240MM;
		
		return bullet;
	}

	public static BulletConfiguration getShellDUConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardShellConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_shell.stackFromEnum(Ammo240Shell.APFSDS_DU));
		bullet.dmgMin = 70;
		bullet.dmgMax = 80;
		bullet.doesPenetrate = true;
		bullet.style = BulletConfiguration.STYLE_APDS;
		
		bullet.spentCasing = CASINNG240MM;
		
		return bullet;
	}

	public static BulletConfiguration getShellW9Config() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardShellConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_shell.stackFromEnum(Ammo240Shell.W9));
		bullet.dmgMin = 100;
		bullet.dmgMax = 150;
		
		bullet.bntImpact = (bulletnt, x, y, z, sideHit) -> {
			BulletConfigFactory.nuclearExplosion(bulletnt, x, y, z, ExplosionNukeSmall.PARAMS_TOTS);
		};
		
		bullet.spentCasing = CASINNG240MM;
		
		return bullet;
	}

}
