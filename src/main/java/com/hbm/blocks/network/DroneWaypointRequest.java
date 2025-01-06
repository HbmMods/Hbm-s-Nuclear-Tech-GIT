package com.hbm.blocks.network;

import com.hbm.tileentity.network.TileEntityDroneWaypointRequest;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class DroneWaypointRequest extends BlockContainer {

	public DroneWaypointRequest() {
		super(Material.circuits);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityDroneWaypointRequest();
	}

	@Override
	public int getRenderType() {
		return RadioTorchBase.renderID;
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
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		return true;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 vec0, Vec3 vec1) {

		int meta = world.getBlockMetadata(x, y, z) & 7;
		ForgeDirection dir = ForgeDirection.getOrientation(meta);

		this.setBlockBounds(
				dir.offsetX == 1 ? 0F : 0.375F,
				dir.offsetY == 1 ? 0F : 0.375F,
				dir.offsetZ == 1 ? 0F : 0.375F,
				dir.offsetX == -1 ? 1F : 0.625F,
				dir.offsetY == -1 ? 1F : 0.625F,
				dir.offsetZ == -1 ? 1F : 0.625F
				);

		return super.collisionRayTrace(world, x, y, z, vec0, vec1);
	}

	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float fX, float fY, float fZ, int meta) {
		return side;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {

		int meta = world.getBlockMetadata(x, y, z);
		ForgeDirection dir = ForgeDirection.getOrientation(meta);
		Block b = world.getBlock(x - dir.offsetX, y - dir.offsetY, z - dir.offsetZ);

		if(!b.isSideSolid(world, x - dir.offsetX, y - dir.offsetY, z - dir.offsetZ, dir) && (!b.renderAsNormalBlock() || b.isAir(world, x, y, z))) {
			this.dropBlockAsItem(world, x, y, z, meta, 0);
			world.setBlockToAir(x, y, z);
		}
	}

	@Override
	public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side) {
		if(!super.canPlaceBlockOnSide(world, x, y, z, side)) return false;

		ForgeDirection dir = ForgeDirection.getOrientation(side);
		Block b = world.getBlock(x - dir.offsetX, y - dir.offsetY, z - dir.offsetZ);

		return b.isSideSolid(world, x - dir.offsetX, y - dir.offsetY, z - dir.offsetZ, dir) || (b.renderAsNormalBlock() && !b.isAir(world, x, y, z));
	}
}
