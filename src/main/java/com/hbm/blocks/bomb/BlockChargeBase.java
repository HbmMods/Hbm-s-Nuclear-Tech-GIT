package com.hbm.blocks.bomb;

import static net.minecraftforge.common.util.ForgeDirection.DOWN;
import static net.minecraftforge.common.util.ForgeDirection.EAST;
import static net.minecraftforge.common.util.ForgeDirection.NORTH;
import static net.minecraftforge.common.util.ForgeDirection.SOUTH;
import static net.minecraftforge.common.util.ForgeDirection.UP;
import static net.minecraftforge.common.util.ForgeDirection.WEST;

import com.hbm.blocks.BlockBase;
import com.hbm.interfaces.IBomb;

import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class BlockChargeBase extends BlockBase implements IBomb {
	
	public BlockChargeBase() {
		super(Material.tnt);
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float fX, float fY, float fZ, int meta) {
		return side;
	}
	
	@Override
	public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side) {
		ForgeDirection dir = ForgeDirection.getOrientation(side);
		return	(dir == DOWN && world.isSideSolid(x, y + 1, z, DOWN)) ||
				(dir == UP && world.isSideSolid(x, y - 1, z, UP)) ||
				(dir == NORTH && world.isSideSolid(x, y, z + 1, NORTH)) ||
				(dir == SOUTH && world.isSideSolid(x, y, z - 1, SOUTH)) ||
				(dir == WEST && world.isSideSolid(x + 1, y, z, WEST)) ||
				(dir == EAST && world.isSideSolid(x - 1, y, z, EAST));
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return null;
	}
}
