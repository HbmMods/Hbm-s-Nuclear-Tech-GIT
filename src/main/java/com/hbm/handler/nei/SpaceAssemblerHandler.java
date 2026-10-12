package com.hbm.handler.nei;

import com.hbm.inventory.recipes.SpaceAssemblerRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemSatellite.EnumSatType;

import net.minecraft.item.ItemStack;

public class SpaceAssemblerHandler extends NEIGenericRecipeHandler {

	public SpaceAssemblerHandler() {
		super(new ItemStack(ModItems.satellite, 1, EnumSatType.SCIENCE_ASSEMBLER.ordinal()).getDisplayName(),
				SpaceAssemblerRecipes.INSTANCE, new ItemStack(ModItems.satellite, 1, EnumSatType.SCIENCE_ASSEMBLER.ordinal()));
	}

	@Override public String getRecipeID() { return "ntmSpaceAssembler"; }
}
