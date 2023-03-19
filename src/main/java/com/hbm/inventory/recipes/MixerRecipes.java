package com.hbm.inventory.recipes;

import static com.hbm.inventory.OreDictManager.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIcon;

public class MixerRecipes extends SerializableRecipe {

	public static HashMap<FluidType, MixerRecipe> recipes = new HashMap();
	
	@Override
	public void registerDefaults() {
		recipes.put(Fluids.COOLANT, new MixerRecipe(2_000, 50).setStack1(new FluidStack(Fluids.WATER, 1_800)).setSolid(new OreDictStack(KNO.dust())));
		recipes.put(Fluids.CRYOGEL, new MixerRecipe(2_000, 50).setStack1(new FluidStack(Fluids.COOLANT, 1_800)).setSolid(new ComparableStack(ModItems.powder_ice)));
		recipes.put(Fluids.NITAN, new MixerRecipe(1_000, 50).setStack1(new FluidStack(Fluids.KEROSENE, 600)).setStack2(new FluidStack(Fluids.MERCURY, 200)).setSolid(new ComparableStack(ModItems.powder_nitan_mix)));
		recipes.put(Fluids.FRACKSOL, new MixerRecipe(1_000, 20).setStack1(new FluidStack(Fluids.SULFURIC_ACID, 900)).setStack2(new FluidStack(Fluids.PETROLEUM, 100)));
		recipes.put(Fluids.ENDERJUICE, new MixerRecipe(100, 100).setStack1(new FluidStack(Fluids.XPJUICE, 500)).setSolid(new OreDictStack(DIAMOND.dust())));
		recipes.put(Fluids.SALIENT, new MixerRecipe(1000, 20).setStack1(new FluidStack(Fluids.SEEDSLURRY, 500)).setStack2(new FluidStack(Fluids.BLOOD, 500)));
		recipes.put(Fluids.COLLOID, new MixerRecipe(500, 20).setStack1(new FluidStack(Fluids.WATER, 500)).setSolid(new ComparableStack(ModItems.dust)));
		recipes.put(Fluids.PHOSGENE, new MixerRecipe(1000, 20).setStack1(new FluidStack(Fluids.UNSATURATEDS, 500)).setStack2(new FluidStack(Fluids.CHLORINE, 500)));
		recipes.put(Fluids.MUSTARDGAS, new MixerRecipe(1000, 20).setStack1(new FluidStack(Fluids.REFORMGAS, 750)).setStack2(new FluidStack(Fluids.CHLORINE, 250)).setSolid(new OreDictStack(S.dust())));

		recipes.put(Fluids.SOLVENT, new MixerRecipe(1000, 50).setStack1(new FluidStack(Fluids.NAPHTHA, 500)).setStack2(new FluidStack(Fluids.AROMATICS, 500)));
		recipes.put(Fluids.SULFURIC_ACID, new MixerRecipe(500, 50).setStack1(new FluidStack(Fluids.ACID, 800)).setSolid(new OreDictStack(S.dust())));
		recipes.put(Fluids.NITRIC_ACID, new MixerRecipe(500, 50).setStack1(new FluidStack(Fluids.SULFURIC_ACID, 500)).setSolid(new OreDictStack(KNO.dust())));
		recipes.put(Fluids.RADIOSOLVENT, new MixerRecipe(1000, 50).setStack1(new FluidStack(Fluids.REFORMGAS, 750)).setStack2(new FluidStack(Fluids.CHLORINE, 250)));
		recipes.put(Fluids.SCHRABIDIC, new MixerRecipe(16_000, 100).setStack1(new FluidStack(Fluids.SAS3, 8_000)).setStack2(new FluidStack(Fluids.ACID, 6_000)).setSolid(new ComparableStack(ModItems.pellet_charged)));
		
		recipes.put(Fluids.LUBRICANT, new MixerRecipe(1_000, 20).setStack1(new FluidStack(Fluids.HEATINGOIL, 500)).setStack2(new FluidStack(Fluids.UNSATURATEDS, 500)));
		recipes.put(Fluids.PETROIL, new MixerRecipe(1_000, 30).setStack1(new FluidStack(Fluids.RECLAIMED, 800)).setStack2(new FluidStack(Fluids.LUBRICANT, 200)));

		recipes.put(Fluids.SYNGAS, new MixerRecipe(1_000, 50).setStack1(new FluidStack(Fluids.COALOIL, 500)).setStack2(new FluidStack(Fluids.STEAM, 500)));
		recipes.put(Fluids.OXYHYDROGEN, new MixerRecipe(1_000, 50).setStack1(new FluidStack(Fluids.HYDROGEN, 500)).setStack2(new FluidStack(Fluids.OXYGEN, 500)));

		recipes.put(Fluids.PETROIL_LEADED, new MixerRecipe(1_000, 40).setStack1(new FluidStack(Fluids.PETROIL, 800)).setSolid(new ComparableStack(ModItems.antiknock)));
		recipes.put(Fluids.GASOLINE_LEADED, new MixerRecipe(1_000, 40).setStack1(new FluidStack(Fluids.GASOLINE, 800)).setSolid(new ComparableStack(ModItems.antiknock)));
		recipes.put(Fluids.COALGAS_LEADED, new MixerRecipe(1_000, 40).setStack1(new FluidStack(Fluids.COALGAS, 800)).setSolid(new ComparableStack(ModItems.antiknock)));

		recipes.put(Fluids.DIESEL_REFORM, new MixerRecipe(1_000, 50).setStack1(new FluidStack(Fluids.DIESEL, 900)).setStack2(new FluidStack(Fluids.REFORMATE, 100)));
		recipes.put(Fluids.DIESEL_CRACK_REFORM, new MixerRecipe(1_000, 50).setStack1(new FluidStack(Fluids.DIESEL_CRACK, 900)).setStack2(new FluidStack(Fluids.REFORMATE, 100)));
		recipes.put(Fluids.KEROSENE_REFORM, new MixerRecipe(1_000, 50).setStack1(new FluidStack(Fluids.KEROSENE, 900)).setStack2(new FluidStack(Fluids.REFORMATE, 100)));
	}
	
	public static MixerRecipe getOutput(FluidType type) {
		return recipes.get(type);
	}
	
	@Override
	public String getFileName() {
		return "hbmMixer.json";
	}
	
	@Override
	public Object getRecipeObject() {
		return recipes;
	}

	@Override
	public void deleteRecipes() {
		recipes.clear();
	}
	
	@Override
	public void readRecipe(JsonElement recipe) {
		JsonObject obj = (JsonObject) recipe;
		
		FluidStack output = this.readFluidStack(obj.get("output").getAsJsonArray());
		MixerRecipe mix = new MixerRecipe(output.fill, obj.get("duration").getAsInt());

		if(obj.has("input1")) mix.setStack1(this.readFluidStack(obj.get("input1").getAsJsonArray()));
		if(obj.has("input2")) mix.setStack2(this.readFluidStack(obj.get("input2").getAsJsonArray()));
		if(obj.has("solidInput")) mix.setSolid(this.readAStack(obj.get("solidInput").getAsJsonArray()));
		
		recipes.put(output.type, mix);
	}
	
	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		Entry<FluidType, MixerRecipe> rec = (Entry<FluidType, MixerRecipe>) recipe;
		MixerRecipe mix = rec.getValue();
		FluidStack output = new FluidStack(rec.getKey(), mix.output);
		
		writer.name("duration").value(mix.processTime);
		writer.name("output");
		this.writeFluidStack(output, writer);
		
		if(mix.input1 != null) { writer.name("input1"); this.writeFluidStack(mix.input1, writer); }
		if(mix.input2 != null) { writer.name("input2"); this.writeFluidStack(mix.input2, writer); }
		if(mix.solidInput != null) { writer.name("solidInput"); this.writeAStack(mix.solidInput, writer); }
	}

	public static HashMap getRecipes() {
		
		HashMap<Object[], Object> recipes = new HashMap<Object[], Object>();
		
		for(Entry<FluidType, MixerRecipe> entry : MixerRecipes.recipes.entrySet()) {
			
			FluidType type = entry.getKey();
			MixerRecipe recipe = entry.getValue();
			FluidStack output = new FluidStack(type, recipe.output);

			List<Object> objects = new ArrayList();
			if(recipe.input1 != null) objects.add(ItemFluidIcon.make(recipe.input1));
			if(recipe.input2 != null) objects.add(ItemFluidIcon.make(recipe.input2));
			if(recipe.solidInput != null) objects.add(recipe.solidInput);
			
			recipes.put(objects.toArray(), ItemFluidIcon.make(output));
		}
		
		return recipes;
	}
	
	public static class MixerRecipe {
		public FluidStack input1;
		public FluidStack input2;
		public AStack solidInput;
		public int processTime;
		public int output;
		
		protected MixerRecipe(int output, int processTime) {
			this.output = output;
			this.processTime = processTime;
		}

		protected MixerRecipe setStack1(FluidStack stack) { input1 = stack; return this; }
		protected MixerRecipe setStack2(FluidStack stack) { input2 = stack; return this; }
		protected MixerRecipe setSolid(AStack stack) { solidInput = stack; return this; }
	}
}
