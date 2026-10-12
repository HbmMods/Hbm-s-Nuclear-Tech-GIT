package com.hbm.blocks.turret;

import com.hbm.blocks.BlockDummyable;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TurretBaseNT extends BlockDummyable {

	public TurretBaseNT(Material mat) {
		super(mat);
	}

	@Override
	public int[] getDimensions() {
		return new int[] { 0, 0, 1, 0, 1, 0 };
	}

	@Override
	public int getOffset() {
		return 0;
	}
	@Override
	public int[][] getAllPorts(ForgeDirection dir) {
		return rotatePorts(new int[][] {
			{1, 0, 0, ForgeDirection.SOUTH.ordinal()},
			{0, 0, 0, ForgeDirection.SOUTH.ordinal()},
			{0, 0, -1, ForgeDirection.NORTH.ordinal()},
			{1, 0, -1, ForgeDirection.NORTH.ordinal()},
			{1, 0, 0, ForgeDirection.EAST.ordinal()},
			{1, 0, -1, ForgeDirection.EAST.ordinal()},
			{0, 0, 0, ForgeDirection.WEST.ordinal()},
			{0, 0, -1, ForgeDirection.WEST.ordinal()}
		}, dir);
	}
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_) {
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return this.standardOpenBehavior(world, x, y, z, player, 0);
	}
}
