package com.hbm.blocks.machine;

import com.hbm.lib.Library;
import com.hbm.tileentity.machine.TileEntityMachineTapeDrive;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class MachineTapeDrive extends BlockMachineBase {

	public MachineTapeDrive() {
		super(Material.iron, 0);
		this.rotatable = true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityMachineTapeDrive();
	}
	
	@Override public int getRenderType(){ return -1; }
	@Override public boolean isOpaqueCube() { return false; }
	@Override public boolean renderAsNormalBlock() { return false; }

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {

		int meta = world.getBlockMetadata(x, y, z);
		setBlockBounds(0, 0, 0, 1, 1, 1);
		
		if(meta == Library.POS_X.ordinal()) setBlockBounds(0, 0, 0, 0.75F, 1, 1);
		if(meta == Library.POS_Z.ordinal()) setBlockBounds(0, 0, 0, 1, 1, 0.75F);
		if(meta == Library.NEG_X.ordinal()) setBlockBounds(0.25F, 0, 0, 1, 1, 1);
		if(meta == Library.NEG_Z.ordinal()) setBlockBounds(0, 0, 0.25F, 1, 1, 1);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		this.setBlockBoundsBasedOnState(world, x, y, z);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}
}
