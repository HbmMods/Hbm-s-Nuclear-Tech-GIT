package com.hbm.module;

import java.util.ArrayList;
import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.util.ItemStackUtil;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumChatFormatting;

/**
 * A simple module for determining the burn time of a stack with added options to define bonuses
 * @author hbm
 *
 */
public class ModuleBurnTime {

	private double modLog = 1.0D;
	private double modWood = 1.0D;
	private double modCoal = 1.0D;
	private double modLignite = 1.0D;
	private double modCoke = 1.0D;
	private double modSolid = 1.0D;
	private double modRocket = 1.0D;
	
	public int getBurnTime(ItemStack stack) {
		int fuel = TileEntityFurnace.getItemBurnTime(stack);
		
		if(fuel == 0)
			return 0;

		if(stack.getItem() == ModItems.solid_fuel)					return (int) (fuel * modSolid);
		if(stack.getItem() == ModItems.solid_fuel_presto) 			return (int) (fuel * modSolid);
		if(stack.getItem() == ModItems.solid_fuel_presto_triplet)	return (int) (fuel * modSolid);
		
		if(stack.getItem() == ModItems.rocket_fuel)					return (int) (fuel * modRocket);
		
		List<String> names = ItemStackUtil.getOreDictNames(stack);
		
		for(String name : names) {
			if(name.contains("Coke"))		return (int) (fuel * modCoke);
			if(name.contains("Coal"))		return (int) (fuel * modCoal);
			if(name.contains("Lignite"))	return (int) (fuel * modLignite);
			if(name.startsWith("log"))		return (int) (fuel * modLog);
			if(name.contains("Wood"))		return (int) (fuel * modWood);
		}
		
		return fuel;
	}
	
	public List<String> getDesc() {
		List<String> list = new ArrayList();

		list.add(EnumChatFormatting.GOLD + "Burn time bonuses:");
		
		addIf(list, "Logs", modLog);
		addIf(list, "Wood", modWood);
		addIf(list, "Coal", modCoal);
		addIf(list, "Lignite", modLignite);
		addIf(list, "Coke", modCoke);
		addIf(list, "Solid Fuel", modSolid);
		addIf(list, "Rocket Fuel", modRocket);
		
		if(list.size() == 1)
			list.clear();
		
		return list;
	}
	
	private void addIf(List<String> list, String name, double mod) {
		
		if(mod != 1.0D)
			list.add(EnumChatFormatting.YELLOW + "- " + name + ": " + getPercent(mod));
	}
	
	private String getPercent(double mod) {
		mod -= 1D;
		String num = ((int) (mod * 100)) + "%";
		
		if(mod < 0)
			num = EnumChatFormatting.RED + "-" + num;
		else
			num = EnumChatFormatting.GREEN + "+" + num;
		
		return num;
	}
	
	public ModuleBurnTime setLogMod(double mod) { this.modLog = mod; return this; }
	public ModuleBurnTime setWoodMod(double mod) { this.modWood = mod; return this; }
	public ModuleBurnTime setCoalMod(double mod) { this.modCoal = mod; return this; }
	public ModuleBurnTime setLigniteMod(double mod) { this.modLignite = mod; return this; }
	public ModuleBurnTime setCokeMod(double mod) { this.modCoke = mod; return this; }
	public ModuleBurnTime setSolidMod(double mod) { this.modSolid = mod; return this; }
	public ModuleBurnTime setRocketMod(double mod) { this.modRocket = mod; return this; }
}
