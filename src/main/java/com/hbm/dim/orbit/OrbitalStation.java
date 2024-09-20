package com.hbm.dim.orbit;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

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

	public String name = ""; // I dub thee

	public CelestialBody orbiting;
	public CelestialBody target;

	public StationState state = StationState.ORBIT;
	public int stateTimer;
	public int maxStateTimer = 100;

	public boolean hasStation = false;

	// the coordinates of the station within the dimension
	public int dX;
	public int dZ;

	public List<String> errors = new ArrayList<String>();
	public int errorTimer;

	public enum StationState {
		ORBIT, // big chillin
		LEAVING, // prepare engines for transfer
		TRANSFER, // going from A to B
		ARRIVING, // spool down engines
	}

	private TileEntityOrbitalStation mainPort;
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
		if(!canTravel(orbiting, target)) {
			collectErrors();
			return;
		}

		setState(StationState.LEAVING, getLeaveTime());
		this.target = target;
	}

	public void update(World world) {
		if(!world.isRemote) {
			if(state == StationState.LEAVING) {
				if(stateTimer > maxStateTimer) {
					setState(StationState.TRANSFER, getTransferTime());
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
				errors = new ArrayList<String>();
				errors.add("No engines available");
				errorTimer = 2;
			}

			errorTimer--;
			if(errorTimer <= 0) {
				errors = new ArrayList<String>();
				errorTimer = 0;
			}
		}

		stateTimer++;
	}

	private boolean canTravel(CelestialBody from, CelestialBody to) {
		if(engines.size() == 0) return false;

		double deltaV = SolarSystem.getDeltaVBetween(from, to);
		int shipMass = 200_000; // Always static, to not punish building big cool stations
		float totalThrust = getTotalThrust();

		for(IPropulsion engine : engines) {
			float massPortion = engine.getThrust() / totalThrust;
			if(!engine.canPerformBurn(Math.round(shipMass * massPortion), deltaV)) {
				return false;
			}
		}

		return true;
	}

	private void collectErrors() {
		errors = new ArrayList<String>();
		for(IPropulsion engine : engines) {
			engine.addErrors(errors);
		}
		errorTimer = 100;
	}

	private float getTotalThrust() {
		float thrust = 0;
		for(IPropulsion engine : engines) {
			thrust += engine.getThrust();
		}
		return thrust;
	}

	// Has the side effect of beginning engine burns
	private int getLeaveTime() {
		int leaveTime = 20;
		for(IPropulsion engine : engines) {
			int time = engine.startBurn();
			if(time > leaveTime) leaveTime = time;
		}
		return leaveTime;
	}

	// And this one will end engine burns
	private int getArriveTime() {
		int arriveTime = 20;
		for(IPropulsion engine : engines) {
			int time = engine.endBurn();
			if(time > arriveTime) arriveTime = time;
		}
		return arriveTime;
	}

	private int getTransferTime() {
		if(mainPort == null) return -1;

		int size = calculateSize();
		double distance = SolarSystem.calculateDistanceBetweenTwoBodies(mainPort.getWorldObj(), orbiting, target);
		float thrust = getTotalThrust();

		return (int)(Math.log(1 + (distance * size / thrust * 100)) * 150);
	}

	private void setState(StationState state, int timeUntilNext) {
		this.state = state;
		stateTimer = 0;
		maxStateTimer = timeUntilNext;
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
		if(port.getBlockType() == ModBlocks.orbital_station) mainPort = port;
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

	// I can't stop pronouncing this as hors d'oeuvre
	private static final ForgeDirection[] horDir = new ForgeDirection[] { ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.WEST, ForgeDirection.EAST };

	// calculates the top down area of the station
	// super fucking fast but like, don't call it every frame
	public int calculateSize() {
		if(mainPort == null) return 0;
		World world = mainPort.getWorldObj();

		int minX, maxX;
		int minZ, maxZ;
		minX = maxX = mainPort.xCoord;
		minZ = maxZ = mainPort.zCoord;

		Stack<ThreeInts> stack = new Stack<ThreeInts>();
		stack.push(new ThreeInts(mainPort.xCoord, mainPort.yCoord, mainPort.zCoord));

		HashSet<ThreeInts> visited = new HashSet<ThreeInts>();

		while(!stack.isEmpty()) {
			ThreeInts pos = stack.pop();
			visited.add(pos);

			if(pos.x < minX) minX = pos.x;
			if(pos.x > maxX) maxX = pos.x;
			if(pos.z < minZ) minZ = pos.z;
			if(pos.z > maxZ) maxZ = pos.z;

			for(ForgeDirection dir : horDir) {
				ThreeInts nextPos = pos.getPositionAtOffset(dir);

				if(!visited.contains(nextPos) && isInStation(world, nextPos)) {
					stack.push(nextPos);
				}
			}
		}

		return (maxX - minX + 1) * (maxZ - minZ + 1);
	}

	private boolean isInStation(World world, ThreeInts pos) {
		if(world.getHeightValue(pos.x, pos.z) > 1) return true;
		return Math.abs(pos.x - mainPort.xCoord) < 5 && Math.abs(pos.z - mainPort.zCoord) < 5; // minimum station size
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

		buf.writeInt(errors.size());
		for(String error : errors) {
			BufferUtil.writeString(buf, error);
		}
	}

	public static OrbitalStation deserialize(ByteBuf buf) {
		OrbitalStation station = new OrbitalStation(CelestialBody.getBody(buf.readInt()));
		station.target = CelestialBody.getBody(buf.readInt());
		station.state = StationState.values()[buf.readInt()];
		station.stateTimer = buf.readInt();
		station.maxStateTimer = buf.readInt();
		station.name = BufferUtil.readString(buf);

		station.errors = new ArrayList<String>();
		int count = buf.readInt();
		for(int i = 0; i < count; i++) {
			station.errors.add(BufferUtil.readString(buf));
		}
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
