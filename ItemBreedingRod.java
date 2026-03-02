package com.hbm.items.machine;

import com.hbm.items.ItemEnumMulti;

public class ItemBreedingRod extends ItemEnumMulti {
	
	public ItemBreedingRod() {
		super(BreedingRodType.class, true, true);
	}
	
	public enum BreedingRodType {
        LITHIUM,
		TRITIUM,
		CO,
		CO60,
		TH232,
		THF,
		U235,
		NP237,
		U238,
		PU238,
		PU239,
		PU240,
		PU241,
		AM241,
		AM242,
		AM243,
		CM242,
		CM243,
		CM244,
		CM245,
		CF249,
		CF250,
		CF251,
		CF252,
		RGP,
		RGA,
		RGC,
		WASTE,
		
		//Required for prototype
		LEAD,
		URANIUM,
		
		RA226,
		AC227
	}
}
