package com.hbm.tileentity.network;

import java.util.ArrayList;
import java.util.List;

import api.hbm.energy.IEnergyConductor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityConnector extends TileEntityPylonBase {

	@Override
	public ConnectionType getConnectionType() {
		return ConnectionType.SINGLE;
	}

	@Override
	public Vec3[] getMountPos() {
		return new Vec3[] {Vec3.createVectorHelper(0.5, 0.5, 0.5)};
	}

	@Override
	public double getMaxWireLength() {
		return 10;
	}
	
	@Override
	public List<int[]> getConnectionPoints() {
		List<int[]> pos = new ArrayList(connected);
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata()).getOpposite();
		//pos.add(new int[] {xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ});
		
		TileEntity te = worldObj.getTileEntity(xCoord, yCoord, zCoord);
		
		if(te instanceof IEnergyConductor) {
			
			IEnergyConductor conductor = (IEnergyConductor) te;
			
			if(conductor.canConnect(dir.getOpposite())) {
				
				if(this.getPowerNet() == null && conductor.getPowerNet() != null) {
					conductor.getPowerNet().joinLink(this);
				}
				
				if(this.getPowerNet() != null && conductor.getPowerNet() != null && this.getPowerNet() != conductor.getPowerNet()) {
					conductor.getPowerNet().joinNetworks(this.getPowerNet());
				}
			}
		}
		
		return pos;
	}

	@Override
	public boolean canConnect(ForgeDirection dir) { //i've about had it with your fucking bullshit
		return ForgeDirection.getOrientation(this.getBlockMetadata()).getOpposite() == dir;
	}
}
