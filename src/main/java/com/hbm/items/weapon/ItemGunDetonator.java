package com.hbm.items.weapon;

import com.hbm.handler.GunConfiguration;

import net.minecraft.item.ItemStack;

public class ItemGunDetonator extends ItemGunBase {

	public ItemGunDetonator(GunConfiguration config) {
		super(config);
	}
	
	@Override
	public boolean hasInfinity(ItemStack stack, GunConfiguration config) {
		return true;
	}
}
