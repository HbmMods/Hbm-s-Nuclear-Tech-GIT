package com.hbm.tileentity.conductor;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.calc.UnionOfTileEntitiesAndBooleansForFluids;
import com.hbm.interfaces.IFluidDuct;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.tileentity.network.TileEntityPipeBaseNT;

import net.minecraft.tileentity.TileEntity;

public class TileEntityOilDuctSolid extends TileEntity implements IFluidDuct {

	public FluidType type = Fluids.OIL;
	public List<UnionOfTileEntitiesAndBooleansForFluids> uoteab = new ArrayList<UnionOfTileEntitiesAndBooleansForFluids>();
	
	@Override
	public void updateEntity() {
		
		//if(!worldObj.isRemote)
		//	PacketDispatcher.wrapper.sendToAll(new TEFluidPipePacket(xCoord, yCoord, zCoord, type));
		
		//this.updateConnections();
		
		worldObj.setBlock(xCoord, yCoord, zCoord, ModBlocks.fluid_duct_paintable);
		
		TileEntity tile = worldObj.getTileEntity(xCoord, yCoord, zCoord);
		if(tile instanceof TileEntityPipeBaseNT) {
			((TileEntityPipeBaseNT) tile).setType(this.type);
		}
	}

	@Override
	public FluidType getType() {
		return type;
	}

	@Override
	public boolean setType(FluidType type) {
		return false;
	}
}
