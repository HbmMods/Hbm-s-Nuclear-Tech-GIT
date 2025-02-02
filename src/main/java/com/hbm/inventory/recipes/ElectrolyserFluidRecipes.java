package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIcon;

import net.minecraft.item.ItemStack;

public class ElectrolyserFluidRecipes extends SerializableRecipe {

	public static HashMap<FluidType, ElectrolysisRecipe> recipes = new HashMap();

	@Override
	public void registerDefaults() {
		recipes.put(Fluids.WATER, new ElectrolysisRecipe(2_000, new FluidStack(Fluids.HYDROGEN, 200), new FluidStack(Fluids.OXYGEN, 200), 10));
		recipes.put(Fluids.HEAVYWATER, new ElectrolysisRecipe(2_000, new FluidStack(Fluids.DEUTERIUM, 200), new FluidStack(Fluids.OXYGEN, 200), 10));
		recipes.put(Fluids.VITRIOL, new ElectrolysisRecipe(1_000, new FluidStack(Fluids.SULFURIC_ACID, 500), new FluidStack(Fluids.CHLORINE, 500), new ItemStack(ModItems.powder_iron), new ItemStack(ModItems.ingot_mercury)));
		recipes.put(Fluids.SLOP, new ElectrolysisRecipe(1_000, new FluidStack(Fluids.MERCURY, 250), new FluidStack(Fluids.NONE, 0), new ItemStack(ModItems.niter, 2), new ItemStack(ModItems.powder_limestone, 2), new ItemStack(ModItems.sulfur)));
		recipes.put(Fluids.REDMUD, new ElectrolysisRecipe(450, new FluidStack(Fluids.MERCURY, 150), new FluidStack(Fluids.LYE, 50), new ItemStack(ModItems.powder_titanium, 3), new ItemStack(ModItems.powder_iron, 3), new ItemStack(ModItems.powder_aluminium, 2)));
		recipes.put(Fluids.ALUMINA, new ElectrolysisRecipe(200, new FluidStack(Fluids.CARBONDIOXIDE, 100), new FluidStack(Fluids.NONE, 0),40, new ItemStack(ModItems.powder_aluminium, 7), new ItemStack(ModItems.fluorite, 2)));

		recipes.put(Fluids.POTASSIUM_CHLORIDE, new ElectrolysisRecipe(250, new FluidStack(Fluids.CHLORINE, 125), new FluidStack(Fluids.NONE, 0), new ItemStack(ModItems.dust)));
		recipes.put(Fluids.CALCIUM_CHLORIDE, new ElectrolysisRecipe(250, new FluidStack(Fluids.CHLORINE, 125), new FluidStack(Fluids.CALCIUM_SOLUTION, 125)));
	}

	public static HashMap getRecipes() {

		HashMap<Object, Object[]> recipes = new HashMap<Object, Object[]>();

		for(Entry<FluidType, ElectrolysisRecipe> entry : ElectrolyserFluidRecipes.recipes.entrySet()) {

			ElectrolysisRecipe recipe = entry.getValue();
			FluidStack input = new FluidStack(entry.getKey(), recipe.amount);
			List outputs = new ArrayList();
			if(recipe.output1.type != Fluids.NONE) outputs.add(ItemFluidIcon.make(recipe.output1));
			if(recipe.output2.type != Fluids.NONE) outputs.add(ItemFluidIcon.make(recipe.output2));
			for(ItemStack byproduct : recipe.byproduct) outputs.add(byproduct);

			recipes.put(ItemFluidIcon.make(input), outputs.toArray());
		}

		return recipes;
	}
	public static ElectrolysisRecipe getRecipe(FluidType type) {
		if(type == null)
			return null;
		return recipes.get(type);
	}

	@Override
	public String getFileName() {
		return "hbmElectrolyzerFluid.json";
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

		FluidStack input = this.readFluidStack(obj.get("input").getAsJsonArray());
		FluidStack output1 = this.readFluidStack(obj.get("output1").getAsJsonArray());
		FluidStack output2 = this.readFluidStack(obj.get("output2").getAsJsonArray());

		int duration = 20;
		if(obj.has("duraion")) duration = obj.get("duration").getAsInt();

		ItemStack[] byproducts = new ItemStack[0];
		if(obj.has("byproducts")) byproducts = this.readItemStackArray(obj.get("byproducts").getAsJsonArray());

		recipes.put(input.type, new ElectrolysisRecipe(input.fill, output1, output2, duration, byproducts));
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		Entry<FluidType, ElectrolysisRecipe> rec = (Entry) recipe;

		writer.name("input"); this.writeFluidStack(new FluidStack(rec.getKey(), rec.getValue().amount), writer);
		writer.name("output1"); this.writeFluidStack(rec.getValue().output1, writer);
		writer.name("output2"); this.writeFluidStack(rec.getValue().output2, writer);

		if(rec.getValue().byproduct != null && rec.getValue().byproduct.length > 0) {
			writer.name("byproducts").beginArray();
			for(ItemStack stack : rec.getValue().byproduct) this.writeItemStack(stack, writer);
			writer.endArray();
		}

		writer.name("duration").value(rec.getValue().duration);
	}

	public static class ElectrolysisRecipe {
		public FluidStack output1;
		public FluidStack output2;
		public int amount;
		public ItemStack[] byproduct;
		public int duration;

		public ElectrolysisRecipe(int amount, FluidStack output1, FluidStack output2, ItemStack... byproduct) {
			this.output1 = output1;
			this.output2 = output2;
			this.amount = amount;
			this.byproduct = byproduct;
			this.duration = 20;
		}
		public ElectrolysisRecipe(int amount, FluidStack output1, FluidStack output2, int duration, ItemStack... byproduct) {
			this.output1 = output1;
			this.output2 = output2;
			this.amount = amount;
			this.byproduct = byproduct;
			this.duration = duration;
		}
	}


}
