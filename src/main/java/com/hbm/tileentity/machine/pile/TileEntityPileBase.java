package com.hbm.tileentity.machine.pile;

import com.hbm.handler.neutron.NeutronNodeWorld;
import com.hbm.handler.neutron.NeutronNodeWorld.StreamWorld;
import com.hbm.handler.neutron.PileNeutronHandler;
import com.hbm.handler.neutron.PileNeutronHandler.PileNeutronStream;
import com.hbm.handler.neutron.PileNeutronHandler.PileNeutronNode;

import com.hbm.util.fauxpointtwelve.BlockPos;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;

public abstract class TileEntityPileBase extends TileEntity {

	@Override
	public abstract void updateEntity();

	@Override
	public void invalidate() {
		super.invalidate();
		NeutronNodeWorld.removeNode(worldObj, new BlockPos(this));
	}

	@Override
	public void onChunkUnload() {
		super.onChunkUnload();
		NeutronNodeWorld.removeNode(worldObj, new BlockPos(this));
	}

	protected void castRay(int flux) {

		BlockPos pos = new BlockPos(this);

		if(flux == 0) {
			// simple way to remove the node from the cache when no flux is going into it!
			NeutronNodeWorld.removeNode(worldObj, pos);
			return;
		}

		StreamWorld streamWorld = NeutronNodeWorld.getOrAddWorld(worldObj);
		PileNeutronNode node = (PileNeutronNode) streamWorld.getNode(pos);

		if(node == null) {
			node = PileNeutronHandler.makeNode(streamWorld, this);
			streamWorld.addNode(node);
		}

		Vec3 neutronVector = Vec3.createVectorHelper(1, 0, 0);

		neutronVector.rotateAroundZ((float)(Math.PI * 2D * worldObj.rand.nextDouble()));
		neutronVector.rotateAroundY((float)(Math.PI * 2D * worldObj.rand.nextDouble()));
		neutronVector.rotateAroundX((float)(Math.PI * 2D * worldObj.rand.nextDouble()));

		new PileNeutronStream(node, neutronVector, flux);
	}
}
