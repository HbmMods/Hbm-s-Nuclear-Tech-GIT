package com.hbm.handler.nei;

import java.util.HashMap;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.trait.FT_Heatable;
import com.hbm.inventory.fluid.trait.FT_Heatable.HeatingStep;
import com.hbm.inventory.fluid.trait.FT_Heatable.HeatingType;
import com.hbm.items.machine.ItemFluidIcon;

public class BoilingHandler extends NEIUniversalHandler {

	public BoilingHandler() {
		super("Boiler", ModBlocks.machine_boiler, generateRecipes());
	}

	@Override
	public String getKey() {
		return "ntmBoiling";
	}
	
	public static HashMap<Object, Object> cache;
	
	public static HashMap<Object, Object> generateRecipes() {
		
		if(cache != null) return cache;
		
		cache = new HashMap();
		
		for(FluidType type : Fluids.getInNiceOrder()) {
			
			if(type.hasTrait(FT_Heatable.class)) {
				FT_Heatable trait = type.getTrait(FT_Heatable.class);
				
				if(trait.getEfficiency(HeatingType.BOILER) > 0) {
					HeatingStep step = trait.getFirstStep();
					cache.put(ItemFluidIcon.make(type, step.amountReq), ItemFluidIcon.make(step.typeProduced, step.amountProduced));
				}
			}
		}
		
		return cache;
	}
}
