package com.hbm.hrist;

import com.hbm.util.fauxpointtwelve.DirPos;

// like a tile entity but without all the useless crap
public class ConduitPiece {

	protected boolean valid = true;
	
	// definitions and lines are always 1:1, definition of index 0 is associated with line of index 0
	// could have probably solved this with a new class, now that i think of it
	public ConnectionDefinition[] definitions;
	public ConduitLine[] liveConnections;
	
	public ConduitPiece(ConnectionDefinition... defs) {
		definitions = defs;
		for(ConnectionDefinition def : defs) def.withParent(this);
		liveConnections = new ConduitLine[defs.length];
	}
	
	public boolean isValid() { return this.valid; }
	
	// if a piece goes offline, the entire connection dies with it ad has to be recalculated out of the surviving pieces
	public void invalidate() {
		this.valid = false;
		for(ConduitLine con : this.liveConnections) {
			if(con != null) con.destroy();
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
			if(parent == null) throw new IllegalStateException("Connection def has been initialized with no parent!"); // never happens
			for(int i = 0; i < parent.definitions.length; i++) {
				if(parent.definitions[i] == this) return parent.liveConnections[i];
			}
			return null;
		}
		
		public void setLine(ConduitLine line) {
			if(parent == null) throw new IllegalStateException("Connection def has been initialized with no parent!"); // never happens
			for(int i = 0; i < parent.definitions.length; i++) {
				if(parent.definitions[i] == this) this.parent.liveConnections[i] = line;
			}
		}
	}
}
