package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.entity.mob.EntityCyberCrab;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityCyberCrab extends TileEntity {
	
	int age = 0;

	@Override
	public void updateEntity() {
		
		if (!this.worldObj.isRemote) {
			
			age++;
			if(age > 200) {
				List<Entity> entities = this.worldObj.getEntitiesWithinAABB(EntityCyberCrab.class,
						AxisAlignedBB.getBoundingBox(this.xCoord - 5, this.yCoord - 2, this.zCoord - 5, this.xCoord + 6,
								this.yCoord + 4, this.zCoord + 6));
				
				if(entities.size() < 5) {
					EntityCyberCrab crab = new EntityCyberCrab(worldObj);
					crab.setPosition(this.xCoord + 0.5, this.yCoord + 1, this.zCoord + 0.5);
					worldObj.spawnEntityInWorld(crab);
				}
				
				age = 0;
			}
		}
	}

}
