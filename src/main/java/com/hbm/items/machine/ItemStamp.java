package com.hbm.items.machine;

import java.util.List;

import com.hbm.items.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemStamp extends Item {
	
	public ItemStamp(int dura)
	{
		this.setMaxDamage(dura);
		setMaxStackSize(1);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
		String name = this.getUnlocalizedName();
		if (!name.contains("flat") && !name.contains("9") && !name.contains("44") && !name.contains("357") && !name.contains("50"))
			list.add("[CREATED USING TEMPLATE FOLDER]");
	}

}
