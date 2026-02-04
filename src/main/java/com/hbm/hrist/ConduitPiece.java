package com.hbm.hrist;

import com.hbm.util.fauxpointtwelve.BlockPos;

// like a tile entity but without all the useless crap
public class ConduitPiece {

	protected boolean valid = true;
	public ConnectionDefinition[] definitions;
	public ConduitConnection[] liveConnections;
	
	public ConduitPiece(ConnectionDefinition... defs) {
		definitions = defs;
		for(ConnectionDefinition def : defs) def.withParent(this);
		liveConnections = new ConduitConnection[defs.length];
	}
	
	public boolean isValid() { return this.valid; }
	
	// if a piece goes offline, the entire connection dies with it ad has to be recalculated out of the surviving pieces
	public void invalidate() {
		this.valid = false;
		for(ConduitConnection con : this.liveConnections) {
			if(con != null) con.invalidate();
		}
	}
	
	/** Describes each branch or connecting line on a piece, building block and not a working instance */
	public static class ConnectionDefinition {
		
		public ConduitPiece parent;
		public BlockPos[] connectors = new BlockPos[2];
		double distance;
		
		public ConnectionDefinition(BlockPos start, BlockPos end) {
			this(start, end, start.distanceTo(end));
		}
		
		public ConnectionDefinition(BlockPos start, BlockPos end, double distance) {
			this.connectors[0] = start;
			this.connectors[1] = end;
			this.distance = distance;
		}
		
		public ConnectionDefinition withParent(ConduitPiece parent) {
			this.parent = parent;
			return this;
		}
	}
}
