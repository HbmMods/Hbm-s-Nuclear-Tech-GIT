package com.hbm.blocks.machine.fusion;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.fusion.TileEntityFusionPlasmaForge;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineFusionPlasmaForge extends BlockDummyable {

	public MachineFusionPlasmaForge() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityFusionPlasmaForge();
		if(meta >= 6) return new TileEntityProxyCombo().inventory().power().fluid();
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return this.standardOpenBehavior(world, x, y, z, player, 0);
	}

	@Override public int[] getDimensions() { return new int[] { 2, 0, 2, 2, 5, 5 }; }
	@Override public int getOffset() { return 5; }
	
	@Override
	public int[][] getAllDimensions() {
		return new int[][] {
			new int[] { 2, 0, 2, 2, 5, 5 },
			new int[] { 4, -3, 0, 0, 4, 4 },
			new int[] { 2, 0, 3, -2, 4, 4 },
			new int[] { 2, 0, -2, 3, 4, 4 },
			new int[] { 2, 0, 4, -3, 3, 3 },
			new int[] { 2, 0, -3, 4, 3, 3 },
			new int[] { 2, 0, 5, -4, 2, 2 },
			new int[] { 2, 0, -4, 5, 2, 2 },
			new int[] { 3, -2, 1, 1, 5, 5 }
		};
	}

	@Override
	public double[][] getAABBExtras() {
		return new double[][] {
			{1.5, 3.5, 1, -1, 5.5, 5.5},
			{1.5, 3.5, 1, -1, -5.5, -5.5}
		};
	}

	@Override
	public int[][] getAllPorts(ForgeDirection dir) {
		return rotatePorts(new int[][] {
			{-2, 0, 5, ForgeDirection.SOUTH.ordinal()},
			{-1, 0, 5, ForgeDirection.SOUTH.ordinal()},
			{0, 0, 5, ForgeDirection.SOUTH.ordinal()},
			{1, 0, 5, ForgeDirection.SOUTH.ordinal()},
			{2, 0, 5, ForgeDirection.SOUTH.ordinal()},
			{-2, 0, -5, ForgeDirection.NORTH.ordinal()},
			{-1, 0, -5, ForgeDirection.NORTH.ordinal()},
			{0, 0, -5, ForgeDirection.NORTH.ordinal()},
			{1, 0, -5, ForgeDirection.NORTH.ordinal()},
			{2, 0, -5, ForgeDirection.NORTH.ordinal()}
		}, dir);
	}
	@Override
	public boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {
		return super.checkRequirement(world, x, y, z, dir, o) &&
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {2, 0, 3, -2, 4, 4}, x, y, z, dir) &&
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {2, 0, -2, 3, 4, 4}, x, y, z, dir) &&
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {2, 0, 4, -3, 3, 3}, x, y, z, dir) &&
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {2, 0, -3, 4, 3, 3}, x, y, z, dir) &&
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {2, 0, 5, -4, 2, 2}, x, y, z, dir) &&
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {2, 0, -4, 5, 2, 2}, x, y, z, dir) &&
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {3, -2, 1, 1, 5, 5}, x, y, z, dir) &&
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {4, -3, 0, 0, 4, 4}, x, y, z, dir);
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {2, 0, 3, -2, 4, 4}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {2, 0, -2, 3, 4, 4}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {2, 0, 4, -3, 3, 3}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {2, 0, -3, 4, 3, 3}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {2, 0, 5, -4, 2, 2}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {2, 0, -4, 5, 2, 2}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {3, -2, 1, 1, 5, 5}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {4, -3, 0, 0, 4, 4}, this, dir);
		
		for(int[] offset : getAllPorts(dir)) {
			this.makeExtra(world, x + offset[0], y + offset[1], z + offset[2]);
		}
	}
}
