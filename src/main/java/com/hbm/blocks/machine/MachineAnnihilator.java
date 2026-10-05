package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineAnnihilator;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineAnnihilator extends BlockDummyable {

	public MachineAnnihilator() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineAnnihilator();
		if(meta >= 6) return new TileEntityProxyCombo().inventory().fluid();
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return standardOpenBehavior(world, x, y, z, player, side);
	}

	@Override public int[] getDimensions() { return new int[] {2, 0, 4, 4, 1, 1}; }
	@Override public int getOffset() { return 4; }
	@Override
	public int[][] getAllDimensions() {
		return new int[][] {
			new int[] {2, 0, 4, 4, 1, 1},
			new int[] {8, -2, 1, 1, 1, 1, -3, 0, 0}
		};
	}
	@Override
	public int[][] getAllPorts(ForgeDirection dir) {
		return rotatePorts(new int[][] {
			{1, 0, 3, ForgeDirection.EAST.ordinal()},
			{-1, 0, 3, ForgeDirection.WEST.ordinal()},
			{0, 0, 4, ForgeDirection.SOUTH.ordinal()}
		}, dir);
	}
	@Override
	protected boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {
		return super.checkRequirement(world, x, y, z, dir, o) && 
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * (o - 3), y, z + dir.offsetZ * (o - 3), new int[] {8, -2, 1, 1, 1, 1}, x, y, z, dir);
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * (o - 3), y, z + dir.offsetZ * (o - 3), new int[] {8, -2, 1, 1, 1, 1}, this, dir);
		for(int[] offset : getAllPorts(dir)) {
			this.makeExtra(world, x + offset[0], y + offset[1], z + offset[2]);
		}
	}
}
