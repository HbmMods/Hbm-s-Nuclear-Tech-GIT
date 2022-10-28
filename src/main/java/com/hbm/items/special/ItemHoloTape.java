package com.hbm.items.special;

import com.hbm.items.ItemEnumMulti;

public class ItemHoloTape extends ItemEnumMulti {

	public ItemHoloTape(Class<? extends Enum> theEnum, boolean multiName, boolean multiTexture) {
		super(theEnum, multiName, multiTexture);
		this.setMaxStackSize(1);
	}
}
