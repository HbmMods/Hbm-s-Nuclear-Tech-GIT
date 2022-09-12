package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.machine.TileEntityCrucible;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachineCrucible extends BlockDummyable {

	public MachineCrucible() {
		super(Material.rock);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= 12)
			return new TileEntityCrucible();
		
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return this.standardOpenBehavior(world, x, y, z, player, 0);
	}

	@Override
	public int[] getDimensions() {
		return new int[] {1, 0, 1, 1, 1, 1};
	}

	@Override
	public int getOffset() {
		return 1;
	}
}
