package com.hbm.uninos;

import java.util.HashMap;

import com.hbm.util.fauxpointtwelve.BlockPos;

import net.minecraft.world.World;

public class GenNodespace<T> {
	
	public HashMap<World, GenNodeWorld<T>> worlds = new HashMap<>();
	
	public GenNode<T> getNode(World world, int x, int y, int z) {
		GenNodeWorld nodeWorld = worlds.get(world);
		if(nodeWorld != null) return (GenNode<T>) nodeWorld.nodes.get(new BlockPos(x, y, z));
		return null;
	}
	
	public void createNode(World world, GenNode<T> node) {
		GenNodeWorld nodeWorld = worlds.get(world);
		if(nodeWorld == null) {
			nodeWorld = new GenNodeWorld();
			worlds.put(world, nodeWorld);
		}
		nodeWorld.pushNode(node);
	}
	
	public void destroyNode(World world, int x, int y, int z) {
		GenNode<T> node = getNode(world, x, y, z);
		if(node != null) {
			worlds.get(world).popNode(node);
		}
	}
}
