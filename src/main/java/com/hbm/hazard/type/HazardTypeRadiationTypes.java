package com.hbm.hazard.type;

import java.util.List;

import com.hbm.hazard.modifier.HazardModifier;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class HazardTypeRadiationTypes extends HazardTypeBase
{

	@Override
	public void onUpdate(EntityLivingBase target, float level, ItemStack stack)
	{
		// Not needed
	}

	@Override
	public void updateEntity(EntityItem item, float level)
	{
		// Not needed
	}

	@Override
	public void addHazardInformation(
			EntityPlayer player, List<String> list, float level, ItemStack stack, List<HazardModifier> modifiers
	)
	{
		// Not needed
	}

}
