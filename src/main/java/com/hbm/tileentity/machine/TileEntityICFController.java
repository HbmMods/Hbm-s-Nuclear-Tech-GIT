package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.tileentity.TileEntityTickingBase;
import com.hbm.util.fauxpointtwelve.BlockPos;

import api.hbm.energymk2.IEnergyReceiverMK2;

public class TileEntityICFController extends TileEntityTickingBase implements IEnergyReceiverMK2 {
	
	public long power;
	
	public int cellCount;
	public int emitterCount;
	public int capacitorCount;
	public int turbochargerCount;

	protected List<BlockPos> ports = new ArrayList();
	
	public boolean assembled;

	@Override
	public String getInventoryName() {
		return "container.icfController";
	}

	@Override
	public void updateEntity() {
		
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public long getMaxPower() {
		return capacitorCount * 1_000_000 + turbochargerCount * 2_500_000; //TEMP
	}
}
