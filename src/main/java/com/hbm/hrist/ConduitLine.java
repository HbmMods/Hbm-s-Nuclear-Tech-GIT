package com.hbm.hrist;

import java.util.HashSet;
import java.util.Set;

import com.hbm.hrist.ConduitPiece.ConnectionDefinition;

/** Generated out of multiple ConnectionDefinitions to form a straight segment, ends at a branch */
public class ConduitLine {

	protected boolean valid = true;
	public LineEndpoint[] connectedTo = new LineEndpoint[2]; // a sausage always has two ends
	public Set<ConnectionDefinition> constructedFrom = new HashSet();
	public ConnectionStatus state = ConnectionStatus.ONLINE;
	
	public double cachedDistance = 0;
	public boolean hasChanged = true;
	
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
		for(ConnectionDefinition def : constructedFrom) {
			if(def.getLine() == this) {
				def.setLine(null);
			}
		}
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
