package com.hbm.tileentity.conductor;

import java.util.ArrayList;
import java.util.List;

import com.hbm.calc.UnionOfTileEntitiesAndBooleansForFluids;
import com.hbm.handler.FluidTypeHandler.FluidTypeTheOldOne;
import com.hbm.interfaces.IFluidDuct;
import net.minecraft.tileentity.TileEntity;

public class TileEntityGasDuctSolid extends TileEntity implements IFluidDuct {

	public FluidTypeTheOldOne type = FluidTypeTheOldOne.GAS;
	public List<UnionOfTileEntitiesAndBooleansForFluids> uoteab = new ArrayList<UnionOfTileEntitiesAndBooleansForFluids>();

	@Override
	public FluidTypeTheOldOne getType() {
		return type;
	}

}
