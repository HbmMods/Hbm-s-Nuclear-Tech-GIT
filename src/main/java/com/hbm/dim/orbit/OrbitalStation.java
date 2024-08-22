package com.hbm.dim.orbit;

import com.hbm.dim.CelestialBody;
import com.hbm.dim.SolarSystemWorldSavedData;

import io.netty.buffer.ByteBuf;

public class OrbitalStation {

	public CelestialBody orbiting;
	public int x;
	public int z;

	public static OrbitalStation clientStation = new OrbitalStation(CelestialBody.getBody(0));

	public static final int STATION_SIZE = 1024; // total area for each station
	public static final int BUFFER_SIZE = 256; // size of the buffer region that drops you out of orbit (preventing seeing other stations)
	
	/**
	 * Space station spatial space
	 * - Stations are spread out over the orbital dimension, in restricted areas
	 * - Attempting to leave the region your station is in will cause you to fall back to the orbited planet
	 * - The current station you are near is fetched using the player's XZ coordinate
	 * - The station will determine what body is being orbited, and therefore what to show in the skybox
	 */

	// For client
	public OrbitalStation(CelestialBody orbiting) {
		this.orbiting = orbiting;
	}

	// For server
	public OrbitalStation(CelestialBody orbiting, int x, int z) {
		this(orbiting);
		this.x = x;
		this.z = z;
	}

	// Finds a space station for a given set of coordinates
	public static OrbitalStation getStation(int x, int z) {
		// yeah they aren't exactly chunks but this is a nice little hashable that already exists
		SolarSystemWorldSavedData data = SolarSystemWorldSavedData.get();
		OrbitalStation station = data.getStation(x, z);

		// Fallback for when a station doesn't exist (should only occur when using debug wand!)
		if(station == null) {
			station = data.addStation(x, z, CelestialBody.getBody(0));
		}

		return station;
	}

	public void serialize(ByteBuf buf) {
		buf.writeInt(orbiting.dimensionId);
	}

	public static OrbitalStation deserialize(ByteBuf buf) {
		OrbitalStation station = new OrbitalStation(CelestialBody.getBody(buf.readInt()));
		return station;
	}

}
