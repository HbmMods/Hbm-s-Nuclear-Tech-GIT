package com.hbm.items.machine;

import java.util.List;

import com.hbm.inventory.recipes.ChemplantRecipes;
import com.hbm.inventory.recipes.ChemplantRecipes.ChemRecipe;
import com.hbm.items.ModItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

public class ItemChemistryIcon extends Item {

	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	public ItemChemistryIcon() {
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	public String getItemStackDisplayName(ItemStack stack) {
		
		ChemRecipe recipe = ChemplantRecipes.indexMapping.get(stack.getItemDamage());
		
		String s = ("" + StatCollector.translateToLocal(ModItems.chemistry_template.getUnlocalizedName() + ".name")).trim();
		String s1 = ("" + StatCollector.translateToLocal("chem." + recipe.name)).trim();

		if(s1 != null) {
			s = s + " " + s1;
		}

		return s;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		for(int i = 0; i < ChemplantRecipes.recipes.size(); i++) {
			list.add(new ItemStack(item, 1, ChemplantRecipes.recipes.get(i).getId()));
		}
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		this.icons = new IIcon[ChemplantRecipes.recipes.size()];

		for(int i = 0; i < icons.length; ++i) {
			this.icons[i] = reg.registerIcon("hbm:chem_icon_" + ChemplantRecipes.recipes.get(i).name);
		}
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int i) {
		ChemRecipe rec = ChemplantRecipes.indexMapping.get(i);
		
		if(rec != null) {
			return this.icons[rec.listing % this.icons.length];
		} else {
			return ModItems.nothing.getIconFromDamage(i);
		}
	}
}
