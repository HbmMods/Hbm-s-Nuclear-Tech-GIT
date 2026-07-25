package com.hbm.inventory.fluid.trait;

import java.util.List;

import com.hbm.util.i18n.I18nUtil;

import net.minecraft.util.EnumChatFormatting;

public class FluidTraitSimple {

	public static class FT_Gaseous extends FluidTrait {
		@Override public void addInfoHidden(List<String> info) {
			info.add(EnumChatFormatting.BLUE + "[" + I18nUtil.resolveKey("hbmfluid.trait.gaseous") + "]");
		}
	}

	/** gaseous at room temperature, for cryogenic hydrogen for example */
	public static class FT_Gaseous_ART extends FluidTrait {
		@Override public void addInfoHidden(List<String> info) {
			info.add(EnumChatFormatting.BLUE + "[" + I18nUtil.resolveKey("hbmfluid.trait.gaseousRoom") + "]");
		}
	}

	public static class FT_Liquid extends FluidTrait {
		@Override public void addInfoHidden(List<String> info) {
			info.add(EnumChatFormatting.BLUE + "[" + I18nUtil.resolveKey("hbmfluid.trait.liquid") + "]");
		}
	}

	/** to viscous to be sprayed/turned into a mist */
	public static class FT_Viscous extends FluidTrait {
		@Override public void addInfoHidden(List<String> info) {
			info.add(EnumChatFormatting.BLUE + "[" + I18nUtil.resolveKey("hbmfluid.trait.viscous") + "]");
		}
	}
	@Deprecated public static class FT_Plasma extends FluidTrait {
		@Override public void addInfoHidden(List<String> info) {
			info.add(EnumChatFormatting.LIGHT_PURPLE + "[Plasma]");
		}
	}

	public static class FT_Amat extends FluidTrait {
		@Override public void addInfo(List<String> info) {
			info.add(EnumChatFormatting.DARK_RED + "[" + I18nUtil.resolveKey("hbmfluid.trait.antimatter") + "]");
		}
	}

	public static class FT_LeadContainer extends FluidTrait {
		@Override public void addInfo(List<String> info) {
			info.add(EnumChatFormatting.DARK_RED + "[" + I18nUtil.resolveKey("hbmfluid.trait.leadContainer") + "]");
		}
	}

	public static class FT_Delicious extends FluidTrait {
		@Override public void addInfoHidden(List<String> info) {
			info.add(EnumChatFormatting.DARK_GREEN + "[" + I18nUtil.resolveKey("hbmfluid.trait.delicious") + "]");
		}
	}

	public static class FT_Unsiphonable extends FluidTrait {
		@Override public void addInfoHidden(List<String> info) {
			info.add(EnumChatFormatting.BLUE + "[" + I18nUtil.resolveKey("hbmfluid.trait.unsiphonable") + "]");
		}
	}

	public static class FT_NoID extends FluidTrait { }
	public static class FT_NoContainer extends FluidTrait { }
}
