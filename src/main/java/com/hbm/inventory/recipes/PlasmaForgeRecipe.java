package com.hbm.inventory.recipes;

import java.util.ArrayList;
import java.util.List;

import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.util.BobMathUtil;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.util.EnumChatFormatting;

public class PlasmaForgeRecipe extends GenericRecipe {
	
	// minimum plasma energy to perform the recipe
	public long ignitionTemp;

	public PlasmaForgeRecipe(String name) { super(name); }

	public PlasmaForgeRecipe setInputEnergy(long ignitionTemp) { this.ignitionTemp = ignitionTemp; return this; }

	public List<String> print() {
		List<String> list = new ArrayList();
		list.add(EnumChatFormatting.YELLOW + this.getLocalizedName());

		duration(list);
		power(list);
		list.add(EnumChatFormatting.LIGHT_PURPLE + I18nUtil.resolveKey("gui.recipe.fusionIn") + ": " + BobMathUtil.getShortNumber(ignitionTemp) + "TU/t");
		input(list);
		output(list);

		return list;
	}
}
