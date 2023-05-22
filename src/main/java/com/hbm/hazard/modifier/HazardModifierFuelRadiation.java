package com.hbm.hazard.modifier;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class HazardModifierFuelRadiation extends HazardModifier {
	
float target;
	
	public HazardModifierFuelRadiation(float target) {
		this.target = target;
	}

	@Override
	public float modify(ItemStack stack, EntityLivingBase holder, float level) {
		double depletion = Math.pow(stack.getItem().getDurabilityForDisplay(stack), 0.4D);
		level = (float) (level + (this.target - level) * depletion);
		
		return level;
	}
}
