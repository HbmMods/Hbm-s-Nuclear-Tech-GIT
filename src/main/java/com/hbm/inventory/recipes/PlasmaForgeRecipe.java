package com.hbm.inventory.recipes;

import java.util.ArrayList;
import java.util.List;

import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.util.BobMathUtil;
import com.hbm.util.Clock;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.EnumChatFormatting;

public class PlasmaForgeRecipe extends GenericRecipe {
	
	// minimum plasma energy to perform the recipe
	public long ignitionTemp;

	public PlasmaForgeRecipe(String name) { super(name); }

	public PlasmaForgeRecipe setInputEnergy(long ignitionTemp) { this.ignitionTemp = ignitionTemp; return this; }

	public List<String> print() {
		List<String> list = new ArrayList();

		header(list);
		autoSwitch(list);
		duration(list);
		power(list);
		list.add(EnumChatFormatting.LIGHT_PURPLE + I18nUtil.resolveKey("gui.recipe.plasmaIn") + ": " + BobMathUtil.getShortNumber(ignitionTemp) + "TU/t");
		input(list);
		output(list);

		return list;
	}
	
	@Override
	public void printNEIExtras() {

		int side = 164;
		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
		
		String duration = BobMathUtil.getShortNumber(this.duration) + " ticks";
		fontRenderer.drawString(duration, side - fontRenderer.getStringWidth(duration), 45, 0x404040);
		
		if(Clock.get_ms() % 2000 < 1000) {
			String consumption = BobMathUtil.getShortNumber(this.power) + "HE/t";
			fontRenderer.drawString(consumption, side - fontRenderer.getStringWidth(consumption), 57, 0x404040);
		} else {
			String temp = BobMathUtil.getShortNumber(this.ignitionTemp) + "TU/t";
			fontRenderer.drawString(temp, side - fontRenderer.getStringWidth(temp), 57, 0xa000a0);
		}
	}
}
