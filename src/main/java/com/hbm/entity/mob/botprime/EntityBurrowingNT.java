package com.hbm.entity.mob.botprime;

import net.minecraft.entity.EntityCreature;
import net.minecraft.world.World;

public abstract class EntityBurrowingNT extends EntityCreature {

	protected float dragInAir;
	protected float dragInGround;

	public EntityBurrowingNT(World world) {
		super(world);
	}

	@Override
	protected void fall(float dist) {
	}

	@Override
	public float getEyeHeight() {
		return this.height * 0.5F;
	}

	public boolean getIsHead() {
		return false;
	}

	@Override
	protected void updateFallState(double distFallen, boolean onGround) {
	}

	@Override
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

	@Override
	public boolean isOnLadder() {
		return false;
	}
}