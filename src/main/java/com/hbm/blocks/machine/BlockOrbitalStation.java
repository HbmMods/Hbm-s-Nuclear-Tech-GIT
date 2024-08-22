package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.atmosphere.IBlockSealable;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityOrbitalStation;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockOrbitalStation extends BlockDummyable implements IBlockSealable {

	public BlockOrbitalStation(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityOrbitalStation();
		if(meta >= 6) return new TileEntityProxyCombo(true, false, false);
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {0, 0, 0, 0, 0, 0};
	}

	@Override
	public int getOffset() {
		return 0;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(player.isSneaking())
			return false;

		if(world.isRemote) {
			return true;
		} else {
			int[] pos = this.findCore(world, x, y, z);

			if(pos == null)
				return false;

			TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);

			if(!(te instanceof TileEntityOrbitalStation))
				return false;

			((TileEntityOrbitalStation)te).toggleOrbiting();
			
			return true;
		}
	}

	@Override
	public boolean isSealed(World world, int x, int y, int z) {
		return true;
	}
	
}
