package com.hbm.handler.imc;

import java.util.ArrayList;

import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.util.Tuple.Triplet;

import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

/**
 * @author UFFR
 */

public class IMCBlastFurnace extends IMCHandler {
	public static final ArrayList<Triplet<Object, Object, ItemStack>> buffer = new ArrayList<>();

	@Override
	public void process(IMCMessage message) {
		
		final NBTTagCompound data = message.getNBTValue();
		final NBTTagCompound outputData = data.getCompoundTag("output");
		final ItemStack output = ItemStack.loadItemStackFromNBT(outputData);

		if(output == null) {
			printError(message, "Output stack could not be read!");
			return;
		}
		
		final Object input1;
		final Object input2;
		
		switch(data.getString("inputType1")) {
		case "ore":
			input1 = data.getString("input1");
			break;
			
		case "orelist":
			final NBTTagList list = data.getTagList("input1", 8);
			final ArrayList<String> ores = new ArrayList<String>(list.tagCount());
			for(int i = 0; i < list.tagCount(); i++)
				ores.add(list.getStringTagAt(i));
			input1 = ores;
			break;
			
		case "itemstack":
			input1 = new ComparableStack(ItemStack.loadItemStackFromNBT(data.getCompoundTag("input1")));
			break;
			
		default:
			printError(message, "Unhandled input type!");
			return;
		}
		
		switch(data.getString("inputType2")) {
		case "ore":
			input2 = data.getString("input2");
			break;
			
		case "orelist":
			final NBTTagList list = data.getTagList("input2", 9);
			final ArrayList<String> ores = new ArrayList<String>(list.tagCount());
			for(int i = 0; i < list.tagCount(); i++)
				ores.add(list.getStringTagAt(i));
			input2 = ores;
			break;
			
		case "itemstack":
			input2 = new ComparableStack(ItemStack.loadItemStackFromNBT(data.getCompoundTag("input2")));
			break;
			
		default:
			printError(message, "Unhandled input type!");
			return;
		}
		
		buffer.add(new Triplet<Object, Object, ItemStack>(input1, input2, output));
	}
}
