package com.hbm.creativetabs;

import com.hbm.blocks.ModBlocks;
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
		
		if(ModBlocks.nuke_man != null) return Item.getItemFromBlock(ModBlocks.nuke_man);
		return Items.iron_pickaxe;
	}

}
