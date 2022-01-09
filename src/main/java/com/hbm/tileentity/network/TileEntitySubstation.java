package com.hbm.tileentity.network;

import com.hbm.blocks.BlockDummyable;

import api.hbm.energy.IEnergyConductor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;

public class TileEntitySubstation extends TileEntityPylonBase {

	@Override
	public ConnectionType getConnectionType() {
		return ConnectionType.QUAD;
	}

	@Override
	public Vec3[] getMountPos() {
		
		double topOff = 5.25;
		Vec3 vec = Vec3.createVectorHelper(1, 0, 0);

		switch(getBlockMetadata() - BlockDummyable.offset) {
		case 2: vec.rotateAroundY((float) Math.PI * 0.0F); break;
		case 4: vec.rotateAroundY((float) Math.PI * 0.5F); break;
		case 3: vec.rotateAroundY((float) Math.PI * 0.0F); break;
		case 5: vec.rotateAroundY((float) Math.PI * 0.5F); break;
		}
		
		return new Vec3[] {
				Vec3.createVectorHelper(0.5 + vec.xCoord * 0.5, topOff, 0.5 + vec.zCoord * 0.5),
				Vec3.createVectorHelper(0.5 + vec.xCoord * 1.5, topOff, 0.5 + vec.zCoord * 1.5),
				Vec3.createVectorHelper(0.5 - vec.xCoord * 0.5, topOff, 0.5 - vec.zCoord * 0.5),
				Vec3.createVectorHelper(0.5 - vec.xCoord * 1.5, topOff, 0.5 - vec.zCoord * 1.5),
		};
	}

	@Override
	public Vec3 getConnectionPoint() {
		return Vec3.createVectorHelper(xCoord + 0.5, yCoord + 5.25, zCoord + 0.5);
	}

	@Override
	public double getMaxWireLength() {
		return 20;
	}
	
	@Override
	protected void connect() {

		manageNets(worldObj.getTileEntity(xCoord + 2, yCoord, zCoord - 1));
		manageNets(worldObj.getTileEntity(xCoord + 2, yCoord, zCoord + 1));
		manageNets(worldObj.getTileEntity(xCoord - 2, yCoord, zCoord - 1));
		manageNets(worldObj.getTileEntity(xCoord - 2, yCoord, zCoord + 1));
		manageNets(worldObj.getTileEntity(xCoord - 1, yCoord, zCoord + 2));
		manageNets(worldObj.getTileEntity(xCoord + 1, yCoord, zCoord + 2));
		manageNets(worldObj.getTileEntity(xCoord - 1, yCoord, zCoord - 2));
		manageNets(worldObj.getTileEntity(xCoord + 1, yCoord, zCoord - 2));
		
		super.connect();
	}
	
	private void manageNets(TileEntity te) {
		
		if(te instanceof IEnergyConductor) {
			
			IEnergyConductor conductor = (IEnergyConductor) te;
			
			if(this.getPowerNet() == null && conductor.getPowerNet() != null) {
				conductor.getPowerNet().joinLink(this);
			}
			
			if(this.getPowerNet() != null && conductor.getPowerNet() != null && this.getPowerNet() != conductor.getPowerNet()) {
				conductor.getPowerNet().joinNetworks(this.getPowerNet());
			}
		}
	}
}
