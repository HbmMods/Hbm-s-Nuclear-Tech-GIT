package com.hbm.blocks.test;

import com.hbm.interfaces.Untested;
import com.hbm.tileentity.network.TileEntityCableBaseNT;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Powered by satan energy!
 * @author hbm
 */
@Untested
public class TestConductor extends BlockContainer {

	public TestConductor(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityCableBaseNT();
	}
}
