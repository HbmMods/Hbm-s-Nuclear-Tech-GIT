package com.hbm.util;

import com.hbm.tileentity.machine.rbmk.RBMKDials;
import net.minecraft.util.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class GameRuleHelper {

	public static double getClampedDouble(World world, RBMKDials.RBMKKeys rule, double min, double max) {
		return MathHelper.clamp_double(GameRuleHelper.parseDouble(world, world.getGameRules().getGameRuleStringValue(rule.keyString), (double) rule.defValue), min, max);
	}

	public static int getClampedInt(World world, RBMKDials.RBMKKeys rule, int min, int max) {
		return MathHelper.clamp_int(GameRuleHelper.parseInt(world, world.getGameRules().getGameRuleStringValue(rule.keyString), (int) rule.defValue), min, max);
	}

	public static double getDoubleMinimum(World world, RBMKDials.RBMKKeys rule, double min) {
		return Math.max(GameRuleHelper.parseDouble(world, world.getGameRules().getGameRuleStringValue(rule.keyString), (double) rule.defValue), min);
	}

	public static int getIntegerMinimum(World world, RBMKDials.RBMKKeys rule, int min) {
		return Math.max(GameRuleHelper.parseInt(world, world.getGameRules().getGameRuleStringValue(rule.keyString), (int) rule.defValue), min);
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
