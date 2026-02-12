package com.hbm.hrist;

import com.hbm.util.fauxpointtwelve.DirPos;

// like a tile entity but without all the useless crap
public class ConduitPiece {

	protected boolean valid = true;
	public ConnectionDefinition[] definitions;
	
	public ConduitPiece(ConnectionDefinition... defs) {
		definitions = defs;
		for(ConnectionDefinition def : defs) def.withParent(this);
	}
	
	public boolean isValid() { return this.valid; }
	
	// if a piece goes offline, the entire connection dies with it and has to be recalculated out of the surviving pieces
	public void invalidate() {
		this.valid = false;
		for(ConnectionDefinition def : this.definitions) {
			if(def.liveConnection != null) def.liveConnection.destroy();
		}
	}
	
	/** if it's a switch, then we don't want a line connection there */
	public boolean hasMultipleConnections(DirPos pos) {
		int count = 0;
		for(ConnectionDefinition def : definitions) {
			if(def.connectors[0].equals(pos)) count++;
			if(def.connectors[1].equals(pos)) count++;
			if(count > 1) return true;
		}
		return false;
	}
	
	/** Describes each branch or connecting line on a piece, building block and not a working instance */
	public static class ConnectionDefinition {
		
		public ConduitPiece parent;
		public final DirPos[] connectors = new DirPos[2];
		public final double distance;
		public ConduitLine liveConnection;
		
		public ConnectionDefinition(DirPos start, DirPos end) {
			this(start, end, start.distanceTo(end));
		}
		
		public ConnectionDefinition(DirPos start, DirPos end, double distance) {
			this.connectors[0] = start;
			this.connectors[1] = end;
			this.distance = distance;
		}
		
		public ConnectionDefinition withParent(ConduitPiece parent) {
			this.parent = parent;
			return this;
		}
		
		public ConduitLine getLine() {
			return liveConnection;
		}
		
		public void setLine(ConduitLine line) {
			this.liveConnection = line;
		}
	}
}
