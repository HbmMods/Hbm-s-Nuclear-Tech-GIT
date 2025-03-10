package com.hbm.tileentity.network;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.uninos.UniNodespace;

import api.hbm.fluidmk2.FluidNode;
import api.hbm.fluidmk2.IFluidPipeMK2;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPipeExhaust extends TileEntity implements IFluidPipeMK2 {
	
	protected FluidNode[] nodes = new FluidNode[3];
	protected FluidType[] smokes = new FluidType[] {Fluids.SMOKE, Fluids.SMOKE_LEADED, Fluids.SMOKE_POISON};
	
	public FluidType[] getSmokes() {
		return smokes;
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			for(int i = 0; i < getSmokes().length; i++) {
				if(this.nodes[i] == null || this.nodes[i].expired) {
					this.nodes[i] = (FluidNode) UniNodespace.getNode(worldObj, xCoord, yCoord, zCoord, getSmokes()[i].getNetworkProvider());
						
					if(this.nodes[i] == null || this.nodes[i].expired) {
						this.nodes[i] = this.createNode(getSmokes()[i]);
						UniNodespace.createNode(worldObj, this.nodes[i]);
					}
				}
			}
		}
	}

	@Override
	public void invalidate() {
		super.invalidate();

		if(!worldObj.isRemote) {
			for(int i = 0; i < getSmokes().length; i++) {
				if(this.nodes[i] != null) {
					UniNodespace.destroyNode(worldObj, xCoord, yCoord, zCoord, getSmokes()[i].getNetworkProvider());
				}
			}
		}
	}
	
	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		return dir != ForgeDirection.UNKNOWN && (type == Fluids.SMOKE || type == Fluids.SMOKE_LEADED || type == Fluids.SMOKE_POISON);
	}
}
