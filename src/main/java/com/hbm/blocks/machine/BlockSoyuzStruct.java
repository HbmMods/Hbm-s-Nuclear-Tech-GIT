package com.hbm.blocks.machine;

import com.hbm.tileentity.machine.TileEntitySoyuzStruct;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockSoyuzStruct extends BlockContainer {

	public BlockSoyuzStruct(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntitySoyuzStruct();
	}
	
    public boolean isOpaqueCube() {
    	
        return false;
    }
}
