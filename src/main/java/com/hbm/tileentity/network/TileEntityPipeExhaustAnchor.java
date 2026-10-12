package com.hbm.tileentity.network;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.uninos.UniNodespace;
import com.hbm.util.fauxpointtwelve.BlockPos;

import api.hbm.fluidmk2.FluidNode;
import api.hbm.fluidmk2.IFluidPipeMK2;

import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPipeExhaustAnchor extends TileEntityPipelineBase implements IFluidPipeMK2 {

	protected FluidNode[] nodes = new FluidNode[3];
	protected FluidType[] smokes = new FluidType[] { Fluids.SMOKE, Fluids.SMOKE_LEADED, Fluids.SMOKE_POISON };

	@Override
	public NetworkType getNetworkType() {
		return NetworkType.EXHAUST;
	}

	@Override
	public FluidNode createNode(FluidType type) {
		return this.initNode(new FluidNode(type.getNetworkProvider(), new BlockPos(xCoord, yCoord, zCoord)));
	}

	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		return this.getOppositeDir() == dir && (type == Fluids.SMOKE || type == Fluids.SMOKE_LEADED || type == Fluids.SMOKE_POISON);
	}

	@Override
	public void updateEntity() {
		if(!worldObj.isRemote) {
			for(int i = 0; i < smokes.length; i++) {
				updateNode(i);
			}
		}
	}

	@Override
	protected void addNodeConnection(int x, int y, int z) {
		for(int i = 0; i < smokes.length; i++) {
			updateNode(i);
			this.linkNode(this.nodes[i], x, y, z);
		}
	}

	@Override
	protected void destroyNode(int x, int y, int z) {
		for(int i = 0; i < smokes.length; i++) {
			UniNodespace.destroyNode(worldObj, x, y, z, smokes[i].getNetworkProvider());
		}
	}
	
	private void updateNode(int i) {
		this.nodes[i] = this.ensureNode(this.nodes[i], smokes[i].getNetworkProvider(), () -> createNode(smokes[i]));
	}
}
