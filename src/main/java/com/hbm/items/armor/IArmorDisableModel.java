package com.hbm.items.armor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IArmorDisableModel {

	public boolean disablesPart(EntityPlayer player, ItemStack stack, EnumPlayerPart part);
	
	public static enum EnumPlayerPart {
		HEAD,
		HAT,
		BODY,
		LEFT_ARM,
		RIGHT_ARM,
		LEFT_LEG,
		RIGHT_LEG
	}
}
