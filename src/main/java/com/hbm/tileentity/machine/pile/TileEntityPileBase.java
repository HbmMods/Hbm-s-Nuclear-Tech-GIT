package com.hbm.tileentity.machine.pile;

import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import api.hbm.block.IPileNeutronReceiver;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

public abstract class TileEntityPileBase extends TileEntity {

	@Override
	public abstract void updateEntity();
	
	protected void castRay(int flux, int range) {
		Random rand = worldObj.rand;
		Vec3 vec = Vec3.createVectorHelper(1, 0, 0);
		vec.rotateAroundZ((float)(rand.nextDouble() * Math.PI * 2D));
		vec.rotateAroundY((float)(rand.nextDouble() * Math.PI * 2D));
		vec.rotateAroundX((float)(rand.nextDouble() * Math.PI * 2D));
		
		int prevX = xCoord;
		int prevY = yCoord;
		int prevZ = zCoord;
		
		for(float i = 1; i <= range; i += 0.5F) {

			int x = (int)Math.floor(xCoord + 0.5 + vec.xCoord * i);
			int y = (int)Math.floor(yCoord + 0.5 + vec.yCoord * i);
			int z = (int)Math.floor(zCoord + 0.5 + vec.zCoord * i);
			
			if(x == prevX && y == prevY && z == prevZ)
				continue;

			prevX = x;
			prevY = y;
			prevZ = z;
			
			/*if(i == range || i == 1) {
				NBTTagCompound data2 = new NBTTagCompound();
				data2.setString("type", "vanillaExt");
				data2.setString("mode", i == range ? "greendust" : 
										i == 1 ? "reddust" : "bluedust");
				data2.setDouble("posX", xCoord + 0.5 + vec.xCoord * i);
				data2.setDouble("posY", yCoord + 0.5 + vec.yCoord * i);
				data2.setDouble("posZ", zCoord + 0.5 + vec.zCoord * i);
				MainRegistry.proxy.effectNT(data2);
			}*/
			
			Block b = worldObj.getBlock(x, y, z);
			
			if(b == ModBlocks.concrete || b == ModBlocks.concrete_smooth || b == ModBlocks.concrete_asbestos || b == ModBlocks.concrete_colored || b == ModBlocks.brick_concrete)
				flux *= 0.25;
			
			if(b == ModBlocks.block_boron)
				return;
			
			int meta = worldObj.getBlockMetadata(x, y, z);
			
			if(b == ModBlocks.block_graphite_rod && (meta & 8) == 0)
				return;
			
			TileEntity te = worldObj.getTileEntity(x, y, z);
			
			if(te instanceof IPileNeutronReceiver) {
				
				//this part throttles neutron efficiency for reactions that are way too close, efficiency reaches 100% after 1.5 meters
				//This entire time, this multiplier has been using the max distance, not the actual one, meaning efficency has always been 100%
				//float mult = Math.min((float)i / 1.5F, 1F);
				//int n = (int)(flux * mult);
				
				IPileNeutronReceiver rec = (IPileNeutronReceiver) te;
				rec.receiveNeutrons(flux);
				
				if(b != ModBlocks.block_graphite_detector || (meta & 8) == 0)
					return;
			}
			
			List<EntityLivingBase> entities = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(x + 0.5, y + 0.5, z + 0.5, x + 0.5, y + 0.5, z + 0.5));
			
			if(entities != null)
				for(EntityLivingBase e : entities) {
					
					ContaminationUtil.contaminate(e, HazardType.RADIATION, ContaminationType.CREATIVE, flux / 4F);
				}
		}
	}
}
