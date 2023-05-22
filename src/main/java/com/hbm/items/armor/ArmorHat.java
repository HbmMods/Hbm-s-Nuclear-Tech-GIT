package com.hbm.items.armor;

import net.minecraft.entity.item.EntityItem;

public class ArmorHat extends ArmorModel {

	public ArmorHat(ArmorMaterial armorMaterial, int armorType) {
		super(armorMaterial, armorType);
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem) {
		entityItem.setDead();
		return true;
	}

}
