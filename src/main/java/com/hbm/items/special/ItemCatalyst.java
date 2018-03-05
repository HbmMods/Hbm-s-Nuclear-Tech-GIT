package com.hbm.items.special;

import java.util.List;

import com.hbm.items.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemCatalyst extends Item {

	int color;
	long powerAbs;
	float powerMod;
	float heatMod;
	float fuelMod;
	
	public ItemCatalyst(int color) {
		this.color = color;
		this.powerAbs = 0;
		this.powerMod = 1.0F;
		this.heatMod = 1.0F;
		this.fuelMod = 1.0F;
	}
	
	public ItemCatalyst(int color, long powerAbs, float powerMod, float heatMod, float fuelMod) {
		this.color = color;
		this.powerAbs = powerAbs;
		this.powerMod = powerMod;
		this.heatMod = heatMod;
		this.fuelMod = fuelMod;
	}
	
	public int getColor() {
		return this.color;
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Absolute Energy Bonus: " + powerAbs + "HE");
		list.add("Energy Modifier:           " + (powerMod >= 1 ? "+" : "") + (Math.round(powerMod * 1000) * .10 - 100) + "%");
		list.add("Heat Modifier:               " + (heatMod >= 1 ? "+" : "") + (Math.round(heatMod * 1000) * .10 - 100) + "%");
		list.add("Fuel Modifier:               " + (fuelMod >= 1 ? "+" : "") + (Math.round(fuelMod * 1000) * .10 - 100) + "%");
	}
	
	public static long getPowerAbs(ItemStack stack) {
		if(stack == null || !(stack.getItem() instanceof ItemCatalyst))
			return 0;
		return ((ItemCatalyst)stack.getItem()).powerAbs;
	}
	
	public static float getPowerMod(ItemStack stack) {
		if(stack == null || !(stack.getItem() instanceof ItemCatalyst))
			return 0;
		return ((ItemCatalyst)stack.getItem()).powerMod;
	}
	
	public static float getHeatMod(ItemStack stack) {
		if(stack == null || !(stack.getItem() instanceof ItemCatalyst))
			return 0;
		return ((ItemCatalyst)stack.getItem()).heatMod;
	}
	
	public static float getFuelMod(ItemStack stack) {
		if(stack == null || !(stack.getItem() instanceof ItemCatalyst))
			return 0;
		return ((ItemCatalyst)stack.getItem()).fuelMod;
	}

}
