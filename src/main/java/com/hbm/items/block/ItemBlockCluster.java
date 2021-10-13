package com.hbm.items.block;

import java.util.List;

import com.hbm.util.I18nUtil;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemBlockCluster extends ItemBlock {
	
	public ItemBlockCluster(Block block) {
		super(block);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		super.addInformation(stack, player, list, bool);
		
		list.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("trait.tile.cluster"));
	}
}
