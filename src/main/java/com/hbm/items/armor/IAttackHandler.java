package com.hbm.items.armor;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public interface IAttackHandler {

	public void handleAttack(LivingAttackEvent event, ItemStack armor);
}
