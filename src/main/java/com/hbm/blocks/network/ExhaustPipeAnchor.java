package com.hbm.blocks.network;

import java.util.List;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.tileentity.network.TileEntityPipeExhaustAnchor;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ExhaustPipeAnchor extends BasePipeAnchor {

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityPipeExhaustAnchor();
	}

	@Override
	public void addHookText(List<String> text, World world, int x, int y, int z) {
		text.add(Fluids.SMOKE.getLocalizedName());
		text.add(Fluids.SMOKE_LEADED.getLocalizedName());
		text.add(Fluids.SMOKE_POISON.getLocalizedName());
	}
}
