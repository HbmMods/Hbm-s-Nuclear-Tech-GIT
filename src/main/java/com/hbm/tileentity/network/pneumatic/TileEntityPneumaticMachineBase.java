package com.hbm.tileentity.network.pneumatic;

import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.tileentity.network.pneumatic.TileEntityPneumoTube.PneumaticNode;
import com.hbm.uninos.UniNodespace;
import com.hbm.uninos.networkproviders.PneumaticNetworkProvider;
import com.hbm.util.fauxpointtwelve.BlockPos;

import api.hbm.ntl.IPneumaticConnector;
import api.hbm.ntl.StackCache;

public abstract class TileEntityPneumaticMachineBase extends TileEntityMachineBase implements IPneumaticConnector, IGUIProvider {
	
	protected PneumaticNode node;
	public StackCache cache;

	public TileEntityPneumaticMachineBase(int slotCount) {
		super(slotCount);
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(this.node == null || this.node.expired) {
				if(this.cache != null) this.cache.dissolveCache();
				
				this.node = (PneumaticNode) UniNodespace.getNode(worldObj, xCoord, yCoord, zCoord, PneumaticNetworkProvider.THE_PROVIDER);
				
				if(this.node == null || this.node.expired) {
					this.node = (PneumaticNode) new PneumaticNode(new BlockPos(xCoord, yCoord, zCoord)).setStandardConnections(xCoord, yCoord, zCoord);
					UniNodespace.createNode(worldObj, this.node);
				}
			}
			
			if(this.cache == null || this.cache.hasExpired) {
				this.cache = new StackCache(xCoord, yCoord, zCoord);
			}
			
			if(this.node != null && this.node.hasValidNet()) {
				this.node.net.addStackCache(cache);
			}
		}
	}

	@Override
	public void invalidate() {
		super.invalidate();

		if(!worldObj.isRemote && this.node != null) {
			UniNodespace.destroyNode(worldObj, xCoord, yCoord, zCoord, PneumaticNetworkProvider.THE_PROVIDER);
		}
		
		if(this.cache != null) this.cache.dissolveCache();
	}

	@Override
	public void onChunkUnload() {
		super.onChunkUnload();

		if(!worldObj.isRemote && this.node != null) {
			UniNodespace.destroyNode(worldObj, xCoord, yCoord, zCoord, PneumaticNetworkProvider.THE_PROVIDER);
		}
		
		if(this.cache != null) this.cache.dissolveCache();
	}
}
