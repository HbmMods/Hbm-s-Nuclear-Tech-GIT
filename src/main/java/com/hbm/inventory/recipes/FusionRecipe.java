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

public class FusionRecipe extends GenericRecipe {
	
	// minimum klystron energy to ignite the plasma
	public long ignitionTemp;
	// plasma output energy at full blast
	public long outputTemp;
	// neutron output energy at full blast
	public double neutronFlux;
	
	public float r = 1F;
	public float g = 0.2F;
	public float b = 0.6F;

	public FusionRecipe(String name) { super(name); }

	public FusionRecipe setInputEnergy(long ignitionTemp) { this.ignitionTemp = ignitionTemp; return this; }
	public FusionRecipe setOutputEnergy(long outputTemp) { this.outputTemp = outputTemp; return this; }
	public FusionRecipe setOutputFlux(double neutronFlux) { this.neutronFlux = neutronFlux; return this; }
	public FusionRecipe setRGB(float r, float g, float b) { this.r = r; this.g = g; this.b = b; return this; }

	public List<String> print() {
		List<String> list = new ArrayList();
		list.add(EnumChatFormatting.YELLOW + this.getLocalizedName());

		duration(list);
		power(list);
		list.add(EnumChatFormatting.LIGHT_PURPLE + I18nUtil.resolveKey("gui.recipe.fusionIn") + ": " + BobMathUtil.getShortNumber(ignitionTemp) + "KyU/t");
		list.add(EnumChatFormatting.LIGHT_PURPLE + I18nUtil.resolveKey("gui.recipe.fusionOut") + ": " + BobMathUtil.getShortNumber(outputTemp) + "TU/t");
		list.add(EnumChatFormatting.LIGHT_PURPLE + I18nUtil.resolveKey("gui.recipe.fusionFlux") + ": " + ((int)(neutronFlux * 10)) / 10D + " flux/t");
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
			String temp = BobMathUtil.getShortNumber(this.ignitionTemp) + "Ky/t";
			fontRenderer.drawString(temp, side - fontRenderer.getStringWidth(temp), 57, 0xa000a0);
		}
	}
}
