package com.hbm.handler;

import java.util.List;

import com.hbm.handler.guncfg.BulletConfigFactory;
import com.hbm.interfaces.IBulletHitBehavior;
import com.hbm.interfaces.IBulletHurtBehavior;
import com.hbm.interfaces.IBulletImpactBehavior;
import com.hbm.interfaces.IBulletRicochetBehavior;
import com.hbm.interfaces.IBulletUpdateBehavior;

import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;

public class BulletConfiguration {
	
	//what item this specific configuration consumes
	public Item ammo;
	//how many ammo units one item restores
	public int ammoCount = 1;
	//how fast the bullet is (in sanics per second, or sps)
	public float velocity;
	//spread of bullets in gaussian range
	public float spread;
	//weapon durability reduced (centered around 10)
	public int wear;
	//greatest amount of pellets created each shot
	public int bulletsMin;
	//least amount of pellets created each shot
	public int bulletsMax;
	
	//damage bounds
	public float dmgMin;
	public float dmgMax;
	
	//acceleration torwards neg Y
	public double gravity;
	//max age in ticks before despawning
	public int maxAge;

	//whether the projectile should be able to bounce off of blocks
	public boolean doesRicochet;
	//the maximum angle at which the projectile should bounce
	public double ricochetAngle;
	//lower bound ricochet chance (below R angle)
	public int LBRC;
	//higher bound ricochet chance (above R angle)
	public int HBRC;
	//how much of the initial velocity is kept after bouncing
	public double bounceMod;

	//whether or not the bullet should penetrate mobs
	public boolean doesPenetrate;
	//whether or not the bullet should phase through blocks
	public boolean isSpectral;
	//whether or not the bullet should break glass
	public boolean doesBreakGlass;
	//whether the bullet should stay alive after colliding with a block
	public boolean liveAfterImpact;
	
	//bullet effects
	public List<PotionEffect> effects;
	public int incendiary;
	public int emp;
	public boolean blockDamage = true;
	public float explosive;
	public double jolt;
	public int rainbow;
	public int nuke;
	public int shrapnel;
	public int chlorine;
	public int leadChance;
	public int caustic;
	public boolean destroysBlocks;
	public boolean instakill;
	public IBulletHurtBehavior bHurt;
	public IBulletHitBehavior bHit;
	public IBulletRicochetBehavior bRicochet;
	public IBulletImpactBehavior bImpact;
	public IBulletUpdateBehavior bUpdate;
	
	//appearance
	public int style;
	//additional appearance data, i.e. particle effects
	public int trail;
	//ricochet sound type
	public int plink;
	//vanilla particle FX
	public String vPFX = "";

	public static final int STYLE_NONE = -1;
	public static final int STYLE_NORMAL = 0;
	public static final int STYLE_PISTOL = 1;
	public static final int STYLE_FLECHETTE = 2;
	public static final int STYLE_PELLET = 3;
	public static final int STYLE_BOLT = 4;
	public static final int STYLE_FOLLY = 5;
	public static final int STYLE_ROCKET = 6;
	public static final int STYLE_STINGER = 7;
	public static final int STYLE_NUKE = 8;
	public static final int STYLE_MIRV = 9;
	public static final int STYLE_GRENADE = 10;
	public static final int STYLE_BF = 11;
	public static final int STYLE_ORB = 12;
	public static final int STYLE_METEOR = 13;
	public static final int STYLE_APDS = 14;
	public static final int STYLE_BLADE = 15;

	public static final int PLINK_NONE = 0;
	public static final int PLINK_BULLET = 1;
	public static final int PLINK_GRENADE = 2;
	public static final int PLINK_ENERGY = 3;
	public static final int PLINK_SING = 4;

	public static final int BOLT_LACUNAE = 0;
	public static final int BOLT_NIGHTMARE = 1;
	public static final int BOLT_LASER = 2;
	public static final int BOLT_ZOMG = 3;
	public static final int BOLT_WORM = 4;
	
	public BulletConfiguration setToBolt(int trail) {
		
		this.style = STYLE_BOLT;
		this.trail = trail;
		return this;
	}
	
	public BulletConfiguration setToFire(int duration) {
		
		this.incendiary = duration;
		return this;
	}
	
	public BulletConfiguration setToGuided() {
		
		this.bUpdate = BulletConfigFactory.getLaserSteering();
		this.doesRicochet = false;
		return this;
	}
	
	public BulletConfiguration setToHoming(Item ammo) {
		
		this.ammo = ammo;
		this.bUpdate = BulletConfigFactory.getHomingBehavior(200, 45);
		this.dmgMin *= 1.5F;
		this.dmgMax *= 1.5F;
		this.wear *= 0.5;
		this.doesRicochet = false;
		this.doesPenetrate = false;
		this.vPFX = "greendust";
		return this;
	}
	
	public BulletConfiguration accuracyMod(float mod) {
		
		this.spread *= mod;
		return this;
	}
}
