package com.hbm.util;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.render.util.EnumSymbol;

import net.minecraft.util.ResourceLocation;

public class CompatFluidRegistry {

	/** Registers a fluid with a custom ID. */
	public static FluidType registerFluid(String name, int id, int color, int p, int f, int r, EnumSymbol symbol, ResourceLocation texture) {
		FluidType type = new FluidType(name, id, color, p, f, r, symbol, texture);
		Fluids.metaOrder.add(type);
		return type;
	}
}
