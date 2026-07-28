package com.hbm.saveddata.satellites;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import com.hbm.util.fauxpointtwelve.DimPos;

import api.hbm.redstoneoverradio.IRORInteractive;
import net.minecraft.world.World;

public class SatelliteRayScan extends SatelliteBase {

	public List<RayEvent> cachedResults = new ArrayList();
	
	public static final int MAX_SCAN_RANGE = 250;
	
	public static final String CMD_SURVEY = "survey";
	public static final String CMD_COUNT = "count";
	public static final String CMD_GETINFO = "getinfo";
	public static final String CMD_GETPOSITION = "getposition";
	
	public SatelliteRayScan() { }

	@Override public String getType() { return "NB_RAY_SCANNER"; }
	
	@Override
	public void onCommandImpl(World world, String... cmd) {
		if(cmd.length <= 0) return;
		
		if(cmd[0].equals(CMD_SURVEY)) {
			this.cachedResults.clear();
			
			for(Entry<DimPos, RayEvent> entry : rayEvent.entrySet()) {
				DimPos pos = entry.getKey();
				RayEvent event = entry.getValue();
				if(pos.getDim() != world.provider.dimensionId) continue;
				int dX = pos.getX() - this.targetX;
				int dZ = pos.getZ() - this.targetZ;
				
				if(dX * dX + dZ * dZ <= MAX_SCAN_RANGE * MAX_SCAN_RANGE) {
					this.cachedResults.add(event);
				}
			}
			return;
		}
		
		if(cmd[0].equals(CMD_COUNT)) {
			this.tx = "" + cachedResults.size();
			return;
		}
		
		if(cmd[0].equals(CMD_GETINFO) && cmd.length == 2) {
			RayEvent event = getEventFromIndex(cmd[1]);
			if(event == null) { this.tx = ""; return; }
			this.tx = "" + event.info;
			return;
		}
		
		if(cmd[0].equals(CMD_GETPOSITION) && cmd.length == 2) {
			RayEvent event = getEventFromIndex(cmd[1]);
			if(event == null) { this.tx = ""; return; }
			this.tx = event.x + ";" + event.z;
			return;
		}
	}
	
	public RayEvent getEventFromIndex(String cmd) {
		if(cachedResults.size() <= 0) return null;
		int index = IRORInteractive.parseInt(cmd, 0, cachedResults.size()) - 1;
		return cachedResults.get(index);
	}
	
	public static LinkedHashMap<DimPos, RayEvent> rayEvent = new LinkedHashMap();
	
	public static void reportEvent(World world, int x, int y, int z, String info, int lifetime) {
		rayEvent.put(new DimPos(x, y, z, world.provider.dimensionId), new RayEvent(world, lifetime, x, z, info));
	}
	
	// only once per second
	public static void updateSystem(World world) {
		
		rayEvent.entrySet().removeIf(entry -> {
			return world.provider.dimensionId == entry.getKey().getDim() && world.getTotalWorldTime() > entry.getValue().expiresOn;
		});
	}
	
	public static class RayEvent {

		public static final String INFO_ARC_FLASH = "ARC_FLASH";
		public static final String INFO_NUCLEAR = "NEUTRON_EMISSION";
		public static final String INFO_PARTICLE = "HIGH_ENERGY_PARTICLES";
		public static final String INFO_RADAR = "RADAR_WAVES";
		public static final String INFO_RADIO = "RADIO_WAVES";

		public long expiresOn;
		public String info;
		public int x;
		public int z;
		
		public RayEvent(World world, int lifetime, int x, int z, String info) {
			this.expiresOn = world.getTotalWorldTime() + lifetime;
			this.x = x;
			this.z = z;
			this.info = info;
		}
	}
}
