package com.hbm.uninos;

import java.util.HashMap;
import java.util.HashSet;

import com.hbm.util.fauxpointtwelve.BlockPos;

import net.minecraft.world.World;

public class UniNodespace {
	
	public static HashMap<World, UniNodeWorld> worlds = new HashMap();
	
	/*
	 * attempt #1 went south because there would be an entirely separate nodespace for every single possible type
	 * which for fluids means at least 150 alone, and that's not great.
	 * this is attempt #2 which is not good for reasons explained below
	 */
	public static class UniNodeWorld {
		
		public static int nextId = 0;

		//shot in the dark: how well is the dual hashmap system gonna perform?
		//how are we gonna handle type segregation for network forming?
		public HashMap<BlockPos, HashSet<Long>> posToId = new HashMap<>();
		public HashMap<Long, GenNode> idToNode = new HashMap<>();
		
		/** Adds a node at all its positions to the nodespace */
		public void pushNode(GenNode node) {
			for(BlockPos pos : node.positions) {
				HashSet<Long> set = posToId.get(pos);
				if(set == null) {
					set = new HashSet();
					posToId.put(pos, set);
				}
				set.add(node.id);
			}
		}
		
		/** Removes the specified node from all positions from nodespace */
		public void popNode(GenNode node) {
			if(node.net != null) node.net.destroy();
			for(BlockPos pos : node.positions) {
				HashSet<Long> set = posToId.get(pos);
				if(set != null) {
					set.remove(node.id);
					if(set.isEmpty()) posToId.remove(pos);
				}
			}
			node.expired = true;
		}
	}
	/*
	 * yeah this shit isn't gonna work because this allows multiple nodes of the same type in the same pos
	 * (we don't want that) which also makes it near impossible to do per-type position node lookups
	 * (sure it's possible but we are gonna have to iterate over every possible node in that spot, which is
	 * usually 1, but who knows how we end up using this system so i'd rather not)
	 */
}
