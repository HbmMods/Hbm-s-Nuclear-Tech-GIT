package com.hbm.handler.guncfg;

import java.util.ArrayList;
import java.util.HashMap;

import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.lib.HbmCollection;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.potion.HbmPotion;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationKeyframe;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;

public class Gun556mmFactory {

	public static GunConfiguration getEuphieConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 2;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.hasSights = false;
		config.reloadDuration = 20;
		config.firingDuration = 0;
		config.ammoCap = 40;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CROSS;
		config.durability = 10000;
		config.reloadSound = GunConfiguration.RSOUND_MAG;
		config.firingSound = "hbm:weapon.hksShoot";
		config.reloadSoundEnd = false;
		
		config.name = "baeAR";
		config.manufacturer = EnumGunManufacturer.BAE;
		
		config.comment.add("Why is this gun so sticky?");
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.R556_NORMAL);
		config.config.add(BulletConfigSyncingUtil.R556_GOLD);
		config.config.add(BulletConfigSyncingUtil.R556_TRACER);
		config.config.add(BulletConfigSyncingUtil.R556_PHOSPHORUS);
		config.config.add(BulletConfigSyncingUtil.R556_AP);
		config.config.add(BulletConfigSyncingUtil.R556_DU);
		config.config.add(BulletConfigSyncingUtil.R556_STAR);
		config.config.add(BulletConfigSyncingUtil.CHL_R556);
		config.config.add(BulletConfigSyncingUtil.R556_SLEEK);
		config.config.add(BulletConfigSyncingUtil.R556_K);
		
		return config;
	}
	
	public static GunConfiguration getSPIWConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 3;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.hasSights = true;
		config.reloadDuration = 25;
		config.firingDuration = 0;
		config.ammoCap = 20;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_BOX;
		config.durability = 7000;
		config.reloadSound = GunConfiguration.RSOUND_MAG;
		config.firingSound = "hbm:weapon.hksShoot";
		config.reloadSoundEnd = false;
		
		config.animations.put(AnimType.CYCLE, new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0.5, 0, 0, 25))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 75))
						)
				);
		
		config.name = "spiw";
		config.manufacturer = EnumGunManufacturer.H_AND_R;
		
		config.comment.add("Launch some flechettes in the breeze");
		config.comment.add("Find his arms nailed to the trees");
		config.comment.add("Napalm sticks to kids");
		
		config.config = new ArrayList<Integer>();
		config.config.addAll(HbmCollection.NATOFlechette);
		
		return config;
	}
	
	public static GunConfiguration getGLauncherConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 60;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.hasSights = true;
		config.reloadDuration = 40;
		config.firingDuration = 0;
		config.ammoCap = 0;
		config.reloadType = GunConfiguration.RELOAD_NONE;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CIRCUMFLEX;
		config.firingSound = "hbm:weapon.glauncher";
		config.reloadSound = GunConfiguration.RSOUND_GRENADE;
		config.reloadSoundEnd = false;
		
		config.config = new ArrayList<Integer>();
		config.config.addAll(HbmCollection.grenade);
		
		return config;
	}
	
	public static GunConfiguration getMLRConfig()
	{
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 2;
		config.roundsPerCycle = 1;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.hasSights = true;
		config.reloadDuration = 20;
		config.ammoCap = 45;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.BOX;
		config.durability = 500000;
		config.reloadSound = "hbm:weapon.carbineMagInPB3";
		config.firingSound = "hbm:weapon.carbineShootPB3";
		config.reloadSoundEnd = true;
		
		config.name = "lunaAR";
		config.manufacturer = EnumGunManufacturer.LUNA;
		config.comment.add("\"May you never reincarnate again\"");
		
		config.config.addAll(HbmCollection.NATO);
		config.config.addAll(HbmCollection.NATOFlechette);
		
		config.animations = new HashMap<>();
		config.animations.put(AnimType.CYCLE, new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(-0.35, 0, 0, 30))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 30))));
		
		return config;
	}
	
	public static GunConfiguration getG36Config()
	{
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 3;
		config.roundsPerCycle = 1;
		config.firingMode = 1;
		config.hasSights = true;
		config.reloadDuration = 20;
		config.ammoCap = 30;
		config.reloadType = 1;
		config.allowsInfinity = false;
		config.crosshair = Crosshair.CLASSIC;
		config.durability = 4000;
		config.reloadSound = GunConfiguration.RSOUND_MAG;
		config.firingSound = "hbm:weapon.carbineShootPB3";
		config.reloadSoundEnd = false;
		
		config.name = "g36";
		config.manufacturer = EnumGunManufacturer.H_AND_K;
		
		config.config.addAll(HbmCollection.NATO);
		
		config.animations = new HashMap<>();
		config.animations.put(AnimType.CYCLE, new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(-0.35, 0, 0, 30))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 30))));
		
		return config;
	}

	static final float inaccuracy = 1.15F;
	public static BulletConfiguration get556Config() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_556, 1, 0);
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 16;
		bullet.dmgMax = 20;
		bullet.penetration = 20;
		
		return bullet;
	}

	public static BulletConfiguration get556GoldConfig() {
		
		BulletConfiguration bullet = get556Config();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_556, 1, 1);
		bullet.dmgMin = 250;
		bullet.dmgMax = 320;
		bullet.spread = 0.0F;
		
		return bullet;
	}

	public static BulletConfiguration get556PhosphorusConfig() {
		
		BulletConfiguration bullet = get556Config();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_556, 1, 2);
		bullet.wear = 15;
		bullet.incendiary = 5;
		bullet.doesPenetrate = false;
		
		PotionEffect eff = new PotionEffect(HbmPotion.phosphorus.id, 20 * 20, 0, true);
		eff.getCurativeItems().clear();
		bullet.effects = new ArrayList<PotionEffect>();
		bullet.effects.add(new PotionEffect(eff));
		
		bullet.bImpact = (projectile, x, y, z) -> {

			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "vanillaburst");
			data.setString("mode", "flame");
			data.setInteger("count", 15);
			data.setDouble("motion", 0.05D);
			
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, projectile.posX, projectile.posY, projectile.posZ), new TargetPoint(projectile.dimension, projectile.posX, projectile.posY, projectile.posZ, 50));
		};
		
		return bullet;
	}

	public static BulletConfiguration get556APConfig() {
		
		BulletConfiguration bullet = get556Config();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_556, 1, 3);
		bullet.dmgMin = 20;
		bullet.dmgMax = 26;
		bullet.penetration *= 1.5;
		bullet.wear = 15;
		bullet.leadChance = 10;
		
		return bullet;
	}

	public static BulletConfiguration get556DUConfig() {
		
		BulletConfiguration bullet = get556Config();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_556, 1, 4);
		bullet.dmgMin = 24;
		bullet.dmgMax = 32;
		bullet.penetration *= 2;
		bullet.wear = 25;
		bullet.leadChance = 50;
		
		return bullet;
	}

	public static BulletConfiguration get556StarConfig() {
		
		BulletConfiguration bullet = get556Config();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_556, 1, 5);
		bullet.dmgMin = 30;
		bullet.dmgMax = 36;
		bullet.penetration *= 2.5;
		bullet.wear = 25;
		bullet.leadChance = 100;
		
		return bullet;
	}

	public static BulletConfiguration get556SleekConfig() {
		
		BulletConfiguration bullet = get556Config();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_556, 1, 6);
		bullet.dmgMin = 45;
		bullet.dmgMax = 50;
		bullet.wear = 10;
		bullet.leadChance = 100;
		bullet.doesPenetrate = false;
		
		bullet.bHit = (projectile, hit) -> {
				
			if(projectile.worldObj.isRemote)
				return;
			
			EntityBulletBase meteor = new EntityBulletBase(projectile.worldObj, BulletConfigSyncingUtil.MASKMAN_METEOR);
			meteor.setPosition(hit.posX, hit.posY + 30 + meteor.worldObj.rand.nextInt(10), hit.posZ);
			meteor.motionY = -1D;
			meteor.shooter = projectile.shooter;
			projectile.worldObj.spawnEntityInWorld(meteor);
		};
		
		bullet.bImpact = (projectile, x, y, z) -> {

			if(projectile.worldObj.isRemote)
				return;
			
			if(y == -1)
				return;
			
			EntityBulletBase meteor = new EntityBulletBase(projectile.worldObj, BulletConfigSyncingUtil.MASKMAN_METEOR);
			meteor.setPosition(projectile.posX, projectile.posY + 30 + meteor.worldObj.rand.nextInt(10), projectile.posZ);
			meteor.motionY = -1D;
			meteor.shooter = projectile.shooter;
			projectile.worldObj.spawnEntityInWorld(meteor);
		};
		
		return bullet;
	}

	public static BulletConfiguration get556TracerConfig() {
		
		BulletConfiguration bullet = get556Config();

		bullet.ammo = new ComparableStack(ModItems.ammo_556, 1, 7);
		bullet.vPFX = "reddust";
		
		return bullet;
	}

	public static BulletConfiguration get556FlechetteConfig() {
		
		BulletConfiguration bullet = get556Config();

		bullet.ammo = new ComparableStack(ModItems.ammo_556, 1, 8);
		bullet.dmgMin = 26;
		bullet.dmgMax = 32;
		bullet.penetration = 22;
		bullet.HBRC = 2;
		bullet.LBRC = 95;
		bullet.wear = 15;
		bullet.style = BulletConfiguration.STYLE_FLECHETTE;
		bullet.doesPenetrate = false;
		
		return bullet;
	}

	public static BulletConfiguration get556FlechetteIncendiaryConfig() {
		
		BulletConfiguration bullet = get556FlechetteConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_556, 1, 9);
		bullet.incendiary = 5;
		
		return bullet;
	}

	public static BulletConfiguration get556FlechettePhosphorusConfig() {
		
		BulletConfiguration bullet = get556FlechetteConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_556, 1, 10);
		bullet.incendiary = 5;
		
		PotionEffect eff = new PotionEffect(HbmPotion.phosphorus.id, 20 * 20, 0, true);
		eff.getCurativeItems().clear();
		bullet.effects = new ArrayList<PotionEffect>();
		bullet.effects.add(new PotionEffect(eff));
		
		bullet.bImpact = (projectile, x, y, z) -> {

			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "vanillaburst");
			data.setString("mode", "flame");
			data.setInteger("count", 15);
			data.setDouble("motion", 0.05D);
			
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, projectile.posX, projectile.posY, projectile.posZ), new TargetPoint(projectile.dimension, projectile.posX, projectile.posY, projectile.posZ, 50));
		};
		
		return bullet;
	}

	public static BulletConfiguration get556FlechetteDUConfig() {
		
		BulletConfiguration bullet = get556FlechetteConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_556, 1, 11);
		bullet.dmgMin = 46;
		bullet.dmgMax = 52;
		bullet.penetration *= 2.5;
		bullet.wear = 25;
		bullet.leadChance = 50;
		bullet.doesPenetrate = true;
		
		return bullet;
	}

	public static BulletConfiguration get556FlechetteSleekConfig() {
		
		BulletConfiguration bullet = get556FlechetteConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_556, 1, 12);
		bullet.dmgMin = 45;
		bullet.dmgMax = 50;
		bullet.wear = 10;
		bullet.leadChance = 50;
		bullet.doesPenetrate = false;
		
		bullet.bHit = (projectile, hit) -> {

			if(projectile.worldObj.isRemote)
				return;
			
			EntityBulletBase meteor = new EntityBulletBase(projectile.worldObj, BulletConfigSyncingUtil.MASKMAN_METEOR);
			meteor.setPosition(hit.posX, hit.posY + 30 + meteor.worldObj.rand.nextInt(10), hit.posZ);
			meteor.motionY = -1D;
			meteor.shooter = projectile.shooter;
			projectile.worldObj.spawnEntityInWorld(meteor);
		};
		
		bullet.bImpact = (projectile, x, y, z) -> {

			if(projectile.worldObj.isRemote)
				return;
			
			if(y == -1)
				return;
			
			EntityBulletBase meteor = new EntityBulletBase(projectile.worldObj, BulletConfigSyncingUtil.MASKMAN_METEOR);
			meteor.setPosition(projectile.posX, projectile.posY + 30 + meteor.worldObj.rand.nextInt(10), projectile.posZ);
			meteor.motionY = -1D;
			meteor.shooter = projectile.shooter;
			projectile.worldObj.spawnEntityInWorld(meteor);
		};
		
		return bullet;
	}
	
	public static BulletConfiguration get556KConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_556, 1, 13);
		bullet.dmgMin = 0;
		bullet.dmgMax = 0;
		bullet.penetration = 0;
		bullet.maxAge = 0;
		
		return bullet;
	}
}