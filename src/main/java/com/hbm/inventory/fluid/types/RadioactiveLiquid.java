package com.hbm.inventory.fluid.types;

import com.hbm.render.util.EnumSymbol;

public class RadioactiveLiquid extends RadioactiveFluid {

	public RadioactiveLiquid(String name, int color, int p, int f, int r, EnumSymbol symbol) {
		super(name, color, p, f, r, symbol);
		this.addTraits(FluidTrait.LIQUID);
	}
}
