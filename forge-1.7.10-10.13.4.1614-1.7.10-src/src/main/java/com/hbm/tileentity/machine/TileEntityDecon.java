package com.hbm.tileentity.machine;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityDecon extends TileEntity {

	@Override
	public void updateEntity() {
		
		if (!this.worldObj.isRemote) {
			List<Entity> entities = this.worldObj.getEntitiesWithinAABB(Entity.class,
					AxisAlignedBB.getBoundingBox(this.xCoord - 0.5, this.yCoord, this.zCoord - 0.5, this.xCoord + 1.5,
							this.yCoord + 2, this.zCoord + 1.5));
			
			if (!entities.isEmpty()) {
				for (Entity e : entities) {
					
					float f = e.getEntityData().getFloat("hfr_radiation");
					f -= 0.5F;
					
					if(f < 0) f = 0;
					
					e.getEntityData().setFloat("hfr_radiation", f);
				}
			}
		}
	}

}
