package com.hbm.hazard.transformer;

import java.util.List;

import com.hbm.hazard.HazardEntry;
import com.hbm.hazard.HazardRegistry;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class HazardTransformerDigammaNBT extends HazardTransformerBase
{
	public static final String DRX_KEY = "hfrHazDigamma";
	@Override
	public void transformPre(ItemStack stack, List<HazardEntry> entries)
	{
		// Unused I guess
	}

	@Override
	public void transformPost(ItemStack stack, List<HazardEntry> entries)
	{
		if(stack.hasTagCompound() && stack.stackTagCompound.hasKey(DRX_KEY))
			entries.add(new HazardEntry(HazardRegistry.DIGAMMA, stack.stackTagCompound.getFloat(DRX_KEY)));

	}
	
	public static ItemStack addDRXNBT(ItemStack stack, float drx)
	{
		if (!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		
		stack.stackTagCompound.setFloat(DRX_KEY, drx);
		return stack;
	}

}
