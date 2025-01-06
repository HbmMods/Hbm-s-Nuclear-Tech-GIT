package com.hbm.items.special;

import com.hbm.items.ItemEnumMulti;

public class ItemCircuitStarComponent extends ItemEnumMulti {

	public ItemCircuitStarComponent() {
		super(CircuitComponentType.class, true, true);
	}
	
	public static enum CircuitComponentType {
		CHIPSET,
		CPU,
		RAM,
		CARD
	}
}
