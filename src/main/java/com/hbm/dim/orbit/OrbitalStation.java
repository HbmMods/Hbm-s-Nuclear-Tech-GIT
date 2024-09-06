package com.hbm.dim.orbit;

import java.util.HashSet;
import java.util.HashMap;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.BlockOrbitalStation;
import com.hbm.dim.CelestialBody;
import com.hbm.dim.SolarSystem;
import com.hbm.dim.SolarSystemWorldSavedData;
import com.hbm.handler.ThreeInts;
import com.hbm.tileentity.machine.TileEntityOrbitalStation;
import com.hbm.util.BufferUtil;

import api.hbm.tile.IPropulsion;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class OrbitalStation {

	public String name; // I dub thee

	public CelestialBody orbiting;
	public CelestialBody target;

	public StationState state = StationState.ORBIT;
	public int stateTimer;
	public int maxStateTimer = 100;

	public boolean hasStation = false;

	// the coordinates of the station within the dimension
	public int dX;
	public int dZ;

	public String error;
	public int errorTimer;

	public enum StationState {
		ORBIT, // big chillin
		LEAVING, // prepare engines for transfer
		TRANSFER, // going from A to B
		ARRIVING, // spool down engines
	}

	private HashMap<ThreeInts, TileEntityOrbitalStation> ports = new HashMap<>();
	private int portIndex = 0;

	private HashSet<IPropulsion> engines = new HashSet<>();

	public static OrbitalStation clientStation = new OrbitalStation(CelestialBody.getBody(0));

	public static final int STATION_SIZE = 1024; // total area for each station
	public static final int BUFFER_SIZE = 256; // size of the buffer region that drops you out of orbit (preventing seeing other stations)
	public static final int WARNING_SIZE = 32; // size of the region that warns the player about falling out of orbit


	
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

	public void travelTo(World world, CelestialBody target) {
		if(state != StationState.ORBIT) return; // only when at rest can we start a new journey
		if(!canTravel()) return;

		setState(StationState.LEAVING, getLeaveTime());
		this.target = target;
	}

	public void update(World world) {
		if(!world.isRemote) {
			if(state == StationState.LEAVING) {
				if(stateTimer > maxStateTimer) {
					setState(StationState.TRANSFER, getTransferTime(world));
				}
			} else if(state == StationState.TRANSFER) {
				if(stateTimer > maxStateTimer) {
					setState(StationState.ARRIVING, getArriveTime());
					orbiting = target;
				}
			} else if(state == StationState.ARRIVING) {
				if(stateTimer > maxStateTimer) {
					setState(StationState.ORBIT, 0);
				}
			}

			SolarSystemWorldSavedData.get().markDirty();

			if(engines.size() == 0) {
				setError("No engines available");
				errorTimer = 2;
			}

			errorTimer--;
			if(errorTimer <= 0) {
				error = null;
				errorTimer = 0;
			}
		}

		stateTimer++;
	}

	private boolean canTravel() {
		if(engines.size() == 0) return false;

		for(IPropulsion engine : engines) {
			if(!engine.canPerformBurn(100_000, 5_000)) {
				setError("Engines require fuel");
				return false;
			}
		}

		return true;
	}

	// Has the side effect of beginning engine burns
	private int getLeaveTime() {
		int leaveTime = 0;
		for(IPropulsion engine : engines) {
			int time = engine.startBurn();
			if(time > leaveTime) leaveTime = time;
		}
		return leaveTime;
	}

	// And this one will end engine burns
	private int getArriveTime() {
		int arriveTime = 0;
		for(IPropulsion engine : engines) {
			int time = engine.endBurn();
			if(time > arriveTime) arriveTime = time;
		}
		return arriveTime;
	}

	private int getTransferTime(World world) {
		double distance = SolarSystem.calculateDistanceBetweenTwoBodies(world, orbiting, target);
		return (int)(Math.log(1 + (distance / 1000)) * 125);
	}

	private void setState(StationState state, int timeUntilNext) {
		this.state = state;
		stateTimer = 0;
		maxStateTimer = timeUntilNext;
	}

	private void setError(String error) {
		this.error = error;
		errorTimer = 100;
	}
	
	public static void addPropulsion(IPropulsion propulsion) {
		TileEntity te = propulsion.getTileEntity();
		OrbitalStation station = getStationFromPosition(te.xCoord, te.zCoord);
		station.engines.add(propulsion);
	}

	public static void removePropulsion(IPropulsion propulsion) {
		TileEntity te = propulsion.getTileEntity();
		OrbitalStation station = getStationFromPosition(te.xCoord, te.zCoord);
		station.engines.remove(propulsion);
	}

	public void addPort(TileEntityOrbitalStation port) {
		ports.put(new ThreeInts(port.xCoord, port.yCoord, port.zCoord), port);
	}

	public TileEntityOrbitalStation getPort() {
		if(ports.size() == 0) return null;

		// First, find any port that's available
		int index = 0;
		for(TileEntityOrbitalStation port : ports.values()) {
			if(!port.hasDocked && !port.isReserved) {
				portIndex = index;
				return port;
			}
			index++;
		}

		// Failing that, round robin on the occupied ports
		portIndex++;
		if(portIndex >= ports.size()) portIndex = 0;

		return ports.values().toArray(new TileEntityOrbitalStation[ports.size()])[portIndex];
	}

	public static TileEntityOrbitalStation getPort(int x, int z) {
		return getStationFromPosition(x, z).getPort();
	}

	public double getUnscaledProgress(float partialTicks) {
		if(state == StationState.ORBIT) return 0;
		return MathHelper.clamp_double(((double)stateTimer + partialTicks) / (double)maxStateTimer, 0, 1);
	}

	public double getTransferProgress(float partialTicks) {
		if(state != StationState.TRANSFER) return 0;
		return easeInOutCirc(getUnscaledProgress(partialTicks));
	}

	private double easeInOutCirc(double t) {
		return t < 0.5
			? (1 - Math.sqrt(1 - Math.pow(2 * t, 3))) / 2
			: (Math.sqrt(1 - Math.pow(-2 * t + 2, 3)) + 1) / 2;
	}

	// Finds a space station for a given set of coordinates
	public static OrbitalStation getStationFromPosition(int x, int z) {
		SolarSystemWorldSavedData data = SolarSystemWorldSavedData.get();
		OrbitalStation station = data.getStationFromPosition(x, z);

		// Fallback for when a station doesn't exist (should only occur when using debug wand!)
		if(station == null) {
			station = data.addStation(MathHelper.floor_float((float)x / STATION_SIZE), MathHelper.floor_float((float)z / STATION_SIZE), CelestialBody.getBody(0));
		}

		return station;
	}

	public static OrbitalStation getStation(int x, int z) {
		return getStationFromPosition(x * STATION_SIZE, z * STATION_SIZE);
	}

	public void serialize(ByteBuf buf) {
		buf.writeInt(orbiting.dimensionId);
		buf.writeInt(target.dimensionId);
		buf.writeInt(state.ordinal());
		buf.writeInt(stateTimer);
		buf.writeInt(maxStateTimer);
		BufferUtil.writeString(buf, name);
		BufferUtil.writeString(buf, error);
	}

	public static OrbitalStation deserialize(ByteBuf buf) {
		OrbitalStation station = new OrbitalStation(CelestialBody.getBody(buf.readInt()));
		station.target = CelestialBody.getBody(buf.readInt());
		station.state = StationState.values()[buf.readInt()];
		station.stateTimer = buf.readInt();
		station.maxStateTimer = buf.readInt();
		station.name = BufferUtil.readString(buf);
		station.error = BufferUtil.readString(buf);
		return station;
	}

	public static void spawn(World world, int x, int z) {
		int y = 127;
		if(world.getBlock(x, y, z) == ModBlocks.orbital_station) return;

		BlockOrbitalStation block = (BlockOrbitalStation) ModBlocks.orbital_station;

		BlockDummyable.safeRem = true;
		world.setBlock(x, y, z, block, 12, 3);
		block.fillSpace(world, x, y, z, ForgeDirection.NORTH, 0);
		BlockDummyable.safeRem = false;
	}

	// Mark the station as travelable
	public static void addStation(int x, int z, CelestialBody body) {
		SolarSystemWorldSavedData data = SolarSystemWorldSavedData.get();
		OrbitalStation station = data.getStationFromPosition(x * STATION_SIZE, z * STATION_SIZE);

		if(station == null) {
			station = data.addStation(x, z, body);
		}

		station.orbiting = station.target = body;
		station.hasStation = true;
	}

}
