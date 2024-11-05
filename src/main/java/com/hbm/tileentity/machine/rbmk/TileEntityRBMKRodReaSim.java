package com.hbm.tileentity.machine.rbmk;

import com.hbm.handler.neutron.NeutronNodeWorld;
import com.hbm.handler.neutron.RBMKNeutronHandler;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.ColumnType;

import com.hbm.util.fauxpointtwelve.BlockPos;
import net.minecraft.util.Vec3;

import static com.hbm.handler.neutron.RBMKNeutronHandler.*;

public class TileEntityRBMKRodReaSim extends TileEntityRBMKRod {

	public TileEntityRBMKRodReaSim() {
		super();
	}

	@Override
	public String getName() {
		return "container.rbmkReaSim";
	}

	private BlockPos pos;

	@Override
	public void spreadFlux(double flux, double ratio) {

		if(pos == null)
			pos = new BlockPos(this);

		if (flux == 0) {
			// simple way to remove the node from the cache when no flux is going into it!
			NeutronNodeWorld.removeNode(pos);
			return;
		}

		RBMKNeutronNode node = (RBMKNeutronNode) NeutronNodeWorld.getNode(pos);

		if(node == null) {
			node = makeNode(this);
			NeutronNodeWorld.addNode(node);
		}

		int count = RBMKDials.getReaSimCount(worldObj);

		for (int i = 0; i < count; i++) {
			Vec3 neutronVector = Vec3.createVectorHelper(1, 0, 0);

			neutronVector.rotateAroundY((float)(Math.PI * 2D * worldObj.rand.nextDouble()));

			new RBMKNeutronHandler.RBMKNeutronStream(makeNode(this), neutronVector, flux, ratio);
			// Create new neutron streams
		}
	}

	@Override
	public ColumnType getConsoleType() {
		return ColumnType.FUEL_SIM;
	}
}
