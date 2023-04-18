package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.gas.BlockGasAir;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

public class TileEntityAirPump extends TileEntity {
	private static final int START_CONCENTRATION_VALUE = 15;
	private int cooldownTicks = 0;
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote && worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
			spread(xCoord, yCoord, zCoord, 0);
	}
	
	/*private void releaseAir(final int xOffset, final int yOffset, final int zOffset) {
		final Block block = worldObj.getBlock(xCoord + xOffset, yCoord + yOffset, zCoord + zOffset);
		if (block.isAir(worldObj, xCoord + xOffset, yCoord + yOffset, zCoord + zOffset)) {// can be air
			//final int energy_cost = (!block.isAssociatedBlock(WarpDrive.blockAir)) ? WarpDriveConfig.BREATHING_ENERGY_PER_NEW_AIR_BLOCK[0] : WarpDriveConfig.BREATHING_ENERGY_PER_EXISTING_AIR_BLOCK[0];
			//if (isEnabled && energy_consume(energy_cost, true)) {// enough energy and enabled
				if (worldObj.setBlock(xCoord + xOffset, yCoord + yOffset, zCoord + zOffset, ModBlocks.air_block, START_CONCENTRATION_VALUE, 2)) {
					// (needs to renew air or was not maxed out)
					energy_consume(WarpDriveConfig.BREATHING_ENERGY_PER_NEW_AIR_BLOCK[0], false);
				} else {
					energy_consume(WarpDriveConfig.BREATHING_ENERGY_PER_EXISTING_AIR_BLOCK[0], false);
				}
			} else {// low energy => remove air block
				if (block.isAssociatedBlock(WarpDrive.blockAir)) {
					final int metadata = worldObj.getBlockMetadata(xCoord + xOffset, yCoord + yOffset, zCoord + zOffset);
					if (metadata > 4) {
						worldObj.setBlockMetadataWithNotify(xCoord + xOffset, yCoord + yOffset, zCoord + zOffset, metadata - 4, 2);
					} else if (metadata > 1) {
						worldObj.setBlockMetadataWithNotify(xCoord + xOffset, yCoord + yOffset, zCoord + zOffset, 1, 2);
					} else {
						// worldObj.setBlockMetadataWithNotify(xCoord + xOffset, yCoord + yOffset,  zCoord + zOffset, 0, 0, 2);
					}
				}
			//}
		}
	}*/
	
	
	private void spread(int x, int y, int z, int index) {
		
		if(index > 8)
			return;
		
		if(worldObj.getBlock(x, y, z).isAir(worldObj, x, y, z) || worldObj.getBlock(x, y, z) instanceof BlockGasAir)
			worldObj.setBlock(x, y, z, ModBlocks.air_block9);
		
		if(!(worldObj.getBlock(x, y, z) instanceof BlockGasAir) && worldObj.getBlock(x, y, z) != ModBlocks.air_vent)
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
