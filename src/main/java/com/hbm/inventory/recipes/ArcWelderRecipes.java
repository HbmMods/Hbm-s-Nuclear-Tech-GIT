package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static com.hbm.inventory.OreDictManager.*;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIcon;

import net.minecraft.item.ItemStack;

public class ArcWelderRecipes extends SerializableRecipe {
	
	public static List<ArcWelderRecipe> recipes = new ArrayList();

	@Override
	public void registerDefaults() {

		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.motor, 2), 100, 200L,
				new OreDictStack(IRON.plate(), 2), new ComparableStack(ModItems.coil_copper), new ComparableStack(ModItems.coil_copper_torus)));
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.motor, 2), 100, 400L,
				new OreDictStack(STEEL.plate(), 1), new ComparableStack(ModItems.coil_copper), new ComparableStack(ModItems.coil_copper_torus)));

		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.circuit_copper), 100, 1_000L, new FluidStack(Fluids.GAS, 250),
				new ComparableStack(ModItems.circuit_aluminium, 1), new OreDictStack(NETHERQUARTZ.dust()), new ComparableStack(ModItems.wire_copper, 8)));
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.circuit_red_copper), 100, 2_500L, new FluidStack(Fluids.PETROLEUM, 250),
				new ComparableStack(ModItems.circuit_copper, 1), new OreDictStack(GOLD.dust()), new ComparableStack(ModItems.wire_red_copper, 8)));
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.circuit_gold), 100, 10_000L, new FluidStack(Fluids.UNSATURATEDS, 250),
				new ComparableStack(ModItems.circuit_red_copper, 1), new OreDictStack(ANY_PLASTIC.ingot()), new ComparableStack(ModItems.wire_gold, 8)));
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.circuit_schrabidium), 100, 50_000L, new FluidStack(Fluids.SOURGAS, 250),
				new ComparableStack(ModItems.circuit_gold, 1), new OreDictStack(DESH.ingot()), new ComparableStack(ModItems.wire_schrabidium, 8)));

		//earlygame welded parts
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.plate_welded, 1, Mats.MAT_IRON.id), 100, 100L,
				new OreDictStack(IRON.plateCast(), 2)));
		//high-demand mid-game parts
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.plate_welded, 1, Mats.MAT_STEEL.id), 100, 500L,
				new OreDictStack(STEEL.plateCast(), 2)));
		//mid-game, single combustion engine running on LPG
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.plate_welded, 1, Mats.MAT_TITANIUM.id), 600, 50_000L,
				new OreDictStack(TI.plateCast(), 2)));
		//mid-game PWR
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.plate_welded, 1, Mats.MAT_ZIRCONIUM.id), 600, 10_000L,
				new OreDictStack(ZR.plateCast(), 2)));
		//late-game fusion
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.plate_welded, 1, Mats.MAT_TCALLOY.id), 1_200, 1_000_000L, new FluidStack(Fluids.OXYGEN, 1_000),
				new OreDictStack(TCALLOY.plateCast(), 2)));
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.plate_welded, 1, Mats.MAT_CDALLOY.id), 1_200, 1_000_000L, new FluidStack(Fluids.OXYGEN, 1_000),
				new OreDictStack(CDALLOY.plateCast(), 2)));
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.plate_welded, 1, Mats.MAT_TUNGSTEN.id), 1_200, 250_000L, new FluidStack(Fluids.OXYGEN, 1_000),
				new OreDictStack(W.plateCast(), 2)));
		//pre-DFC
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.plate_welded, 1, Mats.MAT_OSMIRIDIUM.id), 6_000, 20_000_000L, new FluidStack(Fluids.REFORMGAS, 16_000),
				new OreDictStack(OSMIRIDIUM.plateCast(), 2)));
	}
	
	public static HashMap getRecipes() {

		HashMap<Object, Object> recipes = new HashMap<Object, Object>();
		
		for(ArcWelderRecipe recipe : ArcWelderRecipes.recipes) {
			
			int size = recipe.ingredients.length + (recipe.fluid != null ? 1 : 0);
			Object[] array = new Object[size];
			
			for(int i = 0; i < recipe.ingredients.length; i++) {
				array[i] = recipe.ingredients[i];
			}
			
			if(recipe.fluid != null) array[size - 1] = ItemFluidIcon.make(recipe.fluid);
			
			recipes.put(array, recipe.output);
		}
		
		return recipes;
	}
	
	public static ArcWelderRecipe getRecipe(ItemStack... inputs) {
		
		outer:
		for(ArcWelderRecipe recipe : recipes) {

			List<AStack> recipeList = new ArrayList();
			for(AStack ingredient : recipe.ingredients) recipeList.add(ingredient);
			
			for(int i = 0; i < inputs.length; i++) {
				
				ItemStack inputStack = inputs[i];

				if(inputStack != null) {
					
					boolean hasMatch = false;
					Iterator<AStack> iterator = recipeList.iterator();

					while(iterator.hasNext()) {
						AStack recipeStack = iterator.next();

						if(recipeStack.matchesRecipe(inputStack, true) && inputStack.stackSize >= recipeStack.stacksize) {
							hasMatch = true;
							recipeList.remove(recipeStack);
							break;
						}
					}

					if(!hasMatch) {
						continue outer;
					}
				}
			}
			
			if(recipeList.isEmpty()) return recipe;
		}
		
		return null;
	}

	@Override
	public String getFileName() {
		return "hbmArcWelder.json";
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
		
		AStack[] inputs = this.readAStackArray(obj.get("inputs").getAsJsonArray());
		FluidStack fluid = obj.has("fluid") ? this.readFluidStack(obj.get("fluid").getAsJsonArray()) : null;
		ItemStack output = this.readItemStack(obj.get("output").getAsJsonArray());
		int duration = obj.get("duration").getAsInt();
		long consumption = obj.get("consumption").getAsLong();
		
		recipes.add(new ArcWelderRecipe(output, duration, consumption, fluid, inputs));
	}

	@Override
	public void writeRecipe(Object obj, JsonWriter writer) throws IOException {
		ArcWelderRecipe recipe = (ArcWelderRecipe) obj;
		
		writer.name("inputs").beginArray();
		for(AStack aStack : recipe.ingredients) {
			this.writeAStack(aStack, writer);
		}
		writer.endArray();
		
		if(recipe.fluid != null) {
			writer.name("fluid");
			this.writeFluidStack(recipe.fluid, writer);
		}
		
		writer.name("output");
		this.writeItemStack(recipe.output, writer);

		writer.name("duration").value(recipe.duration);
		writer.name("consumption").value(recipe.consumption);
	}
	
	public static class ArcWelderRecipe {
		
		public AStack[] ingredients;
		public FluidStack fluid;
		public ItemStack output;
		public int duration;
		public long consumption;
		
		public ArcWelderRecipe(ItemStack output, int duration, long consumption, FluidStack fluid, AStack... ingredients) {
			this.ingredients = ingredients;
			this.fluid = fluid;
			this.output = output;
			this.duration = duration;
			this.consumption = consumption;
		}
		
		public ArcWelderRecipe(ItemStack output, int duration, long consumption, AStack... ingredients) {
			this(output, duration, consumption, null, ingredients);
		}
	}
}
