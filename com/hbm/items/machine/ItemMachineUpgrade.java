package com.hbm.items.machine;

import java.util.List;

import com.hbm.items.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemMachineUpgrade extends Item {
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		
		if(this == ModItems.upgrade_speed_1)
		{
			list.add("Speed Upgrade");
			list.add("Mining Drill:");
			list.add("Delay -15 / Consumption +300");
			list.add("");
			list.add("Assembly Machine:");
			list.add("Delay -25 / Consumption +300");
			list.add("");
			list.add("Chemical Plant:");
			list.add("Delay -25 / Consumption +300");
		}
		
		if(this == ModItems.upgrade_speed_2)
		{
			list.add("Speed Upgrade");
			list.add("Mining Drill:");
			list.add("Delay -30 / Consumption +600");
			list.add("");
			list.add("Assembly Machine:");
			list.add("Delay -50 / Consumption +600");
			list.add("");
			list.add("Chemical Plant:");
			list.add("Delay -50 / Consumption +600");
		}
		
		if(this == ModItems.upgrade_speed_3)
		{
			list.add("Speed Upgrade");
			list.add("Mining Drill:");
			list.add("Delay -45 / Consumption +900");
			list.add("");
			list.add("Assembly Machine:");
			list.add("Delay -75 / Consumption +900");
			list.add("");
			list.add("Chemical Plant:");
			list.add("Delay -75 / Consumption +900");
		}
		
		if(this == ModItems.upgrade_effect_1)
		{
			list.add("Effectiveness Upgrade");
			list.add("Mining Drill:");
			list.add("Radius +1 / Consumption +80");
		}
		
		if(this == ModItems.upgrade_effect_2)
		{
			list.add("Effectiveness Upgrade");
			list.add("Mining Drill:");
			list.add("Radius +2 / Consumption +160");
		}
		
		if(this == ModItems.upgrade_effect_3)
		{
			list.add("Effectiveness Upgrade");
			list.add("Mining Drill:");
			list.add("Radius +3 / Consumption +240");
		}
		
		if(this == ModItems.upgrade_power_1)
		{
			list.add("Power Saving Upgrade");
			list.add("Mining Drill:");
			list.add("Consumption -30 / Delay +5");
			list.add("");
			list.add("Assembly Machine:");
			list.add("Consumption -30 / Delay +5");
			list.add("");
			list.add("Chemical Plant:");
			list.add("Consumption -30 / Delay +5");
		}
		
		if(this == ModItems.upgrade_power_2)
		{
			list.add("Power Saving Upgrade");
			list.add("Mining Drill:");
			list.add("Consumption -60 / Delay +10");
			list.add("");
			list.add("Assembly Machine:");
			list.add("Consumption -60 / Delay +10");
			list.add("");
			list.add("Chemical Plant:");
			list.add("Consumption -60 / Delay +10");
		}
		
		if(this == ModItems.upgrade_power_3)
		{
			list.add("Power Saving Upgrade");
			list.add("Mining Drill:");
			list.add("Consumption -90 / Delay +15");
			list.add("");
			list.add("Assembly Machine:");
			list.add("Consumption -90 / Delay +15");
			list.add("");
			list.add("Chemical Plant:");
			list.add("Consumption -90 / Delay +15");
		}
		
		if(this == ModItems.upgrade_fortune_1)
		{
			list.add("Fortune Upgrade");
			list.add("Mining Drill:");
			list.add("Fortune +1 / Delay +15");
		}
		
		if(this == ModItems.upgrade_fortune_2)
		{
			list.add("Fortune Upgrade");
			list.add("Mining Drill:");
			list.add("Fortune +2 / Delay +30");
		}
		
		if(this == ModItems.upgrade_fortune_3)
		{
			list.add("Fortune Upgrade");
			list.add("Mining Drill:");
			list.add("Fortune +3 / Delay +45");
		}
		
		if(this == ModItems.upgrade_afterburn_1)
		{
			list.add("Afterburner Upgrade");
			list.add("Turbofan:");
			list.add("Production x2 / Consumption x2.5");
		}
		
		if(this == ModItems.upgrade_afterburn_2)
		{
			list.add("Afterburner Upgrade");
			list.add("Turbofan:");
			list.add("Production x3 / Consumption x5");
		}
		
		if(this == ModItems.upgrade_afterburn_3)
		{
			list.add("Afterburner Upgrade");
			list.add("Turbofan:");
			list.add("Production x4 / Consumption x7.5");
		}
		
		if(this == ModItems.upgrade_radius)
		{
			list.add("Forcefield Range Upgrade");
			list.add("Radius +16 / Consumption +500");
			list.add("");
			list.add("Stacks to 16");
		}
		
		if(this == ModItems.upgrade_health)
		{
			list.add("Forcefield Health Upgrade");
			list.add("Max. Health +50 / Consumption +250");
			list.add("");
			list.add("Stacks to 16");
		}
		
		if(this == ModItems.upgrade_screm)
		{
			list.add("It's like in Super Mario where all blocks are");
			list.add("actually Toads, but here it's Half-Life scientists");
			list.add("and they scream. A lot.");
		}
	}

}
