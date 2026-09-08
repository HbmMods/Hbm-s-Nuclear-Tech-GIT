package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityLaunchpadLambda;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LaunchpadLambda extends BlockDummyable {

	public LaunchpadLambda() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityLaunchpadLambda();
		if(meta >= 6) return new TileEntityProxyCombo().inventory().power().fluid();
		return null;
	}

	@Override
	public int[][] getAllDimensions() {
		return new int[][] {
			new int[] {1, 0, 7, 7, 7, 7},
			new int[] {2, -2, 7, -6, 7, 7},
			new int[] {2, -2, -6, 7, 7, 7},
			new int[] {2, -2, 7, 7, 7, -6},
			new int[] {2, -2, 7, 7, -6, 7},
		};
	}

	@Override public int[] getDimensions() { return new int[] {1, 0, 7, 7, 7, 7}; }
	@Override public int getOffset() { return 7; }
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return this.standardOpenBehavior(world, x, y, z, player, 0);
	}

	@Override
	protected boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {
		if(!super.checkRequirement(world, x, y, z, dir, o)) return false;
		
		x += dir.offsetX * o;
		z += dir.offsetZ * o;

		if(!MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] {2, -2, 7, -6, 7, 7}, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] {2, -2, -6, 7, 7, 7}, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] {2, -2, 7, 7, 7, -6}, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] {2, -2, 7, 7, -6, 7}, x, y, z, dir)) return false;
		
		return true;
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		
		x += dir.offsetX * o;
		z += dir.offsetZ * o;

		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {2, -2, 7, -6, 7, 7}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {2, -2, -6, 7, 7, 7}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {2, -2, 7, 7, 7, -6}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {2, -2, 7, 7, -6, 7}, this, dir);
	}
}
