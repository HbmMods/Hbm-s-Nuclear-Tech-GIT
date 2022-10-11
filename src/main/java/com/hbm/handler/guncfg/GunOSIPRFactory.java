package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.blocks.generic.RedBarrel;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.lib.ModDamageSource;
import com.hbm.potion.HbmPotion;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;

public class GunOSIPRFactory {
	
	public static GunConfiguration getOSIPRConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 2;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.reloadDuration = 20;
		config.firingDuration = 0;
		config.ammoCap = 30;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_ARROWS;
		config.durability = 50000;
		config.reloadSound = "hbm:weapon.osiprReload";
		config.firingSound = "hbm:weapon.osiprShoot";
		config.reloadSoundEnd = false;
		
		config.name = "osipr";
		config.manufacturer = EnumGunManufacturer.COMBINE;
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.SPECIAL_OSIPR);
		
		return config;
	}
	
	public static GunConfiguration getAltConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 15;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.reloadDuration = 20;
		config.firingDuration = 0;
		config.ammoCap = 0;
		config.reloadType = GunConfiguration.RELOAD_NONE;
		config.allowsInfinity = true;
		config.firingSound = "hbm:weapon.singFlyby";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.SPECIAL_OSIPR_CHARGED);
		
		return config;
	}
	
	public static GunConfiguration getSMGConfig()
	{
		final GunConfiguration config = getOSIPRConfig().clone();
		
		config.name = "pulseSMG";
		config.rateOfFire = 1;
		config.reloadDuration = 10;
		
		return config;
	}
	
	public static GunConfiguration getHMGConfig()
	{
		final GunConfiguration config = getOSIPRConfig().clone();
		
		config.name = "pulseHMG";
		config.rateOfFire = 1;
		config.reloadDuration *= 2;
		config.ammoCap = 120;
		
		return config;
	}

	static final float inaccuracy = 1.5f;
	public static BulletConfiguration getPulseConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.gun_osipr_ammo);
		bullet.ammoCount = 30;
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 15;
		bullet.dmgMax = 21;
		bullet.penetration = 24;
		bullet.velocity = 40;
		bullet.style = BulletConfiguration.STYLE_BOLT;
		bullet.trail = 2;
		
		return bullet;
	}
	public static BulletConfiguration getPulseChargedConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.gun_osipr_ammo2);
		bullet.ricochetAngle = 360;
		bullet.LBRC = 100;
		bullet.HBRC = 100;
		bullet.bounceMod = 1;
		bullet.style = BulletConfiguration.STYLE_ORB;
		bullet.damageType = ModDamageSource.s_combineball;
		bullet.penetration = 50;
		bullet.penetrationModifier = 1;
		bullet.liveAfterImpact = true;
		bullet.spread = 0;
		bullet.gravity = 0;
		bullet.maxAge *= 2;
		bullet.velocity /= 2;
		
		bullet.bHurt = (ball, entity) ->
		{
			if (entity instanceof EntityLivingBase)
			{
				entity.addVelocity(ball.motionX, ball.motionY, ball.motionZ);
				
				if (entity != ball.shooter && ((EntityLivingBase) entity).getHealth() <= 1000)
					((EntityLivingBase) entity).addPotionEffect(new PotionEffect(HbmPotion.bang.id, 1, 0));
			}
		};
		
		bullet.bRicochet = (ball, x, y, z) ->
		{
			final Block block = ball.worldObj.getBlock(x, y, z);
			if (block instanceof RedBarrel)
				((RedBarrel) block).explode(ball.worldObj, x, y, z);
		};
		
		bullet.bImpact = (ball, x, y, z) ->
		{
			final Block block = ball.worldObj.getBlock(x, y, z);
			if (block instanceof RedBarrel)
				((RedBarrel) block).explode(ball.worldObj, x, y, z);
		};
		
		return bullet;
	}
}