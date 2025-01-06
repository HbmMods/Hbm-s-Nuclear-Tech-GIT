package com.hbm.inventory.fluid.trait;

import java.util.List;

import net.minecraft.util.EnumChatFormatting;

public class FluidTraitSimple {

	public static class FT_Gaseous extends FluidTrait {
		@Override public void addInfoHidden(List<String> info) {
			info.add(EnumChatFormatting.BLUE + "[Gaseous]");
		}
	}

	/** gaseous at room temperature, for cryogenic hydrogen for example */
	public static class FT_Gaseous_ART extends FluidTrait {
		@Override public void addInfoHidden(List<String> info) {
			info.add(EnumChatFormatting.BLUE + "[Gaseous at Room Temperature]");
		}
	}

	public static class FT_Liquid extends FluidTrait {
		@Override public void addInfoHidden(List<String> info) {
			info.add(EnumChatFormatting.BLUE + "[Liquid]");
		}
	}

	/** to viscous to be sprayed/turned into a mist */
	public static class FT_Viscous extends FluidTrait {
		@Override public void addInfoHidden(List<String> info) {
			info.add(EnumChatFormatting.BLUE + "[Viscous]");
		}
	}

	public static class FT_Plasma extends FluidTrait {
		@Override public void addInfoHidden(List<String> info) {
			info.add(EnumChatFormatting.LIGHT_PURPLE + "[Plasma]");
		}
	}

	public static class FT_Amat extends FluidTrait {
		@Override public void addInfo(List<String> info) {
			info.add(EnumChatFormatting.DARK_RED + "[Antimatter]");
		}
	}

	public static class FT_LeadContainer extends FluidTrait {
		@Override public void addInfo(List<String> info) {
			info.add(EnumChatFormatting.DARK_RED + "[Requires hazardous material tank to hold]");
		}
	}

	public static class FT_Delicious extends FluidTrait {
		@Override public void addInfoHidden(List<String> info) {
			info.add(EnumChatFormatting.DARK_GREEN + "[Delicious]");
		}
	}

	public static class FT_Unsiphonable extends FluidTrait {
		@Override public void addInfoHidden(List<String> info) {
			info.add(EnumChatFormatting.BLUE + "[Ignored by siphon]");
		}
	}

	public static class FT_NoID extends FluidTrait { }
	public static class FT_NoContainer extends FluidTrait { }
}
