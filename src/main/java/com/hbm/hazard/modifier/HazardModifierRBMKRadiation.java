package com.hbm.hazard.modifier;

import com.hbm.hazard.HazardRegistry;
import com.hbm.items.machine.ItemRBMKPellet;
import com.hbm.items.machine.ItemRBMKRod;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class HazardModifierRBMKRadiation extends HazardModifier {
	
	float target;
	
	public HazardModifierRBMKRadiation(float target) {
		this.target = target;
	}

	@Override
	public float modify(ItemStack stack, EntityLivingBase holder, float level) {
		
		if(stack.getItem() instanceof ItemRBMKRod) {
			double depletion = 1D - ItemRBMKRod.getEnrichment(stack);
			double xenon = ItemRBMKRod.getPoisonLevel(stack);
			
			level = (float) (level + (this.target - level) * depletion);
			level += HazardRegistry.xe135 * xenon;
			
		} else if(stack.getItem() instanceof ItemRBMKPellet) {
			
			level = level + (target - level) * (ItemRBMKPellet.rectify(stack.getItemDamage()) / 4F);
			
			if(ItemRBMKPellet.hasXenon(stack.getItemDamage()))
				level += HazardRegistry.xe135 * HazardRegistry.nugget;
		}
		
		return level;
	}

}
