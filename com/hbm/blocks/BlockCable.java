package com.hbm.blocks;

import com.hbm.tileentity.TileEntityCable;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCable extends BlockContainer {

	protected BlockCable(Material p_i45386_1_) {
		super(p_i45386_1_);
		float p = 1F/16F;
		this.setBlockBounds(11 * p / 2, 11 * p / 2, 11 * p / 2, 1 - 11 * p / 2, 1 - 11 * p / 2, 1 - 11 * p / 2);
		this.useNeighborBrightness = true;
	}
	
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		TileEntityCable cable = (TileEntityCable)world.getTileEntity(x, y, z);

		if(cable != null)
		{
			float p = 1F/16F;
			float minX = 11 * p / 2 - (cable.connections[5] != null ? (11 * p / 2) : 0);
			float minY = 11 * p / 2 - (cable.connections[1] != null ? (11 * p / 2) : 0);
			float minZ = 11 * p / 2 - (cable.connections[2] != null ? (11 * p / 2) : 0);
			float maxX = 1 - 11 * p / 2 + (cable.connections[3] != null ? (11 * p / 2) : 0);
			float maxY = 1 - 11 * p / 2 + (cable.connections[0] != null ? (11 * p / 2) : 0);
			float maxZ = 1 - 11 * p / 2 + (cable.connections[4] != null ? (11 * p / 2) : 0);
			
			this.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
		}
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}
	
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		TileEntityCable cable = (TileEntityCable)world.getTileEntity(x, y, z);

		if(cable != null)
		{
			float p = 1F/16F;
			float minX = 11 * p / 2 - (cable.connections[5] != null ? (11 * p / 2) : 0);
			float minY = 11 * p / 2 - (cable.connections[1] != null ? (11 * p / 2) : 0);
			float minZ = 11 * p / 2 - (cable.connections[2] != null ? (11 * p / 2) : 0);
			float maxX = 1 - 11 * p / 2 + (cable.connections[3] != null ? (11 * p / 2) : 0);
			float maxY = 1 - 11 * p / 2 + (cable.connections[0] != null ? (11 * p / 2) : 0);
			float maxZ = 1 - 11 * p / 2 + (cable.connections[4] != null ? (11 * p / 2) : 0);
			
			this.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityCable();
	}
	
	@Override
	public int getRenderType(){
		return -1;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

}
