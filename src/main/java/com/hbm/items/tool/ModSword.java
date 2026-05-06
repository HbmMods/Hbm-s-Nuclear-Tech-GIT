package com.hbm.items.tool;

import java.util.List;

import com.hbm.items.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class ModSword extends ItemSword {

	public ModSword(ToolMaterial mat) {
		super(mat);
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		if(this == ModItems.pipe_lead)
			list.add("I'm going to attempt a manual override on this wall.");
		
		if(this == ModItems.reer_graar) {
			list.add("Call now!");
			list.add("555-10-3728-ZX7-INFINITE");
		}
	}
}
