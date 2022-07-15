package com.hbm.handler.imc;

import java.util.ArrayList;

import com.hbm.inventory.FluidStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.ChemPlantRecipesNT.ChemPlantRecipe;

import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class IMCChemPlant extends IMCHandler
{
	public static final ArrayList<ChemPlantRecipe> buffer = new ArrayList<>();
	@Override
	public void process(IMCMessage message)
	{
		NBTTagCompound data = message.getNBTValue();
		String name = data.getString("name");
		FluidStack[] fIn = new FluidStack[2];
		FluidStack[] fOut = new FluidStack[2];
		ComparableStack[] iIn = new ComparableStack[4];
		ItemStack[] iOut = new ItemStack[4];
		int time = data.getInteger("time");
		
		if (data.getBoolean("hasFluidIn"))
		{
			fIn[0] = new FluidStack(Fluids.fromName(data.getString("fluidIn1Type")), data.getInteger("fluidIn1Amount"));
			fIn[1] = new FluidStack(Fluids.fromName(data.getString("fluidIn2Type")), data.getInteger("fluidIn2Amount"));
		}
		if (data.getBoolean("hasFluidOut"))
		{
			fOut[0] = new FluidStack(Fluids.fromName(data.getString("fluidOut1Type")), data.getInteger("fluidOut1Amount"));
			fOut[1] = new FluidStack(Fluids.fromName(data.getString("fluidOut2Type")), data.getInteger("fluidOut2Amount"));
		}
		if (data.getBoolean("hasItemIn"))
		{
			NBTTagCompound inputs = data.getCompoundTag("itemIn");
			byte i = 0;
			while (inputs.hasKey("itemIn" + (i + 1)))
				iIn[i] = new ComparableStack(ItemStack.loadItemStackFromNBT(inputs.getCompoundTag("itemIn" + (i + 1))));
		}
		if (data.getBoolean("hasItemOut"))
		{
			NBTTagCompound outputs = data.getCompoundTag("itemOut");
			byte i = 0;
			while (outputs.hasKey("itemOut" + (i + 1)))
				iOut[i] = ItemStack.loadItemStackFromNBT(outputs.getCompoundTag("itemOut" + (i + 1)));
		}
		
		buffer.add(new ChemPlantRecipe(name, time)
				.setFluidInput(fIn[0], fIn[1])
				.setFluidOutput(fOut[0], fOut[1])
				.setItemInput(iIn)
				.setItemOutput(iOut));
	}

}
