package com.hbm.inventory.recipes.loader;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.hbm.inventory.FluidStack;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.recipes.loader.GenericRecipes.ChanceOutput;
import com.hbm.inventory.recipes.loader.GenericRecipes.ChanceOutputMulti;
import com.hbm.inventory.recipes.loader.GenericRecipes.IOutput;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.util.BobMathUtil;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class GenericRecipe {
	
	protected final String name;
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
	public GenericRecipe setIcon(Block block) { return this.setIcon(new ItemStack(block)); }
	public GenericRecipe setNamed() { this.customLocalization = true; return this; }

	public GenericRecipe inputItems(AStack... input) { this.inputItem = input; return this; }
	public GenericRecipe inputFluids(FluidStack... input) { this.inputFluid = input; return this; }
	public GenericRecipe outputItems(IOutput... output) { this.outputItem = output; return this; }
	public GenericRecipe outputFluids(FluidStack... output) { this.outputFluid = output; return this; }
	
	public GenericRecipe outputItems(ItemStack... output) {
		this.outputItem = new IOutput[output.length];
		for(int i = 0; i < outputItem.length; i++) this.outputItem[i] = new ChanceOutput(output[i]);
		return this;
	}
	
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
	
	public String getInternalName() {
		return this.name;
	}
	
	public String getLocalizedName() {
		if(customLocalization) return I18nUtil.resolveKey(name);
		return this.getIcon().getDisplayName();
	}
	
	public List<String> print() {
		List<String> list = new ArrayList();
		list.add(EnumChatFormatting.YELLOW + this.getLocalizedName());
		if(duration > 0) list.add(EnumChatFormatting.RED + "Duration: " + this.duration / 20D + "s");
		if(power > 0) list.add(EnumChatFormatting.RED + "Consumption: " + BobMathUtil.getShortNumber(power) + "HE/t");
		list.add(EnumChatFormatting.BOLD + "Input:");
		if(inputItem != null) for(AStack stack : inputItem) {
			ItemStack display = stack.extractForCyclingDisplay(20);
			list.add("  " + EnumChatFormatting.GRAY + display.stackSize + "x " + display.getDisplayName());
		}
		if(inputFluid != null) for(FluidStack fluid : inputFluid) list.add("  " + EnumChatFormatting.BLUE + fluid.fill + "mB " + fluid.type.getLocalizedName() + (fluid.pressure == 0 ? "" : " at " + EnumChatFormatting.RED + fluid.pressure + " PU"));
		list.add(EnumChatFormatting.BOLD + "Output:");
		if(outputItem != null) for(IOutput output : outputItem) for(String line : output.getLabel()) list.add("  " + line);
		if(outputFluid != null) for(FluidStack fluid : outputFluid) list.add("  " + EnumChatFormatting.BLUE + fluid.fill + "mB " + fluid.type.getLocalizedName() + (fluid.pressure == 0 ? "" : " at " + EnumChatFormatting.RED + fluid.pressure + " PU"));
		return list;
	}
	
	/** Default impl only matches localized name substring, can be extended to include ingredients as well */
	public boolean matchesSearch(String substring) {
		return getLocalizedName().toLowerCase(Locale.US).contains(substring.toLowerCase(Locale.US));
	}
}
