package com.hbm.items.bomb;

import java.util.List;

import com.hbm.util.I18nUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemMissileShuttle extends Item {

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		for(String s : I18nUtil.resolveKeyArray( "item.missile_shuttle.desc"))
			list.add(s);
	}
}