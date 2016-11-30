package com.hbm.items.bomb;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemPrototypeBlock extends ItemBlock {

	public ItemPrototypeBlock(Block p_i45328_1_) {
		super(p_i45328_1_);
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add("It didn't have to be like this.");
		list.add("");
		list.add("You monster.");
		
		/*list.add("In memory of Euphemia.");
		list.add("");
		list.add("Rest in spaghetti, never forgetti.");*/
	}

}
