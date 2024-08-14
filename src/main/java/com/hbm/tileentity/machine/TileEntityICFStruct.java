package com.hbm.tileentity.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachineICF;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
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
			for(int j = -2; j <= 2; j++) if(!cbarp(ModBlocks.icf_component, Math.abs(i) <= 2 ? 2 : 4, j, 2, i, dir)) return;
			for(int j = -2; j <= 2; j++) if(j != 0) if(!cbarp(ModBlocks.icf_component, Math.abs(i) <= 2 ? 2 : 4, j, 3, i, dir)) return;
			for(int j = -2; j <= 2; j++) if(!cbarp(ModBlocks.icf_component, Math.abs(i) <= 2 ? 2 : 4, j, 4, i, dir)) return;
			for(int j = -1; j <= 1; j++) if(!cbarp(ModBlocks.icf_component, Math.abs(i) <= 2 ? 2 : 4, j, 5, i, dir)) return;
		}
		
		BlockDummyable.safeRem = true;
		worldObj.setBlock(xCoord, yCoord, zCoord, ModBlocks.icf, this.getBlockMetadata() + BlockDummyable.offset, 3);
		((MachineICF) ModBlocks.icf).fillSpace(worldObj, xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ, dir, -((MachineICF) ModBlocks.icf).getOffset());
		BlockDummyable.safeRem = false;
	}
	
	/** check block at relative position */
	public boolean cbarp(Block block, int meta, int widthwiseOffset, int y, int lengthwiseOffset, ForgeDirection dir) {
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		int ix = xCoord + rot.offsetX * lengthwiseOffset + dir.offsetX * widthwiseOffset;
		int iy = yCoord + y;
		int iz = zCoord + rot.offsetZ * lengthwiseOffset + dir.offsetZ * widthwiseOffset;
		
		return worldObj.getBlock(ix, iy, iz) == block && worldObj.getBlockMetadata(ix, iy, iz) == meta;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
