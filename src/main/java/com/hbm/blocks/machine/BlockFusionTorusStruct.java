package com.hbm.blocks.machine;

import com.hbm.tileentity.machine.TileEntityFusionTorusStruct;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockFusionTorusStruct extends BlockContainer {

	public BlockFusionTorusStruct(Material mat) { super(mat); }

	@Override public TileEntity createNewTileEntity(World world, int meta) { return new TileEntityFusionTorusStruct(); }
	@Override public boolean isOpaqueCube() { return false; }
}
