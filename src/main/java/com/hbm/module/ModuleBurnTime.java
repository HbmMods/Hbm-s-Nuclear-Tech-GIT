package com.hbm.module;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.handler.FuelHandler;
import com.hbm.items.ModItems;
import com.hbm.tileentity.IConfigurableMachine;
import com.hbm.util.ItemStackUtil;

import net.minecraft.item.ItemStack;
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

	public void readIfPresent(JsonObject obj) {
		modTime[modLog] = IConfigurableMachine.grab(obj, "D:timeLog", modTime[modLog]);
		modTime[modWood] = IConfigurableMachine.grab(obj, "D:timeWood", modTime[modWood]);
		modTime[modCoal] = IConfigurableMachine.grab(obj, "D:timeCoal", modTime[modCoal]);
		modTime[modLignite] = IConfigurableMachine.grab(obj, "D:timeLignite", modTime[modLignite]);
		modTime[modCoke] = IConfigurableMachine.grab(obj, "D:timeCoke", modTime[modCoke]);
		modTime[modSolid] = IConfigurableMachine.grab(obj, "D:timeSolid", modTime[modSolid]);
		modTime[modRocket] = IConfigurableMachine.grab(obj, "D:timeRocket", modTime[modRocket]);
		modTime[modBalefire] = IConfigurableMachine.grab(obj, "D:timeBalefire", modTime[modBalefire]);

		modHeat[modLog] = IConfigurableMachine.grab(obj, "D:heatLog", modHeat[modLog]);
		modHeat[modWood] = IConfigurableMachine.grab(obj, "D:heatWood", modHeat[modWood]);
		modHeat[modCoal] = IConfigurableMachine.grab(obj, "D:heatCoal", modHeat[modCoal]);
		modHeat[modLignite] = IConfigurableMachine.grab(obj, "D:heatLignite", modHeat[modLignite]);
		modHeat[modCoke] = IConfigurableMachine.grab(obj, "D:heatCoke", modHeat[modCoke]);
		modHeat[modSolid] = IConfigurableMachine.grab(obj, "D:heatSolid", modHeat[modSolid]);
		modHeat[modRocket] = IConfigurableMachine.grab(obj, "D:heatRocket", modHeat[modRocket]);
		modHeat[modBalefire] = IConfigurableMachine.grab(obj, "D:heatBalefie", modHeat[modBalefire]);
	}

	public void writeConfig(JsonWriter writer) throws IOException {
		writer.name("D:timeLog").value(modTime[modLog]);
		writer.name("D:timeWood").value(modTime[modWood]);
		writer.name("D:timeCoal").value(modTime[modCoal]);
		writer.name("D:timeLignite").value(modTime[modLignite]);
		writer.name("D:timeCoke").value(modTime[modCoke]);
		writer.name("D:timeSolid").value(modTime[modSolid]);
		writer.name("D:timeRocket").value(modTime[modRocket]);
		writer.name("D:timeBalefire").value(modTime[modBalefire]);
		writer.name("D:heatLog").value(modHeat[modLog]);
		writer.name("D:heatWood").value(modHeat[modWood]);
		writer.name("D:heatCoal").value(modHeat[modCoal]);
		writer.name("D:heatLignite").value(modHeat[modLignite]);
		writer.name("D:heatCoke").value(modHeat[modCoke]);
		writer.name("D:heatSolid").value(modHeat[modSolid]);
		writer.name("D:heatRocket").value(modHeat[modRocket]);
		writer.name("D:heatBalefie").value(modHeat[modBalefire]);
	}

	public int getBurnTime(ItemStack stack) {
		//int fuel = TileEntityFurnace.getItemBurnTime(stack);
		int fuel = FuelHandler.getBurnTimeFromCache(stack);

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
	public double[] getModHeat() {
		return modHeat;
	}
	public double[] getModTime() {
		return modTime;
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
