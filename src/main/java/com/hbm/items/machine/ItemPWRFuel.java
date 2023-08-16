package com.hbm.items.machine;

import com.hbm.items.ItemEnumMulti;

public class ItemPWRFuel extends ItemEnumMulti {

	public ItemPWRFuel() {
		super(EnumPWRFuel.class, true, true);
	}
	
	public static enum EnumPWRFuel {
		MEU,
		HEU233,
		HEU235,
		MEN,
		HEN237,
		MOX,
		MEP,
		HEP239,
		HEP241,
		MEA,
		HEA242,
		HES326,
		HES327;
	}
}
