package com.hbm.entity.projectile;

import com.hbm.entity.particle.EntityOilSpillFX;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityOilSpill extends EntityThrowable {

	public EntityOilSpill(World p_i1773_1_) {
		super(p_i1773_1_);
	}

	public EntityOilSpill(World p_i1774_1_, EntityLivingBase p_i1774_2_) {
		super(p_i1774_1_, p_i1774_2_);
	}

	@Override
	public void entityInit() { }

	public EntityOilSpill(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_) {
		super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if(!worldObj.isRemote) {
			worldObj.spawnEntityInWorld(new EntityOilSpillFX(worldObj, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0));
			if(this.isBurning()) {
				this.setDead();
				worldObj.createExplosion(null, posX, posY, posZ, 1.5F, true);
			}
		}
	}

	@Override
	protected void onImpact(MovingObjectPosition p_70184_1_) {
		if(this.ticksExisted > 5) {
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
