package com.hbm.creativetabs;

import com.hbm.items.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class ConsumableTab extends CreativeTabs {

	public ConsumableTab(int p_i1853_1_, String p_i1853_2_) {
		super(p_i1853_1_, p_i1853_2_);
	}

	@Override
	public Item getTabIconItem() {
		
		if(ModItems.bottle_nuka != null)
		{
			return ModItems.bottle_nuka;
		}
		
		return Items.iron_pickaxe;
	}
}
