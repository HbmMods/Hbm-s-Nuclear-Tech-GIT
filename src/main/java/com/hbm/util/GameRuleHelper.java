package com.hbm.util;

import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GameRuleHelper {
	
	public static double getClampedDouble(World world, String rule, double def, double min, double max) {
		return MathHelper.clamp_double(GameRuleHelper.parseDouble(world.getGameRules().getGameRuleStringValue(rule), def), min, max);
	}
	
	public static double getDoubleMinimum(World world, String rule, double def, double min) {
		return Math.max(GameRuleHelper.parseDouble(world.getGameRules().getGameRuleStringValue(rule), def), min);
	}
	
	public static int getIntegerMinimum(World world, String rule, int def, int min) {
		return Math.max(GameRuleHelper.parseInt(world.getGameRules().getGameRuleStringValue(rule), def), min);
	}

	public static double parseDouble(String s, double def) {
		
		try {
			return Double.parseDouble(s);
		} catch(Exception ex) { }
		
		return def;
	}

	public static int parseInt(String s, int def) {
		
		try {
			return Integer.parseInt(s);
		} catch(Exception ex) { }
		
		return def;
	}

}
