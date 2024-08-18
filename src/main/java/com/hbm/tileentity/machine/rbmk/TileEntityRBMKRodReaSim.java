package com.hbm.tileentity.machine.rbmk;

import com.hbm.handler.rbmkmk2.RBMKHandler;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.ColumnType;

import com.hbm.util.fauxpointtwelve.BlockPos;
import net.minecraft.util.Vec3;

public class TileEntityRBMKRodReaSim extends TileEntityRBMKRod {
	
	public TileEntityRBMKRodReaSim() {
		super();
	}

	@Override
	public String getName() {
		return "container.rbmkReaSim";
	}
	
	@Override
	public void spreadFlux(double flux, double ratio) {

		int count = RBMKDials.getReaSimCount(worldObj);

		for (int i = 0; i < count; i++) {
			Vec3 neutronVector = Vec3.createVectorHelper(1, 0, 0);

			neutronVector.rotateAroundY((float)(Math.PI * 2D * worldObj.rand.nextDouble()));

			new RBMKHandler.NeutronStream(RBMKHandler.makeNode(this), neutronVector, flux, ratio);
			// Create new neutron streams
		}
	}

	@Override
	public ColumnType getConsoleType() {
		return ColumnType.FUEL_SIM;
	}
}
