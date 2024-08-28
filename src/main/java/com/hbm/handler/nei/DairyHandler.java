package com.hbm.handler.nei;

import java.util.HashMap;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.machine.ItemFluidIcon;

import net.minecraft.item.ItemStack;

public class DairyHandler extends NEIUniversalHandler {

	public DairyHandler() {
		super("Dairy", ModBlocks.machine_milk_reformer, getDairyRecipesForNEI());
	}

	@Override
	public String getKey() {
		return "ntmDairy";
	}

    public static HashMap<Object, Object> getDairyRecipesForNEI() {

		HashMap<Object, Object> recipes = new HashMap<>();
        
        ItemStack[] in = new ItemStack[] {
            ItemFluidIcon.make(Fluids.MILK, 100),
        };

        ItemStack[] out = new ItemStack[] {
            ItemFluidIcon.make(Fluids.EMILK, 50),
            ItemFluidIcon.make(Fluids.CMILK, 35),
            ItemFluidIcon.make(Fluids.CREAM, 15),
        };

        recipes.put(in, out);

        return recipes;
	}
    
}
