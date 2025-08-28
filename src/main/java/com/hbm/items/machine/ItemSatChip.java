package com.hbm.items.machine;

import java.util.List;

import com.hbm.items.ISatChip;
import com.hbm.items.ModItems;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemSatChip extends Item implements ISatChip {

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add(I18nUtil.resolveKey("satchip.frequency") + ": " + getFreq(itemstack));

		if(this == ModItems.sat_foeq)
			list.add(I18nUtil.resolveKey("satchip.foeq"));

		if (this == ModItems.sat_gerald) {
			String[] lines = I18nUtil.resolveKeyArray("satchip.gerald.desc");
			for (String line : lines) {
				list.add(line);
			}
		}

		if(this == ModItems.sat_laser)
			list.add(I18nUtil.resolveKey("satchip.laser"));

		if(this == ModItems.sat_mapper)
			list.add(I18nUtil.resolveKey("satchip.mapper"));

		if(this == ModItems.sat_miner)
			list.add(I18nUtil.resolveKey("satchip.miner"));

		if(this == ModItems.sat_lunar_miner)
			list.add(I18nUtil.resolveKey("satchip.lunar_miner"));

		if(this == ModItems.sat_radar)
			list.add(I18nUtil.resolveKey("satchip.radar"));

		if(this == ModItems.sat_resonator)
			list.add(I18nUtil.resolveKey("satchip.resonator"));

		if(this == ModItems.sat_scanner)
			list.add(I18nUtil.resolveKey("satchip.scanner"));
	}
}
