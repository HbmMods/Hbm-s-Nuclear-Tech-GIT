package com.hbm.inventory.material;

/* with every new rewrite, optimization and improvement, the code becomes more gregian */
public class Mats {

	public static NTMMaterial
	
			IRON		= make("Iron"),
			GOLD		= make("Gold"),
			STEEL		= make("Steel"),
			TUNGSTEN	= make("Tungsten"),
			COPPER		= make("Copper");
	
	public static NTMMaterial make(String... names) {
		return new NTMMaterial(names);
	}
}
