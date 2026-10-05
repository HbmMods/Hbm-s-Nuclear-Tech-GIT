package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineChemicalPlant;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineChemicalPlant extends BlockDummyable {

	public MachineChemicalPlant(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineChemicalPlant();
		if(meta >= 6) return new TileEntityProxyCombo().inventory().power().fluid();
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return this.standardOpenBehavior(world, x, y, z, player, 0);
	}

	@Override public int[] getDimensions() { return new int[] {2, 0, 1, 1, 1, 1}; }
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
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		
		for(int[] offset : getAllPorts(dir)) {
			this.makeExtra(world, x + offset[0], y + offset[1], z + offset[2]);
		}
	}
}
