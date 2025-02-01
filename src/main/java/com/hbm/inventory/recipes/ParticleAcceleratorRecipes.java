package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ModItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ParticleAcceleratorRecipes extends SerializableRecipe {
	
	public static final List<ParticleAcceleratorRecipe> recipes = new ArrayList();
	
	@Override
	public void registerDefaults() {

		recipes.add(new ParticleAcceleratorRecipe(
				new ComparableStack(ModItems.particle_hydrogen),
				new ComparableStack(ModItems.particle_copper),
				900,
				new ItemStack(ModItems.particle_aproton),
				new ItemStack(ModItems.particle_aelectron)
				));
		recipes.add(new ParticleAcceleratorRecipe(
				new ComparableStack(ModItems.particle_amat),
				new ComparableStack(ModItems.particle_amat),
				900,
				new ItemStack(ModItems.particle_aschrab),
				new ItemStack(ModItems.particle_empty)
				));
		recipes.add(new ParticleAcceleratorRecipe(
				new ComparableStack(ModItems.particle_aschrab),
				new ComparableStack(ModItems.particle_aschrab),
				100000,
				new ItemStack(ModItems.particle_dark),
				new ItemStack(ModItems.particle_empty)
				));
		recipes.add(new ParticleAcceleratorRecipe(
				new ComparableStack(ModItems.particle_hydrogen),
				new ComparableStack(ModItems.particle_amat),
				2000,
				new ItemStack(ModItems.particle_muon),
				new ItemStack(ModItems.particle_empty)
				));
		recipes.add(new ParticleAcceleratorRecipe(
				new ComparableStack(ModItems.particle_hydrogen),
				new ComparableStack(ModItems.particle_lead),
				5000,
				new ItemStack(ModItems.particle_higgs),
				new ItemStack(ModItems.particle_empty)
				));
		recipes.add(new ParticleAcceleratorRecipe(
				new ComparableStack(ModItems.particle_muon),
				new ComparableStack(ModItems.particle_higgs),
				2000,
				new ItemStack(ModItems.particle_tachyon),
				new ItemStack(ModItems.particle_empty)
				));
		recipes.add(new ParticleAcceleratorRecipe(
				new ComparableStack(ModItems.particle_muon),
				new ComparableStack(ModItems.particle_dark),
				100000,
				new ItemStack(ModItems.particle_strange),
				new ItemStack(ModItems.particle_empty)
				));
		recipes.add(new ParticleAcceleratorRecipe(
				new ComparableStack(ModItems.particle_strange),
				new ComparableStack(ModItems.powder_magic),
				500000,
				new ItemStack(ModItems.particle_sparkticle),
				new ItemStack(ModItems.dust)
				));
		recipes.add(new ParticleAcceleratorRecipe(
				new ComparableStack(ModItems.particle_sparkticle),
				new ComparableStack(ModItems.particle_higgs),
				1000000,
				new ItemStack(ModItems.particle_digamma),
				new ItemStack(ModItems.particle_empty)
				));
		recipes.add(new ParticleAcceleratorRecipe(
				new ComparableStack(Items.chicken),
				new ComparableStack(Items.chicken),
				100,
				new ItemStack(ModItems.nugget),
				new ItemStack(ModItems.nugget)
				));
		recipes.add(new ParticleAcceleratorRecipe(
				new ComparableStack(Blocks.sand),
				new ComparableStack(Blocks.sand),
				10,
				new ItemStack(ModItems.static_sandwich),
				new ItemStack(ModItems.static_sandwich)
				));
	}
	
	public ParticleAcceleratorRecipe getOutput(ItemStack input1, ItemStack input2, int momentum) {
		
		for(ParticleAcceleratorRecipe recipe : recipes) {
			
			if(momentum >= recipe.momentum &&
					((recipe.input1.matchesRecipe(input1, true) && recipe.input2.matchesRecipe(input2, true)) ||
							(recipe.input1.matchesRecipe(input2, true) && recipe.input2.matchesRecipe(input1, true)))) {
				return recipe;
			}
		}
		
		return null;
	}

	public static class ParticleAcceleratorRecipe {
		public AStack input1;
		public AStack input2;
		public int momentum;
		public ItemStack output1;
		public ItemStack output2;
		
		public ParticleAcceleratorRecipe(AStack in1, AStack in2, int momentum, ItemStack out1, ItemStack out2) {
			this.input1 = in1;
			this.input2 = in2;
			this.momentum = momentum;
			this.output1 = out1;
			this.output2 = out2;
		}
	}

	@Override
	public String getFileName() {
		return "hbmParticleAccelerator.json";
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
		int momentum = obj.get("momentum").getAsInt();
		AStack[] in = this.readAStackArray(obj.get("inputs").getAsJsonArray());
		ItemStack[] out = this.readItemStackArray(obj.get("outputs").getAsJsonArray());
		
		this.recipes.add(new ParticleAcceleratorRecipe(
				in[0],
				in[1],
				momentum,
				out[0],
				out[1]
				));
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		ParticleAcceleratorRecipe rec = (ParticleAcceleratorRecipe) recipe;
		
		writer.name("momentum").value(rec.momentum);
		
		writer.name("inputs").beginArray();
		this.writeAStack(rec.input1, writer);
		this.writeAStack(rec.input2, writer);
		writer.endArray();
		
		writer.name("outputs").beginArray();
		this.writeItemStack(rec.output1, writer);
		this.writeItemStack(rec.output2, writer);
		writer.endArray();
	}
}
