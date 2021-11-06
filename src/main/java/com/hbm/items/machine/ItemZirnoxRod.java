package com.hbm.items.machine;

import java.util.List;

import com.hbm.lib.Library;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemZirnoxRod extends ItemFuelRod {
	
	public ItemZirnoxRod(int life, int heat) {
		super(life, heat);
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add(EnumChatFormatting.YELLOW + "[ZIRNOX Fuel Rod]");
		
		list.add(EnumChatFormatting.DARK_AQUA + "  Generates " + heat + " heat per tick");
		list.add(EnumChatFormatting.DARK_AQUA + "  Lasts " + Library.getShortNumber(lifeTime) + " ticks");
	}
	
}
