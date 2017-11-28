package com.hbm.items.special;

import net.minecraft.item.Item;

public class ItemCatalyst extends Item {
	
	int color;
	
	public ItemCatalyst(int color) {
		this.color = color;
	}
	
	public int getColor() {
		return this.color;
	}

}
