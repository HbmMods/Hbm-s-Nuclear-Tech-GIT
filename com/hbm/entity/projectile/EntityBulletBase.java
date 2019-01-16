package com.hbm.entity.projectile;

import com.hbm.handler.BulletConfiguration;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityBulletBase extends Entity implements IProjectile {
	
	private BulletConfiguration config;
	private EntityLivingBase shooter;

	public EntityBulletBase(World world) {
		super(world);
		this.renderDistanceWeight = 10.0D;
		this.setSize(0.5F, 0.5F);
	}

	public EntityBulletBase(World world, BulletConfiguration config) {
		super(world);
		this.config = config;
		this.renderDistanceWeight = 10.0D;
		this.setSize(0.5F, 0.5F);
	}
	
	public EntityBulletBase(World world, BulletConfiguration config, EntityLivingBase entity, float vel) {
		super(world);
		this.config = config;
		shooter = entity;

		this.setLocationAndAngles(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ, entity.rotationYaw, entity.rotationPitch);
		
		this.posX -= MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		this.posY -= 0.10000000149011612D;
		this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		this.setPosition(this.posX, this.posY, this.posZ);
		
		this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
		this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
		this.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI));

		this.renderDistanceWeight = 10.0D;
		this.setSize(0.5F, 0.5F);

		this.dataWatcher.updateObject(16, (byte)config.style);
		this.dataWatcher.updateObject(17, (byte)config.trail);
	}

	@Override
	public void setThrowableHeading(double moX, double moY, double moZ, float mult1, float mult2) {
		
		float deviation = 0;
		
		if(config != null)
			deviation = config.spread;
		
		float f2 = MathHelper.sqrt_double(moX * moX + moY * moY + moZ * moZ);
		moX /= f2;
		moY /= f2;
		moZ /= f2;
		moX += this.rand.nextGaussian() * /*(this.rand.nextBoolean() ? -1 : 1) **/ deviation * mult2;
		moY += this.rand.nextGaussian() * /*(this.rand.nextBoolean() ? -1 : 1) **/ deviation * mult2;
		moZ += this.rand.nextGaussian() * /*(this.rand.nextBoolean() ? -1 : 1) **/ deviation * mult2;
		moX *= mult1;
		moY *= mult1;
		moZ *= mult1;
		this.motionX = moX;
		this.motionY = moY;
		this.motionZ = moZ;
		
		float f3 = MathHelper.sqrt_double(moX * moX + moZ * moZ);
		this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(moX, moZ) * 180.0D / Math.PI);
		this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(moY, f3) * 180.0D / Math.PI);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotation2(double x, double y, double z, float r0, float r1, int i) {
		this.setPosition(x, y, z);
		this.setRotation(r0, r1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void setVelocity(double x, double y, double z) {
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;

		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
			float f = MathHelper.sqrt_double(x * x + z * z);
			this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(x, z) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(y, f) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch;
			this.prevRotationYaw = this.rotationYaw;
			this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
		}
	}

	@Override
	protected void entityInit() {
		//style
		this.dataWatcher.addObject(16, Byte.valueOf((byte) 0));
		//trail
		this.dataWatcher.addObject(17, Byte.valueOf((byte) 0));
	}
	
	@Override
	public void onUpdate() {
		
		super.onUpdate();
		
		if(config == null)
			return;
		
		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
			float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f) * 180.0D / Math.PI);
		}

		motionY -= config.gravity;
		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
		this.setPosition(this.posX, this.posY, this.posZ);

		float f2;
		this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
		f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
		for (this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f2) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
		{
			;
		}

		while(this.rotationPitch - this.prevRotationPitch >= 180.0F) {
			this.prevRotationPitch += 360.0F;
		}
		
		while(this.rotationYaw - this.prevRotationYaw < -180.0F) {
			this.prevRotationYaw -= 360.0F;
		}
		
		while(this.rotationYaw - this.prevRotationYaw >= 180.0F) {
			this.prevRotationYaw += 360.0F;
		}
		

		//this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
		//this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		// TODO Auto-generated method stub
		
	}

}
