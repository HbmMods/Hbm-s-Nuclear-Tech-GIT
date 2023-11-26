package com.hbm.inventory.recipes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.hbm.inventory.OreDictManager.*;
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
		recipes.add(new MagicRecipe(new ItemStack(ModItems.balefire_and_steel), new OreDictStack(STEEL.ingot()), new ComparableStack(ModItems.egg_balefire_shard)));
		recipes.add(new MagicRecipe(new ItemStack(ModItems.mysteryshovel), new ComparableStack(Items.iron_shovel), new ComparableStack(Items.bone), new ComparableStack(ModItems.ingot_starmetal), new ComparableStack(ModItems.ducttape)));
		recipes.add(new MagicRecipe(new ItemStack(ModItems.ingot_electronium), new ComparableStack(ModItems.pellet_charged), new ComparableStack(ModItems.pellet_charged), new ComparableStack(ModItems.ingot_dineutronium), new ComparableStack(ModItems.ingot_dineutronium)));

		recipes.add(new MagicRecipe(new ItemStack(ModItems.ammo_44, 1, ItemAmmoEnums.Ammo44Magnum.PIP.ordinal()),
				new ComparableStack(ModItems.ammo_44),
				new ComparableStack(ModItems.powder_magic),
				new ComparableStack(ModItems.powder_magic),
				new ComparableStack(ModItems.powder_magic)));
		recipes.add(new MagicRecipe(new ItemStack(ModItems.ammo_44, 1, ItemAmmoEnums.Ammo44Magnum.BJ.ordinal()),
				new ComparableStack(ModItems.ammo_44),
				new ComparableStack(ModItems.powder_magic),
				new ComparableStack(ModItems.powder_magic),
				new ComparableStack(ModItems.powder_desh)));
		recipes.add(new MagicRecipe(new ItemStack(ModItems.ammo_44, 1, ItemAmmoEnums.Ammo44Magnum.SILVER.ordinal()),
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
				new OreDictStack(GOLD.dust())));
		
		recipes.add(new MagicRecipe(new ItemStack(ModBlocks.hadron_coil_mese),
				new ComparableStack(ModBlocks.hadron_coil_chlorophyte),
				new OreDictStack(DNT.wireDense()),
				new OreDictStack(W.wireDense()),
				new OreDictStack(GOLD.wireDense())));
		
		recipes.add(new MagicRecipe(new ItemStack(ModItems.gun_darter),
				new OreDictStack(STEEL.plate()),
				new OreDictStack(STEEL.plate()),
				new ComparableStack(ModItems.ingot_polymer),
				new OreDictStack(GOLD.plate())));

		recipes.add(new MagicRecipe(new ItemStack(ModItems.ammo_dart, 4, ItemAmmoEnums.AmmoDart.NUCLEAR.ordinal()),
				new OreDictStack(ANY_RUBBER.ingot()),
				new ComparableStack(ModItems.nugget_pu239),
				new ComparableStack(ModItems.circuit_aluminium)));
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
