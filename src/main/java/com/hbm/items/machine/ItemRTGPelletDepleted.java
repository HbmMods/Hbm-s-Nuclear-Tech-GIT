package com.hbm.items.machine;

import com.hbm.items.ItemEnumMulti;

public class ItemRTGPelletDepleted extends ItemEnumMulti {
	
	public ItemRTGPelletDepleted() {
		super(DepletedRTGMaterial.class, true, true);
	}
	
	public enum DepletedRTGMaterial {
		BISMUTH,
		MERCURY,
		NEPTUNIUM,
		LEAD,
		ZIRCONIUM,
		NICKEL;
	}
}
