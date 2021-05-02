package com.hbm.tileentity.machine.rbmk;

import net.minecraft.util.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class RBMKDials {

	public static final String KEY_PASSIVE_COOLING = "dialPassiveCooling";
	public static final String KEY_COLUMN_HEAT_FLOW = "dialColumnHeatFlow";
	public static final String KEY_FUEL_DIFFUSION_MOD = "dialDiffusionMod";
	public static final String KEY_HEAT_PROVISION = "dialHeatProvision";
	public static final String KEY_COLUMN_HEIGHT = "dialColumnHeight";
	public static final String KEY_PERMANENT_SCRAP = "dialEnablePermaScrap";
	
	public static void createDials(World world) {
		GameRules rules = world.getGameRules();

		rules.setOrCreateGameRule(KEY_PASSIVE_COOLING, "5.0");
		rules.setOrCreateGameRule(KEY_COLUMN_HEAT_FLOW, "0.2");
		rules.setOrCreateGameRule(KEY_FUEL_DIFFUSION_MOD, "1.0");
		rules.setOrCreateGameRule(KEY_HEAT_PROVISION, "0.2");
		rules.setOrCreateGameRule(KEY_COLUMN_HEIGHT, "4");
		rules.setOrCreateGameRule(KEY_PERMANENT_SCRAP, "false");
	}

	public static double getPassiveCooling(World world) {
		return shittyWorkaroundParseDouble(world.getGameRules().getGameRuleStringValue(KEY_PASSIVE_COOLING), 5.0D);
	}
	
	//[0;1]
	public static double getColumnHeatFlow(World world) {
		return MathHelper.clamp_double(shittyWorkaroundParseDouble(world.getGameRules().getGameRuleStringValue(KEY_COLUMN_HEAT_FLOW), 5.0D), 0.0D, 1.0D);
	}
	
	//[0;1]
	public static double getFuelDiffusionMod(World world) {
		return MathHelper.clamp_double(shittyWorkaroundParseDouble(world.getGameRules().getGameRuleStringValue(KEY_FUEL_DIFFUSION_MOD), 1.0D), 0.0D, 1.0D);
	}
	
	//[0;1]
	public static double getFuelHeatProvision(World world) {
		return MathHelper.clamp_double(shittyWorkaroundParseDouble(world.getGameRules().getGameRuleStringValue(KEY_HEAT_PROVISION), 0.2D), 0.0D, 1.0D);
	}
	
	public static int getColumnHeight(World world) {
		return MathHelper.clamp_int(shittyWorkaroundParseInt(world.getGameRules().getGameRuleStringValue(KEY_COLUMN_HEIGHT), 4), 1, 16) - 1;
	}
	
	public static boolean getPermaScrap(World world) {
		return world.getGameRules().getGameRuleBooleanValue(KEY_PERMANENT_SCRAP);
	}
	
	//why make the double representation accessible in a game rule when you can just force me to add a second pointless parsing operation?
	public static double shittyWorkaroundParseDouble(String s, double def) {
		
		try {
			return Double.parseDouble(s);
		} catch(Exception ex) { }
		
		return def;
	}
	public static int shittyWorkaroundParseInt(String s, int def) {
		
		try {
			return Integer.parseInt(s);
		} catch(Exception ex) { }
		
		return def;
	}
}
