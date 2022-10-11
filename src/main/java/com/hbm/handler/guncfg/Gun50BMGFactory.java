package com.hbm.handler.guncfg;

import java.util.ArrayList;

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
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;

public class Gun50BMGFactory {
	
	public static GunConfiguration getCalamityConfig() {
		
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
		config.durability = 5 * 50 * 10; //15 * capacity * default wear
		config.reloadSound = GunConfiguration.RSOUND_MAG;
		config.firingSound = "hbm:weapon.calShoot";
		config.reloadSoundEnd = false;
		
		config.animations.put(AnimType.CYCLE, new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(1, 0, 0, 25))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 75))
						)
				);
		
		config.animations.put(AnimType.RELOAD, new BusAnimation()
				.addBus("MAG", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, -1, 0, 500))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 500))
						)
				);
		
		config.name = "mg42";
		config.manufacturer = EnumGunManufacturer.WGW;
		
		config.config = HbmCollection.fiftyBMG;
		
		return config;
	}
	
	public static GunConfiguration getSaddleConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 3;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.reloadDuration = 30;
		config.firingDuration = 0;
		config.ammoCap = 100;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_BOX;
		config.durability = 3500;
		config.reloadSound = GunConfiguration.RSOUND_MAG;
		config.firingSound = "hbm:weapon.calShoot";
		
		config.name = "maximDouble";
		config.manufacturer = EnumGunManufacturer.UNKNOWN;
		
		config.config = HbmCollection.fiftyBMG;
		
		return config;
	}
	
	public static GunConfiguration getLunaticMarksman()
	{
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 15;
		config.reloadDuration = 15;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.roundsPerCycle = 1;
		config.firingSound = "hbm:weapon.heavyShootPB3";
		config.firingPitch = 0.75F;
		config.ammoCap = 4;
		config.reloadType = GunConfiguration.RELOAD_SINGLE;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CLASSIC;
		config.reloadSound = GunConfiguration.RSOUND_SHOTGUN;
		config.reloadSoundEnd = true;
		config.durability = 500000;
		
		config.name = "lunaSniper";
		config.manufacturer = EnumGunManufacturer.LUNA;
		config.comment.add("\"You do not spark joy\"");
		
		config.config = new ArrayList<Integer>(3);
		config.config.add(BulletConfigSyncingUtil.ROUND_LUNA_SNIPER_SABOT);
		config.config.add(BulletConfigSyncingUtil.ROUND_LUNA_SNIPER_INCENDIARY);
		config.config.add(BulletConfigSyncingUtil.ROUND_LUNA_SNIPER_EXPLOSIVE);
		
		config.animations.put(AnimType.CYCLE, new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(-0.45, 0.15, 0, 40))//Moves back and raise slightly
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 75)))//Then forward again
				.addBus("EJECT", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 30))//Wait
						.addKeyframe(new BusAnimationKeyframe(50, 0, 0, 120))));//Fly out
//		config.animations.put(AnimType.RELOAD, new BusAnimation()
//				.addBus("TILT", new BusAnimationSequence()//Tilt gun and release slide to feed next round
//						.addKeyframe(new BusAnimationKeyframe(45, 20, 0, 500))//Tilt for reload
//						.addKeyframe(new BusAnimationKeyframe(45, 20, -0.2, 1200))//Wait and pull back slide
//						.addKeyframe(new BusAnimationKeyframe(45, 20, 0, 75))//Release slide
//						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 400)))//Return
//				.addBus("INSERT_ROUND", new BusAnimationSequence()//Insert the new round
//						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 600))//Wait to tilt
//						.addKeyframe(new BusAnimationKeyframe(-20, -2, 0.75, 400))//Just plop that thing in there
//						.addKeyframe(new BusAnimationKeyframe(20, -2, 0.75, 75))));//Wait for the slide to close
		
		return config;
	}

	// TODO Finish
	public static BulletConfiguration getLunaticSabotRound()
	{
		final BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_luna_sniper, 1, 0);
		bullet.spread = 0.0F;
		bullet.dmgMax = 500F;
		bullet.dmgMin = 450F;
		bullet.penetration = 10000;
		bullet.penetrationModifier = 1;
		bullet.wear = 2000;
		bullet.velocity = 100;
		bullet.doesPenetrate = true;
		bullet.leadChance = 20;
		bullet.incendiary = 10;
		
		bullet.effects = new ArrayList<PotionEffect>();
//		bullet.effects.add(HbmPotion.getPotionNoCure(HbmPotion.fragile.id, 30 * 20, 2));
//		bullet.effects.add(HbmPotion.getPotionNoCure(HbmPotion.perforated.id, 30 * 20, 2));
//		bullet.effects.add(HbmPotion.getPotionNoCure(HbmPotion.lead.id, 30 * 20, 1));
		
		bullet.blockDamage = true;
		bullet.bImpact = (projectile, x, y, z) -> projectile.worldObj.newExplosion(projectile, x, y, z, 5.0F, true, false);
		
		return bullet;
	}
	
	public static BulletConfiguration getLunaticIncendiaryRound()
	{
		final BulletConfiguration bullet = getLunaticSabotRound().clone();
		
		bullet.ammo.meta = 1;
		bullet.incendiary = 50;
//		bullet.effects.add(HbmPotion.getPotionNoCure(HbmPotion.phosphorus.id, 30 * 30, 2));
		
		return bullet;
	}
	
	public static BulletConfiguration getLunaticExplosiveRound()
	{
		final BulletConfiguration bullet = getLunaticSabotRound().clone();
		
		bullet.ammo.meta = 2;
		bullet.explosive = 25;
		bullet.bImpact = (projectile, x, y, z) -> projectile.worldObj.newExplosion(projectile, x, y, z, 25.0F, true, false);
		
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
		config.durability = 100000;
		config.reloadSound = GunConfiguration.RSOUND_MAG;
		config.firingSound = "hbm:turret.howard_fire";
		
		config.name = "ar15_50";
		config.manufacturer = EnumGunManufacturer.ARMALITE;
		
		config.config = new ArrayList<Integer>();
		config.config.addAll(HbmCollection.fiftyBMG);
		config.config.addAll(HbmCollection.fiftyBMGFlechette);
		
		return config;
	}
	
	public static GunConfiguration getM2Config()
	{
		GunConfiguration config = getAR15Config().clone();
		
		config.rateOfFire = 2;
		config.durability *= 10;
		config.ammoCap = 0;
		config.crosshair = Crosshair.L_BOX;
		config.reloadType = GunConfiguration.RELOAD_NONE;
		config.hasSights = false;
		config.allowsInfinity = true;
		config.firingSound = "hbm:turret.chekhov_fire";
		config.equipSound = "hbm:turret.howard_reload";
		
		config.name = "m2";
		config.manufacturer = EnumGunManufacturer.COLT;
		config.comment.add("\"A single man can do unbelievable things...");
		config.comment.add("A single man with a .50 cal machine gun can do even more.\"");
		
		config.advLore.add("The §lM2 machine gun§r§7 or §lBrowning .50 caliber machine gun§r§7 is a heavy machine gun");
		config.advLore.add("designed toward the end of World War I by John Browning. Its design is similar to Browning's");
		config.advLore.add("earlier M1919 Browning machine gun, which was chambered for the .30-06 cartridge. The M2 uses");
		config.advLore.add("the much larger and much more powerful .50 BMG (12.7 mm) cartridge, which was developed alongside");
		config.advLore.add("and takes its name from the gun itself (BMG standing for §oBrowning machine gun§r§7). It has been");
		config.advLore.add("referred to as \"Ma Deuce\", in reference to its M2 nomenclature. The design has had many specific");
		config.advLore.add("designations; the official US military designation for the current infantry type is §lBrowning");
		config.advLore.add("§lMachine Gun, Cal. .50, M2, HB, Flexible§r§7. It is effective against infantry, unarmored or");
		config.advLore.add("lightly armored vehicles and boats, light fortifications, and low-flying aircraft.");
		
		config.advFuncLore.add("The Browning M2 is an air-cooled, belt-fed machine gun. The M2 fires from a closed bolt, operated");
		config.advFuncLore.add("on the short recoil principle. The M2 fires the .50 BMG cartridge, which offers long range, accuracy,");
		config.advFuncLore.add("and immense stopping power. The closed bolt firing cycle made the M2 usable as a synchronized machine");
		config.advFuncLore.add("gun on aircraft before and during World War II, as on the early versions of the Curtiss P-40 fighter.");
		config.advFuncLore.add("The M2 is a scaled-up version of John Browning's M1917 .30 caliber machine gun. ");
		
		return config;
	}
	
	static final float inaccuracy = 0.0005F;
	static byte i = 0;
	public static BulletConfiguration get50BMGConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_50bmg, 1, i++);
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 50;
		bullet.dmgMax = 56;
		bullet.penetration = 120;
		
		return bullet;
	}

	public static BulletConfiguration get50BMGFireConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_50bmg, 1, i++);
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 50;
		bullet.dmgMax = 56;
		bullet.penetration = 120;
		bullet.wear = 15;
		bullet.incendiary = 5;
		
		return bullet;
	}
	
	public static BulletConfiguration get50BMGPhosphorusConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_50bmg, 1, i++);
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 50;
		bullet.dmgMax = 56;
		bullet.penetration = 75;
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
	
	public static BulletConfiguration get50BMGExplosiveConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_50bmg, 1, i++);
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 90;
		bullet.dmgMax = 94;
		bullet.penetration = 100;
		bullet.wear = 25;
		bullet.explosive = 1;
		
		return bullet;
	}

	public static BulletConfiguration get50BMGAPConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_50bmg, 1, i++);
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 82;
		bullet.dmgMax = 88;
		bullet.penetration = 150;
		bullet.wear = 15;
		bullet.leadChance = 10;
		
		return bullet;
	}

	public static BulletConfiguration get50BMGDUConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_50bmg, 1, i++);
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 90;
		bullet.dmgMax = 96;
		bullet.penetration = 200;
		bullet.wear = 25;
		bullet.leadChance = 50;
		
		return bullet;
	}
	
	public static BulletConfiguration get50BMGStarConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_50bmg, 1, i++);
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 108;
		bullet.dmgMax = 112;
		bullet.penetration = 250;
		bullet.wear = 25;
		bullet.leadChance = 100;
		
		return bullet;
	}

	public static BulletConfiguration get50BMGSleekConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_50bmg, 1, i++);
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 60;
		bullet.dmgMax = 80;
		bullet.penetration = 120;
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
	
	public static BulletConfiguration get50BMGFlechetteConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_50bmg, 1, i++);
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 60;
		bullet.dmgMax = 64;
		bullet.penetration = 130;
		bullet.style = BulletConfiguration.STYLE_FLECHETTE;
		
		return bullet;
	}
	
	public static BulletConfiguration get50BMGFlechetteAMConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_50bmg, 1, i++);
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 70;
		bullet.dmgMax = 74;
		bullet.penetration = 140;
		bullet.style = BulletConfiguration.STYLE_FLECHETTE;
		
		bullet.bHit = (projectile, hit) -> {

				if(projectile.worldObj.isRemote)
					return;
				
				if(hit instanceof EntityLivingBase) {
					ContaminationUtil.contaminate((EntityLivingBase) hit, HazardType.RADIATION, ContaminationType.RAD_BYPASS, 100F);
				}
		};
		
		return bullet;
	}
	
	public static BulletConfiguration get50BMGFlechettePOConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_50bmg, 1, i++);
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 70;
		bullet.dmgMax = 74;
		bullet.penetration = 130;
		bullet.style = BulletConfiguration.STYLE_FLECHETTE;
		
		bullet.bHit = (projectile, hit) -> {

				if(projectile.worldObj.isRemote)
					return;
				
				if(hit instanceof EntityLivingBase) {
					ContaminationUtil.contaminate((EntityLivingBase) hit, HazardType.RADIATION, ContaminationType.RAD_BYPASS, 50F);
				}
		};
		
		return bullet;
	}
}