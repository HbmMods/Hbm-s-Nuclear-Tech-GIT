package com.hbm.inventory.fluid;

import java.util.List;

import com.hbm.inventory.fluid.FluidType.FluidTrait;
import com.hbm.render.util.EnumSymbol;

import net.minecraft.util.EnumChatFormatting;

/** If it burns, it needs to be an instance of this class. */
public class FluidTypeFlammable extends FluidType {
	
	/** How much heat energy (usually translates into HE 1:1) 1000mB hold */
	protected double energy;
	
	public FluidTypeFlammable(String compat, int color, int p, int f, int r, EnumSymbol symbol, String name) {
		this(compat, color, p, f, r, symbol, name, 0, new FluidTrait[0]);
	}
	
	public FluidTypeFlammable(String compat, int color, int p, int f, int r, EnumSymbol symbol, String name, FluidTrait... traits) {
		this(compat, color, p, f, r, symbol, name, 0, traits);
	}
	
	public FluidTypeFlammable(String compat, int color, int p, int f, int r, EnumSymbol symbol, String name, int temperature) {
		this(compat, color, p, f, r, symbol, name, temperature, new FluidTrait[0]);
	}
	
	public FluidTypeFlammable(String compat, int color, int p, int f, int r, EnumSymbol symbol, String name, int temperature, FluidTrait... traits) {
		super(compat, color, p, f, r, symbol, name, temperature, traits);
	}
	
	public FluidTypeFlammable setHeatEnergy(double energy) {
		this.energy = energy;
		return this;
	}

	@Override
	public void addInfo(List<String> info) {
		super.addInfo(info);
		
		if(energy > 0)
			info.add(EnumChatFormatting.YELLOW + "Provides " + EnumChatFormatting.RED + "" + ((int) energy) + "HE " + EnumChatFormatting.YELLOW + "per bucket burned");
	}
}
