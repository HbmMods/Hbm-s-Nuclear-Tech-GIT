package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.util.Tuple.Pair;

import net.minecraft.item.ItemStack;

public class CyclotronRecipes extends SerializableRecipe {
	
	public static HashMap<Pair<ComparableStack, AStack>, Pair<ItemStack, Integer>> recipes = new HashMap();

	@Override
	public void registerDefaults() {

		/// LITHIUM START ///
		int liA = 50;

		makeRecipe(new ComparableStack(ModItems.part_lithium), new OreDictStack("dustLithium"), new ItemStack(ModItems.powder_beryllium), liA);
		makeRecipe(new ComparableStack(ModItems.part_lithium), new OreDictStack("dustBeryllium"), new ItemStack(ModItems.powder_boron), liA);
		makeRecipe(new ComparableStack(ModItems.part_lithium), new OreDictStack("dustBoron"), new ItemStack(ModItems.powder_coal), liA);
		makeRecipe(new ComparableStack(ModItems.part_lithium), new OreDictStack("dustNetherQuartz"), new ItemStack(ModItems.powder_fire), liA);
		makeRecipe(new ComparableStack(ModItems.part_lithium), new OreDictStack("dustPhosphorus"), new ItemStack(ModItems.sulfur), liA);
		makeRecipe(new ComparableStack(ModItems.part_lithium), new OreDictStack("dustIron"), new ItemStack(ModItems.powder_cobalt), liA);
		makeRecipe(new ComparableStack(ModItems.part_lithium), new ComparableStack(ModItems.powder_strontium), new ItemStack(ModItems.powder_zirconium), liA);
		makeRecipe(new ComparableStack(ModItems.part_lithium), new OreDictStack("dustGold"), new ItemStack(ModItems.ingot_mercury), liA);
		makeRecipe(new ComparableStack(ModItems.part_lithium), new OreDictStack("dustPolonium"), new ItemStack(ModItems.powder_astatine), liA);
		makeRecipe(new ComparableStack(ModItems.part_lithium), new OreDictStack("dustLanthanium"), new ItemStack(ModItems.powder_cerium), liA);
		makeRecipe(new ComparableStack(ModItems.part_lithium), new OreDictStack("dustActinium"), new ItemStack(ModItems.powder_thorium), liA);
		makeRecipe(new ComparableStack(ModItems.part_lithium), new OreDictStack("dustUranium"), new ItemStack(ModItems.powder_neptunium), liA);
		makeRecipe(new ComparableStack(ModItems.part_lithium), new OreDictStack("dustNeptunium"), new ItemStack(ModItems.powder_plutonium), liA);
		makeRecipe(new ComparableStack(ModItems.part_lithium), new ComparableStack(ModItems.powder_reiium), new ItemStack(ModItems.powder_weidanium), liA);
		/// LITHIUM END ///

		/// BERYLLIUM START ///
		int beA = 25;

		makeRecipe(new ComparableStack(ModItems.part_beryllium), new OreDictStack("dustLithium"), new ItemStack(ModItems.powder_boron), beA);
		makeRecipe(new ComparableStack(ModItems.part_beryllium), new OreDictStack("dustNetherQuartz"), new ItemStack(ModItems.sulfur), beA);
		makeRecipe(new ComparableStack(ModItems.part_beryllium), new OreDictStack("dustTitanium"), new ItemStack(ModItems.powder_iron), beA);
		makeRecipe(new ComparableStack(ModItems.part_beryllium), new OreDictStack("dustCobalt"), new ItemStack(ModItems.powder_copper), beA);
		makeRecipe(new ComparableStack(ModItems.part_beryllium), new ComparableStack(ModItems.powder_strontium), new ItemStack(ModItems.powder_niobium), beA);
		makeRecipe(new ComparableStack(ModItems.part_beryllium), new ComparableStack(ModItems.powder_cerium), new ItemStack(ModItems.powder_neodymium), beA);
		makeRecipe(new ComparableStack(ModItems.part_beryllium), new OreDictStack("dustThorium"), new ItemStack(ModItems.powder_uranium), beA);
		makeRecipe(new ComparableStack(ModItems.part_beryllium), new ComparableStack(ModItems.powder_weidanium), new ItemStack(ModItems.powder_australium), beA);
		/// BERYLLIUM END ///
		
		/// CARBON START ///
		int caA = 10;

		makeRecipe(new ComparableStack(ModItems.part_carbon), new OreDictStack("dustBoron"), new ItemStack(ModItems.powder_aluminium), caA);
		makeRecipe(new ComparableStack(ModItems.part_carbon), new OreDictStack("dustSulfur"), new ItemStack(ModItems.powder_titanium), caA);
		makeRecipe(new ComparableStack(ModItems.part_carbon), new OreDictStack("dustTitanium"), new ItemStack(ModItems.powder_cobalt), caA);
		makeRecipe(new ComparableStack(ModItems.part_carbon), new ComparableStack(ModItems.powder_caesium), new ItemStack(ModItems.powder_lanthanium), caA);
		makeRecipe(new ComparableStack(ModItems.part_carbon), new ComparableStack(ModItems.powder_neodymium), new ItemStack(ModItems.powder_gold), caA);
		makeRecipe(new ComparableStack(ModItems.part_carbon), new ComparableStack(ModItems.ingot_mercury), new ItemStack(ModItems.powder_polonium), caA);
		makeRecipe(new ComparableStack(ModItems.part_carbon), new ComparableStack(ModItems.powder_lead), new ItemStack(ModItems.powder_ra226),caA);
		makeRecipe(new ComparableStack(ModItems.part_carbon), new ComparableStack(ModItems.powder_astatine), new ItemStack(ModItems.powder_actinium), caA);
		makeRecipe(new ComparableStack(ModItems.part_carbon), new ComparableStack(ModItems.powder_australium), new ItemStack(ModItems.powder_verticium), caA);
		/// CARBON END ///
		
		/// COPPER START ///
		int coA = 15;
		
		makeRecipe(new ComparableStack(ModItems.part_copper), new OreDictStack("dustBeryllium"), new ItemStack(ModItems.powder_quartz), coA);
		makeRecipe(new ComparableStack(ModItems.part_copper), new OreDictStack("dustCoal"), new ItemStack(ModItems.powder_bromine), coA);
		makeRecipe(new ComparableStack(ModItems.part_copper), new OreDictStack("dustTitanium"), new ItemStack(ModItems.powder_strontium), coA);
		makeRecipe(new ComparableStack(ModItems.part_copper), new OreDictStack("dustIron"), new ItemStack(ModItems.powder_niobium), coA);
		makeRecipe(new ComparableStack(ModItems.part_copper), new ComparableStack(ModItems.powder_bromine), new ItemStack(ModItems.powder_iodine), coA);
		makeRecipe(new ComparableStack(ModItems.part_copper), new ComparableStack(ModItems.powder_strontium), new ItemStack(ModItems.powder_neodymium), coA);
		makeRecipe(new ComparableStack(ModItems.part_copper), new ComparableStack(ModItems.powder_niobium), new ItemStack(ModItems.powder_caesium), coA);
		makeRecipe(new ComparableStack(ModItems.part_copper), new ComparableStack(ModItems.powder_iodine), new ItemStack(ModItems.powder_polonium), coA);
		makeRecipe(new ComparableStack(ModItems.part_copper), new ComparableStack(ModItems.powder_caesium), new ItemStack(ModItems.powder_actinium), coA);
		makeRecipe(new ComparableStack(ModItems.part_copper), new OreDictStack("dustGold"), new ItemStack(ModItems.powder_uranium), coA);
		makeRecipe(new ComparableStack(ModItems.part_copper), new ComparableStack(ModItems.powder_verticium), new ItemStack(ModItems.powder_unobtainium), coA);
		/// COPPER END ///

		/// PLUTONIUM START ///
		int plA = 100;
		
		makeRecipe(new ComparableStack(ModItems.part_plutonium), new OreDictStack("dustPhosphorus"), new ItemStack(ModItems.powder_tennessine), plA);
		makeRecipe(new ComparableStack(ModItems.part_plutonium), new OreDictStack("dustPlutonium"), new ItemStack(ModItems.powder_tennessine), plA);
		makeRecipe(new ComparableStack(ModItems.part_plutonium), new ComparableStack(ModItems.powder_tennessine), new ItemStack(ModItems.powder_reiium), plA);
		makeRecipe(new ComparableStack(ModItems.part_plutonium), new ComparableStack(ModItems.pellet_charged), new ItemStack(ModItems.nugget_schrabidium), 1000);
		makeRecipe(new ComparableStack(ModItems.part_plutonium), new ComparableStack(ModItems.powder_unobtainium), new ItemStack(ModItems.powder_daffergon), plA);
		makeRecipe(new ComparableStack(ModItems.part_plutonium), new ComparableStack(ModItems.cell_antimatter), new ItemStack(ModItems.cell_anti_schrabidium), 0);
		/// PLUTONIUM END ///
		
		///TODO: fictional elements
	}
	
	private static void makeRecipe(ComparableStack part, AStack in, ItemStack out, int amat) {
		recipes.put(new Pair(part, in), new Pair(out, amat));
	}
	
	public static Object[] getOutput(ItemStack stack, ItemStack box) {
		
		if(stack == null || stack.getItem() == null || box == null)
			return null;

		ComparableStack boxStack = new ComparableStack(box).makeSingular();
		ComparableStack comp = new ComparableStack(stack).makeSingular();
		
		//boo hoo we iterate over a hash map, cry me a river
		for(Entry<Pair<ComparableStack, AStack>, Pair<ItemStack, Integer>> entry : recipes.entrySet()) {
			
			if(entry.getKey().getKey().isApplicable(boxStack) && entry.getKey().getValue().isApplicable(comp)) {
				return new Object[] { entry.getValue().getKey(), entry.getValue().getValue() };
			}
		}
		
		//there's literally 0 reason why this doesn't work yet it refuses, fuck this
		
		/*Pair<ItemStack, Integer> output = recipes.get(new Pair(boxStack, comp));
		
		if(output != null) {
			return new Object[] { output.getKey().copy(), output.getValue() };
		}
		
		for(String name : ItemStackUtil.getOreDictNames(stack)) {
			OreDictStack ods = new OreDictStack(name);
			output = recipes.get(new Pair(new ComparableStack(ModItems.part_beryllium), new OreDictStack("dustCobalt")));
			
			if(output != null) {
				return new Object[] { output.getKey().copy(), output.getValue() };
			}
		}*/
		
		return null;
	}
	
	public static Map<Object[], Object> getRecipes() {
		
		Map<Object[], Object> map = new HashMap<Object[], Object>();
		
		for(Entry<Pair<ComparableStack, AStack>, Pair<ItemStack, Integer>> entry : recipes.entrySet()) {
			List<ItemStack> stack = entry.getKey().getValue().extractForNEI();
			
			for(ItemStack ingredient : stack) {
				map.put(new ItemStack[] { entry.getKey().getKey().toStack(), ingredient }, entry.getValue().getKey());
			}
		}
		
		return map;
	}

	@Override
	public String getFileName() {
		return "hbmCyclotron.json";
	}

	@Override
	public Object getRecipeObject() {
		return this.recipes;
	}

	@Override
	public void readRecipe(JsonElement recipe) {
		JsonArray particle = ((JsonObject)recipe).get("particle").getAsJsonArray();
		JsonArray input = ((JsonObject)recipe).get("input").getAsJsonArray();
		JsonArray output = ((JsonObject)recipe).get("output").getAsJsonArray();
		int antimatter = ((JsonObject)recipe).get("antimatter").getAsInt();
		ItemStack partStack = this.readItemStack(particle);
		AStack inStack = this.readAStack(input);
		ItemStack outStack = this.readItemStack(output);
		
		this.recipes.put(new Pair(new ComparableStack(partStack), inStack),  new Pair(outStack, antimatter));
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		try{
			Entry<Pair<ComparableStack, AStack>, Pair<ItemStack, Integer>> rec = (Entry<Pair<ComparableStack, AStack>, Pair<ItemStack, Integer>>) recipe;
			
			writer.name("particle");
			this.writeItemStack(rec.getKey().getKey().toStack(), writer);
			writer.name("input");
			this.writeAStack(rec.getKey().getValue(), writer);
			writer.name("output");
			this.writeItemStack(rec.getValue().getKey(), writer);
			writer.name("antimatter").value(rec.getValue().getValue());
			
		} catch(Exception ex) {
			MainRegistry.logger.error(ex);
			ex.printStackTrace();
		}
	}

	@Override
	public void deleteRecipes() {
		this.recipes.clear();
	}

	@Override
	public String getComment() {
		return "The particle item, while being an input, has to be defined as an item stack without ore dictionary support.";
	}
}
