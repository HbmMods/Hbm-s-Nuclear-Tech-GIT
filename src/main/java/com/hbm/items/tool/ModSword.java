package com.hbm.items.tool;

import java.util.List;

import com.hbm.items.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class ModSword extends ItemSword {

	public ModSword(ToolMaterial p_i45356_1_) {
		super(p_i45356_1_);
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		if(this == ModItems.saw)
			list.add("Prepare for your examination!");
		if(this == ModItems.bat)
			list.add("Do you like hurting other people?");
		if(this == ModItems.bat_nail)
			list.add("Or is it a classic?");
		if(this == ModItems.golf_club)
			list.add("Property of Miami Beach Golf Club.");
		if(this == ModItems.pipe_rusty)
			list.add("Ouch! Ouch! Ouch!");
		if(this == ModItems.pipe_lead)
			list.add("Manually override anything by smashing it with this pipe.");
			//list.add("I'm going to attempt a manual override on this wall.");
		if(this == ModItems.reer_graar) {
			list.add("Call now!");
			list.add("555-10-3728-ZX7-INFINITE");
		}
	}
}
