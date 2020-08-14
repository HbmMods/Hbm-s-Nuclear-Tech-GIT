package com.hbm.entity.mob.sodtekhnologiyah;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class WormMovementBody {
	private EntityWormBase user;

	public WormMovementBody(EntityWormBase par1) {
		this.user = par1;
	}

	protected void updateMovement() {
		double var9 = 128.0D;
		if((this.user.targetedEntity != null) && (this.user.targetedEntity.getDistanceSqToEntity(this.user) < var9 * var9)) {
			this.user.waypointX = this.user.targetedEntity.posX;
			this.user.waypointY = this.user.targetedEntity.posY;
			this.user.waypointZ = this.user.targetedEntity.posZ;
		}
		if(((this.user.ticksExisted % 60 == 0) || (this.user.ticksExisted == 1))
				&& ((this.user.targetedEntity == null) || (this.user.followed == null))) {
			findEntityToFollow(this.user.worldObj.selectEntitiesWithinAABB(EntityLiving.class,
					this.user.boundingBox.expand(this.user.rangeForParts, this.user.rangeForParts, this.user.rangeForParts),
					EntityWormBase.wormSelector));
		}
		double var1 = this.user.waypointX - this.user.posX;
		double var3 = this.user.waypointY - this.user.posY;
		double var5 = this.user.waypointZ - this.user.posZ;
		double var7 = var1 * var1 + var3 * var3 + var5 * var5;

		var7 = MathHelper.sqrt_double(var7);
		if(this.user.targetedEntity != null) {
			this.user.faceEntity(this.user.targetedEntity, 180.0F, 180.0F);
		}
		this.user.bodySpeed = Math.max(0.0D, Math.min(var7 - this.user.segmentDistance, this.user.maxBodySpeed));
		if(var7 < this.user.segmentDistance * 0.895D) {
			this.user.motionX *= 0.8D;
			this.user.motionY *= 0.8D;
			this.user.motionZ *= 0.8D;
		} else {
			this.user.motionX = (var1 / var7 * this.user.bodySpeed);
			this.user.motionY = (var3 / var7 * this.user.bodySpeed);
			this.user.motionZ = (var5 / var7 * this.user.bodySpeed);
		}
	}

	protected void findEntityToFollow(List<EntityWormBase> par1List) {
		for(EntityWormBase var3 : par1List) {
			if(var3.getUniqueWormID() == this.user.getUniqueWormID()) {
				if(var3.getIsHead()) {
					if(this.user.getPartID() == 0) {
						this.user.targetedEntity = ((Entity) var3);
					}
					this.user.followed = ((EntityLivingBase) var3);
				} else if(var3.getPartID() == this.user.getPartID() - 1) {
					this.user.targetedEntity = ((Entity) var3);
				}
			}
		}
		this.user.didCheck = true;
	}
}
