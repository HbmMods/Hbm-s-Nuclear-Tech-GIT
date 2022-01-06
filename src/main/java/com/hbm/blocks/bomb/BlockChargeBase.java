package com.hbm.blocks.bomb;

import static net.minecraftforge.common.util.ForgeDirection.DOWN;
import static net.minecraftforge.common.util.ForgeDirection.EAST;
import static net.minecraftforge.common.util.ForgeDirection.NORTH;
import static net.minecraftforge.common.util.ForgeDirection.SOUTH;
import static net.minecraftforge.common.util.ForgeDirection.UP;
import static net.minecraftforge.common.util.ForgeDirection.WEST;

import com.hbm.blocks.BlockBase;
import com.hbm.interfaces.IBomb;

import api.hbm.block.IToolable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class BlockChargeBase extends BlockBase implements IBomb, IToolable {
	
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
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		
		ForgeDirection dir = ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z));
		
		if(!world.isSideSolid(x - dir.offsetX, y - dir.offsetY, z - dir.offsetZ, dir)) {
			world.setBlockToAir(x, y, z);
			this.explode(world, x, y, z);
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		
		float f = 0.0625F;
		
		switch(world.getBlockMetadata(x, y, z)) {
		case 0: this.setBlockBounds(0.0F, 10 * f, 0.0F, 1.0F, 1.0F, 1.0F); break;
		case 1: this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 6 * f, 1.0F); break;
		case 2: this.setBlockBounds(0.0F, 0.0F, 10 * f, 1.0F, 1.0F, 1.0F); break;
		case 3: this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 6 * f); break;
		case 4: this.setBlockBounds(10 * f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F); break;
		case 5: this.setBlockBounds(0.0F, 0.0F, 0.0F, 6 * f, 1.0F, 1.0F); break;
		}
	}
	

	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {
		
		if(tool != ToolType.DEFUSER)
			return false;
		
		this.dismantle(world, x, y, z);
		return true;
	}
}
