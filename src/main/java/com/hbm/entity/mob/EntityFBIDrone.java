package com.hbm.entity.mob;

import com.hbm.entity.grenade.EntityGrenadeStrong;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityFBIDrone extends EntityUFOBase {

	private int attackCooldown;

	public EntityFBIDrone(World world) {
		super(world);
	}

	@Override
	protected void updateEntityActionState() {
		super.updateEntityActionState();
		if(this.courseChangeCooldown > 0) this.courseChangeCooldown--;
		if(this.scanCooldown > 0) this.scanCooldown--;

		if(!worldObj.isRemote) {
			
			if(attackCooldown > 0) attackCooldown--;
			
			if(this.target != null && attackCooldown <= 0) {
				
				Vec3 vec = Vec3.createVectorHelper(posX - target.posX, posY - target.posY, posZ - target.posZ);
				if(Math.abs(vec.xCoord) < 5 && Math.abs(vec.zCoord) < 5 && vec.yCoord > 3) {
					attackCooldown = 60;
					EntityGrenadeStrong grenade = new EntityGrenadeStrong(worldObj);
					grenade.setPosition(posX, posY, posZ);
					worldObj.spawnEntityInWorld(grenade);
				}
			}
		}
		
		if(this.courseChangeCooldown > 0) {
			approachPosition(this.target == null ? 0.25D : 0.5D);
		}
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(35.0D);
	}

	@Override
	protected int getScanRange() {
		return 100;
	}

	@Override
	protected int targetHeightOffset() {
		return 7 + rand.nextInt(4);
	}

	@Override
	protected int wanderHeightOffset() {
		return 7 + rand.nextInt(4);
	}
}
