package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.machine.TileEntityMachineTrackingRadar;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineTrackingRadar extends BlockDummyable {

	public MachineTrackingRadar() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineTrackingRadar();
		return null;
	}

	@Override public int[] getDimensions() { return new int[] {9, 0, 1, 1, 1, 1}; }
	@Override public int getOffset() { return 1; }
	
	@Override
	public int[][] getAllPorts(ForgeDirection dir) {
		return rotatePorts(new int[][] {
			{1, 0, 1, ForgeDirection.EAST.ordinal()},
			{1, 0, 1, ForgeDirection.SOUTH.ordinal()},
			{1, 0, -1, ForgeDirection.EAST.ordinal()},
			{1, 0, -1, ForgeDirection.NORTH.ordinal()},
			{-1, 0, 1, ForgeDirection.WEST.ordinal()},
			{-1, 0, 1, ForgeDirection.SOUTH.ordinal()},
			{-1, 0, -1, ForgeDirection.WEST.ordinal()},
			{-1, 0, -1, ForgeDirection.NORTH.ordinal()}
		}, dir);
	}
	
	@Override
	protected void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		for(int[] offset : getAllPorts(dir)) {
			this.makeExtra(world, x + offset[0], y + offset[1], z + offset[2]);
		}
	}
}
