package com.hbm.items.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemBlockBlastInfo extends ItemBlockBase {

	public ItemBlockBlastInfo(Block block) {
		super(block);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		super.addInformation(stack, player, list, bool);
		list.add(EnumChatFormatting.GOLD + "Blast Resistance: " + field_150939_a.getExplosionResistance(null));
	}
}
