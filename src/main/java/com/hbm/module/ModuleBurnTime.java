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

	private static final int modLog = 0;
	private static final int modWood = 1;
	private static final int modCoal = 2;
	private static final int modLignite = 3;
	private static final int modCoke = 4;
	private static final int modSolid = 5;
	private static final int modRocket = 6;
	private static final int modBalefire = 7;

	private double[] modTime = new double[8];
	private double[] modHeat = new double[8];
	
	public ModuleBurnTime() {
		for(int i = 0; i < modTime.length; i++) {
			modTime[i] = 1.0D;
			modHeat[i] = 1.0D;
		}
	}
	
	public int getBurnTime(ItemStack stack) {
		int fuel = TileEntityFurnace.getItemBurnTime(stack);
		
		if(fuel == 0)
			return 0;
		
		return (int) (fuel * getMod(stack, modTime));
	}
	
	public int getBurnHeat(int base, ItemStack stack) {
		return (int) (base * getMod(stack, modHeat));
	}
	
	public double getMod(ItemStack stack, double[] mod) {
		
		if(stack == null)
			return 0;

		if(stack.getItem() == ModItems.solid_fuel)						return mod[modSolid];
		if(stack.getItem() == ModItems.solid_fuel_presto) 				return mod[modSolid];
		if(stack.getItem() == ModItems.solid_fuel_presto_triplet)		return mod[modSolid];

		if(stack.getItem() == ModItems.solid_fuel_bf)					return mod[modBalefire];
		if(stack.getItem() == ModItems.solid_fuel_presto_bf) 			return mod[modBalefire];
		if(stack.getItem() == ModItems.solid_fuel_presto_triplet_bf)	return mod[modBalefire];
		
		if(stack.getItem() == ModItems.rocket_fuel)						return mod[modRocket];
		
		List<String> names = ItemStackUtil.getOreDictNames(stack);
		
		for(String name : names) {
			if(name.contains("Coke"))		return mod[modCoke];
			if(name.contains("Coal"))		return mod[modCoal];
			if(name.contains("Lignite"))	return mod[modLignite];
			if(name.startsWith("log"))		return mod[modLog];
			if(name.contains("Wood"))		return mod[modWood];
		}
		
		return 1;
	}
	
	public List<String> getDesc() {
		List<String> desc = new ArrayList();
		desc.addAll(getTimeDesc());
		desc.addAll(getHeatDesc());
		return desc;
	}
	
	public List<String> getTimeDesc() {
		List<String> list = new ArrayList();

		list.add(EnumChatFormatting.GOLD + "Burn time bonuses:");
		
		addIf(list, "Logs", modTime[modLog]);
		addIf(list, "Wood", modTime[modWood]);
		addIf(list, "Coal", modTime[modCoal]);
		addIf(list, "Lignite", modTime[modLignite]);
		addIf(list, "Coke", modTime[modCoke]);
		addIf(list, "Solid Fuel", modTime[modSolid]);
		addIf(list, "Rocket Fuel", modTime[modRocket]);
		addIf(list, "Balefire", modTime[modBalefire]);
		
		if(list.size() == 1)
			list.clear();
		
		return list;
	}
	
	public List<String> getHeatDesc() {
		List<String> list = new ArrayList();

		list.add(EnumChatFormatting.RED + "Burn heat bonuses:");
		
		addIf(list, "Logs", modHeat[modLog]);
		addIf(list, "Wood", modHeat[modWood]);
		addIf(list, "Coal", modHeat[modCoal]);
		addIf(list, "Lignite", modHeat[modLignite]);
		addIf(list, "Coke", modHeat[modCoke]);
		addIf(list, "Solid Fuel", modHeat[modSolid]);
		addIf(list, "Rocket Fuel", modHeat[modRocket]);
		addIf(list, "Balefire", modHeat[modBalefire]);
		
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
			num = EnumChatFormatting.RED + num;
		else
			num = EnumChatFormatting.GREEN + "+" + num;
		
		return num;
	}
	
	public ModuleBurnTime setLogTimeMod(double mod) { this.modTime[modLog] = mod; return this; }
	public ModuleBurnTime setWoodTimeMod(double mod) { this.modTime[modWood] = mod; return this; }
	public ModuleBurnTime setCoalTimeMod(double mod) { this.modTime[modCoal] = mod; return this; }
	public ModuleBurnTime setLigniteTimeMod(double mod) { this.modTime[modLignite] = mod; return this; }
	public ModuleBurnTime setCokeTimeMod(double mod) { this.modTime[modCoke] = mod; return this; }
	public ModuleBurnTime setSolidTimeMod(double mod) { this.modTime[modSolid] = mod; return this; }
	public ModuleBurnTime setRocketTimeMod(double mod) { this.modTime[modRocket] = mod; return this; }
	public ModuleBurnTime setBalefireTimeMod(double mod) { this.modTime[modBalefire] = mod; return this; }
	
	public ModuleBurnTime setLogHeatMod(double mod) { this.modHeat[modLog] = mod; return this; }
	public ModuleBurnTime setWoodHeatMod(double mod) { this.modHeat[modWood] = mod; return this; }
	public ModuleBurnTime setCoalHeatMod(double mod) { this.modHeat[modCoal] = mod; return this; }
	public ModuleBurnTime setLigniteHeatMod(double mod) { this.modHeat[modLignite] = mod; return this; }
	public ModuleBurnTime setCokeHeatMod(double mod) { this.modHeat[modCoke] = mod; return this; }
	public ModuleBurnTime setSolidHeatMod(double mod) { this.modHeat[modSolid] = mod; return this; }
	public ModuleBurnTime setRocketHeatMod(double mod) { this.modHeat[modRocket] = mod; return this; }
	public ModuleBurnTime setBalefireHeatMod(double mod) { this.modHeat[modBalefire] = mod; return this; }
}
