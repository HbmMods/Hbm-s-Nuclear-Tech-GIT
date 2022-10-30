package com.hbm.items.machine;

import java.util.List;

import com.hbm.items.ISatChip;
import com.hbm.items.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemSatChip extends Item implements ISatChip {

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Satellite frequency: " + getFreq(itemstack));
		
		if(this == ModItems.sat_foeq)
			list.add("Gives you an achievement. That's it.");
		
		if(this == ModItems.sat_gerald) {
			list.add("Single use.");
			list.add("Requires orbital module.");
			list.add("Melter of CPUs, bane of every server owner.");
		}
		
		if(this == ModItems.sat_laser)
			list.add("Allows to summon lasers with a 15 second cooldown.");
		
		if(this == ModItems.sat_mapper)
			list.add("Displays currently loaded chunks.");
		
		if(this == ModItems.sat_miner)
			list.add("Will deliver ore powders to a cargo landing pad.");
		
		if(this == ModItems.sat_lunar_miner)
			list.add("Mines moon turf to deliver it to a cargo landing pad.");
		
		if(this == ModItems.sat_radar)
			list.add("Shows a map of active entities.");
		
		if(this == ModItems.sat_resonator)
			list.add("Allows for teleportation with no cooldown.");
		
		if(this == ModItems.sat_scanner)
			list.add("Creates a topdown map of underground ores.");
	}
}
