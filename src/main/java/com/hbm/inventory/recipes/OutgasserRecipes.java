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
import com.hbm.util.Tuple.Triplet;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class OutgasserRecipes extends SerializableRecipe {
	
	public static Map<AStack, Triplet<ItemStack, FluidStack, Long>> recipes = new HashMap();

	@Override
	public void registerDefaults() {

		/* lithium to tritium */
		recipes.put(new OreDictStack(LI.block()),		new Triplet(null, new FluidStack(Fluids.TRITIUM, 10000), 10000L));
		recipes.put(new OreDictStack(LI.ingot()),		new Triplet(null, new FluidStack(Fluids.TRITIUM, 1000), 10000L));
		recipes.put(new OreDictStack(LI.dust()),		new Triplet(null, new FluidStack(Fluids.TRITIUM, 1000), 10000L));
		recipes.put(new OreDictStack(LI.dustTiny()),	new Triplet(null, new FluidStack(Fluids.TRITIUM, 100), 10000L));

		/* gold to gold-198 */
		recipes.put(new OreDictStack(GOLD.ingot()),		new Triplet(new ItemStack(ModItems.ingot_au198), null, 10000L));
		recipes.put(new OreDictStack(GOLD.nugget()),	new Triplet(new ItemStack(ModItems.nugget_au198), null, 10000L));
		recipes.put(new OreDictStack(GOLD.dust()),		new Triplet(new ItemStack(ModItems.powder_au198), null, 10000L));

		/* thorium to thorium fuel */
		recipes.put(new OreDictStack(TH232.ingot()),	new Triplet(new ItemStack(ModItems.ingot_thorium_fuel), null, 10000L));
		recipes.put(new OreDictStack(TH232.nugget()),	new Triplet(new ItemStack(ModItems.nugget_thorium_fuel), null, 10000L));
		recipes.put(new OreDictStack(TH232.billet()),	new Triplet(new ItemStack(ModItems.billet_thorium_fuel), null, 10000L));

		/* mushrooms to glowing mushrooms */
		recipes.put(new ComparableStack(Blocks.brown_mushroom),	new Triplet(new ItemStack(ModBlocks.mush), null, 10000L));
		recipes.put(new ComparableStack(Blocks.red_mushroom),	new Triplet(new ItemStack(ModBlocks.mush), null, 10000L));
		recipes.put(new ComparableStack(Items.mushroom_stew),	new Triplet(new ItemStack(ModItems.glowing_stew), null, 10000L));

		recipes.put(new OreDictStack(COAL.gem()),		new Triplet(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.COAL, 1), new FluidStack(Fluids.SYNGAS, 50), 10000L));
		recipes.put(new OreDictStack(COAL.dust()),		new Triplet(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.COAL, 1), new FluidStack(Fluids.SYNGAS, 50), 10000L));
		recipes.put(new OreDictStack(COAL.block()),		new Triplet(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.COAL, 9), new FluidStack(Fluids.SYNGAS, 500), 10000L));
		
		recipes.put(new OreDictStack(PVC.ingot()),		new Triplet(new ItemStack(ModItems.ingot_c4), new FluidStack(Fluids.COLLOID, 250), 10000L));

		recipes.put(new ComparableStack(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.COAL)),	new Triplet(null, new FluidStack(Fluids.COALOIL, 100), 10000L));
		recipes.put(new ComparableStack(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.WAX)),	new Triplet(null, new FluidStack(Fluids.RADIOSOLVENT, 100), 10000L));

		recipes.put(new ComparableStack(ModItems.book_of_),	new Triplet(new ItemStack(ModItems.book_lemegeton), null, 34560000000L));
	}
	
	public static Triplet<ItemStack, FluidStack, Long> getOutput(ItemStack input) {
		
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
		
		for(Entry<AStack, Triplet<ItemStack, FluidStack, Long>> entry : OutgasserRecipes.recipes.entrySet()) {
			
			AStack input = entry.getKey();
			ItemStack solidOutput = entry.getValue().getX();
			FluidStack fluidOutput = entry.getValue().getY();
			Long fluxNeeded = entry.getValue().getZ();

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
		Long fluxNeeded = 10000L;
		
		if(obj.has("solidOutput")) {
			solidOutput = this.readItemStack(obj.get("solidOutput").getAsJsonArray());
		}
		
		if(obj.has("fluidOutput")) {
			fluidOutput = this.readFluidStack(obj.get("fluidOutput").getAsJsonArray());
		}

		if(obj.has("fluxNeeded")) {
			fluxNeeded = this.readValue(obj.get("fluxNeeded").getAsJsonArray());
			if(fluxNeeded == 0) fluxNeeded = 10000L;
		}

		if(solidOutput != null || fluidOutput != null) {
			this.recipes.put(input, new Triplet(solidOutput, fluidOutput, fluxNeeded));
		}
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		Entry<AStack, Triplet<ItemStack, FluidStack, Long>> rec = (Entry<AStack, Triplet<ItemStack, FluidStack, Long>>) recipe;
		
		writer.name("input");
		this.writeAStack(rec.getKey(), writer);
		
		if(rec.getValue().getX() != null) {
			writer.name("solidOutput");
			this.writeItemStack(rec.getValue().getX(), writer);
		}
		
		if(rec.getValue().getY() != null) {
			writer.name("fluidOutput");
			this.writeFluidStack(rec.getValue().getY(), writer);
		}

		if(rec.getValue().getZ() != null) {
			writer.name("fluxNeeded");
			this.writeValue(rec.getValue().getZ(), writer);
		}
	}

	@Override
	public void deleteRecipes() {
		recipes.clear();
	}
}
