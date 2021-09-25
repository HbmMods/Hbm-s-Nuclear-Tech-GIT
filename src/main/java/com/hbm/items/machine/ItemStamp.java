package com.hbm.items.machine;

import java.util.List;

import com.hbm.inventory.MachineRecipes.StampType;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemStamp extends Item
{
	private StampType type = StampType.FLAT;
	public ItemStamp(int dura, StampType typeIn)
	{
		this.setMaxDamage(dura);
		setMaxStackSize(1);
		type = typeIn;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
		switch (type)
		{
		case CIRCUIT:
		case DISC:
		case PLATE:
		case WIRE:
			list.add("[CREATED USING TEMPLATE FOLDER]");
			break;
		default:
			break;
		}
	}
	
	public StampType getType()
	{
		return type;
	}

}
