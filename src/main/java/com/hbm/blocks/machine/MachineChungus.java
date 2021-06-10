package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityChungus;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachineChungus extends BlockDummyable {

	public MachineChungus(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= 12)
			return new TileEntityChungus();
		
		if(meta >= 6)
			return new TileEntityProxyCombo(false, true, true);
		
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] { 3, 0, 10, 4, 2, 2 };
	}

	@Override
	public int getOffset() {
		return 4;
	}

}
