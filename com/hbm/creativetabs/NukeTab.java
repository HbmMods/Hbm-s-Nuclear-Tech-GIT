package com.hbm.creativetabs;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class NukeTab extends CreativeTabs {

	public NukeTab(int p_i1853_1_, String p_i1853_2_) {
		super(p_i1853_1_, p_i1853_2_);
		this.setBackgroundImageName("nuke.png");
	}

	@Override
	public Item getTabIconItem() {
		
		if(ModBlocks.float_bomb != null)
		{
			return Item.getItemFromBlock(ModBlocks.float_bomb);
		}
		
		return Items.iron_pickaxe;
	}

}
