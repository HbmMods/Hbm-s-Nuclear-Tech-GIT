package com.hbm.tileentity.machine;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;

import io.netty.buffer.ByteBuf;

public class TileEntityMachinePumpSteam extends TileEntityMachinePumpBase {

	public FluidTank steam;
	public FluidTank lps;
	
	public TileEntityMachinePumpSteam() {
		super();
		water = new FluidTank(Fluids.WATER, steamSpeed * 100);
		steam = new FluidTank(Fluids.STEAM, 1_000);
		lps = new FluidTank(Fluids.SPENTSTEAM, 10);
	}
	
	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {water, steam, lps};
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {water, lps};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {steam};
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		steam.serialize(buf);
		lps.serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		steam.deserialize(buf);
		lps.deserialize(buf);
	}

	@Override
	protected boolean canOperate() {
		return steam.getFill() >= 100 && lps.getMaxFill() - lps.getFill() > 0 && water.getFill() < water.getMaxFill();
	}

	@Override
	protected void operate() {
		steam.setFill(steam.getFill() - 100);
		lps.setFill(lps.getFill() + 1);
		water.setFill(Math.min(water.getFill() + steamSpeed, water.getMaxFill()));
	}
}
