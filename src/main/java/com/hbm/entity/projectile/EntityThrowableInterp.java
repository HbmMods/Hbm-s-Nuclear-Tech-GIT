package com.hbm.entity.projectile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class EntityThrowableInterp extends EntityThrowableNT {
	
	private int turnProgress;
	private double syncPosX;
	private double syncPosY;
	private double syncPosZ;
	private double syncYaw;
	private double syncPitch;
	@SideOnly(Side.CLIENT)
	private double velocityX;
	@SideOnly(Side.CLIENT)
	private double velocityY;
	@SideOnly(Side.CLIENT)
	private double velocityZ;

	public EntityThrowableInterp(World world) {
		super(world);
	}

	public EntityThrowableInterp(World world, double x, double y, double z) {
		super(world, x, y, z);
	}
	
	@Override
	public void onUpdate() {
		
		if(!worldObj.isRemote) {
			int orientation = this.dataWatcher.getWatchableObjectInt(10);
			if(orientation >= 6 && !this.inGround) {
				this.dataWatcher.updateObject(10, orientation - 6);
			}
			super.onUpdate();
		} else {
			if(this.turnProgress > 0) {
				double interpX = this.posX + (this.syncPosX - this.posX) / (double) this.turnProgress;
				double interpY = this.posY + (this.syncPosY - this.posY) / (double) this.turnProgress;
				double interpZ = this.posZ + (this.syncPosZ - this.posZ) / (double) this.turnProgress;
				double d = MathHelper.wrapAngleTo180_double(this.syncYaw - (double) this.rotationYaw);
				this.rotationYaw = (float) ((double) this.rotationYaw + d / (double) this.turnProgress);
				this.rotationPitch = (float)((double)this.rotationPitch + (this.syncPitch - (double)this.rotationPitch) / (double)this.turnProgress);
				--this.turnProgress;
				this.setPosition(interpX, interpY, interpZ);
			} else {
				this.setPosition(this.posX, this.posY, this.posZ);
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void setVelocity(double velX, double velY, double velZ) {
		this.velocityX = this.motionX = velX;
		this.velocityY = this.motionY = velY;
		this.velocityZ = this.motionZ = velZ;
	}
	
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int theNumberThree) {
		this.syncPosX = x;
		this.syncPosY = y;
		this.syncPosZ = z;
		this.syncYaw = yaw;
		this.syncPitch = pitch;
		this.turnProgress = theNumberThree + approachNum();
		this.motionX = this.velocityX;
		this.motionY = this.velocityY;
		this.motionZ = this.velocityZ;
	}
	
	/**
	 * @return a number added to the basic "3" of the approach progress value. Larger numbers make the approach smoother, but lagging behind the true value more.
	 */
	public int approachNum() {
		return 0;
	}
}
