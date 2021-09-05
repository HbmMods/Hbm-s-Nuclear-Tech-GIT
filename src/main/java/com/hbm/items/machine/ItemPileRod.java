package com.hbm.items.machine;

import java.util.List;

import com.hbm.items.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemPileRod extends Item {
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {

		list.add(EnumChatFormatting.YELLOW + "Use on drilled graphite to insert");
		list.add(EnumChatFormatting.YELLOW + "Use screwdriver to extract");
		list.add("");
		
		if(this == ModItems.pile_rod_uranium) {
			list.add(EnumChatFormatting.GREEN + "[Reactive Fuel]");
			list.add(EnumChatFormatting.YELLOW + "Use hand drill to take core sample");
		}
		
		if(this == ModItems.pile_rod_boron) {
			list.add(EnumChatFormatting.BLUE + "[Neutron Absorber]");
			list.add(EnumChatFormatting.YELLOW + "Click to toggle");
		}
		
		if(this == ModItems.pile_rod_source || this == ModItems.pile_rod_plutonium) {
			list.add(EnumChatFormatting.LIGHT_PURPLE + "[Neutron Source]");
		}
	}
}
