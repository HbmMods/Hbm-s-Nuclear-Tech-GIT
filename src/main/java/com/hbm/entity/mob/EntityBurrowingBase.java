package com.hbm.entity.mob;

import net.minecraft.entity.EntityCreature;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

/**
 * Base class for "digging" entities, removes things such as fall behavior, enables noClip and implements some basic movement code
 * @author hbm
 */
public abstract class EntityBurrowingBase extends EntityCreature {

	protected float airDrag;
	protected float airDragY;
	protected float groundDrag;
	protected float groundDragY;

	public EntityBurrowingBase(World world) {
		super(world);
		this.noClip = true;
		this.airDrag = 0.995F;
		this.airDragY = 0.997F;
		this.groundDrag = 0.98F;
		this.groundDragY = 0.995F;
	}

	/**
	 * Since most burrowing entities (such as worms, drills and whatnot) rotate along with their movement, center the eye height.
	 * We can override this later on in case we have an "upright" burrowing entity.
	 */
	@Override
	public float getEyeHeight() {
		return this.height * 0.5F;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		
		if(this.isEntityInvulnerable() || source == DamageSource.drown || source == DamageSource.inWall) {
			return false;
		}
		
		return super.attackEntityFrom(source, amount);
	}

	/**
	 * No fall and fallstate functions
	 */
	@Override
	protected void fall(float dist) { }
	@Override
	protected void updateFallState(double distFallen, boolean onGround) { }

	/**
	 * ...and we turn off the default AI routines. We don't care about path-finding anyway.
	 */
	@Override
	protected void updateEntityActionState() { }

	/**
	 * Calls moveFlying to add motion depending on our entity's rotation, then moves. Drag is applied depending on whether it is underground or airborne.
	 * Called in onLivingUpdate TODO: figure out if strafe and forward are set by one of the superclasses or if this part is pointless
	 */
	@Override
	public void moveEntityWithHeading(float strafe, float forward) {

		float drag = this.groundDrag;
		float dragY = this.groundDragY;

		if(!isEntityInsideOpaqueBlock() && !isInWater() && !handleLavaMovement()) {
			drag = this.airDrag;
			dragY = this.airDragY;
		}

		//misnomer, actually just sets the motion, the moving part happens the line after that
		moveFlying(strafe, forward, 0.02F);
		moveEntity(this.motionX, this.motionY, this.motionZ);

		this.motionX *= drag;
		this.motionY *= dragY;
		this.motionZ *= drag;
	}

	/**
	 * Sorry, can't use ladders.
	 */
	@Override
	public boolean isOnLadder() {
		return false;
	}
	
	/**
	 * If the mob supports the movement behavior through air
	 * @return true if the entity can fly and false if it can only move through ground.
	 */
	public boolean canFly() {
		return false;
	}

	/**
	 * Contrary to its name, all I could find about it was that it controlled some rotation behavior. BoT seems to work fine with it, so we'll use it here too.
	 */
	@Override
	protected boolean isAIEnabled() {
		return true;
	}

	/**
	 * Whether the entity can freely dig up and down or if gravity should be applied instead.
	 * Some entities might not be able to course-correct when airborne, such as small non-worm entities.
	 * @return true if vertical movement can be performed and false if gravity should apply.
	 */
	protected boolean canSupportMovement() {
		return isEntityInsideOpaqueBlock() || canFly();
	}
}
