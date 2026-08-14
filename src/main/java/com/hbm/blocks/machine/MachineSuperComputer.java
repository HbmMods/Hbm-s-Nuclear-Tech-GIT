package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineSuperComputer;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineSuperComputer extends BlockDummyable {

	public MachineSuperComputer(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineSuperComputer();
		if(meta >= 6) return new TileEntityProxyCombo().inventory().power().fluid();
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return this.standardOpenBehavior(world, x, y, z, player, 0);
	}

	@Override public int[] getDimensions() { return new int[] {5, 0, 3, 3, 3, 3}; }
	@Override public int getOffset() { return 8; }

	@Override
	public int[][] getAllDimensions() {
		return new int[][] {
			getDimensions(),
			new int[] {6, -6, 3, 3, 1, 1},
			new int[] {6, -6, 1, 1, 3, 3},
			new int[] {7, -7, 1, 1, 1, 1},
			new int[] {2, 0, -3, 8, 1, 1}
		};
	}

	@Override
	protected boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {
		for(int[] dim : this.getAllDimensions()) if(!MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, dim, x, y, z, dir)) return false;
		return true;
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		x += dir.offsetX * o;
		z += dir.offsetZ * o;

		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {6, -6, 3, 3, 1, 1}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {6, -6, 1, 1, 3, 3}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {7, -7, 1, 1, 1, 1}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {2, 0, -3, 8, 1, 1}, this, dir);
		
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		this.makeExtra(world, x + dir.offsetX * 8, y, z + dir.offsetZ * 8);
		this.makeExtra(world, x + dir.offsetX * 7 + rot.offsetX, y, z + dir.offsetZ * 7 + rot.offsetZ);
		this.makeExtra(world, x + dir.offsetX * 7 - rot.offsetX, y, z + dir.offsetZ * 7 - rot.offsetZ);
		this.makeExtra(world, x + dir.offsetX * 5 + rot.offsetX, y, z + dir.offsetZ * 5 + rot.offsetZ);
		this.makeExtra(world, x + dir.offsetX * 5 - rot.offsetX, y, z + dir.offsetZ * 5 - rot.offsetZ);
		
	}
}
