package com.hbm.entity.projectile;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityChopperMine extends Entity implements IProjectile {
	
	public int timer = 0;
	public Entity shooter;
	public EntityChopperMine(World p_i1582_1_) {
		super(p_i1582_1_);
	}
	
	public EntityChopperMine(World p_i1582_1_, double x, double y, double z, double moX, double moY, double moZ, Entity shooter) {
		super(p_i1582_1_);
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		
		this.motionX = moX;
		this.motionY = moY;
		this.motionZ = moZ;
		
		this.shooter = shooter;
		
		this.setSize(12, 12);
		
		this.isImmuneToFire = true;
	}

	@Override
	public void setThrowableHeading(double p_70186_1_, double p_70186_3_, double p_70186_5_, float p_70186_7_,
			float p_70186_8_) {
		
	}

	@Override
	protected void entityInit() {
		
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		

		Vec3 vec31 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
		Vec3 vec3 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY,
				this.posZ + this.motionZ);
		MovingObjectPosition movingobjectposition = this.worldObj.func_147447_a(vec31, vec3, false, true, false);
		vec31 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
		vec3 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY,
				this.posZ + this.motionZ);

		if (movingobjectposition != null) {
			vec3 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord,
					movingobjectposition.hitVec.zCoord);
		}

		Entity entity = null;
		List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this,
				this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
		double d0 = 0.0D;
		int i;
		float f1;

		for (i = 0; i < list.size(); ++i) {
			Entity entity1 = (Entity) list.get(i);

			if (entity1.canBeCollidedWith() && (entity1 != this.shooter)) {
				f1 = 0.3F;
				AxisAlignedBB axisalignedbb1 = entity1.boundingBox.expand(f1, f1, f1);
				MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec31, vec3);

				if (movingobjectposition1 != null) {
					double d1 = vec31.distanceTo(movingobjectposition1.hitVec);

					if (d1 < d0 || d0 == 0.0D) {
						entity = entity1;
						d0 = d1;
					}
				}
			}
		}

		if (entity != null) {
			movingobjectposition = new MovingObjectPosition(entity);
		}

		if (movingobjectposition != null && movingobjectposition.entityHit != null
				&& movingobjectposition.entityHit instanceof EntityPlayer) {

			worldObj.createExplosion(shooter, this.posX, this.posY, this.posZ, 5F, false);
			this.setDead();
		}
		
		//if(timer % 10 == 0 && timer % 20 != 0)
		//	worldObj.playSoundAtEntity(this, "random.click", 10.0F, 1F);
		//if(timer % 20 == 0)
		//	worldObj.playSoundAtEntity(this, "random.click", 10.0F, 1.5F);
		
		worldObj.playSoundAtEntity(this, "hbm:misc.nullMine", 10.0F, 1F);
		
		if(timer >= 100 || worldObj.getBlock((int)this.posX, (int)this.posY, (int)this.posZ).getMaterial() != Material.air)
		{
			worldObj.createExplosion(shooter, this.posX, this.posY, this.posZ, 5F, false);
			this.setDead();
		}

		if(motionY > -0.85)
			this.motionY -= 0.05;
		
		this.motionX *= 0.9;
		this.motionZ *= 0.9;

		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
		
		timer++;
	}

}
