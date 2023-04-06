package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class WatzPump extends BlockDummyable {

	public WatzPump() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityWatzPump();
		
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {1, 0, 0, 0, 0, 0};
	}

	@Override
	public int getOffset() {
		return 0;
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		int meta = world.getBlockMetadata(x, y, z);
		return side == ForgeDirection.UP && meta == 1;
	}
	
	public static class TileEntityWatzPump extends TileEntity {
		@Override public boolean canUpdate() { return false; }
		@Override @SideOnly(Side.CLIENT) public double getMaxRenderDistanceSquared() { return 65536.0D; }
		AxisAlignedBB bb = null;
		@Override public AxisAlignedBB getRenderBoundingBox() {
			if(bb == null) bb = AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord, zCoord - 1, xCoord + 2, yCoord + 2, zCoord + 2);
			return bb;
		}
	}
}
