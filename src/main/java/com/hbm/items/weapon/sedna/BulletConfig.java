package com.hbm.items.weapon.sedna;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.hbm.entity.projectile.EntityBulletBaseMK4;
import com.hbm.entity.projectile.EntityBulletBeamBase;
import com.hbm.interfaces.NotableComments;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmo;
import com.hbm.lib.ModDamageSource;
import com.hbm.particle.SpentCasing;
import com.hbm.util.BobMathUtil;
import com.hbm.util.EntityDamageUtil;
import com.hbm.util.TrackerUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ForgeDirection;

@NotableComments
public class BulletConfig implements Cloneable {
	
	public static List<BulletConfig> configs = new ArrayList();
	
	public int id;
	
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

	public Consumer<Entity> onUpdate;
	public BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> onImpact;
	public BiConsumer<EntityBulletBeamBase, MovingObjectPosition> onImpactBeam; //fuck fuck fuck fuck i should have used a better base class here god dammit
	public BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> onRicochet = LAMBDA_STANDARD_RICOCHET;
	public BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> onEntityHit = LAMBDA_STANDARD_ENTITY_HIT;
	
	public double gravity = 0;
	public int expires = 30;
	public boolean impactsEntities = true;
	public boolean doesPenetrate = false;
	/** Whether projectiles ignore blocks entirely */
	public boolean isSpectral = false;
	public int selfDamageDelay = 2;
	
	public boolean blackPowder = false;
	public boolean renderRotations = true;
	public SpentCasing casing;
	public BiConsumer<EntityBulletBaseMK4, Float> renderer;
	public BiConsumer<EntityBulletBeamBase, Float> rendererBeam;
	
	public BulletConfig() {
		this.id = configs.size();
		configs.add(this);
	}
	
	/** Required for the clone() operation to reset the ID, otherwise the ID and config entry will be the same as the original */
	public BulletConfig forceReRegister() {
		this.id = configs.size();
		configs.add(this);
		return this;
	}

	public BulletConfig setItem(Item ammo) {											this.ammo = new ComparableStack(ammo); return this; }
	public BulletConfig setItem(EnumAmmo ammo) {										this.ammo = new ComparableStack(ModItems.ammo_standard, 1, ammo.ordinal()); return this; }
	public BulletConfig setReloadCount(int ammoReloadCount) {							this.ammoReloadCount = ammoReloadCount; return this; }
	public BulletConfig setVel(float velocity) {										this.velocity = velocity; return this; }
	public BulletConfig setSpread(float spread) {										this.spread = spread; return this; }
	public BulletConfig setWear(float wear) {											this.wear = wear; return this; }
	public BulletConfig setProjectiles(int amount) {									this.projectilesMin = this.projectilesMax = amount; return this; }
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
	public BulletConfig setBlackPowder(boolean bp) {									this.blackPowder = bp; return this; }
	public BulletConfig setRenderRotations(boolean rot) {								this.renderRotations = rot; return this; }
	public BulletConfig setCasing(SpentCasing casing) {									this.casing = casing; return this; }
	
	public BulletConfig setRenderer(BiConsumer<EntityBulletBaseMK4, Float> renderer) {		this.renderer = renderer; return this; }
	public BulletConfig setRendererBeam(BiConsumer<EntityBulletBeamBase, Float> renderer) {	this.rendererBeam = renderer; return this; }

	public BulletConfig setOnUpdate(Consumer<Entity> lambda) {												this.onUpdate = lambda; return this; }
	public BulletConfig setOnRicochet(BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> lambda) {		this.onRicochet = lambda; return this; }
	public BulletConfig setOnImpact(BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> lambda) {			this.onImpact = lambda; return this; }
	public BulletConfig setOnBeamImpact(BiConsumer<EntityBulletBeamBase, MovingObjectPosition> lambda) {	this.onImpactBeam = lambda; return this; }
	public BulletConfig setOnEntityHit(BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> lambda) {		this.onEntityHit = lambda; return this; }
	
	public DamageSource getDamage(Entity projectile, EntityLivingBase shooter, boolean bypass) {
		
		DamageSource dmg;
		
		if(shooter != null) dmg = new EntityDamageSourceIndirect(damageType, projectile, shooter);
		else dmg = new DamageSource(damageType);
		
		if(this.dmgProj) dmg.setProjectile();
		if(this.dmgFire) dmg.setFireDamage();
		if(this.dmgExplosion) dmg.setExplosion();
		if(this.dmgBypass || bypass) dmg.setDamageBypassesArmor();
		
		return dmg;
	}
	
	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_STANDARD_RICOCHET = (bullet, mop) -> {
		
		if(mop.typeOfHit == mop.typeOfHit.BLOCK) {

			ForgeDirection dir = ForgeDirection.getOrientation(mop.sideHit);
			Vec3 face = Vec3.createVectorHelper(dir.offsetX, dir.offsetY, dir.offsetZ);
			Vec3 vel = Vec3.createVectorHelper(bullet.motionX, bullet.motionY, bullet.motionZ).normalize();

			double angle = Math.abs(BobMathUtil.getCrossAngle(vel, face) - 90);

			if(angle <= bullet.config.ricochetAngle) {
				
				bullet.ricochets++;
				if(bullet.ricochets > bullet.config.maxRicochetCount) {
					bullet.setPosition(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
					bullet.setDead();
				}
				
				switch(mop.sideHit) {
				case 0: case 1: bullet.motionY *= -1; break;
				case 2: case 3: bullet.motionZ *= -1; break;
				case 4: case 5: bullet.motionX *= -1; break;
				}
				bullet.worldObj.playSoundAtEntity(bullet, "hbm:weapon.ricochet", 0.25F, 1.0F);
				bullet.setPosition(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
				//send a teleport so the ricochet is more accurate instead of the interp smoothing fucking everything up
				if(bullet.worldObj instanceof WorldServer) TrackerUtil.sendTeleport((WorldServer) bullet.worldObj, bullet);
				return;

			} else {
				bullet.setPosition(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
				bullet.setDead();
			}
		}
	};
	
	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_STANDARD_ENTITY_HIT = (bullet, mop) -> {
		
		if(mop.typeOfHit == mop.typeOfHit.ENTITY) {
			Entity entity = mop.entityHit;
			
			if(entity == bullet.getThrower() && bullet.ticksExisted < bullet.selfDamageDelay()) return;
			if(entity instanceof EntityLivingBase && ((EntityLivingBase) entity).getHealth() <= 0) return;
			
			DamageSource damageCalc = bullet.config.getDamage(bullet, bullet.getThrower(), false);
			
			if(!(entity instanceof EntityLivingBase)) {
				EntityDamageUtil.attackEntityFromIgnoreIFrame(entity, damageCalc, bullet.damage);
				return;
			}
			
			EntityLivingBase living = (EntityLivingBase) entity;
			float prevHealth = living.getHealth();
			
			if(bullet.config.armorPiercingPercent == 0) {
				EntityDamageUtil.attackEntityFromIgnoreIFrame(entity, damageCalc, bullet.damage);
			} else {
				DamageSource damagePiercing = bullet.config.getDamage(bullet, bullet.getThrower(), true);
				EntityDamageUtil.attackArmorPiercing(living, damageCalc, damagePiercing, bullet.damage, bullet.config.armorPiercingPercent);
			}
			
			float newHealth = living.getHealth();
			
			if(bullet.config.damageFalloffByPen) bullet.damage -= Math.max(prevHealth - newHealth, 0) * 0.5;
			if(!bullet.doesPenetrate() || bullet.damage < 0) {
				bullet.setPosition(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
				bullet.setDead();
			}
		}
	};
	
	public static BiConsumer<EntityBulletBeamBase, MovingObjectPosition> LAMBDA_STANDARD_BEAM_HIT = (bullet, mop) -> {
		
		if(mop.typeOfHit == mop.typeOfHit.ENTITY) {
			Entity entity = mop.entityHit;
			
			if(entity instanceof EntityLivingBase && ((EntityLivingBase) entity).getHealth() <= 0) return;
			
			DamageSource damageCalc = bullet.config.getDamage(bullet, bullet.getThrower(), false);
			
			if(!(entity instanceof EntityLivingBase)) {
				EntityDamageUtil.attackEntityFromIgnoreIFrame(entity, damageCalc, bullet.damage);
				return;
			}
			
			EntityLivingBase living = (EntityLivingBase) entity;
			
			if(bullet.config.armorPiercingPercent == 0) {
				EntityDamageUtil.attackEntityFromIgnoreIFrame(entity, damageCalc, bullet.damage);
			} else {
				DamageSource damagePiercing = bullet.config.getDamage(bullet, bullet.getThrower(), true);
				EntityDamageUtil.attackArmorPiercing(living, damageCalc, damagePiercing, bullet.damage, bullet.config.armorPiercingPercent);
			}
		}
	};
	
	public static BiConsumer<EntityBulletBeamBase, MovingObjectPosition> LAMBDA_BEAM_HIT = (beam, mop) -> {
		
		if(mop.typeOfHit == mop.typeOfHit.ENTITY) {
			Entity entity = mop.entityHit;
			
			if(entity instanceof EntityLivingBase && ((EntityLivingBase) entity).getHealth() <= 0) return;
			
			DamageSource damageCalc = beam.config.getDamage(beam, beam.thrower, false);
			
			if(!(entity instanceof EntityLivingBase)) {
				EntityDamageUtil.attackEntityFromIgnoreIFrame(entity, damageCalc, beam.damage);
				return;
			}
			
			EntityLivingBase living = (EntityLivingBase) entity;
			
			if(beam.config.armorPiercingPercent == 0) {
				EntityDamageUtil.attackEntityFromIgnoreIFrame(entity, damageCalc, beam.damage);
			} else {
				DamageSource damagePiercing = beam.config.getDamage(beam, beam.thrower, true);
				EntityDamageUtil.attackArmorPiercing(living, damageCalc, damagePiercing, beam.damage, beam.config.armorPiercingPercent);
			}
		}
	};
	
	@Override
	public BulletConfig clone() {
		try {
			BulletConfig clone = (BulletConfig) super.clone();
			clone.forceReRegister();
			return clone;
		} catch(CloneNotSupportedException e) { }
		return null;
	}
}
