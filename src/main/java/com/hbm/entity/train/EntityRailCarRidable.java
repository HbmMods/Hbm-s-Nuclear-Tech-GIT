package com.hbm.entity.train;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class EntityRailCarRidable extends EntityRailCarBase {
	
	public EntityRailCarRidable(World world) {
		super(world);
	}

	@Override
	public boolean interactFirst(EntityPlayer player) {
		if(this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != player) {
			return true;
		} else {
			if(!this.worldObj.isRemote) {
				player.mountEntity(this);
			}
			return true;
		}
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
	}

	@Override
	public void updateRiderPosition() {
		
		Vec3 offset = getRiderSeatPosition();
		offset.rotateAroundY((float) (-this.rotationYaw * Math.PI / 180));
		
		if(this.riddenByEntity != null) {
			this.riddenByEntity.setPosition(this.posX + offset.xCoord, this.posY + offset.yCoord, this.posZ + offset.zCoord);
		}
	}

	/** Returns a Vec3 showing the relative position from the driver to the core */
	public abstract Vec3 getRiderSeatPosition();
}
