package com.hbm.creativetabs;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class MissileTab extends CreativeTabs {

	public MissileTab(int p_i1853_1_, String p_i1853_2_) {
		super(p_i1853_1_, p_i1853_2_);
	}

	@Override
	public Item getTabIconItem() {
		
		if(ModItems.missile_nuclear != null)
		{
			return ModItems.missile_nuclear;
		}
		
		return Items.iron_pickaxe;
	}
}
