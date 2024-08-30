package com.hbm.dim.orbit;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.dim.CelestialBody;
import com.hbm.dim.SolarSystemWorldSavedData;
import com.hbm.handler.MultiblockHandlerXR;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class OrbitalStation {

	public CelestialBody orbiting;
	public CelestialBody target;

	public StationState state = StationState.ORBIT;
	public int stateTimer;

	// the coordinates of the station within the dimension
	public int dX;
	public int dZ;

	public enum StationState {
		ORBIT, // big chillin
		LEAVING, // leaving orbit
		TRANSFER, // going from A to B
		ARRIVING, // we got there
	}

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
		this.target = orbiting;
	}

	// For server
	public OrbitalStation(CelestialBody orbiting, int x, int z) {
		this(orbiting);
		this.dX = x;
		this.dZ = z;
	}

	public void travelTo(CelestialBody target) {
		if(state != StationState.ORBIT) return; // only when at rest can we start a new journey

		state = StationState.TRANSFER;
		stateTimer = 0;
		this.target = target;
	}

	public void update(World world) {
		if(!world.isRemote) {
			if(state == StationState.TRANSFER) {
				if(stateTimer > 400) {
					state = StationState.ORBIT;
					orbiting = target;
				}
			}

			SolarSystemWorldSavedData.get().markDirty();
		}

		stateTimer++;
	}

	public double getProgress(float partialTicks) {
		if(state != StationState.TRANSFER) return 0;
		double t = MathHelper.clamp_double(((double)stateTimer + partialTicks) * 0.0025D, 0, 1);
		return easeInOutCirc(t);
	}

	private double easeInOutCirc(double t) {
		return t < 0.5
			? (1 - Math.sqrt(1 - Math.pow(2 * t, 4))) / 2
			: (Math.sqrt(1 - Math.pow(-2 * t + 2, 4)) + 1) / 2;
	}

	// Finds a space station for a given set of coordinates
	public static OrbitalStation getStation(int x, int z) {
		SolarSystemWorldSavedData data = SolarSystemWorldSavedData.get();
		OrbitalStation station = data.getStation(x, z);

		// Fallback for when a station doesn't exist (should only occur when using debug wand!)
		if(station == null) {
			station = data.addStation(x / STATION_SIZE, z / STATION_SIZE, CelestialBody.getBody(0));
		}

		return station;
	}

	public void serialize(ByteBuf buf) {
		buf.writeInt(orbiting.dimensionId);
		buf.writeInt(target.dimensionId);
		buf.writeInt(state.ordinal());
		buf.writeInt(stateTimer);
	}

	public static OrbitalStation deserialize(ByteBuf buf) {
		OrbitalStation station = new OrbitalStation(CelestialBody.getBody(buf.readInt()));
		station.target = CelestialBody.getBody(buf.readInt());
		station.state = StationState.values()[buf.readInt()];
		station.stateTimer = buf.readInt();
		return station;
	}

	public static void spawn(World world, int x, int z) {
		int y = 127;
		if(world.getBlock(x, y, z) == ModBlocks.orbital_station) return;

		int[] dimensions = ((BlockDummyable)ModBlocks.orbital_station).getDimensions();

		BlockDummyable.safeRem = true;
		world.setBlock(x, y, z, ModBlocks.orbital_station, 12, 3);
		MultiblockHandlerXR.fillSpace(world, x, y, z, dimensions, ModBlocks.orbital_station, ForgeDirection.NORTH);
		BlockDummyable.safeRem = false;
	}

}
