package com.hbm.inventory.fluid.trait;

import java.util.List;

import net.minecraft.util.EnumChatFormatting;

public class FT_Corrosive extends FluidTrait {
	
	/* 0-100 */
	private int rating;

	public FT_Corrosive(int rating) {
		this.rating = rating;
	}
	
	public int getRating() {
		return rating;
	}
	
	public boolean isHighlyCorrosive() {
		return rating > 50;
	}
	
	@Override
	public void addInfo(List<String> info) {
		
		if(isHighlyCorrosive())
			info.add(EnumChatFormatting.GOLD + "[Strongly Corrosive]");
		else
			info.add(EnumChatFormatting.YELLOW + "[Corrosive]");
	}
}
