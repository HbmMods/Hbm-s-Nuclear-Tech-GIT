package com.hbm.dim;

import java.util.HashMap;
import java.util.Random;
import java.util.Map.Entry;

import com.hbm.config.SpaceConfig;
import com.hbm.dim.orbit.OrbitalStation;
import com.hbm.dim.orbit.OrbitalStation.StationState;
import com.hbm.dim.trait.CelestialBodyTrait;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.Constants.NBT;

public class SolarSystemWorldSavedData extends WorldSavedData {

	/**
	 * Similar to CelestialBodyWorldSavedData but for anything that must be visible from
	 * other bodies, like atmospheric data or UTTER ANNIHILATION
	 */

	private static final String DATA_NAME = "SolarSystemData";

	public SolarSystemWorldSavedData(String name) {
		super(name);
	}

	private Random rand = new Random();

	private HashMap<String, HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait>> traitMap = new HashMap<String, HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait>>();
	private HashMap<ChunkCoordIntPair, OrbitalStation> stations = new HashMap<>();
	
	public static SolarSystemWorldSavedData get() {
		return get(DimensionManager.getWorld(0));
	}

	public static SolarSystemWorldSavedData get(World world) {
		SolarSystemWorldSavedData result = (SolarSystemWorldSavedData) world.mapStorage.loadData(SolarSystemWorldSavedData.class, DATA_NAME);
		
		if(result == null) {
			world.mapStorage.setData(DATA_NAME, new SolarSystemWorldSavedData(DATA_NAME));
			result = (SolarSystemWorldSavedData) world.mapStorage.loadData(SolarSystemWorldSavedData.class, DATA_NAME);
		}
		
		return result;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		for(CelestialBody body : CelestialBody.getAllBodies()) {
			if(nbt.hasKey("b_" + body.name)) {
				NBTTagCompound data = nbt.getCompoundTag("b_" + body.name);
				HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait> traits = new HashMap<>();

				for(Entry<String, Class<? extends CelestialBodyTrait>> entry : CelestialBodyTrait.traitMap.entrySet()) {
					if(data.hasKey(entry.getKey())) {
						try {
							CelestialBodyTrait trait = entry.getValue().newInstance();
							trait.readFromNBT(data.getCompoundTag(entry.getKey()));
							traits.put(trait.getClass(), trait);
						} catch (Exception ex) {}
					}
				}

				traitMap.put(body.name, traits);
			}
		}

		NBTTagList stationList = nbt.getTagList("stations", NBT.TAG_COMPOUND);
		for(int i = 0; i < stationList.tagCount(); i++) {
			NBTTagCompound stationTag = stationList.getCompoundTagAt(i);
			int x = stationTag.getInteger("x");
			int z = stationTag.getInteger("z");
			CelestialBody orbiting = CelestialBody.getBody(stationTag.getString("orbiting"));
			CelestialBody target = CelestialBody.getBody(stationTag.getString("target"));
			StationState state = StationState.values()[stationTag.getInteger("state")];
			int stateTimer = stationTag.getInteger("stateTimer");
			int maxStateTimer = stationTag.getInteger("maxStateTimer");
			boolean hasStation = stationTag.getBoolean("hasStation");
			String name = stationTag.getString("name");

			ChunkCoordIntPair pos = new ChunkCoordIntPair(x, z);
			OrbitalStation station = new OrbitalStation(orbiting, x, z);
			station.target = target;
			station.state = state;
			station.stateTimer = stateTimer;
			station.maxStateTimer = maxStateTimer;
			station.hasStation = hasStation;
			station.name = name;

			stations.put(pos, station);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		for(Entry<String, HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait>> entry : traitMap.entrySet()) {
			NBTTagCompound data = new NBTTagCompound();

			for(CelestialBodyTrait trait : entry.getValue().values()) {
				String name = CelestialBodyTrait.traitMap.inverse().get(trait.getClass());
				NBTTagCompound traitData = new NBTTagCompound();
				trait.writeToNBT(traitData);
				data.setTag(name, traitData);
			}
	
			nbt.setTag("b_" + entry.getKey(), data);
		}

		NBTTagList stationList = new NBTTagList();
		for(OrbitalStation station : stations.values()) {
			NBTTagCompound stationTag = new NBTTagCompound();
			stationTag.setInteger("x", station.dX);
			stationTag.setInteger("z", station.dZ);
			stationTag.setString("orbiting", station.orbiting.name);
			stationTag.setString("target", station.target.name);
			stationTag.setInteger("state", station.state.ordinal());
			stationTag.setInteger("stateTimer", station.stateTimer);
			stationTag.setInteger("maxStateTimer", station.maxStateTimer);
			stationTag.setBoolean("hasStation", station.hasStation);
			stationTag.setString("name", station.name);

			stationList.appendTag(stationTag);
		}
		nbt.setTag("stations", stationList);
	}

	public void setTraits(String bodyName, CelestialBodyTrait... traits) {
		if(traits.length == 0) {
			clearTraits(bodyName);
			return;
		}

		HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait> newTraits = new HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait>();
		for(CelestialBodyTrait trait : traits) {
			newTraits.put(trait.getClass(), trait);
		}

		this.traitMap.put(bodyName, newTraits);
		
		markDirty();
	}

	public void clearTraits(String bodyName) {
		this.traitMap.remove(bodyName);
		
		markDirty();
	}

	public HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait> getTraits(String bodyName) {
		return traitMap.get(bodyName);
	}

	// Grabs an existing station
	public OrbitalStation getStationFromPosition(int x, int z) {
		// yeah they aren't exactly chunks but this is a nice little hashable that already exists
		ChunkCoordIntPair pos = new ChunkCoordIntPair(MathHelper.floor_float((float)x / OrbitalStation.STATION_SIZE), MathHelper.floor_float((float)z / OrbitalStation.STATION_SIZE));
		return stations.get(pos);
	}

	public ChunkCoordIntPair findFreeSpace() {
		int size = SpaceConfig.maxProbeDistance / OrbitalStation.STATION_SIZE;

		// Has a guard so it doesn't loop forever on a spammed out world
		ChunkCoordIntPair pos = new ChunkCoordIntPair(rand.nextInt(size * 2) - size, rand.nextInt(size * 2) - size);
		for(int i = 0; stations.containsKey(pos) && i < 512; i++) {
			pos = new ChunkCoordIntPair(rand.nextInt(size * 2) - size, rand.nextInt(size * 2) - size);
		}

		return pos;
	}

	// Finds an unoccupied space and adds a new station
	public OrbitalStation addStation(CelestialBody orbiting) {
		ChunkCoordIntPair pos = findFreeSpace();

		return addStation(pos.chunkXPos, pos.chunkZPos, orbiting);
	}

	// Adds a station at a given set of coordinates (used for debug stations)
	// Won't overwrite existing stations
	public OrbitalStation addStation(int x, int z, CelestialBody orbiting) {
		ChunkCoordIntPair pos = new ChunkCoordIntPair(x, z);

		OrbitalStation station = stations.get(pos);

		if(station == null) {
			station = new OrbitalStation(orbiting, x, z);
			stations.put(pos, station);
		}

		markDirty();

		return station;
	}


	// Client sync
	private static HashMap<String, HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait>> clientTraits = new HashMap<>();

	public static void updateClientTraits(HashMap<String, HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait>> traits) {
		clientTraits = traits;
		if(clientTraits == null) clientTraits = new HashMap<>();
	}

	public static HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait> getClientTraits(String bodyName) {
		return clientTraits.get(bodyName);
	}
	
}
