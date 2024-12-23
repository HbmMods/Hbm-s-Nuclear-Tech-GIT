package com.hbm.items.weapon.sedna;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.hbm.blocks.bomb.BlockDetonatable;
import com.hbm.entity.projectile.EntityBulletBaseMK4;
import com.hbm.entity.projectile.EntityBulletBeamBase;
import com.hbm.interfaces.NotableComments;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.factory.ConfettiUtil;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmo;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmoSecret;
import com.hbm.particle.SpentCasing;
import com.hbm.util.BobMathUtil;
import com.hbm.util.EntityDamageUtil;
import com.hbm.util.TrackerUtil;

import com.hbm.util.DamageResistanceHandler.DamageClass;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
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
	public ProjectileType pType = ProjectileType.BULLET;

	public float damageMult = 1.0F;
	public float armorThresholdNegation = 0.0F;
	public float armorPiercingPercent = 0.0F;
	public float knockbackMult = 0.1F;
	public float headshotMult = 1.25F;
	
	public DamageClass dmgClass = DamageClass.PHYSICAL;
	
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

	public BulletConfig setBeam() {														this.pType = ProjectileType.BEAM; return this; }
	public BulletConfig setChunkloading() {												this.pType = ProjectileType.BULLET_CHUNKLOADING; return this; }
	public BulletConfig setItem(Item ammo) {											this.ammo = new ComparableStack(ammo); return this; }
	public BulletConfig setItem(ItemStack ammo) {										this.ammo = new ComparableStack(ammo); return this; }
	public BulletConfig setItem(EnumAmmo ammo) {										this.ammo = new ComparableStack(ModItems.ammo_standard, 1, ammo.ordinal()); return this; }
	public BulletConfig setItem(EnumAmmoSecret ammo) {									this.ammo = new ComparableStack(ModItems.ammo_secret, 1, ammo.ordinal()); return this; }
	public BulletConfig setReloadCount(int ammoReloadCount) {							this.ammoReloadCount = ammoReloadCount; return this; }
	public BulletConfig setVel(float velocity) {										this.velocity = velocity; return this; }
	public BulletConfig setSpread(float spread) {										this.spread = spread; return this; }
	public BulletConfig setWear(float wear) {											this.wear = wear; return this; }
	public BulletConfig setProjectiles(int amount) {									this.projectilesMin = this.projectilesMax = amount; return this; }
	public BulletConfig setProjectiles(int min, int max) {								this.projectilesMin = min; this.projectilesMax = max; return this; }
	public BulletConfig setDamage(float damageMult) {									this.damageMult = damageMult; return this; }
	public BulletConfig setThresholdNegation(float armorThresholdNegation) {			this.armorThresholdNegation = armorThresholdNegation; return this; }
	public BulletConfig setArmorPiercing(float armorPiercingPercent) {					this.armorPiercingPercent = armorPiercingPercent; return this; }
	public BulletConfig setKnockback(float knockbackMult) {								this.knockbackMult = knockbackMult; return this; }
	public BulletConfig setHeadshot(float headshotMult) {								this.headshotMult = headshotMult; return this; }
	public BulletConfig setupDamageClass(DamageClass clazz) {							this.dmgClass = clazz; return this; }
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
	
	public static enum ProjectileType {
		BULLET,
		BULLET_CHUNKLOADING,
		BEAM
	}
	
	@SuppressWarnings("incomplete-switch") //shut up
	public static DamageSource getDamage(Entity projectile, EntityLivingBase shooter, DamageClass dmgClass) {
		
		DamageSource dmg;
		
		if(shooter != null) dmg = new DamageSourceSednaWithAttacker(dmgClass.name(), projectile, shooter);
		else dmg = new DamageSourceSednaNoAttacker(dmgClass.name());
		
		switch(dmgClass) {
		case PHYSICAL: dmg.setProjectile(); break;
		case FIRE: dmg.setFireDamage(); break;
		case EXPLOSIVE: dmg.setExplosion(); break;
		case ELECTRIC: break;
		case LASER: break;
		case SUBATOMIC: break;
		}
		
		return dmg;
	}
	
	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_STANDARD_RICOCHET = (bullet, mop) -> {
		
		if(mop.typeOfHit == mop.typeOfHit.BLOCK) {
			
			Block b = bullet.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ);
			if(b.getMaterial() == Material.glass) {
				bullet.worldObj.func_147480_a(mop.blockX, mop.blockY, mop.blockZ, false);
				bullet.setPosition(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
				return;
			}
			if(b instanceof BlockDetonatable) {
				((BlockDetonatable) b).onShot(bullet.worldObj, mop.blockX, mop.blockY, mop.blockZ);
			}

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
			
			DamageSource source = bullet.config.getDamage(bullet, bullet.getThrower(), bullet.config.dmgClass);
			float intendedDamage = bullet.damage;
			
			if(!(entity instanceof EntityLivingBase)) {
				EntityDamageUtil.attackEntityFromIgnoreIFrame(entity, source, bullet.damage);
				return;
			} else if(bullet.config.headshotMult > 1F) {
				
				EntityLivingBase living = (EntityLivingBase) entity;
				double head = living.height - living.getEyeHeight();
				
				if(!!living.isEntityAlive() && mop.hitVec != null && mop.hitVec.yCoord > (living.posY + living.height - head * 2)) {
					intendedDamage *= bullet.config.headshotMult;
				}
			}
			
			EntityLivingBase living = (EntityLivingBase) entity;
			float prevHealth = living.getHealth();
			
			EntityDamageUtil.attackEntityFromNT(living, source, intendedDamage, true, true, bullet.config.knockbackMult, bullet.config.armorThresholdNegation, bullet.config.armorPiercingPercent);
			
			float newHealth = living.getHealth();
			
			if(bullet.config.damageFalloffByPen) bullet.damage -= Math.max(prevHealth - newHealth, 0) * 0.5;
			if(!bullet.doesPenetrate() || bullet.damage < 0) {
				bullet.setPosition(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
				bullet.setDead();
			}
			
			if(!living.isEntityAlive()) ConfettiUtil.decideConfetti(living, source);
		}
	};
	
	public static BiConsumer<EntityBulletBeamBase, MovingObjectPosition> LAMBDA_STANDARD_BEAM_HIT = (bullet, mop) -> {
		
		if(mop.typeOfHit == mop.typeOfHit.ENTITY) {
			Entity entity = mop.entityHit;
			
			if(entity instanceof EntityLivingBase && ((EntityLivingBase) entity).getHealth() <= 0) return;

			DamageSource source = bullet.config.getDamage(bullet, bullet.getThrower(), bullet.config.dmgClass);
			
			if(!(entity instanceof EntityLivingBase)) {
				EntityDamageUtil.attackEntityFromIgnoreIFrame(entity, source, bullet.damage);
				return;
			}
			
			EntityLivingBase living = (EntityLivingBase) entity;
			EntityDamageUtil.attackEntityFromNT(living, source, bullet.damage, true, true, bullet.config.knockbackMult, bullet.config.armorThresholdNegation, bullet.config.armorPiercingPercent);
			if(!living.isEntityAlive()) ConfettiUtil.decideConfetti(living, source);
		}
	};
	
	public static BiConsumer<EntityBulletBeamBase, MovingObjectPosition> LAMBDA_BEAM_HIT = (beam, mop) -> {
		
		if(mop.typeOfHit == mop.typeOfHit.ENTITY) {
			Entity entity = mop.entityHit;
			
			if(entity instanceof EntityLivingBase && ((EntityLivingBase) entity).getHealth() <= 0) return;
			
			DamageSource source = beam.config.getDamage(beam, beam.thrower, beam.config.dmgClass);
			
			if(!(entity instanceof EntityLivingBase)) {
				EntityDamageUtil.attackEntityFromIgnoreIFrame(entity, source, beam.damage);
				return;
			}
			
			EntityLivingBase living = (EntityLivingBase) entity;
			EntityDamageUtil.attackEntityFromNT(living, source, beam.damage, true, false, beam.config.knockbackMult, beam.config.armorThresholdNegation, beam.config.armorPiercingPercent);
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
