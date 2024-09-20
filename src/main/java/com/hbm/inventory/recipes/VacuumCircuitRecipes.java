package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemCircuit.EnumCircuitType;

import net.minecraft.item.ItemStack;

public class VacuumCircuitRecipes extends SerializableRecipe {
	
	public static List<VacuumCircuitRecipe> recipes = new ArrayList();

	@Override
	public void registerDefaults() {
		
		//t0 you can go to the mun when you got all the shit. :)
		recipes.add(new VacuumCircuitRecipe(new ItemStack(ModItems.circuit, 1, EnumCircuitType.PROCESST1.ordinal()), 200, 250,
				new AStack[] {
						new ComparableStack(ModItems.circuit, 4, EnumCircuitType.CHIP)},
				new AStack[] {
						new ComparableStack(ModItems.circuit, 1, EnumCircuitType.PCB),
						new ComparableStack(ModItems.circuit, 2, EnumCircuitType.CAPACITOR)}
		));
		
		recipes.add(new VacuumCircuitRecipe(new ItemStack(ModItems.circuit, 1, EnumCircuitType.PROCESST2.ordinal()), 400, 1_000,
				new AStack[] {
						new ComparableStack(ModItems.circuit, 1, EnumCircuitType.GASCHIP)},
				new AStack[] {
						new ComparableStack(ModItems.circuit, 1, EnumCircuitType.BASIC),
						new ComparableStack(ModItems.circuit, 2, EnumCircuitType.CAPACITOR)}
		));
		
		recipes.add(new VacuumCircuitRecipe(new ItemStack(ModItems.circuit, 1, EnumCircuitType.PROCESST3.ordinal()), 800, 25_000,
				new AStack[] {
						new ComparableStack(ModItems.circuit, 2, EnumCircuitType.GASCHIP),
						new ComparableStack(ModItems.circuit, 1, EnumCircuitType.HFCHIP)},
				new AStack[] {
						new ComparableStack(ModItems.circuit, 1, EnumCircuitType.ADVANCED),
						new ComparableStack(ModItems.circuit, 2, EnumCircuitType.CAPACITOR_LANTHANIUM)}
		));
		
	}
	
	
	public static VacuumCircuitRecipe getRecipe(ItemStack[] inputs) {

		for(VacuumCircuitRecipe recipe : recipes) {
			if(matchesIngredients(new ItemStack[] {inputs[0], inputs[1]}, recipe.wafer) &&
					matchesIngredients(new ItemStack[] {inputs[2], inputs[3]}, recipe.pcb)) return recipe;
		}
		
		return null;
	}
	
	public static HashMap getRecipes() {

		HashMap<Object, Object> recipes = new HashMap<Object, Object>();
		
		for(VacuumCircuitRecipe recipe : VacuumCircuitRecipes.recipes) {
			
			List ingredients = new ArrayList();
			for(AStack stack : recipe.wafer) ingredients.add(stack);
			for(AStack stack : recipe.pcb) ingredients.add(stack);
			
			recipes.put(ingredients.toArray(), recipe.output);
		}
		
		return recipes;
	}

	@Override
	public String getFileName() {
		return "hbmVacuumCircuit.json";
	}

	@Override
	public Object getRecipeObject() {
		return recipes;
	}

	@Override
	public void deleteRecipes() {
		recipes.clear();
		wafer.clear();
		pcb.clear();
	}

	@Override
	public void readRecipe(JsonElement recipe) {
		JsonObject obj = (JsonObject) recipe;

		AStack[] wafer = this.readAStackArray(obj.get("wafer").getAsJsonArray());
		AStack[] pcb = this.readAStackArray(obj.get("pcb").getAsJsonArray());
		ItemStack output = this.readItemStack(obj.get("output").getAsJsonArray());
		int duration = obj.get("duration").getAsInt();
		long consumption = obj.get("consumption").getAsLong();
		
		recipes.add(new VacuumCircuitRecipe(output, duration, consumption, wafer, pcb));
	}

	@Override
	public void writeRecipe(Object obj, JsonWriter writer) throws IOException {
		VacuumCircuitRecipe recipe = (VacuumCircuitRecipe) obj;
		
		writer.name("wafer").beginArray();
		for(AStack aStack : recipe.wafer) this.writeAStack(aStack, writer);
		writer.endArray();
		
		writer.name("pcb").beginArray();
		for(AStack aStack : recipe.pcb) this.writeAStack(aStack, writer);
		writer.endArray();
		
		
		writer.name("output");
		this.writeItemStack(recipe.output, writer);

		writer.name("duration").value(recipe.duration);
		writer.name("consumption").value(recipe.consumption);
	}

	public static HashSet<AStack> wafer = new HashSet();
	public static HashSet<AStack> pcb = new HashSet();
	
	public static class VacuumCircuitRecipe {

		public AStack[] wafer;
		public AStack[] pcb;
		public ItemStack output;
		public int duration;
		public long consumption;
		
		public VacuumCircuitRecipe(ItemStack output, int duration, long consumption, AStack[] wafer, AStack[] pcb) {
			this.wafer = wafer;
			this.pcb = pcb;
			this.output = output;
			this.duration = duration;
			this.consumption = consumption;
			for(AStack t : wafer) VacuumCircuitRecipes.wafer.add(t);
			for(AStack t : pcb) VacuumCircuitRecipes.pcb.add(t);
		}
	}
}
