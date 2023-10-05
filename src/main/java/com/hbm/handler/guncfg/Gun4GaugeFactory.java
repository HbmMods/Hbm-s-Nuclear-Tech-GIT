package com.hbm.handler.guncfg;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.projectile.EntityBulletBaseNT;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNT;
import com.hbm.explosion.ExplosionNT.ExAttrib;
import com.hbm.explosion.ExplosionNukeSmall;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.CasingEjector;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.items.ItemAmmoEnums.Ammo4Gauge;
import com.hbm.lib.HbmCollection;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.lib.ModDamageSource;
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
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.IExtendedEntityProperties;

public class Gun4GaugeFactory {
	
	private static final CasingEjector EJECTOR_SHOTGUN;
	private static final SpentCasing CASING4GAUGE;

	static {
		EJECTOR_SHOTGUN = new CasingEjector().setMotion(Vec3.createVectorHelper(-0.4, 0.4, 0)).setOffset(Vec3.createVectorHelper(-0.5, 0, 0.5)).setAngleRange(0.01F, 0.03F);
		CASING4GAUGE = new SpentCasing(CasingType.SHOTGUN).setScale(2.5F).setBounceMotion(0.01F, 0.03F);
	}
	
	private static GunConfiguration getShotgunConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 15;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.reloadDuration = 10;
		config.firingDuration = 0;
		config.ammoCap = 4;
		config.reloadType = GunConfiguration.RELOAD_SINGLE;
		config.allowsInfinity = true;
		config.hasSights = true;
		config.absoluteFOV = true;
		config.zoomFOV = 0.5F;
		config.crosshair = Crosshair.L_CIRCLE;
		config.reloadSound = GunConfiguration.RSOUND_SHOTGUN;
		
		config.ejector = EJECTOR_SHOTGUN;
		
		return config;
	}
	
	public static GunConfiguration getKS23Config() {
		
		GunConfiguration config = getShotgunConfig();
		
		config.durability = 3000;
		config.reloadSound = GunConfiguration.RSOUND_SHOTGUN;
		config.firingSound = "hbm:weapon.revolverShootAlt";
		config.firingPitch = 0.65F;
		
		config.name = "ks23";
		config.manufacturer = EnumGunManufacturer.TULSKY;

		config.config = HbmCollection.g4;
		
		return config;
	}
	
	public static GunConfiguration getSauerConfig() {
		
		GunConfiguration config = getShotgunConfig();

		config.rateOfFire = 20;
		config.ammoCap = 0;
		config.reloadType = GunConfiguration.RELOAD_NONE;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.durability = 3000;
		config.reloadSound = GunConfiguration.RSOUND_SHOTGUN;
		config.firingSound = "hbm:weapon.sauergun";
		config.firingPitch = 1.0F;
		
		config.ejector = EJECTOR_SHOTGUN.clone().setDelay(12);
		
		config.name = "sauer";
		config.manufacturer = EnumGunManufacturer.CUBE;
		
		config.animations.put(AnimType.CYCLE, new BusAnimation()
				.addBus("SAUER_RECOIL", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0.5, 0, 0, 50))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 50))
						)
				.addBus("SAUER_TILT", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0.0, 0, 0, 200))	// do nothing for 200ms
						.addKeyframe(new BusAnimationKeyframe(0, 0, 30, 150))	//tilt forward
						.addKeyframe(new BusAnimationKeyframe(45, 0, 30, 150))	//tilt sideways
						.addKeyframe(new BusAnimationKeyframe(45, 0, 30, 200))	//do nothing for 200ms (eject)
						.addKeyframe(new BusAnimationKeyframe(0, 0, 30, 150))	//restore sideways
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 150))	//restore forward
						)
				.addBus("SAUER_COCK", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 500))	//do nothing for 500ms
						.addKeyframe(new BusAnimationKeyframe(1, 0, 0, 100))	//pull back lever for 100ms
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 100))	//release lever for 100ms
						)
				.addBus("SAUER_SHELL_EJECT", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 500))	//do nothing for 500ms
						.addKeyframe(new BusAnimationKeyframe(0, 0, 1, 500))	//FLING!
						)
				);
		
		config.config = HbmCollection.g4;
		
		return config;
	}
	
	public static BulletConfiguration get4GaugeConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_4gauge.stackFromEnum(Ammo4Gauge.STOCK));
		bullet.dmgMin = 5;
		bullet.dmgMax = 8;
		bullet.bulletsMin *= 2;
		bullet.bulletsMax *= 2;
		
		bullet.spentCasing = CASING4GAUGE.clone().register("4GaStock").setColor(0xFFD800, SpentCasing.COLOR_CASE_4GA);
		
		return bullet;
	}
	
	public static BulletConfiguration get4GaugeSlugConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_4gauge.stackFromEnum(Ammo4Gauge.SLUG));
		bullet.dmgMin = 25;
		bullet.dmgMax = 32;
		bullet.wear = 7;
		bullet.style = BulletConfiguration.STYLE_NORMAL;
		
		bullet.spentCasing = CASING4GAUGE.clone().register("4GaSlug").setColor(0xE01A1A, SpentCasing.COLOR_CASE_4GA);
		
		return bullet;
	}

	public static BulletConfiguration get4GaugeFlechetteConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_4gauge.stackFromEnum(Ammo4Gauge.FLECHETTE));
		bullet.dmgMin = 8;
		bullet.dmgMax = 15;
		bullet.bulletsMin *= 2;
		bullet.bulletsMax *= 2;
		bullet.wear = 15;
		bullet.style = BulletConfiguration.STYLE_FLECHETTE;
		bullet.HBRC = 2;
		bullet.LBRC = 95;
		BulletConfigFactory.makeFlechette(bullet);
		
		bullet.spentCasing = CASING4GAUGE.clone().register("4GaFlech").setColor(0x1537FF, SpentCasing.COLOR_CASE_4GA);
		
		return bullet;
	}

	public static BulletConfiguration get4GaugeFlechettePhosphorusConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_4gauge.stackFromEnum(Ammo4Gauge.FLECHETTE_PHOSPHORUS));
		bullet.dmgMin = 8;
		bullet.dmgMax = 15;
		bullet.bulletsMin *= 2;
		bullet.bulletsMax *= 2;
		bullet.wear = 15;
		bullet.style = BulletConfiguration.STYLE_FLECHETTE;
		bullet.HBRC = 2;
		bullet.LBRC = 95;
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
		
		bullet.spentCasing = CASING4GAUGE.clone().register("4GaPhos").setColor(0xF6871A, SpentCasing.COLOR_CASE_4GA);
		
		return bullet;
	}

	public static BulletConfiguration get4GaugeExplosiveConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardGrenadeConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_4gauge.stackFromEnum(Ammo4Gauge.EXPLOSIVE));
		bullet.velocity *= 2;
		bullet.gravity *= 2;
		bullet.dmgMin = 20;
		bullet.dmgMax = 25;
		bullet.wear = 25;
		bullet.trail = 1;
		
		bullet.spentCasing = CASING4GAUGE.clone().register("4GaExp").setColor(0x3F8243, SpentCasing.COLOR_CASE_4GA);
		
		return bullet;
	}

	public static BulletConfiguration get4GaugeMiningConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardGrenadeConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_4gauge.stackFromEnum(Ammo4Gauge.MINING));
		bullet.velocity *= 2;
		bullet.gravity *= 2;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.wear = 25;
		bullet.trail = 1;
		bullet.explosive = 0.0F;
		
		bullet.bntImpact = (bulletnt, x, y, z, sideHit) -> {
			
			if(bulletnt.worldObj.isRemote)
				return;
			
			ExplosionNT explosion = new ExplosionNT(bulletnt.worldObj, null, bulletnt.posX, bulletnt.posY, bulletnt.posZ, 4);
			explosion.atttributes.add(ExAttrib.ALLDROP);
			explosion.atttributes.add(ExAttrib.NOHURT);
			explosion.doExplosionA();
			explosion.doExplosionB(false);
			
			ExplosionLarge.spawnParticles(bulletnt.worldObj, bulletnt.posX, bulletnt.posY, bulletnt.posZ, 15);
		};
		
		bullet.spentCasing = CASING4GAUGE.clone().register("4GaSem").setColor(0x5C5C5C, SpentCasing.COLOR_CASE_4GA);
		
		return bullet;
	}

		public static BulletConfiguration get4GaugeLTBLConfig() {

			BulletConfiguration bullet = BulletConfigFactory.standardGrenadeConfig();

			bullet.ammo = new ComparableStack(ModItems.ammo_4gauge.stackFromEnum(Ammo4Gauge.LTBL));
			bullet.velocity *= 3;
			bullet.gravity *= 3;
			bullet.dmgMin = 10;
			bullet.dmgMax = 15;
			bullet.setToFire(5);
			bullet.wear = 25;
			bullet.trail = 1;
			bullet.explosive = 0.0F;

			bullet.bntImpact = BulletConfigFactory.getFlashbangEffect(4,4*20,false);
		bullet.spentCasing = CASING4GAUGE.clone().register("4GaSem").setColor(0x5C5C5C, SpentCasing.COLOR_CASE_4GA);

		return bullet;
	}
		public static BulletConfiguration get4GaugeButterConfig() {

			BulletConfiguration bullet = BulletConfigFactory.standardGrenadeConfig();

			bullet.ammo = new ComparableStack(ModItems.ammo_4gauge.stackFromEnum(Ammo4Gauge.BUTTER));
			bullet.velocity *= 5;
			bullet.gravity *= 2;
			bullet.dmgMin = 1;
			bullet.dmgMax = 2;
			bullet.wear = 75;
			bullet.trail = 0;
			bullet.explosive = 0.0F;

			bullet.bntImpact = BulletConfigFactory.getButterBulletImpactBehaviorNT(4,4*80,false);
		bullet.spentCasing = CASING4GAUGE.clone().register("4GaSem").setColor(0x5C5C5C, SpentCasing.COLOR_CASE_4GA);

		return bullet;
	}
	public static BulletConfiguration get4GaugeLTBLSConfig() {

		BulletConfiguration bullet = BulletConfigFactory.standardGrenadeConfig();

		bullet.ammo = new ComparableStack(ModItems.ammo_4gauge.stackFromEnum(Ammo4Gauge.LTBL_SUPER));
		bullet.velocity *= 3;
		bullet.gravity *= 3;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.setToFire(60);
		bullet.wear = 25;
		bullet.trail = 1;
		bullet.explosive = 0.0F;

		bullet.bntImpact = BulletConfigFactory.getFlashbangEffect(8,6*20,true);
		bullet.spentCasing = CASING4GAUGE.clone().register("4GaSem").setColor(0x5C5C5C, SpentCasing.COLOR_CASE_4GA);

		return bullet;
	}
	public static BulletConfiguration get4GaugeBalefireConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardGrenadeConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_4gauge.stackFromEnum(Ammo4Gauge.BALEFIRE));
		bullet.velocity *= 2;
		bullet.gravity *= 2;
		bullet.dmgMin = 50;
		bullet.dmgMax = 65;
		bullet.wear = 25;
		bullet.trail = 1;
		bullet.explosive = 0.0F;
		
		bullet.bntImpact = (bulletnt, x, y, z, sideHit) -> {
			
			if(bulletnt.worldObj.isRemote)
				return;
			
			ExplosionNT explosion = new ExplosionNT(bulletnt.worldObj, null, bulletnt.posX, bulletnt.posY, bulletnt.posZ, 6);
			explosion.atttributes.add(ExAttrib.BALEFIRE);
			explosion.doExplosionA();
			explosion.doExplosionB(false);
			
			ExplosionLarge.spawnParticles(bulletnt.worldObj, bulletnt.posX, bulletnt.posY, bulletnt.posZ, 30);
		};
		
		bullet.spentCasing = CASING4GAUGE.clone().register("4GaBale").setColor(0x7BFF44, SpentCasing.COLOR_CASE_4GA);
		
		return bullet;
	}

	public static BulletConfiguration getGrenadeKampfConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_4gauge.stackFromEnum(Ammo4Gauge.KAMPF));
		bullet.spread = 0.0F;
		bullet.gravity = 0.0D;
		bullet.wear = 15;
		bullet.explosive = 3.5F;
		bullet.style = BulletConfiguration.STYLE_GRENADE;
		bullet.trail = 4;
		bullet.vPFX = "smoke";
		
		bullet.spentCasing = CASING4GAUGE.clone().register("4GaKampf").setColor(0xE7BA48, SpentCasing.COLOR_CASE_4GA);
		
		return bullet;
	}

	public static BulletConfiguration getGrenadeCanisterConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_4gauge.stackFromEnum(Ammo4Gauge.CANISTER));
		bullet.spread = 0.0F;
		bullet.gravity = 0.0D;
		bullet.wear = 15;
		bullet.explosive = 1F;
		bullet.style = BulletConfiguration.STYLE_GRENADE;
		bullet.trail = 4;
		bullet.vPFX = "smoke";
		
		bullet.bntUpdate = (bulletnt) -> {
			
			if(!bulletnt.worldObj.isRemote) {
				
				if(bulletnt.ticksExisted > 10) {
					bulletnt.setDead();
					
					for(int i = 0; i < 50; i++) {
						
						EntityBulletBaseNT bolt = new EntityBulletBaseNT(bulletnt.worldObj, BulletConfigSyncingUtil.M44_AP);
						bolt.setPosition(bulletnt.posX, bulletnt.posY, bulletnt.posZ);
						bolt.setThrowableHeading(bulletnt.motionX, bulletnt.motionY, bulletnt.motionZ, 0.25F, 0.1F);
						bolt.setThrower(bulletnt.getThrower());
						bulletnt.worldObj.spawnEntityInWorld(bolt);
					}
				}
			}
		};
		
		bullet.spentCasing = CASING4GAUGE.clone().register("4GaCan").setColor(0xCACACA, SpentCasing.COLOR_CASE_4GA);
		
		return bullet;
	}

	public static BulletConfiguration get4GaugeSleekConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardAirstrikeConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_4gauge.stackFromEnum(Ammo4Gauge.SLEEK));
		
		bullet.spentCasing = CASING4GAUGE.clone().register("4GaIF").setColor(0x1D1D1D, SpentCasing.COLOR_CASE_4GA);
		
		return bullet;
	}
	
	public static BulletConfiguration get4GaugeClawConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_4gauge.stackFromEnum(Ammo4Gauge.CLAW));
		bullet.dmgMin = 6;
		bullet.dmgMax = 9;
		bullet.bulletsMin *= 2;
		bullet.bulletsMax *= 2;
		bullet.leadChance = 100;
		
		bullet.bntHurt = (bulletnt, hit) -> {
			
			if(bulletnt.worldObj.isRemote)
				return;
			
			if(hit instanceof EntityLivingBase) {
				EntityLivingBase living = (EntityLivingBase) hit;
				float f = living.getHealth();
				
				if(f > 0) {
					f = Math.max(0, f - 2);
					living.setHealth(f);
					
					if(f == 0)
						living.onDeath(ModDamageSource.causeBulletDamage(bulletnt, hit));
				}
			}
		};
		
		bullet.spentCasing = CASING4GAUGE.clone().register("4GaClaw").setColor(0x5E38CC, SpentCasing.COLOR_CASE_4GA);
		
		return bullet;
	}
	
	public static BulletConfiguration get4GaugeVampireConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_4gauge.stackFromEnum(Ammo4Gauge.VAMPIRE));
		bullet.dmgMin = 6;
		bullet.dmgMax = 9;
		bullet.bulletsMin *= 2;
		bullet.bulletsMax *= 2;
		bullet.leadChance = 100;
		bullet.style = BulletConfiguration.STYLE_FLECHETTE;
		
		bullet.bntHurt = (bulletnt, hit) -> {

			if(bulletnt.worldObj.isRemote)
				return;

			if(hit instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) hit;

				IExtendedEntityProperties prop = player.getExtendedProperties("WitcheryExtendedPlayer");

				NBTTagCompound blank = new NBTTagCompound();
				blank.setTag("WitcheryExtendedPlayer", new NBTTagCompound());

				if(prop != null) {
					prop.loadNBTData(blank);
				}
			}
		};
		
		bullet.spentCasing = CASING4GAUGE.clone().register("4GaVamp").setColor(0x278400, SpentCasing.COLOR_CASE_4GA);
		
		return bullet;
	}
	
	public static BulletConfiguration get4GaugeVoidConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_4gauge.stackFromEnum(Ammo4Gauge.VOID));
		bullet.dmgMin = 6;
		bullet.dmgMax = 9;
		bullet.bulletsMin *= 2;
		bullet.bulletsMax *= 2;
		bullet.leadChance = 0;
		
		bullet.bntHurt = (bulletnt, hit) -> {
				
			if(bulletnt.worldObj.isRemote)
				return;

			if(hit instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) hit;

				player.inventory.dropAllItems();
				player.worldObj.newExplosion(bulletnt.getThrower(), player.posX, player.posY, player.posZ, 5.0F, true, true);
			}
		};
		
		bullet.spentCasing = CASING4GAUGE.clone().register("4GaVoid").setColor(0x3F3F3F, SpentCasing.COLOR_CASE_4GA);
		
		return bullet;
	}

	public static BulletConfiguration get4GaugeQuackConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_4gauge.stackFromEnum(Ammo4Gauge.QUACK));
		bullet.velocity *= 2D;
		bullet.spread = 0.0F;
		bullet.gravity = 0.0D;
		bullet.wear = 10;
		bullet.explosive = 1F;
		bullet.style = BulletConfiguration.STYLE_BOLT;
		bullet.trail = 4;
		bullet.vPFX = "explode";
		
		bullet.bntUpdate = (bulletnt) -> {

			if(!bulletnt.worldObj.isRemote) {
				if(bulletnt.ticksExisted % 2 == 0) {

					List<EntityCreature> creatures = bulletnt.worldObj.getEntitiesWithinAABB(EntityCreature.class, bulletnt.boundingBox.expand(10, 10, 10));
					for(EntityCreature creature : creatures) {

						if(creature.getClass().getCanonicalName().startsWith("net.minecraft.entity.titan")) {
							BulletConfigFactory.nuclearExplosion(creature, 0, 0, 0, ExplosionNukeSmall.PARAMS_TOTS);

							bulletnt.worldObj.removeEntity(creature);
							bulletnt.worldObj.unloadEntities(new ArrayList() {{ add(creature); }});
						}
					}
				}
			}
		};
		
		bullet.spentCasing = CASING4GAUGE.clone().register("4GaDucc").setColor(0x1E1E1E, SpentCasing.COLOR_CASE_4GA);
		
		return bullet;
	}
}
