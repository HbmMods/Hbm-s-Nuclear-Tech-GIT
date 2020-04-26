package com.hbm.items.tool;

import java.util.List;

import com.hbm.items.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemSatChip extends Item {

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Satellite frequency: " + getFreq(itemstack));
		
		if(this == ModItems.sat_foeq)
			list.add("Gives you an achievement. That's it.");
		
		if(this == ModItems.sat_gerald)
			list.add("Unused (for now)");
		
		if(this == ModItems.sat_laser)
			list.add("Allows to summon lasers with a 15 second cooldown.");
		
		if(this == ModItems.sat_mapper)
			list.add("Displays currently loaded chunks.");
		
		if(this == ModItems.sat_miner)
			list.add("Will deliver ore powders to a cargo landing pad.");
		
		if(this == ModItems.sat_radar)
			list.add("Shows a map of active entities.");
		
		if(this == ModItems.sat_resonator)
			list.add("Unused");
		
		if(this == ModItems.sat_scanner)
			list.add("Creates a topdown map of underground ores.");
	}

	public static int getFreq(ItemStack stack) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
			return 0;
		}
		return stack.stackTagCompound.getInteger("freq");
	}
	
	public static void setFreq(ItemStack stack, int i) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
		}
		stack.stackTagCompound.setInteger("freq", i);
	}

}
