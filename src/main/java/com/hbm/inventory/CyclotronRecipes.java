package com.hbm.inventory;

import java.util.HashMap;

import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.items.ModItems;

import net.minecraft.item.ItemStack;

public class CyclotronRecipes {

	private static HashMap<Object, ItemStack> lithium = new HashMap();
	private static HashMap<Object, ItemStack> beryllium = new HashMap();
	private static HashMap<Object, ItemStack> carbon = new HashMap();
	private static HashMap<Object, ItemStack> copper = new HashMap();
	private static HashMap<Object, ItemStack> plutonium = new HashMap();
	
	public static void register() {

		/// LITHIUM START ///
		lithium.put("dustNetherQuartz", new ItemStack(ModItems.powder_fire));
		lithium.put("dustPhosphorus", new ItemStack(ModItems.sulfur));
		lithium.put("dustIron", new ItemStack(ModItems.powder_cobalt));
		lithium.put("dustGold", new ItemStack(ModItems.nugget_mercury));
		lithium.put("dustPolonium", new ItemStack(ModItems.powder_astatine));
		lithium.put("dustLanthanium", new ItemStack(ModItems.powder_cerium));
		lithium.put("dustActinium", new ItemStack(ModItems.powder_thorium));
		lithium.put("dustUranium", new ItemStack(ModItems.powder_neptunium));
		lithium.put("dustNeptunium", new ItemStack(ModItems.powder_plutonium));
		/// LITHIUM END ///

		/// BERYLLIUM START ///
		beryllium.put("dustNetherQuartz", new ItemStack(ModItems.sulfur));
		beryllium.put(new ComparableStack(ModItems.powder_strontium), new ItemStack(ModItems.powder_niobium));
		beryllium.put(new ComparableStack(ModItems.powder_cerium), new ItemStack(ModItems.powder_neodymium));
		beryllium.put("dustThorium", new ItemStack(ModItems.powder_uranium));
		/// BERYLLIUM END ///
		
		/// CARBON START ///
		carbon.put("dustSulfur", new ItemStack(ModItems.powder_titanium));
		carbon.put("dustTitanium", new ItemStack(ModItems.powder_iron));
		carbon.put(new ComparableStack(ModItems.powder_caesium), new ItemStack(ModItems.powder_lanthanium));
		carbon.put(new ComparableStack(ModItems.powder_neodymium), new ItemStack(ModItems.powder_gold));
		carbon.put(new ComparableStack(ModItems.nugget_mercury), new ItemStack(ModItems.powder_polonium));
		carbon.put(new ComparableStack(ModItems.powder_astatine), new ItemStack(ModItems.powder_actinium));
		/// CARBON END ///
		
		/// COPPER START ///
		copper.put("dustBeryllium", new ItemStack(ModItems.powder_quartz));
		copper.put("dustCoal", new ItemStack(ModItems.powder_bromine));
		copper.put("dustTitanium", new ItemStack(ModItems.powder_strontium));
		copper.put("dustIron", new ItemStack(ModItems.powder_niobium));
		copper.put(new ComparableStack(ModItems.powder_bromine), new ItemStack(ModItems.powder_iodine));
		copper.put(new ComparableStack(ModItems.powder_strontium), new ItemStack(ModItems.powder_neodymium));
		copper.put(new ComparableStack(ModItems.powder_niobium), new ItemStack(ModItems.powder_caesium));
		copper.put(new ComparableStack(ModItems.powder_iodine), new ItemStack(ModItems.powder_polonium));
		copper.put(new ComparableStack(ModItems.powder_caesium), new ItemStack(ModItems.powder_actinium));
		copper.put("dustGold", new ItemStack(ModItems.powder_uranium));
		/// COPPER END ///

		/// PLUTONIUM START ///
		plutonium.put("dustPhosphorus", new ItemStack(ModItems.powder_tennessine));
		plutonium.put("dustPlutonium", new ItemStack(ModItems.powder_tennessine));
		/// PLUTONIUM END ///
		
		///TODO: fictional elements
	}
	
	public static ItemStack getOutput(ItemStack stack, ItemStack box) {
		
		if(stack == null || stack.getItem() == null || box == null)
			return null;
		
		HashMap<Object, ItemStack> pool = null;
		
		if(box.getItem() == ModItems.part_lithium) {
			pool = lithium;
		} else if(box.getItem() == ModItems.part_beryllium) {
			pool = beryllium;
		} else if(box.getItem() == ModItems.part_carbon) {
			pool = carbon;
		} else if(box.getItem() == ModItems.part_copper) {
			pool = copper;
		} else if(box.getItem() == ModItems.part_plutonium) {
			pool = plutonium;
		}
		
		if(pool == null)
			return null;
		
		ComparableStack comp = new ComparableStack(stack.getItem(), 1, stack.getItemDamage());
		
		if(pool.containsKey(comp))
			return pool.get(comp).copy();
		
		String[] dictKeys = comp.getDictKeys();
		
		for(String key : dictKeys) {

			if(pool.containsKey(key))
				return pool.get(key).copy();
		}
		
		return null;
	}
}
