package com.hbm.entity.projectile;

import com.hbm.entity.effect.EntityMist;
import com.hbm.inventory.fluid.FluidType;

import com.hbm.inventory.fluid.Fluids;

import com.hbm.inventory.fluid.trait.FluidTrait;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityFluidMirv extends EntityThrowable {
	public int fluidFill = 0;
	
	public EntityFluidMirv(World world) {
		super(world);
		this.ignoreFrustumCheck = true;
	}

	@Override
	public void onUpdate() {
		this.lastTickPosX = this.prevPosX = posX;
		this.lastTickPosY = this.prevPosY = posY;
		this.lastTickPosZ = this.prevPosZ = posZ;
		if(!worldObj.isRemote) {
			double nextX = posX + motionX;
			double nextY = posY + motionY;
			double nextZ = posZ + motionZ;
			
			Vec3 start = Vec3.createVectorHelper(posX, posY, posZ);
			Vec3 end = Vec3.createVectorHelper(nextX, nextY, nextZ);
			MovingObjectPosition mop = worldObj.func_147447_a(start, end, false, false, false);

			if(mop != null && mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
				this.setPosition(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
				impact();
				return;
			}
			
			if(nextY < 0) {
				this.setDead();
				return;
			}

			this.setPosition(posX + this.motionX, posY + this.motionY, posZ + this.motionZ);

			this.motionX *= 0.99;
			this.motionZ *= 0.99;
			this.motionY -= 0.05D;

			this.rotation();
		}
	}
	
	public void rotation() {
		float f2 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
		this.rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);
		this.rotationPitch = (float) (Math.atan2(motionY, f2) * 180.0D / Math.PI) - 90;
		while(rotationPitch - prevRotationPitch < -180.0F) prevRotationPitch -= 360.0F;
		while(rotationPitch - prevRotationPitch >= 180.0F) prevRotationPitch += 360.0F;
		while(rotationYaw - prevRotationYaw < -180.0F) prevRotationYaw -= 360.0F;
		while(rotationYaw - prevRotationYaw >= 180.0F) prevRotationYaw += 360.0F;
	}
	
	public void impact() {
		FluidType type = getFluid();
		if(type == Fluids.NONE) return;
		worldObj.playSoundEffect(posX, posY, posZ, "random.fizz", 5f, 2.6f + (rand.nextFloat() - rand.nextFloat() * 0.8f));
		float size = 5f + 20f * Math.min(Math.max(fluidFill, 0), 8000) / 8000f;
		
		EntityMist mist = new EntityMist(worldObj);
		mist.setType(type);
		mist.setPosition(posX, posY, posZ);
		mist.setArea(size, size / 2);
		mist.setDuration(200);
		worldObj.spawnEntityInWorld(mist);
		FluidTrait.onRelease(worldObj,(int) posX,(int) posY,(int) posZ, type, null, FluidTrait.FluidReleaseType.SPILL, fluidFill);
		this.setDead();
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(12, 0);
	}
	@Override
	protected void onImpact(MovingObjectPosition p_70184_1_) {}
	
	public void setFluid(FluidType type, int fill) {
		this.dataWatcher.updateObject(12, type.getID());
		this.fluidFill = fill;
	}
	
	public FluidType getFluid() {
		return Fluids.fromID(this.dataWatcher.getWatchableObjectInt(12));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("fluid", this.dataWatcher.getWatchableObjectInt(12));
		nbt.setInteger("fill", fluidFill);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		this.dataWatcher.updateObject(12, nbt.getInteger("fluid"));
		fluidFill = nbt.getInteger("fill");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance)
	{
		return distance < 25000;
	}
}
