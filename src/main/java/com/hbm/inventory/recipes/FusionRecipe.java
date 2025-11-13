package com.hbm.inventory.recipes;

import java.util.ArrayList;
import java.util.List;

import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.util.BobMathUtil;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.util.EnumChatFormatting;

public class FusionRecipe extends GenericRecipe {
	
	// minimum klystron energy to ignite the plasma
	public long ignitionTemp;
	// plasma output energy at full blast
	public long outputTemp;

	public FusionRecipe(String name) { super(name); }

	public FusionRecipe setInputEnergy(long ignitionTemp) { this.ignitionTemp = ignitionTemp; return this; }
	public FusionRecipe setOutputEnergy(long outputTemp) { this.outputTemp = outputTemp; return this; }

	public List<String> print() {
		List<String> list = new ArrayList();
		list.add(EnumChatFormatting.YELLOW + this.getLocalizedName());

		duration(list);
		power(list);
		list.add(EnumChatFormatting.RED + I18nUtil.resolveKey("gui.recipe.fusionIn") + ": " + BobMathUtil.getShortNumber(ignitionTemp) + "KyU/t");
		list.add(EnumChatFormatting.RED + I18nUtil.resolveKey("gui.recipe.fusionOut") + ": " + BobMathUtil.getShortNumber(outputTemp) + "TU/t");
		input(list);
		output(list);

		return list;
	}
}
