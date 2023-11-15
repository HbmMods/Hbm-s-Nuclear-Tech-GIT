package com.hbm.items.bomb;

import java.util.List;

import com.hbm.util.I18nUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemN2 extends Item {
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		for(String s : I18nUtil.resolveKeyArray( "tile.nuke_n2.desc"))
			list.add(s);
	}

}
