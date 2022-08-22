package com.hbm.inventory.fluid.trait;

import java.util.List;

import com.hbm.util.BobMathUtil;

import net.minecraft.util.EnumChatFormatting;

public class FT_Flammable extends FluidTrait {
	
	/** How much heat energy (usually translates into HE 1:1) 1000mB hold */
	private long energy;
	
	public FT_Flammable(long energy) {
		this.energy = energy;
	}
	
	public long getHeatEnergy() {
		return this.energy;
	}
	
	@Override
	public void addInfo(List<String> info) {
		super.addInfo(info);
		
		info.add(EnumChatFormatting.YELLOW + "[Flammable]");
		
		if(energy > 0)
			info.add(EnumChatFormatting.YELLOW + "Provides " + EnumChatFormatting.RED + "" + BobMathUtil.getShortNumber(energy) + "TU " + EnumChatFormatting.YELLOW + "per bucket");
	}
}
