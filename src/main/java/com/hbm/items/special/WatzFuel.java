package com.hbm.items.special;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class WatzFuel extends ItemHazard {

	public int lifeTime;
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
	
	public WatzFuel(float radiation, boolean blinding, int lifeTime, int power, float powerMultiplier, int heat, float heatMultiplier, float decayMultiplier) {
		super(radiation, false, blinding);
		this.lifeTime = lifeTime * 100;
		this.power = power/10;
		this.powerMultiplier = powerMultiplier;
		this.heat = heat;
		this.heatMultiplier = heatMultiplier;
		this.decayMultiplier = decayMultiplier;
		this.setMaxDamage(100);
		this.canRepair = false;
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Max age:          " + this.lifeTime/100 + " ticks");
		list.add("Power per tick:  " + (power) + "HE");
		list.add("Power multiplier: " + (powerMultiplier >= 1 ? "+" : "") + (Math.round(powerMultiplier * 1000) * .10 - 100) + "%");
		list.add("Heat provided:   " + heat + " heat");
		list.add("Heat multiplier:   " + (heatMultiplier >= 1 ? "+" : "") + (Math.round(heatMultiplier * 1000) * .10 - 100) + "%");
		list.add("Decay multiplier: " + (decayMultiplier >= 1 ? "+" : "") + (Math.round(decayMultiplier * 1000) * .10 - 100) + "%");
		
		super.addInformation(itemstack, player, list, bool);
	}
	
	public static void setLifeTime(ItemStack stack, int time) {
		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		
		stack.stackTagCompound.setInteger("life", time);
	}
	
	public static void updateDamage(ItemStack stack) {
		
		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		
		stack.setItemDamage((int)((double)getLifeTime(stack) / (double)((WatzFuel)stack.getItem()).lifeTime * 100D));
	}
	
	public static int getLifeTime(ItemStack stack) {
		if(!stack.hasTagCompound()) {
			stack.stackTagCompound = new NBTTagCompound();
			return 0;
		}
		
		return stack.stackTagCompound.getInteger("life");
	}
}
