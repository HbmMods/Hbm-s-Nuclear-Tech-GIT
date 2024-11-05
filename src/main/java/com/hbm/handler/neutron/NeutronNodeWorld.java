package com.hbm.handler.neutron;

import com.hbm.util.fauxpointtwelve.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NeutronNodeWorld {
	// HashMap of all neutron nodes and their positions.
	protected static HashMap<BlockPos, NeutronNode> nodeCache = new HashMap<>();

	public static void addNode(NeutronNode node) {
		nodeCache.put(node.pos, node);
	}

	public static void removeNode(BlockPos position) {
		nodeCache.remove(position);
	}

	public static NeutronNode getNode(BlockPos position) {
		return nodeCache.get(position);
	}

	public static void removeAllNodes() {
		nodeCache.clear();
	}

	// List of all stream worlds.
	public static HashMap<World, StreamWorld> streamWorlds = new HashMap<>();

	public static class StreamWorld {

		List<NeutronStream> streams;

		public StreamWorld() {
			streams = new ArrayList<>();
		}

		public void addStream(NeutronStream stream) {
			this.streams.add(stream);
		}

		public void removeAllStreams() {
			this.streams.clear();
		}

		public void removeAllStreamsOfType(NeutronStream.NeutronType type) {
			List<NeutronStream> toRemove = new ArrayList<>();
			for (NeutronStream stream : streams) {
				if (stream.type == type)
					toRemove.add(stream);
			}
			toRemove.forEach((stream) -> streams.remove(stream));
		}
	}

	public static void removeAllWorlds() {
		streamWorlds.clear();
	}
}
