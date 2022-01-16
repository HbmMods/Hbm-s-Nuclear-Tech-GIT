package com.hbm.inventory.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIcon;

import net.minecraft.item.ItemStack;

public class GasCentrifugeRecipes {
	
	public static enum PseudoFluidType {
		NONE	(0,		0,		"NONE",		"Empty",				false,	(ItemStack)null),
		
		NUF6 	(400,	300,	"LEUF6",	"Natural UF6",			false,	new ItemStack(ModItems.nugget_u238, 1)),
		LEUF6 	(300,	200,	"MEUF6",	"Low Enriched UF6",		false,	new ItemStack(ModItems.nugget_u238, 1), new ItemStack(ModItems.fluorite, 1)),
		MEUF6	(200,	100,	"HEUF6",	"Medium Enriched UF6",	false,	new ItemStack(ModItems.nugget_u238, 1)),
		HEUF6	(300,	0,		"NONE",		"High Enriched UF6",	true,	new ItemStack(ModItems.nugget_u238, 2), new ItemStack(ModItems.nugget_u235, 1), new ItemStack(ModItems.fluorite, 1)),
		
		PF6		(300,	0,		"NONE",		"Plutonium Hexafluoride",	false,	new ItemStack(ModItems.nugget_pu238, 1), new ItemStack(ModItems.nugget_pu_mix, 2), new ItemStack(ModItems.fluorite, 1));
		
		int fluidConsumed;
		int fluidProduced;
		String outputFluid;
		String name;
		boolean isHighSpeed;
		ItemStack[] output;
		
		PseudoFluidType(int fluidConsumed, int fluidProduced, String outputFluid, String name, boolean isHighSpeed, ItemStack... output) {
			this.fluidConsumed = fluidConsumed;
			this.fluidProduced = fluidProduced;
			this.outputFluid = outputFluid;
			this.name = name;
			this.isHighSpeed = isHighSpeed; 
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
		
		public boolean getIfHighSpeed() {
			return this.isHighSpeed;
		}
		
		public ItemStack[] getOutput() {
			return this.output;
		}
		
	};
	
	//Recipes for NEI
	public static List<ItemStack> getGasCentOutputs(FluidType fluid) {
		List<ItemStack> outputs = new ArrayList(4);
		
		if(fluid == Fluids.UF6) {
			outputs.add(new ItemStack(ModItems.nugget_u238, 11));
			outputs.add(new ItemStack(ModItems.nugget_u235, 1)); 
			outputs.add(new ItemStack(ModItems.fluorite, 4));
		} else if(fluid == Fluids.PUF6) {
			outputs.add(new ItemStack(ModItems.nugget_pu238, 3));
			outputs.add(new ItemStack(ModItems.nugget_pu_mix, 6));
			outputs.add(new ItemStack(ModItems.fluorite, 3));
		}
		return outputs;
	}
	
	public static int getQuantityRequired(FluidType fluid) {
		if(fluid == Fluids.UF6) return 1200;
		if(fluid == Fluids.PUF6)return 900;
		return 0;
	}
	
	public static Map<Object, Object[]> getGasCentrifugeRecipes() {
		Map<Object, Object[]> recipes = new HashMap<Object, Object[]>();

		for(int i = 0; i < Fluids.getAll().length; i++) {
			if(getGasCentOutputs(Fluids.fromID(i)) != null) {
				List<ItemStack> out = getGasCentOutputs(Fluids.fromID(i));
				ItemStack[] outputs = new ItemStack[4];
				
				for(int j = 0; j < out.size(); j++) {
					outputs[j] = out.get(j).copy();
				}
				for(int j = 0; j < 4; j++)
					if(outputs[j] == null)
						outputs[j] = new ItemStack(ModItems.nothing);
				
				ItemStack input = new ItemStack(ModItems.fluid_icon, 1, i);
				ItemFluidIcon.addQuantity(input, getQuantityRequired(Fluids.fromID(i)));
				
				recipes.put(input, outputs);
			}
		}
		
		return recipes;
	}
	
}
