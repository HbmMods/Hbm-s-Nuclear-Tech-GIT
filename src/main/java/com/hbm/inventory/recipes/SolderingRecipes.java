package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemCircuit.EnumCircuitType;

import net.minecraft.item.ItemStack;

public class SolderingRecipes extends SerializableRecipe {
	
	public static List<SolderingRecipe> recipes = new ArrayList();

	@Override
	public void registerDefaults() {
		
		recipes.add(new SolderingRecipe(new ItemStack(ModItems.circuit, 1, EnumCircuitType.ANALOG.ordinal()), 100, 100,
				new AStack[] {
						new ComparableStack(ModItems.circuit, 3, EnumCircuitType.VACUUM_TUBE.ordinal()),
						new ComparableStack(ModItems.circuit, 2, EnumCircuitType.CAPACITOR.ordinal())},
				new AStack[] {
						new ComparableStack(ModItems.circuit, 4, EnumCircuitType.PCB.ordinal())},
				new AStack[] {}
		));
	}
	
	public static SolderingRecipe getRecipe(ItemStack[] inputs) {

		for(SolderingRecipe recipe : recipes) {
			if(matchesIngredients(new ItemStack[] {inputs[0], inputs[1], inputs[2]}, recipe.toppings) &&
					matchesIngredients(new ItemStack[] {inputs[3], inputs[4]}, recipe.pcb) &&
					matchesIngredients(new ItemStack[] {inputs[5]}, recipe.solder)) return recipe;
		}
		
		return null;
	}

	@Override
	public String getFileName() {
		return "hbmSoldering.json";
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
		
	}

	@Override
	public void writeRecipe(Object obj, JsonWriter writer) throws IOException {
		
	}
	
	public static class SolderingRecipe {

		public AStack[] toppings;
		public AStack[] pcb;
		public AStack[] solder;
		public FluidStack fluid;
		public ItemStack output;
		public int duration;
		public long consumption;
		
		public SolderingRecipe(ItemStack output, int duration, long consumption, FluidStack fluid, AStack[] toppings, AStack[] pcb, AStack[] solder) {
			this.toppings = toppings;
			this.pcb = pcb;
			this.solder = solder;
			this.fluid = fluid;
			this.output = output;
			this.duration = duration;
			this.consumption = consumption;
		}
		
		public SolderingRecipe(ItemStack output, int duration, long consumption, AStack[] toppings, AStack[] pcb, AStack[] solder) {
			this(output, duration, consumption, null, toppings, pcb, solder);
		}
	}
}
