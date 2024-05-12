package com.hbm.tileentity.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachineICF;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityICFStruct extends TileEntity {
	
	@Override
	public void updateEntity() {
		if(worldObj.isRemote) return;
		if(worldObj.getTotalWorldTime() % 20 != 0) return;

		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata());
		
		for(int i = -8; i <= 8; i++) {
			
			if(!cbarp(ModBlocks.icf_component, 0, 1, 0, i, dir)) return;
			if(i != 0) if(!cbarp(ModBlocks.icf_component, 0, 0, 0, i, dir)) return;
			if(!cbarp(ModBlocks.icf_component, 0, -1, 0, i, dir)) return;
			if(!cbarp(ModBlocks.icf_component, 2, 0, 3, i, dir)) return;

			for(int j = -1; j <= 1; j++) if(!cbarp(ModBlocks.icf_component, Math.abs(i) <= 2 ? 2 : 4, j, 1, i, dir)) return;
			for(int j = -1; j <= 1; j++) if(!cbarp(ModBlocks.icf_component, Math.abs(i) <= 2 ? 2 : 4, j, 2, i, dir)) return;
			for(int j = -1; j <= 1; j++) if(j != 0) if(!cbarp(ModBlocks.icf_component, Math.abs(i) <= 2 ? 2 : 4, j, 3, i, dir)) return;
			for(int j = -1; j <= 1; j++) if(!cbarp(ModBlocks.icf_component, Math.abs(i) <= 2 ? 2 : 4, j, 4, i, dir)) return;
			for(int j = -1; j <= 1; j++) if(!cbarp(ModBlocks.icf_component, Math.abs(i) <= 2 ? 2 : 4, j, 5, i, dir)) return;
		}
		
		BlockDummyable.safeRem = true;
		worldObj.setBlock(xCoord, yCoord, zCoord, ModBlocks.icf, this.getBlockMetadata() + BlockDummyable.offset, 3);
		((MachineICF) ModBlocks.icf).fillSpace(worldObj, xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ, dir, -((MachineICF) ModBlocks.icf).getOffset());
		BlockDummyable.safeRem = false;
	}
	
	/** check block at relative position */
	public boolean cbarp(Block block, int meta, int x, int y, int z, ForgeDirection dir) {
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		int ix = xCoord + dir.offsetX * z + rot.offsetX * z;
		int iy = yCoord + y;
		int iz = zCoord + dir.offsetZ * x + rot.offsetZ * x;
		
		return worldObj.getBlock(ix, iy, iz) == block && worldObj.getBlockMetadata(ix, iy, iz) == meta;
	}
}
