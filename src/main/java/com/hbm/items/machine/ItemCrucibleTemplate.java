package com.hbm.items.machine;

import java.util.List;

import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.inventory.recipes.CrucibleRecipes;
import com.hbm.inventory.recipes.CrucibleRecipes.CrucibleRecipe;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

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
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		
		CrucibleRecipe recipe = CrucibleRecipes.indexMapping.get(stack.getItemDamage());
		
		if(recipe == null) {
			return;
		}
		
		list.add("Input:");
		
		for(MaterialStack in : recipe.input) {
			list.add("- " + in.material.names[0] + ": " + Mats.formatAmount(in.amount));
		}
		
		list.add("Output:");
		
		for(MaterialStack out : recipe.output) {
			list.add("- " + out.material.names[0] + ": " + Mats.formatAmount(out.amount));
		}
	}
}
