package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.entity.projectile.EntityBulletBaseNT;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.CasingEjector;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.items.ItemAmmoEnums.Ammo50BMG;
import com.hbm.items.ItemAmmoEnums.AmmoLunaticSniper;
import com.hbm.lib.HbmCollection;
import com.hbm.lib.RefStrings;
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
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class Gun50BMGFactory {
	
	private static final CasingEjector EJECTOR_BMG;
	private static final CasingEjector EJECTOR_SNIPER;
	private static final SpentCasing CASING50BMG;
	private static final SpentCasing CASINGLUNA;

	static {
		EJECTOR_BMG = new CasingEjector().setMotion(-0.35, 0.9, 0).setOffset(-0.45, -0.2, 0.35).setAngleRange(0.01F, 0.05F);
		EJECTOR_SNIPER = new CasingEjector().setMotion(-2, 0.15, 0).setOffset(-0.45, -0.2, 0.35).setAngleRange(0.02F, 0.05F);
		CASING50BMG = new SpentCasing(CasingType.BOTTLENECK).setScale(3F).setBounceMotion(0.01F, 0.05F).setColor(SpentCasing.COLOR_CASE_BRASS).setupSmoke(0.125F, 0.5D, 60, 20);
		CASINGLUNA = new SpentCasing(CasingType.BOTTLENECK).setScale(4F).setBounceMotion(0.02F, 0.05F).setColor(SpentCasing.COLOR_CASE_BRASS).setupSmoke(0.125F, 0.5D, 60, 30);
	}
	
	public static BulletConfiguration getLunaticSabotRound() {
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();

		bullet.ammo = new ComparableStack(ModItems.ammo_luna_sniper.stackFromEnum(AmmoLunaticSniper.SABOT));
		bullet.spread = 0.0F;
		bullet.dmgMax = 500F;
		bullet.dmgMin = 450F;
		bullet.headshotMult = 2.5F;
		bullet.wear = 2000;
		bullet.velocity = 10F;
		bullet.doesPenetrate = true;
		bullet.leadChance = 20;

		bullet.blockDamage = false;
		bullet.bntImpact = (projectile, x, y, z, sideHit) -> projectile.worldObj.newExplosion(projectile, x, y, z, 2.0F, false, false);
		
		bullet.spentCasing = CASINGLUNA.clone().register("LunaStock");

		return bullet;
	}

	public static BulletConfiguration getLunaticIncendiaryRound() {
		BulletConfiguration bullet = getLunaticSabotRound().clone();

		bullet.ammo = new ComparableStack(ModItems.ammo_luna_sniper.stackFromEnum(AmmoLunaticSniper.INCENDIARY));

		bullet.ammo.meta = 1;
		bullet.incendiary = 10;
		bullet.bntImpact = (projectile, x, y, z, sideHit) -> projectile.worldObj.newExplosion(projectile, x, y, z, 5.0F, true, false);
		
		bullet.spentCasing = CASINGLUNA.clone().register("LunaInc");

		return bullet;
	}

	public static BulletConfiguration getLunaticExplosiveRound() {
		BulletConfiguration bullet = getLunaticSabotRound().clone();

		bullet.ammo = new ComparableStack(ModItems.ammo_luna_sniper.stackFromEnum(AmmoLunaticSniper.EXPLOSIVE));

		bullet.ammo.meta = 2;
		bullet.explosive = 25;
		bullet.destroysBlocks = true;
		bullet.bntImpact = (projectile, x, y, z, sideHit) -> projectile.worldObj.newExplosion(projectile, x, y, z, 25.0F, true, false);
		
		bullet.spentCasing = CASINGLUNA.clone().register("LunaExp");

		return bullet;
	}

	public static GunConfiguration getAR15Config() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 1;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.reloadDuration = 20;
		config.firingDuration = 0;
		config.ammoCap = 50;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.NONE;
		config.durability = 100_000;
		config.reloadSound = GunConfiguration.RSOUND_MAG;
		config.firingSound = "hbm:turret.howard_fire";
		
		config.name = "ar15_50";
		config.manufacturer = EnumGunManufacturer.ARMALITE;
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.BMG50_FLECHETTE_AM);
		config.config.add(BulletConfigSyncingUtil.BMG50_FLECHETTE_PO);
		config.config.add(BulletConfigSyncingUtil.BMG50_FLECHETTE_NORMAL);
		config.config.add(BulletConfigSyncingUtil.BMG50_NORMAL);
		config.config.add(BulletConfigSyncingUtil.BMG50_INCENDIARY);
		config.config.add(BulletConfigSyncingUtil.BMG50_PHOSPHORUS);
		config.config.add(BulletConfigSyncingUtil.BMG50_EXPLOSIVE);
		config.config.add(BulletConfigSyncingUtil.BMG50_AP);
		config.config.add(BulletConfigSyncingUtil.BMG50_DU);
		config.config.add(BulletConfigSyncingUtil.BMG50_STAR);
		config.config.add(BulletConfigSyncingUtil.CHL_BMG50);
		config.config.add(BulletConfigSyncingUtil.BMG50_SLEEK);
		
		config.ejector = EJECTOR_BMG;
		
		return config;
	}
	
	public static GunConfiguration getM2Config() {
		GunConfiguration config = getAR15Config();
		
		config.rateOfFire = 2;
		config.durability *= 10;
		config.ammoCap = 0;
		config.crosshair = Crosshair.L_BOX;
		config.reloadType = GunConfiguration.RELOAD_NONE;
		config.hasSights = true;
		config.zoomFOV = 0.66F;
		config.allowsInfinity = true;
		config.durability = 10_000;
		config.firingSound = "hbm:turret.chekhov_fire";
		config.equipSound = "hbm:turret.howard_reload";
		
		config.name = "m2";
		config.manufacturer = EnumGunManufacturer.COLT;
		config.comment.add("\"A single man can do unbelievable things...");
		config.comment.add("A single man with a .50 cal machine gun can do even more.\"");
		
		config.animations.put(AnimType.CYCLE, new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(1, 0, 0, 25))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 75))
						)
				);
		
		config.ejector = EJECTOR_BMG;
		
		config.config.clear();
		config.config.addAll(HbmCollection.bmg50);
		
		return config;
	}
	
	public static final ResourceLocation scope_luna = new ResourceLocation(RefStrings.MODID, "textures/misc/scope_luna.png");
	
	public static GunConfiguration getLunaticMarksman() {
		GunConfiguration config = new GunConfiguration();

		config.rateOfFire = 15;
		config.reloadDuration = 15;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.roundsPerCycle = 1;
		config.firingSound = "hbm:weapon.hicalShot";
		config.firingPitch = 0.75F;
		config.ammoCap = 4;
		config.reloadType = GunConfiguration.RELOAD_SINGLE;
		config.hasSights = true;
		config.zoomFOV = 0.2F; //x5 magnification
		config.scopeTexture = scope_luna;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CLASSIC;
		config.reloadSound = GunConfiguration.RSOUND_SHOTGUN;
		config.reloadSoundEnd = true;
		config.durability = 500_000;

		config.name = "lunaSniper";
		config.manufacturer = EnumGunManufacturer.LUNA;
		config.comment.add("\"You do not spark joy\"");

		config.config = new ArrayList();
		config.config.add(BulletConfigSyncingUtil.ROUND_LUNA_SNIPER_SABOT);
		config.config.add(BulletConfigSyncingUtil.ROUND_LUNA_SNIPER_INCENDIARY);
		config.config.add(BulletConfigSyncingUtil.ROUND_LUNA_SNIPER_EXPLOSIVE);

		config.animations.put(AnimType.CYCLE,
				new BusAnimation()
						.addBus("RECOIL", new BusAnimationSequence()
								.addKeyframe(new BusAnimationKeyframe(-0.45, 0.15, 0, 40)) // Moves back  and raise slightly
								.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 75))) // Then forward  again
						.addBus("EJECT", new BusAnimationSequence().addKeyframe(new BusAnimationKeyframe(0, 0, 0, 30)) // Wait
								.addKeyframe(new BusAnimationKeyframe(50, 0, 0, 120)))); // Fly // out

		config.ejector = EJECTOR_SNIPER;
		return config;
	}

	static float inaccuracy = 2.5F;
	public static BulletConfiguration get50BMGConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_50bmg.stackFromEnum(Ammo50BMG.STOCK));
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 30;
		bullet.dmgMax = 36;
		
		bullet.spentCasing = CASING50BMG.clone().register("50BMGStock");
		
		return bullet;
	}

	public static BulletConfiguration get50BMGFireConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_50bmg.stackFromEnum(Ammo50BMG.INCENDIARY));
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 30;
		bullet.dmgMax = 36;
		bullet.wear = 15;
		bullet.incendiary = 5;
		
		bullet.spentCasing = CASING50BMG.clone().register("50BMGInc");
		
		return bullet;
	}

	public static BulletConfiguration get50BMGPhosphorusConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_50bmg.stackFromEnum(Ammo50BMG.PHOSPHORUS));
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 30;
		bullet.dmgMax = 36;
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
		
		bullet.spentCasing = CASING50BMG.clone().register("50BMGPhos");
		
		return bullet;
	}

	public static BulletConfiguration get50BMGExplosiveConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_50bmg.stackFromEnum(Ammo50BMG.EXPLOSIVE));
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 60;
		bullet.dmgMax = 64;
		bullet.wear = 25;
		bullet.explosive = 1;
		
		bullet.spentCasing = CASING50BMG.clone().register("50BMGExp");
		
		return bullet;
	}

	public static BulletConfiguration get50BMGAPConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_50bmg.stackFromEnum(Ammo50BMG.AP));
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 62;
		bullet.dmgMax = 68;
		bullet.wear = 15;
		bullet.leadChance = 10;
		
		bullet.spentCasing = CASING50BMG.clone().register("50BMGAP");
		
		return bullet;
	}

	public static BulletConfiguration get50BMGDUConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_50bmg.stackFromEnum(Ammo50BMG.DU));
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 80;
		bullet.dmgMax = 86;
		bullet.wear = 25;
		bullet.leadChance = 50;
		
		bullet.spentCasing = CASING50BMG.clone().register("50BMGDU");
		
		return bullet;
	}

	public static BulletConfiguration get50BMGStarConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_50bmg.stackFromEnum(Ammo50BMG.STAR));
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 98;
		bullet.dmgMax = 102;
		bullet.wear = 25;
		bullet.leadChance = 100;
		
		bullet.spentCasing = CASING50BMG.clone().register("50BMGStar");
		
		return bullet;
	}

	public static BulletConfiguration get50BMGSleekConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_50bmg.stackFromEnum(Ammo50BMG.SLEEK));
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 50;
		bullet.dmgMax = 70;
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
		
		bullet.spentCasing = CASING50BMG.clone().register("50BMGIF");
		
		return bullet;
	}
	
	public static BulletConfiguration get50BMGFlechetteConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_50bmg.stackFromEnum(Ammo50BMG.FLECHETTE));
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 50;
		bullet.dmgMax = 54;
		bullet.style = bullet.STYLE_FLECHETTE;
		BulletConfigFactory.makeFlechette(bullet);
		
		bullet.spentCasing = CASING50BMG.clone().register("50BMGFlech");
		
		return bullet;
	}
	
	public static BulletConfiguration get50BMGFlechetteAMConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();

		bullet.ammo = new ComparableStack(ModItems.ammo_50bmg.stackFromEnum(Ammo50BMG.FLECHETTE_AM));
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 60;
		bullet.dmgMax = 64;
		bullet.style = bullet.STYLE_FLECHETTE;
		BulletConfigFactory.makeFlechette(bullet);
		
		bullet.bntHit = (bulletnt, hit) -> {

			if(bulletnt.worldObj.isRemote)
				return;

			if(hit instanceof EntityLivingBase) {
				ContaminationUtil.contaminate((EntityLivingBase) hit, HazardType.RADIATION, ContaminationType.RAD_BYPASS, 100F);
			}
		};
		
		bullet.spentCasing = CASING50BMG.clone().register("50BMGAM");
		
		return bullet;
	}
	
	public static BulletConfiguration get50BMGFlechettePOConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();

		bullet.ammo = new ComparableStack(ModItems.ammo_50bmg.stackFromEnum(Ammo50BMG.FLECHETTE_PO));
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 60;
		bullet.dmgMax = 64;
		bullet.style = bullet.STYLE_FLECHETTE;
		BulletConfigFactory.makeFlechette(bullet);
		
		bullet.bntHit = (bulletnt, hit) -> {

			if(bulletnt.worldObj.isRemote)
				return;

			if(hit instanceof EntityLivingBase) {
				ContaminationUtil.contaminate((EntityLivingBase) hit, HazardType.RADIATION, ContaminationType.RAD_BYPASS, 50F);
			}
		};
		
		bullet.spentCasing = CASING50BMG.clone().register("50BMGPO");
		
		return bullet;
	}
}
