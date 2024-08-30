package com.hbm.items.armor;

import com.hbm.handler.ArmorModHandler;

public class ItemModBattery extends ItemArmorMod {
	
	public double mod;

	public ItemModBattery(double mod) {
		super(ArmorModHandler.battery, true, true, true, true);
		this.mod = mod;
	}
}
