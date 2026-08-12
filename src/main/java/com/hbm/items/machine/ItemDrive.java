package com.hbm.items.machine;

import com.hbm.items.ItemEnumMulti;

public class ItemDrive extends ItemEnumMulti {

	public ItemDrive() {
		super(EnumDriveType.class, true, true);
	}

	public static enum EnumDriveType {
		FLASH_EMPTY,
		DISK_EMPTY,
		FLASH_BROKEN,
		DISK_BROKEN,
	}
}
