package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ModItems;
import com.hbm.tileentity.machine.TileEntityHadron.EnumHadronState;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

@Deprecated public class HadronRecipes extends SerializableRecipe {
	
	/*
	 * Since we're dealing with like 10 or so recipes, using a HashMap (or to combine two keys, a HashMap *in* a HashMap)
	 * would be less performant than those few steps through a good old Array list, and it's much easier to implement too.
	 */
	private static final List<HadronRecipe> recipes = new ArrayList();
	
	/*
	 * We CAN actually implement recipes with the same input items but different momentum requirements.
	 * Just be sure to register the higher requirement BEFORE the lower one since those need to be checked first.
	 * 
	 * It's important to remember that, ok?
	 * 
	 * Update, T+6 minutes: I went for coffee and already forgot what I was doing, good thing I keep these notes, hehe.
	 * Having multiple recipes with different momentum requirements (at most I would expect 2) isn't exactly necessary
	 * since the thing differentiates between ring and line accelerator mode, and line accelerators are by design always shorter anyway.
	 */
	@Override
	public void registerDefaults() {

		recipes.add(new HadronRecipe(
				new ItemStack(ModItems.particle_hydrogen),
				new ItemStack(ModItems.particle_copper),
				900,
				new ItemStack(ModItems.particle_aproton),
				new ItemStack(ModItems.particle_aelectron),
				true
				));
		recipes.add(new HadronRecipe(
				new ItemStack(ModItems.particle_amat),
				new ItemStack(ModItems.particle_amat),
				900,
				new ItemStack(ModItems.particle_aschrab),
				new ItemStack(ModItems.particle_empty),
				false
				));
		recipes.add(new HadronRecipe(
				new ItemStack(ModItems.particle_aschrab),
				new ItemStack(ModItems.particle_aschrab),
				100000,
				new ItemStack(ModItems.particle_dark),
				new ItemStack(ModItems.particle_empty),
				false
				));
		recipes.add(new HadronRecipe(
				new ItemStack(ModItems.particle_hydrogen),
				new ItemStack(ModItems.particle_amat),
				2000,
				new ItemStack(ModItems.particle_muon),
				new ItemStack(ModItems.particle_empty),
				true
				));
		recipes.add(new HadronRecipe(
				new ItemStack(ModItems.particle_hydrogen),
				new ItemStack(ModItems.particle_lead),
				5000,
				new ItemStack(ModItems.particle_higgs),
				new ItemStack(ModItems.particle_empty),
				false
				));
		recipes.add(new HadronRecipe(
				new ItemStack(ModItems.particle_muon),
				new ItemStack(ModItems.particle_higgs),
				2000,
				new ItemStack(ModItems.particle_tachyon),
				new ItemStack(ModItems.particle_empty),
				true
				));
		recipes.add(new HadronRecipe(
				new ItemStack(ModItems.particle_muon),
				new ItemStack(ModItems.particle_dark),
				100000,
				new ItemStack(ModItems.particle_strange),
				new ItemStack(ModItems.particle_empty),
				false
				));
		recipes.add(new HadronRecipe(
				new ItemStack(ModItems.particle_strange),
				new ItemStack(ModItems.powder_magic),
				500000,
				new ItemStack(ModItems.particle_sparkticle),
				new ItemStack(ModItems.dust),
				false
				));
		recipes.add(new HadronRecipe(
				new ItemStack(ModItems.particle_sparkticle),
				new ItemStack(ModItems.particle_higgs),
				1000000,
				new ItemStack(ModItems.particle_digamma),
				new ItemStack(ModItems.particle_empty),
				false
				));
		recipes.add(new HadronRecipe(
				new ItemStack(Items.chicken),
				new ItemStack(Items.chicken),
				100,
				new ItemStack(ModItems.nugget),
				new ItemStack(ModItems.nugget),
				false
				));
	}
	
	public static EnumHadronState returnCode = EnumHadronState.NORESULT;
	
	/**
	 * Resolves recipes, simple enough.
	 * @param in1
	 * @param in2
	 * @param momentum
	 * @param analysisOnly true == line accelerator mode
	 * @return either null (no recipe) or an ItemStack array with 2 non-null instances
	 */
	public static ItemStack[] getOutput(ItemStack in1, ItemStack in2, int momentum, boolean analysisOnly) {
		
		returnCode = EnumHadronState.NORESULT_WRONG_INGREDIENT;
		
		for(HadronRecipe r : recipes) {
			
			if((r.in1.isApplicable(in1) && r.in2.isApplicable(in2)) ||
					(r.in1.isApplicable(in2) && r.in2.isApplicable(in1))) {

				if(analysisOnly && !r.analysisOnly)	returnCode = EnumHadronState.NORESULT_WRONG_MODE;
				if(momentum < r.momentum)			returnCode = EnumHadronState.NORESULT_TOO_SLOW;
				
				if(momentum >= r.momentum && analysisOnly == r.analysisOnly)
					return new ItemStack[] {r.out1, r.out2};
			}
		}
		return null;
	}
	
	public static List<HadronRecipe> getRecipes() {
		return recipes;
	}
	
	public static class HadronRecipe {
		
		public ComparableStack in1;
		public ComparableStack in2;
		public int momentum;
		public ItemStack out1;
		public ItemStack out2;
		public boolean analysisOnly;
		
		public HadronRecipe(ItemStack in1, ItemStack in2, int momentum, ItemStack out1, ItemStack out2, boolean analysisOnly) {
			this.in1 = new ComparableStack(in1).makeSingular();
			this.in2 = new ComparableStack(in2).makeSingular();
			this.momentum = momentum;
			this.out1 = out1;
			this.out2 = out2;
			this.out1.stackSize = 1;
			this.out2.stackSize = 1;
			this.analysisOnly = analysisOnly;
		}
	}

	@Override
	public String getFileName() {
		return "hbmHadronCollider.json";
	}

	@Override
	public Object getRecipeObject() {
		return this.recipes;
	}

	@Override
	public void readRecipe(JsonElement recipe) { }

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException { }

	@Override
	public void deleteRecipes() {
		this.recipes.clear();
	}
}
