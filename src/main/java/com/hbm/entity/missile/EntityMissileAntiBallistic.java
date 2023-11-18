package com.hbm.entity.missile;

import java.util.List;

import com.hbm.entity.particle.EntitySmokeFX;
import com.hbm.explosion.ExplosionLarge;

import api.hbm.entity.IRadarDetectable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityMissileAntiBallistic extends Entity implements IRadarDetectable {

	int activationTimer;

	public EntityMissileAntiBallistic(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	@Override
	public void onUpdate() {

		if(activationTimer < 40) {
			activationTimer++;

			motionY = 1.5D;

			this.setLocationAndAngles(posX + this.motionX, posY + this.motionY, posZ + this.motionZ, 0, 0);
			this.rotation();

			if(!this.worldObj.isRemote && this.posY < 400)
				this.worldObj.spawnEntityInWorld(new EntitySmokeFX(this.worldObj, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0));

		} else {

			if(activationTimer == 40) {
				ExplosionLarge.spawnParticlesRadial(worldObj, posX, posY, posZ, 15);
				activationTimer = 100;
			}

			for(int i = 0; i < 5; i++) {

				targetMissile();

				this.setLocationAndAngles(posX + this.motionX, posY + this.motionY, posZ + this.motionZ, 0, 0);
				this.rotation();

				if(!this.worldObj.isRemote && this.posY < 400)
					this.worldObj.spawnEntityInWorld(new EntitySmokeFX(this.worldObj, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0));

				List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(posX - 5, posY - 5, posZ - 5, posX + 5, posY + 5, posZ + 5));

				for(Entity e : list) {
					if(e instanceof EntityMissileBaseNT || e instanceof EntityMissileCustom) {
						ExplosionLarge.explode(worldObj, posX, posY, posZ, 15F, true, false, true);
						this.setDead();
						return;
					}
				}
			}
		}
		
		if(this.posY > 2000)
			this.setDead();

		if(this.worldObj.getBlock((int) this.posX, (int) this.posY, (int) this.posZ) != Blocks.air && this.worldObj.getBlock((int) this.posX, (int) this.posY, (int) this.posZ) != Blocks.water && this.worldObj.getBlock((int) this.posX, (int) this.posY, (int) this.posZ) != Blocks.flowing_water) {

			if(!this.worldObj.isRemote) {
				ExplosionLarge.explode(worldObj, posX, posY, posZ, 10F, true, true, true);
			}
			this.setDead();
			return;
		}

	}

	protected void rotation() {
		float f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
		this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

		for(this.rotationPitch = (float) (Math.atan2(this.motionY, f2) * 180.0D / Math.PI) - 90; this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
			;
		}

		while(this.rotationPitch - this.prevRotationPitch >= 180.0F) {
			this.prevRotationPitch += 360.0F;
		}

		while(this.rotationYaw - this.prevRotationYaw < -180.0F) {
			this.prevRotationYaw -= 360.0F;
		}

		while(this.rotationYaw - this.prevRotationYaw >= 180.0F) {
			this.prevRotationYaw += 360.0F;
		}
	}

	private void targetMissile() {

		List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(posX - 500, 0, posZ - 500, posX + 500, 5000, posZ + 500));

		Entity target = null;
		double closest = 1000D;

		for(Entity e : list) {
			if(e instanceof EntityMissileBaseNT || e instanceof EntityMissileCustom) {
				double dis = Math.sqrt(Math.pow(e.posX - posX, 2) + Math.pow(e.posY - posY, 2) + Math.pow(e.posZ - posZ, 2));

				if(dis < closest) {
					closest = dis;
					target = e;
				}
			}
		}

		if(target != null) {

			Vec3 vec = Vec3.createVectorHelper(target.posX - posX, target.posY - posY, target.posZ - posZ);

			vec.normalize();

			this.motionX = vec.xCoord * 0.065D;
			this.motionY = vec.yCoord * 0.065D;
			this.motionZ = vec.zCoord * 0.065D;
		}
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
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		return distance < 500000;
	}

	@Override
	public RadarTargetType getTargetType() {
		return RadarTargetType.MISSILE_AB;
	}

}
