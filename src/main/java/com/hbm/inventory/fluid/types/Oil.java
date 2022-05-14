package com.hbm.inventory.fluid.types;

import com.hbm.render.util.EnumSymbol;

public class Oil extends FluidTypeFlammable {

	public Oil(String compat, int color, int p, int f, int r, EnumSymbol symbol) {
		super(compat, color, p, f, r, symbol);
		this.addTraits(FluidTrait.LIQUID, FluidTrait.PETROCHEMICAL);
	}
}
