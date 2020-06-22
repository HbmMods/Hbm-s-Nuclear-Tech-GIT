package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

public class TileEntityFF extends TileEntity {

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}

	List<Entity> outside = new ArrayList();
	List<Entity> inside = new ArrayList();
	
	@Override
	public void updateEntity() {
		
		//if(!worldObj.isRemote)
			prototype(10F);
	}
	
	public void prototype(float rad) {

		List<Entity> oLegacy = new ArrayList(outside);
		List<Entity> iLegacy = new ArrayList(inside);

		outside.clear();
		inside.clear();
		
		List<Object> list = worldObj.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(xCoord + 0.5 - (rad + 25), yCoord + 0.5 - (rad + 25), zCoord + 0.5 - (rad + 25), xCoord + 0.5 + (rad + 25), yCoord + 0.5 + (rad + 25), zCoord + 0.5 + (rad + 25)));
		
		for(Object o : list) {
			
			if(o instanceof Entity && !(o instanceof EntityPlayer)) {
				Entity entity = (Entity)o;
				
				double dist = Math.sqrt(Math.pow(xCoord + 0.5 - entity.posX, 2) + Math.pow(yCoord + 0.5 - entity.posY, 2) + Math.pow(zCoord + 0.5 - entity.posZ, 2));
				
				boolean out = dist > rad;
				
				//if the entity has not been registered yet
				if(!oLegacy.contains(entity) && !iLegacy.contains(entity)) {
					if(out) {
						outside.add(entity);
					} else {
						inside.add(entity);
					}
					
				//if the entity has been detected before
				} else {
					
					//if the entity has crossed inwards
					if(oLegacy.contains(entity) && !out) {
						Vec3 vec = Vec3.createVectorHelper(xCoord + 0.5 - entity.posX, yCoord + 0.5 - entity.posY, zCoord + 0.5 - entity.posZ);
						vec = vec.normalize();
						
						double mx = -vec.xCoord * (rad + 1);
						double my = -vec.yCoord * (rad + 1);
						double mz = -vec.zCoord * (rad + 1);
						
						entity.setLocationAndAngles(xCoord + 0.5 + mx, yCoord + 0.5 + my, zCoord + 0.5 + mz, 0, 0);
						
						double mo = Math.sqrt(Math.pow(entity.motionX, 2) + Math.pow(entity.motionY, 2) + Math.pow(entity.motionZ, 2));

						entity.motionX = vec.xCoord * -mo;
						entity.motionY = vec.yCoord * -mo;
						entity.motionZ = vec.zCoord * -mo;

						entity.posX -= entity.motionX;
						entity.posY -= entity.motionY;
						entity.posZ -= entity.motionZ;

			    		worldObj.playSoundAtEntity(entity, "hbm:weapon.sparkShoot", 2.5F, 1.0F);
						outside.add(entity);
					} else
					
					//if the entity has crossed outwards
					if(iLegacy.contains(entity) && out) {
						Vec3 vec = Vec3.createVectorHelper(xCoord + 0.5 - entity.posX, yCoord + 0.5 - entity.posY, zCoord + 0.5 - entity.posZ);
						vec = vec.normalize();
						
						double mx = -vec.xCoord * (rad - 1);
						double my = -vec.yCoord * (rad - 1);
						double mz = -vec.zCoord * (rad - 1);

						entity.setLocationAndAngles(xCoord + 0.5 + mx, yCoord + 0.5 + my, zCoord + 0.5 + mz, 0, 0);
						
						double mo = Math.sqrt(Math.pow(entity.motionX, 2) + Math.pow(entity.motionY, 2) + Math.pow(entity.motionZ, 2));

						entity.motionX = vec.xCoord * mo;
						entity.motionY = vec.yCoord * mo;
						entity.motionZ = vec.zCoord * mo;

						entity.posX -= entity.motionX;
						entity.posY -= entity.motionY;
						entity.posZ -= entity.motionZ;

			    		worldObj.playSoundAtEntity(entity, "hbm:weapon.sparkShoot", 2.5F, 1.0F);
						inside.add(entity);
					} else {
						
						if(out) {
							outside.add(entity);
						} else {
							inside.add(entity);
						}
					}
				}
			}
		}
	}
}
