package com.hbm.hrist;

import java.util.HashMap;
import java.util.Map;

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
	
	public static class ConduitWorld {
		
		/** Maps conduit core pos to the actual conduit piece logical unit, for access of the conduit blocks */
		public static Map<BlockPos, ConduitPiece> pieces = new HashMap();
		/** Maps a connection pos to a conduit piece, used for calculating connections */
		public static Map<DirPos, ConduitPiece> connections = new HashMap();
		
		public void push(ConduitPiece piece, BlockPos core) {
			pieces.put(core, piece);
			
			// for branches, there's some repetition in the keys, but we don't care about that
			// since this maps to the PIECE, not the CONNECTION
			for(ConnectionDefinition def : piece.definitions) {
				pieces.put(def.connectors[0], piece);
				pieces.put(def.connectors[1], piece);
			}
		}
		
		public void pop(ConduitPiece piece, BlockPos core) {
			if(piece == null) return;
			pieces.remove(core);
			
			for(ConnectionDefinition def : piece.definitions) {
				pieces.remove(def.connectors[0]);
				pieces.remove(def.connectors[1]);
			}
		}
	}
}
