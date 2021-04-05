package com.hbm.items.machine;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.items.special.ItemHazard;
import com.hbm.main.MainRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemRBMKRod extends ItemHazard {
	
	String fullName = "";

	public ItemRBMKRod(String fullName) {
		
		this.fullName = fullName;
		
		this.setContainerItem(ModItems.rbmk_fuel_empty);
		this.setMaxStackSize(1);
		this.setCreativeTab(MainRegistry.controlTab);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		
		list.add(EnumChatFormatting.ITALIC + this.fullName);
		super.addInformation(stack, player, list, bool);
	}
}
