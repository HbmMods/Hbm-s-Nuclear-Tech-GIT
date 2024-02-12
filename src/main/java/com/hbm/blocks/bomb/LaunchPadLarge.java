package com.hbm.blocks.bomb;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.bomb.TileEntityLaunchPadLarge;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class LaunchPadLarge extends BlockDummyable {

	public LaunchPadLarge(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityLaunchPadLarge();
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return this.standardOpenBehavior(world, x, y, z, player, 0);
	}

	@Override
	public int[] getDimensions() {
		return new int[] {0, 0, 4, 4, 4, 4};
	}

	@Override
	public int getOffset() {
		return 4;
	}
}
