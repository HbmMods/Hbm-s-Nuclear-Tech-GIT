package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineExposureChamber;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineExposureChamber extends BlockDummyable {

	public MachineExposureChamber(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineExposureChamber();
		if(meta >= 6) return new TileEntityProxyCombo().inventory().power();
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {4, 0, 2, 2, 2, 2};
	}

	@Override
	public int getOffset() {
		return 2;
	}
	@Override
	public int[][] getAllDimensions() {
		return new int[][] {
			getDimensions(),
			new int[] {3, 0, 0, 0, -3, 8},
			new int[] {0, 0, 1, -1, -3, 6, 0, 2, 0},
			new int[] {0, 0, -1, 1, -3, 6, 0, 2, 0},
			new int[] {3, 0, 1, -1, 0, 1, 0, 0, -7},
			new int[] {3, 0, -1, 1, 0, 1, 0, 0, -7},
		};
	}
	@Override
	public int[][] getAllPorts(ForgeDirection dir) {
		return rotatePorts(new int[][] {
			{-7, 0, 1, ForgeDirection.SOUTH.ordinal()},
			{-7, 0, -1, ForgeDirection.NORTH.ordinal()},
			{-8, 0, 1, ForgeDirection.SOUTH.ordinal()},
			{-8, 0, -1, ForgeDirection.NORTH.ordinal()},
			{-8, 0, 0, ForgeDirection.WEST.ordinal()}
		}, dir);
	}
	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		int cx = x + dir.offsetX * o;
		int cz = z + dir.offsetZ * o;

		ForgeDirection rot = dir.getRotation(ForgeDirection.UP).getOpposite();

		MultiblockHandlerXR.fillSpace(world, cx, y, cz, new int[] {3, 0, 0, 0, -3, 8}, this, dir);
		MultiblockHandlerXR.fillSpace(world, cx, y + 2, cz, new int[] {0, 0, 1, -1, -3, 6}, this, dir);
		MultiblockHandlerXR.fillSpace(world, cx, y + 2, cz, new int[] {0, 0, -1, 1, -3, 6}, this, dir);
		MultiblockHandlerXR.fillSpace(world, cx + rot.offsetX * 7, y, cz + rot.offsetZ * 7, new int[] {3, 0, 1, -1, 0, 1}, this, dir);
		MultiblockHandlerXR.fillSpace(world, cx + rot.offsetX * 7, y, cz + rot.offsetZ * 7, new int[] {3, 0, -1, 1, 0, 1}, this, dir);

		for(int[] offset : getAllPorts(dir)) {
			this.makeExtra(world, x + offset[0], y + offset[1], z + offset[2]);
		}
	}

	@Override
	protected boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {

		x += dir.offsetX * o;
		z += dir.offsetZ * o;

		ForgeDirection rot = dir.getRotation(ForgeDirection.UP).getOpposite();

		if(!MultiblockHandlerXR.checkSpace(world, x, y, z, getDimensions(), x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] {3, 0, 0, 0, -3, 8}, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] {0, 0, 1, -1, -3, 6}, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] {0, 0, -1, 1, -3, 6}, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x + rot.offsetX * 7, y, z + rot.offsetZ * 7, new int[] {3, 0, 1, -1, 0, 1}, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x + rot.offsetX * 7, y, z + rot.offsetZ * 7, new int[] {3, 0, -1, 1, 0, 1}, x, y, z, dir)) return false;

		return true;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return super.standardOpenBehavior(world, x, y, z, player, 0);
	}
}
