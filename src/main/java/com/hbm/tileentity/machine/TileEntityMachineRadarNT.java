package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.Tuple.Pair;

import api.hbm.entity.IRadarDetectable;
import api.hbm.entity.IRadarDetectableNT;
import api.hbm.entity.RadarEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldServer;

/**
 * Now with SmЯt™ lag-free entity detection!
 * @author hbm
 */
public class TileEntityMachineRadarNT extends TileEntityMachineBase {
	
	public boolean scanMissiles = true;
	public boolean scanPlayers = true;
	public boolean smartMode = true;
	public boolean redMode = true;
	
	public boolean jammed = false;

	public TileEntityMachineRadarNT() {
		super(1);
	}

	@Override
	public String getName() {
		return "";
	}

	@Override
	public void updateEntity() {
		
	}
	
	//List of lambdas that are supplied a Pair with the entity and radar in question to generate a RadarEntry
	//The converters coming first have the highest priority
	public static List<Function<Pair<Entity, Object>, RadarEntry>> converters = new ArrayList();
	public static List<Class> classes = new ArrayList();
	public static List<Entity> matchingEntities = new ArrayList();
	
	/**
	 * Iterates over every entity in the world and add them to the matchingEntities list if the class is in the detectable list
	 * From this compiled list, radars can easily grab the required entities since we can assume that the total amount of detectable entities is comparatively low
	 */
	public static void updateSystem() {
		matchingEntities.clear();
		
		for(WorldServer world : Minecraft.getMinecraft().getIntegratedServer().worldServers) {
			for(Object entity : world.loadedEntityList) {
				for(Class clazz : classes) {
					if(clazz.isAssignableFrom(entity.getClass())) {
						matchingEntities.add((Entity) entity);
						break;
					}
				}
			}
		}
	}

	/** Registers a class that if an entity inherits that class, it is picked up by the system */
	public static void registerEntityClasses() {
		classes.add(IRadarDetectableNT.class);
		classes.add(IRadarDetectable.class);
		classes.add(EntityPlayer.class);
	}
	
	/** Registers converters. Converters are used to go over the list of detected entities and turn them into a RadarEntry using the entity instance and the radar's instance. */
	public static void registerConverters() {
		//IRadarDetectableNT
		converters.add(x -> {
			Entity e = x.getKey();
			if(e instanceof IRadarDetectableNT) {
				IRadarDetectableNT detectable = (IRadarDetectableNT) e;
				if(detectable.canBeSeenBy(x.getValue())) return new RadarEntry(detectable, e);
			}
			return null;
		});
		//IRadarDetectable, Legacy
		converters.add(x -> {
			Entity e = x.getKey();
			if(e instanceof IRadarDetectable) {
				return new RadarEntry((IRadarDetectable) e, e);
			}
			return null;
		});
		//Players
		converters.add(x -> {
			Entity e = x.getKey();
			if(e instanceof EntityPlayer) {
				return new RadarEntry((EntityPlayer) e);
			}
			return null;
		});
	}
}
