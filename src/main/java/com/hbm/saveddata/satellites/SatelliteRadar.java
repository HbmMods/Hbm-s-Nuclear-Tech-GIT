package com.hbm.saveddata.satellites;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.hbm.tileentity.machine.TileEntityMachineRadarNT;

import api.hbm.entity.IRadarDetectableNT.RadarScanParams;
import api.hbm.redstoneoverradio.IRORInteractive;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

/**
 * Basically the AUTOCAL is now mandatory lol lmao
 */
public class SatelliteRadar extends SatelliteBase {
	
	public static final int MAX_SCAN_RANGE = 1_000;
	public static RadarScanParams scanParams = new RadarScanParams(true, true, true, false);

	public static final String CMD_SURVEY = "survey";
	public static final String CMD_FILTER = "filter";
	public static final String CMD_COUNT = "count";
	public static final String CMD_GETTARGETID = "gettargetid";
	public static final String CMD_GETPOSITION = "getposition";
	public static final String CMD_GETNAME = "getname";
	
	public List<Entity> cachedRadarResults = new ArrayList();
	public List<Entity> filteredRadarResults = new ArrayList();
	
	public SatelliteRadar() { }

	@Override public String getType() { return "LEO_RADAR"; }
	
	@Override
	public void onCommandImpl(World world, String... cmd) {
		if(cmd.length <= 0) return;
		
		if(cmd[0].equals(CMD_SURVEY)) {
			
			cachedRadarResults.clear();
			
			for(Entity entity : TileEntityMachineRadarNT.matchingEntities) {
				if(entity.dimension != world.provider.dimensionId) continue;
				
				int x = (int) Math.floor(entity.posX);
				int z = (int) Math.floor(entity.posZ);
				
				double dX = x - targetX;
				double dZ = z - targetZ;
				
				if(dX * dX + dZ * dZ <= MAX_SCAN_RANGE * MAX_SCAN_RANGE) {
					cachedRadarResults.add(entity);
				}
			}
			
			filteredRadarResults = new ArrayList(cachedRadarResults);
			return;
		}
		
		if(cmd[0].equals(CMD_FILTER) && cmd.length == 2) {
			
			filteredRadarResults.clear();
			String filter = cmd[1].toLowerCase(Locale.US);
			
			for(Entity entity : cachedRadarResults) {
				if(entity.isDead) continue;
				String classname = entity.getClass().getSimpleName().toLowerCase(Locale.US);
				if(classname.contains(filter)) {
					filteredRadarResults.add(entity);
				}
			}
			return;
		}
		
		if(cmd[0].equals(CMD_COUNT)) {
			this.tx = "" + filteredRadarResults.size();
			return;
		}
		
		if(cmd[0].equals(CMD_GETTARGETID) && cmd.length == 2) {
			Entity target = getTargetFromIndex(cmd[1]);
			if(target == null) { this.tx = ""; return; }
			this.tx = "" + target.getEntityId();
			return;
		}
		
		if(cmd[0].equals(CMD_GETPOSITION) && cmd.length == 2) {
			Entity target = getTargetFromIndex(cmd[1]);
			if(target == null) { this.tx = ""; return; }
			this.tx = (int) Math.floor(target.posX) + ";" + (int) Math.floor(target.posY) + ";" + (int) Math.floor(target.posZ);
			return;
		}
		
		if(cmd[0].equals(CMD_GETNAME) && cmd.length == 2) {
			Entity target = getTargetFromIndex(cmd[1]);
			if(target == null) { this.tx = ""; return; }
			this.tx = target.getClass().getSimpleName().toLowerCase(Locale.US);
			return;
		}
	}
	
	public Entity getTargetFromIndex(String cmd) {
		if(filteredRadarResults.size() <= 0) return null;
		int index = IRORInteractive.parseInt(cmd, 1, filteredRadarResults.size()) - 1;
		Entity target = filteredRadarResults.get(index);
		if(target.isDead) return null;
		return target;
	}
}
