package com.hbm.items.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockDecoPipe extends ItemBlock {

	public ItemBlockDecoPipe(Block block) {
		super(block);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		
		super.addInformation(stack, player, list, bool);
		list.add("Purely decorative");
	}
}
