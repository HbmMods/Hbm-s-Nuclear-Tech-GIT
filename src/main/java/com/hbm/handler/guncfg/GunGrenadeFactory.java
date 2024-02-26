package com.hbm.handler.guncfg;

import com.hbm.entity.projectile.EntityBulletBaseNT;
import com.hbm.explosion.ExplosionNukeSmall;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.CasingEjector;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.items.ItemAmmoEnums.AmmoGrenade;
import com.hbm.lib.HbmCollection;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.main.ResourceManager;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.particle.SpentCasing;
import com.hbm.particle.SpentCasing.CasingType;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class GunGrenadeFactory {

	private static final CasingEjector EJECTOR_LAUNCHER;
	private static final CasingEjector EJECTOR_CONGOLAKE;
	private static final SpentCasing CASING40MM;

	static {
		EJECTOR_LAUNCHER = new CasingEjector().setAngleRange(0.02F, 0.03F).setAfterReload();
		EJECTOR_CONGOLAKE = new CasingEjector().setMotion(0.3, 0.1, 0).setAngleRange(0.02F, 0.03F).setDelay(15);
		CASING40MM = new SpentCasing(CasingType.STRAIGHT).setScale(4F, 4F, 3F).setBounceMotion(0.02F, 0.03F).setColor(SpentCasing.COLOR_CASE_40MM, 0x4B5443).setupSmoke(1F, 0.5D, 60, 40);
	}
	
	public static GunConfiguration getHK69Config() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 30;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.hasSights = true;
		config.zoomFOV = 0.66F;
		config.reloadDuration = 40;
		config.firingDuration = 0;
		config.ammoCap = 1;
		config.reloadType = GunConfiguration.RELOAD_SINGLE;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CIRCUMFLEX;
		config.firingSound = "hbm:weapon.hkShoot";
		config.reloadSound = GunConfiguration.RSOUND_GRENADE;
		config.reloadSoundEnd = false;
		
		config.name = "gPistol";
		config.manufacturer = EnumGunManufacturer.H_AND_K;

		config.config = HbmCollection.grenade;
		config.durability = 300;
		
		config.ejector = EJECTOR_LAUNCHER;
		
		return config;
	}
	
	public static GunConfiguration getCongoConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 24;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.reloadDuration = 16;
		config.firingDuration = 0;
		config.ammoCap = 4;
		config.reloadType = GunConfiguration.RELOAD_SINGLE;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CIRCUMFLEX;
		config.firingSound = "hbm:weapon.glShoot";
		config.reloadSound = GunConfiguration.RSOUND_GRENADE_NEW;
		config.reloadSoundEnd = false;
		
		config.name = "congoLake";
		config.manufacturer = EnumGunManufacturer.NAWS;

		config.config = HbmCollection.grenade;
		config.durability = 2500;
		
		config.ejector = EJECTOR_CONGOLAKE;

		config.reloadAnimationsSequential = true;

		config.loadAnimations = i -> {
			config.animations.put(AnimType.CYCLE, ResourceManager.congolake_anim.get("Fire"));
			config.animations.put(AnimType.CYCLE_EMPTY, ResourceManager.congolake_anim.get("FireEmpty"));
			config.animations.put(AnimType.RELOAD, ResourceManager.congolake_anim.get("ReloadStart"));
			config.animations.put(AnimType.RELOAD_EMPTY, ResourceManager.congolake_anim.get("ReloadEmpty"));
			config.animations.put(AnimType.RELOAD_CYCLE, ResourceManager.congolake_anim.get("Reload"));
			config.animations.put(AnimType.RELOAD_END, ResourceManager.congolake_anim.get("ReloadEnd"));
		};

		return config;
	}

	public static BulletConfiguration getGrenadeConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardGrenadeConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_grenade.stackFromEnum(AmmoGrenade.STOCK));
		bullet.velocity = 2.0F;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.wear = 10;
		bullet.trail = 0;
		
		bullet.spentCasing = CASING40MM.clone().register("40MMStock");
		
		return bullet;
	}
	
	public static BulletConfiguration getGrenadeHEConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardGrenadeConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_grenade.stackFromEnum(AmmoGrenade.HE));
		bullet.velocity = 2.0F;
		bullet.dmgMin = 20;
		bullet.dmgMax = 15;
		bullet.wear = 15;
		bullet.explosive = 5.0F;
		bullet.trail = 1;
		
		bullet.spentCasing = CASING40MM.clone().register("40MMHE");
		
		return bullet;
	}
	
	public static BulletConfiguration getGrenadeIncendirayConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardGrenadeConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_grenade.stackFromEnum(AmmoGrenade.INCENDIARY));
		bullet.velocity = 2.0F;
		bullet.dmgMin = 15;
		bullet.dmgMax = 15;
		bullet.wear = 15;
		bullet.trail = 0;
		bullet.incendiary = 2;
		
		bullet.spentCasing = CASING40MM.clone().register("40MMInc");
		
		return bullet;
	}
	
	public static BulletConfiguration getGrenadePhosphorusConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardGrenadeConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_grenade.stackFromEnum(AmmoGrenade.PHOSPHORUS));
		bullet.velocity = 2.0F;
		bullet.dmgMin = 15;
		bullet.dmgMax = 15;
		bullet.wear = 15;
		bullet.trail = 0;
		bullet.incendiary = 2;
		
		bullet.bntImpact = BulletConfigFactory.getPhosphorousEffect(10, 60 * 20, 100, 0.5D, 1F);
		
		bullet.spentCasing = CASING40MM.clone().register("40MMPhos");
		
		return bullet;
	}
	
	public static BulletConfiguration getGrenadeChlorineConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardGrenadeConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_grenade.stackFromEnum(AmmoGrenade.CHLORINE));
		bullet.velocity = 2.0F;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.wear = 10;
		bullet.trail = 3;
		bullet.explosive = 0;
		bullet.chlorine = 50;
		
		bullet.spentCasing = CASING40MM.clone().register("40MMTox");
		
		return bullet;
	}
	
	public static BulletConfiguration getGrenadeSleekConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardGrenadeConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_grenade.stackFromEnum(AmmoGrenade.SLEEK));
		bullet.velocity = 2.0F;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.wear = 10;
		bullet.trail = 4;
		bullet.explosive = 7.5F;
		bullet.jolt = 6.5D;
		
		bullet.spentCasing = CASING40MM.clone().register("40MMIF").setColor(SpentCasing.COLOR_CASE_40MM, 0x1C1C1C);
		
		return bullet;
	}
	
	public static BulletConfiguration getGrenadeConcussionConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardGrenadeConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_grenade.stackFromEnum(AmmoGrenade.CONCUSSION));
		bullet.velocity = 2.0F;
		bullet.dmgMin = 15;
		bullet.dmgMax = 20;
		bullet.blockDamage = false;
		bullet.explosive = 10.0F;
		bullet.trail = 3;
		
		bullet.spentCasing = CASING40MM.clone().register("40MMCon").setColor(SpentCasing.COLOR_CASE_40MM, 0x3D5E1D);
		
		return bullet;
	}
	
	public static BulletConfiguration getGrenadeFinnedConfig() {
		
		BulletConfiguration bullet = getGrenadeConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_grenade.stackFromEnum(AmmoGrenade.FINNED));
		bullet.gravity = 0.02;
		bullet.explosive = 1.5F;
		bullet.trail = 5;
		
		bullet.spentCasing = CASING40MM.clone().register("40MMFin").setColor(SpentCasing.COLOR_CASE_40MM, 0x007FDB);
		
		return bullet;
	}
	
	public static BulletConfiguration getGrenadeNuclearConfig() {
		
		BulletConfiguration bullet = getGrenadeConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_grenade.stackFromEnum(AmmoGrenade.NUCLEAR));
		bullet.velocity = 4;
		bullet.explosive = 0.0F;
		
		bullet.bntImpact = (bulletnt, x, y, z, sideHit) -> {
			BulletConfigFactory.nuclearExplosion(bulletnt, x, y, z, ExplosionNukeSmall.PARAMS_TOTS);
		};
		
		bullet.spentCasing = CASING40MM.clone().register("40MMNuke").setColor(SpentCasing.COLOR_CASE_40MM, 0xE2C000);
		
		return bullet;
	}

	public static BulletConfiguration getGrenadeTracerConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardGrenadeConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_grenade.stackFromEnum(AmmoGrenade.TRACER));
		bullet.velocity = 2.0F;
		bullet.wear = 10;
		bullet.explosive = 0F;
		bullet.trail = 5;
		bullet.vPFX = "bluedust";
		
		bullet.spentCasing = CASING40MM.clone().register("40MMTrac").setColor(0xEEEEEE, 0x0075CA);
		
		return bullet;
	}

	public static BulletConfiguration getGrenadeKampfConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_grenade.stackFromEnum(AmmoGrenade.KAMPF));
		bullet.spread = 0.0F;
		bullet.gravity = 0.0D;
		bullet.wear = 15;
		bullet.explosive = 3.5F;
		bullet.style = BulletConfiguration.STYLE_GRENADE;
		bullet.trail = 4;
		bullet.vPFX = "smoke";
		
		//bullet.spentCasing = CASING40MM.clone().register("40MMKampf").setColor(0xEBC35E); //does not eject, whole cartridge leaves the gun
		
		return bullet;
	}

	public static BulletConfiguration getGrenadeLeadbursterConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardGrenadeConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_grenade.stackFromEnum(AmmoGrenade.LEADBURSTER));
		bullet.spread = 0.0F;
		bullet.gravity = 0.01D;
		bullet.explosive = 0F;
		bullet.style = BulletConfiguration.STYLE_LEADBURSTER;
		bullet.doesRicochet = false;
		bullet.doesPenetrate = true;
		bullet.vPFX = "";

		bullet.bntImpact = (bulletnt, x, y, z, sideHit) -> {
			
			switch(sideHit) {
			case 0: bulletnt.rotationPitch = (float) (90); break;
			case 1: bulletnt.rotationPitch = (float) (-90); break;
			case 2: bulletnt.rotationPitch = 0; bulletnt.rotationYaw = 0; break;
			case 3: bulletnt.rotationPitch = 0; bulletnt.rotationYaw = (float) 180; break;
			case 4: bulletnt.rotationPitch = 0; bulletnt.rotationYaw = 90; break;
			case 5: bulletnt.rotationPitch = 0; bulletnt.rotationYaw = (float) -90; break;
			}
			
			Vec3 vec = Vec3.createVectorHelper(0, 0, 1);
			vec.rotateAroundX((float) (bulletnt.rotationPitch * Math.PI / 180D));
			vec.rotateAroundY((float) (bulletnt.rotationYaw * Math.PI / 180));

			double offset = 0.1;
			bulletnt.posX -= vec.xCoord * offset;
			bulletnt.posY -= vec.yCoord * offset;
			bulletnt.posZ -= vec.zCoord * offset;
			
			bulletnt.ticksExisted = 0;
			bulletnt.getStuck(x, y, z, sideHit);
		};
		
		bullet.bntUpdate = (bulletnt) -> {
			if(bulletnt.worldObj.isRemote) return;
			
			switch(bulletnt.getStuckIn()) {
			case 0: bulletnt.rotationPitch = (float) (90); break;
			case 1: bulletnt.rotationPitch = (float) (-90); break;
			case 2: bulletnt.rotationPitch = 0; bulletnt.rotationYaw = 0; break;
			case 3: bulletnt.rotationPitch = 0; bulletnt.rotationYaw = (float) 180; break;
			case 4: bulletnt.rotationPitch = 0; bulletnt.rotationYaw = 90; break;
			case 5: bulletnt.rotationPitch = 0; bulletnt.rotationYaw = (float) -90; break;
			}
			
			if(bulletnt.ticksInGround < 20) return;
			int timer = bulletnt.ticksInGround - 20;
			
			Vec3 offset = Vec3.createVectorHelper(0, 0, -0.5);
			offset.rotateAroundX((float) (bulletnt.rotationPitch * Math.PI / 180D));
			offset.rotateAroundY((float) (bulletnt.rotationYaw * Math.PI / 180));
			
			if(bulletnt.ticksExisted >= 100) {
				bulletnt.setDead();
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "vanillaExt");
				data.setString("mode", "largeexplode");
				data.setFloat("size", 1F);
				data.setByte("count", (byte)1);
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, bulletnt.posX + offset.xCoord, bulletnt.posY + offset.yCoord, bulletnt.posZ + offset.zCoord), new TargetPoint(bulletnt.dimension, bulletnt.posX, bulletnt.posY, bulletnt.posZ, 50));
			}
			
			if(timer > 60) return;
			
			bulletnt.worldObj.playSoundEffect(bulletnt.posX, bulletnt.posY, bulletnt.posZ, "hbm:weapon.silencerShoot", 2F, 1F);
			
			for(int i = 0; i < 5; i++) {
				Vec3 vec = Vec3.createVectorHelper(0, 1, 0);
				vec.rotateAroundX((float) Math.toRadians(11.25 * i));
				vec.rotateAroundZ((float) -Math.toRadians(13 * timer));
				vec.rotateAroundX((float) (bulletnt.rotationPitch * Math.PI / 180D));
				vec.rotateAroundY((float) (bulletnt.rotationYaw * Math.PI / 180));
				
				EntityBulletBaseNT pellet = new EntityBulletBaseNT(bulletnt.worldObj, BulletConfigSyncingUtil.R556_NORMAL);
				double dist = 0.5;
				double off = 0.5;
				pellet.setPosition(bulletnt.posX + vec.xCoord * dist + offset.xCoord * off, bulletnt.posY + vec.yCoord * dist + offset.yCoord * off, bulletnt.posZ + vec.zCoord * dist + offset.zCoord * off);
				double vel = 0.5;
				pellet.motionX = vec.xCoord * vel;
				pellet.motionY = vec.yCoord * vel;
				pellet.motionZ = vec.zCoord * vel;
				
				float hyp = MathHelper.sqrt_double(pellet.motionX * pellet.motionX + pellet.motionZ * pellet.motionZ);
				pellet.prevRotationYaw = pellet.rotationYaw = (float) (Math.atan2(pellet.motionX, pellet.motionZ) * 180.0D / Math.PI);
				pellet.prevRotationPitch = pellet.rotationPitch = (float) (Math.atan2(pellet.motionY, (double) hyp) * 180.0D / Math.PI);
				
				bulletnt.worldObj.spawnEntityInWorld(pellet);
			}
		};
		
		return bullet;
	}
}
