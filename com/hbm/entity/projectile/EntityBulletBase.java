package com.hbm.entity.projectile;

import com.hbm.handler.BulletConfiguration;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityBulletBase extends Entity implements IProjectile {
	
	private BulletConfiguration config;
	private EntityLivingBase shooter;

	private EntityBulletBase(World world) { super(world); }

	private EntityBulletBase(World world, BulletConfiguration config) {
		super(world);
		this.config = config;
		
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
		
		this.setSize(0.5F, 0.5F);
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
	protected void entityInit() {
		//style
		this.dataWatcher.addObject(16, Byte.valueOf((byte) 0));
	}
	
	@Override
	public void onUpdate() {
		
		super.onUpdate();
		
		if(config == null) {
			this.setDead();
			return;
		}
		
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
