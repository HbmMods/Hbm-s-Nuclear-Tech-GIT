package com.hbm.inventory.fluid.types;

import com.hbm.render.util.EnumSymbol;

public class FlammableGas extends FluidTypeFlammable {

	public FlammableGas(String compat, int color, int p, int f, int r, EnumSymbol symbol) {
		super(compat, color, p, f, r, symbol);
		this.addTraits(FluidTrait.GASEOUS);
	}
}
