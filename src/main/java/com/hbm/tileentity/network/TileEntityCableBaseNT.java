package com.hbm.tileentity.network;

import api.hbm.energy.IEnergyConductor;
import api.hbm.energy.IPowerNet;
import api.hbm.energy.PowerNet;
import api.hbm.energymk2.IEnergyConductorMK2;
import api.hbm.energymk2.Nodespace;
import api.hbm.energymk2.Nodespace.PowerNode;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCableBaseNT extends TileEntity implements IEnergyConductor, IEnergyConductorMK2 {
	
	protected IPowerNet network;
	protected PowerNode node;

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote && canUpdate()) {
			
			//we got here either because the net doesn't exist or because it's not valid, so that's safe to assume
			this.setPowerNet(null);
			
			this.connect();
			
			if(this.getPowerNet() == null) {
				this.setPowerNet(new PowerNet().joinLink(this));
			}
		}
		
		if(!worldObj.isRemote) {
			
			if(this.node == null || this.node.expired) {
				this.node = Nodespace.getNode(worldObj, xCoord, yCoord, zCoord);
				
				if(this.node == null || this.node.expired) {
					this.node = this.createNode();
					Nodespace.createNode(worldObj, this.node);
				}
			}
		}
	}
	
	public void onNodeDestroyedCallback() {
		this.node = null;
	}
	
	protected void connect() {
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			
			TileEntity te = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
			
			if(te instanceof IEnergyConductor) {
				
				IEnergyConductor conductor = (IEnergyConductor) te;
				
				if(!conductor.canConnect(dir.getOpposite()))
					continue;
				
				if(this.getPowerNet() == null && conductor.getPowerNet() != null) {
					conductor.getPowerNet().joinLink(this);
				}
				
				if(this.getPowerNet() != null && conductor.getPowerNet() != null && this.getPowerNet() != conductor.getPowerNet()) {
					conductor.getPowerNet().joinNetworks(this.getPowerNet());
				}
			}
		}
	}

	@Override
	public void invalidate() {
		super.invalidate();
		
		if(!worldObj.isRemote) {
			if(this.network != null) {
				this.network.reevaluate();
				this.network = null;
			}
			
			if(this.node != null) {
				Nodespace.destroyNode(worldObj, xCoord, yCoord, zCoord);
			}
		}
	}

	/**
	 * Only update until a power net is formed, in >99% of the cases it should be the first tick. Everything else is handled by neighbors and the net itself.
	 */
	@Override
	public boolean canUpdate() {
		return (this.network == null || !this.network.isValid()) && !this.isInvalid();
	}

	@Override
	public boolean canConnect(ForgeDirection dir) {
		return dir != ForgeDirection.UNKNOWN;
	}

	@Override
	public long getPower() {
		return 0;
	}

	@Override
	public long getMaxPower() {
		return 0;
	}

	@Override
	public void setPowerNet(IPowerNet network) {
		this.network = network;
	}

	@Override
	public long transferPower(long power) {
		
		if(this.network == null)
			return power;
		
		return this.network.transferPower(power);
	}

	@Override
	public IPowerNet getPowerNet() {
		return this.network;
	}
}
