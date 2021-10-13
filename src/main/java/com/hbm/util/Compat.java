package com.hbm.util;

import net.minecraft.item.Item;

public class Compat {

	public static Item tryLoadItem(String domain, String name) {
		String reg = domain + ":" + name;
		return (Item) Item.itemRegistry.getObject(reg);
	}
}
