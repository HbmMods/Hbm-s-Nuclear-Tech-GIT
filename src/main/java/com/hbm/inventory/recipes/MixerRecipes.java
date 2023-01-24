package com.hbm.inventory.recipes;

import static com.hbm.inventory.OreDictManager.KNO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.hbm.inventory.FluidStack;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIcon;

public class MixerRecipes {

	public static HashMap<FluidType, MixerRecipe> recipes = new HashMap();
	
	public static void register() {
		recipes.put(Fluids.COOLANT, new MixerRecipe(2_000, 50).setStack1(new FluidStack(Fluids.WATER, 1_800)).setSolid(new OreDictStack(KNO.dust())));
		recipes.put(Fluids.CRYOGEL, new MixerRecipe(2_000, 50).setStack1(new FluidStack(Fluids.COOLANT, 1_800)).setSolid(new ComparableStack(ModItems.powder_ice)));
		recipes.put(Fluids.NITAN, new MixerRecipe(1_000, 50).setStack1(new FluidStack(Fluids.KEROSENE, 600)).setStack2(new FluidStack(Fluids.MERCURY, 200)).setSolid(new ComparableStack(ModItems.powder_nitan_mix)));
		recipes.put(Fluids.FRACKSOL, new MixerRecipe(1_000, 20).setStack1(new FluidStack(Fluids.WATER, 1_000)).setStack2(new FluidStack(Fluids.PETROLEUM, 100)).setSolid(new OreDictStack(OreDictManager.S.dust())));
		recipes.put(Fluids.ENDERJUICE, new MixerRecipe(100, 100).setStack1(new FluidStack(Fluids.XPJUICE, 500)).setSolid(new OreDictStack(OreDictManager.DIAMOND.dust())));
		
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
