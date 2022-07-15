package com.hbm.handler.guncfg;

import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.entity.logic.EntityNukeExplosionMK4;
import com.hbm.handler.BulletConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;

public class GunCannonFactory {
	static final int stockPen = 10000;
	static byte i = 0;
	public static BulletConfiguration getShellConfig() {
		
		final BulletConfiguration bullet = BulletConfigFactory.standardShellConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_shell, 1, i++);
		bullet.dmgMin = 225;
		bullet.dmgMax = 235;
		bullet.penetration = stockPen;
		bullet.explosive = 40F;
		bullet.blockDamage = false;
		
		return bullet;
	}

	public static BulletConfiguration getShellExplosiveConfig() {
		
		final BulletConfiguration bullet = BulletConfigFactory.standardShellConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_shell, 1, i++);
		bullet.dmgMin = 235;
		bullet.dmgMax = 245;
		bullet.penetration = stockPen;
		bullet.explosive = 40F;
		bullet.blockDamage = true;
		
		return bullet;
	}

	public static BulletConfiguration getShellAPConfig() {
		
		final BulletConfiguration bullet = BulletConfigFactory.standardShellConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_shell, 1, i++);
		bullet.dmgMin = 250;
		bullet.dmgMax = 255;
		bullet.penetration = (int) (stockPen * 1.5);
		bullet.doesPenetrate = true;
		bullet.style = BulletConfiguration.STYLE_APDS;
		
		return bullet;
	}

	public static BulletConfiguration getShellDUConfig() {
		
		final BulletConfiguration bullet = BulletConfigFactory.standardShellConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_shell, 1, i++);
		bullet.dmgMin = 270;
		bullet.dmgMax = 280;
		bullet.penetration = stockPen * 2;
		bullet.doesPenetrate = true;
		bullet.style = BulletConfiguration.STYLE_APDS;
		
		return bullet;
	}

	public static BulletConfiguration getShellW9Config() {
		
		final BulletConfiguration bullet = BulletConfigFactory.standardShellConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_shell, 1, i++);
		bullet.dmgMin = 1200;
		bullet.dmgMax = 1250;
		bullet.penetration = stockPen;
		
		bullet.bImpact = (projectile, x, y, z) -> {

			BulletConfigFactory.nuclearExplosion(projectile, x, y, z, 1);
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getShellW9FullConfig()
	{
		final BulletConfiguration bullet = getShellW9Config().clone();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_shell, 1, i++);
		
		bullet.bImpact = (projectile, x, y, z) ->
		{
			projectile.worldObj.playSoundEffect(x, y, z, "random.explode", 1.0f, projectile.worldObj.rand.nextFloat() * 0.1F + 0.9F);

			projectile.worldObj.spawnEntityInWorld(EntityNukeExplosionMK4.statFac(projectile.worldObj, BombConfig.boyRadius, x + 0.5, y + 0.5, z + 0.5));
			projectile.worldObj.spawnEntityInWorld(EntityNukeCloudSmall.statFac(projectile.worldObj, x, y, z, BombConfig.boyRadius));
		};
		
		return bullet;
	}

}
