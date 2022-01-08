package com.hbm.items.machine;

import java.util.List;

import com.hbm.util.BobMathUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemZirnoxBreedingRod extends ItemZirnoxRod {

	public ItemZirnoxBreedingRod(int life, int heat) {
		super(life, heat);
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		
		list.add(EnumChatFormatting.YELLOW + "[ZIRNOX Breeding Rod]");
		list.add(EnumChatFormatting.DARK_AQUA + "  Place next to fuel rods to breed");
		list.add(EnumChatFormatting.DARK_AQUA + "  Lasts " + BobMathUtil.getShortNumber(lifeTime) + " ticks");
	}
}