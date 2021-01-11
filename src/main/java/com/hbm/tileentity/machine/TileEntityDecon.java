package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.extprop.HbmLivingProps;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityDecon extends TileEntity {

	@Override
	public void updateEntity() {

		if(!this.worldObj.isRemote) {

			List<EntityLivingBase> entities = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(this.xCoord - 0.5, this.yCoord, this.zCoord - 0.5, this.xCoord + 1.5, this.yCoord + 2, this.zCoord + 1.5));

			if(!entities.isEmpty()) {
				for(EntityLivingBase e : entities) {
					HbmLivingProps.incrementRadiation(e, -0.5F);
				}
			}
		}
	}

}
