package com.hbm.entity.mob.botprime;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.world.World;

public abstract class EntityBurrowingNT extends EntityCreature {

	protected float dragInAir;
	protected float dragInGround;

	public EntityBurrowingNT(World world) {
		super(world);
	}

	protected void fall(float dist) {
	}

	public float getEyeHeight() {
		return this.height * 0.5F;
	}

	public boolean getIsHead() {
		return false;
	}
<<<<<<< HEAD
	
	protected void updateFallState(double distFallen, boolean onGround) { }
	
    public void moveEntityWithHeading(float strafe, float forward) {
    	
    	float drag = this.dragInGround;
    	
		if((!isEntityInsideOpaqueBlock()) && (!isInWater()) && (!handleLavaMovement())) {
			drag = this.dragInAir;
		} else if(this.getRNG().nextInt(100) == 0) {
			//Block b = worldObj.getBlock((int)Math.floor(posX), (int)Math.floor(posY), (int)Math.floor(posZ));
			//this.playSound(b.stepSound.getStepResourcePath(), 5F, 1F);
		}
        
        if (!getIsHead()) {
        	drag *= 0.9F;
        }
        
        moveFlying(strafe, forward, 0.02F);
        
        moveEntity(this.motionX, this.motionY, this.motionZ);
        
        this.motionX *= drag;
        this.motionY *= drag;
        this.motionZ *= drag;
    }

    public boolean isOnLadder() {
    	return false;
    }
=======

	protected void updateFallState(double distFallen, boolean onGround) {
	}

	public void moveEntityWithHeading(float strafe, float forward) {

		float drag = this.dragInGround;

		if(!isEntityInsideOpaqueBlock() && !isInWater() && !handleLavaMovement()) {
			drag = this.dragInAir;
		}

		if(!getIsHead()) {
			drag *= 0.9F;
		}

		moveFlying(strafe, forward, 0.02F);

		moveEntity(this.motionX, this.motionY, this.motionZ);

		this.motionX *= drag;
		this.motionY *= drag;
		this.motionZ *= drag;
	}

	public boolean isOnLadder() {
		return false;
	}
>>>>>>> master
}
