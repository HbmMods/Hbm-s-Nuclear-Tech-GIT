package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.entity.particle.EntityBSmokeFX;
import com.hbm.entity.projectile.EntityBoxcar;
import com.hbm.entity.projectile.EntityBuilding;
import com.hbm.entity.projectile.EntityDuchessGambit;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.CasingEjector;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.items.ItemAmmoEnums.Ammo44Magnum;
import com.hbm.lib.HbmCollection;
import com.hbm.lib.RefStrings;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.particle.SpentCasing;
import com.hbm.particle.SpentCasing.CasingType;
import com.hbm.potion.HbmPotion;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class Gun44MagnumFactory {
	
	private static final CasingEjector EJECTOR_PIP;
	private static final SpentCasing CASING44;

	static {
		EJECTOR_PIP = new CasingEjector().setMotion(Vec3.createVectorHelper(0, 0, -0.05)).setOffset(Vec3.createVectorHelper(0, -0.15, 0)).setAngleRange(0.01F, 0.05F).setAfterReload().setAmount(6);
		CASING44 = new SpentCasing(CasingType.STRAIGHT).setScale(1.5F, 1.0F, 1.5F).setBounceMotion(0.01F, 0.05F).setColor(SpentCasing.COLOR_CASE_44);
	}
	
	public static GunConfiguration getBaseConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 10;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.reloadDuration = 10;
		config.firingDuration = 0;
		config.ammoCap = 6;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CLASSIC;
		config.reloadSound = GunConfiguration.RSOUND_REVOLVER;
		config.firingSound = "hbm:weapon.revolverShootAlt";
		config.reloadSoundEnd = false;
		
		config.config.addAll(HbmCollection.m44Normal);
		
		config.ejector = EJECTOR_PIP;
		
		return config;
	}
	
	public static GunConfiguration getNovacConfig() {
		
		GunConfiguration config = getBaseConfig();
		
		config.durability = 2500;
		
		config.name = "ifHorseshoe";
		config.manufacturer = EnumGunManufacturer.IF;
		config.comment.add("Fallout New Vegas wasn't THAT good.");
		
		return config;
	}
	
	public static final ResourceLocation scope_lilmac = new ResourceLocation(RefStrings.MODID, "textures/misc/scope_44.png");
	
	public static GunConfiguration getMacintoshConfig() {
		
		GunConfiguration config = getBaseConfig();
		
		config.durability = 4000;
		
		config.name = "ifScope";
		config.manufacturer = EnumGunManufacturer.IF;
		config.comment.add("Poppin' mentats like tic tacs");
		
		config.allowsInfinity = false;
		config.hasSights = true;
		config.absoluteFOV = true;
		config.zoomFOV = 0.25F;
		config.scopeTexture = scope_lilmac;
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.M44_PIP);
		config.config.addAll(HbmCollection.m44Normal);
		
		return config;
	}
	
	public static GunConfiguration getBlackjackConfig() {
		
		GunConfiguration config = getBaseConfig();
		
		config.durability = 4000;
		config.ammoCap = 5;

		config.allowsInfinity = false;
		config.name = "ifVanity";
		config.manufacturer = EnumGunManufacturer.IF;
		config.comment.add("Alcoholism is cool!");
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.M44_BJ);
		config.config.addAll(HbmCollection.m44Normal);
		
		config.ejector = EJECTOR_PIP.clone().setAmount(5);
		
		return config;
	}
	
	public static GunConfiguration getSilverConfig() {
		
		GunConfiguration config = getBaseConfig();
		
		config.durability = 4000;
		config.ammoCap = 6;

		config.allowsInfinity = false;
		config.name = "ifStorm";
		config.manufacturer = EnumGunManufacturer.IF;
		config.comment.add("Our friendship is based on abusive behaviour");
		config.comment.add("and mutual hate. It's not that complicated.");
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.M44_SILVER);
		config.config.addAll(HbmCollection.m44Normal);
		
		return config;
	}
	
	public static GunConfiguration getRedConfig() {
		
		GunConfiguration config = getBaseConfig();
		
		config.durability = 4000;
		config.ammoCap = 8;

		config.allowsInfinity = false;
		config.name = "ifPit";
		config.manufacturer = EnumGunManufacturer.IF;
		config.comment.add("Explore the other side");
		config.comment.add("...from afar!");
		
		config.config = new ArrayList<Integer>();
		config.config.addAll(HbmCollection.m44All);
		
		config.ejector = EJECTOR_PIP.clone().setAmount(64);
		
		return config;
	}
	
	public static BulletConfiguration getNoPipConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_44.stackFromEnum(Ammo44Magnum.STOCK));
		bullet.dmgMin = 18;
		bullet.dmgMax = 26;
		
		bullet.spentCasing = CASING44.clone().register("44NoPip");
		
		return bullet;
	}
	
	public static BulletConfiguration getNoPipAPConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_44.stackFromEnum(Ammo44Magnum.AP));
		bullet.dmgMin = 25;
		bullet.dmgMax = 32;
		bullet.wear = 15;
		bullet.leadChance = 10;
		
		bullet.spentCasing = CASING44.clone().register("44AP");
		
		return bullet;
	}
	
	public static BulletConfiguration getNoPipDUConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_44.stackFromEnum(Ammo44Magnum.DU));
		bullet.dmgMin = 28;
		bullet.dmgMax = 40;
		bullet.wear = 25;
		bullet.leadChance = 50;
		
		bullet.spentCasing = CASING44.clone().register("44DU");
		
		return bullet;
	}
	
	public static BulletConfiguration getPhosphorusConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_44.stackFromEnum(Ammo44Magnum.PHOSPHORUS));
		bullet.dmgMin = 18;
		bullet.dmgMax = 26;
		bullet.wear = 15;
		bullet.incendiary = 5;
		bullet.doesPenetrate = false;
		
		PotionEffect eff = new PotionEffect(HbmPotion.phosphorus.id, 20 * 20, 0, true);
		eff.getCurativeItems().clear();
		bullet.effects = new ArrayList();
		bullet.effects.add(new PotionEffect(eff));
		
		bullet.bntImpact = (bulletnt, x, y, z) -> {
			
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "vanillaburst");
			data.setString("mode", "flame");
			data.setInteger("count", 15);
			data.setDouble("motion", 0.05D);
			
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, bulletnt.posX, bulletnt.posY, bulletnt.posZ), new TargetPoint(bulletnt.dimension, bulletnt.posX, bulletnt.posY, bulletnt.posZ, 50));
		};
		
		bullet.spentCasing = CASING44.clone().register("44Phos");
		
		return bullet;
	}
	
	public static BulletConfiguration getNoPipStarConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_44.stackFromEnum(Ammo44Magnum.STAR));
		bullet.dmgMin = 42;
		bullet.dmgMax = 50;
		bullet.wear = 25;
		bullet.leadChance = 100;
		
		bullet.spentCasing = CASING44.clone().register("44Star");
		
		return bullet;
	}
	
	public static BulletConfiguration getPipConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_44.stackFromEnum(Ammo44Magnum.PIP));
		bullet.dmgMin = 30;
		bullet.dmgMax = 36;
		bullet.wear = 25;
		bullet.doesPenetrate = false;
		
		bullet.bntHit = (bulletnt, hit) -> {
				
			if(!bulletnt.worldObj.isRemote) {
				EntityBoxcar pippo = new EntityBoxcar(bulletnt.worldObj);
				pippo.posX = hit.posX;
				pippo.posY = hit.posY + 50;
				pippo.posZ = hit.posZ;
				
				for(int j = 0; j < 50; j++) {
					EntityBSmokeFX fx = new EntityBSmokeFX(bulletnt.worldObj, pippo.posX + (bulletnt.worldObj.rand.nextDouble() - 0.5) * 4, pippo.posY + (bulletnt.worldObj.rand.nextDouble() - 0.5) * 12, pippo.posZ + (bulletnt.worldObj.rand.nextDouble() - 0.5) * 4, 0, 0, 0);
					bulletnt.worldObj.spawnEntityInWorld(fx);
				}
				bulletnt.worldObj.spawnEntityInWorld(pippo);
				
				bulletnt.worldObj.playSoundEffect(pippo.posX, pippo.posY + 50, pippo.posZ, "hbm:alarm.trainHorn", 100F, 1F);
			}
		};
		
		bullet.spentCasing = CASING44.clone().register("44Pip").setColor(0x532C64);
		
		return bullet;
	}
	
	public static BulletConfiguration getBJConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_44.stackFromEnum(Ammo44Magnum.BJ));
		bullet.dmgMin = 30;
		bullet.dmgMax = 36;
		bullet.wear = 25;
		bullet.doesPenetrate = false;
		
		bullet.bntHit = (bulletnt, hit) -> {
				
			if(!bulletnt.worldObj.isRemote) {
				EntityDuchessGambit pippo = new EntityDuchessGambit(bulletnt.worldObj);
				pippo.posX = hit.posX;
				pippo.posY = hit.posY + 50;
				pippo.posZ = hit.posZ;
				
				for(int j = 0; j < 150; j++) {
					EntityBSmokeFX fx = new EntityBSmokeFX(bulletnt.worldObj, pippo.posX + (bulletnt.worldObj.rand.nextDouble() - 0.5) * 7, pippo.posY + (bulletnt.worldObj.rand.nextDouble() - 0.5) * 8, pippo.posZ + (bulletnt.worldObj.rand.nextDouble() - 0.5) * 18, 0, 0, 0);
					bulletnt.worldObj.spawnEntityInWorld(fx);
				}
				bulletnt.worldObj.spawnEntityInWorld(pippo);
				
				bulletnt.worldObj.playSoundEffect(pippo.posX, pippo.posY + 50, pippo.posZ, "hbm:weapon.boat", 100F, 1F);
			}
		};
		
		bullet.spentCasing = CASING44.clone().register("44BJ").setColor(0x632B2C);
		
		return bullet;
	}
	
	public static BulletConfiguration getSilverStormConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_44.stackFromEnum(Ammo44Magnum.SILVER));
		bullet.dmgMin = 30;
		bullet.dmgMax = 36;
		bullet.wear = 25;
		bullet.doesPenetrate = false;
		
		bullet.bntHit = (bulletnt, hit) -> {
				
			if(!bulletnt.worldObj.isRemote) {
				EntityBuilding pippo = new EntityBuilding(bulletnt.worldObj);
				pippo.posX = hit.posX;
				pippo.posY = hit.posY + 50;
				pippo.posZ = hit.posZ;
				
				for(int j = 0; j < 150; j++) {
					EntityBSmokeFX fx = new EntityBSmokeFX(bulletnt.worldObj, pippo.posX + (bulletnt.worldObj.rand.nextDouble() - 0.5) * 15, pippo.posY + (bulletnt.worldObj.rand.nextDouble() - 0.5) * 15, pippo.posZ + (bulletnt.worldObj.rand.nextDouble() - 0.5) * 15, 0, 0, 0);
					bulletnt.worldObj.spawnEntityInWorld(fx);
				}
				bulletnt.worldObj.spawnEntityInWorld(pippo);
				
				bulletnt.worldObj.playSoundEffect(pippo.posX, pippo.posY + 50, pippo.posZ, "hbm:block.debris", 100F, 1F);
			}
		};
		
		bullet.spentCasing = CASING44.clone().register("44Silver").setColor(0x2B5963);
		
		return bullet;
	}
	
	public static BulletConfiguration getRocketConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_44.stackFromEnum(Ammo44Magnum.ROCKET));
		bullet.velocity = 5;
		bullet.explosive = 15F;
		bullet.trail = 1;
		
		bullet.spentCasing = CASING44.clone().register("44Rocket");
		
		return bullet;
	}

}
