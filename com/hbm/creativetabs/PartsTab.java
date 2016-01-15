package com.hbm.creativetabs;

import com.hbm.items.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class PartsTab extends CreativeTabs {

	public PartsTab(int p_i1853_1_, String p_i1853_2_) {
		super(p_i1853_1_, p_i1853_2_);
	}

	@Override
	public Item getTabIconItem() {
		
		if(ModItems.ingot_uranium != null)
		{
			return ModItems.ingot_uranium;
		}
		
		return Items.iron_pickaxe;
	}

}
