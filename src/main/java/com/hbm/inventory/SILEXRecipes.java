package com.hbm.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.util.WeightedRandomObject;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class SILEXRecipes {

	private static HashMap<Object, SILEXRecipe> recipes = new HashMap();
	private static HashMap<ComparableStack, ComparableStack> itemTranslation = new HashMap();
	private static HashMap<String, String> dictTranslation = new HashMap();
	
	public static void register() {

		itemTranslation.put(new ComparableStack(ModItems.fluid_icon, 1, FluidType.UF6.ordinal()), new ComparableStack(ModItems.ingot_uranium));
		dictTranslation.put("dustUranium", "ingotUranium");
		recipes.put("ingotUranium", new SILEXRecipe(900, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u235), 1))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u238), 8))
				);
		
		recipes.put(new ComparableStack(ModItems.ingot_pu_mix), new SILEXRecipe(900, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu239), 6))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu240), 3))
				);

		itemTranslation.put(new ComparableStack(ModItems.fluid_icon, 1, FluidType.PUF6.ordinal()), new ComparableStack(ModItems.ingot_plutonium));
		dictTranslation.put("dustPlutonium", "ingotPlutonium");
		recipes.put("ingotPlutonium", new SILEXRecipe(900, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu238), 3))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu239), 4))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu240), 2))
				);

		recipes.put(new ComparableStack(ModItems.ingot_schraranium), new SILEXRecipe(900, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_schrabidium), 4))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_uranium), 3))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_plutonium), 2))
				);

		itemTranslation.put(new ComparableStack(ModItems.powder_australium), new ComparableStack(ModItems.ingot_australium));
		recipes.put(new ComparableStack(ModItems.ingot_australium), new SILEXRecipe(900, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_australium_lesser), 5))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_australium_greater), 1))
				);
		
		recipes.put(new ComparableStack(ModItems.crystal_schraranium), new SILEXRecipe(900, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_schrabidium), 5))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_uranium), 2))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_plutonium), 2))
				);

		itemTranslation.put(new ComparableStack(ModItems.powder_lapis), new ComparableStack(Items.dye, 1, 4));
		recipes.put(new ComparableStack(Items.dye, 1, 4), new SILEXRecipe(100, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.sulfur), 4))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_aluminium), 3))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_cobalt), 3))
				);

		for(int i = 0; i < 5; i++) {
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_ueu, 1, i), new SILEXRecipe(600, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_uranium), 9 - 2 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_plutonium), 1 + 2 * i)) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_ueu, 1, i + 5), new SILEXRecipe(600, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_uranium), 8 - 2 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_plutonium), 1 + 2 * i)) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_balefire_gold, 1, i), new SILEXRecipe(600, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_au198), 9 - 2 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_balefire), 1 + 2 * i)) );
			
			recipes.put(new ComparableStack(ModItems.rbmk_pellet_balefire_gold, 1, i + 5), new SILEXRecipe(600, 100)
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_xe135_tiny), 1))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_au198), 8 - 2 * i))
					.addOut(new WeightedRandomObject(new ItemStack(ModItems.powder_balefire), 1 + 2 * i)) );
		}
		
		recipes.put(new ComparableStack(ModItems.fallout, 1), new SILEXRecipe(100, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.dust), 90))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_co60), 6))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_au198), 1))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_ra226), 3))
				);
	}
	
	public static SILEXRecipe getOutput(ItemStack stack) {
		
		if(stack == null || stack.getItem() == null)
			return null;
		
		ComparableStack comp = translateItem(stack);
		
		if(recipes.containsKey(comp))
			return recipes.get(comp);
		
		String[] dictKeys = comp.getDictKeys();
		
		for(String key : dictKeys) {
			
			String translation = translateDict(key);
			if(recipes.containsKey(translation))
				return recipes.get(translation);
		}
		
		return null;
	}
	
	public static ComparableStack translateItem(ItemStack stack) {
		ComparableStack orig = new ComparableStack(stack.getItem(), 1, stack.getItemDamage());
		ComparableStack translation = itemTranslation.get(orig);
		
		if(translation != null)
			return translation;
		
		return orig;
	}
	
	public static String translateDict(String key) {
		
		String translation = dictTranslation.get(key);
		
		if(translation != null)
			return translation;
		
		return key;
	}
	
	public static List<Object> getAllIngredients() {
		List<Object> ing = new ArrayList();
		
		for(Entry<Object, SILEXRecipe> entry : SILEXRecipes.recipes.entrySet()) {
			ing.add(entry.getKey());
		}
		for(Entry<ComparableStack, ComparableStack> entry : SILEXRecipes.itemTranslation.entrySet()) {
			ing.add(entry.getKey());
		}
		for(Entry<String, String> entry : SILEXRecipes.dictTranslation.entrySet()) {
			ing.add(entry.getKey());
		}
		
		return ing;
	}

	public static Map<Object, SILEXRecipe> getRecipes() {
		
		Map<Object, SILEXRecipe> recipes = new HashMap<Object, SILEXRecipe>();
		List<Object> ing = getAllIngredients();
		
		for(Object ingredient : ing) {
			
			if(ingredient instanceof String) {
				List<ItemStack> ingredients = OreDictionary.getOres((String)ingredient);
				if(ingredients.size() > 0) {
					SILEXRecipe output = getOutput(ingredients.get(0));
					if(output != null)
						recipes.put(ingredients, output);
				}
				
			} else if(ingredient instanceof ComparableStack) {
				SILEXRecipe output = getOutput(((ComparableStack) ingredient).toStack());
				if(output != null)
					recipes.put(((ComparableStack)ingredient).toStack(), output);
			}
		}
		
		return recipes;
	}
	
	public static class SILEXRecipe {
		
		public int fluidProduced;
		public int fluidConsumed;
		public List<WeightedRandomObject> outputs = new ArrayList();
		
		public SILEXRecipe(int fluidProduced, int fluidConsumed) {
			this.fluidProduced = fluidProduced;
			this.fluidConsumed = fluidConsumed;
		}
		
		public SILEXRecipe addOut(WeightedRandomObject entry) {
			outputs.add(entry);
			return this;
		}
	}
}
