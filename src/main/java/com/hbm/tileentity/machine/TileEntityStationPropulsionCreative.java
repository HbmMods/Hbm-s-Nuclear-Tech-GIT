package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.dim.CelestialBody;

import api.hbm.tile.IPropulsion;
import net.minecraft.tileentity.TileEntity;

public class TileEntityStationPropulsionCreative extends TileEntity implements IPropulsion {

	private boolean hasRegistered = false;

	@Override
	public void updateEntity() {
		if(!CelestialBody.inOrbit(worldObj)) return;

		if(!worldObj.isRemote) {
			if(!hasRegistered) {
				registerPropulsion();
				hasRegistered = true;
			}
		}
	}

	@Override
	public void invalidate() {
		super.invalidate();

		if(hasRegistered) {
			unregisterPropulsion();
			hasRegistered = false;
		}
	}

	@Override
	public void onChunkUnload() {
		super.onChunkUnload();

		if(hasRegistered) {
			unregisterPropulsion();
			hasRegistered = false;
		}
	}

	@Override
	public TileEntity getTileEntity() {
		return this;
	}

	@Override
	public boolean canPerformBurn(int shipMass, double deltaV) {
		return true;
	}

	@Override
	public void addErrors(List<String> errors) { }

	@Override
	public float getThrust() {
		return 10_000_000;
	}

	@Override
	public int startBurn() {
		return 20;
	}

	@Override
	public int endBurn() {
		return 20;
	}
	
}
