package com.hbm.blocks.machine;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockBeamBase extends BlockContainer {

	public BlockBeamBase() {
		super(Material.air);
		setLightLevel(1.0F);
		setLightOpacity(0);
		setHardness(-1);
		setResistance(1_000_000);
		setBlockBounds(0, 0, 0, 0, 0, 0);
	}

	@Override public boolean isOpaqueCube() { return false; }
	@Override public boolean renderAsNormalBlock() { return false; }
	@Override public int getRenderType() { return -1; }

	@Override public Item getItemDropped(int i, Random rand, int j) { return null; }
	@Override public int quantityDropped(Random rand) { return 0; }
	
	@Override public boolean isAir(IBlockAccess world, int x, int y, int z)  { return true; }
	@Override public boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z) { return true; }
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int x, int y, int z) { return null; }

	// This was taken from GregsLighting (cargo cult behaviour)
	// This is a bit screwy, but it's needed so that trees are not prevented from growing
	// near a floodlight beam.
	@Override public boolean isLeaves(IBlockAccess world, int x, int y, int z) { return true; }
}
