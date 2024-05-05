package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.entity.mob.EntityCyberCrab;
import com.hbm.entity.mob.EntityTeslaCrab;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityCyberCrab extends TileEntity {
	
	int age = 0;

	@Override
	public void updateEntity() {
		
		if (!this.worldObj.isRemote) {
			
			age++;
			if(age > 200 && worldObj.getBlock(xCoord, yCoord + 1, zCoord) == Blocks.air && worldObj.getClosestPlayer(xCoord + 0.5, yCoord + 1, zCoord + 0.5, 25) != null) {
				List<Entity> entities = this.worldObj.getEntitiesWithinAABB(EntityCyberCrab.class,
						AxisAlignedBB.getBoundingBox(this.xCoord - 5, this.yCoord - 2, this.zCoord - 5, this.xCoord + 6,
								this.yCoord + 4, this.zCoord + 6));
				
				if(entities.size() < 5) {
					
					EntityCyberCrab crab;
					
					if(worldObj.rand.nextInt(5) == 0)
						crab = new EntityTeslaCrab(worldObj);
					else
						crab = new EntityCyberCrab(worldObj);
					
					crab.setPosition(this.xCoord + 0.5, this.yCoord + 1, this.zCoord + 0.5);
					worldObj.spawnEntityInWorld(crab);
				}
				
				age = 0;
			}
		}
	}

}
