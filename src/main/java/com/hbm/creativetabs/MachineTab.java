package com.hbm.creativetabs;

import com.hbm.blocks.ModBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class MachineTab extends CreativeTabs {

	public MachineTab(int p_i1853_1_, String p_i1853_2_) {
		super(p_i1853_1_, p_i1853_2_);
	}

	@Override
	public Item getTabIconItem() {
		
		if(ModBlocks.machine_generator != null)
		{
			return Item.getItemFromBlock(ModBlocks.machine_rtg_grey);
		}
		
		return Items.iron_pickaxe;
	}

}
