package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.entity.projectile.EntityBulletBaseNT;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.CasingEjector;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ItemAmmoEnums.Ammo556mm;
import com.hbm.items.ModItems;
import com.hbm.lib.HbmCollection;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.particle.SpentCasing;
import com.hbm.particle.SpentCasing.CasingType;
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

	private static final CasingEjector EJECTOR_RIFLE;
	private static final CasingEjector EJECTOR_GRENADE;
	private static final SpentCasing CASING556;

	static {
		EJECTOR_RIFLE = new CasingEjector().setMotion(-0.35, 0.6, 0).setOffset(-0.35, 0, 0.35).setAngleRange(0.01F, 0.03F);
		EJECTOR_GRENADE = new CasingEjector().setAngleRange(0.02F, 0.03F).setDelay(30);
		CASING556 = new SpentCasing(CasingType.BOTTLENECK).setScale(1.25F).setBounceMotion(0.01F, 0.03F).setColor(SpentCasing.COLOR_CASE_BRASS);
	}

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
		
		//config.config = new ArrayList();
		//config.config.add(BulletConfigSyncingUtil.R556_GOLD);
		
		config.config = HbmCollection.r556;
		
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

		config.config = HbmCollection.r556Flechette;
		
		config.ejector = EJECTOR_RIFLE;
		
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
		
		config.config = HbmCollection.grenade;
		
		config.ejector = EJECTOR_GRENADE;
		
		return config;
	}

	private static float inaccuracy = 2.5F;
	public static BulletConfiguration get556Config() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_556.stackFromEnum(Ammo556mm.STOCK));
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 16;
		bullet.dmgMax = 20;
		
		bullet.spentCasing = CASING556.clone().register("556Stock");
		
		return bullet;
	}

	public static BulletConfiguration get556GoldConfig() {
		
		BulletConfiguration bullet = get556Config();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_556.stackFromEnum(Ammo556mm.GOLD));
		bullet.dmgMin = 250;
		bullet.dmgMax = 320;
		bullet.spread = 0.0F;
		
		bullet.spentCasing = null;
		
		return bullet;
	}

	public static BulletConfiguration get556PhosphorusConfig() {
		
		BulletConfiguration bullet = get556Config();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_556.stackFromEnum(Ammo556mm.PHOSPHORUS));
		bullet.wear = 15;
		bullet.incendiary = 5;
		bullet.doesPenetrate = false;
		
		PotionEffect eff = new PotionEffect(HbmPotion.phosphorus.id, 20 * 20, 0, true);
		eff.getCurativeItems().clear();
		bullet.effects = new ArrayList();
		bullet.effects.add(new PotionEffect(eff));
		
		bullet.bntImpact = (bulletnt, x, y, z, sideHit) -> {

			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "vanillaburst");
			data.setString("mode", "flame");
			data.setInteger("count", 15);
			data.setDouble("motion", 0.05D);

			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, bulletnt.posX, bulletnt.posY, bulletnt.posZ), new TargetPoint(bulletnt.dimension, bulletnt.posX, bulletnt.posY, bulletnt.posZ, 50));
		};
		
		bullet.spentCasing = CASING556.clone().register("556Phos");
		
		return bullet;
	}

	public static BulletConfiguration get556APConfig() {
		
		BulletConfiguration bullet = get556Config();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_556.stackFromEnum(Ammo556mm.AP));
		bullet.dmgMin = 20;
		bullet.dmgMax = 26;
		bullet.wear = 15;
		bullet.leadChance = 10;
		
		bullet.spentCasing = CASING556.clone().register("556AP");
		
		return bullet;
	}

	public static BulletConfiguration get556DUConfig() {
		
		BulletConfiguration bullet = get556Config();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_556.stackFromEnum(Ammo556mm.DU));
		bullet.dmgMin = 24;
		bullet.dmgMax = 32;
		bullet.wear = 25;
		bullet.leadChance = 50;
		
		bullet.spentCasing = CASING556.clone().register("556DU");
		
		return bullet;
	}

	public static BulletConfiguration get556StarConfig() {
		
		BulletConfiguration bullet = get556Config();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_556.stackFromEnum(Ammo556mm.STAR));
		bullet.dmgMin = 30;
		bullet.dmgMax = 36;
		bullet.wear = 25;
		bullet.leadChance = 100;
		
		bullet.spentCasing = CASING556.clone().register("556Star");
		
		return bullet;
	}

	public static BulletConfiguration get556SleekConfig() {
		
		BulletConfiguration bullet = get556Config();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_556.stackFromEnum(Ammo556mm.SLEEK));
		bullet.dmgMin = 45;
		bullet.dmgMax = 50;
		bullet.wear = 10;
		bullet.leadChance = 100;
		bullet.doesPenetrate = false;
		
		bullet.bntHit = (bulletnt, hit) -> {

			if(bulletnt.worldObj.isRemote)
				return;

			EntityBulletBaseNT meteor = new EntityBulletBaseNT(bulletnt.worldObj, BulletConfigSyncingUtil.MASKMAN_METEOR);
			meteor.setPosition(hit.posX, hit.posY + 30 + meteor.worldObj.rand.nextInt(10), hit.posZ);
			meteor.motionY = -1D;
			meteor.setThrower(bulletnt.getThrower());
			bulletnt.worldObj.spawnEntityInWorld(meteor);
		};

		bullet.bntImpact = (bulletnt, x, y, z, sideHit) -> {

			if(bulletnt.worldObj.isRemote)
				return;

			if(y == -1)
				return;

			EntityBulletBaseNT meteor = new EntityBulletBaseNT(bulletnt.worldObj, BulletConfigSyncingUtil.MASKMAN_METEOR);
			meteor.setPosition(bulletnt.posX, bulletnt.posY + 30 + meteor.worldObj.rand.nextInt(10), bulletnt.posZ);
			meteor.motionY = -1D;
			meteor.setThrower(bulletnt.getThrower());
			bulletnt.worldObj.spawnEntityInWorld(meteor);
		};
		
		bullet.spentCasing = CASING556.clone().register("556IF");
		
		return bullet;
	}

	public static BulletConfiguration get556TracerConfig() {
		
		BulletConfiguration bullet = get556Config();

		bullet.ammo = new ComparableStack(ModItems.ammo_556.stackFromEnum(Ammo556mm.TRACER));
		bullet.vPFX = "reddust";
		
		bullet.spentCasing = CASING556.clone().register("556Trac");
		
		return bullet;
	}

	public static BulletConfiguration get556FlechetteConfig() {
		
		BulletConfiguration bullet = get556Config();

		bullet.ammo = new ComparableStack(ModItems.ammo_556.stackFromEnum(Ammo556mm.FLECHETTE));
		bullet.dmgMin = 26;
		bullet.dmgMax = 32;
		bullet.HBRC = 2;
		bullet.LBRC = 95;
		bullet.wear = 15;
		bullet.style = BulletConfiguration.STYLE_FLECHETTE;
		bullet.doesPenetrate = false;
		BulletConfigFactory.makeFlechette(bullet);
		
		bullet.spentCasing = CASING556.clone().register("556Flec");
		
		return bullet;
	}

	public static BulletConfiguration get556FlechetteIncendiaryConfig() {
		
		BulletConfiguration bullet = get556FlechetteConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_556.stackFromEnum(Ammo556mm.FLECHETTE_INCENDIARY));
		bullet.incendiary = 5;
		BulletConfigFactory.makeFlechette(bullet);
		
		bullet.spentCasing = CASING556.clone().register("556FlecInc");
		
		return bullet;
	}

	public static BulletConfiguration get556FlechettePhosphorusConfig() {
		
		BulletConfiguration bullet = get556FlechetteConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_556.stackFromEnum(Ammo556mm.FLECHETTE_PHOSPHORUS));
		bullet.incendiary = 5;
		
		PotionEffect eff = new PotionEffect(HbmPotion.phosphorus.id, 20 * 20, 0, true);
		eff.getCurativeItems().clear();
		bullet.effects = new ArrayList();
		bullet.effects.add(new PotionEffect(eff));
		
		bullet.bntImpact = (bulletnt, x, y, z, sideHit) -> {

			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "vanillaburst");
			data.setString("mode", "flame");
			data.setInteger("count", 15);
			data.setDouble("motion", 0.05D);

			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, bulletnt.posX, bulletnt.posY, bulletnt.posZ), new TargetPoint(bulletnt.dimension, bulletnt.posX, bulletnt.posY, bulletnt.posZ, 50));
		};
		
		bullet.spentCasing = CASING556.clone().register("556FlecPhos");
		
		return bullet;
	}

	public static BulletConfiguration get556FlechetteDUConfig() {
		
		BulletConfiguration bullet = get556FlechetteConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_556.stackFromEnum(Ammo556mm.FLECHETTE_DU));
		bullet.dmgMin = 46;
		bullet.dmgMax = 52;
		bullet.wear = 25;
		bullet.leadChance = 50;
		bullet.doesPenetrate = true;
		
		bullet.spentCasing = CASING556.clone().register("556FlecDU");
		
		return bullet;
	}

	public static BulletConfiguration get556FlechetteSleekConfig() {
		
		BulletConfiguration bullet = get556FlechetteConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_556.stackFromEnum(Ammo556mm.FLECHETTE_SLEEK));
		bullet.dmgMin = 45;
		bullet.dmgMax = 50;
		bullet.wear = 10;
		bullet.leadChance = 50;
		bullet.doesPenetrate = false;
		
		bullet.bntHit = (bulletnt, hit) -> {

			if(bulletnt.worldObj.isRemote)
				return;

			EntityBulletBaseNT meteor = new EntityBulletBaseNT(bulletnt.worldObj, BulletConfigSyncingUtil.MASKMAN_METEOR);
			meteor.setPosition(hit.posX, hit.posY + 30 + meteor.worldObj.rand.nextInt(10), hit.posZ);
			meteor.motionY = -1D;
			meteor.setThrower(bulletnt.getThrower());
			bulletnt.worldObj.spawnEntityInWorld(meteor);
		};
		
		bullet.bntImpact = (bulletnt, x, y, z, sideHit) -> {

			if(bulletnt.worldObj.isRemote)
				return;

			if(y == -1)
				return;

			EntityBulletBaseNT meteor = new EntityBulletBaseNT(bulletnt.worldObj, BulletConfigSyncingUtil.MASKMAN_METEOR);
			meteor.setPosition(bulletnt.posX, bulletnt.posY + 30 + meteor.worldObj.rand.nextInt(10), bulletnt.posZ);
			meteor.motionY = -1D;
			meteor.setThrower(bulletnt.getThrower());
			bulletnt.worldObj.spawnEntityInWorld(meteor);
		};
		
		bullet.spentCasing = CASING556.clone().register("556FlecIF");
		
		return bullet;
	}
	
	public static BulletConfiguration get556KConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_556.stackFromEnum(Ammo556mm.K));
		bullet.dmgMin = 0;
		bullet.dmgMax = 0;
		bullet.maxAge = 0;
		
		return bullet;
	}
}
