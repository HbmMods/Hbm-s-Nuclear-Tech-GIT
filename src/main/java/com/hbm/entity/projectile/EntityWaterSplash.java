package com.hbm.entity.projectile;

import com.hbm.main.MainRegistry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityWaterSplash extends EntityThrowable {

	public EntityWaterSplash(World p_i1773_1_) {
		super(p_i1773_1_);
	}

	public EntityWaterSplash(World p_i1774_1_, EntityLivingBase p_i1774_2_) {
		super(p_i1774_1_, p_i1774_2_);
	}

	@Override
	public void entityInit() {
	}

	public EntityWaterSplash(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_) {
		super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if(!worldObj.isRemote) {

			if(this.ticksExisted > 200) {
				this.setDead();
			}
		} else {
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "waterSplash");
			data.setDouble("posX", posX);
			data.setDouble("posY", posY);
			data.setDouble("posZ", posZ);
			MainRegistry.proxy.effectNT(data);
		}
	}

	@Override
	protected void onImpact(MovingObjectPosition p_70184_1_) {
		if(this.ticksExisted > 5) {
			worldObj.spawnParticle("splash", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
			this.setDead();
		}
	}

	@Override
	public boolean writeToNBTOptional(NBTTagCompound nbt) {
		return false;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		this.setDead();
	}
}
