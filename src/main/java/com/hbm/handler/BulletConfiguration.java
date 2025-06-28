package com.hbm.handler;

import java.util.List;

import com.hbm.entity.projectile.EntityBulletBaseNT;
import com.hbm.entity.projectile.EntityBulletBaseNT.*;
import com.hbm.handler.guncfg.BulletConfigFactory;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.particle.SpentCasing;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumChatFormatting;

@Deprecated
public class BulletConfiguration implements Cloneable {
	
	//what item this specific configuration consumes
	public ComparableStack ammo;
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
	public float headshotMult = 1.0F;
	
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
	//how many ticks until the projectile can hurt the shooter
	public int selfDamageDelay = 5;

	//whether or not the bullet should penetrate mobs
	public boolean doesPenetrate;
	//disables collisions with blocks entirely
	public boolean isSpectral;
	//whether or not the bullet should break glass
	public boolean doesBreakGlass;
	//bullets still call the impact function when hitting blocks but do not get destroyed
	public boolean liveAfterImpact;
	
	//creates a "muzzle flash" and a ton of smoke with every projectile spawned
	public boolean blackPowder = false;
	
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
	/*public IBulletHurtBehavior bHurt;
	public IBulletHitBehavior bHit;
	public IBulletRicochetBehavior bRicochet;
	public IBulletImpactBehavior bImpact;
	public IBulletUpdateBehavior bUpdate;*/
	public IBulletHurtBehaviorNT bntHurt;
	public IBulletHitBehaviorNT bntHit;
	public IBulletRicochetBehaviorNT bntRicochet;
	public IBulletImpactBehaviorNT bntImpact;
	public IBulletUpdateBehaviorNT bntUpdate;
	
	//appearance
	public int style;
	//additional appearance data, i.e. particle effects
	public int trail;
	//ricochet sound type
	public int plink;
	//vanilla particle FX
	public String vPFX = "";
	public SpentCasing spentCasing;
	
	//energy projectiles
	//power consumed per shot
	public int dischargePerShot;
	//unlocalised firing mode name
	public String modeName;
	//firing mode text colour
	public EnumChatFormatting chatColour = EnumChatFormatting.WHITE;
	//firing rate
	public int firingRate;
	
	public String damageType = ModDamageSource.s_bullet;
	public boolean dmgProj = true;
	public boolean dmgFire = false;
	public boolean dmgExplosion = false;
	public boolean dmgBypass = false;

	public static final int STYLE_NONE = -1;
	public static final int STYLE_NORMAL = 0;
	public static final int STYLE_PISTOL = 1;
	public static final int STYLE_FLECHETTE = 2;
	public static final int STYLE_PELLET = 3;
	public static final int STYLE_BOLT = 4;
	public static final int STYLE_FOLLY = 5;
	public static final int STYLE_ROCKET = 6;
	public static final int STYLE_STINGER = 7;
	public static final int STYLE_GRENADE = 10;
	public static final int STYLE_BF = 11;
	public static final int STYLE_ORB = 12;
	public static final int STYLE_METEOR = 13;
	public static final int STYLE_APDS = 14;
	public static final int STYLE_BLADE = 15;
	public static final int STYLE_TAU = 17;
	public static final int STYLE_LEADBURSTER = 18;

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
	public static final int BOLT_GLASS_CYAN = 5;
	public static final int BOLT_GLASS_BLUE = 6;
	
	public BulletConfiguration setToBolt(int trail) {
		
		this.style = STYLE_BOLT;
		this.trail = trail;
		return this;
	}
	
	public BulletConfiguration setToFire(int duration) {
		
		this.incendiary = duration;
		return this;
	}
	
	public BulletConfiguration setHeadshot(float mult) {
		this.headshotMult = mult;
		return this;
	}
	
	public BulletConfiguration getChlorophyte() {
		this.bntUpdate = BulletConfigFactory.getHomingBehavior(30, 180);
		this.bntHurt = BulletConfigFactory.getPenHomingBehavior();
		this.dmgMin *= 2F;
		this.dmgMax *= 2F;
		this.wear *= 0.5;
		this.velocity *= 0.3;
		this.doesRicochet = false;
		this.doesPenetrate = true;
		this.vPFX = "greendust";
		
		if(this.spentCasing != null) {
			int[] colors = this.spentCasing.getColors();
			this.spentCasing = this.spentCasing.clone();
			
			if(colors != null && colors.length > 0) {
				int[] colorClone = new int[colors.length];
				for(int i = 0; i < colors.length; i++) colorClone[i] = colors[i];
				colorClone[colorClone.length - 1] = 0x659750; // <- standard chlorophyte coloring in last place
				this.spentCasing.setColor(colorClone).register(this.spentCasing.getName() + "Cl");
			}
		}
		
		return this;
	}
	
	public BulletConfiguration setToHoming(ItemStack ammo) {
		this.ammo = new ComparableStack(ammo);
		return getChlorophyte();
	}
	
	public BulletConfiguration accuracyMod(float mod) {
		
		this.spread *= mod;
		return this;
	}
	
	public DamageSource getDamage(EntityBulletBaseNT bullet, EntityLivingBase shooter) {
		
		DamageSource dmg;
		
		String unloc = damageType;
		
		if(unloc.equals(ModDamageSource.s_zomg_prefix))
			unloc += (bullet.worldObj.rand.nextInt(5) + 1); //pain
		
		if(shooter != null)
			dmg = new EntityDamageSourceIndirect(unloc, bullet, shooter);
		else
			dmg = new DamageSource(unloc);
		
		if(this.dmgProj) dmg.setProjectile();
		if(this.dmgFire) dmg.setFireDamage();
		if(this.dmgExplosion) dmg.setExplosion();
		if(this.dmgBypass) dmg.setDamageBypassesArmor();
		
		return dmg;
	}
	
	@Override
	public BulletConfiguration clone() {
		try {
			return (BulletConfiguration) super.clone();
		} catch(CloneNotSupportedException e) {
			MainRegistry.logger.catching(e);
			return new BulletConfiguration();
		}
	}
}
