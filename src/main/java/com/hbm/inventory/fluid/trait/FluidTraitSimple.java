package com.hbm.inventory.fluid.trait;

import java.util.List;

import com.hbm.util.I18nUtil;
import net.minecraft.util.EnumChatFormatting;

public class FluidTraitSimple {

	public static class FT_Gaseous extends FluidTrait {
		@Override public void addInfoHidden(List<String> info) {
			info.add(EnumChatFormatting.BLUE + I18nUtil.resolveKey("hbmfluid.TraitSimple.Gaseous"));
		}
	}

	/** gaseous at room temperature, for cryogenic hydrogen for example */
	public static class FT_Gaseous_ART extends FluidTrait {
		@Override public void addInfoHidden(List<String> info) {
			info.add(EnumChatFormatting.BLUE + I18nUtil.resolveKey("hbmfluid.TraitSimple.Gaseous_ART"));
		}
	}

	public static class FT_Liquid extends FluidTrait {
		@Override public void addInfoHidden(List<String> info) {
			info.add(EnumChatFormatting.BLUE + I18nUtil.resolveKey("hbmfluid.TraitSimple.Liquid"));
		}
	}

	/** to viscous to be sprayed/turned into a mist */
	public static class FT_Viscous extends FluidTrait {
		@Override public void addInfoHidden(List<String> info) {
			info.add(EnumChatFormatting.BLUE + I18nUtil.resolveKey("hbmfluid.TraitSimple.Viscous"));
		}
	}

	public static class FT_Plasma extends FluidTrait {
		@Override public void addInfoHidden(List<String> info) {
			info.add(EnumChatFormatting.LIGHT_PURPLE + I18nUtil.resolveKey("hbmfluid.TraitSimple.Plasma"));
		}
	}

	public static class FT_Amat extends FluidTrait {
		@Override public void addInfo(List<String> info) {
			info.add(EnumChatFormatting.DARK_RED + I18nUtil.resolveKey("hbmfluid.TraitSimple.Amat"));
		}
	}

	public static class FT_LeadContainer extends FluidTrait {
		@Override public void addInfo(List<String> info) {
			info.add(EnumChatFormatting.DARK_RED + I18nUtil.resolveKey("hbmfluid.TraitSimple.LeadContainer"));
		}
	}
	
	public static class FT_Delicious extends FluidTrait {
		@Override public void addInfoHidden(List<String> info) {
			info.add(EnumChatFormatting.DARK_GREEN + I18nUtil.resolveKey("hbmfluid.TraitSimple.Delicious"));
		}
	}
	
	public static class FT_Leaded extends FluidTrait {
		@Override public void addInfoHidden(List<String> info) {
			info.add(EnumChatFormatting.BLUE + I18nUtil.resolveKey("hbmfluid.TraitSimple.Leaded"));
		}
	}

	public static class FT_NoID extends FluidTrait { }
	public static class FT_NoContainer extends FluidTrait { }
}
