package com.hbm.inventory.recipes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ItemAmmoEnums;
import com.hbm.items.ModItems;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

public class MagicRecipes {
	
	private static List<MagicRecipe> recipes = new ArrayList();

	public static ItemStack getRecipe(InventoryCrafting matrix) {
		
		List<ComparableStack> comps = new ArrayList();
		
		for(int i = 0; i < 4; i++) {
			if(matrix.getStackInSlot(i) != null)
				comps.add(ComparableStack.getComparableStack(matrix.getStackInSlot(i)).makeSingular());
		}
		
		//Collections.sort(comps);
		
		for(MagicRecipe recipe : recipes) {
			if(recipe.matches(comps))
				return recipe.getResult();
		}
		
		return null;
	}
	
	public static void register() {
		recipes.add(new MagicRecipe(new ItemStack(ModItems.ingot_u238m2), ComparableStack.getComparableStack(ModItems.ingot_u238m2, 1, 1), ComparableStack.getComparableStack(ModItems.ingot_u238m2, 1, 2), ComparableStack.getComparableStack(ModItems.ingot_u238m2, 1, 3)));
		recipes.add(new MagicRecipe(new ItemStack(ModItems.rod_of_discord), ComparableStack.getComparableStack(Items.ender_pearl), ComparableStack.getComparableStack(Items.blaze_rod), ComparableStack.getComparableStack(ModItems.nugget_euphemium)));
//		recipes.add(new MagicRecipe(new ItemStack(ModItems.balefire_and_steel), new OreDictStack("ingotSteel"), ComparableStack.getComparableStack(ModItems.egg_balefire_shard)));
		recipes.add(new MagicRecipe(new ItemStack(ModItems.mysteryshovel), ComparableStack.getComparableStack(Items.iron_shovel), ComparableStack.getComparableStack(Items.bone), ComparableStack.getComparableStack(ModItems.ingot_starmetal), ComparableStack.getComparableStack(ModItems.ducttape)));
		recipes.add(new MagicRecipe(new ItemStack(ModItems.ingot_electronium), ComparableStack.getComparableStack(ModItems.pellet_charged), ComparableStack.getComparableStack(ModItems.pellet_charged), ComparableStack.getComparableStack(ModItems.ingot_dineutronium), ComparableStack.getComparableStack(ModItems.ingot_dineutronium)));

		recipes.add(new MagicRecipe(new ItemStack(ModItems.ammo_44, 1, ItemAmmoEnums.Ammo44Magnum.PIP.ordinal()),
				ComparableStack.getComparableStack(ModItems.ammo_44),
				ComparableStack.getComparableStack(ModItems.powder_magic),
				ComparableStack.getComparableStack(ModItems.powder_magic),
				ComparableStack.getComparableStack(ModItems.powder_magic)));
		recipes.add(new MagicRecipe(new ItemStack(ModItems.ammo_44, 1, ItemAmmoEnums.Ammo44Magnum.BJ.ordinal()),
				ComparableStack.getComparableStack(ModItems.ammo_44),
				ComparableStack.getComparableStack(ModItems.powder_magic),
				ComparableStack.getComparableStack(ModItems.powder_magic),
				ComparableStack.getComparableStack(ModItems.powder_desh)));
		recipes.add(new MagicRecipe(new ItemStack(ModItems.ammo_44, 1, ItemAmmoEnums.Ammo44Magnum.SILVER.ordinal()),
				ComparableStack.getComparableStack(ModItems.ammo_44),
				ComparableStack.getComparableStack(ModItems.powder_magic),
				ComparableStack.getComparableStack(ModItems.powder_magic),
				ComparableStack.getComparableStack(ModItems.ingot_starmetal)));
		
//		recipes.add(new MagicRecipe(new ItemStack(ModItems.gun_bf),
//				ComparableStack.getComparableStack(ModItems.gun_fatman),
//				ComparableStack.getComparableStack(ModItems.egg_balefire_shard),
//				ComparableStack.getComparableStack(ModItems.powder_magic),
//				ComparableStack.getComparableStack(ModItems.powder_magic)));
//		
//		recipes.add(new MagicRecipe(new ItemStack(ModItems.diamond_gavel),
//				ComparableStack.getComparableStack(ModBlocks.gravel_diamond),
//				ComparableStack.getComparableStack(ModBlocks.gravel_diamond),
//				ComparableStack.getComparableStack(ModBlocks.gravel_diamond),
//				ComparableStack.getComparableStack(ModItems.lead_gavel)));
//		
//		recipes.add(new MagicRecipe(new ItemStack(ModItems.mese_gavel),
//				ComparableStack.getComparableStack(ModItems.shimmer_handle),
//				ComparableStack.getComparableStack(ModItems.powder_dineutronium),
//				ComparableStack.getComparableStack(ModItems.blades_desh),
//				ComparableStack.getComparableStack(ModItems.diamond_gavel)));
		
		recipes.add(new MagicRecipe(new ItemStack(ModBlocks.hadron_coil_mese),
				ComparableStack.getComparableStack(ModBlocks.hadron_coil_chlorophyte),
				ComparableStack.getComparableStack(ModItems.powder_dineutronium),
				ComparableStack.getComparableStack(ModItems.plate_desh),
				new OreDictStack("dustGold")));
//		
//		recipes.add(new MagicRecipe(new ItemStack(ModItems.gun_darter),
//				new OreDictStack("plateSteel"),
//				new OreDictStack("plateSteel"),
//				ComparableStack.getComparableStack(ModItems.ingot_polymer),
//				new OreDictStack("plateGold")));

		recipes.add(new MagicRecipe(new ItemStack(ModItems.ammo_dart, 4, ItemAmmoEnums.AmmoDart.NUCLEAR.ordinal()),
				ComparableStack.getComparableStack(ModItems.plate_polymer),
				ComparableStack.getComparableStack(ModItems.nugget_pu239),
				ComparableStack.getComparableStack(ModItems.circuit_aluminium)));
	}
	
	public static List<MagicRecipe> getRecipes() {
		return recipes;
	}
	
	public static class MagicRecipe {

		public List<AStack> in;
		public ItemStack out;
		
		public MagicRecipe(ItemStack out, AStack... in) {
			this.out = out;
			this.in = Arrays.asList(in);
			//Collections.sort(this.in);
		}
		
		public boolean matches(List<ComparableStack> comps) {
			
			if(comps.size() != in.size())
				return false;
			
			for(int i = 0; i < in.size(); i++) {
				
				if(!in.get(i).isApplicable(comps.get(i)))
					return false;
			}
			
			return true;
		}
		
		public ItemStack getResult() {
			return out.copy();
		}
	}
}
