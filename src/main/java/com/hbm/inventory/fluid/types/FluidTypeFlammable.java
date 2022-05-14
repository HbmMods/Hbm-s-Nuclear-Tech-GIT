package com.hbm.inventory.fluid.types;

import java.util.List;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.render.util.EnumSymbol;
import com.hbm.util.BobMathUtil;

import net.minecraft.util.EnumChatFormatting;

/** If it burns, it needs to be an instance of this class. */
public class FluidTypeFlammable extends FluidType {
	
	/** How much heat energy (usually translates into HE 1:1) 1000mB hold */
	protected long energy;
	
	public FluidTypeFlammable(String compat, int color, int p, int f, int r, EnumSymbol symbol) {
		super(compat, color, p, f, r, symbol);
	}
	
	public FluidTypeFlammable setHeatEnergy(long energy) {
		this.energy = energy;
		return this;
	}
	
	public long getHeatEnergy() {
		return this.energy;
	}

	@Override
	public void addInfo(List<String> info) {
		super.addInfo(info);
		
		info.add(EnumChatFormatting.YELLOW + "[Flammable]");
		
		if(energy > 0)
			info.add(EnumChatFormatting.YELLOW + "Provides " + EnumChatFormatting.RED + "" + BobMathUtil.getShortNumber(energy) + "HE " + EnumChatFormatting.YELLOW + "per bucket");
	}
}
