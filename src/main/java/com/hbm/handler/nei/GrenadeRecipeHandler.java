package com.hbm.handler.nei;

import java.util.ArrayList;

import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.grenade.ItemGrenadeExtra.EnumGrenadeExtra;
import com.hbm.items.weapon.grenade.ItemGrenadeFilling.EnumGrenadeFilling;
import com.hbm.items.weapon.grenade.ItemGrenadeFuze.EnumGrenadeFuze;
import com.hbm.items.weapon.grenade.ItemGrenadeShell.EnumGrenadeShell;
import com.hbm.util.EnumUtil;

import com.hbm.items.weapon.grenade.ItemGrenadeUniversal;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class GrenadeRecipeHandler  extends NEIUniversalHandler {

	public GrenadeRecipeHandler() {
		super("Grenade Crafting", Blocks.crafting_table, new ArrayList());
	}

	@Override
	public String getKey() {
		return "ntmGrenade";
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		
		if(outputId.equals(getKey())) {
			
			for(EnumGrenadeShell shell : EnumGrenadeShell.values()) for(EnumGrenadeFilling filling : EnumGrenadeFilling.values()) {
				if(filling.compatibleShells.contains(shell)) for(EnumGrenadeFuze fuze : EnumGrenadeFuze.values()) {
					addRecipe(shell, filling, fuze, null);
					for(EnumGrenadeExtra extra : EnumGrenadeExtra.values()) addRecipe(shell, filling, fuze, extra);
				}
			}
			
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		
		if(ingredient.getItem() == ModItems.grenade_shell) {
			EnumGrenadeShell shell = EnumUtil.grabEnumSafely(EnumGrenadeShell.class, ingredient.getItemDamage());
			for(EnumGrenadeFilling filling : EnumGrenadeFilling.values()) {
				if(filling.compatibleShells.contains(shell)) for(EnumGrenadeFuze fuze : EnumGrenadeFuze.values()) {
					addRecipe(shell, filling, fuze, null);
					for(EnumGrenadeExtra extra : EnumGrenadeExtra.values()) addRecipe(shell, filling, fuze, extra);
				}
			}
		}
		
		if(ingredient.getItem() == ModItems.grenade_filling) {
			EnumGrenadeFilling filling = EnumUtil.grabEnumSafely(EnumGrenadeFilling.class, ingredient.getItemDamage());
			for(EnumGrenadeShell shell : EnumGrenadeShell.values()) {
				if(filling.compatibleShells.contains(shell)) for(EnumGrenadeFuze fuze : EnumGrenadeFuze.values()) {
					addRecipe(shell, filling, fuze, null);
					for(EnumGrenadeExtra extra : EnumGrenadeExtra.values()) addRecipe(shell, filling, fuze, extra);
				}
			}
		}
		
		if(ingredient.getItem() == ModItems.grenade_fuze) {
			EnumGrenadeFuze fuze = EnumUtil.grabEnumSafely(EnumGrenadeFuze.class, ingredient.getItemDamage());
			for(EnumGrenadeShell shell : EnumGrenadeShell.values()) for(EnumGrenadeFilling filling : EnumGrenadeFilling.values()) {
				if(filling.compatibleShells.contains(shell)) {
					addRecipe(shell, filling, fuze, null);
					for(EnumGrenadeExtra extra : EnumGrenadeExtra.values()) addRecipe(shell, filling, fuze, extra);
				}
			}
		}
		
		if(ingredient.getItem() == ModItems.grenade_extra) {
			EnumGrenadeExtra extra = EnumUtil.grabEnumSafely(EnumGrenadeExtra.class, ingredient.getItemDamage());
			for(EnumGrenadeShell shell : EnumGrenadeShell.values()) for(EnumGrenadeFilling filling : EnumGrenadeFilling.values()) {
				if(filling.compatibleShells.contains(shell)) for(EnumGrenadeFuze fuze : EnumGrenadeFuze.values()) {
					addRecipe(shell, filling, fuze, extra);
				}
			}
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		if(result == null || result.getItem() != ModItems.grenade_universal) return;

		EnumGrenadeShell shell = ItemGrenadeUniversal.getShell(result);
		EnumGrenadeFilling filling = ItemGrenadeUniversal.getFilling(result);
		EnumGrenadeFuze fuze = ItemGrenadeUniversal.getFuze(result);
		EnumGrenadeExtra extra = ItemGrenadeUniversal.getExtra(result);
		addRecipe(shell, filling, fuze, extra);
	}
	
	public void addRecipe(EnumGrenadeShell shell, EnumGrenadeFilling filling, EnumGrenadeFuze fuze, EnumGrenadeExtra extra) {
		
		ItemStack[][] ins = new ItemStack[extra != null ? 4 : 3][1];
		ins[0][0] = DictFrame.fromOne(ModItems.grenade_shell, shell);
		ins[1][0] = DictFrame.fromOne(ModItems.grenade_filling, filling);
		ins[2][0] = DictFrame.fromOne(ModItems.grenade_fuze, fuze);
		if(extra != null) ins[3][0] = DictFrame.fromOne(ModItems.grenade_extra, extra);
		
		ItemStack[][] outs = new ItemStack[][] {{ItemGrenadeUniversal.make(shell, filling, fuze, extra)}};
		this.arecipes.add(new RecipeSet(ins, outs, null));
	}
}
