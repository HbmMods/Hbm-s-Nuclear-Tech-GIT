package com.hbm.hrist;

import java.util.HashSet;
import java.util.Set;

import com.hbm.hrist.ConduitPiece.ConnectionDefinition;
import com.hbm.hrist.ConduitSpace.ConduitWorld;

import net.minecraft.world.World;

/** Generated out of multiple ConnectionDefinitions to form a straight segment, ends at a branch */
public class ConduitLine {

	public World world;
	protected boolean valid = true;
	public LineEndpoint[] connectedTo = new LineEndpoint[2]; // a sausage always has two ends
	private Set<ConnectionDefinition> constructedFrom = new HashSet();
	public ConnectionStatus state = ConnectionStatus.ONLINE;
	
	public double cachedDistance = 0;
	public boolean hasChanged = true;
	
	public ConduitLine(World world) {
		this.world = world;
	}
	
	public int getDefCount() {
		return this.constructedFrom.size();
	}
	
	public void join(ConnectionDefinition def) {
		this.constructedFrom.add(def);
		this.setChanged();
	}
	
	public void absorb(ConduitLine smallerLine) {
		for(ConnectionDefinition smallerDef : smallerLine.constructedFrom) {
			smallerDef.setLine(this);
		}
		smallerLine.constructedFrom.clear();
		smallerLine.invalidate();
	}
	
	public void setChanged() { this.hasChanged = true; }
	
	public double getDistance() {
		if(this.hasChanged) {
			this.cachedDistance = 0;
			for(ConnectionDefinition def : constructedFrom) this.cachedDistance += def.distance;
		}
		return this.cachedDistance;
	}
	
	public boolean isValid() { return this.valid; }
	public void invalidate() { this.valid = false; }
	
	public void destroy() {
		ConduitWorld cWorld = ConduitSpace.worlds.get(world);
		for(ConnectionDefinition def : constructedFrom) {
			if(def.getLine() == this) {
				def.setLine(null);
				if(cWorld != null && def.parent.valid) cWorld.orphans.add(def);
			}
		}
		this.constructedFrom.clear();
		this.invalidate();
	}
	
	public static enum ConnectionStatus {
		ONLINE,
		OFFLINE
	}
	
	public static class LineEndpoint {
		public ConduitLine[] connections;
		
		public LineEndpoint(ConduitLine... connections) {
			this.connections = connections;
		}
	}
}
