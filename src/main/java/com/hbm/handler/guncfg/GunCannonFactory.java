package com.hbm.handler.guncfg;

import com.hbm.calc.EasyLocation;
import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.entity.logic.EntityNukeExplosionMK4;
import com.hbm.handler.BulletConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.particle.SpentCasingConfig;
import com.hbm.particle.SpentCasingConfigBuilder;
import com.hbm.particle.SpentCasingConfig.CasingType;

import net.minecraft.util.Vec3;

public class GunCannonFactory {
	
	private static final SpentCasingConfigBuilder CASING_CANNON_BUILDER = new SpentCasingConfigBuilder("240", CasingType.BRASS_BOTTLENECK, false)
			.setInitialMotion(Vec3.createVectorHelper(0, 0.2, -1)).setPosOffset(new EasyLocation(0, 1.75, 0)).setSmokeChance(0)
			.setScaleX(10).setScaleY(10).setScaleZ(10);
	public static final SpentCasingConfig
			CASING_240 = CASING_CANNON_BUILDER.build(),
			
			CASING_16IN = CASING_CANNON_BUILDER.setRegistryName("16inch").setInitialMotion(Vec3.createVectorHelper(0, 1, -1.75))
			.setScaleX(20).setScaleY(20).setScaleZ(25)
			.build();
	
	static final int stockPen = 10000;
	static byte i = 0;
	public static BulletConfiguration getShellConfig() {
		
		final BulletConfiguration bullet = BulletConfigFactory.standardShellConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_shell, 1, i++);
		bullet.dmgMin = 225;
		bullet.dmgMax = 235;
		bullet.penetration = 40;
		bullet.explosive = 20F;
		bullet.blockDamage = false;
		
		return bullet;
	}

	public static BulletConfiguration getShellExplosiveConfig() {
		
		final BulletConfiguration bullet = BulletConfigFactory.standardShellConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_shell, 1, i++);
		bullet.dmgMin = 235;
		bullet.dmgMax = 245;
		bullet.penetration = 50;
		bullet.explosive = 25F;
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
		
		bullet.bImpact = (projectile, x, y, z) -> BulletConfigFactory.nuclearExplosion(projectile, (int) projectile.posX, (int) projectile.posY, (int) projectile.posZ, 1);
		
		return bullet;
	}
	
	public static BulletConfiguration getShellW9FullConfig()
	{
		final BulletConfiguration bullet = getShellW9Config().clone();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_shell, 1, 5);
		
		bullet.bImpact = (projectile, x, y, z) ->
		{
			projectile.worldObj.playSoundEffect(x, y, z, "random.explode", 1.0f, projectile.worldObj.rand.nextFloat() * 0.1F + 0.9F);

			projectile.worldObj.spawnEntityInWorld(EntityNukeExplosionMK4.statFac(projectile.worldObj, BombConfig.boyRadius, projectile.posX + 0.5, projectile.posY + 0.5, projectile.posZ + 0.5));
			projectile.worldObj.spawnEntityInWorld(EntityNukeCloudSmall.statFac(projectile.worldObj, projectile.posX, projectile.posY, projectile.posZ, BombConfig.boyRadius));
		};
		
		return bullet;
	}

}