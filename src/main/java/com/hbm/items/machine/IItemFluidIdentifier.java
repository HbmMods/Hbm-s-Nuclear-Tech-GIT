package com.hbm.items.machine;

import com.hbm.inventory.fluid.FluidType;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IItemFluidIdentifier {

	/**
	 * World might be null if the ID is used inside of a GUI
	 * Position only has to be accurate when used in-world
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param stack
	 * @return
	 */
	public FluidType getType(World world, int x, int y, int z, ItemStack stack);
}
