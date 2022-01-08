package com.hbm.items.machine;

import java.util.List;

import com.hbm.util.BobMathUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemZirnoxRod extends ItemFuelRod {
	
	public int heat;
	
	public ItemZirnoxRod(int life, int heat) {
		super(life);
		this.heat = heat;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		
		list.add(EnumChatFormatting.YELLOW + "[ZIRNOX Fuel Rod]");
		list.add(EnumChatFormatting.DARK_AQUA + "  Generates " + heat + " heat per tick");
		list.add(EnumChatFormatting.DARK_AQUA + "  Lasts " + BobMathUtil.getShortNumber(lifeTime) + " ticks");
	}
}