package com.hbm.blocks.machine;

import com.hbm.tileentity.machine.TileEntityPlasmaStruct;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockPlasmaStruct extends BlockMachineBase {

	public BlockPlasmaStruct(Material mat) {
		super(mat, 0);
		this.rotatable = true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityPlasmaStruct();
	}
	
    public boolean isOpaqueCube() {
    	
        return false;
    }

}
