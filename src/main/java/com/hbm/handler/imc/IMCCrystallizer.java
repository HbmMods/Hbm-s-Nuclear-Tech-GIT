package com.hbm.handler.imc;

import java.util.HashMap;

import com.hbm.inventory.FluidStack;
import com.hbm.inventory.RecipesCommon;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.CrystallizerRecipes.CrystallizerRecipe;
import com.hbm.util.Tuple.Pair;

import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class IMCCrystallizer extends IMCHandler {
	
	public static HashMap<Pair<Object, FluidType>, CrystallizerRecipe> buffer = new HashMap();

	@Override
	public void process(IMCMessage message) {
		
		NBTTagCompound data = message.getNBTValue();

		NBTTagCompound output = data.getCompoundTag("output");
		ItemStack out = ItemStack.loadItemStackFromNBT(output);
		
		if(out == null) {
			this.printError(message, "Output stack could not be read!");
			return;
		}
		
		NBTTagCompound input = data.getCompoundTag("input");
		ItemStack in = ItemStack.loadItemStackFromNBT(input);
		
		int time = data.getInteger("duration");
		FluidStack acid = new FluidStack(Fluids.fromID(data.getInteger("acid")), data.getInteger("amount"));
		
		if(time <= 0)
			time = 600;
		
		if(acid.type == Fluids.NONE)
			acid = new FluidStack(Fluids.PEROXIDE, 500);
		
		CrystallizerRecipe recipe = new CrystallizerRecipe(out, time);
		recipe.acidAmount = acid.fill;
		
		if(in != null) {
			buffer.put(new Pair(new RecipesCommon.ComparableStack(in), acid.type), recipe);
		} else {
			String dict = data.getString("oredict");
			
			if(!dict.isEmpty()) {
				buffer.put(new Pair(dict, acid.type), recipe);
			} else {
				this.printError(message, "Input stack could not be read!");
			}
		}
	}
}
