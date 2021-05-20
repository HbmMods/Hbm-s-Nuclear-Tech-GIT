package com.hbm.items.machine;

import java.util.List;

import com.hbm.items.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBlades extends Item {
	
	public ItemBlades(int dura)
	{
		this.setMaxDamage(dura);
		setMaxStackSize(1);
	}
}
