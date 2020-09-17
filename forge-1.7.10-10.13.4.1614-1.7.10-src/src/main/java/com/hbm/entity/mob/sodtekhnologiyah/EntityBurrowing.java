package com.hbm.entity.mob.sodtekhnologiyah;

import net.minecraft.entity.EntityCreature;
import net.minecraft.world.World;

public abstract class EntityBurrowing extends EntityCreature {
	
	protected float dragInAir;
	protected float dragInGround;
	  
	public EntityBurrowing(World world) {
		super(world);
	}
	
	/*
	 * No need to update this
	 */
	protected void fall(float dist) { }
	
	/*
	 * Our "eye"-height should always be centered
	 */
	public float getEyeHeight() {
		return this.height * 0.5F;
	}
	
	public boolean getIsHead() {
		return false;
	}
	
	/*
	 * No fall damage :P
	 */
	protected void updateFallState(double distFallen, boolean onGround) { }
	
    public void moveEntityWithHeading(float strafe, float forward) {
    	
    	float drag = this.dragInGround;
        if ((!isEntityInsideOpaqueBlock()) && (!isInWater()) && (!handleLavaMovement()))
        {
        	drag = this.dragInAir;
        }
        else
        {
          this.distanceWalkedModified = ((float)(this.distanceWalkedModified + Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ) * 0.4D));
          this.distanceWalkedOnStepModified = ((float)(this.distanceWalkedOnStepModified + Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ + this.motionY * this.motionY) * 0.4D));
          /*if (this.distanceWalkedOnStepModified > this.nextStepDistance)
          {
            //this.nextStepDistance = ((int)this.distanceWalkedOnStepModified + 1);
            if (isInWater())
            {
              float var39 = (float) (Math.sqrt(this.motionX * this.motionX * 0.2D + this.motionY * this.motionY + this.motionZ * this.motionZ * 0.2) * 0.35F);
              if (var39 > 1.0F) {
                var39 = 1.0F;
              }
              playSound("liquid.swim", var39, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
            }
            if (getIsHead()) {
            	playSound("alexmod.destroyer.dig", getSoundVolume(), 1.0F);
            }
          }*/
        }
        if (!getIsHead()) {
        	drag *= 0.9F;
        }
        moveFlying(strafe, forward, 0.02F);
        
        moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= drag;
        this.motionY *= drag;
        this.motionZ *= drag;
        
        this.prevLimbSwingAmount = this.limbSwingAmount;
        double deltaX = this.posX - this.prevPosX;
        double deltaZ = this.posZ - this.prevPosZ;
        float dist = (float) Math.sqrt(deltaX * deltaX + deltaZ * deltaZ) * 4.0F;
        if (dist > 1.0F) {
          dist = 1.0F;
        }
        this.limbSwingAmount += (dist - this.limbSwingAmount) * 0.4F;
        this.limbSwing += this.limbSwingAmount;
    }

    public boolean isOnLadder() {
    	return false;
    }

}
