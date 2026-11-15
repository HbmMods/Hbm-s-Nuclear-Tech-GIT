package com.hbm.inventory.machinetraits;
import java.util.List;

import com.hbm.util.i18n.I18nUtil;

import net.minecraft.util.EnumChatFormatting;
public class MachineTraitSimple {

	public static class MT_HeatSource extends MachineTrait {
		@Override public void addInfo(List<String> info) {
			info.add(EnumChatFormatting.RED + "[" + I18nUtil.resolveKey("machine.trait.heatSource") + "]");
		}
	}

	public static class MT_Polluting extends MachineTrait {
		@Override public void addInfo(List<String> info) {
			info.add(EnumChatFormatting.GOLD + "[" + I18nUtil.resolveKey("machine.trait.polluting") + "]");
		}
	}

	public static class MT_Smoke extends MachineTrait {
		@Override public void addInfo(List<String> info) {
			info.add(EnumChatFormatting.GRAY + "[" + I18nUtil.resolveKey("machine.trait.smoke") + "]");
		}
	}

	public static class MT_Heavy extends MachineTrait {
		@Override public void addInfo(List<String> info) {
			info.add(EnumChatFormatting.DARK_BLUE + "[" + I18nUtil.resolveKey("machine.trait.heavy") + "]");
		}
	}
	public static class MT_SuperHeavy extends MachineTrait {
		@Override public void addInfo(List<String> info) {
			info.add(EnumChatFormatting.DARK_BLUE + "[" + I18nUtil.resolveKey("machine.trait.superheavy") + "]");
		}
	}
	public static class MT_LiquidStorage extends MachineTrait {
		@Override public void addInfo(List<String> info) {
			info.add(EnumChatFormatting.BLUE + "[" + I18nUtil.resolveKey("machine.trait.liquidStorage") + "]");
		}
	}
	public static class MT_CustomHidden extends MachineTrait {
		public String content;
		public EnumChatFormatting color;
		public MT_CustomHidden(String content, EnumChatFormatting color) {
			this.content = content;
			this.color = color;
		}
		@Override public void addInfoHidden(List<String> info) {
			info.add(this.color + this.content);
		}
	}
}
