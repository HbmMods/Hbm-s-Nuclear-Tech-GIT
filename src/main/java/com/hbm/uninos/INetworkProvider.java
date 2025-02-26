package com.hbm.uninos;

/**
 * Each instance of a network provider is a valid "type" of node in UNINOS
 * @author hbm
 */
public interface INetworkProvider {

	public INodeNet<? extends INetworkProvider> provideNetwork();
}
