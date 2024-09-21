package com.hbm.entity.projectile;

import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.lib.ModDamageSource;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.AuxParticlePacketNT;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityBulletBaseMK4 extends EntityThrowableInterp {
	
	public BulletConfig config;
	public double velocity;
	public double prevVelocity;

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
	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {
		if(!worldObj.isRemote) {
			if(mop.typeOfHit == mop.typeOfHit.ENTITY && !mop.entityHit.isEntityAlive()) return;
			
			this.setDead();
			
			if(mop.typeOfHit == mop.typeOfHit.ENTITY && mop.entityHit.isEntityAlive()) {
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

	@Override protected double headingForceMult() { return 1D; }
	@Override public double getGravityVelocity() { return this.config.gravity; }
	@Override protected double motionMult() { return this.config.velocity; }
	@Override protected float getAirDrag() { return 1F; }
	@Override protected float getWaterDrag() { return 1F; }
}
