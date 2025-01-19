package com.hbm.tileentity.machine.rbmk;

import com.hbm.config.GeneralConfig;
import com.hbm.main.MainRegistry;
import com.hbm.util.GameRuleHelper;

import com.hbm.util.Tuple;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RBMKDials {

	public enum RBMKKeys {
		KEY_SAVE_DIALS("dialSaveDials", true),
		KEY_PASSIVE_COOLING("dialPassiveCooling", 1.0),
		KEY_COLUMN_HEAT_FLOW("dialColumnHeatFlow", 0.2),
		KEY_FUEL_DIFFUSION_MOD("dialDiffusionMod", 1.0),
		KEY_HEAT_PROVISION("dialHeatProvision", 0.2),
		KEY_COLUMN_HEIGHT("dialColumnHeight", 4),
		KEY_PERMANENT_SCRAP("dialEnablePermaScrap", true),
		KEY_BOILER_HEAT_CONSUMPTION("dialBoilerHeatConsumption", 0.1),
		KEY_CONTROL_SPEED_MOD("dialControlSpeed", 1.0),
		KEY_REACTIVITY_MOD("dialReactivityMod", 1.0),
		KEY_OUTGASSER_MOD("dialOutgasserSpeedMod", 1.0),
		KEY_SURGE_MOD("dialControlSurgeMod", 1.0),
		KEY_FLUX_RANGE("dialFluxRange", 5),
		KEY_REASIM_RANGE("dialReasimRange", 10),
		KEY_REASIM_COUNT("dialReasimCount", 6),
		KEY_REASIM_MOD("dialReasimOutputMod", 1.0),
		KEY_REASIM_BOILERS("dialReasimBoilers", false),
		KEY_REASIM_BOILER_SPEED("dialReasimBoilerSpeed", 0.05),
		KEY_DISABLE_MELTDOWNS("dialDisableMeltdowns", false),
		KEY_ENABLE_MELTDOWN_OVERPRESSURE("dialEnableMeltdownOverpressure", false),
		KEY_MODERATOR_EFFICIENCY("dialModeratorEfficiency", 1.0),
		KEY_ABSORBER_EFFICIENCY("dialAbsorberEfficiency", 1.0),
		KEY_REFLECTOR_EFFICIENCY("dialReflectorEfficiency", 1.0),
		KEY_DISABLE_DEPLETION("dialDisableDepletion", true),
		KEY_DISABLE_XENON("dialDisableXenon", true);

		public final String keyString;
		public final Object defValue;

		RBMKKeys(String key, Object def) {
			keyString = key;
			defValue = def;
		}
	}

	public static HashMap<RBMKKeys, List<Tuple.Pair<World, Object>>> gameRules = new HashMap<>();

	public static void createDials(World world) {
		createDials(world, false);
	}

	public static void createDials(World world, boolean forceRecreate) {
		GameRules rules = world.getGameRules();

		for(RBMKKeys key : RBMKKeys.values())
			gameRules.put(key, new ArrayList<>());
		refresh(world);

		if(!rules.getGameRuleBooleanValue(RBMKKeys.KEY_SAVE_DIALS.keyString) || forceRecreate) {
			for(RBMKKeys key : RBMKKeys.values())
				rules.setOrCreateGameRule(key.keyString, String.valueOf(key.defValue));
		}
	}


	/**
	 * Refresh all gamerules.
	 * @param world World to refresh for.
	 */
	public static void refresh(World world) {
		List<Tuple.Pair<World, Object>> toRemove = new ArrayList<>();
		for(List<Tuple.Pair<World, Object>> values : gameRules.values()) {

			for(Tuple.Pair<World, Object> rulePair : values)
				if(rulePair.key == world)
					toRemove.add(rulePair);

			for(Tuple.Pair<World, Object> pair : toRemove)
				values.remove(pair);

			toRemove.clear();
		}

		gameRules.get(RBMKKeys.KEY_PASSIVE_COOLING).add(new Tuple.Pair<>(world, GameRuleHelper.getDoubleMinimum(world, RBMKKeys.KEY_PASSIVE_COOLING, 0.0D)));
		gameRules.get(RBMKKeys.KEY_COLUMN_HEAT_FLOW).add(new Tuple.Pair<>(world, GameRuleHelper.getClampedDouble(world, RBMKKeys.KEY_COLUMN_HEAT_FLOW, 0.0D, 1.0D)));
		gameRules.get(RBMKKeys.KEY_FUEL_DIFFUSION_MOD).add(new Tuple.Pair<>(world, GameRuleHelper.getDoubleMinimum(world, RBMKKeys.KEY_FUEL_DIFFUSION_MOD, 0.0D)));
		gameRules.get(RBMKKeys.KEY_HEAT_PROVISION).add(new Tuple.Pair<>(world, GameRuleHelper.getClampedDouble(world, RBMKKeys.KEY_HEAT_PROVISION, 0.0D, 1.0D)));
		gameRules.get(RBMKKeys.KEY_COLUMN_HEIGHT).add(new Tuple.Pair<>(world, GameRuleHelper.getClampedInt(world, RBMKKeys.KEY_COLUMN_HEIGHT, 2, 16) - 1));
		gameRules.get(RBMKKeys.KEY_PERMANENT_SCRAP).add(new Tuple.Pair<>(world, world.getGameRules().getGameRuleBooleanValue(RBMKKeys.KEY_PERMANENT_SCRAP.keyString)));
		gameRules.get(RBMKKeys.KEY_BOILER_HEAT_CONSUMPTION).add(new Tuple.Pair<>(world, GameRuleHelper.getDoubleMinimum(world, RBMKKeys.KEY_BOILER_HEAT_CONSUMPTION, 0D)));
		gameRules.get(RBMKKeys.KEY_CONTROL_SPEED_MOD).add(new Tuple.Pair<>(world, GameRuleHelper.getDoubleMinimum(world, RBMKKeys.KEY_CONTROL_SPEED_MOD, 0.0D)));
		gameRules.get(RBMKKeys.KEY_REACTIVITY_MOD).add(new Tuple.Pair<>(world, GameRuleHelper.getDoubleMinimum(world, RBMKKeys.KEY_REACTIVITY_MOD, 0.0D)));
		gameRules.get(RBMKKeys.KEY_OUTGASSER_MOD).add(new Tuple.Pair<>(world, GameRuleHelper.getDoubleMinimum(world, RBMKKeys.KEY_OUTGASSER_MOD, 0.0D)));
		gameRules.get(RBMKKeys.KEY_SURGE_MOD).add(new Tuple.Pair<>(world, GameRuleHelper.getDoubleMinimum(world, RBMKKeys.KEY_SURGE_MOD, 0.0D)));
		gameRules.get(RBMKKeys.KEY_FLUX_RANGE).add(new Tuple.Pair<>(world, GameRuleHelper.getClampedInt(world, RBMKKeys.KEY_FLUX_RANGE, 1, 100)));
		gameRules.get(RBMKKeys.KEY_REASIM_RANGE).add(new Tuple.Pair<>(world, GameRuleHelper.getClampedInt(world, RBMKKeys.KEY_REASIM_RANGE, 1, 100)));
		gameRules.get(RBMKKeys.KEY_REASIM_COUNT).add(new Tuple.Pair<>(world, GameRuleHelper.getClampedInt(world, RBMKKeys.KEY_REASIM_COUNT, 1, 24)));
		gameRules.get(RBMKKeys.KEY_REASIM_MOD).add(new Tuple.Pair<>(world, GameRuleHelper.getDoubleMinimum(world, RBMKKeys.KEY_REASIM_MOD, 0.0D)));
		gameRules.get(RBMKKeys.KEY_REASIM_BOILERS).add(new Tuple.Pair<>(world, world.getGameRules().getGameRuleBooleanValue(RBMKKeys.KEY_REASIM_BOILERS.keyString) || (GeneralConfig.enable528 && GeneralConfig.enable528ReasimBoilers)));
		gameRules.get(RBMKKeys.KEY_REASIM_BOILER_SPEED).add(new Tuple.Pair<>(world, GameRuleHelper.getClampedDouble(world, RBMKKeys.KEY_REASIM_BOILER_SPEED, 0.0D, 1.0D)));
		gameRules.get(RBMKKeys.KEY_DISABLE_MELTDOWNS).add(new Tuple.Pair<>(world, world.getGameRules().getGameRuleBooleanValue(RBMKKeys.KEY_DISABLE_MELTDOWNS.keyString)));
		gameRules.get(RBMKKeys.KEY_ENABLE_MELTDOWN_OVERPRESSURE).add(new Tuple.Pair<>(world, world.getGameRules().getGameRuleBooleanValue(RBMKKeys.KEY_ENABLE_MELTDOWN_OVERPRESSURE.keyString)));
		gameRules.get(RBMKKeys.KEY_MODERATOR_EFFICIENCY).add(new Tuple.Pair<>(world, GameRuleHelper.getClampedDouble(world, RBMKKeys.KEY_MODERATOR_EFFICIENCY, 0.0D, 1.0D)));
		gameRules.get(RBMKKeys.KEY_ABSORBER_EFFICIENCY).add(new Tuple.Pair<>(world, GameRuleHelper.getClampedDouble(world, RBMKKeys.KEY_ABSORBER_EFFICIENCY, 0.0D, 1.0D)));
		gameRules.get(RBMKKeys.KEY_REFLECTOR_EFFICIENCY).add(new Tuple.Pair<>(world, GameRuleHelper.getClampedDouble(world, RBMKKeys.KEY_REFLECTOR_EFFICIENCY, 0.0D, 1.0D)));
		gameRules.get(RBMKKeys.KEY_DISABLE_DEPLETION).add(new Tuple.Pair<>(world, world.getGameRules().getGameRuleBooleanValue(RBMKKeys.KEY_DISABLE_DEPLETION.keyString)));
		gameRules.get(RBMKKeys.KEY_DISABLE_XENON).add(new Tuple.Pair<>(world, world.getGameRules().getGameRuleBooleanValue(RBMKKeys.KEY_DISABLE_XENON.keyString)));
	}

	/**
	 * Gets a gamerule from the internal cache.
	 * This will not work if called on the client.
	 * @param world World to get the gamerule for.
	 * @param rule Rule to get.
	 * @return The rule in an Object.
	 */
	public static Object getGameRule(World world, RBMKKeys rule) {
		if(world.isRemote) {
			MainRegistry.logger.error("Attempted to grab cached gamerules on client side, returning default value.");
			MainRegistry.logger.error("Gamerule: {}, Default Value: {}.", rule.keyString, rule.defValue.toString());
			return rule.defValue;
		}
		return getGameRule(world, rule, false);
	}

	public static Object getGameRule(World world, RBMKKeys rule, boolean isIteration) {
		List<Tuple.Pair<World, Object>> rulesList = new ArrayList<>();

		for(Tuple.Pair<World, Object> rulePair : gameRules.get(rule)) {
			if(rulePair.key == world) {
				rulesList.add(rulePair);
			}
		}

		if(rulesList.isEmpty()) {
			if(isIteration)
				throw new NullPointerException("Cannot find gamerule for dial " + rule.keyString + " after creation.");
			else {
				world.getGameRules().setOrCreateGameRule(rule.keyString, rule.defValue.toString()); // fuck
				refresh(world);
				return getGameRule(world, rule, true);
			}
		} else if(rulesList.size() > 1)
			// what??? why???
			MainRegistry.logger.warn("Duplicate values for gamerules detected! Found {} rules for gamerule {}", rulesList.size(), rule.keyString);

		return rulesList.get(0).value; // realistically there should only be one of this gamerule that satisfies the filter sooooo...
	}

	/**
	 * Returns the amount of heat per tick removed from components passively
	 * @param world
	 * @return >0
	 */
	public static double getPassiveCooling(World world) {
		return (double) getGameRule(world, RBMKKeys.KEY_PASSIVE_COOLING);
	}

	/**
	 * Returns the percentual step size how quickly neighboring component heat equalizes. 1 is instant, 0.5 is in 50% steps, et cetera.
	 * @param world
	 * @return [0;1]
	 */
	public static double getColumnHeatFlow(World world) {
		return (double) getGameRule(world, RBMKKeys.KEY_COLUMN_HEAT_FLOW);
	}

	/**
	 * Returns a modifier for fuel rod diffusion, i.e. how quickly the core and hull temperatures equalize.
	 * @param world
	 * @return >0
	 */
	public static double getFuelDiffusionMod(World world) {
		return (double) getGameRule(world, RBMKKeys.KEY_FUEL_DIFFUSION_MOD);
	}

	/**
	 * Returns the percentual step size how quickly the fuel hull heat and the component heat equalizes. 1 is instant, 0.5 is in 50% steps, et cetera.
	 * @param world
	 * @return [0;1]
	 */
	public static double getFuelHeatProvision(World world) {
		return (double) getGameRule(world, RBMKKeys.KEY_HEAT_PROVISION);
	}

	/**
	 * Simple integer that decides how tall the structure is.
	 * @param world
	 * @return [0;15]
	 */
	public static int getColumnHeight(World world) {
		return (int) getGameRule(world, RBMKKeys.KEY_COLUMN_HEIGHT);
	}

	/**
	 * Whether or not scrap entities despawn on their own or remain alive until picked up.
	 * @param world
	 * @return
	 */
	public static boolean getPermaScrap(World world) {
		return (boolean) getGameRule(world, RBMKKeys.KEY_PERMANENT_SCRAP);
	}

	/**
	 * How many heat units are consumed per mB water used.
	 * @param world
	 * @return >0
	 */
	public static double getBoilerHeatConsumption(World world) {
		return (double) getGameRule(world, RBMKKeys.KEY_BOILER_HEAT_CONSUMPTION);
	}

	/**
	 * A multiplier for how quickly the control rods move.
	 * @param world
	 * @return >0
	 */
	public static double getControlSpeed(World world) {
		return (double) getGameRule(world, RBMKKeys.KEY_CONTROL_SPEED_MOD);
	}

	/**
	 * A multiplier for how much flux the rods give out.
	 * @param world
	 * @return >0
	 */
	public static double getReactivityMod(World world) {
		return (double) getGameRule(world, RBMKKeys.KEY_REACTIVITY_MOD);
	}

	/**
	 * A multiplier for how much flux the rods give out.
	 * @param world
	 * @return >0
	 */
	public static double getOutgasserMod(World world) {
		return (double) getGameRule(world, RBMKKeys.KEY_OUTGASSER_MOD);
	}

	/**
	 * A multiplier for how high the power surge goes when inserting control rods.
	 * @param world
	 * @return >0
	 */
	public static double getSurgeMod(World world) {
		if(world.isRemote) { // The control rods use this gamerule for RBMK diag, which happens to be calculated on the client side. whoops!
			return GameRuleHelper.getDoubleMinimum(world, RBMKKeys.KEY_PASSIVE_COOLING, 0.0D);
		}
		return (double) getGameRule(world, RBMKKeys.KEY_SURGE_MOD);
	}

	/**
	 * Simple integer that decides how far the flux of a normal fuel rod reaches.
	 * @param world
	 * @return [1;100]
	 */
	public static int getFluxRange(World world) {
		return (int) getGameRule(world, RBMKKeys.KEY_FLUX_RANGE);
	}

	/**
	 * Simple integer that decides how far the flux of a ReaSim fuel rod reaches.
	 * @param world
	 * @return [1;100]
	 */
	public static int getReaSimRange(World world) {
		return (int) getGameRule(world, RBMKKeys.KEY_REASIM_RANGE);
	}

	/**
	 * Simple integer that decides how many neutrons are created from ReaSim fuel rods.
	 * @param world
	 * @return [1;24]
	 */
	public static int getReaSimCount(World world) {
		return (int) getGameRule(world, RBMKKeys.KEY_REASIM_COUNT);
	}

	/**
	 * Returns a modifier for the outgoing flux of individual streams from the ReaSim fuel rod to compensate for the potentially increased stream count.
	 * @param world
	 * @return >0
	 */
	public static double getReaSimOutputMod(World world) {
		return (double) getGameRule(world, RBMKKeys.KEY_REASIM_MOD);
	}

	/**
	 * Whether or not all components should act like boilers with dedicated in/outlet blocks
	 * @param world
	 * @return
	 */
	public static boolean getReasimBoilers(World world) {
		return (boolean) getGameRule(world, RBMKKeys.KEY_REASIM_BOILERS);
	}

	/**
	 * How much % of the possible steam ends up being produced per tick
	 * @param world
	 * @return [0;1]
	 */
	public static double getReaSimBoilerSpeed(World world) {
		return (double) getGameRule(world, RBMKKeys.KEY_REASIM_BOILER_SPEED);
	}

	/**
	 * Whether or not fuel columns should initiate a meltdown when overheating
	 * The method is in reverse because the default for older worlds will be 'false'
	 * @param world
	 * @return
	 */
	public static boolean getMeltdownsDisabled(World world) {
		return (boolean) getGameRule(world, RBMKKeys.KEY_DISABLE_MELTDOWNS);
	}

	/**
	 * Whether or not connected pipes and turbines should explode when the reactor undergoes a meltdown.
	 * @param world
	 * @return
	 */
	public static boolean getOverpressure(World world) {
		return (boolean) getGameRule(world, RBMKKeys.KEY_ENABLE_MELTDOWN_OVERPRESSURE);
	}

	/**
	 * The percentage of neutrons to moderate from fast to slow when they pass through a moderator.
	 * @param world
	 * @return
	 */
	public static double getModeratorEfficiency(World world) {
		return (double) getGameRule(world, RBMKKeys.KEY_MODERATOR_EFFICIENCY);
	}

	/**
	 * The percentage of neutrons to be absorbed when a stream hits an absorber column.
	 * @param world
	 * @return
	 */
	public static double getAbsorberEfficiency(World world) {
		return (double) getGameRule(world, RBMKKeys.KEY_ABSORBER_EFFICIENCY);
	}

	/**
	 * The percentage of neutron to reflect when a stream hits a reflector column.
	 * @param world
	 * @return
	 */
	public static double getReflectorEfficiency(World world) {
		return (double) getGameRule(world, RBMKKeys.KEY_REFLECTOR_EFFICIENCY);
	}
	
	/**
	 * Whether fuel rods should deplete, disabling this makes rods last forever
	 * @param world
	 * @return
	 */
	public static boolean getDepletion(World world) {
		return !((boolean) getGameRule(world, RBMKKeys.KEY_DISABLE_DEPLETION));
	}
	
	/**
	 * Whether xenon poison should be calculated
	 * @param world
	 * @return
	 */
	public static boolean getXenon(World world) {
		return !((boolean) getGameRule(world, RBMKKeys.KEY_DISABLE_XENON));
	}
}
