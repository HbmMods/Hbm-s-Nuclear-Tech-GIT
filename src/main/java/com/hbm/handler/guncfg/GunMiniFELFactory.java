package com.hbm.handler.guncfg;
import java.util.ArrayList;

import com.hbm.entity.projectile.EntityBulletBaseNT;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.potion.HbmPotion;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;

public class GunMiniFELFactory {
	public static GunConfiguration getMiniFelConfiguration() { // i cant even bother
		
		GunConfiguration config = new GunConfiguration();
		
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.firingDuration = 0;
		config.durability = 2500;
		config.allowsInfinity = false;
		config.crosshair = Crosshair.BOX;
		config.firingSound = "hbm:weapon.zomgShoot";
		config.maxCharge = 1_000_000;
		config.chargeRate = 2500;
		
		config.name = "LIY2001 Anti-Material Electromagnetic Rifle Prototype";
		config.manufacturer = EnumGunManufacturer.OXFORD;
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.GLASS_EMRADIO);
		config.config.add(BulletConfigSyncingUtil.GLASS_EMMICRO);
		config.config.add(BulletConfigSyncingUtil.GLASS_EMIR);
		config.config.add(BulletConfigSyncingUtil.GLASS_EMVISIBLE);
		config.config.add(BulletConfigSyncingUtil.GLASS_EMUV);
		config.config.add(BulletConfigSyncingUtil.GLASS_EMXRAY);
		config.config.add(BulletConfigSyncingUtil.GLASS_EMGAMMA);
		
		return config;
	}
	
	public static BulletConfiguration getEMRadioConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.velocity = 4.0F;
		bullet.spread = 0F;
		bullet.dmgMin = 35;
		bullet.dmgMax = 45;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		bullet.gravity = 0D;
		bullet.maxAge = 100;
		bullet.doesRicochet = true;
		bullet.ricochetAngle = 90;
		bullet.HBRC = 2;
		bullet.LBRC = 90;
		bullet.bounceMod = 1;
		bullet.doesPenetrate = true;
		bullet.style = BulletConfiguration.STYLE_BOLT;
		bullet.trail = BulletConfiguration.BOLT_LASER;
		bullet.plink = BulletConfiguration.PLINK_ENERGY;
		bullet.dischargePerShot = 25_000;
		bullet.firingRate = 20;
		bullet.modeName = "weapon.elecGun.glass_cannon.radio";
		bullet.chatColour = EnumChatFormatting.DARK_RED;
		bullet.setToFire(200);
		
		return bullet;
	}
	
	public static BulletConfiguration getEMMicroConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.velocity = 3.0F;
		bullet.spread = 0F;
		bullet.dmgMin = 18;
		bullet.dmgMax = 22;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		bullet.gravity = 0D;
		bullet.maxAge = 100;
		bullet.doesRicochet = true;
		bullet.ricochetAngle = 90;
		bullet.HBRC = 2;
		bullet.LBRC = 90;
		bullet.bounceMod = 1;
		bullet.doesPenetrate = true;
		bullet.style = BulletConfiguration.STYLE_BOLT;
		bullet.trail = BulletConfiguration.BOLT_LASER;
		bullet.dischargePerShot = 12_500;
		bullet.firingRate = 15;
		bullet.modeName = "weapon.elecGun.glass_cannon.micro";
		bullet.chatColour = EnumChatFormatting.RED;
		bullet.setToFire(200);
		
		return bullet;
	}
	
	public static BulletConfiguration getEMInfraredConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.velocity = 2.0F;
		bullet.spread = 0F;
		bullet.dmgMin = 9;
		bullet.dmgMax = 11;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		bullet.gravity = 0D;
		bullet.maxAge = 100;
		bullet.doesRicochet = true;
		bullet.ricochetAngle = 90;
		bullet.HBRC = 2;
		bullet.LBRC = 90;
		bullet.bounceMod = 1;
		bullet.doesPenetrate = true;
		bullet.style = BulletConfiguration.STYLE_BOLT;
		bullet.trail = BulletConfiguration.BOLT_LASER;
		bullet.dischargePerShot = 6_000;
		bullet.firingRate = 10;
		bullet.modeName = "weapon.elecGun.glass_cannon.ir";
		bullet.chatColour = EnumChatFormatting.RED;
		bullet.setToFire(100);
		
		return bullet;
	}
	
	public static BulletConfiguration getEMVisibleConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.velocity = 2.0F;
		bullet.spread = 0F;
		bullet.dmgMin = 4;
		bullet.dmgMax = 6;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		bullet.gravity = 0D;
		bullet.maxAge = 100;
		bullet.doesRicochet = true;
		bullet.ricochetAngle = 90;
		bullet.HBRC = 2;
		bullet.LBRC = 90;
		bullet.bounceMod = 1;
		bullet.doesPenetrate = true;
		bullet.style = BulletConfiguration.STYLE_BOLT;
		bullet.trail = BulletConfiguration.BOLT_WORM;
		bullet.dischargePerShot = 2_500;
		bullet.firingRate = 5;
		bullet.modeName = "weapon.elecGun.glass_cannon.visible";
		bullet.chatColour = EnumChatFormatting.GREEN;
		bullet.setToFire(100);
		
		return bullet;
	}
	
	public static BulletConfiguration getEMUVConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.velocity = 2.0F;
		bullet.spread = 0F;
		bullet.dmgMin = 3;
		bullet.dmgMax = 3;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		bullet.gravity = 0D;
		bullet.maxAge = 100;
		bullet.doesRicochet = true;
		bullet.ricochetAngle = 90;
		bullet.HBRC = 2;
		bullet.LBRC = 90;
		bullet.bounceMod = 1;
		bullet.doesPenetrate = true;
		bullet.style = BulletConfiguration.STYLE_BOLT;
		bullet.trail = BulletConfiguration.BOLT_GLASS_CYAN;
		bullet.dischargePerShot = 1_200;
		bullet.firingRate = 3;
		bullet.modeName = "weapon.elecGun.glass_cannon.uv";
		bullet.chatColour = EnumChatFormatting.AQUA;
		bullet.setToFire(100);
		
		return bullet;
	}
	
	public static BulletConfiguration getEMXrayConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.velocity = 2.0F;
		bullet.spread = 0F;
		bullet.dmgMin = 2;
		bullet.dmgMax = 2;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		bullet.gravity = 0D;
		bullet.maxAge = 100;
		bullet.doesRicochet = true;
		bullet.ricochetAngle = 90;
		bullet.HBRC = 2;
		bullet.LBRC = 90;
		bullet.bounceMod = 1;
		bullet.doesPenetrate = true;
		bullet.style = BulletConfiguration.STYLE_BOLT;
		bullet.trail = BulletConfiguration.BOLT_GLASS_BLUE;
		bullet.dischargePerShot = 800;
		bullet.firingRate = 2;
		bullet.modeName = "weapon.elecGun.glass_cannon.xray";
		bullet.chatColour = EnumChatFormatting.BLUE;
		bullet.setToFire(40);
		
		return bullet;
	}
	
	public static BulletConfiguration getEMGammaConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.velocity = 2.0F;
		bullet.spread = 0F;
		bullet.dmgMin = 1;
		bullet.dmgMax = 1;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		bullet.gravity = 0D;
		bullet.maxAge = 100;
		bullet.doesRicochet = true;
		bullet.ricochetAngle = 90;
		bullet.HBRC = 2;
		bullet.LBRC = 90;
		bullet.bounceMod = 1;
		bullet.doesPenetrate = true;
		bullet.style = BulletConfiguration.STYLE_BOLT;
		bullet.trail = BulletConfiguration.BOLT_LACUNAE;
		bullet.dischargePerShot = 400;
		bullet.firingRate = 1;
		bullet.modeName = "weapon.elecGun.glass_cannon.gamma";
		bullet.chatColour = EnumChatFormatting.LIGHT_PURPLE;
		bullet.setToFire(40);
		
		return bullet;
	}

}
    	
    	/*
        GunConfiguration config = new GunConfiguration();

        config.roundsPerCycle = 1;
        config.gunMode = GunConfiguration.MODE_NORMAL;
        config.firingMode = GunConfiguration.FIRE_AUTO;
        config.firingDuration = 0;
        config.durability = 2500;
        config.allowsInfinity = false;
        config.crosshair = Crosshair.BOX;
        config.firingSound = "hbm:weapon.zomgShoot";
        config.maxCharge = 1_000_000;
        config.chargeRate = 2500;

        config.name = "Handheld Miniaturised FEL";
		config.manufacturer = EnumGunManufacturer.OXFORD;

        return config;
    }
}
*/