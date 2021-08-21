package com.hbm.handler.imc;

import java.util.HashMap;

import com.hbm.inventory.RecipesCommon;

import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class IMCCrystallizer extends IMCHandler {
	
	public static HashMap<Object, ItemStack> buffer = new HashMap();

	@Override
	public void process(IMCMessage message) {
		
		NBTTagCompound data = message.getNBTValue();

		NBTTagCompound output = data.getCompoundTag("output");
		ItemStack out = ItemStack.loadItemStackFromNBT(output);
		
		if(out == null) {
			this.printError(message, "Output stack could not be read!");
		}
		
		NBTTagCompound input = data.getCompoundTag("input");
		ItemStack in = ItemStack.loadItemStackFromNBT(input);
		
		if(in != null) {
			buffer.put(new RecipesCommon.ComparableStack(in), out);
		} else {
			String dict = data.getString("oredict");
			
			if(!dict.isEmpty()) {
				buffer.put(dict, out);
			} else {
				this.printError(message, "Input stack could not be read!");
			}
		}
	}
}
