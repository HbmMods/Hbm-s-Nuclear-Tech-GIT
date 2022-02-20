package com.hbm.inventory.recipes;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.hbm.inventory.FluidStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIcon;

import net.minecraft.item.ItemStack;

public class GasCentrifugeRecipes {
	
	public static enum PseudoFluidType {
		NONE		(0,		0,		"NONE",			"Empty",					false,	(ItemStack[])null),
		
		NUF6 		(400,	300,	"LEUF6",		"Natural UF6",				false,	new ItemStack(ModItems.nugget_u238, 1)),
		LEUF6 		(300,	200,	"MEUF6",		"Low Enriched UF6",			false,	new ItemStack(ModItems.nugget_u238, 1), new ItemStack(ModItems.fluorite, 1)),
		MEUF6		(200,	100,	"HEUF6",		"Medium Enriched UF6",		false,	new ItemStack(ModItems.nugget_u238, 1)),
		HEUF6		(300,	0,		"NONE",			"High Enriched UF6",		true,	new ItemStack(ModItems.nugget_u238, 2), new ItemStack(ModItems.nugget_u235, 1), new ItemStack(ModItems.fluorite, 1)),
		
		PF6			(300,	0,		"NONE",			"Plutonium Hexafluoride",	false,	new ItemStack(ModItems.nugget_pu238, 1), new ItemStack(ModItems.nugget_pu_mix, 2), new ItemStack(ModItems.fluorite, 1)),

		MUD			(1000,	500,	"MUD_HEAVY",	"Poisonous Mud",			false,	new ItemStack(ModItems.powder_lead, 1), new ItemStack(ModItems.dust, 1)),
		MUD_HEAVY	(500,	0,		"NONE",			"Heavy Mud Fraction",		false,	new ItemStack(ModItems.powder_iron, 1), new ItemStack(ModItems.dust, 1), new ItemStack(ModItems.nuclear_waste_tiny, 1));
		
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
		
	/* Recipe NEI Handler */
	private static Map<FluidStack, ItemStack[]> gasCent = new HashMap();
	
	//Iterators are lots of fun
	public static Map<Object, Object[]> getGasCentrifugeRecipes() {
		Map<Object, Object[]> recipes = new HashMap<Object, Object[]>();
		Iterator itr = gasCent.entrySet().iterator();
		
		while(itr.hasNext()) {
			Map.Entry entry = (Entry) itr.next();
			FluidStack input = (FluidStack) entry.getKey();
			ItemStack[] out = new ItemStack[4];
			ItemStack[] outputs = (ItemStack[]) entry.getValue();		
			
			for(int j = 0; j < outputs.length; j++) {
				out[j] = outputs[j].copy();
			}
			for(int j = 0; j < 4; j++)
				if(out[j] == null)
					out[j] = new ItemStack(ModItems.nothing);
			
			recipes.put(ItemFluidIcon.make(input.type, input.fill), outputs);
		}
		
		return recipes;
	}
	
	public static void register() {
		gasCent.put(new FluidStack(1200, Fluids.UF6), new ItemStack[] {new ItemStack(ModItems.nugget_u238, 11), new ItemStack(ModItems.nugget_u235, 1), new ItemStack(ModItems.fluorite, 4)});
		gasCent.put(new FluidStack(900, Fluids.PUF6), new ItemStack[] {new ItemStack(ModItems.nugget_pu238, 3), new ItemStack(ModItems.nugget_pu_mix, 6), new ItemStack(ModItems.fluorite, 3)});
		gasCent.put(new FluidStack(1000, Fluids.WATZ), new ItemStack[] {new ItemStack(ModItems.powder_iron, 1), new ItemStack(ModItems.powder_lead, 1), new ItemStack(ModItems.nuclear_waste_tiny, 1)});
	}
}
