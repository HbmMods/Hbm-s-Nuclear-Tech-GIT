package com.hbm.uninos;

import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import net.minecraft.world.World;

/**
 * Each instance of a network provider is a valid "type" of node in UNINOS
 * @author hbm
 */
public interface INetworkProvider<T extends NodeNet> {

	public T provideNetwork();
	public GenNode<T> provideNode(BlockPos... positions);
	
	public default void spawnDebugParticles(World world, DirPos pos, boolean receive) { }
}
