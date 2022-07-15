package com.hbm.hazard.transformer;

import java.util.List;

import com.hbm.hazard.HazardEntry;
import com.hbm.hazard.HazardRegistry;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class HazardTransformerRadiationNBT extends HazardTransformerBase {
	
	public static final String RAD_KEY = "hfrHazRadiation";

	@Override
	public void transformPre(ItemStack stack, List<HazardEntry> entries) { }

	@Override
	public void transformPost(ItemStack stack, List<HazardEntry> entries) {
		
		if(stack.hasTagCompound() && stack.stackTagCompound.hasKey(RAD_KEY)) {
			entries.add(new HazardEntry(HazardRegistry.RADIATION, stack.stackTagCompound.getFloat(RAD_KEY)));
		}
	}
	public static ItemStack addRadNBT(ItemStack stack, float rad)
	{
		if (!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		
		stack.stackTagCompound.setFloat(RAD_KEY, rad);
		return stack;
	}
}
