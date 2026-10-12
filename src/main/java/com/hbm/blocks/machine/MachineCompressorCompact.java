package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineCompressorCompact;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineCompressorCompact extends BlockDummyable {

	public MachineCompressorCompact() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int meta) {
		if(meta >= 12) return new TileEntityMachineCompressorCompact();
		if(meta >= 6) return new TileEntityProxyCombo().power().fluid();
		return null;
	}

	@Override public int[] getDimensions() { return new int[] {2, 0, 1, 1, 3, 3}; }
	@Override public int getOffset() { return 1; }
	@Override
	public int[][] getAllPorts(ForgeDirection dir) {
		return rotatePorts(new int[][] {
			{3, 1, 0, ForgeDirection.EAST.ordinal()},
			{-3, 1, 0, ForgeDirection.WEST.ordinal()},
			{1, 1, 1, ForgeDirection.SOUTH.ordinal()},
			{-1, 1, 1, ForgeDirection.SOUTH.ordinal()},
			{1, 1, -1, ForgeDirection.NORTH.ordinal()},
			{-1, 1, -1, ForgeDirection.NORTH.ordinal()}
		}, dir);
	}
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return this.standardOpenBehavior(world, x, y, z, player, 0);
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		for(int[] offset : getAllPorts(dir)) {
			this.makeExtra(world, x + offset[0], y + offset[1], z + offset[2]);
		}
	}
}
