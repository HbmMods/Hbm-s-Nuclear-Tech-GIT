package com.hbm.inventory.recipes;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.hbm.inventory.FluidStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.RefineryRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.util.Tuple.Pair;

import net.minecraft.item.ItemStack;

public class RadiolysisRecipes {
	//All cracking recipes, + 2H2O => H2O2 + H2, + heavy oil => light oil + waste product, and others i haven't even thought of yet
	
	private static Map<FluidType, Pair<FluidStack, FluidStack>> radiolysis = new HashMap(); //fluidstacks :reimumunch:
	
	/* I am proud of this */
	public static Map<Object, Object[]> getRecipesForNEI() {
		Map<Object, Object[]> recipes = new HashMap<Object, Object[]>();
		Iterator itr = radiolysis.entrySet().iterator();
		
		while(itr.hasNext()) {
			Map.Entry entry = (Entry) itr.next();
			Pair<FluidStack, FluidStack> pair = (Pair<FluidStack, FluidStack>) entry.getValue();
			ItemStack[] outputs = new ItemStack[2];
			if(pair.getKey().type == Fluids.NONE) {
				outputs[0] = new ItemStack(ModItems.nothing);
			} else {
				outputs[0] = ItemFluidIcon.make(pair.getKey().type, pair.getKey().fill);
			}
			if(pair.getValue().type == Fluids.NONE) {
				outputs[1] = new ItemStack(ModItems.nothing);
			} else {
				outputs[1] = ItemFluidIcon.make(pair.getValue().type, pair.getValue().fill);
			}
			
			recipes.put(ItemFluidIcon.make((FluidType) entry.getKey(), 100), outputs);
		}
		
		return recipes;
	}
	
	public static void registerRadiolysis() {
		radiolysis.put(Fluids.WATER, new Pair(new FluidStack(80, Fluids.ACID), new FluidStack(20, Fluids.HYDROGEN)));
		
		//now this is poggers
		radiolysis.put(Fluids.OIL, new Pair(new FluidStack(RefineryRecipes.oil_crack_oil, Fluids.CRACKOIL), new FluidStack(RefineryRecipes.oil_crack_petro, Fluids.PETROLEUM)));
		radiolysis.put(Fluids.BITUMEN, new Pair(new FluidStack(RefineryRecipes.bitumen_crack_oil, Fluids.OIL), new FluidStack(RefineryRecipes.bitumen_crack_aroma, Fluids.AROMATICS)));
		radiolysis.put(Fluids.SMEAR, new Pair(new FluidStack(RefineryRecipes.smear_crack_napht, Fluids.NAPHTHA), new FluidStack(RefineryRecipes.smear_crack_petro, Fluids.PETROLEUM)));
		radiolysis.put(Fluids.GAS, new Pair(new FluidStack(RefineryRecipes.gas_crack_petro, Fluids.PETROLEUM), new FluidStack(RefineryRecipes.gas_crack_unsat, Fluids.UNSATURATEDS)));
		radiolysis.put(Fluids.DIESEL, new Pair(new FluidStack(RefineryRecipes.diesel_crack_kero, Fluids.KEROSENE), new FluidStack(RefineryRecipes.diesel_crack_petro, Fluids.PETROLEUM)));
		radiolysis.put(Fluids.KEROSENE, new Pair(new FluidStack(RefineryRecipes.kero_crack_petro, Fluids.PETROLEUM), new FluidStack(0, Fluids.NONE)));
	}
	
	public static Pair<FluidStack, FluidStack> getRadiolysis(FluidType input) {
		return radiolysis.get(input);
	}
}
