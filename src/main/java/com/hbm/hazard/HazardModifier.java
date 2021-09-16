package com.hbm.hazard;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public abstract class HazardModifier {

	public abstract float modify(ItemStack stack, EntityLivingBase holder, float level);
	
	/**
	 * Returns the level after applying all modifiers to it, in order.
	 * @param stack
	 * @param entity
	 * @param level
	 * @param mods
	 * @return
	 */
	public static float evalAllModifiers(ItemStack stack, EntityLivingBase entity, float level, List<HazardModifier> mods) {
		
		for(HazardModifier mod : mods) {
			level = mod.modify(stack, entity, level);
		}
		
		return level;
	}
}
