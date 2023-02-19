package com.hbm.hazard.transformer;

import java.util.List;

import com.hbm.hazard.HazardEntry;
import com.hbm.hazard.HazardRegistry;

import net.minecraft.item.ItemStack;

public class HazardTransformerRadiationNBT extends HazardTransformerBase {
	
	public static final String RAD_KEY = "hfrHazRadiation";	
	public static final String ACTIVATION_KEY = "ntmNeutron";

	@Override
	public void transformPre(ItemStack stack, List<HazardEntry> entries) { }

	@Override
	public void transformPost(ItemStack stack, List<HazardEntry> entries) {
		float level = 0;
		if(stack.hasTagCompound() && stack.stackTagCompound.hasKey(RAD_KEY)) {
			level += stack.stackTagCompound.getFloat(RAD_KEY);
		}
		if(stack.hasTagCompound() && stack.stackTagCompound.hasKey(ACTIVATION_KEY)) {
			level += stack.stackTagCompound.getFloat(ACTIVATION_KEY);
		}
		entries.add(new HazardEntry(HazardRegistry.RADIATION, level));
	}

}
