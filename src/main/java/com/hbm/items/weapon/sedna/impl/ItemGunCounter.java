package com.hbm.items.weapon.sedna.impl;

import com.hbm.items.IStatTrack;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class ItemGunCounter extends ItemGunBaseNT implements IStatTrack {
	
	public static final String KEY_STATTRACK = "stattrack";

	public ItemGunCounter(WeaponQuality quality, GunConfig... cfg) {
		super(quality, cfg);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		int score = 0;
		if(stack.hasTagCompound()) score = getScore(stack);
		return "" + score;
	}

	@Override
	public void tickUp(ItemStack stack, EntityLivingBase target) {
		this.setScore(stack, this.getScore(stack) + 1);
	}
	
	public static int getScore(ItemStack stack) { return getValueInt(stack, KEY_STATTRACK); }
	public static void setScore(ItemStack stack, int value) { setValueInt(stack, KEY_STATTRACK, value); }
}
