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
		list.add(I18nUtil.resolveKey("item.sat.desc.frequency") + ": " + getFreq(itemstack));

		if(this == ModItems.sat_foeq)
			list.add(I18nUtil.resolveKey("item.sat.desc.foeq"));

		if(this == ModItems.sat_gerald) {
			list.add(I18nUtil.resolveKey("item.sat.desc.gerald.single_use"));
			list.add(I18nUtil.resolveKey("item.sat.desc.gerald.orbital_module"));
			list.add(I18nUtil.resolveKey("item.sat.desc.gerald.melter"));
		}

		if(this == ModItems.sat_laser)
			list.add(I18nUtil.resolveKey("item.sat.desc.laser"));

		if(this == ModItems.sat_mapper)
			list.add(I18nUtil.resolveKey("item.sat.desc.mapper"));

		if(this == ModItems.sat_miner)
			list.add(I18nUtil.resolveKey("item.sat.desc.miner"));

		if(this == ModItems.sat_lunar_miner)
			list.add(I18nUtil.resolveKey("item.sat.desc.lunar_miner"));

		if(this == ModItems.sat_radar)
			list.add(I18nUtil.resolveKey("item.sat.desc.radar"));

		if(this == ModItems.sat_resonator)
			list.add(I18nUtil.resolveKey("item.sat.desc.resonator"));

		if(this == ModItems.sat_scanner)
			list.add(I18nUtil.resolveKey("item.sat.desc.scanner"));
	}
}
