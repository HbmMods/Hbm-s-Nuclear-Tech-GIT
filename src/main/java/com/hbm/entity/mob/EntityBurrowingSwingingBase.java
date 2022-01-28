package com.hbm.entity.mob;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

/**
 * The name comes from the "swinging" movement patterns as part of its custom path-finding. Also handles rotation.
 * The end result kind of looks like dolphins jumping out of the water just to dive back in.
 * This class assumes that all implementations are indeed hostile mobs.
 * @author hbm
 *
 */
public abstract class EntityBurrowingSwingingBase extends EntityBurrowingBase {

	protected Entity target = null;

	public EntityBurrowingSwingingBase(World world) {
		super(world);
	}
	
	@Override
	public void onUpdate() {
		
		super.onUpdate();

		double dx = motionX;
		double dy = motionY;
		double dz = motionZ;
		float f3 = MathHelper.sqrt_double(dx * dx + dz * dz);
		this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(dx, dz) * 180.0D / Math.PI);
		this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(dy, f3) * 180.0D / Math.PI);
	}

	@Override
	protected void updateAITasks() {

		this.updateEntityActionState();
		super.updateAITasks();

		swingingMovement();
	}

	@Override
	protected void updateEntityActionState() {
		
		if(!this.worldObj.isRemote && this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL) {
			setDead();
		}
		if((this.target != null) && (this.target.isDead)) {
			this.target = null;
		}
		if(this.posY < -10.0D) {
			this.motionY = 1D;
		} else if(this.posY < 3.0D) {
			this.motionY = 0.3D;
		}
	}
	
	protected void swingingMovement() {
		
	}
}
