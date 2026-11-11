package com.hbm.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface IStatTrack {

	public void tickUp(ItemStack stack, EntityLivingBase target);
}
