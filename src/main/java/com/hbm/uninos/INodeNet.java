package com.hbm.uninos;

public interface INodeNet<T extends INetworkProvider> {

	public boolean isValid();
	public void destroy();
}
