package com.hbm.inventory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class CyclotronRecipes {

	//i could have used classes for this, oh bother
	private static HashMap<Object, ItemStack> lithium = new HashMap();
	private static HashMap<Object, ItemStack> beryllium = new HashMap();
	private static HashMap<Object, ItemStack> carbon = new HashMap();
	private static HashMap<Object, ItemStack> copper = new HashMap();
	private static HashMap<Object, ItemStack> plutonium = new HashMap();
	private static HashMap<Object, Integer> liAmat = new HashMap();
	private static HashMap<Object, Integer> beAmat = new HashMap();
	private static HashMap<Object, Integer> caAmat = new HashMap();
	private static HashMap<Object, Integer> coAmat = new HashMap();
	private static HashMap<Object, Integer> plAmat = new HashMap();
	
	public static void register() {

		/// LITHIUM START ///
		int liA = 50;
		
		makeRecipe(lithium, liAmat, "dustNetherQuartz", new ItemStack(ModItems.powder_fire), liA);
		makeRecipe(lithium, liAmat, "dustPhosphorus", new ItemStack(ModItems.sulfur), liA);
		makeRecipe(lithium, liAmat, "dustIron", new ItemStack(ModItems.powder_cobalt), liA);
		makeRecipe(lithium, liAmat, "dustGold", new ItemStack(ModItems.nugget_mercury), liA);
		makeRecipe(lithium, liAmat, "dustPolonium", new ItemStack(ModItems.powder_astatine), liA);
		makeRecipe(lithium, liAmat, "dustLanthanium", new ItemStack(ModItems.powder_cerium), liA);
		makeRecipe(lithium, liAmat, "dustActinium", new ItemStack(ModItems.powder_thorium), liA);
		makeRecipe(lithium, liAmat, "dustUranium", new ItemStack(ModItems.powder_neptunium), liA);
		makeRecipe(lithium, liAmat, "dustNeptunium", new ItemStack(ModItems.powder_plutonium), liA);
		makeRecipe(lithium, liAmat, new ComparableStack(ModItems.powder_reiium), new ItemStack(ModItems.powder_weidanium), liA);
		/// LITHIUM END ///

		/// BERYLLIUM START ///
		int beA = 25;
		
		makeRecipe(beryllium, beAmat, "dustNetherQuartz", new ItemStack(ModItems.sulfur), beA);
		makeRecipe(beryllium, beAmat, "dustTitanium", new ItemStack(ModItems.powder_iron), beA);
		makeRecipe(beryllium, beAmat, "dustCobalt", new ItemStack(ModItems.powder_copper), beA);
		makeRecipe(beryllium, beAmat, new ComparableStack(ModItems.powder_strontium), new ItemStack(ModItems.powder_niobium), beA);
		makeRecipe(beryllium, beAmat, new ComparableStack(ModItems.powder_cerium), new ItemStack(ModItems.powder_neodymium), beA);
		makeRecipe(beryllium, beAmat, "dustThorium", new ItemStack(ModItems.powder_uranium), beA);
		makeRecipe(beryllium, beAmat, new ComparableStack(ModItems.powder_weidanium), new ItemStack(ModItems.powder_australium), beA);
		/// BERYLLIUM END ///
		
		/// CARBON START ///
		int caA = 10;
		
		makeRecipe(carbon, caAmat, "dustSulfur", new ItemStack(ModItems.powder_titanium), caA);
		makeRecipe(carbon, caAmat, "dustTitanium", new ItemStack(ModItems.powder_cobalt), caA);
		makeRecipe(carbon, caAmat, new ComparableStack(ModItems.powder_caesium), new ItemStack(ModItems.powder_lanthanium), caA);
		makeRecipe(carbon, caAmat, new ComparableStack(ModItems.powder_neodymium), new ItemStack(ModItems.powder_gold), caA);
		makeRecipe(carbon, caAmat, new ComparableStack(ModItems.nugget_mercury), new ItemStack(ModItems.powder_polonium), caA);
		makeRecipe(carbon, caAmat, new ComparableStack(ModItems.powder_astatine), new ItemStack(ModItems.powder_actinium), caA);
		makeRecipe(carbon, caAmat, new ComparableStack(ModItems.powder_australium), new ItemStack(ModItems.powder_verticium), caA);
		/// CARBON END ///
		
		/// COPPER START ///
		int coA = 15;
		
		makeRecipe(copper, coAmat, "dustBeryllium", new ItemStack(ModItems.powder_quartz), coA);
		makeRecipe(copper, coAmat, "dustCoal", new ItemStack(ModItems.powder_bromine), coA);
		makeRecipe(copper, coAmat, "dustTitanium", new ItemStack(ModItems.powder_strontium), coA);
		makeRecipe(copper, coAmat, "dustIron", new ItemStack(ModItems.powder_niobium), coA);
		makeRecipe(copper, coAmat, new ComparableStack(ModItems.powder_bromine), new ItemStack(ModItems.powder_iodine), coA);
		makeRecipe(copper, coAmat, new ComparableStack(ModItems.powder_strontium), new ItemStack(ModItems.powder_neodymium), coA);
		makeRecipe(copper, coAmat, new ComparableStack(ModItems.powder_niobium), new ItemStack(ModItems.powder_caesium), coA);
		makeRecipe(copper, coAmat, new ComparableStack(ModItems.powder_iodine), new ItemStack(ModItems.powder_polonium), coA);
		makeRecipe(copper, coAmat, new ComparableStack(ModItems.powder_caesium), new ItemStack(ModItems.powder_actinium), coA);
		makeRecipe(copper, coAmat, "dustGold", new ItemStack(ModItems.powder_uranium), coA);
		makeRecipe(copper, coAmat, new ComparableStack(ModItems.powder_verticium), new ItemStack(ModItems.powder_unobtainium), coA);
		/// COPPER END ///

		/// PLUTONIUM START ///
		int plA = 100;
		
		makeRecipe(plutonium, plAmat, "dustPhosphorus", new ItemStack(ModItems.powder_tennessine), plA);
		makeRecipe(plutonium, plAmat, "dustPlutonium", new ItemStack(ModItems.powder_tennessine), plA);
		makeRecipe(plutonium, plAmat, new ComparableStack(ModItems.powder_tennessine), new ItemStack(ModItems.powder_reiium), plA);
		makeRecipe(plutonium, plAmat, new ComparableStack(ModItems.pellet_charged), new ItemStack(ModItems.nugget_schrabidium), 1000);
		makeRecipe(plutonium, plAmat, new ComparableStack(ModItems.powder_unobtainium), new ItemStack(ModItems.powder_daffergon), plA);
		makeRecipe(plutonium, plAmat, new ComparableStack(ModItems.cell_antimatter), new ItemStack(ModItems.cell_anti_schrabidium), 0);
		/// PLUTONIUM END ///
		
		///TODO: fictional elements
	}
	
	private static void makeRecipe(HashMap<Object, ItemStack> map, HashMap<Object, Integer> aMap, Object in, ItemStack out, int amat) {
		map.put(in, out);
		aMap.put(in, amat);
	}
	
	public static Object[] getOutput(ItemStack stack, ItemStack box) {
		
		if(stack == null || stack.getItem() == null || box == null)
			return null;

		HashMap<Object, ItemStack> pool = null;
		HashMap<Object, Integer> aPool = null;
		
		if(box.getItem() == ModItems.part_lithium) {
			pool = lithium;
			aPool = liAmat;
		} else if(box.getItem() == ModItems.part_beryllium) {
			pool = beryllium;
			aPool = beAmat;
		} else if(box.getItem() == ModItems.part_carbon) {
			pool = carbon;
			aPool = caAmat;
		} else if(box.getItem() == ModItems.part_copper) {
			pool = copper;
			aPool = coAmat;
		} else if(box.getItem() == ModItems.part_plutonium) {
			pool = plutonium;
			aPool = plAmat;
		}
		
		if(pool == null)
			return null;
		
		ComparableStack comp = new ComparableStack(stack.getItem(), 1, stack.getItemDamage());
		
		if(pool.containsKey(comp))
			return new Object[] {pool.get(comp).copy(), aPool.get(comp)};
		
		String[] dictKeys = comp.getDictKeys();
		
		for(String key : dictKeys) {

			if(pool.containsKey(key))
				return new Object[] {pool.get(key).copy(), aPool.get(key)};
		}
		
		return null;
	}
	
	public static Map<Object[], Object> getRecipes() {
		
		Map<Object[], Object> recipes = new HashMap<Object[], Object>();

		addRecipes(recipes, lithium, ModItems.part_lithium);
		addRecipes(recipes, beryllium, ModItems.part_beryllium);
		addRecipes(recipes, carbon, ModItems.part_carbon);
		addRecipes(recipes, copper, ModItems.part_copper);
		addRecipes(recipes, plutonium, ModItems.part_plutonium);
		
		return recipes;
	}
	
	private static void addRecipes(Map<Object[], Object> recipes, HashMap<Object, ItemStack> map, Item part) {
		
		for(Entry<Object, ItemStack> entry : map.entrySet()) {
			
			if(entry.getKey() instanceof ComparableStack) {
				
				recipes.put(new ItemStack[] { new ItemStack(part), ((ComparableStack) entry.getKey()).toStack() }, entry.getValue());
				
			} else if(entry.getKey() instanceof String) {

				List<ItemStack> ores = OreDictionary.getOres((String) entry.getKey());
				
				for(ItemStack ore : ores) {
					recipes.put(new ItemStack[] { new ItemStack(part), ore }, entry.getValue());
				}
			}
		}
	}
}
