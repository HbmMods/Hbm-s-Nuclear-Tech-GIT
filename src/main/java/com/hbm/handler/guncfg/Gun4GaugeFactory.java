package com.hbm.handler.guncfg;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNT;
import com.hbm.explosion.ExplosionNT.ExAttrib;
import com.hbm.explosion.ExplosionNukeSmall;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.lib.HbmCollection;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.lib.ModDamageSource;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
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
import net.minecraftforge.common.IExtendedEntityProperties;

public class Gun4GaugeFactory {
	
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
		config.crosshair = Crosshair.L_CIRCLE;
		config.reloadSound = GunConfiguration.RSOUND_SHOTGUN;
		
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
		
		config.config = HbmCollection.fourGauge;
		
		config.advLore.add("The KS-23 is a Soviet shotgun, although because it uses a rifled barrel it is officially designated");
		config.advLore.add("by the Russian military as a carbine. KS stands for §oKarabin Spetsialniy§r§7, \"Special Carbine\". It is");
		config.advLore.add("renowned for its large caliber, firing a 23 mm round, equating to 6.27 gauge using the British and");
		config.advLore.add("American standards of shotgun gauges and approximately 4 gauge using the current European standards");
		config.advLore.add("(based on the metric CIP tables), making it the largest-bore shotgun in use today.");
		
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
		
		config.config = HbmCollection.fourGauge;
		
		return config;
	}
	static byte i = 0;
	public static BulletConfiguration get4GaugeConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_4gauge, 1, i++);
		bullet.dmgMin = 5;
		bullet.dmgMax = 8;
		bullet.penetration = 18;
		bullet.bulletsMin *= 2;
		bullet.bulletsMax *= 2;
		
		return bullet;
	}
	
	public static BulletConfiguration get4GaugeSlugConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_4gauge, 1, i++);
		bullet.dmgMin = 25;
		bullet.dmgMax = 32;
		bullet.penetration = 25;
		bullet.wear = 7;
		bullet.style = BulletConfiguration.STYLE_NORMAL;
		
		return bullet;
	}

	public static BulletConfiguration get4GaugeFlechetteConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_4gauge, 1, i++);
		bullet.dmgMin = 8;
		bullet.dmgMax = 15;
		bullet.penetration = 22;
		bullet.bulletsMin *= 2;
		bullet.bulletsMax *= 2;
		bullet.wear = 15;
		bullet.style = BulletConfiguration.STYLE_FLECHETTE;
		bullet.HBRC = 2;
		bullet.LBRC = 95;
		
		return bullet;
	}

	public static BulletConfiguration get4GaugeFlechettePhosphorusConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_4gauge, 1, i++);
		bullet.dmgMin = 8;
		bullet.dmgMax = 15;
		bullet.penetration = 18;
		bullet.bulletsMin *= 2;
		bullet.bulletsMax *= 2;
		bullet.wear = 15;
		bullet.style = BulletConfiguration.STYLE_FLECHETTE;
		bullet.HBRC = 2;
		bullet.LBRC = 95;
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

	public static BulletConfiguration get4GaugeExplosiveConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardGrenadeConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_4gauge, 1, i++);
		bullet.velocity *= 2;
		bullet.gravity *= 2;
		bullet.dmgMin = 20;
		bullet.dmgMax = 25;
		bullet.wear = 25;
		bullet.trail = 1;
		
		return bullet;
	}

	public static BulletConfiguration get4GaugeMiningConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardGrenadeConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_4gauge, 1, i++);
		bullet.velocity *= 2;
		bullet.gravity *= 2;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.wear = 25;
		bullet.trail = 1;
		bullet.explosive = 0.0F;
		
		bullet.bImpact = (projectile, x, y, z) -> {

				if(projectile.worldObj.isRemote)
					return;
				
				ExplosionNT explosion = new ExplosionNT(projectile.worldObj, null, projectile.posX, projectile.posY, projectile.posZ, 4);
				explosion.addAllAttrib(ExAttrib.ALLDROP, ExAttrib.NOHURT);
				explosion.doExplosionA();
				explosion.doExplosionB(false);
				
				ExplosionLarge.spawnParticles(projectile.worldObj, projectile.posX, projectile.posY, projectile.posZ, 15);
		};
		
		return bullet;
	}

	public static BulletConfiguration get4GaugeBalefireConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardGrenadeConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_4gauge, 1, i++);
		bullet.velocity *= 2;
		bullet.gravity *= 2;
		bullet.dmgMin = 50;
		bullet.dmgMax = 65;
		bullet.wear = 25;
		bullet.trail = 1;
		bullet.explosive = 0.0F;
		
		bullet.bImpact = (projectile, x, y, z) -> {

				if(projectile.worldObj.isRemote)
					return;
				
				ExplosionNT explosion = new ExplosionNT(projectile.worldObj, null, projectile.posX, projectile.posY, projectile.posZ, 6);
				explosion.addAttrib(ExAttrib.BALEFIRE);
				explosion.doExplosionA();
				explosion.doExplosionB(false);
				
				ExplosionLarge.spawnParticles(projectile.worldObj, projectile.posX, projectile.posY, projectile.posZ, 30);
		};
		
		return bullet;
	}

	public static BulletConfiguration getGrenadeKampfConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_4gauge, 1, i++);
		bullet.spread = 0.0F;
		bullet.gravity = 0.0D;
		bullet.wear = 15;
		bullet.explosive = 3.5F;
		bullet.style = BulletConfiguration.STYLE_GRENADE;
		bullet.trail = 4;
		bullet.vPFX = "smoke";
		
		return bullet;
	}

	public static BulletConfiguration getGrenadeCanisterConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_4gauge, 1, i++);
		bullet.spread = 0.0F;
		bullet.gravity = 0.0D;
		bullet.wear = 15;
		bullet.explosive = 1F;
		bullet.style = BulletConfiguration.STYLE_GRENADE;
		bullet.trail = 4;
		bullet.vPFX = "smoke";
		
		bullet.bUpdate = (projectile) -> {

				if(!projectile.worldObj.isRemote) {
					
					if(projectile.ticksExisted > 10) {
						projectile.setDead();
						
						for(int i = 0; i < 50; i++) {
							
							EntityBulletBase bolt = new EntityBulletBase(projectile.worldObj, BulletConfigSyncingUtil.M44_AP);
							bolt.setPosition(projectile.posX, projectile.posY, projectile.posZ);
							bolt.setThrowableHeading(projectile.motionX, projectile.motionY, projectile.motionZ, 0.25F, 0.1F);
							projectile.worldObj.spawnEntityInWorld(bolt);
						}
				}
			}
		};
		
		return bullet;
	}

	public static BulletConfiguration get4GaugeSleekConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardAirstrikeConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_4gauge, 1, i++);
		
		return bullet;
	}
	
	public static BulletConfiguration get4GaugeClawConfig() {
		
		BulletConfiguration bullet = get4GaugeConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_4gauge, 1, i++);
		bullet.dmgMin = 6;
		bullet.dmgMax = 9;
		bullet.penetration = 20;
		bullet.bulletsMin *= 2;
		bullet.bulletsMax *= 2;
		bullet.leadChance = 100;
		
		bullet.bHurt = (projectile, hit) -> {

				if(projectile.worldObj.isRemote)
					return;
				
				if(hit instanceof EntityLivingBase) {
					EntityLivingBase living = (EntityLivingBase) hit;
					float f = living.getHealth();
					
					if(f > 0) {
						f = Math.max(0, f - 2);
						living.setHealth(f);
						
						if(f == 0)
							living.onDeath(ModDamageSource.causeBulletDamage(projectile, hit));
					}
				}
		};
		
		return bullet;
	}
	
	public static BulletConfiguration get4GaugeVampireConfig() {
		
		BulletConfiguration bullet = get4GaugeConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_4gauge, 1, i++);
		bullet.dmgMin = 5;
		bullet.dmgMax = 8;
		bullet.bulletsMin *= 2;
		bullet.bulletsMax *= 2;
		bullet.leadChance = 100;
		bullet.style = BulletConfiguration.STYLE_FLECHETTE;
		
		bullet.bHurt = (projectile, hit) -> {

				if(projectile.worldObj.isRemote)
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
		
		return bullet;
	}
	
	public static BulletConfiguration get4GaugeVoidConfig() {
		
		BulletConfiguration bullet = get4GaugeConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_4gauge, 1, i++);
		bullet.dmgMin = 6;
		bullet.dmgMax = 9;
		bullet.bulletsMin *= 2;
		bullet.bulletsMax *= 2;
		bullet.leadChance = 0;
		
		bullet.bHurt = (projectile, hit) -> {

				if(projectile.worldObj.isRemote)
					return;
				
				if(hit instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) hit;
					
					player.inventory.dropAllItems();
					player.worldObj.newExplosion(projectile.shooter, player.posX, player.posY, player.posZ, 5.0F, true, true);
				}
		};
		
		return bullet;
	}

	public static BulletConfiguration get4GaugeQuackConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_4gauge, 1, i++);
		bullet.penetration = Integer.MAX_VALUE;
		bullet.velocity *= 2D;
		bullet.spread = 0.0F;
		bullet.gravity = 0.0D;
		bullet.wear = 10;
		bullet.explosive = 1F;
		bullet.style = BulletConfiguration.STYLE_BOLT;
		bullet.trail = 4;
		bullet.vPFX = "explode";
		
		bullet.bUpdate = (projectile) -> {

				if(!projectile.worldObj.isRemote) {
					
					if(projectile.ticksExisted % 2 == 0) {
						
						List<EntityCreature> creatures = projectile.worldObj.getEntitiesWithinAABB(EntityCreature.class, projectile.boundingBox.expand(10, 10, 10));
						
						for(EntityCreature creature : creatures) {
							
							if(creature.getClass().getCanonicalName().startsWith("net.minecraft.entity.titan")) {
								ExplosionNukeSmall.explode(projectile.worldObj, creature.posX, creature.posY, creature.posZ, ExplosionNukeSmall.medium);
								creature.isDead = true;
							}
						}
						
					}
				}
		};
		
		return bullet;
	}
}
