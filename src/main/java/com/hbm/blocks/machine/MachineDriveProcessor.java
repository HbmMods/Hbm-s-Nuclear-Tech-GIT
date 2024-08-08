package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineDriveProcessor;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachineDriveProcessor extends BlockDummyable {
	
	public MachineDriveProcessor(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineDriveProcessor();
		return new TileEntityProxyCombo(true, true, false); // no need for extra atm, it's just two blocks
	}

	@Override
	public int[] getDimensions() {
		return new int[] {0, 0, 0, 0, 0, 1};
	}

	@Override
	public int getOffset() {
		return 0;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return this.standardOpenBehavior(world, x, y, z, player, 0);
	}
	
}
