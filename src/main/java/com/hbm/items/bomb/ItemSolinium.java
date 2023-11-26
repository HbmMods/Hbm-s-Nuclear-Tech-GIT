package com.hbm.items.bomb;

import java.util.List;

import com.hbm.util.I18nUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemSolinium extends Item {

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		for(String s : I18nUtil.resolveKeyArray( "tile.nuke_solinium.desc"))
			list.add(s);
		super.addInformation(itemstack, player, list, bool);
	}
}
