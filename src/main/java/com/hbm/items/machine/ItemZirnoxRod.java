package com.hbm.items.machine;

import java.util.HashMap;
import java.util.List;

import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemZirnoxRod extends ItemFuelRod {
	
	public ItemZirnoxRod(float radiation, boolean blinding, int life, int heat) {
		super(radiation, blinding, life, heat);
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add(EnumChatFormatting.YELLOW + "[ZIRNOX Fuel Rod]");
		
		list.add(EnumChatFormatting.DARK_AQUA + "  Generates " + heat + " heat per tick");
		list.add(EnumChatFormatting.DARK_AQUA + "  Lasts " + Library.getShortNumber(lifeTime) + " ticks");
	}
	
}
