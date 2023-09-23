package com.hbm.entity.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public abstract class EntityDroneBase extends Entity {
	
	protected int turnProgress;
	protected double syncPosX;
	protected double syncPosY;
	protected double syncPosZ;
	@SideOnly(Side.CLIENT) protected double velocityX;
	@SideOnly(Side.CLIENT) protected double velocityY;
	@SideOnly(Side.CLIENT) protected double velocityZ;

	public EntityDroneBase(World world) {
		super(world);
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	public boolean canAttackWithItem() {
		return true;
	}

	@Override
	public boolean hitByEntity(Entity attacker) {

		if(attacker instanceof EntityPlayer) {
			this.setDead();
		}
		
		return false;
	}

	@Override
	protected boolean canTriggerWalking() {
		return true;
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(10, new Byte((byte) 0));
	}
	
	/**
	 * 0: Empty<br>
	 * 1: Crate<br>
	 * 2: Barrel<br>
	 */
	public void setAppearance(int style) {
		this.dataWatcher.updateObject(10, (byte) style);
	}
	
	public int getAppearance() {
		return this.dataWatcher.getWatchableObjectByte(10);
	}
	
	@Override
	public void onUpdate() {
		
		if(worldObj.isRemote) {
			if(this.turnProgress > 0) {
				double interpX = this.posX + (this.syncPosX - this.posX) / (double) this.turnProgress;
				double interpY = this.posY + (this.syncPosY - this.posY) / (double) this.turnProgress;
				double interpZ = this.posZ + (this.syncPosZ - this.posZ) / (double) this.turnProgress;
				--this.turnProgress;
				this.setPosition(interpX, interpY, interpZ);
			} else {
				this.setPosition(this.posX, this.posY, this.posZ);
			}

			worldObj.spawnParticle("smoke", posX + 1.125, posY + 0.75, posZ, 0, -0.2, 0);
			worldObj.spawnParticle("smoke", posX - 1.125, posY + 0.75, posZ, 0, -0.2, 0);
			worldObj.spawnParticle("smoke", posX, posY + 0.75, posZ + 1.125, 0, -0.2, 0);
			worldObj.spawnParticle("smoke", posX, posY + 0.75, posZ - 1.125, 0, -0.2, 0);
		}
	}
	
	public double getSpeed() {
		return 0.125D;
	}
	
	@SideOnly(Side.CLIENT)
	public void setVelocity(double motionX, double motionY, double motionZ) {
		this.velocityX = this.motionX = motionX;
		this.velocityY = this.motionY = motionY;
		this.velocityZ = this.motionZ = motionZ;
	}
}
