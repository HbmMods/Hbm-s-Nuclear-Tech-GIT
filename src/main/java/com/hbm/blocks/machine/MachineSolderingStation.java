package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineSolderingStation;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineSolderingStation extends BlockDummyable {

	public MachineSolderingStation(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineSolderingStation();
		return new TileEntityProxyCombo().inventory().power().fluid();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return this.standardOpenBehavior(world, x, y, z, player, 0);
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
	public int[] getDimensions() {
		return new int[] {0, 0, 1, 0, 1, 0};
	}

	@Override
	public int getOffset() {
		return 0;
	}
}
