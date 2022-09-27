package com.hbm.tileentity.machine;

import com.hbm.inventory.material.Mats.MaterialStack;

import api.hbm.block.ICrucibleAcceptor;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFoundryOutlet extends TileEntityFoundryBase {

	@Override public boolean canAcceptPartialPour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) { return false; }
	@Override public MaterialStack pour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) { return stack; }

	@Override
	public boolean canAcceptPartialFlow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) {
		return this.standardCheck(world, x, y, z, side, stack);
	}
	
	@Override
	public MaterialStack flow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) {
		return standardAdd(world, x, y, z, side, stack);
	}

	@Override
	public int getCapacity() {
		return 0;
	}
}
