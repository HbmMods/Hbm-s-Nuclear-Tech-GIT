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
			list.add("Mining Drill:");
			list.add("Delay -15 / Consumption +300");
			list.add("");
			list.add("Assembly Machine:");
			list.add("Delay -25 / Consumption +300");
			list.add("");
			list.add("Chemical Plant:");
			list.add("Delay -25 / Consumption +300");
			list.add("");
			list.add("Crystallizer:");
			list.add("Delay -10% / Consumption +1000");
			list.add("");
			list.add("Cyclotron:");
			list.add("Speed x2");
			list.add("");
			list.add("Maxwell:");
			list.add("Damage +0.25/t");
		}
		
		if(this == ModItems.upgrade_speed_2)
		{
			list.add("Mining Drill:");
			list.add("Delay -30 / Consumption +600");
			list.add("");
			list.add("Assembly Machine:");
			list.add("Delay -50 / Consumption +600");
			list.add("");
			list.add("Chemical Plant:");
			list.add("Delay -50 / Consumption +600");
			list.add("");
			list.add("Crystallizer:");
			list.add("Delay -20% / Consumption +2000");
			list.add("");
			list.add("Cyclotron:");
			list.add("Speed x3");
			list.add("");
			list.add("Maxwell:");
			list.add("Damage +0.5/t");
		}
		
		if(this == ModItems.upgrade_speed_3)
		{
			list.add("Mining Drill:");
			list.add("Delay -45 / Consumption +900");
			list.add("");
			list.add("Assembly Machine:");
			list.add("Delay -75 / Consumption +900");
			list.add("");
			list.add("Chemical Plant:");
			list.add("Delay -75 / Consumption +900");
			list.add("");
			list.add("Crystallizer");
			list.add("Speed Delay -30% / Consumption +3000");
			list.add("");
			list.add("Cyclotron");
			list.add("Speed x4");
			list.add("");
			list.add("Maxwell:");
			list.add("Damage +0.75/t");
		}
		
		if(this == ModItems.upgrade_effect_1)
		{
			list.add("Mining Drill:");
			list.add("Radius +1 / Consumption +80");
			list.add("");
			list.add("Crystallizer:");
			list.add("+5% chance of not consuming an item / Acid consumption +1000mB");
			list.add("");
			list.add("Cyclotron:");
			list.add("-50% chance of incrementing overheat counter");
			list.add("");
			list.add("Maxwell:");
			list.add("Range +3m");
		}
		
		if(this == ModItems.upgrade_effect_2)
		{
			list.add("Mining Drill:");
			list.add("Radius +2 / Consumption +160");
			list.add("");
			list.add("Crystallizer:");
			list.add("+10% chance of not consuming an item / Acid consumption +2000mB");
			list.add("");
			list.add("Cyclotron:");
			list.add("-66% chance of incrementing overheat counter");
			list.add("");
			list.add("Maxwell:");
			list.add("Range +6m");
		}
		
		if(this == ModItems.upgrade_effect_3)
		{
			list.add("Mining Drill:");
			list.add("Radius +3 / Consumption +240");
			list.add("");
			list.add("Crystallizer:");
			list.add("+15% chance of not consuming an item / Acid consumption +3000mB");
			list.add("");
			list.add("Cyclotron:");
			list.add("-75% chance of incrementing overheat counter");
			list.add("");
			list.add("Maxwell:");
			list.add("Range +9m");
		}
		
		if(this == ModItems.upgrade_power_1)
		{
			list.add("Mining Drill:");
			list.add("Consumption -30 / Delay +5");
			list.add("");
			list.add("Assembly Machine:");
			list.add("Consumption -30 / Delay +5");
			list.add("");
			list.add("Chemical Plant:");
			list.add("Consumption -30 / Delay +5");
			list.add("");
			list.add("Cyclotron:");
			list.add("Consumption -100k");
			list.add("");
			list.add("Maxwell:");
			list.add("Consumption -150");
			list.add("Consumption when firing -1500");
		}
		
		if(this == ModItems.upgrade_power_2)
		{
			list.add("Mining Drill:");
			list.add("Consumption -60 / Delay +10");
			list.add("");
			list.add("Assembly Machine:");
			list.add("Consumption -60 / Delay +10");
			list.add("");
			list.add("Chemical Plant:");
			list.add("Consumption -60 / Delay +10");
			list.add("");
			list.add("Cyclotron:");
			list.add("Consumption -200k");
			list.add("");
			list.add("Maxwell:");
			list.add("Consumption -300");
			list.add("Consumption when firing -3000");
		}
		
		if(this == ModItems.upgrade_power_3)
		{
			list.add("Mining Drill:");
			list.add("Consumption -90 / Delay +15");
			list.add("");
			list.add("Assembly Machine:");
			list.add("Consumption -90 / Delay +15");
			list.add("");
			list.add("Chemical Plant:");
			list.add("Consumption -90 / Delay +15");
			list.add("");
			list.add("Cyclotron:");
			list.add("Consumption -300k");
			list.add("");
			list.add("Maxwell:");
			list.add("Consumption -450");
			list.add("Consumption when firing -4500");
		}
		
		if(this == ModItems.upgrade_fortune_1)
		{
			list.add("Mining Drill:");
			list.add("Fortune +1 / Delay +15");
		}
		
		if(this == ModItems.upgrade_fortune_2)
		{
			list.add("Mining Drill:");
			list.add("Fortune +2 / Delay +30");
		}
		
		if(this == ModItems.upgrade_fortune_3)
		{
			list.add("Mining Drill:");
			list.add("Fortune +3 / Delay +45");
		}
		
		if(this == ModItems.upgrade_afterburn_1)
		{
			list.add("Turbofan:");
			list.add("Production x2 / Consumption x2.5");
			list.add("");
			list.add("Maxwell:");
			list.add("Afterburn +3s");
		}
		
		if(this == ModItems.upgrade_afterburn_2)
		{
			list.add("Turbofan:");
			list.add("Production x3 / Consumption x5");
			list.add("");
			list.add("Maxwell:");
			list.add("Afterburn +6s");
		}
		
		if(this == ModItems.upgrade_afterburn_3)
		{
			list.add("Turbofan:");
			list.add("Production x4 / Consumption x7.5");
			list.add("");
			list.add("Maxwell:");
			list.add("Afterburn +9s");
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
		
		if(this == ModItems.upgrade_smelter)
		{
			list.add("Mining Laser Upgrade");
			list.add("Smelts blocks. Easy enough.");
		}
		
		if(this == ModItems.upgrade_shredder)
		{
			list.add("Mining Laser Upgrade");
			list.add("Crunches ores");
		}
		
		if(this == ModItems.upgrade_centrifuge)
		{
			list.add("Mining Laser Upgrade");
			list.add("Hopefully self-explanatory");
		}
		
		if(this == ModItems.upgrade_crystallizer)
		{
			list.add("Mining Laser Upgrade");
			list.add("Your new best friend");
		}
		
		if(this == ModItems.upgrade_screm)
		{
			list.add("Mining Laser Upgrade");
			list.add("It's like in Super Mario where all blocks are");
			list.add("actually Toads, but here it's Half-Life scientists");
			list.add("and they scream. A lot.");
		}
		
		if(this == ModItems.upgrade_nullifier)
		{
			list.add("Mining Laser Upgrade");
			list.add("50% chance to override worthless items with /dev/zero");
			list.add("50% chance to move worthless items to /dev/null");
		}
	}

}
