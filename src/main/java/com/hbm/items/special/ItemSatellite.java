package com.hbm.items.special;

import java.util.List;

import com.hbm.items.ISatChip;
import com.hbm.items.ItemEnumMulti;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemSatellite extends ItemEnumMulti implements ISatChip {

	public ItemSatellite() {
		super(EnumSatType.class, true, true);
	}

	public static enum EnumSatType {
		SPY,
		SCANNER,
		RADAR,
		MINER_ASTRO,
		MINER_LUNAR,
		PRECISION_LASER,
		DEATH_RAY,
		XENIUM_RESONATOR,
		RELAY,
		DETECTOR,
		RAY_SCAN,
		SCIENCE,
		SCIENCE_ASSEMBLER,
		SCIENCE_SENSOR,
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		list.add(I18nUtil.resolveKey("satchip.frequency") + ": " + getFreq(stack));
	}
}
