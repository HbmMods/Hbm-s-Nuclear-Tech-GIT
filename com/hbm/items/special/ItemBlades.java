package com.hbm.items.special;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemCassette;
import com.hbm.items.tool.ItemCassette.TrackType;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBlades extends Item {
	
	public ItemBlades(int dura)
	{
		this.setMaxDamage(dura);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
		if(this == ModItems.stamp_iron_circuit ||
				this == ModItems.stamp_iron_plate ||
				this == ModItems.stamp_iron_wire ||
				this == ModItems.stamp_obsidian_circuit ||
				this == ModItems.stamp_obsidian_plate ||
				this == ModItems.stamp_obsidian_wire ||
				this == ModItems.stamp_schrabidium_circuit ||
				this == ModItems.stamp_schrabidium_plate ||
				this == ModItems.stamp_schrabidium_wire ||
				this == ModItems.stamp_steel_circuit ||
				this == ModItems.stamp_steel_plate ||
				this == ModItems.stamp_steel_wire ||
				this == ModItems.stamp_titanium_circuit ||
				this == ModItems.stamp_titanium_plate ||
				this == ModItems.stamp_titanium_wire ||
				this == ModItems.stamp_stone_circuit ||
				this == ModItems.stamp_stone_plate ||
				this == ModItems.stamp_stone_wire)
		list.add("[CREATED USING TEMPLATE FOLDER]");
	}

}
