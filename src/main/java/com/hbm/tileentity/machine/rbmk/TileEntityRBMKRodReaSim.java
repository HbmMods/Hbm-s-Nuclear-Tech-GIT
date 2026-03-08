package com.hbm.tileentity.machine.rbmk;

import com.hbm.handler.neutron.NeutronNodeWorld;
import com.hbm.handler.neutron.RBMKNeutronHandler;
import com.hbm.handler.neutron.NeutronNodeWorld.StreamWorld;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.ColumnType;
import com.hbm.util.Vec3NT;
import com.hbm.util.fauxpointtwelve.BlockPos;

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

		if(pos == null) pos = new BlockPos(this);

		if(flux == 0) {
			// simple way to remove the node from the cache when no flux is going into it!
			NeutronNodeWorld.removeNode(worldObj, pos);
			return;
		}

		StreamWorld streamWorld = NeutronNodeWorld.getOrAddWorld(worldObj);
		RBMKNeutronNode node = (RBMKNeutronNode) streamWorld.getNode(pos);

		if(node == null) {
			node = makeNode(streamWorld, this);
			streamWorld.addNode(node);
		}

		Vec3NT vec = new Vec3NT(1, 0, 0);
		vec.rotateAroundYDeg(worldObj.rand.nextInt(4) * 9D);
		for(int i = 0; i < 8; i++) {
			new RBMKNeutronHandler.RBMKNeutronStream(node, new Vec3NT(vec), flux * 0.75, ratio);
			vec.rotateAroundYDeg(45D);
		}
	}

	@Override
	public ColumnType getConsoleType() {
		return ColumnType.FUEL_SIM;
	}
}
