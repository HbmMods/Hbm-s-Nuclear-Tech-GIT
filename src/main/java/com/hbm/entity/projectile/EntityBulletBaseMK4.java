package com.hbm.entity.projectile;

import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.lib.ModDamageSource;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.util.BobMathUtil;
import com.hbm.util.TrackerUtil;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ForgeDirection;

public class EntityBulletBaseMK4 extends EntityThrowableInterp {
	
	public BulletConfig config;
	public double velocity;
	public double prevVelocity;
	public int ricochets = 0;

	public EntityBulletBaseMK4(World world) {
		super(world);
		this.renderDistanceWeight = 10.0D;
		this.setSize(0.5F, 0.5F);
	}
	
	public EntityBulletBaseMK4(EntityLivingBase entity, BulletConfig config, float baseDamage, float spreadMod, double sideOffset, double heightOffset, double frontOffset) {
		this(entity.worldObj);
		
		this.thrower = entity;
		this.config = config;
		
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
		
		this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, 1.0F, this.config.spread * spreadMod);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(3, Integer.valueOf(0));
	}
	
	public void setBulletConfig(BulletConfig config) {
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
				if(!mop.entityHit.isEntityAlive()) return;
				
				if(mop.entityHit.isEntityAlive()) {
					mop.entityHit.attackEntityFrom(ModDamageSource.turbofan, 1_000F);
					
					NBTTagCompound vdat = new NBTTagCompound();
					vdat.setString("type", "giblets");
					vdat.setInteger("ent", mop.entityHit.getEntityId());
					vdat.setInteger("cDiv", 2);
					PacketDispatcher.wrapper.sendToAllAround(
							new AuxParticlePacketNT(vdat, mop.entityHit.posX, mop.entityHit.posY + mop.entityHit.height * 0.5, mop.entityHit.posZ),
							new TargetPoint(this.dimension, mop.entityHit.posX, mop.entityHit.posY + mop.entityHit.height * 0.5, mop.entityHit.posZ, 150));
				}
			}
		}
	}

	@Override protected double headingForceMult() { return 1D; }
	@Override public double getGravityVelocity() { return this.config.gravity; }
	@Override protected double motionMult() { return this.config.velocity; }
	@Override protected float getAirDrag() { return 1F; }
	@Override protected float getWaterDrag() { return 1F; }
}
