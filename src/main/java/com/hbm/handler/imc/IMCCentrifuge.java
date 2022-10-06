package com.hbm.handler.imc;

import java.util.HashMap;

import com.hbm.inventory.RecipesCommon;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;

import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class IMCCentrifuge extends IMCHandler {
	
	public static HashMap<AStack, ItemStack[]> buffer = new HashMap();

	@Override
	public void process(IMCMessage message) {
		
		NBTTagCompound data = message.getNBTValue();
		ItemStack[] outs = new ItemStack[4];

		for(int i = 0; i < 4; i++) {
			
			NBTTagCompound output = data.getCompoundTag("output" + (i + 1));
			ItemStack out = ItemStack.loadItemStackFromNBT(output);
			
			if(out == null) {
				this.printError(message, "Output stack could not be read!");
				return;
			}
			
			outs[i] = out;
		}
		
		NBTTagCompound input = data.getCompoundTag("input");
		ItemStack in = ItemStack.loadItemStackFromNBT(input);
		
		if(in != null) {
			buffer.put(new RecipesCommon.ComparableStack(in), outs);
		} else {
			String dict = data.getString("oredict");
			
			if(!dict.isEmpty()) {
				buffer.put(new OreDictStack(dict), outs);
			} else {
				this.printError(message, "Input stack could not be read!");
			}
		}
	}
}
