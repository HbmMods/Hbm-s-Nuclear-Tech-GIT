package com.hbm.blocks.network;

import com.hbm.inventory.fluid.FluidType;
import net.minecraft.world.World;

public interface IBlockFluidDuct {

	public void changeTypeRecursively(World world, int x, int y, int z, FluidType prevType, FluidType type, int loopsRemaining);
}
