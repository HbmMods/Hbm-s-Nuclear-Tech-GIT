package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineSatDock;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineSatDock extends BlockDummyable {

	public MachineSatDock() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineSatDock();
		return new TileEntityProxyCombo().inventory();
	}

	@Override public int[] getDimensions() { return new int[] {0, 0, 1, 1, 1, 1}; }
	@Override public int getOffset() { return 1; }
	@Override
	public int[][] getAllPorts(ForgeDirection dir) {
		return rotatePorts(new int[][] {
			{-1, 0, -1, ForgeDirection.WEST.ordinal()},
			{-1, 0, -1, ForgeDirection.NORTH.ordinal()},
			{-1, 0, 0, ForgeDirection.WEST.ordinal()},
			{-1, 0, 1, ForgeDirection.WEST.ordinal()},
			{-1, 0, 1, ForgeDirection.SOUTH.ordinal()},
			{0, 0, -1, ForgeDirection.NORTH.ordinal()},
			{0, 0, 1, ForgeDirection.SOUTH.ordinal()},
			{1, 0, -1, ForgeDirection.EAST.ordinal()},
			{1, 0, -1, ForgeDirection.NORTH.ordinal()},
			{1, 0, 0, ForgeDirection.EAST.ordinal()},
			{1, 0, 1, ForgeDirection.EAST.ordinal()},
			{1, 0, 1, ForgeDirection.SOUTH.ordinal()}
		}, dir);
	}
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return standardOpenBehavior(world, x, y, z, player, 0);
	}
}
