package com.hbm.items.armor;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public interface IDamageHandler {

	public void handleDamage(LivingHurtEvent event, ItemStack stack);
}
