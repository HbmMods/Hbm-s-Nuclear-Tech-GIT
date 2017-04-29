package com.hbm.items.special;

import java.util.List;

import com.hbm.items.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemFuelRod extends ItemRadioactive {
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Used in nuclear reactor");
		
		if(this == ModItems.rod_uranium_fuel)
		{
			list.add("Generates 100 power per tick");
			list.add("Generates 1 heat per tick");
			list.add("Lasts 10000 ticks");
		}
		
		if(this == ModItems.rod_dual_uranium_fuel)
		{
			list.add("Generates 100 power per tick");
			list.add("Generates 1 heat per tick");
			list.add("Lasts 20000 ticks");
		}
		
		if(this == ModItems.rod_quad_uranium_fuel)
		{
			list.add("Generates 100 power per tick");
			list.add("Generates 1 heat per tick");
			list.add("Lasts 40000 ticks");
		}
		
		if(this == ModItems.rod_plutonium_fuel)
		{
			list.add("Generates 150 power per tick");
			list.add("Generates 2 heat per tick");
			list.add("Lasts 25000 ticks");
		}
		
		if(this == ModItems.rod_dual_plutonium_fuel)
		{
			list.add("Generates 150 power per tick");
			list.add("Generates 2 heat per tick");
			list.add("Lasts 50000 ticks");
		}
		
		if(this == ModItems.rod_quad_plutonium_fuel)
		{
			list.add("Generates 150 power per tick");
			list.add("Generates 2 heat per tick");
			list.add("Lasts 100000 ticks");
		}
		
		if(this == ModItems.rod_mox_fuel)
		{
			list.add("Generates 50 power per tick");
			list.add("Generates 1 heat per tick");
			list.add("Lasts 100000 ticks");
		}
		
		if(this == ModItems.rod_dual_mox_fuel)
		{
			list.add("Generates 50 power per tick");
			list.add("Generates 1 heat per tick");
			list.add("Lasts 200000 ticks");
		}
		
		if(this == ModItems.rod_quad_mox_fuel)
		{
			list.add("Generates 50 power per tick");
			list.add("Generates 1 heat per tick");
			list.add("Lasts 400000 ticks");
		}
		
		if(this == ModItems.rod_schrabidium_fuel)
		{
			list.add("Generates 25000 power per tick");
			list.add("Generates 10 heat per tick");
			list.add("Lasts 2500000 ticks");
		}
		
		if(this == ModItems.rod_dual_schrabidium_fuel)
		{
			list.add("Generates 25000 power per tick");
			list.add("Generates 10 heat per tick");
			list.add("Lasts 5000000 ticks");
		}
		
		if(this == ModItems.rod_quad_schrabidium_fuel)
		{
			list.add("Generates 25000 power per tick");
			list.add("Generates 10 heat per tick");
			list.add("Lasts 10000000 ticks");
		}
	}

}
