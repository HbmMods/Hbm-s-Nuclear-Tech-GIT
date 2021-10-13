package com.hbm.entity.qic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntitySPV extends Entity {

	public EntitySPV(World p_i1582_1_) {
		super(p_i1582_1_);
		this.setSize(0.5F, 0.5F);
	}

	@Override
	protected void entityInit() { }

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) { }

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) { }

	@Override
	public void onUpdate() {
		
		if(this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase && ((EntityLivingBase)this.riddenByEntity).moveForward != 0) {
			EntityLivingBase riding = (EntityLivingBase) this.riddenByEntity;
			Vec3 vec = riding.getLookVec();
			this.motionX = vec.xCoord * riding.moveForward * 0.25D;
			this.motionY = vec.yCoord * riding.moveForward * 0.25D;
			this.motionZ = vec.zCoord * riding.moveForward * 0.25D;
			
		} else if(this.riddenByEntity == null) {
			this.motionY -= 0.01D;
			
			if(this.onGround) {
				this.motionX = 0;
				this.motionY = 0;
				this.motionZ = 0;
			}
			
		} else {
			this.motionX = 0;
			this.motionY = 0;
			this.motionZ = 0;
		}
		
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		//this.setPositionAndRotation(this.posX + motionX, this.posY + motionY, this.posZ + motionZ, this.rotationYaw, this.rotationPitch);
		
		super.onUpdate();
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	public boolean interactFirst(EntityPlayer player) {
		if(super.interactFirst(player)) {
			return true;
		} else if(!this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == player)) {
			player.mountEntity(this);
			return true;
		} else {
			return false;
		}
	}
}
