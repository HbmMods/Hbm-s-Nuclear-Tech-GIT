package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.FluidStack;
import static com.hbm.inventory.OreDictManager.*;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ModItems;
import com.hbm.items.ItemEnums.EnumTarType;
import com.hbm.items.machine.ItemFluidIcon;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class OutgasserRecipes extends SerializableRecipe {
	
	public static Map<AStack, OutgasserRecipe> recipes = new HashMap();

	@Override
	public void registerDefaults() {

		/* lithium to tritium */
		recipes.put(new OreDictStack(LI.block()),		new OutgasserRecipe(null, new FluidStack(Fluids.TRITIUM, 10_000)));
		recipes.put(new OreDictStack(LI.ingot()),		new OutgasserRecipe(null, new FluidStack(Fluids.TRITIUM, 1_000)));
		recipes.put(new OreDictStack(LI.dust()),		new OutgasserRecipe(null, new FluidStack(Fluids.TRITIUM, 1_000)));
		recipes.put(new OreDictStack(LI.dustTiny()),	new OutgasserRecipe(null, new FluidStack(Fluids.TRITIUM, 100)));

		/* gold to gold-198 */
		recipes.put(new OreDictStack(GOLD.ingot()),		new OutgasserRecipe(new ItemStack(ModItems.ingot_au198), null));
		recipes.put(new OreDictStack(GOLD.nugget()),	new OutgasserRecipe(new ItemStack(ModItems.nugget_au198), null));
		recipes.put(new OreDictStack(GOLD.dust()),		new OutgasserRecipe(new ItemStack(ModItems.powder_au198), null));

		/* thorium to thorium fuel */
		recipes.put(new OreDictStack(TH232.ingot()),	new OutgasserRecipe(new ItemStack(ModItems.ingot_thorium_fuel), null));
		recipes.put(new OreDictStack(TH232.nugget()),	new OutgasserRecipe(new ItemStack(ModItems.nugget_thorium_fuel), null));
		recipes.put(new OreDictStack(TH232.billet()),	new OutgasserRecipe(new ItemStack(ModItems.billet_thorium_fuel), null));

		/* mushrooms to glowing mushrooms */
		recipes.put(new ComparableStack(Blocks.brown_mushroom),	new OutgasserRecipe(new ItemStack(ModBlocks.mush), null));
		recipes.put(new ComparableStack(Blocks.red_mushroom),	new OutgasserRecipe(new ItemStack(ModBlocks.mush), null));
		recipes.put(new ComparableStack(Items.mushroom_stew),	new OutgasserRecipe(new ItemStack(ModItems.glowing_stew), null));

		recipes.put(new OreDictStack(COAL.gem()),		new OutgasserRecipe(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.COAL, 1), new FluidStack(Fluids.SYNGAS, 50)));
		recipes.put(new OreDictStack(COAL.dust()),		new OutgasserRecipe(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.COAL, 1), new FluidStack(Fluids.SYNGAS, 50)));
		recipes.put(new OreDictStack(COAL.block()),		new OutgasserRecipe(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.COAL, 9), new FluidStack(Fluids.SYNGAS, 500)));
		
		recipes.put(new OreDictStack(PVC.ingot()),		new OutgasserRecipe(new ItemStack(ModItems.ingot_c4), new FluidStack(Fluids.COLLOID, 250)));

		recipes.put(new ComparableStack(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.COAL)),	new OutgasserRecipe(null, new FluidStack(Fluids.COALOIL, 100)));
		recipes.put(new ComparableStack(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.WAX)),	new OutgasserRecipe(null, new FluidStack(Fluids.RADIOSOLVENT, 100)));
	}
	
	public static OutgasserRecipe getOutput(ItemStack input) {
		
		ComparableStack comp = new ComparableStack(input).makeSingular();
		
		if(recipes.containsKey(comp)) {
			return recipes.get(comp);
		}
		
		String[] dictKeys = comp.getDictKeys();
		
		for(String key : dictKeys) {
			OreDictStack dict = new OreDictStack(key);
			if(recipes.containsKey(dict)) {
				return recipes.get(dict);
			}
		}
		
		return null;
	}

	public static HashMap getRecipes() {
		
		HashMap<Object, Object[]> recipes = new HashMap<Object, Object[]>();
		
		for(Entry<AStack, OutgasserRecipe> entry : OutgasserRecipes.recipes.entrySet()) {
			
			AStack input = entry.getKey();
			ItemStack solidOutput = entry.getValue().solidOutput;
			FluidStack fluidOutput = entry.getValue().liquidOutput;

			if(solidOutput != null && fluidOutput != null) recipes.put(input, new Object[] {solidOutput, ItemFluidIcon.make(fluidOutput)});
			if(solidOutput != null && fluidOutput == null) recipes.put(input, new Object[] {solidOutput});
			if(solidOutput == null && fluidOutput != null) recipes.put(input, new Object[] {ItemFluidIcon.make(fluidOutput)});
		}
		
		return recipes;
	}

	@Override
	public String getFileName() {
		return "hbmIrradiation.json";
	}

	@Override
	public Object getRecipeObject() {
		return recipes;
	}

	@Override
	public void readRecipe(JsonElement recipe) {
		JsonObject obj = (JsonObject) recipe;
		
		AStack input = this.readAStack(obj.get("input").getAsJsonArray());
		ItemStack solidOutput = null;
		FluidStack fluidOutput = null;
		
		if(obj.has("solidOutput")) solidOutput = this.readItemStack(obj.get("solidOutput").getAsJsonArray());
		if(obj.has("fluidOutput")) fluidOutput = this.readFluidStack(obj.get("fluidOutput").getAsJsonArray());
		
		OutgasserRecipe or = new OutgasserRecipe(solidOutput, fluidOutput);
		if(obj.has("fusionOnly") && obj.get("fusionOnly").getAsBoolean()) or.fusionOnly();
		
		if(solidOutput != null || fluidOutput != null) {
			this.recipes.put(input, or);
		}
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		Entry<AStack, OutgasserRecipe> rec = (Entry<AStack, OutgasserRecipe>) recipe;
		
		writer.name("input");
		this.writeAStack(rec.getKey(), writer);
		
		if(rec.getValue().solidOutput != null) {
			writer.name("solidOutput");
			this.writeItemStack(rec.getValue().solidOutput, writer);
		}
		
		if(rec.getValue().liquidOutput != null) {
			writer.name("fluidOutput");
			this.writeFluidStack(rec.getValue().liquidOutput, writer);
		}
		
		writer.name("fusionOnly").value(rec.getValue().fusionOnly);
	}

	@Override
	public void deleteRecipes() {
		recipes.clear();
	}
	
	public static class OutgasserRecipe {
		
		public ItemStack solidOutput;
		public FluidStack liquidOutput;
		public boolean fusionOnly = false;
		
		public OutgasserRecipe(ItemStack solid, FluidStack liquid) {
			this.solidOutput = solid;
			this.liquidOutput = liquid;
		}
		
		public OutgasserRecipe fusionOnly() {
			this.fusionOnly = true;
			return this;
		}
	}
}
