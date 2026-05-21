package com.hbm.blocks.network;

import com.hbm.blocks.machine.BlockMachineBase;
import com.hbm.tileentity.network.TileEntityRadioAUTOCAL;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class RadioAUTOCAL extends BlockMachineBase {

	public RadioAUTOCAL() {
		super(Material.iron, 0);
		this.rotatable = true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityRadioAUTOCAL();
	}
	
	@Override public int getRenderType(){ return -1; }
	@Override public boolean isOpaqueCube() { return false; }
	@Override public boolean renderAsNormalBlock() { return false; }
}
