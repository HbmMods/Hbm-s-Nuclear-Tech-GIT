package com.hbm.items.machine;

import java.util.List;

import com.hbm.inventory.recipes.ChemplantRecipes;
import com.hbm.inventory.recipes.ChemplantRecipes.ChemRecipe;
import com.hbm.items.ModItems;
import com.hbm.util.i18n.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

public class ItemChemistryTemplate extends Item {

	public ItemChemistryTemplate() {
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	public String getItemStackDisplayName(ItemStack stack) {
		
		ChemRecipe recipe = ChemplantRecipes.indexMapping.get(stack.getItemDamage());
		
		if(recipe == null) {
			return EnumChatFormatting.RED + "Broken Template" + EnumChatFormatting.RESET;
		} else {
			String s = ("" + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name")).trim();
			String s1 = ("" + StatCollector.translateToLocal("chem." + recipe.name)).trim();
	
			if(s1 != null) {
				s = s + " " + s1;
			}
	
			return s;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		for(int i = 0; i < ChemplantRecipes.recipes.size(); i++) {
			list.add(new ItemStack(item, 1, ChemplantRecipes.recipes.get(i).getId()));
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {

		if(!(stack.getItem() instanceof ItemChemistryTemplate))
			return;
		
		ChemRecipe recipe = ChemplantRecipes.indexMapping.get(stack.getItemDamage());

		if(recipe == null) {
			return;
		}

		list.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("info.templatefolder", I18nUtil.resolveKey(ModItems.template_folder.getUnlocalizedName() + ".name")));
		list.add("");

		try {
			list.add(EnumChatFormatting.BOLD + I18nUtil.resolveKey("info.template_out_p"));
			for(int i = 0; i < 4; i++) {
				if(recipe.outputs[i] != null) {
					list.add(recipe.outputs[i].stackSize + "x " + recipe.outputs[i].getDisplayName());
				}
			}

			for(int i = 0; i < 2; i++) {
				if(recipe.outputFluids[i] != null) {
					int p = recipe.outputFluids[i].pressure;
					list.add(recipe.outputFluids[i].fill + "mB " + recipe.outputFluids[i].type.getLocalizedName() + (p != 0 ? (" at " + p + "PU") : ""));
				}
			}

			list.add(EnumChatFormatting.BOLD + I18nUtil.resolveKey("info.template_in_p"));

			for(int i = 0; i < recipe.inputs.length; i++) {
				if(recipe.inputs[i] != null) {
					list.add(recipe.inputs[i].stacksize + "x " + recipe.inputs[i].extractForCyclingDisplay(20).getDisplayName());
				}
			}

			for(int i = 0; i < 2; i++) {
				if(recipe.inputFluids[i] != null) {
					int p = recipe.inputFluids[i].pressure;
					list.add(recipe.inputFluids[i].fill + "mB " + recipe.inputFluids[i].type.getLocalizedName() + (p != 0 ? (" at " + p + "PU") : ""));
				}
			}

			list.add(EnumChatFormatting.BOLD + I18nUtil.resolveKey("info.template_time"));
			list.add(Math.floor((float) (recipe.getDuration()) / 20 * 100) / 100 + " " + I18nUtil.resolveKey("info.template_seconds"));
		} catch(Exception e) {
			list.add("###INVALID###");
			list.add("0x334077-0x6A298F-0xDF3795-0x334077");
		}
	}
}
