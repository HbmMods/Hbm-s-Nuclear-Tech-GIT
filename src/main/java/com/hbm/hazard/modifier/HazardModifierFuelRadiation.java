package com.hbm.hazard.modifier;

import com.hbm.hazard.HazardRegistry;
import com.hbm.items.machine.ItemFuelRod;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class HazardModifierFuelRadiation extends HazardModifier {
	
float target;
	
	public HazardModifierFuelRadiation(float target) {
		this.target = target;
	}

	@Override
	public float modify(ItemStack stack, EntityLivingBase holder, float level) {
		
		if(stack.getItem() instanceof ItemFuelRod) {
			ItemFuelRod fuel = (ItemFuelRod) stack.getItem();
			double depletion = fuel.getDurabilityForDisplay(stack);
			
			level = (float) (level + (this.target - level) * depletion);
			
		}
		
		return level;
	}
}
