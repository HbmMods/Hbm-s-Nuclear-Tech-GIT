package com.hbm.inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
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
				comps.add(new ComparableStack(matrix.getStackInSlot(i)).makeSingular());
		}
		
		//Collections.sort(comps);
		
		for(MagicRecipe recipe : recipes) {
			if(recipe.matches(comps))
				return recipe.getResult();
		}
		
		return null;
	}
	
	public static void register() {
		recipes.add(new MagicRecipe(new ItemStack(ModItems.ingot_u238m2), new ComparableStack(ModItems.ingot_u238m2, 1, 1), new ComparableStack(ModItems.ingot_u238m2, 1, 2), new ComparableStack(ModItems.ingot_u238m2, 1, 3)));
		recipes.add(new MagicRecipe(new ItemStack(ModItems.rod_of_discord), new ComparableStack(Items.ender_pearl), new ComparableStack(Items.blaze_rod), new ComparableStack(ModItems.nugget_euphemium)));
		recipes.add(new MagicRecipe(new ItemStack(ModItems.balefire_and_steel), new OreDictStack("ingotSteel"), new ComparableStack(ModItems.egg_balefire_shard)));
		recipes.add(new MagicRecipe(new ItemStack(ModItems.mysteryshovel), new ComparableStack(Items.iron_shovel), new ComparableStack(Items.bone), new ComparableStack(ModItems.ingot_starmetal), new ComparableStack(ModItems.ducttape)));
		recipes.add(new MagicRecipe(new ItemStack(ModItems.ingot_electronium), new ComparableStack(ModItems.dynosphere_dineutronium_charged), new ComparableStack(ModItems.dynosphere_dineutronium_charged), new ComparableStack(ModItems.dynosphere_dineutronium_charged), new ComparableStack(ModItems.dynosphere_dineutronium_charged)));

		recipes.add(new MagicRecipe(new ItemStack(ModItems.ammo_44_pip),
				new ComparableStack(ModItems.ammo_44),
				new ComparableStack(ModItems.powder_magic),
				new ComparableStack(ModItems.powder_magic),
				new ComparableStack(ModItems.powder_magic)));
		recipes.add(new MagicRecipe(new ItemStack(ModItems.ammo_44_bj),
				new ComparableStack(ModItems.ammo_44),
				new ComparableStack(ModItems.powder_magic),
				new ComparableStack(ModItems.powder_magic),
				new ComparableStack(ModItems.powder_desh)));
		recipes.add(new MagicRecipe(new ItemStack(ModItems.ammo_44_silver),
				new ComparableStack(ModItems.ammo_44),
				new ComparableStack(ModItems.powder_magic),
				new ComparableStack(ModItems.powder_magic),
				new ComparableStack(ModItems.ingot_starmetal)));
		
		recipes.add(new MagicRecipe(new ItemStack(ModItems.gun_bf),
				new ComparableStack(ModItems.gun_fatman),
				new ComparableStack(ModItems.egg_balefire_shard),
				new ComparableStack(ModItems.powder_magic),
				new ComparableStack(ModItems.powder_magic)));
		
		recipes.add(new MagicRecipe(new ItemStack(ModItems.diamond_gavel),
				new ComparableStack(ModBlocks.gravel_diamond),
				new ComparableStack(ModBlocks.gravel_diamond),
				new ComparableStack(ModBlocks.gravel_diamond),
				new ComparableStack(ModItems.lead_gavel)));
		
		recipes.add(new MagicRecipe(new ItemStack(ModItems.mese_gavel),
				new ComparableStack(ModItems.shimmer_handle),
				new ComparableStack(ModItems.powder_dineutronium),
				new ComparableStack(ModItems.blades_desh),
				new ComparableStack(ModItems.diamond_gavel)));
		
		recipes.add(new MagicRecipe(new ItemStack(ModBlocks.hadron_coil_mese),
				new ComparableStack(ModBlocks.hadron_coil_chlorophyte),
				new ComparableStack(ModItems.powder_dineutronium),
				new ComparableStack(ModItems.plate_desh),
				new OreDictStack("dustGold")));
		
		recipes.add(new MagicRecipe(new ItemStack(ModItems.gun_darter),
				new OreDictStack("plateSteel"),
				new OreDictStack("plateSteel"),
				new ComparableStack(ModItems.ingot_polymer),
				new OreDictStack("plateGold")));
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
