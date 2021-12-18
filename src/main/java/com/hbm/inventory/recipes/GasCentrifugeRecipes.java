package com.hbm.inventory.recipes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIcon;

import net.minecraft.item.ItemStack;

public class GasCentrifugeRecipes {
	
	public static enum PseudoFluidType {
		NONE	(0,		0,		"NONE",		"Empty",		new ItemStack(ModItems.polaroid, 0)),
		
		NUF6 	(400,	300,	"LEUF6",	"Natural UF6",		new ItemStack(ModItems.nugget_u238, 1)),
		LEUF6 	(300,	200,	"MEUF6",	"Low Enriched UF6",		new ItemStack(ModItems.nugget_u238, 1), new ItemStack(ModItems.fluorite, 1)),
		MEUF6	(200,	100,	"HEUF6",	"Medium Enriched UF6",		new ItemStack(ModItems.nugget_u238, 1)),
		HEUF6	(300,	0,		"NONE",		"High Enriched UF6",		new ItemStack(ModItems.nugget_u238, 2), new ItemStack(ModItems.nugget_u235, 1), new ItemStack(ModItems.fluorite, 1)),
		
		PF6		(300,	0,		"NONE",		"Plutonium Hexafluoride",		new ItemStack(ModItems.nugget_pu238, 1), new ItemStack(ModItems.nugget_pu_mix, 2), new ItemStack(ModItems.fluorite, 1));
		
		int fluidConsumed;
		int fluidProduced;
		String outputFluid;
		String name;
		ItemStack[] output;
		
		PseudoFluidType(int fluidConsumed, int fluidProduced, String outputFluid, String name, ItemStack... output) {
			this.fluidConsumed = fluidConsumed;
			this.fluidProduced = fluidProduced;
			this.outputFluid = outputFluid;
			this.name = name;
			this.output = output;
		}
		
		public int getFluidConsumed() {
			return this.fluidConsumed;
		}
		
		public int getFluidProduced() {
			return this.fluidProduced;
		}
		
		public PseudoFluidType getOutputFluid() {
			return this.valueOf(this.outputFluid);
		}
		
		public String getName() {
			return this.name;
		}
		
		public ItemStack[] getOutput() {
			return this.output;
		}
		
	};
	
	public static class GasCentRecipe {
		ItemStack[] output;
		int type;
		int quantity;
		
		public GasCentRecipe(ItemStack[] output, int ordinal, int quantity) {
			this.output = output;
			this.type = ordinal;
			this.quantity = quantity;	
		}
		
		public ItemStack[] getOutputs() {
			return this.output;
		}
		
		public int getOrdinal() {
			return this.type;
		}
		
		public int getQuantity() {
			return this.quantity;
		}
	}
	
	static GasCentRecipe[] Recipes = new GasCentRecipe[] {new GasCentRecipe( new ItemStack[] {new ItemStack(ModItems.nugget_u238, 11), new ItemStack(ModItems.nugget_u235, 1), new ItemStack(ModItems.fluorite, 4)}, FluidType.UF6.ordinal(), 1200), new GasCentRecipe( new ItemStack[] {new ItemStack(ModItems.nugget_pu238, 3), new ItemStack(ModItems.nugget_pu_mix, 6), new ItemStack(ModItems.fluorite, 3)}, FluidType.PUF6.ordinal(), 900)};

	public static Map<Object, Object[]> getGasCentrifugeRecipes() {
		Map<Object, Object[]> recipes = new HashMap<Object, Object[]>();

		for(int i = 0; i < 2; i++) {
				
				ItemStack[] outputs = Recipes[i].getOutputs();
				
				ItemStack input = new ItemStack(ModItems.fluid_icon, 1, Recipes[i].getOrdinal());
				ItemFluidIcon.addQuantity(input, Recipes[i].getQuantity());
				
				recipes.put(input, outputs);
		}
		
		return recipes;
	}
	
}
