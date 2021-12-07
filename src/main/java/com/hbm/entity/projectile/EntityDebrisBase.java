package com.hbm.entity.projectile;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ReportedException;
import net.minecraft.world.World;

abstract public class EntityDebrisBase extends Entity {

	public float rot;
	public float lastRot;
	protected boolean hasSizeSet = false;

	public EntityDebrisBase(World world) {
		super(world);
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(20, 0);
		this.rot = this.lastRot = this.rand.nextFloat() * 360;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	abstract public boolean interactFirst(EntityPlayer player);

	abstract protected int getLifetime();

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		this.dataWatcher.updateObject(20, nbt.getInteger("debtype"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("debtype", this.dataWatcher.getWatchableObjectInt(20));
	}

	@Override
	public void moveEntity(double moX, double moY, double moZ) {

		this.worldObj.theProfiler.startSection("move");
		this.ySize *= 0.4F;

		if(this.isInWeb) {
			this.isInWeb = false;
		}

		double initMoX = moX;
		double initMoY = moY;
		double initMoZ = moZ;
		AxisAlignedBB axisalignedbb = this.boundingBox.copy();

		List list = this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.addCoord(moX, moY, moZ));

		for(int i = 0; i < list.size(); ++i) {
			moY = ((AxisAlignedBB) list.get(i)).calculateYOffset(this.boundingBox, moY);
		}

		this.boundingBox.offset(0.0D, moY, 0.0D);

		if(!this.field_70135_K && initMoY != moY) {
			moZ = 0.0D;
			moY = 0.0D;
			moX = 0.0D;
		}

		boolean isGoingDown = this.onGround || initMoY != moY && initMoY < 0.0D;
		int j;

		for(j = 0; j < list.size(); ++j) {
			moX = ((AxisAlignedBB) list.get(j)).calculateXOffset(this.boundingBox, moX);
		}

		this.boundingBox.offset(moX, 0.0D, 0.0D);

		if(!this.field_70135_K && initMoX != moX) {
			moZ = 0.0D;
			moY = 0.0D;
			moX = 0.0D;
		}

		for(j = 0; j < list.size(); ++j) {
			moZ = ((AxisAlignedBB) list.get(j)).calculateZOffset(this.boundingBox, moZ);
		}

		this.boundingBox.offset(0.0D, 0.0D, moZ);

		if(!this.field_70135_K && initMoZ != moZ) {
			moZ = 0.0D;
			moY = 0.0D;
			moX = 0.0D;
		}

		double d10;
		double d11;
		int k;
		double d12;

		if(this.stepHeight > 0.0F && isGoingDown && this.ySize < 0.05F && (initMoX != moX || initMoZ != moZ)) {
			d12 = moX;
			d10 = moY;
			d11 = moZ;
			moX = initMoX;
			moY = (double) this.stepHeight;
			moZ = initMoZ;
			AxisAlignedBB axisalignedbb1 = this.boundingBox.copy();
			this.boundingBox.setBB(axisalignedbb);
			list = this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.addCoord(initMoX, moY, initMoZ));

			for(k = 0; k < list.size(); ++k) {
				moY = ((AxisAlignedBB) list.get(k)).calculateYOffset(this.boundingBox, moY);
			}

			this.boundingBox.offset(0.0D, moY, 0.0D);

			if(!this.field_70135_K && initMoY != moY) {
				moZ = 0.0D;
				moY = 0.0D;
				moX = 0.0D;
			}

			for(k = 0; k < list.size(); ++k) {
				moX = ((AxisAlignedBB) list.get(k)).calculateXOffset(this.boundingBox, moX);
			}

			this.boundingBox.offset(moX, 0.0D, 0.0D);

			if(!this.field_70135_K && initMoX != moX) {
				moZ = 0.0D;
				moY = 0.0D;
				moX = 0.0D;
			}

			for(k = 0; k < list.size(); ++k) {
				moZ = ((AxisAlignedBB) list.get(k)).calculateZOffset(this.boundingBox, moZ);
			}

			this.boundingBox.offset(0.0D, 0.0D, moZ);

			if(!this.field_70135_K && initMoZ != moZ) {
				moZ = 0.0D;
				moY = 0.0D;
				moX = 0.0D;
			}

			if(!this.field_70135_K && initMoY != moY) {
				moZ = 0.0D;
				moY = 0.0D;
				moX = 0.0D;
			} else {
				moY = (double) (-this.stepHeight);

				for(k = 0; k < list.size(); ++k) {
					moY = ((AxisAlignedBB) list.get(k)).calculateYOffset(this.boundingBox, moY);
				}

				this.boundingBox.offset(0.0D, moY, 0.0D);
			}

			if(d12 * d12 + d11 * d11 >= moX * moX + moZ * moZ) {
				moX = d12;
				moY = d10;
				moZ = d11;
				this.boundingBox.setBB(axisalignedbb1);
			}
		}

		this.worldObj.theProfiler.endSection();
		this.worldObj.theProfiler.startSection("rest");
		this.posX = (this.boundingBox.minX + this.boundingBox.maxX) / 2.0D;
		this.posY = this.boundingBox.minY + (double) this.yOffset - (double) this.ySize;
		this.posZ = (this.boundingBox.minZ + this.boundingBox.maxZ) / 2.0D;
		this.isCollidedHorizontally = initMoX != moX || initMoZ != moZ;
		this.isCollidedVertically = initMoY != moY;
		this.onGround = initMoY != moY && initMoY < 0.0D;
		this.isCollided = this.isCollidedHorizontally || this.isCollidedVertically;
		this.updateFallState(moY, this.onGround);

		if(initMoX != moX) {
			//this.motionX = 0.0D;
			this.motionX *= -0.75D;

		}

		if(initMoY != moY) {
			this.motionY = 0.0D;
		}

		if(initMoZ != moZ) {
			//this.motionZ = 0.0D;
			this.motionZ *= -0.75D;
		}

		try {
			this.func_145775_I();
		} catch(Throwable throwable) {
			CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Checking entity block collision");
			CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being checked for collision");
			this.addEntityCrashInfo(crashreportcategory);
			throw new ReportedException(crashreport);
		}

		this.worldObj.theProfiler.endSection();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double dist) {

		int range = 128;
		return dist < range * range;
	}

}