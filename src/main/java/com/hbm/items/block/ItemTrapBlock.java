package com.hbm.items.block;

import java.util.List;

import com.hbm.blocks.generic.TrappedBrick.Trap;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemTrapBlock extends ItemBlock {

	public ItemTrapBlock(Block block) {
		super(block);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		list.add(Trap.get(itemstack.getItemDamage()).toString());
	}

	public int getMetadata(int meta) {
		return meta;
	}
}
