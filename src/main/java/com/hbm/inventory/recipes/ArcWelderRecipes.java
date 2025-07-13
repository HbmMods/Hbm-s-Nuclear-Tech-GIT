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
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ItemGenericPart.EnumPartType;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIcon;

import net.minecraft.item.ItemStack;

public class ArcWelderRecipes extends SerializableRecipe {
	
	public static List<ArcWelderRecipe> recipes = new ArrayList();

	@Override
	public void registerDefaults() {

		//Parts
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.motor, 2), 100, 200L,
				new OreDictStack(IRON.plate(), 2), new ComparableStack(ModItems.coil_copper), new ComparableStack(ModItems.coil_copper_torus)));
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.motor, 2), 100, 400L,
				new OreDictStack(STEEL.plate(), 1), new ComparableStack(ModItems.coil_copper), new ComparableStack(ModItems.coil_copper_torus)));
		recipes.add(new ArcWelderRecipe(DictFrame.fromOne(ModItems.part_generic, EnumPartType.LDE), 200, 5_000L,
				new OreDictStack(AL.plate(), 4), new OreDictStack(FIBER.ingot(), 4), new OreDictStack(ANY_HARDPLASTIC.ingot())));
		recipes.add(new ArcWelderRecipe(DictFrame.fromOne(ModItems.part_generic, EnumPartType.LDE), 200, 10_000L,
				new OreDictStack(TI.plate(), 2), new OreDictStack(FIBER.ingot(), 4), new OreDictStack(ANY_HARDPLASTIC.ingot())));
		recipes.add(new ArcWelderRecipe(DictFrame.fromOne(ModItems.part_generic, EnumPartType.HDE), 600, 25_000_000L, new FluidStack(Fluids.STELLAR_FLUX, 4_000),
				new OreDictStack(ANY_BISMOIDBRONZE.plateCast(), 2), new OreDictStack(CMB.plateWelded(), 1), new ComparableStack(ModItems.ingot_cft)));

		//Dense Wires
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.wire_dense, 1, Mats.MAT_COPPER.id), 100, 10_000L,
				new OreDictStack(CU.wireFine(), 8)));
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.wire_dense, 1, Mats.MAT_ALLOY.id), 100, 10_000L,
				new OreDictStack(ALLOY.wireFine(), 8)));
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.wire_dense, 1, Mats.MAT_GOLD.id), 100, 10_000L,
				new OreDictStack(GOLD.wireFine(), 8)));

		//earlygame welded parts
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.plate_welded, 1, Mats.MAT_IRON.id), 100, 100L,
				new OreDictStack(IRON.plateCast(), 2)));
		//high-demand mid-game parts
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.plate_welded, 1, Mats.MAT_STEEL.id), 100, 500L,
				new OreDictStack(STEEL.plateCast(), 2)));
		//literally just the combination oven
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.plate_welded, 1, Mats.MAT_COPPER.id), 200, 1_000L,
				new OreDictStack(CU.plateCast(), 2)));
		//mid-game, single combustion engine running on LPG
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.plate_welded, 1, Mats.MAT_TITANIUM.id), 600, 50_000L,
				new OreDictStack(TI.plateCast(), 2)));
		//mid-game PWR
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.plate_welded, 1, Mats.MAT_ZIRCONIUM.id), 600, 10_000L,
				new OreDictStack(ZR.plateCast(), 2)));
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.plate_welded, 1, Mats.MAT_ALUMINIUM.id), 300, 10_000L,
				new OreDictStack(AL.plateCast(), 2)));
		//late-game fusion
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.plate_welded, 1, Mats.MAT_TCALLOY.id), 1_200, 1_000_000L, new FluidStack(Fluids.OXYGEN, 1_000),
				new OreDictStack(TCALLOY.plateCast(), 2)));
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.plate_welded, 1, Mats.MAT_CDALLOY.id), 1_200, 1_000_000L, new FluidStack(Fluids.OXYGEN, 1_000),
				new OreDictStack(CDALLOY.plateCast(), 2)));
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.plate_welded, 1, Mats.MAT_TUNGSTEN.id), 1_200, 250_000L, new FluidStack(Fluids.OXYGEN, 1_000),
				new OreDictStack(W.plateCast(), 2)));
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.plate_welded, 1, Mats.MAT_CMB.id), 1_200, 10_000_000L, new FluidStack(Fluids.REFORMGAS, 1_000),
				new OreDictStack(CMB.plateCast(), 2)));
		//pre-DFC
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.plate_welded, 1, Mats.MAT_OSMIRIDIUM.id), 6_000, 20_000_000L, new FluidStack(Fluids.REFORMGAS, 16_000),
				new OreDictStack(OSMIRIDIUM.plateCast(), 2)));
		
		//Missile Parts
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.thruster_small), 60, 1_000L, new OreDictStack(STEEL.plate(), 4), new OreDictStack(AL.wireFine(), 4), new OreDictStack(CU.plate(), 4)));
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.thruster_medium), 100, 2_000L, new OreDictStack(STEEL.plate(), 8), new ComparableStack(ModItems.motor, 1), new OreDictStack(GRAPHITE.ingot(), 8)));
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.thruster_large), 200, 5_000L, new OreDictStack(DURA.ingot(), 10), new ComparableStack(ModItems.motor, 1), new OreDictStack(OreDictManager.getReflector(), 12)));

		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.fuel_tank_small), 60, 1_000L, new OreDictStack(AL.plate(), 6), new OreDictStack(CU.plate(), 4), new ComparableStack(ModBlocks.steel_scaffold, 4)));
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.fuel_tank_medium), 100, 2_000L, new OreDictStack(AL.plateCast(), 4), new OreDictStack(TI.plate(), 8), new ComparableStack(ModBlocks.steel_scaffold, 12)));
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.fuel_tank_large), 200, 5_000L, new OreDictStack(AL.plateWelded(), 8), new OreDictStack(BIGMT.plate(), 12), new ComparableStack(ModBlocks.steel_scaffold, 16)));

		//Missiles
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.missile_anti_ballistic), 100, 5_000L, new OreDictStack(ANY_HIGHEXPLOSIVE.ingot(), 3), new ComparableStack(ModItems.missile_assembly), new ComparableStack(ModItems.thruster_small, 4)));
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.missile_generic), 100, 5_000L, new ComparableStack(ModItems.warhead_generic_small), new ComparableStack(ModItems.fuel_tank_small), new ComparableStack(ModItems.thruster_small)));
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.missile_incendiary), 100, 5_000L, new ComparableStack(ModItems.warhead_incendiary_small), new ComparableStack(ModItems.fuel_tank_small), new ComparableStack(ModItems.thruster_small)));
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.missile_cluster), 100, 5_000L, new ComparableStack(ModItems.warhead_cluster_small), new ComparableStack(ModItems.fuel_tank_small), new ComparableStack(ModItems.thruster_small)));
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.missile_buster), 100, 5_000L, new ComparableStack(ModItems.warhead_buster_small), new ComparableStack(ModItems.fuel_tank_small), new ComparableStack(ModItems.thruster_small)));
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.missile_decoy), 60, 2_500L, new OreDictStack(STEEL.ingot()), new ComparableStack(ModItems.fuel_tank_small), new ComparableStack(ModItems.thruster_small)));

		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.missile_strong), 200, 10_000L, new ComparableStack(ModItems.warhead_generic_medium), new ComparableStack(ModItems.fuel_tank_medium), new ComparableStack(ModItems.thruster_medium)));
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.missile_incendiary_strong), 200, 10_000L, new ComparableStack(ModItems.warhead_incendiary_medium), new ComparableStack(ModItems.fuel_tank_medium), new ComparableStack(ModItems.thruster_medium)));
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.missile_cluster_strong), 200, 10_000L, new ComparableStack(ModItems.warhead_cluster_medium), new ComparableStack(ModItems.fuel_tank_medium), new ComparableStack(ModItems.thruster_medium)));
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.missile_buster_strong), 200, 10_000L, new ComparableStack(ModItems.warhead_buster_medium), new ComparableStack(ModItems.fuel_tank_medium), new ComparableStack(ModItems.thruster_medium)));
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.missile_emp_strong), 200, 10_000L, new ComparableStack(ModBlocks.emp_bomb, 3), new ComparableStack(ModItems.fuel_tank_medium), new ComparableStack(ModItems.thruster_medium)));

		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.missile_burst), 300, 25_000L, new ComparableStack(ModItems.warhead_generic_large), new ComparableStack(ModItems.fuel_tank_medium, 2), new ComparableStack(ModItems.thruster_medium, 4)));
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.missile_inferno), 300, 25_000L, new ComparableStack(ModItems.warhead_incendiary_large), new ComparableStack(ModItems.fuel_tank_medium, 2), new ComparableStack(ModItems.thruster_medium, 4)));
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.missile_rain), 300, 25_000L, new ComparableStack(ModItems.warhead_cluster_large), new ComparableStack(ModItems.fuel_tank_medium, 2), new ComparableStack(ModItems.thruster_medium, 4)));
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.missile_drill), 300, 25_000L, new ComparableStack(ModItems.warhead_buster_large), new ComparableStack(ModItems.fuel_tank_medium, 2), new ComparableStack(ModItems.thruster_medium, 4)));

		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.missile_nuclear), 600, 50_000L, new ComparableStack(ModItems.warhead_nuclear), new ComparableStack(ModItems.fuel_tank_large), new ComparableStack(ModItems.thruster_large, 3)));
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.missile_nuclear_cluster), 600, 50_000L, new ComparableStack(ModItems.warhead_mirv), new ComparableStack(ModItems.fuel_tank_large), new ComparableStack(ModItems.thruster_large, 3)));
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.missile_volcano), 600, 50_000L, new ComparableStack(ModItems.warhead_volcano), new ComparableStack(ModItems.fuel_tank_large), new ComparableStack(ModItems.thruster_large, 3)));

		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.sat_mapper), 600, 10_000L, new ComparableStack(ModItems.sat_base), new ComparableStack(ModItems.sat_head_mapper)));
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.sat_scanner), 600, 10_000L, new ComparableStack(ModItems.sat_base), new ComparableStack(ModItems.sat_head_scanner)));
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.sat_radar), 600, 10_000L, new ComparableStack(ModItems.sat_base), new ComparableStack(ModItems.sat_head_radar)));
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.sat_laser), 600, 50_000L, new ComparableStack(ModItems.sat_base), new ComparableStack(ModItems.sat_head_laser)));
		recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.sat_resonator), 600, 50_000L, new ComparableStack(ModItems.sat_base), new ComparableStack(ModItems.sat_head_resonator)));
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
