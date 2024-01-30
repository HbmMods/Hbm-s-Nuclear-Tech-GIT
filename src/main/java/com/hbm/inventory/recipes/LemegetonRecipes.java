package com.hbm.inventory.recipes;

import java.util.HashMap;
import java.util.Map.Entry;

import static com.hbm.inventory.OreDictManager.*;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.items.ModItems;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class LemegetonRecipes {
	
	public static HashMap<AStack, ItemStack> recipes = new HashMap();
	
	public static void register() {
		recipes.put(new OreDictStack(IRON.ingot()), new ItemStack(ModItems.ingot_steel));
		recipes.put(new OreDictStack(STEEL.ingot()), new ItemStack(ModItems.ingot_dura_steel));
		recipes.put(new OreDictStack(DURA.ingot()), new ItemStack(ModItems.ingot_tcalloy));
		recipes.put(new OreDictStack(TCALLOY.ingot()), new ItemStack(ModItems.ingot_combine_steel));
		recipes.put(new OreDictStack(CMB.ingot()), new ItemStack(ModItems.ingot_dineutronium));

		recipes.put(new OreDictStack(TI.ingot()), new ItemStack(ModItems.ingot_saturnite));
		recipes.put(new OreDictStack(BIGMT.ingot()), new ItemStack(ModItems.ingot_starmetal));

		recipes.put(new OreDictStack(CU.ingot()), new ItemStack(ModItems.ingot_red_copper));
		recipes.put(new OreDictStack(MINGRADE.ingot()), new ItemStack(ModItems.ingot_advanced_alloy));
		recipes.put(new OreDictStack(ALLOY.ingot()), new ItemStack(ModItems.ingot_desh));

		recipes.put(new OreDictStack(PB.ingot()), new ItemStack(Items.gold_ingot));
		recipes.put(new OreDictStack(GOLD.ingot()), new ItemStack(ModItems.ingot_bismuth));
		recipes.put(new OreDictStack(BI.ingot()), new ItemStack(ModItems.ingot_osmiridium));

		recipes.put(new OreDictStack(TH232.ingot()), new ItemStack(ModItems.ingot_uranium));
		recipes.put(new OreDictStack(U.ingot()), new ItemStack(ModItems.ingot_u238));
		recipes.put(new OreDictStack(U238.ingot()), new ItemStack(ModItems.ingot_u235));
		recipes.put(new OreDictStack(U235.ingot()), new ItemStack(ModItems.ingot_plutonium));
		recipes.put(new OreDictStack(PU.ingot()), new ItemStack(ModItems.ingot_pu238));
		recipes.put(new OreDictStack(PU238.ingot()), new ItemStack(ModItems.ingot_pu239));
		recipes.put(new OreDictStack(PU239.ingot()), new ItemStack(ModItems.ingot_pu240));
		recipes.put(new OreDictStack(PU240.ingot()), new ItemStack(ModItems.ingot_pu241));
		recipes.put(new OreDictStack(PU241.ingot()), new ItemStack(ModItems.ingot_am241));
		recipes.put(new OreDictStack(AM241.ingot()), new ItemStack(ModItems.ingot_am242));

		recipes.put(new OreDictStack(RA226.ingot()), new ItemStack(ModItems.ingot_polonium));
		recipes.put(new OreDictStack(PO210.ingot()), new ItemStack(ModItems.ingot_technetium));

		recipes.put(new OreDictStack(POLYMER.ingot()), new ItemStack(ModItems.ingot_pc));
		recipes.put(new OreDictStack(BAKELITE.ingot()), new ItemStack(ModItems.ingot_pvc));
		recipes.put(new OreDictStack(LATEX.ingot()), new ItemStack(ModItems.ingot_rubber));

		recipes.put(new OreDictStack(COAL.gem()), new ItemStack(ModItems.ingot_graphite));
		recipes.put(new OreDictStack(GRAPHITE.ingot()), new ItemStack(Items.diamond));
		recipes.put(new OreDictStack(DIAMOND.gem()), new ItemStack(ModItems.ingot_cft));

		recipes.put(new OreDictStack(F.dust()), new ItemStack(ModItems.gem_sodalite));
		recipes.put(new OreDictStack(SODALITE.gem()), new ItemStack(ModItems.gem_volcanic));
		recipes.put(new OreDictStack(VOLCANIC.gem()), new ItemStack(ModItems.gem_rad));
		recipes.put(new ComparableStack(ModItems.gem_rad), new ItemStack(ModItems.gem_alexandrite));

		recipes.put(new OreDictStack(KEY_SAND), new ItemStack(ModItems.ingot_fiberglass));
		recipes.put(new OreDictStack(FIBER.ingot()), new ItemStack(ModItems.ingot_asbestos));
	}

	public static ItemStack getRecipe(ItemStack ingredient) {
		
		for(Entry<AStack, ItemStack> entry : recipes.entrySet()) {
			if(entry.getKey().matchesRecipe(ingredient, true)) {
				return entry.getValue().copy();
			}
		}
		
		return null;
	}
}
