package com.hbm.items.machine;

import com.hbm.items.ItemEnumMulti;

public class ItemPACoil extends ItemEnumMulti {

	public ItemPACoil() {
		super(EnumCoilType.class, true, true);
		this.setMaxStackSize(1);
	}

	public static enum EnumCoilType {
		GOLD, NIOBIUM, BSCCO
	}
}
