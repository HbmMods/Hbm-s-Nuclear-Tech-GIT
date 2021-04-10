package com.hbm.items.block;

import java.util.List;

import com.hbm.blocks.machine.BlockHadronCoil;
import com.hbm.util.I18nUtil;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemHadronCoil extends ItemBlock {

	public ItemHadronCoil(Block block) {
		super(block);
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		list.add(I18nUtil.resolveKey("info.coil") + ": " + ((BlockHadronCoil)field_150939_a).factor);
	}
}
