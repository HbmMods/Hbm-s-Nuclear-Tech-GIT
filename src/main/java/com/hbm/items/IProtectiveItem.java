package com.hbm.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public interface IProtectiveItem {

	public float[] getCurrentDTDR(ItemStack stack, EntityLivingBase entity, DamageSource damage, float amount, float pierceDT, float pierce);
}
