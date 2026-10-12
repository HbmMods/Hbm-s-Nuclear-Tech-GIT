package com.hbm.items.machine;

import java.util.List;

import com.hbm.items.ISatChip;
import com.hbm.items.ItemEnumMulti;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemOrbitalAssembly extends ItemEnumMulti implements ISatChip {

	public ItemOrbitalAssembly() {
		super(EnumOrbitalAssembly.class, true, false);
	}
	
	public static enum EnumOrbitalAssembly {
		CRYSTAL_CIRCUIT,
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		list.add(EnumChatFormatting.AQUA + I18nUtil.resolveKey("satchip.frequency") + ": " + getFreq(stack));
	}
}
