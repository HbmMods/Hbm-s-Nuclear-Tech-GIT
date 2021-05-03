package com.hbm.items.armor;

import java.util.List;

import com.hbm.handler.ArmorModHandler;
import com.hbm.items.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemModAuto extends ItemArmorMod {

	public ItemModAuto() {
		super(ArmorModHandler.extra, false, true, false, false);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {

		if(this == ModItems.injector_5htp) {
			list.add(EnumChatFormatting.BLUE + "Imported from Japsterdam.");
		}
		if(this == ModItems.injector_knife) {
			list.add(EnumChatFormatting.RED + "Pain.");
			list.add("");
			list.add(EnumChatFormatting.RED + "Hurts, doesn't it?");
		}
		
		list.add("");
		super.addInformation(stack, player, list, bool);
	}
}
