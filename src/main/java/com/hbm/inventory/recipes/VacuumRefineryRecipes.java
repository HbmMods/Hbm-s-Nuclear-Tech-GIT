package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.machine.ItemFluidIcon;

import net.minecraft.item.ItemStack;

public class VacuumRefineryRecipes extends SerializableRecipe {

	public static final int vac_frac_heavy = 40;
	public static final int vac_frac_reform = 25;
	public static final int vac_frac_light = 20;
	public static final int vac_frac_sour = 15;
	
	public static HashMap<FluidType, VacuumRefineryRecipe> recipes = new HashMap();

	@Override
	public void registerDefaults() {

		recipes.put(Fluids.OIL, new VacuumRefineryRecipe(
				new FluidStack(Fluids.HEAVYOIL_VACUUM,	vac_frac_heavy),
				new FluidStack(Fluids.REFORMATE,		vac_frac_reform),
				new FluidStack(Fluids.LIGHTOIL_VACUUM,	vac_frac_light),
				new FluidStack(Fluids.SOURGAS,			vac_frac_sour)
				));
		recipes.put(Fluids.OIL_DS, new VacuumRefineryRecipe(
				new FluidStack(Fluids.HEAVYOIL_VACUUM,	vac_frac_heavy),
				new FluidStack(Fluids.REFORMATE,		vac_frac_reform),
				new FluidStack(Fluids.LIGHTOIL_VACUUM,	vac_frac_light),
				new FluidStack(Fluids.REFORMGAS,		vac_frac_sour)
				));
	}
	
	public static VacuumRefineryRecipe getVacuum(FluidType oil) {
		return recipes.get(oil);
	}

	@Override public String getFileName() { return "hbmVacRefinery.json"; }
	@Override public Object getRecipeObject() { return recipes; }
	@Override public void deleteRecipes() { recipes.clear(); }
	
	@Override public String getComment() {
		return "Inputs always assume 100mB, input ammount cannot be changed.";
	}

	@Override
	public void readRecipe(JsonElement recipe) {
		JsonObject obj = recipe.getAsJsonObject();
		
		FluidType type = Fluids.fromName(obj.get("input").getAsString());
		FluidStack o0 = this.readFluidStack(obj.get("output0").getAsJsonArray());
		FluidStack o1 = this.readFluidStack(obj.get("output1").getAsJsonArray());
		FluidStack o2 = this.readFluidStack(obj.get("output2").getAsJsonArray());
		FluidStack o3 = this.readFluidStack(obj.get("output3").getAsJsonArray());
		
		recipes.put(type, new VacuumRefineryRecipe(o0, o1, o2, o3));
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		Entry<FluidType, VacuumRefineryRecipe> rec = (Entry<FluidType, VacuumRefineryRecipe>) recipe;
		
		writer.name("input").value(rec.getKey().getName());
		
		for(int i = 0; i < 4; i++) {
			writer.name("output" + i);
			this.writeFluidStack(rec.getValue().outputs[i], writer);
		}
	}
	
	public static HashMap getVacuumRecipe() {

		HashMap<Object, Object[]> recipes = new HashMap<Object, Object[]>();
		
		for(Entry<FluidType, VacuumRefineryRecipe> recipe : VacuumRefineryRecipes.recipes.entrySet()) {
			
			VacuumRefineryRecipe fluids = recipe.getValue();
			
			recipes.put(ItemFluidIcon.make(recipe.getKey(), 1000, 2),
					new ItemStack[] {
							ItemFluidIcon.make(fluids.outputs[0].type, fluids.outputs[0].fill * 10),
							ItemFluidIcon.make(fluids.outputs[1].type, fluids.outputs[1].fill * 10),
							ItemFluidIcon.make(fluids.outputs[2].type, fluids.outputs[2].fill * 10),
							ItemFluidIcon.make(fluids.outputs[3].type, fluids.outputs[3].fill * 10) });
		}
		
		return recipes;
	}

	public static class VacuumRefineryRecipe {
		
		public FluidStack[] outputs;
		
		public VacuumRefineryRecipe(FluidStack f0, FluidStack f1, FluidStack f2, FluidStack f3) {
			this.outputs = new FluidStack[] {f0, f1, f2, f3};
		}
	}
}
