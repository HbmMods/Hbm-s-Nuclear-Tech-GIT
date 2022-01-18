package com.hbm.handler;

import com.hbm.entity.mob.siege.EntitySiegeZombie;
import com.hbm.util.ChatBuilder;
import com.hbm.util.GameRuleHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class SiegeOrchestrator {

	public static boolean lastWave = false;
	
	private static int level = 0;
	private static int levelCounter = 0;
	
	private static int siegeMobCount = 0;
	
	public static void update(World world) {
		
		//abort loop if sieges are disabled
		if(!siegeEnabled(world))
			return;

		int waveTime = getWaveDuration(world);
		int pauseTime = getPauseDuration(world);
		int interval = waveTime + pauseTime;
		//whether we're in a wave or pause, pauses apply first in an interval
		boolean wave = (int)(world.getTotalWorldTime() % interval) >= pauseTime;
		
		//send a server-wide message when the wave starts and ends
		if(!lastWave && wave) {
			MinecraftServer.getServer().getConfigurationManager().sendChatMsg(ChatBuilder.start("[SIEGE MODE] A new wave is starting!").color(EnumChatFormatting.RED).flush());
		} else if(lastWave && !wave) {
			MinecraftServer.getServer().getConfigurationManager().sendChatMsg(ChatBuilder.start("[SIEGE MODE] The wave has ended!").color(EnumChatFormatting.RED).flush());
		}
		
		//if we're on pause, do nothing
		if(!wave)
			return;
		
		int spawnDelay = getSpawnDelay(world);
		boolean threshold = spawnThresholdEnabled(world);
		int thresholdSize = getSpawnThreshold(world);
		
		//if threshold is enabled, don't go into the spawn loop if the entity count exceeds the threshold
		if(!(threshold && siegeMobCount > thresholdSize)) {
			for(Object o : world.playerEntities) {
				EntityPlayer player = (EntityPlayer) o;
				
				if((world.getTotalWorldTime() + player.getEntityId()) % spawnDelay == 0) {
					perPlayerSpawn(player);
				}
			}
		}
		
		int countCap = getTierDelay(world);
		int prevLevel = level;
		levelCounter++;
		
		//if the counter has reached the cap, tick up the tier and reset the counter
		while(levelCounter >= countCap) {
			levelCounter -= countCap;
			level++;
		}
		
		//if the counter is below 0, bring up the counter and deduct a tier
		while(levelCounter < 0) {
			levelCounter += countCap;
			level--;
		}
		
		//if the tier has changed, send a broadcast
		if(prevLevel != level) {
			MinecraftServer.getServer().getConfigurationManager().sendChatMsg(ChatBuilder.start("[SIEGE MODE] The siege tier is now " + level + "!").color(EnumChatFormatting.RED).flush());
		}
		
		//every 10s we recount the loaded siege mobs
		if(world.getTotalWorldTime() % 200 == 0) {
			refreshMobCount(world);
		}
	}
	
	public static void perPlayerSpawn(EntityPlayer player) {
		//TODO: either spawn siege mobs outright or dropships, depending on whether dropships are enabled
	}
	
	public static void playerDeathHook(EntityPlayer player) {
		
	}
	
	public static void mobDeathHook(EntityLivingBase entity) {
		
	}
	
	private static void refreshMobCount(World world) {
		
		siegeMobCount = 0;
		
		for(Object o : world.loadedEntityList) {
			Entity entity = (Entity) o;
			
			if(isSiegeMob(entity)) {
				siegeMobCount++;
			}
		}
	}
	
	public static boolean isSiegeMob(Entity entity) {
		
		if(entity instanceof EntitySiegeZombie)
			return true;
		
		return false;
	}

	public static final String KEY_SAVE_RULES = "siegeSaveRules";
	public static final String KEY_ENABLE_SIEGES = "siegeEnable";
	public static final String KEY_WAVE_DURATION = "siegeWaveDuration";
	public static final String KEY_PAUSE_DURATION = "siegePauseDuration";
	public static final String KEY_ENABLE_DROPS = "siegeEnableDropships";
	public static final String KEY_ENABLE_BASES = "siegeEnableBases";
	public static final String KEY_ENABLE_MISSILES = "siegeEnableMissiles";
	public static final String KEY_SPAWN_DIST = "siegeSpawnDist";
	public static final String KEY_SPAWN_DELAY = "siegeSpawnDelay";
	public static final String KEY_TIER_DELAY = "siegeTierDuration";
	public static final String KEY_TIER_ADD_KILL = "siegeTierAddKill";
	public static final String KEY_TIER_SUB_DEATH = "siegeTierSubDeath";
	public static final String KEY_SPAWN_THRESHOLD = "siegeEnableSpawnThreshold";
	public static final String KEY_SPAWN_THRESHOLD_COUNT = "siegeSpawnThreshold";
	
	public static void createGameRules(World world) {

		GameRules rules = world.getGameRules();
		
		if(!rules.getGameRuleBooleanValue(KEY_SAVE_RULES)) {
			rules.setOrCreateGameRule(KEY_SAVE_RULES, "true");
			rules.setOrCreateGameRule(KEY_ENABLE_SIEGES, "false");
			rules.setOrCreateGameRule(KEY_WAVE_DURATION, "" + (20 * 60 * 20));
			rules.setOrCreateGameRule(KEY_PAUSE_DURATION, "" + (10 * 60 * 20));
			rules.setOrCreateGameRule(KEY_ENABLE_DROPS, "true");
			rules.setOrCreateGameRule(KEY_ENABLE_BASES, "true");
			rules.setOrCreateGameRule(KEY_ENABLE_MISSILES, "true");
			rules.setOrCreateGameRule(KEY_SPAWN_DIST, "64");
			rules.setOrCreateGameRule(KEY_SPAWN_DELAY, "" + (10 * 20));
			rules.setOrCreateGameRule(KEY_TIER_DELAY, "" + (15 * 60 * 20));
			rules.setOrCreateGameRule(KEY_TIER_ADD_KILL, "" + (5 * 20));
			rules.setOrCreateGameRule(KEY_TIER_SUB_DEATH, "" + (15 * 20));
			rules.setOrCreateGameRule(KEY_SPAWN_THRESHOLD, "true");
			rules.setOrCreateGameRule(KEY_SPAWN_THRESHOLD_COUNT, "50");
		}
	}
	
	public static boolean siegeEnabled(World world) {
		return world.getGameRules().getGameRuleBooleanValue(KEY_ENABLE_SIEGES);
	}
	
	public static int getWaveDuration(World world) {
		return GameRuleHelper.getIntegerMinimum(world, KEY_WAVE_DURATION, 20 * 60 * 10, 1);
	}
	
	public static int getPauseDuration(World world) {
		return GameRuleHelper.getIntegerMinimum(world, KEY_PAUSE_DURATION, 10 * 60 * 10, 0);
	}
	
	public static double getSpawnDist(World world) {
		return GameRuleHelper.getDoubleMinimum(world, KEY_SPAWN_DIST, 64, 0);
	}
	
	public static int getSpawnDelay(World world) {
		return GameRuleHelper.getIntegerMinimum(world, KEY_SPAWN_DELAY, 10 * 20, 1);
	}
	
	public static int getTierDelay(World world) {
		return GameRuleHelper.getIntegerMinimum(world, KEY_TIER_DELAY, 15 * 60 * 20, 1);
	}
	
	public static int getTierAddKill(World world) {
		return GameRuleHelper.getIntegerMinimum(world, KEY_TIER_ADD_KILL, 5 * 20, 0);
	}
	
	public static int getTierSubDeath(World world) {
		return GameRuleHelper.getIntegerMinimum(world, KEY_TIER_SUB_DEATH, 15 * 20, 0);
	}
	
	public static boolean spawnThresholdEnabled(World world) {
		return world.getGameRules().getGameRuleBooleanValue(KEY_SPAWN_THRESHOLD);
	}
	
	public static int getSpawnThreshold(World world) {
		return GameRuleHelper.getIntegerMinimum(world, KEY_SPAWN_THRESHOLD_COUNT, 50, 1);
	}
}
