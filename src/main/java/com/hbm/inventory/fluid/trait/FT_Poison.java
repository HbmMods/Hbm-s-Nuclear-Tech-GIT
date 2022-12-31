package com.hbm.inventory.fluid.trait;

import java.util.List;

import net.minecraft.util.EnumChatFormatting;

public class FT_Poison extends FluidTrait {

	protected boolean withering = false;
	protected int level = 0;
	
	public FT_Poison(boolean withering, int level) {
		this.withering = withering;
		this.level = level;
	}
	
	public boolean isWithering() {
		return this.withering;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	@Override
	public void addInfoHidden(List<String> info) {
		info.add(EnumChatFormatting.GREEN + "[Toxic Fumes]");
	}
}
