package com.hbm.entity.projectile;

import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityCoin extends EntityThrowableInterp {

	public EntityCoin(World world) {
		super(world);
		this.setSize(1F, 1F);
		this.yOffset = 0.5F;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
	}

	public void setPosition(double x, double y, double z) {
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		float f = this.width / 2.0F;
		this.boundingBox.setBounds(x - f, y - this.yOffset + this.ySize, z - f, x + f, y - this.yOffset + this.ySize + this.height, z + f);
	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {
		if(mop.typeOfHit == mop.typeOfHit.BLOCK) this.setDead();
	}

	@Override
	protected float getAirDrag() {
		return 1F;
	}

	@Override
	public double getGravityVelocity() {
		return 0.02D;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	public boolean canAttackWithItem() {
		return true;
	}
}
