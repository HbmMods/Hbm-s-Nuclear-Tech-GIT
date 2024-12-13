package com.hbm.entity.projectile;

import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.util.BobMathUtil;
import com.hbm.util.TrackerUtil;
import com.hbm.util.Vec3NT;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityTrackerEntry;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityBulletBaseMK4 extends EntityThrowableInterp {
	
	public BulletConfig config;
	//used for rendering tracers
	public double velocity;
	public double prevVelocity;
	public double accel;
	public float damage;
	public int ricochets = 0;
	public Entity lockonTarget = null;

	public EntityBulletBaseMK4(World world) {
		super(world);
		this.renderDistanceWeight = 10.0D;
		this.setSize(0.5F, 0.5F);
		this.isImmuneToFire = true;
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

		/*motionX += entity.motionX;
		motionY += entity.motionY;
		motionZ += entity.motionZ;*/
		
		this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, 1.0F, this.config.spread + gunSpread);
	}
	
	/** For turrets - angles are in radians, andp itch is negative! */
	public EntityBulletBaseMK4(World world, BulletConfig config, float baseDamage, float gunSpread, float yaw, float pitch) {
		this(world);
		
		this.setBulletConfig(config);
		this.damage = baseDamage * this.config.damageMult;
		
		this.prevRotationYaw = this.rotationYaw = yaw * 180F / (float) Math.PI;
		this.prevRotationPitch = this.rotationPitch = -pitch * 180F / (float) Math.PI;
		
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
		
		if(this.lockonTarget != null && !this.lockonTarget.isDead) {
			Vec3NT motion = new Vec3NT(motionX, motionY, motionZ);
			double vel = motion.lengthVector();
			Vec3NT delta = new Vec3NT(lockonTarget.posX - posX, lockonTarget.posY + lockonTarget.height / 2D - posY, lockonTarget.posZ - posZ);
			float turn = Math.min(0.005F * this.ticksExisted, 1F);
			Vec3NT newVec = new Vec3NT(
					BobMathUtil.interp(motion.xCoord, delta.xCoord, turn),
					BobMathUtil.interp(motion.yCoord, delta.yCoord, turn),
					BobMathUtil.interp(motion.zCoord, delta.zCoord, turn)).normalizeSelf().multiply(vel);
			this.motionX = newVec.xCoord;
			this.motionY = newVec.yCoord;
			this.motionZ = newVec.zCoord;
			EntityTrackerEntry entry = TrackerUtil.getTrackerEntry((WorldServer) worldObj, this.getEntityId());
			entry.lastYaw = MathHelper.floor_float(this.rotationYaw * 256.0F / 360.0F) + 10; //force-trigger rotation update
		}
		
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
			if(this.config.onRicochet != null) this.config.onRicochet.accept(this, mop);
			if(this.config.onEntityHit != null) this.config.onEntityHit.accept(this, mop);
		}
	}

	@Override protected double headingForceMult() { return 1D; }
	@Override public double getGravityVelocity() { return this.config.gravity; }
	@Override protected double motionMult() { return this.config.velocity + this.accel; }
	@Override protected float getAirDrag() { return 1F; }
	@Override protected float getWaterDrag() { return 1F; }
	
	@Override public boolean doesImpactEntities() { return this.config.impactsEntities; }
	@Override public boolean doesPenetrate() { return this.config.doesPenetrate; }
	@Override public boolean isSpectral() { return this.config.isSpectral; }
	@Override public int selfDamageDelay() { return this.config.selfDamageDelay; }
	
	@Override @SideOnly(Side.CLIENT) public boolean canRenderOnFire() { return false; }
}
