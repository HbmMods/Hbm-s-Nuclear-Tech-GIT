package com.hbm.hazard.transformer;

import java.util.List;

import com.hbm.hazard.HazardEntry;

import net.minecraft.item.ItemStack;

public abstract class HazardTransformerBase {

	public abstract void transformPre(ItemStack stack, List<HazardEntry> entries);
	public abstract void transformPost(ItemStack stack, List<HazardEntry> entries);
}
