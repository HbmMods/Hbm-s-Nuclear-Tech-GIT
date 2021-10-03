package com.hbm.modules;

import java.util.List;

import com.google.common.annotations.Beta;
import com.hbm.interfaces.Untested;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

@Untested
@Beta
public class BlockRadShieldModule
{
	float EMShield;
	float neutShield;
	
	public void addEMShield(float shield)
	{
		EMShield = shield;
	}
	public void addNeutronShield(float shield)
	{
		neutShield = shield;
	}
	
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
		if (EMShield > 0)
			list.add("Block has an electromagnetic radiation shielding potential of: " + EMShield);
		if (neutShield > 0)
			list.add("Block has a neutron radiation shielding potential of: " + neutShield);
	}
}
