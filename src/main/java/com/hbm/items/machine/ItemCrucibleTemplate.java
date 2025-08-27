package com.hbm.items.machine;

import java.util.List;

import org.lwjgl.input.Keyboard;

import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.inventory.recipes.CrucibleRecipes;
import com.hbm.inventory.recipes.CrucibleRecipes.CrucibleRecipe;
import com.hbm.util.i18n.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

public class ItemCrucibleTemplate extends Item {

	public ItemCrucibleTemplate() {
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		for(int i = 0; i < CrucibleRecipes.recipes.size(); i++) {
			list.add(new ItemStack(item, 1, CrucibleRecipes.recipes.get(i).getId()));
		}
	}

	public String getItemStackDisplayName(ItemStack stack) {
		
		CrucibleRecipe recipe = CrucibleRecipes.indexMapping.get(stack.getItemDamage());
		
		if(recipe == null) {
			return super.getItemStackDisplayName(stack);
		}
		
		String s = ("" + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name")).trim();
		String s1 = ("" + StatCollector.translateToLocal(recipe.getName())).trim();

		if(s1 != null) {
			s = s + " " + s1;
		}

		return s;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		
		CrucibleRecipe recipe = CrucibleRecipes.indexMapping.get(stack.getItemDamage());
		
		if(recipe == null) {
			return;
		}

		list.add(EnumChatFormatting.BOLD + I18nUtil.resolveKey("info.template_out_p"));
		for(MaterialStack out : recipe.output) {
			list.add(I18nUtil.resolveKey(out.material.getUnlocalizedName()) + ": " + Mats.formatAmount(out.amount, Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)));
		}

		list.add(EnumChatFormatting.BOLD + I18nUtil.resolveKey("info.template_in_p"));
		
		for(MaterialStack in : recipe.input) {
			list.add(I18nUtil.resolveKey(in.material.getUnlocalizedName()) + ": " + Mats.formatAmount(in.amount, Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)));
		}
	}
}
