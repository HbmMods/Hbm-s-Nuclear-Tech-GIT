package com.hbm.items.machine;

import com.hbm.items.ItemEnumMulti;
import com.hbm.items.machine.ItemArcElectrode.EnumElectrodeType;

public class ItemArcElectrodeBurnt extends ItemEnumMulti {

	public ItemArcElectrodeBurnt() {
		super(EnumElectrodeType.class, true, true);
		this.setFull3D();
	}
}
