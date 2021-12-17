package com.hbm.inventory.recipes;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.items.ModItems;

import net.minecraft.item.ItemStack;

public class GasCentrifugeRecipes {
	
	public static enum PseudoFluidType {
		NONE	(0,		0,		"NONE",			new ItemStack(ModItems.polaroid, 0)),
		
		NUF6 	(400,	300,	"LEUF6",	new ItemStack(ModItems.nugget_u238, 1)),
		LEUF6 	(300,	200,	"MEUF6",	new ItemStack(ModItems.nugget_u238, 1)),
		MEUF6	(200,	100,	"HEUF6",	new ItemStack(ModItems.nugget_u238, 1)),
		HEUF6	(100,	0,		"NONE",			new ItemStack(ModItems.nugget_u238, 2), new ItemStack(ModItems.nugget_u235, 1)),
		
		PF6		(300,	0,		"NONE",			new ItemStack(ModItems.nugget_pu238, 1), new ItemStack(ModItems.nugget_pu_mix, 2));
		
		int fluidConsumed;
		int fluidProduced;
		String outputFluid;
		ItemStack[] output;
		
		PseudoFluidType(int fluidConsumed, int fluidProduced, String outputFluid, ItemStack... output) {
			this.fluidConsumed = fluidConsumed;
			this.fluidProduced = fluidProduced;
			this.outputFluid = outputFluid;
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
		
		public ItemStack[] getOutput() {
			return this.output;
		}
		
	};
	
}
