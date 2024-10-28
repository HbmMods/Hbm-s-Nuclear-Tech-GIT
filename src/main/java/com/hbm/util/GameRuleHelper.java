package com.hbm.util;

import net.minecraft.util.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class GameRuleHelper {

	public static double getClampedDouble(World world, String rule, double def, double min, double max) {
		return MathHelper.clamp_double(GameRuleHelper.parseDouble(world, world.getGameRules().getGameRuleStringValue(rule), def), min, max);
	}

	public static double getDoubleMinimum(World world, String rule, double def, double min) {
		return Math.max(GameRuleHelper.parseDouble(world, world.getGameRules().getGameRuleStringValue(rule), def), min);
	}

	public static int getIntegerMinimum(World world, String rule, int def, int min) {
		return Math.max(GameRuleHelper.parseInt(world, world.getGameRules().getGameRuleStringValue(rule), def), min);
	}

	public static double parseDouble(World world, String s, double def) {

		GameRules rules = world.getGameRules();
		if(s.isEmpty() && !rules.hasRule(s)) {
			rules.addGameRule(s, String.valueOf(def));
			return def;
		}

		try {
			return Double.parseDouble(s);
		} catch(Exception ex) { }

		return def;
	}

	public static int parseInt(World world, String s, int def) {

		GameRules rules = world.getGameRules();
		if(s.isEmpty() && !rules.hasRule(s)) {
			rules.addGameRule(s, String.valueOf(def));
		}

		try {
			return Integer.parseInt(s);
		} catch(Exception ex) { }

		return def;
	}

}
