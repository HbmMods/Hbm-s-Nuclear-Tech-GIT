package com.hbm.tileentity.network.pneumatic;

import com.hbm.tileentity.network.TileEntityPipelineBase;
import com.hbm.tileentity.network.pneumatic.TileEntityPneumoTube.PneumaticNode;
import com.hbm.uninos.UniNodespace;
import com.hbm.uninos.networkproviders.PneumaticNetworkProvider;
import com.hbm.util.fauxpointtwelve.BlockPos;

import api.hbm.ntl.IPneumaticConnector;

import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPneumaticPipeAnchor extends TileEntityPipelineBase implements IPneumaticConnector {

	protected PneumaticNode node;

	@Override
	public NetworkType getNetworkType() {
		return NetworkType.PNEUMATIC;
	}

	@Override
	public boolean canConnectPneumatic(ForgeDirection dir) {
		return this.getOppositeDir() == dir;
	}

	@Override
	public void updateEntity() {
		if(!worldObj.isRemote) {
			this.updateNode();
		}
	}

	@Override
	protected void addNodeConnection(int x, int y, int z) {
		this.updateNode();
		this.linkNode(this.node, x, y, z);
	}

	@Override
	protected void destroyNode(int x, int y, int z) {
		UniNodespace.destroyNode(worldObj, x, y, z, PneumaticNetworkProvider.THE_PROVIDER);
	}
	
	private void updateNode() {
		this.node = this.ensureNode(this.node, PneumaticNetworkProvider.THE_PROVIDER, () -> this.initNode(new PneumaticNode(new BlockPos(xCoord, yCoord, zCoord))));
	}
}
