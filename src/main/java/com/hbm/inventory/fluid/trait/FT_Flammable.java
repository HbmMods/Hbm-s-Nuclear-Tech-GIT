package com.hbm.inventory.fluid.trait;

public class FT_Flammable extends FluidTrait {
	
	/** How much heat energy (usually translates into HE 1:1) 1000mB hold */
	private long energy;
	
	public FT_Flammable(long energy) {
		this.energy = energy;
	}
	
	public long getHeatEnergy() {
		return this.energy;
	}
}
