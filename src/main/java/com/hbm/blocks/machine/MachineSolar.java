package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineSolarPanel;
import com.hbm.tileentity.machine.TileEntitySolarBoiler;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachineSolar extends BlockDummyable {

	public MachineSolar(Material p_i45394_1_) {
		super(p_i45394_1_);
		// TODO Auto-generated constructor stub
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12)
			return new TileEntityMachineSolarPanel();
		if(meta >= extra)
			return new TileEntityProxyCombo(false, false, true);
		
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {0, 0, 2, 2, 0, 0};
	}

	@Override
	public int getOffset() {
		return 0;
	}

}
