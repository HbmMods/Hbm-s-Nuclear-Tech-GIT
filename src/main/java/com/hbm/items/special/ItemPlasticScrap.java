package com.hbm.items.special;

import com.hbm.items.ItemEnumMulti;

public class ItemPlasticScrap extends ItemEnumMulti {

	public ItemPlasticScrap() {
		super(ScrapType.class, false, false);
		this.setCreativeTab(null);
	}
	
	public static enum ScrapType {
		//GENERAL BOARD
		BOARD_BLANK,
		BOARD_TRANSISTOR,
		BOARD_CONVERTER,
		
		//CHIPSET
		BRIDGE_NORTH,
		BRIDGE_SOUTH,
		BRIDGE_IO,
		BRIDGE_BUS,
		BRIDGE_CHIPSET,
		BRIDGE_CMOS,
		BRIDGE_BIOS,
		
		//CPU
		CPU_REGISTER,
		CPU_CLOCK,
		CPU_LOGIC,
		CPU_CACHE,
		CPU_EXT,
		CPU_SOCKET,
		
		//RAM
		MEM_SOCKET,
		MEM_16K_A,
		MEM_16K_B,
		MEM_16K_C,
		MEM_16K_D,
		
		//EXTENSION CARD
		CARD_BOARD,
		CARD_PROCESSOR
	}
}
