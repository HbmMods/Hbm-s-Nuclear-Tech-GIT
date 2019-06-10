package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;

import net.minecraft.tileentity.TileEntity;

public class TileEntityChlorineSeal extends TileEntity {
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote && worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
			spread(xCoord, yCoord, zCoord, 0);
	}
	
	private void spread(int x, int y, int z, int index) {
		
		if(index > 50)
			return;
		
		if(worldObj.getBlock(x, y, z).isReplaceable(worldObj, x, y, z))
			worldObj.setBlock(x, y, z, ModBlocks.chlorine_gas);
		
		if(worldObj.getBlock(x, y, z) != ModBlocks.chlorine_gas && worldObj.getBlock(x, y, z) != ModBlocks.vent_chlorine_seal)
			return;
		
		switch(worldObj.rand.nextInt(6)) {
		case 0:
			spread(x + 1, y, z, index + 1);
			break;
		case 1:
			spread(x - 1, y, z, index + 1);
			break;
		case 2:
			spread(x, y + 1, z, index + 1);
			break;
		case 3:
			spread(x, y - 1, z, index + 1);
			break;
		case 4:
			spread(x, y, z + 1, index + 1);
			break;
		case 5:
			spread(x, y, z - 1, index + 1);
			break;
		}
	}
}
