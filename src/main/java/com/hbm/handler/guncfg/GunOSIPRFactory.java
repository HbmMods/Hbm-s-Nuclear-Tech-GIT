package com.hbm.handler.guncfg;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.hbm.blocks.generic.RedBarrel;
import com.hbm.calc.EasyLocation;
import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.interfaces.ILocationProvider;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.particle.SpentCasingConfig;
import com.hbm.particle.SpentCasingConfigBuilder;
import com.hbm.particle.SpentCasingConfig.CasingType;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.potion.HbmPotion;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

public class GunOSIPRFactory {
	
	static final SpentCasingConfig CASING_AR2 = new SpentCasingConfigBuilder("ar2", CasingType.AR2, false)
			.setSmokeChance(0).setInitialMotion(Vec3.createVectorHelper(-0.15, 0.2, 0)).setPitchFactor(0.02f)
			.setAfterReload(true).setPosOffset(new EasyLocation(3.5, 0, 0)).build();
	
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
		
		config.casingConfig = Optional.of(CASING_AR2);
		
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
	
	static final float inaccuracy = 1.25f;
	public static BulletConfiguration getPulseConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.gun_osipr_ammo);
		bullet.ammoCount = 30;
		bullet.doesRicochet = false;
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 15;
		bullet.dmgMax = 21;
		bullet.penetration = 24;
		bullet.velocity = 40;
		bullet.style = BulletConfiguration.STYLE_BOLT;
		bullet.trail = 2;
		
		return bullet;
	}
	static final byte ballVel = 2;
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
		bullet.maxAge = 150;
		bullet.velocity = ballVel;
		
		bullet.bHurt = (ball, entity) ->
		{
			if (entity instanceof EntityLivingBase)
			{
				final EntityLivingBase entityLiving = (EntityLivingBase) entity;
				entity.addVelocity(ball.motionX / 2, ball.motionY / 2, ball.motionZ / 2);
				
				if (entity == ball.shooter)
					return;
				
				if (entityLiving.getHealth() <= 1000)
				{
					entityLiving.addPotionEffect(new PotionEffect(HbmPotion.bang.id, 1, 0));
					entityLiving.setLastAttacker(ball.shooter);
				} else if (entityLiving.getHealth() > 1000)
				{
					ball.setDead();
					return;
				}
				
//				tryRedirectBall(ball, entityLiving);
			}
		};
		
		bullet.bRicochet = (ball, x, y, z) ->
		{
			final Block block = ball.worldObj.getBlock(x, y, z);
			if (block instanceof RedBarrel)
				((RedBarrel) block).explode(ball.worldObj, x, y, z);
			
//			tryRedirectBall(ball, null);
		};
		
		bullet.bImpact = (ball, x, y, z) ->
		{
			final Block block = ball.worldObj.getBlock(x, y, z);
			if (block instanceof RedBarrel)
				((RedBarrel) block).explode(ball.worldObj, x, y, z);
			
//			tryRedirectBall(ball, null);
		};
		
		return bullet;
	}
	
	private static void tryRedirectBall(EntityBulletBase ball, EntityLivingBase lastHit)
	{
		if (!ball.worldObj.isRemote)
		{
			final ILocationProvider ballLoc = ILocationProvider.wrap(ball, false), targetLoc;
			final Vec3 newVector;
			final List<Entity> entities = ball.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(ball.posX - 10, ball.posY - 10, ball.posZ - 10, ball.posX + 10, ball.posY + 10, ball.posZ + 10));
			entities.remove(ball);
			entities.remove(ball.shooter);
			entities.remove(lastHit);
			entities.removeIf(e -> Library.isObstructed(ball.worldObj, ballLoc, ILocationProvider.wrap(e, false)));
			if (entities.isEmpty())
				return;
			
			entities.sort(Comparator.comparing(e -> ILocationProvider.distance(ILocationProvider.wrap(e, false), ballLoc)));
			
			targetLoc = ILocationProvider.wrap(entities.get(0), false);
			
			System.out.println(ballLoc);
			System.out.println(targetLoc);
			System.out.println(Vec3.createVectorHelper(ball.motionX, ball.motionY, ball.motionZ));
			
			final double oldMagnitude  = Math.sqrt(ball.motionX * ball.motionX + ball.motionY * ball.motionY + ball.motionZ * ball.motionZ), newMagnitude;
			newVector = Vec3.createVectorHelper(
					targetLoc.posX() - ball.motionX,
					targetLoc.posY() - ball.motionY,
					targetLoc.posZ() - ball.motionZ
					);
			newMagnitude = Math.sqrt(newVector.xCoord * newVector.xCoord + newVector.yCoord * newVector.yCoord + newVector.zCoord * newVector.zCoord);

			ball.motionX = newVector.xCoord * oldMagnitude / newMagnitude;
			ball.motionY = newVector.yCoord * oldMagnitude / newMagnitude;
			ball.motionZ = newVector.zCoord * oldMagnitude / newMagnitude;
						
			System.out.println(Vec3.createVectorHelper(ball.motionX, ball.motionY, ball.motionZ));
		}
	}
}
