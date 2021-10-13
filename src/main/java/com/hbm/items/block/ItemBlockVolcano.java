package com.hbm.items.block;

import java.util.List;

import com.hbm.blocks.bomb.BlockVolcano;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemBlockVolcano extends ItemBlock {

	public ItemBlockVolcano(Block block) {
		super(block);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

	public int getMetadata(int meta) {
		return meta;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		
		int meta = stack.getItemDamage();

		list.add(BlockVolcano.isGrowing(meta) ? (EnumChatFormatting.RED + "DOES GROW") : (EnumChatFormatting.DARK_GRAY + "DOES NOT GROW"));
		list.add(BlockVolcano.isExtinguishing(meta) ? (EnumChatFormatting.RED + "DOES EXTINGUISH") : (EnumChatFormatting.DARK_GRAY + "DOES NOT EXTINGUISH"));
	}
}
