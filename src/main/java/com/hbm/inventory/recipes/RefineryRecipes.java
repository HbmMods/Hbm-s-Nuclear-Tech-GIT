package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ItemEnums.EnumTarType;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.util.ItemStackUtil;

import net.minecraft.item.ItemStack;

public class RefineryRecipes extends SerializableRecipe {

	/// fractions in percent ///
	public static final int oil_frac_heavy = 50;
	public static final int oil_frac_naph = 25;
	public static final int oil_frac_light = 15;
	public static final int oil_frac_petro = 10;
	public static final int crack_frac_naph = 40;
	public static final int crack_frac_light = 30;
	public static final int crack_frac_aroma = 15;
	public static final int crack_frac_unsat = 15;

	public static final int oilds_frac_heavy = 30;
	public static final int oilds_frac_naph = 35;
	public static final int oilds_frac_light = 20;
	public static final int oilds_frac_unsat = 15;
	public static final int crackds_frac_naph = 35;
	public static final int crackds_frac_light = 35;
	public static final int crackds_frac_aroma = 15;
	public static final int crackds_frac_unsat = 15;

	private static Map<FluidType, RefineryRecipe> recipes = new HashMap();

	@Override
	public void registerDefaults() {
		recipes.put(Fluids.HOTOIL, new RefineryRecipe(
				new FluidStack(Fluids.HEAVYOIL,		oil_frac_heavy),
				new FluidStack(Fluids.NAPHTHA,		oil_frac_naph),
				new FluidStack(Fluids.LIGHTOIL,		oil_frac_light),
				new FluidStack(Fluids.PETROLEUM,	oil_frac_petro),
				new ItemStack(ModItems.sulfur)
				));
		recipes.put(Fluids.HOTCRACKOIL, new RefineryRecipe(
				new FluidStack(Fluids.NAPHTHA_CRACK,	crack_frac_naph),
				new FluidStack(Fluids.LIGHTOIL_CRACK,	crack_frac_light),
				new FluidStack(Fluids.AROMATICS,		crack_frac_aroma),
				new FluidStack(Fluids.UNSATURATEDS,		crack_frac_unsat),
				DictFrame.fromOne(ModItems.oil_tar, EnumTarType.CRACK)
				));
		recipes.put(Fluids.HOTOIL_DS, new RefineryRecipe(
				new FluidStack(Fluids.HEAVYOIL,		oilds_frac_heavy),
				new FluidStack(Fluids.NAPHTHA_DS,	oilds_frac_naph),
				new FluidStack(Fluids.LIGHTOIL_DS,	oilds_frac_light),
				new FluidStack(Fluids.UNSATURATEDS,	oilds_frac_unsat),
				DictFrame.fromOne(ModItems.oil_tar, EnumTarType.PARAFFIN)
				));
		recipes.put(Fluids.HOTCRACKOIL_DS, new RefineryRecipe(
				new FluidStack(Fluids.NAPHTHA_DS,		crackds_frac_naph),
				new FluidStack(Fluids.LIGHTOIL_DS,		crackds_frac_light),
				new FluidStack(Fluids.AROMATICS,		crackds_frac_aroma),
				new FluidStack(Fluids.UNSATURATEDS,		crackds_frac_unsat),
				DictFrame.fromOne(ModItems.oil_tar, EnumTarType.PARAFFIN)
				));
	}
	
	public static RefineryRecipe getRefinery(FluidType oil) {
		return recipes.get(oil);
	}

	@Override public String getFileName() { return "hbmRefinery.json"; }
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
		ItemStack solid = this.readItemStack(obj.get("solid").getAsJsonArray());
		
		recipes.put(type, new RefineryRecipe(o0, o1, o2, o3, solid));
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		Entry<FluidType, RefineryRecipe> rec = (Entry<FluidType, RefineryRecipe>) recipe;
		
		writer.name("input").value(rec.getKey().getName());
		
		for(int i = 0; i < 4; i++) {
			writer.name("output" + i);
			this.writeFluidStack(rec.getValue().outputs[i], writer);
		}
		
		writer.name("solid");
		this.writeItemStack(rec.getValue().solid, writer);
	}
	
	public static HashMap<Object, Object[]> getRefineryRecipe() {

		HashMap<Object, Object[]> recipes = new HashMap<Object, Object[]>();
		
		for(Entry<FluidType, RefineryRecipe> recipe : RefineryRecipes.recipes.entrySet()) {
			
			RefineryRecipe fluids = recipe.getValue();
			
			recipes.put(ItemFluidIcon.make(recipe.getKey(), 1000),
					new ItemStack[] {
							ItemFluidIcon.make(fluids.outputs[0].type, fluids.outputs[0].fill * 10),
							ItemFluidIcon.make(fluids.outputs[1].type, fluids.outputs[1].fill * 10),
							ItemFluidIcon.make(fluids.outputs[2].type, fluids.outputs[2].fill * 10),
							ItemFluidIcon.make(fluids.outputs[3].type, fluids.outputs[3].fill * 10),
							ItemStackUtil.carefulCopy(fluids.solid) });
		}
		
		return recipes;
	}
	
	public static class RefineryRecipe {
		
		public FluidStack[] outputs;
		public ItemStack solid;
		
		public RefineryRecipe(FluidStack f0, FluidStack f1, FluidStack f2, FluidStack f3, ItemStack f4) {
			this.outputs = new FluidStack[] {f0, f1, f2, f3};
			this.solid = f4;
		}
	}
}
