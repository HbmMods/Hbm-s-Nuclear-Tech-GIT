package com.hbm.inventory.recipes.loader;

import java.util.Locale;

import com.hbm.inventory.FluidStack;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.recipes.loader.GenericRecipes.ChanceOutput;
import com.hbm.inventory.recipes.loader.GenericRecipes.ChanceOutputMulti;
import com.hbm.inventory.recipes.loader.GenericRecipes.IOutput;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GenericRecipe {
	
	public String name;
	public AStack[] inputItem;
	public FluidStack[] inputFluid;
	public IOutput[] outputItem;
	public FluidStack[] outputFluid;
	public int duration;
	public long power;
	protected ItemStack icon;
	public boolean writeIcon = false;
	public boolean customLocalization = false;
	
	public GenericRecipe(String name) {
		this.name = name;
	}
	
	public GenericRecipe setDuration(int duration) { this.duration = duration; return this; }
	public GenericRecipe setPower(long power) { this.power = power; return this; }
	public GenericRecipe setup(int duration, long power) { return this.setDuration(duration).setPower(power); }
	public GenericRecipe setupNamed(int duration, long power) { return this.setDuration(duration).setPower(power).setNamed(); }
	public GenericRecipe setIcon(ItemStack icon) { this.icon = icon; this.writeIcon = true; return this; }
	public GenericRecipe setIcon(Item item, int meta) { return this.setIcon(new ItemStack(item, 1, meta)); }
	public GenericRecipe setIcon(Item item) { return this.setIcon(new ItemStack(item)); }
	public GenericRecipe setNamed() { this.customLocalization = true; return this; }

	public GenericRecipe setInputItems(AStack... input) { this.inputItem = input; return this; }
	public GenericRecipe setInputFluids(FluidStack... input) { this.inputFluid = input; return this; }
	public GenericRecipe setOutputItems(IOutput... output) { this.outputItem = output; return this; }
	public GenericRecipe setOutputFluids(FluidStack... output) { this.outputFluid = output; return this; }
	
	public ItemStack getIcon() {
		
		if(icon == null) {
			if(outputItem != null) {
				if(outputItem[0] instanceof ChanceOutput) icon = ((ChanceOutput) outputItem[0]).stack.copy();
				if(outputItem[0] instanceof ChanceOutputMulti) icon = ((ChanceOutputMulti) outputItem[0]).pool.get(0).stack.copy();
				return icon;
			}
			if(outputFluid != null) {
				icon = ItemFluidIcon.make(outputFluid[0]);
			}
		}
		
		if(icon == null) icon = new ItemStack(ModItems.nothing);
		return icon;
	}
	
	public String getName() {
		if(customLocalization) return I18nUtil.resolveKey(name);
		return this.getIcon().getDisplayName();
	}
	
	public boolean matchesSearch(String substring) {
		return getName().toLowerCase(Locale.US).contains(substring.toLowerCase(Locale.US));
	}
}
