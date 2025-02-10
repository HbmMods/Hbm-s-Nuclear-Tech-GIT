package com.hbm.handler.nei;

import java.util.HashMap;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.trait.FT_Heatable;
import com.hbm.inventory.fluid.trait.FT_Heatable.HeatingStep;
import com.hbm.inventory.fluid.trait.FT_Heatable.HeatingType;
import com.hbm.items.machine.ItemFluidIcon;

import net.minecraft.item.ItemStack;

public class BoilingHandler extends NEIUniversalHandler {

	public BoilingHandler() {
		super(ModBlocks.machine_boiler.getLocalizedName(), new ItemStack[] { new ItemStack(ModBlocks.machine_boiler), new ItemStack(ModBlocks.machine_industrial_boiler) }, generateRecipes());
	}

	@Override
	public String getKey() {
		return "ntmBoiling";
	}

	public static HashMap<Object, Object> cache;
	public static boolean isReload=false;

	public static HashMap<Object, Object> generateRecipes() {

		if(cache != null && !isReload) return cache;

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
		isReload=false;
		return cache;
	}
}
