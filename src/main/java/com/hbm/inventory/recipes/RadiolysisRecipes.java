package com.hbm.inventory.recipes;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.hbm.inventory.FluidStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.util.Tuple.Pair;

import net.minecraft.item.ItemStack;

public class RadiolysisRecipes {
	//All cracking recipes, + 2H2O => H2O2 + H2, + heavy oil => light oil + waste product, and others i haven't even thought of yet
	
	private static Map<FluidType, Pair<FluidStack, FluidStack>> radiolysis = new HashMap(); //fluidstacks :reimumunch:
	
	/* I am proud of this but I don't think I should be */
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
		
		radiolysis.put(Fluids.BITUMEN, new Pair(new FluidStack(80, Fluids.OIL), new FluidStack(20, Fluids.PETROLEUM)));
		radiolysis.put(Fluids.SMEAR, new Pair(new FluidStack(60, Fluids.NAPHTHA), new FluidStack(40, Fluids.PETROLEUM)));
		radiolysis.put(Fluids.GAS, new Pair(new FluidStack(50, Fluids.PETROLEUM), new FluidStack(0, Fluids.NONE)));
		radiolysis.put(Fluids.DIESEL, new Pair(new FluidStack(40, Fluids.KEROSENE), new FluidStack(30, Fluids.PETROLEUM)));
		radiolysis.put(Fluids.KEROSENE, new Pair(new FluidStack(60, Fluids.PETROLEUM), new FluidStack(0, Fluids.NONE)));
	}
	
	public static Pair<FluidStack, FluidStack> getRadiolysis(FluidType input) {
		return radiolysis.get(input);
	}
}
