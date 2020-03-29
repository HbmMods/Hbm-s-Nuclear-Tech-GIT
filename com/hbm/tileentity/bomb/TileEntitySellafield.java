package com.hbm.tileentity.bomb;

import java.util.List;

import com.hbm.lib.ModDamageSource;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntitySellafield extends TileEntity {
		
	public double radius = 7.5D;
	
	@Override
	public void updateEntity() {
		
		List list = worldObj.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(xCoord + 0.5D - radius, yCoord + 0.5D - radius, zCoord + 0.5D - radius, xCoord + 0.5D + radius, yCoord + 0.5D + radius, zCoord + 0.5D + radius));
		
		for(Object o : list) {
			
			if(o instanceof EntityLivingBase) {
				
				EntityLivingBase entity = (EntityLivingBase) o;
				
				if(Math.sqrt(Math.pow(xCoord + 0.5D - entity.posX, 2) + Math.pow(yCoord + 0.5D - entity.posY, 2) + Math.pow(zCoord + 0.5D - entity.posZ, 2)) <= radius) {
					//Library.applyRadiation(entity, 5 * 60, 100, 4 * 60, 75);
					entity.attackEntityFrom(ModDamageSource.radiation, entity.getHealth() * 0.5F);
				}
			}
		}
	}

	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		radius = nbt.getDouble("radius");
	}

	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setDouble("radius", radius);
	}

}
