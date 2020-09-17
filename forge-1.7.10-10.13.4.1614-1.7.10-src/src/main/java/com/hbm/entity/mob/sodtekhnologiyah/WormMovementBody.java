package com.hbm.entity.mob.sodtekhnologiyah;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class WormMovementBody {
	private EntityWormBase user;

	public WormMovementBody(EntityWormBase user) {
		this.user = user;
	}

	protected void updateMovement() {
		
		double targetingRange = 128.0D;
		
		if((this.user.targetedEntity != null) && (this.user.targetedEntity.getDistanceSqToEntity(this.user) < targetingRange * targetingRange)) {
			this.user.waypointX = this.user.targetedEntity.posX;
			this.user.waypointY = this.user.targetedEntity.posY;
			this.user.waypointZ = this.user.targetedEntity.posZ;
		}
		
		if(((this.user.ticksExisted % 60 == 0) || (this.user.ticksExisted == 1)) && ((this.user.targetedEntity == null) || (this.user.followed == null))) {
			findEntityToFollow(this.user.worldObj.selectEntitiesWithinAABB(EntityLiving.class, this.user.boundingBox.expand(this.user.rangeForParts, this.user.rangeForParts, this.user.rangeForParts), EntityWormBase.wormSelector));
		}
		
		double deltaX = this.user.waypointX - this.user.posX;
		double deltaY = this.user.waypointY - this.user.posY;
		double deltaZ = this.user.waypointZ - this.user.posZ;
		double deltaDist = MathHelper.sqrt_double(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);

		if(this.user.targetedEntity != null) {
			this.user.faceEntity(this.user.targetedEntity, 180.0F, 180.0F);
		}
		
		this.user.bodySpeed = Math.max(0.0D, Math.min(deltaDist - this.user.segmentDistance, this.user.maxBodySpeed));
		
		if(deltaDist < this.user.segmentDistance * 0.895D) {
			this.user.motionX *= 0.8D;
			this.user.motionY *= 0.8D;
			this.user.motionZ *= 0.8D;
		} else {
			this.user.motionX = (deltaX / deltaDist * this.user.bodySpeed);
			this.user.motionY = (deltaY / deltaDist * this.user.bodySpeed);
			this.user.motionZ = (deltaZ / deltaDist * this.user.bodySpeed);
		}
	}

	protected void findEntityToFollow(List<EntityWormBase> segments) {
		
		for(EntityWormBase segment : segments) {
			if(segment.getUniqueWormID() == this.user.getUniqueWormID()) {
				if(segment.getIsHead()) {
					if(this.user.getPartID() == 0) {
						this.user.targetedEntity = ((Entity) segment);
					}
					this.user.followed = ((EntityLivingBase) segment);
					
				} else if(segment.getPartID() == this.user.getPartID() - 1) {
					this.user.targetedEntity = ((Entity) segment);
				}
			}
		}
		this.user.didCheck = true;
	}
}
