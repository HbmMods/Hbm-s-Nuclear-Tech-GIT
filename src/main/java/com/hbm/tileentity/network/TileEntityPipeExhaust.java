package com.hbm.tileentity.network;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.util.Compat;

import api.hbm.fluid.IFluidConductor;
import api.hbm.fluid.IPipeNet;
import api.hbm.fluid.PipeNet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPipeExhaust extends TileEntity implements IFluidConductor {
	
	public IPipeNet[] nets = new IPipeNet[3];
	
	public FluidType[] getSmokes() {
		return new FluidType[] {Fluids.SMOKE, Fluids.SMOKE_LEADED, Fluids.SMOKE_POISON};
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote && canUpdate()) {
			
			for(int i = 0; i < 3; i++) nets[i] = null;

			for(FluidType type : getSmokes()) {
				this.connect(type);
				
				if(this.getPipeNet(type) == null) {
					this.setPipeNet(type, new PipeNet(type).joinLink(this));
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
	public void invalidate() {
		super.invalidate();
		
		if(!worldObj.isRemote) {
			
			for(int i = 0; i < 3; i++) {
				if(nets[i] != null) {
					nets[i].destroy();
				}
			}
		}
	}
	@Override
	public boolean canUpdate() {
		
		if(this.isInvalid()) return false;
		
		for(IPipeNet net : nets) {
			if(net == null || !net.isValid()) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		return dir != ForgeDirection.UNKNOWN && (type == Fluids.SMOKE || type == Fluids.SMOKE_LEADED || type == Fluids.SMOKE_POISON);
	}

	@Override
	public long getDemand(FluidType type, int pressure) {
		return 0;
	}

	@Override
	public IPipeNet getPipeNet(FluidType type) {

		if(type == Fluids.SMOKE) return nets[0];
		if(type == Fluids.SMOKE_LEADED) return nets[1];
		if(type == Fluids.SMOKE_POISON) return nets[2];
		return null;
	}

	@Override
	public void setPipeNet(FluidType type, IPipeNet network) {

		if(type == Fluids.SMOKE) nets[0] = network;
		if(type == Fluids.SMOKE_LEADED) nets[1] = network;
		if(type == Fluids.SMOKE_POISON) nets[2] = network;
	}

	public boolean isLoaded = true;
	
	@Override
	public boolean isLoaded() {
		return isLoaded;
	}

	@Override
	public void onChunkUnload() {
		super.onChunkUnload();
		this.isLoaded = false;
	}
}
