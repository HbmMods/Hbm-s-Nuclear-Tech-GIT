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
	public static final String KEY_BOILER_HEAT_CONSUMPTION = "dialBoilerHeatConsumption";
	public static final String KEY_CONTROL_SPEED_MOD = "dialControlSpeed";
	public static final String KEY_REACTIVITY_MOD = "dialReactivityMod";
	public static final String KEY_SAVE_DIALS = "dialSaveDials";
	public static final String KEY_OUTGASSER_MOD = "dialOutgasserSpeedMod";
	
	public static void createDials(World world) {
		GameRules rules = world.getGameRules();
		
		if(!rules.getGameRuleBooleanValue(KEY_SAVE_DIALS)) {
			rules.setOrCreateGameRule(KEY_PASSIVE_COOLING, "1.0");
			rules.setOrCreateGameRule(KEY_COLUMN_HEAT_FLOW, "0.2");
			rules.setOrCreateGameRule(KEY_FUEL_DIFFUSION_MOD, "1.0");
			rules.setOrCreateGameRule(KEY_HEAT_PROVISION, "0.2");
			rules.setOrCreateGameRule(KEY_COLUMN_HEIGHT, "4");
			rules.setOrCreateGameRule(KEY_PERMANENT_SCRAP, "false");
			rules.setOrCreateGameRule(KEY_BOILER_HEAT_CONSUMPTION, "0.1");
			rules.setOrCreateGameRule(KEY_CONTROL_SPEED_MOD, "1.0");
			rules.setOrCreateGameRule(KEY_REACTIVITY_MOD, "1.0");
			rules.setOrCreateGameRule(KEY_SAVE_DIALS, "true");
			rules.setOrCreateGameRule(KEY_OUTGASSER_MOD, "1.0");
		}
	}
	
	/**
	 * Returns the amount of heat per tick removed from components passively
	 * @param world
	 * @return
	 */
	public static double getPassiveCooling(World world) {
		return Math.max(shittyWorkaroundParseDouble(world.getGameRules().getGameRuleStringValue(KEY_PASSIVE_COOLING), 5.0D), 0.0D);
	}
	
	/**
	 * Returns the percentual step size how quickly neighboring component heat equalizes. 1 is instant, 0.5 is in 50% steps, et cetera.
	 * @param world
	 * @return [0;1]
	 */
	public static double getColumnHeatFlow(World world) {
		return MathHelper.clamp_double(shittyWorkaroundParseDouble(world.getGameRules().getGameRuleStringValue(KEY_COLUMN_HEAT_FLOW), 5.0D), 0.0D, 1.0D);
	}
	
	/**
	 * Returns a modifier for fuel rod diffusion, i.e. how quickly the core and hull temperatures equalize.
	 * @param world
	 * @return >0
	 */
	public static double getFuelDiffusionMod(World world) {
		return Math.max(shittyWorkaroundParseDouble(world.getGameRules().getGameRuleStringValue(KEY_FUEL_DIFFUSION_MOD), 1.0D), 0.0D);
	}
	
	/**
	 * Returns the percentual step size how quickly the fuel hull heat and the component heat equalizes. 1 is instant, 0.5 is in 50% steps, et cetera.
	 * @param world
	 * @return [0;1]
	 */
	public static double getFuelHeatProvision(World world) {
		return MathHelper.clamp_double(shittyWorkaroundParseDouble(world.getGameRules().getGameRuleStringValue(KEY_HEAT_PROVISION), 0.2D), 0.0D, 1.0D);
	}
	
	/**
	 * Simple integer that decides how tall the structure is.
	 * @param world
	 * @return [0;15]
	 */
	public static int getColumnHeight(World world) {
		return MathHelper.clamp_int(shittyWorkaroundParseInt(world.getGameRules().getGameRuleStringValue(KEY_COLUMN_HEIGHT), 4), 1, 16) - 1;
	}
	
	/**
	 * Whether or not scrap entities despawn on their own or remain alive until picked up.
	 * @param world
	 * @return
	 */
	public static boolean getPermaScrap(World world) {
		return world.getGameRules().getGameRuleBooleanValue(KEY_PERMANENT_SCRAP);
	}
	
	/**
	 * How many heat units are consumed per steam unit (scaled per type) produced.
	 * @param world
	 * @return >0
	 */
	public static double getBoilerHeatConsumption(World world) {
		return Math.max(shittyWorkaroundParseDouble(world.getGameRules().getGameRuleStringValue(KEY_BOILER_HEAT_CONSUMPTION), 0.1D), 0D);
	}
	
	/**
	 * A multiplier for how quickly the control rods move.
	 * @param world
	 * @return
	 */
	public static double getControlSpeed(World world) {
		return Math.max(shittyWorkaroundParseDouble(world.getGameRules().getGameRuleStringValue(KEY_CONTROL_SPEED_MOD), 1.0D), 0.0D);
	}
	
	/**
	 * A multiplier for how much flux the rods give out.
	 * @param world
	 * @return
	 */
	public static double getReactivityMod(World world) {
		return Math.max(shittyWorkaroundParseDouble(world.getGameRules().getGameRuleStringValue(KEY_REACTIVITY_MOD), 1.0D), 0.0D);
	}
	
	/**
	 * A multiplier for how much flux the rods give out.
	 * @param world
	 * @return
	 */
	public static double getOutgasserMod(World world) {
		return Math.max(shittyWorkaroundParseDouble(world.getGameRules().getGameRuleStringValue(KEY_OUTGASSER_MOD), 1.0D), 0.0D);
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
