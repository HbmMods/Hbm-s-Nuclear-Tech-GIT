package com.hbm.hrist;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.hbm.hrist.ConduitPiece.ConnectionDefinition;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import net.minecraft.world.World;

/// ROBUR PER UNITATEM ///
public class ConduitSpace {
	
	public static Map<World, ConduitWorld> worlds = new HashMap();
	
	public static void pushPiece(World world, ConduitPiece piece, BlockPos core) {
		ConduitWorld cWorld = worlds.get(world);
		if(cWorld == null) {
			cWorld = new ConduitWorld();
			worlds.put(world, cWorld);
		}
		cWorld.push(piece, core);
	}
	
	public static void popPiece(World world, BlockPos core) {
		ConduitWorld cWorld = worlds.get(world);
		if(cWorld == null) return;
		cWorld.pop(cWorld.pieces.get(core), core);
	}
	
	public static void updateWorld(World world) {
		ConduitWorld cWorld = worlds.get(world);
		if(cWorld == null) return;
		cWorld.update();
	}
	
	public static class ConduitWorld {
		
		/** Maps conduit core pos to the actual conduit piece logical unit, for access of the conduit blocks */
		public Map<BlockPos, ConduitPiece> pieces = new HashMap();
		/** Maps a connection pos to a conduit piece, used for calculating connections.
		 * One position can correspond to multiple connection defs, so we have to store the entire piece. */
		public Map<DirPos, ConduitPiece> connections = new HashMap();
		/** Set of all definitions not yet part of a line */
		public Set<ConnectionDefinition> orphans = new LinkedHashSet();
		
		public void push(ConduitPiece piece, BlockPos core) {
			pieces.put(core, piece);
			
			// for branches, there's some repetition in the keys, but we don't care about that
			// since this maps to the PIECE, not the CONNECTION
			for(ConnectionDefinition def : piece.definitions) {
				connections.put(def.connectors[0], piece);
				connections.put(def.connectors[1], piece);
				orphans.add(def);
			}
			
			for(ConnectionDefinition con : piece.definitions) orphans.add(con);
		}
		
		public void pop(ConduitPiece piece, BlockPos core) {
			if(piece == null) return;
			pieces.remove(core);
			
			for(ConnectionDefinition def : piece.definitions) {
				pieces.remove(def.connectors[0]);
				pieces.remove(def.connectors[1]);
			}
			
			for(ConnectionDefinition con : piece.definitions) orphans.remove(con);
			
			piece.invalidate();
		}
		
		public void update() {
			
			Iterator<ConnectionDefinition> it = orphans.iterator();
			
			while(it.hasNext()) {
				ConnectionDefinition orphan = it.next();
				ConduitLine line = orphan.getLine();
				
				for(DirPos pos : orphan.connectors) {
					DirPos connection = pos.flip();
					ConduitPiece connectedPiece = connections.get(connection);
					if(connectedPiece == null) continue; // if no piece is actually connected, skip
					if(connectedPiece == orphan.parent) continue; // no self-fellating
					if(connectedPiece.hasMultipleConnections(connection)) continue; // if this connection leads to a switch, skip
					
					for(ConnectionDefinition connectedDef : connectedPiece.definitions) {
						ConduitLine connectedLine = connectedDef.getLine();
						if(connectedLine == null) continue;
						
						line = orphan.getLine();
						// if the current line is null
						if(line == null) {
							if(connectedDef.connectors[0].equals(connection) || connectedDef.connectors[1].equals(connection)) {
								orphan.setLine(connectedLine);
							}
						// if not, merge
						} else {
							// larger one eats the smaller one for performance
							ConduitLine larger = line.constructedFrom.size() > connectedLine.constructedFrom.size() ? line : connectedLine;
							ConduitLine smaller = line.constructedFrom.size() > connectedLine.constructedFrom.size() ? connectedLine : line;
							
							larger.constructedFrom.addAll(smaller.constructedFrom);
							for(ConnectionDefinition smallerDef : smaller.constructedFrom) {
								smallerDef.setLine(larger);
							}
							smaller.constructedFrom.clear();
							larger.setChanged();
							smaller.invalidate();
						}
					}
				}
				
				if(orphan.getLine() == null) {
					ConduitLine newLine = new ConduitLine();
					orphan.setLine(newLine);
					newLine.constructedFrom.add(orphan);
					newLine.setChanged();
				}
			}
			
			orphans.clear();
		}
	}
}
