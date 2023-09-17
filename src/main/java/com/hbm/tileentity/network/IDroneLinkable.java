package com.hbm.tileentity.network;

import com.hbm.util.fauxpointtwelve.BlockPos;

public interface IDroneLinkable {

	public BlockPos getPoint();
	public void setNextTarget(int x, int y, int z);
}
