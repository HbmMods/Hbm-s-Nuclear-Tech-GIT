package com.hbm.items.special;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class WatzFuel extends ItemRadioactive {
	
	public int power;
	public float powerMultiplier;
	public int heat;
	public float heatMultiplier;
	public float decayMultiplier;
	
	/**
	 * Constructor for a new Watz fuel pellet
	 * @param lifeTime
	 * @param power
	 * @param powerMultiplier
	 * @param heat
	 * @param heatMultiplier
	 * @param decayMultiplier
	 */
	
	public WatzFuel(int lifeTime, int power, float powerMultiplier, int heat, float heatMultiplier, float decayMultiplier) {
		this.setMaxDamage(lifeTime * 100);
		this.power = power/10;
		this.powerMultiplier = powerMultiplier;
		this.heat = heat;
		this.heatMultiplier = heatMultiplier;
		this.decayMultiplier = decayMultiplier;
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Max age:          " + (this.getMaxDamage()/100) + " ticks");
		list.add("Power per tick:  " + (power) + "HE");
		list.add("Power multiplier: " + (powerMultiplier >= 1 ? "+" : "") + (Math.round(powerMultiplier * 1000) * .10 - 100) + "%");
		list.add("Heat provided:   " + heat + " heat");
		list.add("Heat multiplier:   " + (heatMultiplier >= 1 ? "+" : "") + (Math.round(heatMultiplier * 1000) * .10 - 100) + "%");
		list.add("Decay multiplier: " + (decayMultiplier >= 1 ? "+" : "") + (Math.round(decayMultiplier * 1000) * .10 - 100) + "%");
	}
}
