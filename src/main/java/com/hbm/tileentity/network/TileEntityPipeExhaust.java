package com.hbm.tileentity.network;

import java.util.HashMap;
import java.util.Map.Entry;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.util.Compat;

import api.hbm.fluid.IFluidConductor;
import api.hbm.fluid.IPipeNet;
import api.hbm.fluid.PipeNet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPipeExhaust extends TileEntity implements IFluidConductor {
	
	public HashMap<FluidType, IPipeNet> nets = new HashMap();

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote && canUpdate()) {
			
			//we got here either because the net doesn't exist or because it's not valid, so that's safe to assume
			this.nets.clear();
			
			for(Entry<FluidType, IPipeNet> entry : nets.entrySet()) {
				
				this.connect(entry.getKey());
				
				if(this.getPipeNet(entry.getKey()) == null) {
					this.setPipeNet(entry.getKey(), new PipeNet(entry.getKey()).joinLink(this));
				}
			}
		}
	}
	
	protected void connect(FluidType type) {
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			
			TileEntity te = Compat.getTileStandard(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
			
			if(te instanceof IFluidConductor) {
				
				IFluidConductor conductor = (IFluidConductor) te;
				
				if(!conductor.canConnect(type, dir.getOpposite()))
					continue;
				
				if(this.getPipeNet(type) == null && conductor.getPipeNet(type) != null) {
					conductor.getPipeNet(type).joinLink(this);
				}
				
				if(this.getPipeNet(type) != null && conductor.getPipeNet(type) != null && this.getPipeNet(type) != conductor.getPipeNet(type)) {
					conductor.getPipeNet(type).joinNetworks(this.getPipeNet(type));
				}
			}
		}
	}
	
	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		return type == Fluids.SMOKE || type == Fluids.SMOKE_LEADED || type == Fluids.SMOKE_POISON;
	}

	@Override
	public long getDemand(FluidType type, int pressure) {
		return 0;
	}

	@Override
	public IPipeNet getPipeNet(FluidType type) {
		return nets.get(type);
	}

	@Override
	public void setPipeNet(FluidType type, IPipeNet network) {
		nets.put(type, network);
	}
}
