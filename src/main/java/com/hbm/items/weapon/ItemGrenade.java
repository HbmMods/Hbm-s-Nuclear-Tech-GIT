package com.hbm.items.weapon;

import net.minecraft.item.Item;

public class ItemGrenade extends Item {
	
	public int fuse = 4;

	public ItemGrenade(int fuse) {
		this.maxStackSize = 16;
		this.fuse = fuse;
	}
	
	public static int getFuseTicks(Item grenade) {
		return ((ItemGrenade)grenade).fuse * 20;
	}
}
