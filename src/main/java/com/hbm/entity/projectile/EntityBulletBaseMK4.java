package com.hbm.entity.projectile;

import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.util.BobMathUtil;
import com.hbm.util.EntityDamageUtil;
import com.hbm.util.TrackerUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ForgeDirection;

public class EntityBulletBaseMK4 extends EntityThrowableInterp {
	
	public BulletConfig config;
	//used for rendering tracers
	public double velocity;
	public double prevVelocity;
	public float damage;
	public int ricochets = 0;

	public EntityBulletBaseMK4(World world) {
		super(world);
		this.renderDistanceWeight = 10.0D;
		this.setSize(0.5F, 0.5F);
	}
	
	public EntityBulletBaseMK4(EntityLivingBase entity, BulletConfig config, float baseDamage, float gunSpread, double sideOffset, double heightOffset, double frontOffset) {
		this(entity.worldObj);
		
		this.thrower = entity;
		this.setBulletConfig(config);
		
		this.damage = baseDamage * this.config.damageMult;
		
		this.setLocationAndAngles(thrower.posX, thrower.posY + thrower.getEyeHeight(), thrower.posZ, thrower.rotationYaw, thrower.rotationPitch);
		
		Vec3 offset = Vec3.createVectorHelper(sideOffset, heightOffset, frontOffset);
		offset.rotateAroundX(-this.rotationPitch / 180F * (float) Math.PI);
		offset.rotateAroundY(-this.rotationYaw / 180F * (float) Math.PI);

		this.posX += offset.xCoord;
		this.posY += offset.yCoord;
		this.posZ += offset.zCoord;
		
		this.setPosition(this.posX, this.posY, this.posZ);
		
		this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
		this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
		this.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI));
		
		this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, 1.0F, this.config.spread + gunSpread);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(3, Integer.valueOf(0));
	}
	
	public void setBulletConfig(BulletConfig config) {
		this.config = config;
		this.dataWatcher.updateObject(3, config.id);
	}
	
	public BulletConfig getBulletConfig() {
		int id = this.dataWatcher.getWatchableObjectInt(3);
		if(id < 0 || id > BulletConfig.configs.size()) return null;
		return BulletConfig.configs.get(id);
	}
	
	@Override
	public void onUpdate() {
		
		if(config == null) config = this.getBulletConfig();

		if(config == null){
			this.setDead();
			return;
		}

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		
		super.onUpdate();

		double dX = this.posX - this.prevPosX;
		double dY = this.posY - this.prevPosY;
		double dZ = this.posZ - this.prevPosZ;
		
		this.prevVelocity = this.velocity;
		this.velocity = Math.sqrt(dX * dX + dY * dY + dZ * dZ);
		
		if(!this.onGround && velocity > 0) {
			
			float hyp = MathHelper.sqrt_double(dX * dX + dZ * dZ);
			this.rotationYaw = (float) (Math.atan2(dX, dZ) * 180.0D / Math.PI);
	
			for(this.rotationPitch = (float) (Math.atan2(dY, (double) hyp) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F);
	
			while(this.rotationPitch - this.prevRotationPitch >= 180.0F) this.prevRotationPitch += 360.0F;
			while(this.rotationYaw - this.prevRotationYaw < -180.0F) this.prevRotationYaw -= 360.0F;
			while(this.rotationYaw - this.prevRotationYaw >= 180.0F) this.prevRotationYaw += 360.0F;
		}
		
		if(!worldObj.isRemote && this.ticksExisted > config.expires) this.setDead();
		
		if(this.config.onUpdate != null) this.config.onUpdate.accept(this);
	}
	
	@Override
	public void setDead() {
		super.setDead();
		
		//send a teleport on collision so that the bullets are forced to move even if their lifetime is only 1 tick, letting them render
		if(worldObj instanceof WorldServer) TrackerUtil.sendTeleport((WorldServer) worldObj, this);
	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {
		if(!worldObj.isRemote) {
			
			if(this.config.onImpact != null) this.config.onImpact.accept(this, mop);
			
			if(this.isDead) return;
			
			if(mop.typeOfHit == mop.typeOfHit.BLOCK) {

				ForgeDirection dir = ForgeDirection.getOrientation(mop.sideHit);
				Vec3 face = Vec3.createVectorHelper(dir.offsetX, dir.offsetY, dir.offsetZ);
				Vec3 vel = Vec3.createVectorHelper(motionX, motionY, motionZ).normalize();

				double angle = Math.abs(BobMathUtil.getCrossAngle(vel, face) - 90);

				if(angle <= config.ricochetAngle) {
					
					this.ricochets++;
					if(this.ricochets > this.config.maxRicochetCount) {
						this.setPosition(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
						this.setDead();
					}
					
					switch(mop.sideHit) {
					case 0: case 1: motionY *= -1; break;
					case 2: case 3: motionZ *= -1; break;
					case 4: case 5: motionX *= -1; break;
					}
					worldObj.playSoundAtEntity(this, "hbm:weapon.ricochet", 0.25F, 1.0F);
					this.setPosition(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
					//send a teleport so the ricochet is more accurate instead of the interp smoothing fucking everything up
					if(worldObj instanceof WorldServer) TrackerUtil.sendTeleport((WorldServer) worldObj, this);
					return;

				} else {
					this.setPosition(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
					this.setDead();
				}
			}
			
			if(mop.typeOfHit == mop.typeOfHit.ENTITY) {
				Entity entity = mop.entityHit;
				
				if(entity instanceof EntityLivingBase && ((EntityLivingBase) entity).getHealth() <= 0) {
					return;
				}
				
				DamageSource damageCalc = this.config.getDamage(this, getThrower(), false);
				
				if(!(entity instanceof EntityLivingBase)) {
					EntityDamageUtil.attackEntityFromIgnoreIFrame(entity, damageCalc, this.damage);
					return;
				}
				
				EntityLivingBase living = (EntityLivingBase) entity;
				float prevHealth = living.getHealth();
				
				if(this.config.armorPiercingPercent == 0) {
					EntityDamageUtil.attackEntityFromIgnoreIFrame(entity, damageCalc, this.damage);
				} else {
					DamageSource damagePiercing = this.config.getDamage(this, getThrower(), true);
					EntityDamageUtil.attackArmorPiercing(living, damageCalc, damagePiercing, this.damage, this.config.armorPiercingPercent);
				}
				
				float newHealth = living.getHealth();
				
				if(this.config.damageFalloffByPen) this.damage -= Math.max(prevHealth - newHealth, 0);
				if(!this.doesPenetrate() || this.damage < 0) {
					this.setPosition(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
					this.setDead();
				}
			}
		}
	}

	@Override protected double headingForceMult() { return 1D; }
	@Override public double getGravityVelocity() { return this.config.gravity; }
	@Override protected double motionMult() { return this.config.velocity; }
	@Override protected float getAirDrag() { return 1F; }
	@Override protected float getWaterDrag() { return 1F; }
	
	@Override public boolean doesImpactEntities() { return this.config.impactsEntities; }
	@Override public boolean doesPenetrate() { return this.config.doesPenetrate; }
	@Override public boolean isSpectral() { return this.config.isSpectral; }
	@Override public int selfDamageDelay() { return this.config.selfDamageDelay; }
}
