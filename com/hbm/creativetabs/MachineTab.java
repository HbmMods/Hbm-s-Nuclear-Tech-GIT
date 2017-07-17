package com.hbm.creativetabs;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class MachineTab extends CreativeTabs {

	public MachineTab(int p_i1853_1_, String p_i1853_2_) {
		super(p_i1853_1_, p_i1853_2_);
		setBackgroundImageName("item_search.png");
	}

	@Override
	public Item getTabIconItem() {
		
		if(ModItems.assembly_template != null)
		{
			return ModItems.assembly_template;
		}
		
		return Items.iron_pickaxe;
	}

	@Override
	public boolean hasSearchBar() {
		return true;
	}

}
