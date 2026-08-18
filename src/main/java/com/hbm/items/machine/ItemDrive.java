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
		
		FLASH_FLIGHTSIM,			// precalc for spaceflight
		FLASH_PARTICLESIM,			// precalc for fusion

		DISK_FLIGHTDATA,			// raw data from satellite
		DISK_FLIGHTDATA_PROCESSED,	// processed data from satellite
		DISK_ORBITDATA,				// raw sensor relay data
		DISK_ORBITDATA_PROCESSED,	// processed data from sensor relay
	}
}
