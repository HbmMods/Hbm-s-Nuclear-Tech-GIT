package com.hbm.creativetabs;

import com.hbm.items.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class WeaponTab extends CreativeTabs {

	public WeaponTab(int index, String name) {
		super(index, name);
	}

	@Override
	public Item getTabIconItem() {

		if(ModItems.gun_maresleg != null) {
			return ModItems.gun_maresleg;
		}
		return Items.iron_pickaxe;
	}
}
