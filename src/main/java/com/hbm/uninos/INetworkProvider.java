package com.hbm.uninos;

import com.hbm.util.fauxpointtwelve.BlockPos;

/**
 * Each instance of a network provider is a valid "type" of node in UNINOS
 * @author hbm
 */
public interface INetworkProvider<T extends NodeNet> {

	public T provideNetwork();
	public GenNode<T> provideNode(BlockPos... positions);
}
