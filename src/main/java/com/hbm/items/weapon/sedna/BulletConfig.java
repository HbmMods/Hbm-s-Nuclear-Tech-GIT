package com.hbm.items.weapon.sedna;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import com.hbm.entity.projectile.EntityBulletBaseMK4;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmo;
import com.hbm.lib.ModDamageSource;
import com.hbm.particle.SpentCasing;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;

public class BulletConfig {
	
	public static List<BulletConfig> configs = new ArrayList();
	
	public final int id;
	
	public ComparableStack ammo;
	/** How much ammo is added to a standard mag when loading one item */
	public int ammoReloadCount = 1;
	public float velocity = 10F;
	public float spread = 0F;
	public float wear = 1F;
	public int projectilesMin = 1;
	public int projectilesMax = 1;

	public float damageMult = 1.0F;
	public float armorPiercingPercent = 0.0F;
	public float headshotMult = 1.0F;
	
	public String damageType = ModDamageSource.s_bullet;
	public boolean dmgProj = true;
	public boolean dmgFire = false;
	public boolean dmgExplosion = false;
	public boolean dmgBypass = false;
	
	public float ricochetAngle = 5F;
	public int maxRicochetCount = 2;
	/** Whether damage dealt to an entity is subtracted from the projectile's damage on penetration */
	public boolean damageFalloffByPen = true;
	
	public double gravity = 0;
	public int expires = 30;
	public boolean impactsEntities = true;
	public boolean doesPenetrate = false;
	/** Whether projectiles ignore blocks entirely */
	public boolean isSpectral = false;
	public int selfDamageDelay = 2;
	
	public boolean renderRotations = true;
	public SpentCasing casing;
	public BiConsumer<EntityBulletBaseMK4, Float> renderer;
	
	public BulletConfig() {
		this.id = configs.size();
		configs.add(this);
	}

	public BulletConfig setItem(Item ammo) {											this.ammo = new ComparableStack(ammo); return this; }
	public BulletConfig setItem(EnumAmmo ammo) {										this.ammo = new ComparableStack(ModItems.ammo_standard, 1, ammo.ordinal()); return this; }
	public BulletConfig setReloadCount(int ammoReloadCount) {							this.ammoReloadCount = ammoReloadCount; return this; }
	public BulletConfig setVel(float velocity) {										this.velocity = velocity; return this; }
	public BulletConfig setSpread(float spread) {										this.spread = spread; return this; }
	public BulletConfig setWear(float wear) {											this.wear = wear; return this; }
	public BulletConfig setProjectiles(int min, int max) {								this.projectilesMin = min; this.projectilesMax = max; return this; }
	public BulletConfig setDamage(float damageMult) {									this.damageMult = damageMult; return this; }
	public BulletConfig setArmorPiercing(float armorPiercingPercent) {					this.armorPiercingPercent = armorPiercingPercent; return this; }
	public BulletConfig setHeadshot(float headshotMult) {								this.headshotMult = headshotMult; return this; }
	public BulletConfig setDamageType(String type) {									this.damageType = type; return this; }
	public BulletConfig setupDamageClass(boolean proj, boolean fire, boolean explosion, boolean bypass) {	this.dmgProj = proj; this.dmgFire = fire; this.dmgExplosion = explosion; this.dmgBypass = bypass; return this; }
	public BulletConfig setRicochetAngle(float angle) {									this.ricochetAngle = angle; return this; }
	public BulletConfig setRicochetCount(int count) {									this.maxRicochetCount = count; return this; }
	public BulletConfig setDamageFalloutByPen(boolean falloff) {						this.damageFalloffByPen = falloff; return this; }
	public BulletConfig setGrav(double gravity) {										this.gravity = gravity; return this; }
	public BulletConfig setLife(int expires) {											this.expires = expires; return this; }
	public BulletConfig setImpactsEntities(boolean impact) {							this.impactsEntities = impact; return this; }
	public BulletConfig setDoesPenetrate(boolean pen) {									this.doesPenetrate = pen; return this; }
	public BulletConfig setSpectral(boolean spectral) {									this.isSpectral = spectral; return this; }
	public BulletConfig setSelfDamageDelay(int delay) {									this.selfDamageDelay = delay; return this; }
	public BulletConfig setRenderRotations(boolean rot) {								this.renderRotations = rot; return this; }
	public BulletConfig setCasing(SpentCasing casing) {									this.casing = casing; return this; }
	public BulletConfig setRenderer(BiConsumer<EntityBulletBaseMK4, Float> renderer) {	this.renderer = renderer; return this; }
	
	public DamageSource getDamage(EntityBulletBaseMK4 bullet, EntityLivingBase shooter, boolean bypass) {
		
		DamageSource dmg;
		
		if(shooter != null) dmg = new EntityDamageSourceIndirect(damageType, bullet, shooter);
		else dmg = new DamageSource(damageType);
		
		if(this.dmgProj) dmg.setProjectile();
		if(this.dmgFire) dmg.setFireDamage();
		if(this.dmgExplosion) dmg.setExplosion();
		if(this.dmgBypass || bypass) dmg.setDamageBypassesArmor();
		
		return dmg;
	}
}
