package com.hbm.tileentity.machine.pile;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import api.hbm.block.IPileNeutronReceiver;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;

public abstract class TileEntityPileBase extends TileEntity {

	@Override
	public void updateEntity() {
		
	}
	
	protected void castRay(int flux, int range) {
		
		Random rand = worldObj.rand;
		Vec3 vec = Vec3.createVectorHelper(1, 0, 0);
		vec.rotateAroundZ((float)(rand.nextDouble() * Math.PI * 2D));
		vec.rotateAroundY((float)(rand.nextDouble() * Math.PI * 2D));
		
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
			
			Block b = worldObj.getBlock(x, y, z);
			
			if(b == ModBlocks.block_boron)
				return;
			
			int meta = worldObj.getBlockMetadata(x, y, z);
			
			if(b == ModBlocks.block_graphite_rod && (meta & 4) == 0)
				return;
			
			TileEntity te = worldObj.getTileEntity(x, y, z);
			
			if(te instanceof IPileNeutronReceiver) {
				IPileNeutronReceiver rec = (IPileNeutronReceiver) te;
				rec.receiveNeutrons(flux);
				return;
			}
		}
	}
}
