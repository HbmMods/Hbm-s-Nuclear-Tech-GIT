package com.hbm.inventory.recipes;

import static com.hbm.inventory.OreDictManager.KNO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.OreDictManager;
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
		recipes.put(Fluids.FRACKSOL, new MixerRecipe(1_000, 20).setStack1(new FluidStack(Fluids.WATER, 1_000)).setStack2(new FluidStack(Fluids.PETROLEUM, 100)).setSolid(new OreDictStack(OreDictManager.S.dust())));
		recipes.put(Fluids.ENDERJUICE, new MixerRecipe(100, 100).setStack1(new FluidStack(Fluids.XPJUICE, 500)).setSolid(new OreDictStack(OreDictManager.DIAMOND.dust())));

		recipes.put(Fluids.SOLVENT, new MixerRecipe(1000, 50).setStack1(new FluidStack(Fluids.NAPHTHA, 500)).setStack2(new FluidStack(Fluids.AROMATICS, 500)));
		recipes.put(Fluids.SULFURIC_ACID, new MixerRecipe(500, 50).setStack1(new FluidStack(Fluids.ACID, 800)).setSolid(new OreDictStack(OreDictManager.S.dust())));
		recipes.put(Fluids.NITRIC_ACID, new MixerRecipe(500, 50).setStack1(new FluidStack(Fluids.SULFURIC_ACID, 500)).setSolid(new OreDictStack(OreDictManager.KNO.dust())));
		recipes.put(Fluids.SCHRABIDIC, new MixerRecipe(16_000, 100).setStack1(new FluidStack(Fluids.SAS3, 8_000)).setStack2(new FluidStack(Fluids.ACID, 6_000)).setSolid(new ComparableStack(ModItems.pellet_charged)));
		
		recipes.put(Fluids.LUBRICANT, new MixerRecipe(1_000, 20).setStack1(new FluidStack(Fluids.HEATINGOIL, 500)).setStack2(new FluidStack(Fluids.UNSATURATEDS, 500)));
		recipes.put(Fluids.PETROIL, new MixerRecipe(1_000, 30).setStack1(new FluidStack(Fluids.RECLAIMED, 800)).setStack2(new FluidStack(Fluids.LUBRICANT, 200)));

		recipes.put(Fluids.PETROIL_LEADED, new MixerRecipe(1_000, 40).setStack1(new FluidStack(Fluids.PETROIL, 800)).setSolid(new ComparableStack(ModItems.antiknock)));
		recipes.put(Fluids.GASOLINE_LEADED, new MixerRecipe(1_000, 40).setStack1(new FluidStack(Fluids.GASOLINE, 800)).setSolid(new ComparableStack(ModItems.antiknock)));
		recipes.put(Fluids.COALGAS_LEADED, new MixerRecipe(1_000, 40).setStack1(new FluidStack(Fluids.COALGAS, 800)).setSolid(new ComparableStack(ModItems.antiknock)));
	}
	
	public static MixerRecipe getOutput(FluidType type) {
		return recipes.get(type);
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
		AStack inputItems=new ComparableStack(ModItems.antiknock);
		FluidStack inputFluid1=new FluidStack(Fluids.WATER, 0),inputFluid2=new FluidStack(Fluids.WATER, 0),outputFluid;
		if(obj.has("inputItems"))
		{inputItems = this.readAStack(obj.get("inputItems").getAsJsonArray());}
		if(obj.has("inputFluid1"))
		{inputFluid1 = this.readFluidStack(obj.get("inputFluid1").getAsJsonArray());}
		if(obj.has("inputFluid2"))
		{inputFluid2 = this.readFluidStack(obj.get("inputFluid2").getAsJsonArray());}
		 outputFluid = this.readFluidStack(obj.get("outputFluid").getAsJsonArray());
		int duration = obj.get("duration").getAsInt();

			if(obj.has("inputItems")&&obj.has("inputFluid1")&&obj.has("inputFluid2"))//full
			   {if(inputItems instanceof ComparableStack) {
				recipes.put(outputFluid.type, 
						new MixerRecipe(outputFluid.fill, duration).
						setStack1(inputFluid1).
						setStack2(inputFluid2).
						setSolid(((ComparableStack) inputItems).makeSingular()));
			}
			    else if(inputItems instanceof OreDictStack){
				recipes.put(outputFluid.type, 
						new MixerRecipe(outputFluid.fill, duration).
						setStack1(inputFluid1).
						setStack2(inputFluid2).
						setSolid(((OreDictStack) inputItems).copy()));}
					}
			 if(!obj.has("inputItems")&&obj.has("inputFluid1")&&obj.has("inputFluid2"))//just no item
			    {recipes.put(outputFluid.type, 
			    new MixerRecipe(outputFluid.fill, duration).
			    setStack1(inputFluid1).
			    setStack2(inputFluid2));}
			else if(!obj.has("inputItems")&&obj.has("inputFluid1")&&!obj.has("inputFluid2"))//just fluid1
				{recipes.put(outputFluid.type, 
						new MixerRecipe(outputFluid.fill, duration).
						setStack1(inputFluid1));}
            else if(obj.has("inputItems")&&obj.has("inputFluid1")&&!obj.has("inputFluid2")){//just no fluid2{
					if(inputItems instanceof ComparableStack) {
					recipes.put(outputFluid.type, 
							new MixerRecipe(outputFluid.fill, duration).
							setStack1(inputFluid1).
							setSolid(((ComparableStack) inputItems).makeSingular()));
				    }
				    else if(inputItems instanceof OreDictStack){
					recipes.put(outputFluid.type, 
							new MixerRecipe(outputFluid.fill, duration).
							setStack1(inputFluid1).
							setSolid(((OreDictStack) inputItems).copy()));}
				}

		}
	

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		Entry<FluidType, MixerRecipe> rec = (Entry<FluidType, MixerRecipe>) recipe;
		FluidStack key = new FluidStack(rec.getKey(), rec.getValue().output);
		writer.name("duration").value(rec.getValue().processTime);
		writer.name("outputFluid");
		this.writeFluidStack(key, writer);
		if(rec.getValue().solidInput!=null){
			    writer.name("inputItems");
		        if(rec.getValue().solidInput instanceof OreDictStack) {
			    this.writeAStack((OreDictStack) rec.getValue().solidInput, writer);
		        } else if(rec.getValue().solidInput instanceof ComparableStack) {
			    this.writeAStack((ComparableStack) rec.getValue().solidInput,writer);
		        }
		}
		if(rec.getValue().input1!=null){
			    writer.name("inputFluid1");
		        this.writeFluidStack(rec.getValue().input1, writer);
			}
		if(rec.getValue().input2!=null){
			    writer.name("inputFluid2");
		        this.writeFluidStack(rec.getValue().input2, writer);
			}
		
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
